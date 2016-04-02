package cn.kejso.Spider;

import java.util.List;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import us.codecraft.webmagic.scheduler.DuplicateRemovedScheduler;
import cn.kejso.Config.Config;
import cn.kejso.PageProcess.ContentPageProcess;
import cn.kejso.PageProcess.ListPageProcess;
import cn.kejso.Pipeline.MysqlPipeline;
import cn.kejso.Spider.Control.SpiderContainer;
import cn.kejso.Spider.Control.SpiderContainer.Function;
import cn.kejso.Template.AbstractTemplate;
import cn.kejso.Template.ListAndContentTemplate;
import cn.kejso.Template.SpiderConf;
import cn.kejso.Template.ToolEntity.GlobalConfig;
import cn.kejso.Template.ToolEntity.ListConfig;
import cn.kejso.Tool.SpiderUtil;
import cn.kejso.Tool.SqlUtil;
import cn.kejso.Tool.TemplateConstructor;

public class WanfangPaperSpiderChain {
	
	public static void main(String[] args) {
		
		SpiderChain chain=new SpiderChain("万方法律爬虫链");
		
		List<SpiderConf> confs=TemplateConstructor.getSpiderConf("configs\\wanfangpaper.xml");
		GlobalConfig global=TemplateConstructor.getGlobalConf("configs\\wanfangpaper.xml");
		
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
        
        //后面的spider依赖于前面的spider，则后一个spider的初始化的urls添加操作要等到前面的spider执行完生成相应的数据。
        
        Spider spidercontent=Spider.create(new ContentPageProcess(confs.get(1)))
        						   .scheduler(new FileCacheQueueScheduler(Config.Spider_CacheDir+global.getTaskname()))
        						   .thread(global.getThreadnum())
        						   .addPipeline(new MysqlPipeline(confs.get(1)))
        						   .setExitWhenComplete(true);//没用
        
        SpiderContainer container2=new SpiderContainer(spidercontent,confs.get(1)).AddName("万方法律论文内容爬虫");
        container2.AddgetStartUrlHandler(new Function<Spider,SpiderConf>(){
			@Override
			public List<String> apply(Spider t, SpiderConf e) {
				SpiderConf config=SpiderUtil.getSpiderConfByName(confs.get(1).getDependname(), confs);
				return SqlUtil.getTargetUrls(config);
			}});
        
        chain.AddSpiderNode(container1);
        chain.AddSpiderNode(container2);
        
        chain.startSpiders(true);
        											
       
	}

}
