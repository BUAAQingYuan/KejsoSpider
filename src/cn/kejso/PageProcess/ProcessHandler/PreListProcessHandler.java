package cn.kejso.PageProcess.ProcessHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.codecraft.webmagic.Page;
import cn.kejso.Template.SpiderConf;
import cn.kejso.Template.ToolEntity.PreConfig;
import cn.kejso.Template.ToolEntity.Tag;

//列表页url的前导页处理，获得列表url
public class PreListProcessHandler {
	
	public static  List<Map<String,String>>  processPreListPage (Page page,SpiderConf template) 
	{
		PreConfig config=(PreConfig) template.getConfig();
		
		List<String> urls=page.getHtml().xpath(config.getPrevalue()).all();
		
		//属性
		List<String> attrs=config.getFields();

		List<Map<String,String>> entitys=new ArrayList<Map<String,String>>();
		
		for(String one:urls)
		{
			Map<String,String> entity=new HashMap<String,String>();
			
			//只有一个字段
			entity.put(attrs.get(0), one);
			
			entitys.add(entity);
		}
		
		return entitys;
		
	}
}
