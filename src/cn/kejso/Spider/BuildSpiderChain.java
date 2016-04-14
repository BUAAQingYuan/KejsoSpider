package cn.kejso.Spider;

import java.util.ArrayList;
import java.util.List;

import cn.kejso.Config.Config;
import cn.kejso.PageProcess.ContentPageProcess;
import cn.kejso.PageProcess.ListPageProcess;
import cn.kejso.PageProcess.PreListPageProcess;
import cn.kejso.Pipeline.MysqlPipeline;
import cn.kejso.Spider.Control.FileCacheOnErrorListener;
import cn.kejso.Spider.Control.SpiderContainer;
import cn.kejso.Template.SpiderConf;
import cn.kejso.Template.ToolEntity.BaseConfig;
import cn.kejso.Template.ToolEntity.ContentConfig;
import cn.kejso.Template.ToolEntity.GlobalConfig;
import cn.kejso.Template.ToolEntity.ListConfig;
import cn.kejso.Template.ToolEntity.PreConfig;
import cn.kejso.Tool.TemplateConstructor;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.processor.PageProcessor;

public class BuildSpiderChain {
	
	private List<SpiderConf> confs;
	private GlobalConfig global;
	private SpiderChain chain;
	
	public BuildSpiderChain(String configPath) {
		
		chain=new SpiderChain();
		confs=TemplateConstructor.getSpiderConf(configPath);
		global=TemplateConstructor.getGlobalConf(configPath);
		
		for (SpiderConf conf : confs) {
			Spider spider = Spider.create(getPageProcessor(conf))
									.thread(global.getThreadnum())
									.addPipeline(new MysqlPipeline(conf));
			spider.setSpiderListeners(getSpiderListeners(conf));
			SpiderContainer container = new SpiderContainer(spider, conf);
//			container.AddgetStartUrlHandler(handler);
			chain.AddSpiderNode(container);
		}
	
	}
	
	private List<SpiderListener> getSpiderListeners(SpiderConf conf) {
		List <SpiderListener> list = new ArrayList<SpiderListener>();
		
		if (conf.getConfig() instanceof ContentConfig) {
			list.add(new FileCacheOnErrorListener(Config.Spider_ErrorDir+global.getTaskname()));
		}
		
		return list;
	}

	private PageProcessor getPageProcessor(SpiderConf conf) {
		PageProcessor pp = null;
		
		BaseConfig pageProConf = conf.getConfig();
		if (pageProConf instanceof PreConfig) {
			pp = new PreListPageProcess(conf);
		}
		else if (pageProConf instanceof ListConfig) {
			pp = new ListPageProcess(conf);
		}
		else if (pageProConf instanceof ContentConfig) {
			pp = new ContentPageProcess(conf);
		}
		
		return pp;
	}
	
	public void startSpiders() {
		chain.startSpiders(true);
	}

	public static void main(String[] args) {
		String path = null;
		
		BuildSpiderChain bsc = new BuildSpiderChain(path);
		bsc.startSpiders();
	}
	
}
