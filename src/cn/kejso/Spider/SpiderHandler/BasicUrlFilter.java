package cn.kejso.Spider.SpiderHandler;

import java.util.List;

//url过滤器
public interface BasicUrlFilter {

	public List<String>  filter(List<String> urls);
	
}
