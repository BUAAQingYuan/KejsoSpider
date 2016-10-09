package cn.kejso.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import us.codecraft.webmagic.selector.Selectable;




import com.google.common.collect.Lists;

public class process2 implements PageProcessor {
	
	private Site site;
		
	final private  String  url="http://xueshu.baidu.com/s?wd=cnn&pn=";
	
	private boolean add=true;
	
	public void run() {
		String  s="http://xueshu.baidu.com/s?wd=cnn&pn=0";
		List<String> urls = new ArrayList<String>();
		urls.clear();
		urls.add(s);
		
		Spider.create(this).startUrls(urls).scheduler(new FileCacheQueueScheduler("SpiderScheduler/cache/"+"process2/")).run(); 
	}

	@Override
	public Site getSite() {
		
		site = Site.me().setSleepTime(1000).setRetryTimes(5).setCycleRetryTimes(3).setTimeOut(60000);
		site.setCharset("utf8");
		return site;
	}

	@Override
	public void process(Page page) {
		System.out.println(page.getUrl().toString());
		if(add)
		{
			for(int i=1;i<10;i++)
			{
				page.addTargetRequest(url+String.valueOf(i*10));
			}
		}
	}
	
	
	public static void main(String[] args) throws IOException
	{
		new process2().run();
	}

}
