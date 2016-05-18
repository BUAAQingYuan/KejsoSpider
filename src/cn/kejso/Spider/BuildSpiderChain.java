package cn.kejso.Spider;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kejso.Spider.Control.SpiderContainer;
import cn.kejso.Template.SpiderConf;
import cn.kejso.Template.ToolEntity.GlobalConfig;
import cn.kejso.Tool.SqlUtil;
import cn.kejso.Tool.TemplateConstructor;
import us.codecraft.webmagic.Spider;

public class BuildSpiderChain {

	private static Logger logger = LoggerFactory.getLogger(BuildSpiderChain.class);
	
	private List<SpiderConf> confs;
	private GlobalConfig global;
	private SpiderChain chain;

	public BuildSpiderChain(String configPath) {

		
		confs = TemplateConstructor.getSpiderConf(configPath);
		global = TemplateConstructor.getGlobalConf(configPath);
		chain = new SpiderChain(global);
		
		BuildSpider.setParameter(confs, global);
		
		for (SpiderConf conf : confs) {
			Spider spider = BuildSpider.getSpider(conf);
			
			conf.setStartpoint(getStartpoint(conf));
			
			SpiderContainer container = new SpiderContainer(spider, conf);
			container.AddName(conf.getCname());
			container.setCycleTimes(global.getCycleTimes());
			
			chain.AddSpiderNode(container);
		}

	}
	
	private int getStartpoint(SpiderConf conf) {
		
		return SqlUtil.getCurrentPosition(conf);
	}

	public void startSpiders() {
		chain.startSpiders(true);
	}
	
	public static void main(String[] args) {

		
		String path = "configs\\wanfangLaw.xml";
		/*
		String path=args[0];
		if(path==null)
		{
			System.out.println("未输入配置文件!");
			return ;
		}
		*/
		BuildSpiderChain bsc = new BuildSpiderChain(path);
		bsc.startSpiders();
	}

}
