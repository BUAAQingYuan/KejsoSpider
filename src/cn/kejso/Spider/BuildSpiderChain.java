package cn.kejso.Spider;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kejso.Config.Config;
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
			Spider spider = BuildSpider.getSpider(conf, 0);
			
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

	// 启动爬虫链
	public void startSpiders() {
		chain.startSpiders(true);
	}
	
	// 重试失败的url
	public void startSpidersForErrorUrls() {
		chain.startSpidersForErrorUrls(true);
	}
	
	
	public static void main(String[] args) throws IOException {
		
		if(args.length!=3)
		{
			System.out.println("BuildSpiderChain read KejsoSpider's config and crawl data,then write to the corrsponding database which jdbc-config representation .");
			System.out.println("Usage: java -jar BuildSpiderChain.jar  configfile  jdbc-config [fetch | retry].");
		}
		
		String config=args[0];
		String jdbcconfig=args[1];
		String command=args[2];
		
		Config.setJdbc_config(jdbcconfig);
		
		BuildSpiderChain bsc = new BuildSpiderChain(config);
		if(command.equals("fetch"))
		{
			bsc.startSpiders();
		}else if(command.equals("retry"))
		{
			bsc.startSpidersForErrorUrls();
		}
		
		
		
	}

}
