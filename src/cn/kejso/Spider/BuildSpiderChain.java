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
import cn.kejso.Spider.Control.SpiderContainer.Function;
import cn.kejso.Template.SpiderConf;
import cn.kejso.Template.ToolEntity.BaseConfig;
import cn.kejso.Template.ToolEntity.ContentConfig;
import cn.kejso.Template.ToolEntity.GlobalConfig;
import cn.kejso.Template.ToolEntity.ListConfig;
import cn.kejso.Template.ToolEntity.PreConfig;
import cn.kejso.Template.ToolEntity.ContainStartUrls;
import cn.kejso.Tool.SpiderUtil;
import cn.kejso.Tool.SqlUtil;
import cn.kejso.Tool.TemplateConstructor;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.processor.PageProcessor;

public class BuildSpiderChain {

	private List<SpiderConf> confs;
	private GlobalConfig global;
	private SpiderChain chain;

	public BuildSpiderChain(String configPath) {

		chain = new SpiderChain();
		confs = TemplateConstructor.getSpiderConf(configPath);
		global = TemplateConstructor.getGlobalConf(configPath);

		for (SpiderConf conf : confs) {
			Spider spider = Spider.create(getPageProcessor(conf)).thread(global.getThreadnum())
					.addPipeline(new MysqlPipeline(conf));
			spider.setSpiderListeners(getSpiderListeners(conf));
			spider.setUUID(conf.getCname());
			//设置当前conf对应数据表的当前记录的id
			conf.setStartpoint(getStartpoint(conf));
			SpiderContainer container = new SpiderContainer(spider, conf);
			container.AddgetStartUrlHandler(getStartUrlHandler(conf));
			container.AddName(conf.getCname());
			chain.AddSpiderNode(container);
		}

	}
	
	private int getStartpoint(SpiderConf conf) {
		
		return SqlUtil.getCurrentPosition(conf);
	}

	private Function<Spider, SpiderConf> getStartUrlHandler(SpiderConf conf) {

		Function<Spider, SpiderConf> result = null;

		if (conf.getDependname() == null) {
			result = readUrlFromConf;
		} else if (conf.getDependname() != null) {
			result = readUrlFromSql;
		}

		return result;
	}

	private List<SpiderListener> getSpiderListeners(SpiderConf conf) {
		List<SpiderListener> list = new ArrayList<SpiderListener>();

		//只有内容页才处理error url 吗？
		if (conf.getConfig() instanceof ContentConfig) {
			//每个spider的error url存放到对应的数据表同名的cache文件中
			list.add(new FileCacheOnErrorListener(Config.Spider_ErrorDir + conf.getConfig().getTablename()));
		}

		return list;
	}

	private PageProcessor getPageProcessor(SpiderConf conf) {
		PageProcessor pp = null;

		BaseConfig pageProConf = conf.getConfig();
		if (pageProConf instanceof PreConfig) {
			pp = new PreListPageProcess(conf);
		} else if (pageProConf instanceof ListConfig) {
			pp = new ListPageProcess(conf);
		} else if (pageProConf instanceof ContentConfig) {
			pp = new ContentPageProcess(conf);
		}

		return pp;
	}

	public void startSpiders() {
		chain.startSpiders(true);
	}

	private Function<Spider, SpiderConf> readUrlFromConf = new Function<Spider, SpiderConf>() {
		@Override
		public List<String> apply(Spider t, SpiderConf e) {
			ContainStartUrls config = (ContainStartUrls) e.getConfig();
			return config.getStartUrls();
		}
	};

	// 考虑利用SQL断点恢复
	private Function<Spider, SpiderConf> readUrlFromSql = new Function<Spider, SpiderConf>() {
		@Override
		public List<String> apply(Spider t, SpiderConf e) {
			SpiderConf pre = SpiderUtil.getSpiderConfByName(e.getDependname(), getConfs());

			if (e.getRecoverConfig().getEnable()) {
				if (e.getRecoverConfig().isSimpleRecover()) {
					int currentPos = SqlUtil.getBreakPoint(pre, e);
					return SqlUtil.getPartTargetUrls(pre, currentPos);
				} else if (e.getRecoverConfig().isDeltaRecover()){
					return SqlUtil.getDeltaUrls(pre, e);
				} else if (e.getRecoverConfig().isListDeltaRecover()){
					return SqlUtil.getListDeltaUrls(pre);
				} else
					return null;

			} else
				return SqlUtil.getTargetUrls(pre);
		}
	};

	private List<SpiderConf> getConfs() {
		return confs;
	}

	public static void main(String[] args) {
		String path = "configs\\wanfangExpert.xml";

		BuildSpiderChain bsc = new BuildSpiderChain(path);
		bsc.startSpiders();
	}

}
