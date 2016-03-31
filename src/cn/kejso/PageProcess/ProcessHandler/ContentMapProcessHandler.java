package cn.kejso.PageProcess.ProcessHandler;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import us.codecraft.webmagic.Page;
import cn.kejso.Config.Config;
import cn.kejso.Template.ListAndContentTemplate;
import cn.kejso.Template.ToolEntity.Tag;

//处理内容页
public class ContentMapProcessHandler {
		
	public<T>   T  processContentPage (Page page,ListAndContentTemplate template) 
	{
		
		//页面项
		List<Tag> items=template.getContentconfig().getTags();
		List<String> itemcontents=new ArrayList<String>();
		for(Tag one:items)
		{
			itemcontents.add(page.getHtml().xpath(one.getTagValue()).toString());
		}
		
		//map项
		//List<String> marks=page.getHtml().xpath(template.getContentconfig().getMark()).all();
		List<String> code=page.getHtml().xpath(template.getContentconfig().getCode()).all();
		
		List<String> attrs=template.getContentconfig().getField();

		//存储类
		String moban=template.getContentconfig().getItem();
		
		T url = null;
		try {
			Class clss = Class.forName(Config.StoredEntity+"."+moban);
			url=(T) clss.newInstance();
			
			//页面项属性ֵ
			for(int i=0;i<items.size();i++ )
			{
				BeanUtils.setProperty(url, items.get(i).getTagname(), itemcontents.get(i));
				
			}
			
			//map属性
			
			for(int i=0;i<code.size();i++ )
			{
				if(attrs.get(i)!=null)
				{
					BeanUtils.setProperty(url, attrs.get(i), code.get(i));
				}
			}
			
			
		} catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | InstantiationException e) {

			e.printStackTrace();
		}
		
		return url;
	}
}
