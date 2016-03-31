package cn.kejso.Spider;

import cn.kejso.Config.Config;
import cn.kejso.PageProcess.ContentPageProcess;
import cn.kejso.PageProcess.ListPageProcess;
import cn.kejso.Pipeline.MysqlPipeline;
import cn.kejso.Template.ListAndContentTemplate;
import cn.kejso.Tool.SpiderUtil;
import cn.kejso.Tool.TemplateConstructor;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;

public class RunSpider {
	public static void main(String[] args) {
		
		ListAndContentTemplate template=TemplateConstructor.getListAndContentTemplate("configs\\wanfangpaper.xml");
		
		/*
        Spider.create(new ListPageProcess(template)).startUrls(template.getListconfig().getStarturls())
        											.thread(template.getThreadnum())
        											.addPipeline(new MysqlPipeline(template))
        											.run();
       */
	
		
	   Spider.create(new ContentPageProcess(template))//.startUrls(SpiderUtil.getTargetUrls(template))
	                                                  .scheduler(new FileCacheQueueScheduler(Config.Spider_CacheDir+template.getTaskname()))
	   												  .thread(template.getThreadnum())
	   												  .addPipeline(new MysqlPipeline(template))
	   												  .run();
	   												  
		
	  //Spider spider=new Spider(new ContentPageProcess(template));
		
		
    }
}
