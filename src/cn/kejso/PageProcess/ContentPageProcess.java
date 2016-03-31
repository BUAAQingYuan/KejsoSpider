package cn.kejso.PageProcess;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import cn.kejso.Config.Config;
import cn.kejso.PageProcess.ProcessHandler.ContentMapProcessHandler;
import cn.kejso.Template.ListAndContentTemplate;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class ContentPageProcess implements PageProcessor {
	private Site site;
	private ListAndContentTemplate  template;
	
	public ContentPageProcess(ListAndContentTemplate template)
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
		
		ContentMapProcessHandler  handler=new ContentMapProcessHandler();
		Object result=handler.processContentPage(page, template);
		
		page.putField(Config.PipeLine_Entity, result);
		page.putField(Config.PipeLine_Type, Config.PipeLine_TypeOne);
	}

}
