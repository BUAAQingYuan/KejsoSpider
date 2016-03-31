package cn.kejso.Spider;

import java.util.ArrayList;
import java.util.List;

import cn.kejso.Template.ListAndContentTemplate;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

//流程化的爬虫链处理
public class SpiderChain {
	
	private List<Spider> spiderqueue=new ArrayList<Spider>();
	
	//添加爬虫节点
	public SpiderChain AddSpiderNode(Spider spider)
	{
		return this;
	}
	
	public SpiderChain AddSpiderNode(PageProcessor pageprocessor,ListAndContentTemplate template)
	{
		return this;
	}
	
	
}
