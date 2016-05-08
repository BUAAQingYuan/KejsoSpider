package cn.kejso.PageProcess;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import cn.kejso.Config.Config;
import cn.kejso.PageProcess.ProcessHandler.ContentMapProcessHandler;
import cn.kejso.Template.SpiderConf;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class ContentPageProcess implements PageProcessor {
	private Site site;
	private SpiderConf  template;
	
	public ContentPageProcess(SpiderConf template)
	{
		this.template=template;
	}
	
	@Override
	public Site getSite() {
		
		List<String[]> proxys=Lists.newArrayList( new String[]{"61.135.217.17","80"}, new String[]{"59.41.246.129", "808"});
		
                
		//site = Site.me().setSleepTime(2000).setRetryTimes(5).setCycleRetryTimes(3).setTimeOut(60000).setUserAgent(Config.Spider_userAgent).setHttpProxy(new HttpHost(" 171.38.169.56", 8123));
		site = Site.me().setSleepTime(1000).setRetryTimes(5).setCycleRetryTimes(3).setTimeOut(60000).setUserAgent(Config.Spider_Google_userAgent1);
//		site.setHttpProxyPool(proxys);
		site.setCharset("utf8");
		return site;
	}

	@Override
	public void process(Page page) {
		
		ContentMapProcessHandler  handler=new ContentMapProcessHandler();
		Map<String,String> result=handler.processContentPage(page, template);
		

		//入库
		page.putField(Config.PipeLine_Entity, result);
		page.putField(Config.PipeLine_Type, Config.PipeLine_TypeOne);

//		
//		for(String key:result.keySet())
//		{
//			System.out.print(result.get(key)+"  ");
//		}
//		
//		System.out.println();
		
		
	}

}
