package cn.kejso.Spider.Control;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kejso.Tool.FileUtil;
import cn.kejso.Tool.SpiderUtil;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.SpiderListener;


/*
 * Listener 为 对于每个页面的处理，发生error或success时的监听函数
 */


//对于发生错误的url，存储到一个文本cache中。
public class FileCacheOnErrorListener implements SpiderListener{
	
	private static Logger logger = LoggerFactory.getLogger(FileCacheOnErrorListener.class);
	
	private String  cacheFile;
	
	public FileCacheOnErrorListener(String cacheFile) 
	{
		this.cacheFile=cacheFile;
		File cache=new File(this.cacheFile);
		if(!cache.exists())
		{
			try {
				cache.createNewFile();
			} catch (IOException e) {
				logger.error("Can't Create CacheFile!");
				e.printStackTrace();
			}
		}
	}
	
	public String getCacheFile() {
		return cacheFile;
	}

	public void setCacheFile(String cacheFile) {
		this.cacheFile = cacheFile;
	}
	

	@Override
	public void onError(Request request) {
		FileUtil.PrintURL(cacheFile, request.getUrl());
	}

	
	@Override
	public void onSuccess(Request request) {
		
	}

	

}
