package cn.kejso.PageProcess.ProcessHandler;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kejso.Config.Config;
import cn.kejso.Template.AbstractTemplate;
import cn.kejso.Template.ListAndContentTemplate;
import cn.kejso.Template.SpiderConf;
import cn.kejso.Template.ToolEntity.ListConfig;
import cn.kejso.Template.ToolEntity.Tag;
import cn.kejso.Tool.FileUtil;
import cn.kejso.Tool.SpiderUtil;
import cn.kejso.Tool.SqlUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;

public class UrlListProcessHandler {
	
	private static Logger logger = LoggerFactory.getLogger(UrlListProcessHandler.class);
	
	//处理url列表页面
	public   List<Map<String,String>>  processUrlPage (Page page,SpiderConf template)  
	{
		
		ListConfig config=(ListConfig)template.getConfig();
		
		List<Selectable> nodes=new ArrayList<Selectable>();
		
		//列表节点
		if(config.getListvalue()!=null)
		{
			nodes=page.getHtml().xpath(config.getListvalue()).nodes();
		}
		
		
		List<Map<String,String>> entitys=new ArrayList<Map<String,String>>();
		
		//属性
		List<Tag> attrs=config.getTags();
		
		//常量字段
		List<Tag> consts=config.getConsttags();
		
		//非列表字段
		List<Tag> others=config.getOthertags();
		
		for(Selectable one:nodes)
		{
			List<String> contents=new ArrayList<String>();
			for(int i=0;i<attrs.size();i++ )
			{
				contents.add(one.xpath(attrs.get(i).getTagValue()).toString());
			}
			
			Map<String,String> entity=new HashMap<String,String>();
			
			//属性
			for(int i=0;i<attrs.size();i++ )
			{
				entity.put(attrs.get(i).getTagname(), contents.get(i));
			}
			
			//常量字段
			for(int i=0;i<consts.size();i++)
			{
				entity.put(consts.get(i).getTagname(), consts.get(i).getTagValue());
			}
			
			//非列表字段
			for(int i=0;i<others.size();i++)
			{
				entity.put(others.get(i).getTagname(), page.getHtml().xpath(others.get(i).getTagValue()).toString());
			}			
				
			entitys.add(entity);
	
			
		}
		
		
		String cacheFile=Config.Spider_ErrorDir+config.getTablename();
		
		/*
		 * 判断抓取的动态字段是否为空，如果有常量字段要排除静态字段。如果没有抓取到内容，将其按照error url处理
		 */
		
		//排除字段
		List<String>  excludeFields=new ArrayList<String>();
		
		if(config.getConsttags().size()>0)
		{
			for(Tag one:config.getConsttags())
			{
				excludeFields.add(one.getTagname());
			}
		}
		
		for(Map<String,String> one:entitys)
		{
			if(SpiderUtil.ResultIsNull(one, excludeFields))
			{
				//FileUtil.PrintURL(cacheFile, page.getUrl().toString());
				SqlUtil.insertWrongItem(template, page.getRequest().getUrl());
				logger.info("fail to process page {} .", page.getRequest().getUrl());
				page.setSkip(true);
				break;
			}
		}
		

		return entitys;
	}

	
	
}
