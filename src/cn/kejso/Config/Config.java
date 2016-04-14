package cn.kejso.Config;

public class Config {
	
	//mybatis配置文件
	public static final  String   Mybatis_config="mybatis_config.xml"; 
	
	//每插入多少条记录打印info
	public static final  int      Inertinfo_per_number=10;
	
	//爬虫运行结束后的分割线
	public static final  String   Spider_Info_line="################################";
	
	//mysql info
	public static final  String   Insert_info=" have insert {} units.";
	
	//Mybatis插入语句
	public static final  String   Insert_statement="SqlMapper.TemplateMapper.insertEntity";
		
	//Mybatis 获得最后一条记录的id
	public static final  String   TheLastId_statement="SqlMapper.TemplateMapper.getLastId";
	
	//新建表
	public static final  String   CreateTable_statement="SqlMapper.TemplateMapper.createNewTable";
	
	//获得target url语句
	public static final  String   AllUrl_statement="SqlMapper.TemplateMapper.getAllUrl";
	
	//获得target url语句
	public static final  String   PartUrl_statement="SqlMapper.TemplateMapper.getPartUrl";
		
	//pipeline
	//实体标识
	public static final  String   PipeLine_Entity="storedEntity";
	//存储类型
	public static final  String   PipeLine_Type="type";
	public static final  String   PipeLine_TypeList="typeList";
	public static final  String   PipeLine_TypeOne="typeOne";
	
	//spider缓存
	public static final  String   Spider_CacheDir="SpiderScheduler/cache/";
	//error url
	public static final  String   Spider_ErrorDir="SpiderScheduler/error/";
	//mysql记录缓存
	public static final  String   Spider_SQLCacheDir="MysqlCache/";
	
	//useragent
	public static final  String   Spider_userAgent="Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA";
}
