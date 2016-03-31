package cn.kejso.Config;

public class Config {
	
	//mybatis配置文件
	public static final  String   Mybatis_config="mybatis_config.xml"; 
	
	//mysql info
	public static final  String   Insert_info=" insert {} .";
	
	//Mybatis 插入语句
	public static final  String   Insert_statement="SqlMapper.#Mapper.insert#";
	
	//Mybatis select-all语句
	public static final  String   SelectAll_statement="SqlMapper.#Mapper.getAll#";
	
	//是否存在一张表
	public static final  String   ClassifyUrl_existTable="SqlMapper.ClassifyUrlMapper.existTable";
	//建立新表
	public static final  String   ClassifyUrl_createTable="SqlMapper.ClassifyUrlMapper.createNewTable";
	
	//获取target url语句
	public static final  String   AllUrl_statement="SqlMapper.#Mapper.getAllUrl";
	
	
	//模板类所在的包
	public static final  String   StoredEntity="cn.kejso.StoredEntity";
	
	
	//pipeline标识
	//存储实体
	public static final  String   PipeLine_Entity="storedEntity";
	//存储类型
	public static final  String   PipeLine_Type="type";
	public static final  String   PipeLine_TypeList="typeList";
	public static final  String   PipeLine_TypeOne="typeOne";
	
	
	public static final  String   Spider_CacheDir="SpiderScheduler/cache/";
	
}
