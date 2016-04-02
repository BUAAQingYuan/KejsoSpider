package cn.kejso.Tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kejso.Config.Config;
import cn.kejso.Template.ListAndContentTemplate;
import cn.kejso.Template.SpiderConf;
import cn.kejso.Template.ToolEntity.ListConfig;

public class SpiderUtil {
	
	private static Logger logger = LoggerFactory.getLogger(SpiderUtil.class);
	private static SqlSessionFactory sessionFactory = null;
	
	//读取mybatis配置文件
	static{
		Reader reader=null;
		try {
			reader = Resources.getResourceAsReader(Config.Mybatis_config);
		} catch (IOException e) {
			logger.error("读取mybatis配置文件出错!");
			e.printStackTrace();
		}
		sessionFactory=new SqlSessionFactoryBuilder().build(reader);
	}
	
	public static SqlSession getSession()
	{
		return sessionFactory.openSession();
	}
	
	
	
	//解析list 页面链接
	public static List<String>  getListUrls(String site,int start,int end)
	{
		List<String>  starturls=new ArrayList<String>();
		for(int i=start;i<=end;i++)
		{
			starturls.add(site.replaceAll("#", String.valueOf(i)));
		}
		return starturls;
	}
	
	//获得map 属性
	public static List<String>  getMapFields(String field)
	{
		List<String>  fields=new ArrayList<String>();
		
		String[] temp=field.split("#");
		for(String one:temp)
		{
			if(!one.equals(""))
				fields.add(one);
			else
				fields.add(null);
		}
		return fields;
	}
	
	
	//获得指定名字爬虫的SpiderConf
	public static SpiderConf getSpiderConfByName(String name,List<SpiderConf> spiders)
	{
		SpiderConf target=null;
		for(SpiderConf one:spiders)
		{
			if(one.getName().equals(name))
			{
				 target=one;
				 break;
			}
		}
		
		return target;
	}
	
	
	public static void main(String[] args) {
		//ListAndContentTemplate template=TemplateConstructor.getListAndContentTemplate("configs\\wanfangpaper.xml");
		//SpiderUtil.getTargetUrls(template);
		
		String field="library#titlenumber##department#level##timeline#promulgation#materail#contentclass";
		SpiderUtil.getMapFields(field);
	}
}
