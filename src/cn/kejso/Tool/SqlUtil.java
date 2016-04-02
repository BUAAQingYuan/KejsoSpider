package cn.kejso.Tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import cn.kejso.Config.Config;
import cn.kejso.Template.SpiderConf;
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
			ListConfig config=(ListConfig) template.getConfig();
			String table=config.getTablename();
			String statement=Config.AllUrl_statement;
			Map<String,Object> map=new HashMap<String,Object>();
			
			map.put("tablename",table);
			
			//标记url为unique
			map.put("url", config.getUnique());
			
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

			int  position=(int) session.selectOne(state, map);
			return position;
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
				content.add(entity.get(one));
			}
			return content;
		}
}
