package cn.kejso.Spider;

import java.util.ArrayList;
import java.util.List;

import cn.kejso.PageProcess.ContentPageProcess;
import cn.kejso.PageProcess.ListPageProcess;
import cn.kejso.PageProcess.PreListPageProcess;
import cn.kejso.Pipeline.FilePipeline;
import cn.kejso.Pipeline.MysqlPipeline;
import cn.kejso.Spider.Control.SpiderContainer.Function;
import cn.kejso.Spider.Control.SqlCacheOnErrorListener;
import cn.kejso.Template.SpiderConf;
import cn.kejso.Template.ToolEntity.BaseConfig;
import cn.kejso.Template.ToolEntity.ContainStartUrls;
import cn.kejso.Template.ToolEntity.ContentConfig;
import cn.kejso.Template.ToolEntity.GlobalConfig;
import cn.kejso.Template.ToolEntity.ListConfig;
import cn.kejso.Template.ToolEntity.PreConfig;
import cn.kejso.Tool.SpiderUtil;
import cn.kejso.Tool.SqlUtil;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.processor.PageProcessor;

public class BuildSpider {

	private static List<SpiderConf> confs;
	private static GlobalConfig global;

	private BuildSpider() {

	}

	public static void setParameter(List<SpiderConf> confs, GlobalConfig global) {
		BuildSpider.confs = confs;
		BuildSpider.global = global;
	}

	public static Spider getSpider(SpiderConf conf, int retryTimes) {
		Spider spider = Spider.create(getPageProcessor(conf, retryTimes)).thread(global.getThreadnum())
				.addPipeline(new MysqlPipeline(conf));
		
		//添加额外的pipeline
		if(conf.getConfig().getStorefile()!=null)
		{
			spider.addPipeline(new FilePipeline(conf));
		}
		
		spider.setSpiderListeners(getSpiderListeners(conf));
		return spider;
	}

	//retry标志是否为爬虫重试error链接
	public static Function<Spider, SpiderConf> getStartUrlHandler(SpiderConf conf, boolean retry,boolean merge) {

		Function<Spider, SpiderConf> result = null;
		if(!merge)
		{
			if (retry) {
				result = readRetryUrlFromSql;
			} else if (conf.getDependname() == null) {
				result = readUrlFromConf;
			} else if (conf.getDependname() != null) {
				result = readUrlFromSql;
			}
		}else {
			if(retry)
			{
				result= readRetryUrlFromSql;
			}else{
				result= readRetryUrlFromMerge;
			}
			
		}
		

		return result;
	}

	
	
	
	private static List<SpiderListener> getSpiderListeners(SpiderConf conf) {
		List<SpiderListener> list = new ArrayList<SpiderListener>();
		
		/*
		if (conf.getConfig() instanceof ContentConfig) {
			list.add(new FileCacheOnErrorListener(Config.Spider_ErrorDir + global.getTaskname()));
		}
		*/

		list.add(new SqlCacheOnErrorListener(conf));
		
		return list;
	}

	public static PageProcessor getPageProcessor(SpiderConf conf, int retryTimes) {
		PageProcessor pp = null;

		BaseConfig pageProConf = conf.getConfig();
		if (pageProConf instanceof PreConfig) {
			pp = new PreListPageProcess(conf, retryTimes);
		} else if (pageProConf instanceof ListConfig) {
			pp = new ListPageProcess(conf, retryTimes);
		} else if (pageProConf instanceof ContentConfig) {
			pp = new ContentPageProcess(conf, retryTimes);
		}

		return pp;
	}

	// 简单地从配置文件中读取初始序列
	private static Function<Spider, SpiderConf> readUrlFromConf = new Function<Spider, SpiderConf>() {
		@Override
		public List<String> apply(Spider t, SpiderConf e) {
			ContainStartUrls config = (ContainStartUrls) e.getConfig();
			return config.getStartUrls();
		}
	};
	
	// 从_tmp表中读取retry urls
	private static Function<Spider, SpiderConf> readRetryUrlFromSql = new Function<Spider, SpiderConf>() {
		@Override
		public List<String> apply(Spider t, SpiderConf e) {
			return SqlUtil.getRetryUrls(e);
		}
	};
	
	// 新增加的url和_tmp表中url的集合
	private static Function<Spider, SpiderConf> readRetryUrlFromMerge = new Function<Spider, SpiderConf>() {
		@Override
		public List<String> apply(Spider t, SpiderConf e) {
			//e为爬虫链上的第一个爬虫
			if(e.getDependname()== null)
			{
				//readRetryUrlFromSql
				return SqlUtil.getRetryUrls(e);
			}else{
				SpiderConf pre = SpiderUtil.getSpiderConfByName(e.getDependname(), getConfs());
				List<String> mergeurls=new ArrayList<String>();
				// 区分依赖的是爬虫还是数据表
				if (pre==null) {
					//依赖的是数据表，应该获得该数据表新增的部分
					pre = SpiderUtil.getSpiderConfByTableName(e.getDependname(),getConfs());
					// 获得依赖数据表新增的部分
					mergeurls.addAll(SqlUtil.getListDeltaUrls(pre, e));
					// 获取_tmp表中的部分
					mergeurls.addAll(SqlUtil.getRetryUrls(e));
					return mergeurls;
				}else {
					// 依赖爬虫
					// 获得依赖数据表新增的部分
					mergeurls.addAll(SqlUtil.getListDeltaUrls(pre, e));
					// 获取_tmp表中的部分
					mergeurls.addAll(SqlUtil.getRetryUrls(e));
					return mergeurls;
				}
				
			}
			
		}
	};

	// 利用SQL进行断点恢复工作
	private static Function<Spider, SpiderConf> readUrlFromSql = new Function<Spider, SpiderConf>() {
		@Override
		public List<String> apply(Spider t, SpiderConf e) {
			
			//e为当前spiderconf配置，depend可以为前面的爬虫，也可以为数据表
			SpiderConf pre = SpiderUtil.getSpiderConfByName(e.getDependname(), getConfs());
			
			/*
			 * depend依赖的是数据表,构造一个spiderconf，将数据表信息传入
			 * 因为数据表没有指定从什么地方获取url，默认获取所有url字段的内容。将当前spiderconf 的recoverconfig禁用，使控制流从SqlUtil.getTargetUrls(pre)获取url。
			 */
			if (pre==null) {
				pre=new SpiderConf();
				BaseConfig base=new BaseConfig();
				base.setTablename(e.getDependname());
				base.setUnique(e.getDependField());
				pre.setConfig(base);
				
				e.getRecoverConfig().setEnable(false);
			}
			
			if (e.getRecoverConfig().getEnable()) {
				if (e.getRecoverConfig().isSimpleRecover()) {
					// 简单地从最后一条抓取成功的记录恢复
					int currentPos = SqlUtil.getBreakPoint(pre, e);
					return SqlUtil.getPartTargetUrls(pre, e, currentPos);
				} else if (e.getRecoverConfig().isDeltaRecover()) {
					// 对任务列表及完成列表作差集
					return SqlUtil.getDeltaUrls(pre, e);
				} else if (e.getRecoverConfig().isListDeltaRecover()) {
					// 只对父列表新增的部分进行抓取
					return SqlUtil.getListDeltaUrls(pre, e);
				} else
					return null;
			} else
				// 获得完整列表
				return SqlUtil.getTargetUrls(pre, e);
		}
	};

	private static List<SpiderConf> getConfs() {
		return confs;
	}

}
