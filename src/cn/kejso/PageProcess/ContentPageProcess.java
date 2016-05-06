package cn.kejso.PageProcess;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.http.HttpHost;

import com.google.common.collect.Lists;

import cn.kejso.Config.Config;
import cn.kejso.PageProcess.ProcessHandler.ContentMapProcessHandler;
import cn.kejso.Template.ListAndContentTemplate;
import cn.kejso.Template.SpiderConf;
import cn.kejso.Template.ToolEntity.ContentConfig;
import cn.kejso.Tool.FileUtil;
import cn.kejso.Tool.SpiderUtil;
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
		
		List<String[]> proxys=Lists.newArrayList( new String[]{"101.200.179.38","3128"},new String[]{"121.207.6.126","3128"},new String[]{"111.161.126.108","80"},
												  new String[]{"202.100.167.137","80"},new String[]{"220.172.237.203","80"},new String[]{"202.100.167.145","80"},
												  new String[]{"222.176.112.10","80"},new String[]{"61.159.143.8","80"},new String[]{"218.75.0.83","3128"});
                
		//site = Site.me().setSleepTime(2000).setRetryTimes(5).setCycleRetryTimes(3).setTimeOut(60000).setUserAgent(Config.Spider_userAgent).setHttpProxy(new HttpHost(" 171.38.169.56", 8123));
		site = Site.me().setSleepTime(1000).setRetryTimes(5).setCycleRetryTimes(3).setTimeOut(60000).setUserAgent(Config.Spider_Google_userAgent1);
		//site.setHttpProxyPool(proxys);
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
