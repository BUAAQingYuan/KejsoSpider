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
		public static List<String> getTargetUrls(SpiderConf template, SpiderConf current)
		{
			SqlSession session = SpiderUtil.getSession();
			BaseConfig config = template.getConfig();
			String table = config.getTablename();
			String statement = Config.AllUrl_statement;
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("tablename", table);

			// 标记url为unique
			map.put("url", current.getDependField());

			List<String> urls = session.selectList(statement, map);
			
			session.close();
			return urls;
		}
		
		// 获得Retryurl
		public static List<String> getRetryUrls(SpiderConf template) {
			SqlSession session = SpiderUtil.getSession();
			BaseConfig config = template.getTempTableConfig();
			// 得到用于存放失败条目的表
			String table = config.getTablename();
			String statement = Config.AllUrl_statement;
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("tablename", table);

			// 标记url为unique
			map.put("url", config.getUnique());

			List<String> urls = session.selectList(statement, map);
			
			session.close();
			return urls;
		}
		
		//获得targeturl
		public static List<String> getPartTargetUrls(SpiderConf template, SpiderConf current, int id)
		{
			SqlSession session = SpiderUtil.getSession();
			BaseConfig config = template.getConfig();
			String table = config.getTablename();
			String statement = Config.PartUrl_statement;
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("tablename", table);
			map.put("id", id);

			// 标记url为unique
			map.put("url", current.getDependField());

			List<String> urls = session.selectList(statement, map);
			
			session.close();
			return urls;
		}
		
		//获取两个表之间条目的差
		public static List<String> getDeltaUrls(SpiderConf pre, SpiderConf current) {
			
			SqlSession session = SpiderUtil.getSession();
			String statement = Config.TheDeltaField_statement;

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("targetField", current.getDependField());
			map.put("targetTable", pre.getConfig().getTablename());
			map.put("field", current.getRecoverConfig().getRef());
			map.put("sourceField", current.getRecoverConfig().getField());
			map.put("sourceTable", current.getConfig().getTablename());

			List<String> urls = session.selectList(statement, map);
			session.close();
			return urls;
		}
		
		
		//获取前一个所依赖的表新增的url
		public static List<String>	getListDeltaUrls(SpiderConf conf, SpiderConf current) {
			SqlSession session = SpiderUtil.getSession();

			String statement = Config.PartUrl_statement;
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tablename", conf.getConfig().getTablename());
			map.put("url", current.getDependField());
			map.put("id", conf.getStartpoint());

			List<String> urls = session.selectList(statement, map);
			
			session.close();
			return urls;
		}
		

		//MysqlCache
		//获取一个数据表当前的位置
		public static int   getCurrentPosition(SpiderConf conf)
		{
			SqlSession session = SpiderUtil.getSession();

			String tablename = conf.getConfig().getTablename();

			String state = Config.TheLastId_statement;

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tablename", tablename);

			Object position = session.selectOne(state, map);
			
			session.close();
			if (position == null)
				return 0;

			return (int) position;
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
		
		
		
		//得到url表中的更新点
		public static int getBreakPoint(SpiderConf pre, SpiderConf current) {

			int breakpoint = 0;

			SqlSession session = SpiderUtil.getSession();

			String preTable = pre.getConfig().getTablename();
			String currentTable = current.getConfig().getTablename();

			// 得到内容页中最后一条数据
			String lastFieldState = Config.TheLastRecordField_statement;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tablename", currentTable);
			map.put("field", current.getRecoverConfig().getField());
			String lastField = (String) session.selectOne(lastFieldState, map);

			// 如果这是张新表
			if (lastField == null)
				return breakpoint;

			// 得到这条数据所对应的URL列表中的记录的ID
			String certainIdState = Config.TheCertainId_statement;
			map.clear();
			map.put("tablename", preTable);
			map.put("field", current.getRecoverConfig().getRef());
			map.put("fieldvalue", lastField);
			breakpoint = (int) session.selectOne(certainIdState, map);
			
			session.close();

			return breakpoint;
		}
		
		//清空临时表数据
		public static void cleanTempTable(SpiderConf conf) {
			SqlSession session = SpiderUtil.getSession();

			String cleanTable = Config.TruncateTable_statement;

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tablename", conf.getTempTableConfig().getTablename());

			session.selectOne(cleanTable, map);
			
			session.close();
		}

		//删除临时表
		public static void deleteTempTable(SpiderConf conf) {
			SqlSession session = SpiderUtil.getSession();

			String deleteTable = Config.DropTable_statement;

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tablename", conf.getTempTableConfig().getTablename());

			session.selectOne(deleteTable, map);
			
			session.close();
		}

		//是否存在失败等待重试的页面
		public static boolean hasRetryItem(SpiderConf conf) {
			SqlSession session = SpiderUtil.getSession();

			String retry = Config.TheRecordNumber_statement;
			Map<String, Object> map = new HashMap<String, Object>();
			
		
			map.put("tablename", conf.getTempTableConfig().getTablename());

			Object num = session.selectOne(retry, map);
			
			session.close();

			if (num != null && (int) num > 0) {
				return true;
			}
			return false;
		}
		
		//插入抓取错误项
		public static void insertWrongItem(SpiderConf conf, String value) {
			
			SqlSession session = SpiderUtil.getSession();
			String  statent=Config.Insert_statement;
			
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("tablename", conf.getTempTableConfig().getTablename());
			map.put("fields", conf.getTempTableConfig().getFields());
			List<String> url = new ArrayList<String>();
			url.add(value);
			map.put("entitys", url);
			
			session.insert(statent, map);
			session.commit();
			
			session.close();
		}
		
		public static void main(String[] args){
			
			SqlSession session=SpiderUtil.getSession();
			String state="SqlMapper.UserHandlerMapper.getAllTargetField";
			
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("tablename", "keylabs");
			map.put("field", "url");
			
			List<Map> result = session.selectList(state,map);
			for(Map one:result)
			{
				System.out.println(one.get("id"));
			}
			System.out.println(result.size());
			
		}
}
