package cn.kejso.PageProcess.ProcessHandler;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import cn.kejso.Config.Config;
import cn.kejso.StoredEntity.ClassifyUrl;
import cn.kejso.Template.AbstractTemplate;
import cn.kejso.Template.ListAndContentTemplate;
import cn.kejso.Template.ToolEntity.Tag;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;

public class UrlListProcessHandler {
	
	//返回链接列表的获取接口
	public<T>   List<T>  processUrlPage (Page page,ListAndContentTemplate template)  
	{
		//列表
		List<Selectable> nodes=page.getHtml().xpath(template.getListconfig().getListvalue()).nodes();
		
		List<T>  paperurls=new ArrayList<T>();
		
		//模板类
		String urlitem=template.getListconfig().getUrlitem();
		
		//属性名
		List<Tag> attrs=template.getListconfig().getTags();
		
		for(Selectable one:nodes)
		{
			List<String> contents=new ArrayList<String>();
			for(int i=0;i<attrs.size();i++ )
			{
				contents.add(one.xpath(attrs.get(i).getTagValue()).toString());
			}
			
			
			try {
				Class clss = Class.forName(Config.StoredEntity+"."+urlitem);
				T url=(T) clss.newInstance();
				
				for(int i=0;i<attrs.size();i++ )
				{
					BeanUtils.setProperty(url, attrs.get(i).getTagname(), contents.get(i));
					
				}
				
				paperurls.add(url);
				
			} catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | InstantiationException e) {

				e.printStackTrace();
			}
		
			
		}

		return paperurls;
	}

	
	
}
