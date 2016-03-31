package cn.kejso.Pipeline;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kejso.Config.Config;
import cn.kejso.StoredEntity.ClassifyUrl;
import cn.kejso.Template.ListAndContentTemplate;
import cn.kejso.Tool.SpiderUtil;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class MysqlPipeline implements Pipeline {
	
	private SqlSession session;
	
	private ListAndContentTemplate  template;
	
	private static Logger logger = LoggerFactory.getLogger(MysqlPipeline.class);
	
	//计数
	private int  count=0;

	public  MysqlPipeline(ListAndContentTemplate  template)
	{
		session=SpiderUtil.getSession();
		this.template=template;
	}

	
	@Override
	public void process(ResultItems resultItems, Task task) {
		
		long start = System.currentTimeMillis();
		logger.info("mysqlpipeline start ...");
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
		logger.info("mysqlpipeline end .Cost time:{}��",(System.currentTimeMillis() - start) / 1000.0);
	}
	
	
	//list类
	private void  InsertTypeList(ResultItems resultItems, Task task)
	{
		List<Object> urls=resultItems.get(Config.PipeLine_Entity);
		
		String moban=template.getListconfig().getUrlitem();
		
		String  statent=SpiderUtil.getInsertStatement(moban);
		
		logger.info(SpiderUtil.getMysqlinsertInfo(moban),count);
		
		//新建表
		/*
		String exist=Config.ClassifyUrl_createTable;
		Map<String,Object> createtable=new HashMap<String,Object>();
		createtable.put("tableName", template.getListconfig().getTablename());
		session.selectOne(exist, createtable);
		logger.info("create table "+template.getListconfig().getTablename()+"...");
		*/
		
		for(Object one:urls)
		{
			//session.insert(statent,one);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("tablename",template.getListconfig().getTablename());
			map.put("entity",one);
			session.insert(statent, map);
		}
		
		
		session.commit();
	}
	
	//存储单个实体
	private void  InsertTypeOne(ResultItems resultItems, Task task) 
	{
		Object result=resultItems.get(Config.PipeLine_Entity);
		
		String moban=template.getContentconfig().getItem();
		
		String  statent=SpiderUtil.getInsertStatement(moban);
		
		logger.info(SpiderUtil.getMysqlinsertInfo(moban),count);	
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("tablename",template.getContentconfig().getTablename());
		map.put("entity",result);
		session.insert(statent, map);
		session.commit();
	}
	
}
