package cn.kejso.Spider;

import java.util.List;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import cn.kejso.Config.Config;
import cn.kejso.PageProcess.ContentPageProcess;
import cn.kejso.PageProcess.ListPageProcess;
import cn.kejso.Pipeline.MysqlPipeline;
import cn.kejso.Spider.Control.SpiderContainer;
import cn.kejso.Spider.Control.SpiderContainer.Function;
import cn.kejso.Template.AbstractTemplate;
import cn.kejso.Template.ListAndContentTemplate;
import cn.kejso.Tool.SpiderUtil;
import cn.kejso.Tool.TemplateConstructor;

public class RunSpiderChainExample {
	
	public static void main(String[] args) {
		
		SpiderChain chain=new SpiderChain();
		
		ListAndContentTemplate template=TemplateConstructor.getListAndContentTemplate("configs\\wanfangpaper.xml");
		
		
        Spider spiderurllist=Spider.create(new ListPageProcess(template))
        						   .thread(template.getThreadnum())
        						   .addPipeline(new MysqlPipeline(template));
        
        SpiderContainer container1=new SpiderContainer(spiderurllist,template).AddName("万方法律论文Url爬虫");
        container1.AddgetStartUrlHandler(new Function<Spider,AbstractTemplate>(){
			@Override
			public List<String> apply(Spider t, AbstractTemplate e) {
				return template.getListconfig().getStarturls();
			}});
        
        //后面的spider依赖于前面的spider，则后一个spider的初始化的urls添加操作要等到前面的spider执行完生成相应的数据。
        
        Spider spidercontent=Spider.create(new ContentPageProcess(template))
        						   .scheduler(new FileCacheQueueScheduler(Config.Spider_CacheDir+template.getTaskname()))
        						   .thread(template.getThreadnum())
        						   .addPipeline(new MysqlPipeline(template))
        						   .setExitWhenComplete(true);//没用
        
        SpiderContainer container2=new SpiderContainer(spidercontent,template).AddName("万方法律论文内容爬虫");
        container2.AddgetStartUrlHandler(new Function<Spider,AbstractTemplate>(){
			@Override
			public List<String> apply(Spider t, AbstractTemplate e) {
				return SpiderUtil.getTargetUrls(template);
			}});
        
        chain.AddSpiderNode(container1);
        chain.AddSpiderNode(container2);
        
        chain.startSpiders(true);
        											
       
	}

}
