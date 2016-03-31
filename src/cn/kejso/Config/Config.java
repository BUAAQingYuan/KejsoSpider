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
	public static final  String   Insert_statement="SqlMapper.#Mapper.insert#";
	
	//Mybatis select-all语句
	public static final  String   SelectAll_statement="SqlMapper.#Mapper.getAll#";
	
	//表是否存在
	public static final  String   ClassifyUrl_existTable="SqlMapper.ClassifyUrlMapper.existTable";
	//新建表
	public static final  String   ClassifyUrl_createTable="SqlMapper.ClassifyUrlMapper.createNewTable";
	
	//获得target url语句
	public static final  String   AllUrl_statement="SqlMapper.#Mapper.getAllUrl";
	
	
	//存储实体
	public static final  String   StoredEntity="cn.kejso.StoredEntity";
	
	
	//pipeline
	//实体标识
	public static final  String   PipeLine_Entity="storedEntity";
	//存储类型
	public static final  String   PipeLine_Type="type";
	public static final  String   PipeLine_TypeList="typeList";
	public static final  String   PipeLine_TypeOne="typeOne";
	
	//spider缓存
	public static final  String   Spider_CacheDir="SpiderScheduler/cache/";
	
}
