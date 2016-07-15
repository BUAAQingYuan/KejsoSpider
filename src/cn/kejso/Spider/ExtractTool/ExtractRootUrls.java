package cn.kejso.Spider.ExtractTool;

import java.util.List;

import us.codecraft.webmagic.Spider;
import cn.kejso.PageProcess.ListPageProcess;
import cn.kejso.Pipeline.FilePipeline;
import cn.kejso.Pipeline.MysqlPipeline;
import cn.kejso.Spider.BuildSpider;
import cn.kejso.Spider.SpiderChain;
import cn.kejso.Spider.Control.CustomHttpClientDownloader;
import cn.kejso.Spider.Control.SpiderContainer;
import cn.kejso.Template.SpiderConf;
import cn.kejso.Template.ToolEntity.GlobalConfig;
import cn.kejso.Tool.TemplateConstructor;


public class ExtractRootUrls {
	
	//万方会议抽取初始urls
	public static void ExtractUrlAndPagenum(String configfile)
	{
		SpiderConf conf=TemplateConstructor.getSpiderConf(configfile).get(0);
		GlobalConfig global=TemplateConstructor.getGlobalConf(configfile);

        Spider spider=Spider.create(BuildSpider.getPageProcessor(conf,0))
        						   .thread(global.getThreadnum())
        						   .addPipeline(new FilePipeline(conf));
        
        SpiderContainer container=new SpiderContainer(spider,conf).AddName(conf.getCname());
        container.AddgetStartUrlHandler(BuildSpider.getStartUrlHandler(container.getTemplate(), false));
        spider.startUrls(container.getStartUrls()).setDownloader(new CustomHttpClientDownloader());
        
        SpiderChain.startSpider(container);
	}
	
	//从指定的数据表中
	
	public static void main(String[] args){
		ExtractRootUrls.ExtractUrlAndPagenum("configs\\filetest.xml");
	}
}
