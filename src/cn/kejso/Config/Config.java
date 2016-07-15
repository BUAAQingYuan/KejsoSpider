package cn.kejso.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Config {
	
	private static Logger logger = LoggerFactory.getLogger(Config.class);
	
	//mybatis配置文件
	public static final  String   Mybatis_config="mybatis_config.xml"; 
	private static String  Jdbc_config;
	
	public static String getJdbc_config() {
		if(!Jdbc_config.equals("")&&Jdbc_config!=null)
			return Jdbc_config;
		else
			return null;
	}
	public static void setJdbc_config(String jdbc_config) {
		Jdbc_config = jdbc_config;
		logger.info("set jdbc config file [ {} ] .",jdbc_config);
	}
	
	//每插入多少条记录打印info
	public static final  int      Inertinfo_per_number=10;
	
	//爬虫运行结束后的分割线
	public static final  String   Spider_Info_line="################################";
	
	//mysql info
	public static final  String   Insert_info=" have insert {} units.";
	
	//Mybatis插入语句
	public static final  String   Insert_statement="SqlMapper.TemplateMapper.insertEntity";
	
	// 查记录条数语句
	public static final String TheRecordNumber_statement = "SqlMapper.TemplateMapper.getRecordNum";
		
	//Mybatis 获得最后一条记录的id
	public static final  String   TheLastId_statement="SqlMapper.TemplateMapper.getLastId";
	
	//Mybatis 获得最后一条记录的某一属性
	public static final  String   TheLastRecordField_statement="SqlMapper.TemplateMapper.getLastRecordField";

	//Mybatis 获得具有特定属性记录的ID
	public static final  String   TheCertainId_statement="SqlMapper.TemplateMapper.getCertainId";
	
	//Mybatis 获得两个数据域的差集
	public static final  String   TheDeltaField_statement="SqlMapper.TemplateMapper.getDeltaField";
	
	//新建表
	public static final  String   CreateTable_statement="SqlMapper.TemplateMapper.createNewTable";
	
	//获得target url语句
	public static final  String   AllUrl_statement="SqlMapper.TemplateMapper.getAllUrl";
	
	//获得target url语句
	public static final  String   PartUrl_statement="SqlMapper.TemplateMapper.getPartUrl";
	
	// 清空表语句
	public static final String TruncateTable_statement = "SqlMapper.TemplateMapper.truncateTable";

	// 删除表语句
	public static final String DropTable_statement = "SqlMapper.TemplateMapper.dropTable";
	
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
	//chrome
	public static final  String   Spider_Default_userAgent="Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA";
	
	//IE
	public static final  String   Spider_IE_userAgent1="Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0;";
	public static final  String   Spider_IE_userAgent2="Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0)";
	
	//Firefox
	public static final  String   Spider_Firefox_userAgent="Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1";
	
	//opera
	public static final  String   Spider_Opera_userAgent="Opera/9.80 (Windows NT 6.1; U; zh-cn) Presto/2.9.168 Version/11.50";
	
	//360
	public static final String  Spider_360_userAgent="Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; 360SE)";
	
	//腾讯
	public static final String Spider_Tecent_userAgent="Mozilla/5.0 (Windows NT 6.1) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.41 Safari/535.1 QQBrowser/6.9.11079.201";
	
	//baidu
	public static final  String   Spider_Baidu_userAgent="Baiduspider+(+http://www.baidu.com/search/spider.htm)";
	
	//google
	public static final  String   Spider_Google_userAgent1="Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";
	public static final  String   Spider_Google_userAgent2="Googlebot/2.1 (+http://www.googlebot.com/bot.html)";
	public static final  String   Spider_Google_userAgent3="Googlebot/2.1 (+http://www.google.com/bot.html)";
	
	//雅虎
	public static final  String   Spider_Yahoo_userAgent1="Mozilla/5.0 (compatible; Yahoo! Slurp China; http://misc.yahoo.com.cn/help.html)";
	public static final  String   Spider_Yahoo_userAgent2="Mozilla/5.0 (compatible; Yahoo! Slurp; http://help.yahoo.com/help/us/ysearch/slurp)";
	
	//新浪爱问
	public static final  String   Spider_Sina_userAgent1="iaskspider/2.0(+http://iask.com/help/help_index.html)";
	public static final  String   Spider_Sina_userAgent2="Mozilla/5.0 (compatible; iaskspider/1.0; MSIE 6.0)";
	
	//搜狗
	public static final  String   Spider_Sogou_userAgent1="Sogou web spider/3.0(+http://www.sogou.com/docs/help/webmasters.htm#07)";
	public static final  String   Spider_Sogou_userAgent2="Sogou Push Spider/3.0(+http://www.sogou.com/docs/help/webmasters.htm#07)";
	
	//网易
	public static final  String   Spider_Easynet_userAgent1="Mozilla/5.0 (compatible; YodaoBot/1.0; http://www.yodao.com/help/webmaster/spider/;)";
	
	public static final  String[] Spider_All_userAgent=new String[]{Config.Spider_360_userAgent,Config.Spider_Baidu_userAgent,Config.Spider_Google_userAgent2,
		 															Config.Spider_Default_userAgent,Config.Spider_Easynet_userAgent1,Config.Spider_Google_userAgent3,
		 															Config.Spider_Firefox_userAgent,Config.Spider_Google_userAgent1,Config.Spider_IE_userAgent1,
		 															Config.Spider_IE_userAgent2,Config.Spider_Opera_userAgent,Config.Spider_Sina_userAgent1,
		 															Config.Spider_Sina_userAgent2,Config.Spider_Sogou_userAgent1,Config.Spider_Sogou_userAgent2,
		 															Config.Spider_Tecent_userAgent,Config.Spider_Yahoo_userAgent1,Config.Spider_Yahoo_userAgent2};
	//更换UA的概率
	public static final double  ChangeUA_probability=0.3;

	//MimvpProxy
	public static final  String   mimvpProxyDir="configs/MimvpProxy/";
	public static final  String   mimvpProxyAccount="account.xml";
	
}
