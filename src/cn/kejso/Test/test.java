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
	
	
	//final private  String  url="http://d.wanfangdata.com.cn/Periodical/jfjlgdxxb201303012";
	
	final private  String  url="http://xueshu.baidu.com/s?wd=cnn&pn=";
	
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
		
		//System.out.println(page.getHtml().toString());
		/*
		String abstracts=page.getHtml().xpath("//div[@class='container']//p[@class='Organization_p']/text()").toString();
		System.out.println(abstracts);
		
		String abs=page.getHtml().xpath("//div[@class='container']//p[@class='font_5']/tidyText()").toString();
		System.out.println(abs);
		
		
		String paper=page.getHtml().regex("<span class=\"publishpaper\">发文：</span>(.*)<span class=\"cited\">").toString();
		String yinyong=page.getHtml().regex("<span class=\"cited\">被引：</span>(.*)<span class=\"hindex\">").toString();
		String hindex=page.getHtml().regex("<span class=\"hindex\">H指数：</span>(.*)<input").toString();
		System.out.println("######");
		System.out.println(paper);
		System.out.println(yinyong);
		System.out.println(hindex);
		System.out.println("#######");
		*/
		
		List<String> marks = page.getHtml().xpath("//div[@class='fixed-width baseinfo-feild']/div/span[@class='pre']/text()").all();
		for(String one:marks)
		{
			System.out.println(one);
		}
		
		List<String> codes = page.getHtml().xpath("//div[@class='fixed-width baseinfo-feild']/div/span[@class='text']/allText()").all();
		for(String one:codes)
		{
			System.out.println(one);
		}
		
		
	}
	
	
	public static void main(String[] args) throws IOException
	{
		new test().run();
	}

}
