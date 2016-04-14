package cn.kejso.Spider;

import java.util.ArrayList;
import java.util.List;

import cn.kejso.Config.Config;
import cn.kejso.PageProcess.ContentPageProcess;
import cn.kejso.PageProcess.ListPageProcess;
import cn.kejso.Pipeline.MysqlPipeline;
import cn.kejso.Spider.Control.FileCacheOnErrorListener;
import cn.kejso.Spider.Control.SpiderContainer;
import cn.kejso.Spider.Control.SpiderContainer.Function;
import cn.kejso.Template.ListAndContentTemplate;
import cn.kejso.Template.SpiderConf;
import cn.kejso.Template.ToolEntity.ContentConfig;
import cn.kejso.Template.ToolEntity.GlobalConfig;
import cn.kejso.Template.ToolEntity.ListConfig;
import cn.kejso.Tool.SpiderUtil;
import cn.kejso.Tool.SqlUtil;
import cn.kejso.Tool.TemplateConstructor;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;

public class RunSpider {
	public static void main(String[] args) {
		
		List<SpiderConf> confs=TemplateConstructor.getSpiderConf("configs\\wanfangpaper.xml");
		GlobalConfig global=TemplateConstructor.getGlobalConf("configs\\wanfangpaper.xml");
		
		/*
		Spider spiderurllist=Spider.create(new ListPageProcess(confs.get(0)))
				   .thread(global.getThreadnum())
				   .addPipeline(new MysqlPipeline(confs.get(0)));
		
		SpiderContainer container1=new SpiderContainer(spiderurllist,confs.get(0)).AddName("万方法律论文Url爬虫");
		container1.AddgetStartUrlHandler(new Function<Spider,SpiderConf>(){
			@Override
			public List<String> apply(Spider t, SpiderConf e) {
				ListConfig config=(ListConfig) confs.get(0).getConfig();
				return config.getStarturls();
			}});
       
		SpiderChain.startSpider(container1);
		*/
		
	   //listener
	   
	   List<SpiderListener> listeners=new ArrayList<SpiderListener>();
	   listeners.add(new FileCacheOnErrorListener(Config.Spider_ErrorDir+"wanfang"));
	   
	   ContentConfig config2=(ContentConfig)confs.get(1).getConfig();
	   Spider contentspider=Spider.create(new ContentPageProcess(confs.get(1)))
	                                                  .scheduler(new FileCacheQueueScheduler(Config.Spider_CacheDir+global.getTaskname()))
	   												  .thread(global.getThreadnum())
	   												  .addPipeline(new MysqlPipeline(confs.get(1)));
	   
	   SpiderContainer  container2=new SpiderContainer(contentspider,confs.get(1)).AddName("万方法律论文内容爬虫");
	   container2.AddgetStartUrlHandler(new Function<Spider,SpiderConf>(){
			@Override
			public List<String> apply(Spider t, SpiderConf e) {
				SpiderConf config=SpiderUtil.getSpiderConfByName(confs.get(1).getDependname(), confs);
				return SqlUtil.getTargetUrls(config);
			}});
      
		SpiderChain.startSpider(container2);
	    
		
    }
}
