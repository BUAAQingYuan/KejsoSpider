package cn.kejso.Spider;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kejso.Config.Config;
import cn.kejso.Pipeline.MysqlPipeline;
import cn.kejso.Spider.Control.SpiderContainer;
import cn.kejso.Tool.SqlUtil;
import us.codecraft.webmagic.Spider;

//one.getStatus()
//one.isExitWhenComplete()
//one.notify();
//one.setEmptySleepTime(emptySleepTime);

//流程化的爬虫链处理，前后顺序启动
public class SpiderChain {
	
	private static Logger logger = LoggerFactory.getLogger(SpiderChain.class);
	
	private List<SpiderContainer> spiderqueue=new ArrayList<SpiderContainer>();
	
	private String chainname="AnonymousSpiderChain";
	
	public String getChainname() {
		return chainname;
	}

	public void setChainname(String chainname) {
		this.chainname = chainname;
	}
	
	
	
	//constructor
	public SpiderChain()
	{
		
	}
	
	public SpiderChain(String name)
	{
		this.chainname=name;
	}
	
	
	//添加爬虫节点
	public SpiderChain AddSpiderNode(SpiderContainer spider)
	{
		spiderqueue.add(spider);
		return this;
	}
	
	//去除爬虫节点
	public SpiderChain RemoveSpiderNode(SpiderContainer spider)
	{
		spiderqueue.remove(spider);
		return this;
	}
	
	//启动爬虫队列
	public  void  startSpiders(boolean chain)
	{
		logger.info("Start SpiderChain {} .",chainname);
		
		long chainstart = System.currentTimeMillis();
		
		//true,默认顺序启动
		if(chain)
		{
			
			for(SpiderContainer container:spiderqueue)
			{
				long start = System.currentTimeMillis();
				logger.info(container.getName()+" start ...");
				//添加url
				Spider current=container.getSpider();
				current.startUrls(container.getStartUrls()).run();
				
				//将本次更新记录记录到cache中
				SqlUtil.PrintPositionToCache(container.getTemplate());
				//如果爬虫没有停止
				logger.info("爬虫状态: "+current.getStatus().toString()+" .");
				
				logger.info(container.getName()+" end . Cost time:{}秒",(System.currentTimeMillis() - start) / 1000.0);
				logger.info("下载页面数 : {} .",current.getPageCount());
				logger.info(Config.Spider_Info_line);
			}
			
		}
		else{
			//异步启动
			for(SpiderContainer container:spiderqueue)
			{
				Spider current=container.getSpider();
				current.startUrls(container.getStartUrls()).runAsync();;
			}
		}
		
		logger.info(chainname+" end .Total Cost time:{}秒",(System.currentTimeMillis() - chainstart) / 1000.0);
		
	}

	//启动单个spider
	public static void startSpider(SpiderContainer container)
	{
		long start = System.currentTimeMillis();
		logger.info(container.getName()+" start ...");
		//添加url
		Spider current=container.getSpider();
		current.startUrls(container.getStartUrls()).run();
		
		//将本次更新记录记录到cache中
		SqlUtil.PrintPositionToCache(container.getTemplate());
		//如果爬虫没有停止
		logger.info("爬虫状态: "+current.getStatus().toString()+" .");
		
		logger.info(container.getName()+" end . Cost time:{}秒",(System.currentTimeMillis() - start) / 1000.0);
		logger.info("下载页面数 : {} .",current.getPageCount());
		logger.info(Config.Spider_Info_line);
	}
	
	
	
}
