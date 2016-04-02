package cn.kejso.Pipeline;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kejso.Config.Config;
import cn.kejso.Template.ListAndContentTemplate;
import cn.kejso.Template.SpiderConf;
import cn.kejso.Template.ToolEntity.BaseConfig;
import cn.kejso.Template.ToolEntity.ContentConfig;
import cn.kejso.Template.ToolEntity.ListConfig;
import cn.kejso.Tool.SpiderUtil;
import cn.kejso.Tool.SqlUtil;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class MysqlPipeline implements Pipeline {
	
	private SqlSession session;
	
	private SpiderConf  template;
	
	private static Logger logger = LoggerFactory.getLogger(MysqlPipeline.class);
	
	//计数
	private int  count=0;

	public  MysqlPipeline(SpiderConf  template)
	{
		session=SpiderUtil.getSession();
		this.template=template;
		
		BaseConfig config=template.getConfig();
		//新建表
		String exist=Config.CreateTable_statement;
		Map<String,Object> createtable=new HashMap<String,Object>();
		createtable.put("tableName", config.getTablename());
		createtable.put("uniqueField",config.getUnique());
		createtable.put("fields", config.getFields());
		session.selectOne(exist, createtable);
		logger.info("create table if table {} not exists.",config.getTablename());
	}

	
	@Override
	public void process(ResultItems resultItems, Task task) {
		
		//判断存储类型
		if(resultItems.get(Config.PipeLine_Type).toString().equals(Config.PipeLine_TypeList))
		{
			InsertTypeList(resultItems,task);
		}
		else if(resultItems.get(Config.PipeLine_Type).toString().equals(Config.PipeLine_TypeOne))
		{
			InsertTypeOne(resultItems,task);
		}
		
		count++;
	}
	
	
	//list类
	private void  InsertTypeList(ResultItems resultItems, Task task)
	{
		ListConfig config=(ListConfig) template.getConfig();
		
		List<Map<String,String>> urls=resultItems.get(Config.PipeLine_Entity);
		
		String  statent=Config.Insert_statement;
		
		if(count%Config.Inertinfo_per_number==0)
			logger.info(SqlUtil.getMysqlinsertInfo(config.getTablename()),count);
		
		for(Map<String,String> one:urls)
		{
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("tablename",config.getTablename());
			map.put("fields", config.getFields());
			//首先将Map转换为List
			map.put("entitys",SqlUtil.MapToListByFields(one, config.getFields()));
			session.insert(statent, map);
		}
		
		
		session.commit();
	}
	
	//存储单个实体
	private void  InsertTypeOne(ResultItems resultItems, Task task) 
	{
		ContentConfig config=(ContentConfig) template.getConfig();
		
		Map<String,String> result=resultItems.get(Config.PipeLine_Entity);
		
		String  statent=Config.Insert_statement;
		
		if(count%Config.Inertinfo_per_number==0)
			logger.info(SqlUtil.getMysqlinsertInfo(config.getTablename()),count);	
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("tablename",config.getTablename());
		map.put("fields", config.getFields());
		map.put("entitys",SqlUtil.MapToListByFields(result,config.getFields()));
		session.insert(statent, map);
		session.commit();
	}
	
}
