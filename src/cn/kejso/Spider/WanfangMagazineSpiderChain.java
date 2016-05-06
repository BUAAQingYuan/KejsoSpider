package cn.kejso.Spider;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import cn.kejso.Config.Config;
import cn.kejso.PageProcess.ContentPageProcess;
import cn.kejso.PageProcess.ListPageProcess;
import cn.kejso.PageProcess.PreListPageProcess;
import cn.kejso.Pipeline.MysqlPipeline;
import cn.kejso.Spider.Control.SpiderContainer;
import cn.kejso.Spider.Control.SpiderContainer.Function;
import cn.kejso.Template.SpiderConf;
import cn.kejso.Template.ToolEntity.GlobalConfig;
import cn.kejso.Template.ToolEntity.ListConfig;
import cn.kejso.Template.ToolEntity.PreConfig;
import cn.kejso.Tool.SpiderUtil;
import cn.kejso.Tool.SqlUtil;
import cn.kejso.Tool.TemplateConstructor;

public class WanfangMagazineSpiderChain {
	
	public static void main(String[] args) {
		
		SpiderChain chain=new SpiderChain("万方期刊爬虫链");
		
		List<SpiderConf> confs=TemplateConstructor.getSpiderConf("configs\\WanfangMagazine.xml");
		GlobalConfig global=TemplateConstructor.getGlobalConf("configs\\WanfangMagazine.xml");
		
		/*
		Spider prespider=Spider.create(new PreListPageProcess(confs.get(0)))
				                   .thread(global.getThreadnum())
				                   .addPipeline(new MysqlPipeline(confs.get(0)));

		SpiderContainer container1=new SpiderContainer(prespider,confs.get(0)).AddName("万方期刊列表页预处理爬虫");
		container1.AddgetStartUrlHandler(new Function<Spider,SpiderConf>(){
			@Override
			public List<String> apply(Spider t, SpiderConf e) {
				PreConfig config=(PreConfig) confs.get(0).getConfig();
				List<String> starturls=new ArrayList<String>();
				starturls.add(config.getPreurl());
				return starturls;
			}
			});
	
		SpiderChain.startSpider(container1);
		*/
		
		/*
		Spider listspider=Spider.create(new ListPageProcess(confs.get(1)))
                				.thread(global.getThreadnum())
                				.addPipeline(new MysqlPipeline(confs.get(1)));

		SpiderContainer container2=new SpiderContainer(listspider,confs.get(1)).AddName("万方期刊列表页爬虫");
		container2.AddgetStartUrlHandler(new Function<Spider,SpiderConf>(){
			@Override
			public List<String> apply(Spider t, SpiderConf e) {
				SpiderConf config=SpiderUtil.getSpiderConfByName(confs.get(1).getDependname(), confs);
				return SqlUtil.getTargetUrls(config);
			}
		});
		
		SpiderChain.startSpider(container2);
		
		*/
		
		Spider contentspider=Spider.create(new ContentPageProcess(confs.get(2)))
				   //.scheduler(new FileCacheQueueScheduler(Config.Spider_CacheDir+global.getTaskname()))
				   .thread(global.getThreadnum())
				   .addPipeline(new MysqlPipeline(confs.get(2)))
				   .setExitWhenComplete(true);//没用
		
		SpiderContainer container3=new SpiderContainer(contentspider,confs.get(2)).AddName("万方期刊内容爬虫");
		container3.AddgetStartUrlHandler(new Function<Spider,SpiderConf>(){
			@Override
			public List<String> apply(Spider t, SpiderConf e) {
				SpiderConf config=SpiderUtil.getSpiderConfByName(confs.get(2).getDependname(), confs);
				return SqlUtil.getTargetUrls(config, e);
			}
		});
		
		SpiderChain.startSpider(container3);
		
	}
}
