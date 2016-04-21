package cn.kejso.Tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import cn.kejso.Config.Config;
import cn.kejso.Template.SpiderConf;
import cn.kejso.Template.ToolEntity.BaseConfig;
import cn.kejso.Template.ToolEntity.ListConfig;

public class SqlUtil {
	
		
		//获取数据库插入提示
		public static String  getMysqlinsertInfo(String tablename)
		{
			String info=tablename+Config.Insert_info;
			
			return info;
		}
		
		//获得targeturl
		public static List<String> getTargetUrls(SpiderConf template)
		{
			SqlSession session=SpiderUtil.getSession();
			BaseConfig config=template.getConfig();
			String table=config.getTablename();
			String statement=Config.AllUrl_statement;
			Map<String,Object> map=new HashMap<String,Object>();
			
			map.put("tablename",table);
			
			//标记url为unique
			map.put("url", config.getUnique());
			
			List<String> urls=session.selectList(statement, map);
			return urls;
		}
		
		//获得targeturl
		public static List<String> getPartTargetUrls(SpiderConf template, int id)
		{
			SqlSession session=SpiderUtil.getSession();
			BaseConfig config=template.getConfig();
			String table=config.getTablename();
			String statement=Config.PartUrl_statement;
			Map<String,Object> map=new HashMap<String,Object>();
			
			map.put("tablename",table);
			map.put("id", id);
			
			//标记url为unique
			map.put("url", config.getUnique());
			
			List<String> urls=session.selectList(statement, map);
			return urls;
		}
		
		//获取两个表之间条目的差
		public static List<String> getDeltaUrls(SpiderConf pre, SpiderConf current) {
			
			SqlSession session=SpiderUtil.getSession();
			String statement=Config.TheDeltaField_statement;
			
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("targetField", pre.getConfig().getUnique());
			map.put("targetTable", pre.getConfig().getTablename());
			map.put("field", current.getRecoverConfig().getRef());
			map.put("sourceField", current.getRecoverConfig().getField());
			map.put("sourceTable", current.getConfig().getTablename());
			
			List<String> urls=session.selectList(statement, map);
			return urls;
		}
		
		public static List<String>	getListDeltaUrls(SpiderConf conf) {
			SqlSession session=SpiderUtil.getSession();
			
			String statement=Config.PartUrl_statement;
			
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("tablename", conf.getConfig().getTablename());
			map.put("url", conf.getConfig().getUnique());
			map.put("id", conf.getStartpoint());
			
			List<String> urls=session.selectList(statement, map);
			return urls;
		}

		//MysqlCache
		//获取一个数据表当前的位置
		public static int   getCurrentPosition(SpiderConf conf)
		{
			SqlSession session=SpiderUtil.getSession();
			
			String tablename=conf.getConfig().getTablename();
			
			String state=Config.TheLastId_statement;
			
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("tablename",tablename);

			Object position = session.selectOne(state, map);
			if (position == null)
				return 0;
			
			return (int)position;
		}
		
		//将当前位置写入cache
		public static void  PrintPositionToCache(SpiderConf conf)
		{
			int pos=getCurrentPosition(conf);
			String file=Config.Spider_SQLCacheDir+conf.getConfig().getTablename();
			FileUtil.PrintPosition(pos, file);
		}
		
		//获取之前的位置
		
		//将Map<String,String>按照fields的顺序转化为List
		public static List<String> MapToListByFields(Map<String,String> entity,List<String> fields)
		{
			List<String> content=new ArrayList<String>();
			for(String one:fields)
			{
				content.add(filterQuotation(entity.get(one)));
			}
			return content;
		}
		
		//过滤单双引号
		public static  String  filterQuotation(String field)
		{
			if(field!=null)
				return field.replaceAll("\"", "").replaceAll("\'", "");
			else
				return "";
		}
		
		public static int getBreakPoint(SpiderConf pre, SpiderConf current) {
			
			int  breakpoint = 0;
			
			SqlSession session=SpiderUtil.getSession();
			
			String preTable = pre.getConfig().getTablename();
			String currentTable = current.getConfig().getTablename();
			
			//得到内容页中最后一条数据
			String lastFieldState=Config.TheLastRecordField_statement;
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("tablename", currentTable);
			map.put("field", current.getRecoverConfig().getField());
			String  lastField = (String) session.selectOne(lastFieldState, map);
			
			//如果这是张新表
			if (lastField == null)
				return breakpoint;
			
			//得到这条数据所对应的URL列表中的记录的ID
			String certainIdState=Config.TheCertainId_statement;
			map.clear();
			map.put("tablename", preTable);
			map.put("field", current.getRecoverConfig().getRef());
			map.put("fieldvalue", lastField);
			breakpoint = (int) session.selectOne(certainIdState, map);
			
			return breakpoint;
		}
}
