package cn.kejso.Spider;

import java.util.ArrayList;
import java.util.List;

import javax.management.JMException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kejso.Config.Config;
import cn.kejso.Pipeline.MysqlPipeline;
import cn.kejso.Spider.Control.CustomHttpClientDownloader;
import cn.kejso.Spider.Control.SpiderContainer;
import cn.kejso.Spider.SpiderHandler.BasicTableHandler;
import cn.kejso.Template.SpiderConf;
import cn.kejso.Template.ToolEntity.GlobalConfig;
import cn.kejso.Tool.SqlUtil;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;

//one.getStatus()
//one.isExitWhenComplete()
//one.notify();
//one.setEmptySleepTime(emptySleepTime);

//流程化的爬虫链处理，前后顺序启动
public class SpiderChain {

	private static Logger logger = LoggerFactory.getLogger(SpiderChain.class);

	private List<SpiderContainer> spiderqueue = new ArrayList<SpiderContainer>();

	private String chainname = "AnonymousSpiderChain";
	
	private GlobalConfig global;

	public String getChainname() {
		return chainname;
	}

	public void setChainname(String chainname) {
		this.chainname = chainname;
	}
	
	public GlobalConfig getGlobal() {
		return global;
	}

	public void setGlobal(GlobalConfig global) {
		this.global = global;
	}

	// constructor
	public SpiderChain(GlobalConfig config) {
		this.global=config;
	}

	public SpiderChain(String name) {
		this.chainname = name;
	}

	// 添加爬虫节点
	public SpiderChain AddSpiderNode(SpiderContainer spider) {
		spiderqueue.add(spider);
		return this;
	}

	// 去除爬虫节点
	public SpiderChain RemoveSpiderNode(SpiderContainer spider) {
		spiderqueue.remove(spider);
		return this;
	}

	// 启动爬虫队列
	public void startSpiders(boolean chain,boolean restart) {
		logger.info("Start SpiderChain {} .", chainname);

		long chainstart = System.currentTimeMillis();

		// true,默认顺序启动
		if (chain) {

			for (SpiderContainer container : spiderqueue) {
				
				//retry的时间算在内
				long start = System.currentTimeMillis();
				logger.info(container.getName() + " start ...");
				
				SpiderConf currentconf = container.getTemplate();
				
				// before-table-handler  爬虫运行之前处理数据表
				if (currentconf.getBeforehandler() != null && !currentconf.getBeforehandler().equals("")) {
					logger.info("数据表前置处理...");
					try {
						Class handlerclass = Class.forName(currentconf.getBeforehandler());
						BasicTableHandler handler = (BasicTableHandler) handlerclass.newInstance();
						handler.handler(currentconf.getConfig().getTablename());
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
						logger.error("数据表前置处理失败。");
						e.printStackTrace();
					}

				}
				
				//爬虫运行，包含retry
				while (true) {
					
					Spider current = null;
					
					if(!container.isStart())
						logger.info("爬虫启动...");
					else
						logger.info("爬虫Retry {} ...",global.getCycleTimes()-container.getCycleTimes()+1);
					
					if (!container.isStart()) {
						// 如果是首次启动
						container.setStart();
						current = container.getSpider();
						container.AddgetStartUrlHandler(BuildSpider.getStartUrlHandler(container.getTemplate(), false,false));
					} else if (container.isStart()&&container.continueCycle() && SqlUtil.hasRetryItem(container.getTemplate())) {
						//retry
						// 创建一个新的实例，因为之前的实例无法导入抓取失败的URL
						//retry时 做出一些参数的调整?爬取间隔等...
						container.minusCycleTimes();
						current = BuildSpider.getSpider(container.getTemplate(), GlobalConfig.getCycleTimes() - container.getCycleTimes());
						container.AddgetStartUrlHandler(BuildSpider.getStartUrlHandler(container.getTemplate(), true,false));
					} else {
						//离开循环
						break;
					}
					
					// 添加初始序列、清空临时表并启动爬虫 
					// 设置下载器
					current.startUrls(container.getStartUrls()).setDownloader(new CustomHttpClientDownloader(currentconf));
					// when fetch ,clean temp table . when restart , don't clean temp table
					if(!restart)
					{
						SqlUtil.cleanTempTable(container.getTemplate());
					}
					//设置cache
					current.scheduler(new FileCacheQueueScheduler(Config.Spider_CacheDir+currentconf.getName())).setUUID(currentconf.getName());
					//添加监控器
					try {
						SpiderMonitor.instance().register(current);
					} catch (JMException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					current.run();

					// 如果爬虫没有停止
					logger.info("爬虫状态: " + current.getStatus().toString() + " .");
					logger.info("下载页面数 : {} .", current.getPageCount());
					
				}
				
				// after-table-handler 爬虫运行结束后，处理数据表
				if (currentconf.getAfterhandler() != null && !currentconf.getAfterhandler().equals("")) {
					logger.info("数据表后置处理...");
					try {
						Class handlerclass = Class.forName(currentconf.getAfterhandler());
						BasicTableHandler handler = (BasicTableHandler) handlerclass.newInstance();
						handler.handler(currentconf.getConfig().getTablename());
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
						logger.error("数据表后置处理失败。");
						e.printStackTrace();
					}

				}
				
				logger.info(container.getName() + " end . Cost time:{}秒",(System.currentTimeMillis() - start) / 1000.0);
				logger.info(Config.Spider_Info_line);
				
				// 因为Waiting for table metadata lock而暂时禁用drop table
				// SqlUtil.deleteTempTable(container.getTemplate());
			}
			
		} else {
			// 异步启动
			for (SpiderContainer container : spiderqueue) {
				Spider current = container.getSpider();
				current.startUrls(container.getStartUrls()).runAsync();
			}
		}

		logger.info(chainname + " end .Total Cost time:{}秒", (System.currentTimeMillis() - chainstart) / 1000.0);

	}

	
	// 启动单个spider
	public static void startSpider(SpiderContainer container) {
		long start = System.currentTimeMillis();
		logger.info(container.getName() + " start ...");
		// 添加url
		Spider current = container.getSpider();
		current.startUrls(container.getStartUrls()).run();

		// 将本次更新记录记录到cache中
		//SqlUtil.PrintPositionToCache(container.getTemplate());
		// 如果爬虫没有停止
		logger.info("爬虫状态: " + current.getStatus().toString() + " .");

		logger.info(container.getName() + " end . Cost time:{}秒", (System.currentTimeMillis() - start) / 1000.0);
		logger.info("下载页面数 : {} .", current.getPageCount());
		logger.info(Config.Spider_Info_line);
	}

	
	// 启动爬虫队列重试error urls
	public void startSpidersForErrorUrls(boolean chain)
	{
		logger.info("SpiderChain {} retry error urls.", chainname);

		long chainstart = System.currentTimeMillis();

		// true,默认顺序启动
		if (chain) {

			for (SpiderContainer container : spiderqueue) {
				
				//retry的时间算在内
				long start = System.currentTimeMillis();
				logger.info(container.getName() + " start ...");
				
				SpiderConf currentconf = container.getTemplate();
				
				//爬虫运行，包含retry. 爬取数据部分和重试错误URL部分使用相同的retry次数。
				while (true) {
					
					Spider current = null;
					
					if(!container.isStart())
						logger.info("爬虫启动...");
					else
						logger.info("爬虫Retry {} ...",global.getCycleTimes()-container.getCycleTimes()+1);
					
					if (!container.isStart()) {
						// 如果是首次启动
						container.setStart();
						current = container.getSpider();
						//第一次就readRetryUrlFromSql
						container.AddgetStartUrlHandler(BuildSpider.getStartUrlHandler(container.getTemplate(),false,true));
					} else if (container.isStart()&&container.continueCycle() && SqlUtil.hasRetryItem(container.getTemplate())) {
						//retry
						// 创建一个新的实例，因为之前的实例无法导入抓取失败的URL
						//retry时 做出一些参数的调整?爬取间隔等...
						container.minusCycleTimes();
						current = BuildSpider.getSpider(container.getTemplate(), GlobalConfig.getCycleTimes() - container.getCycleTimes());
						container.AddgetStartUrlHandler(BuildSpider.getStartUrlHandler(container.getTemplate(), true,true));
					} else {
						//离开循环
						break;
					}
					
					// 添加初始序列、清空临时表并启动爬虫 
					// 设置下载器
					current.startUrls(container.getStartUrls()).setDownloader(new CustomHttpClientDownloader(currentconf));
					SqlUtil.cleanTempTable(container.getTemplate());
					//retry过程设置cache
					current.scheduler(new FileCacheQueueScheduler(Config.Spider_CacheDir+currentconf.getName())).setUUID(currentconf.getName()+"_retry");
					//添加监控器
					try {
						SpiderMonitor.instance().register(current);
					} catch (JMException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					current.run();

					// 如果爬虫没有停止
					logger.info("爬虫状态: " + current.getStatus().toString() + " .");
					logger.info("下载页面数 : {} .", current.getPageCount());
					
				}
				
				
				logger.info(container.getName() + " end . Cost time:{}秒",(System.currentTimeMillis() - start) / 1000.0);
				logger.info(Config.Spider_Info_line);
				
				// 因为Waiting for table metadata lock而暂时禁用drop table
				// SqlUtil.deleteTempTable(container.getTemplate());
			}
			
		} else {
			// 异步启动
			for (SpiderContainer container : spiderqueue) {
				Spider current = container.getSpider();
				current.startUrls(container.getStartUrls()).runAsync();
			}
		}

		logger.info(chainname + " end .Total Cost time:{}秒", (System.currentTimeMillis() - chainstart) / 1000.0);
		
	}
	

}
