package cn.kejso.Tool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

import cn.kejso.Template.ListAndContentTemplate;
import cn.kejso.Template.ToolEntity.ContentConfig;
import cn.kejso.Template.ToolEntity.ListConfig;
import cn.kejso.Template.ToolEntity.Tag;

//读取配置文件，构造模板实体
public class TemplateConstructor {

	//读取List-Content模式的配置文件
	public  static  ListAndContentTemplate  getListAndContentTemplate(String configfile)
	{
		ListAndContentTemplate  template=new ListAndContentTemplate();
		XMLConfiguration  xml=null;
		try {
			xml=new XMLConfiguration(configfile);
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(xml.containsKey("TaskName"))
		{
			template.setTaskname(xml.getString("TaskName"));
		}
		
		if(xml.containsKey("Thread"))
		{
			template.setThreadnum(xml.getInt("Thread"));
		}
		
		template.setEnableproxy(xml.getBoolean("ProxyEnable"));
		
		//解析ListConfig
		//添加列表url
		String listurl=xml.getString("ListConfig.ListUrl");
		List<String>  starturls=new ArrayList<String>();
		boolean pageenable=xml.getBoolean("ListConfig.PageEnable");
		
		if(pageenable)
		{	
			int pagebegin=xml.getInt("ListConfig.PageStart");
			int pageend=xml.getInt("ListConfig.PageEnd");
			starturls=SpiderUtil.getListUrls(listurl, pagebegin, pageend);
		}else{
			starturls.add(listurl);
		}
		
		
		String listvalue=xml.getString("ListConfig.ListValue");
		
		String urlitem=xml.getString("ListConfig.UrlItem");
		String tablename=xml.getString("ListConfig.SqlTable");
		
		List tags=xml.configurationsAt("ListConfig.ListTag");
		List<Tag>  listtags=new ArrayList<Tag>();
		for(Iterator it = tags.iterator(); it.hasNext();)
		{
			HierarchicalConfiguration sub = (HierarchicalConfiguration) it.next();
			listtags.add(new Tag(sub.getString("TagName"),sub.getString("TagValue")));
		}
		
		template.setListconfig(new ListConfig(starturls,listvalue,urlitem,tablename,listtags));
		
		//解析ContentConfig
		String contenttable=xml.getString("ContentConfig.ContentTable");
		
		String item=xml.getString("ContentConfig.ContentItem");
		
		List contentitem=xml.configurationsAt("ContentConfig.ContentTag");
		List<Tag>  contenttags=new ArrayList<Tag>();
		for(Iterator it = contentitem.iterator(); it.hasNext();)
		{
			HierarchicalConfiguration sub = (HierarchicalConfiguration) it.next();
			contenttags.add(new Tag(sub.getString("TagName"),sub.getString("TagValue")));
		}
		
		String mark=xml.getString("ContentConfig.ContentList.Mark");
		String code=xml.getString("ContentConfig.ContentList.Code");
		String field=xml.getString("ContentConfig.ContentList.Field");
		
		template.setContentconfig(new ContentConfig(contenttags,contenttable,item,mark,code,SpiderUtil.getMapFields(field)));
		
		return template;
	}
	
	public static void main(String[] args)
	{
		TemplateConstructor.getListAndContentTemplate("configs\\wanfangpaper.xml");
	}
}
