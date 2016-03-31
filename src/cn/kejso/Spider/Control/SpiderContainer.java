package cn.kejso.Spider.Control;

import java.util.List;

import cn.kejso.Template.AbstractTemplate;
import us.codecraft.webmagic.Spider;


//spider容器，用于构造SpiderChain
public class SpiderContainer {
	
	private  String name;
	
	private  Spider spider;
	
	private  AbstractTemplate  template;
	
	@FunctionalInterface  
	public interface Function<T,E> {  
	    public List<String> apply(T t,E e);  
	}  
	
	//获取starturls的func
	private  Function<Spider,AbstractTemplate>  getstarturlshandler=null;
	
	public Spider getSpider() {
		return spider;
	}

	public void setSpider(Spider spider) {
		this.spider = spider;
	}

	public AbstractTemplate getTemplate() {
		return template;
	}

	public void setTemplate(AbstractTemplate template) {
		this.template = template;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public  SpiderContainer(Spider spider,AbstractTemplate template)
	{
		this.spider=spider;
		this.template=template;
	}
	
	public  SpiderContainer(Spider spider,AbstractTemplate template,Function<Spider,AbstractTemplate> handler)
	{
		this.spider=spider;
		this.template=template;
		this.getstarturlshandler=handler;
	}
	
	public SpiderContainer AddgetStartUrlHandler(Function<Spider,AbstractTemplate> handler)
	{
		this.getstarturlshandler=handler;
		return this;
	}
	
	public SpiderContainer AddName(String name)
	{
		this.name=name;
		return this;
	}
	
	
	public List<String> getStartUrls()
	{
		return this.getstarturlshandler.apply(spider, template);
	}

	
	
}
