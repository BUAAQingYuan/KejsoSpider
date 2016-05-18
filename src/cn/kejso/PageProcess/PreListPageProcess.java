package cn.kejso.PageProcess;

import java.util.List;
import java.util.Map;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import cn.kejso.Config.Config;
import cn.kejso.PageProcess.ProcessHandler.PreListProcessHandler;
import cn.kejso.PageProcess.ProcessHandler.UrlListProcessHandler;
import cn.kejso.Template.SpiderConf;
import cn.kejso.Tool.SpiderUtil;

public class PreListPageProcess implements PageProcessor{
	
	private Site site;
	private SpiderConf  template;
	
	public PreListPageProcess(SpiderConf template)
	{
		this.template=template;
	}
	
	@Override
	public Site getSite() {
		site = Site.me().setSleepTime(1000).setRetryTimes(5).setCycleRetryTimes(3).setTimeOut(60000);
		site.setCharset("utf8");
		return site;
	}

	@Override
	public void process(Page page) {
		
		List<Map<String,String>> preurls=PreListProcessHandler.processPreListPage(page, template);
		
		page.putField(Config.PipeLine_Entity, preurls);
		page.putField(Config.PipeLine_Type, Config.PipeLine_TypeList);
		
		//随机设置UA
		if(Math.random()<Config.ChangeUA_probability)
		{
			site.setUserAgent(SpiderUtil.RandomUserAgent());
		}
	}
}
