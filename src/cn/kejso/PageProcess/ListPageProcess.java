package cn.kejso.PageProcess;

import java.util.List;
import java.util.Map;

import cn.kejso.Config.Config;
import cn.kejso.PageProcess.ProcessHandler.UrlListProcessHandler;
import cn.kejso.Template.ListAndContentTemplate;
import cn.kejso.Template.SpiderConf;
import cn.kejso.Tool.SpiderUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class ListPageProcess  implements PageProcessor {
	
	private Site site;
	private SpiderConf  template;
	
	
	public  ListPageProcess(SpiderConf template)
	{	
		this.template=template;
	}
	
	@Override
	public Site getSite() {
		site = Site.me().setSleepTime(10000).setRetryTimes(5).setCycleRetryTimes(3).setTimeOut(60000);
		site.setCharset("utf8").setUserAgent(Config.Spider_Default_userAgent);
		
		return site;
	}

	@Override
	public void process(Page page) {
		
		UrlListProcessHandler handler=new UrlListProcessHandler();
		List<Map<String,String>> paperurls= handler.processUrlPage(page,template);
		page.putField(Config.PipeLine_Entity, paperurls);
		page.putField(Config.PipeLine_Type, Config.PipeLine_TypeList);
		
		//随机设置UA
		if(Math.random()<Config.ChangeUA_probability)
		{
			site.setUserAgent(SpiderUtil.RandomUserAgent());
		}		

	}
	
}
