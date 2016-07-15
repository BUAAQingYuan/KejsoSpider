package cn.kejso.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;



import com.google.common.collect.Lists;

public class test implements PageProcessor {
	
	private Site site;
	
	private int   number=3053;
	
	private int   max=31351;
	
	final private  String  url="http://dl.acm.org/citation.cfm?doid=1273496.1273564";
	
	
	
	public void run() {
		String  s=url;
		List<String> url = new ArrayList<String>();
		url.clear();
		url.add(s);
		Spider.create(this).startUrls(url).run(); 
	}

	@Override
	public Site getSite() {
		
		site = Site.me().setSleepTime(1000).setRetryTimes(5).setCycleRetryTimes(3).setTimeOut(60000);
		site.setCharset("utf8");
		return site;
	}

	@Override
	public void process(Page page) {
		
		System.out.println(page.getHtml().toString());
		
		String abstracts=page.getHtml().xpath("//div[@id='tab-body9']/div[@id='abstract']/div/div/p/text()").toString();
		System.out.println(abstracts);
		
		
		
	}
	
	
	public static void main(String[] args) throws IOException
	{
		new test().run();
	}

}
