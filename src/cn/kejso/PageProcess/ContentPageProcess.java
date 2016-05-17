package cn.kejso.PageProcess;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import cn.kejso.Config.Config;
import cn.kejso.PageProcess.ProcessHandler.ContentMapProcessHandler;
import cn.kejso.Template.SpiderConf;
import cn.kejso.Template.ToolEntity.GlobalConfig;
import cn.kejso.Tool.ProxyUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class ContentPageProcess implements PageProcessor {
	private Site site;
	private SpiderConf template;
	private int retryTimes;
	
	public ContentPageProcess(SpiderConf template, int retryTimes) {
		this.template = template;
		this.retryTimes = retryTimes;
	}

	@Override
	public Site getSite() {

		// site =
		// Site.me().setSleepTime(2000).setRetryTimes(5).setCycleRetryTimes(3).setTimeOut(60000).setUserAgent(Config.Spider_userAgent).setHttpProxy(new
		// HttpHost(" 171.38.169.56", 8123));
		site = Site.me().setSleepTime(GlobalConfig.getSleeptime()).setRetryTimes(5).setCycleRetryTimes(3).setTimeOut(60000)
				.setUserAgent(Config.Spider_Google_userAgent1);
		
		if (GlobalConfig.isMoresleeptime()) {
			site.setSleepTime((int) (GlobalConfig.getSleeptime()*Math.pow(2, retryTimes)));
		}

		if (GlobalConfig.isEnableproxy()) {
			List<String[]> proxys = ProxyUtil.getMimvpProxyList();
			site.setHttpProxyPool(proxys);
		}
		
		site.setCharset("utf8");
		return site;
	}

	@Override
	public void process(Page page) {

		ContentMapProcessHandler handler = new ContentMapProcessHandler();
		Map<String, String> result = handler.processContentPage(page, template);

		// 入库
		page.putField(Config.PipeLine_Entity, result);
		page.putField(Config.PipeLine_Type, Config.PipeLine_TypeOne);

		//
		// for(String key:result.keySet())
		// {
		// System.out.print(result.get(key)+" ");
		// }
		//
		// System.out.println();

	}

}
