package cn.kejso.PageProcess.ProcessHandler;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import cn.kejso.Config.Config;
import cn.kejso.Template.AbstractTemplate;
import cn.kejso.Template.ListAndContentTemplate;
import cn.kejso.Template.SpiderConf;
import cn.kejso.Template.ToolEntity.ListConfig;
import cn.kejso.Template.ToolEntity.Tag;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;

public class UrlListProcessHandler {
	
	//处理url列表页面
	public   List<Map<String,String>>  processUrlPage (Page page,SpiderConf template)  
	{
		
		ListConfig config=(ListConfig)template.getConfig();
		
		//列表节点
		List<Selectable> nodes=page.getHtml().xpath(config.getListvalue()).nodes();
		
		List<Map<String,String>> entitys=new ArrayList<Map<String,String>>();
		
		//属性
		List<Tag> attrs=config.getTags();
		
		for(Selectable one:nodes)
		{
			List<String> contents=new ArrayList<String>();
			for(int i=0;i<attrs.size();i++ )
			{
				contents.add(one.xpath(attrs.get(i).getTagValue()).toString());
			}
			
			Map<String,String> entity=new HashMap<String,String>();
				
			for(int i=0;i<attrs.size();i++ )
			{
				entity.put(attrs.get(i).getTagname(), contents.get(i));
			}
				
			entitys.add(entity);
	
			
		}

		return entitys;
	}

	
	
}
