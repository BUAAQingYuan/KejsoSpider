package cn.kejso.Spider.Control;

import java.util.List;

import cn.kejso.Template.AbstractTemplate;
import cn.kejso.Template.SpiderConf;
import us.codecraft.webmagic.Spider;


//spider容器，用于构造SpiderChain
public class SpiderContainer {
	
	private  String name;
	
	private  Spider spider;
	
	private  SpiderConf template;
	
	private  int cycleTimes;
	
	//是否已经启动过，默认为false
	private  boolean start;
	
	
	@FunctionalInterface  
	public interface Function<T,E> {  
	    public List<String> apply(T t,E e);  
	}  
	
	//获取starturls的func
	private  Function<Spider,SpiderConf>  getstarturlshandler=null;
	
	public Spider getSpider() {
		return spider;
	}

	public void setSpider(Spider spider) {
		this.spider = spider;
	}

	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public SpiderConf getTemplate() {
		return template;
	}

	public void setTemplate(SpiderConf template) {
		this.template = template;
	}
	
	public  SpiderContainer(Spider spider,SpiderConf conf)
	{
		this.spider=spider;
		this.template=conf;
		this.start=false;
	}
	
	public  SpiderContainer(Spider spider,SpiderConf conf,Function<Spider,SpiderConf> handler)
	{
		this.spider=spider;
		this.template=conf;
		this.getstarturlshandler=handler;
	}
	
	public SpiderContainer AddgetStartUrlHandler(Function<Spider,SpiderConf> handler)
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
		return this.getstarturlshandler.apply(this.spider, this.template);
	}


	public boolean isStart() {
		return start;
	}
	
	public void setStart() {
		start = true;
	}
	
	public void setStart(boolean start) {
		this.start = start;
	}
	
	public void setCycleTimes(int value) {
		cycleTimes = value;
	}
	
	public int getCycleTimes() {
		return cycleTimes;
	}
	
	public void minusCycleTimes() {
		cycleTimes--;
	}
	
	public boolean continueCycle() {
		return (cycleTimes>0);
	}
	
}
