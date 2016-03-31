package cn.kejso.Spider;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kejso.Config.Config;
import cn.kejso.Pipeline.MysqlPipeline;
import cn.kejso.Spider.Control.SpiderContainer;
import us.codecraft.webmagic.Spider;

//one.getPageCount()
//one.getStartTime()
//one.getStatus()
//one.isExitWhenComplete()
//one.isSpawnUrl()
//one.notify();
//one.setEmptySleepTime(emptySleepTime);
//one.setExecutorService(executorService)
//one.setSpiderListeners(spiderListeners)
//流程化的爬虫链处理，前后顺序启动
public class SpiderChain {
	
	private static Logger logger = LoggerFactory.getLogger(SpiderChain.class);
	
	private List<SpiderContainer> spiderqueue=new ArrayList<SpiderContainer>();
	
	//添加爬虫节点
	public SpiderChain AddSpiderNode(SpiderContainer spider)
	{
		spiderqueue.add(spider);
		return this;
	}
	
	//启动爬虫队列
	public  void  startSpiders(boolean chain)
	{
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
				
				logger.info(container.getName()+" end . Cost time:{}秒",(System.currentTimeMillis() - start) / 1000.0);
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
		
	}
	
	
	
}
