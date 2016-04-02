package cn.kejso.PageProcess.ProcessHandler;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import us.codecraft.webmagic.Page;
import cn.kejso.Config.Config;
import cn.kejso.Template.ListAndContentTemplate;
import cn.kejso.Template.SpiderConf;
import cn.kejso.Template.ToolEntity.ContentConfig;
import cn.kejso.Template.ToolEntity.Tag;

//处理内容页
public class ContentMapProcessHandler {
		
	public   Map<String,String>  processContentPage (Page page,SpiderConf template) 
	{
		
		ContentConfig config=(ContentConfig) template.getConfig();
		
		//页面项
		List<Tag> items=config.getTags();
		List<String> itemcontents=new ArrayList<String>();
		for(Tag one:items)
		{
			itemcontents.add(page.getHtml().xpath(one.getTagValue()).toString());
		}
		
		//map项
		//List<String> marks=page.getHtml().xpath(template.getContentconfig().getMark()).all();
		List<String> code=page.getHtml().xpath(config.getCode()).all();
		
		List<String> attrs=config.getField();
		
		Map<String,String> result=new HashMap<String,String>();
			
		//页面项
		for(int i=0;i<items.size();i++ )
		{
			result.put(items.get(i).getTagname(), itemcontents.get(i));	
		}
			
		//map属性
		for(int i=0;i<code.size();i++ )
		{
			if(attrs.get(i)!=null)
			{
				result.put(attrs.get(i), code.get(i));
			}
		}
			
		return result;
	}
}
