package cn.kejso.Tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kejso.Config.Config;
import cn.kejso.Template.SpiderConf;

public class SpiderUtil {

	private static Logger logger = LoggerFactory.getLogger(SpiderUtil.class);
	private static SqlSessionFactory sessionFactory = null;

	// 读取mybatis配置文件
	static {
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader(Config.Mybatis_config);
		} catch (IOException e) {
			logger.error("读取mybatis配置文件出错!");
			e.printStackTrace();
		}
		sessionFactory = new SqlSessionFactoryBuilder().build(reader);
	}

	public static SqlSession getSession() {
		return sessionFactory.openSession();
	}

	// 解析list 页面链接
	public static List<String> getListUrls(String site, int start, int end) {
		List<String> starturls = new ArrayList<String>();
		for (int i = start; i <= end; i++) {
			starturls.add(site.replaceAll("#", String.valueOf(i)));
		}
		return starturls;
	}

	// 获得map 属性
	public static List<String> getMapFields(String field) {
		List<String> fields = new ArrayList<String>();

		if (field != null) {
			String[] temp = field.split("#");
			for (String one : temp) {
				if (!one.equals(""))
					fields.add(one);
				else
					fields.add(null);
			}
		}
		return fields;
	}

	// 获得指定名字爬虫的SpiderConf
	public static SpiderConf getSpiderConfByName(String name, List<SpiderConf> spiders) {
		SpiderConf target = null;
		for (SpiderConf one : spiders) {
			if (one.getName().equals(name)) {
				target = one;
				break;
			}
		}

		return target;
	}
	
	//判断一个Map<String,String>实例是否内容(动态字段)为空,excludeFields为排除字段

	//全部内容为空或者null时，返回true

	public static  boolean  ResultIsNull(Map<String,String> result,List<String> excludeFields)
	{

		boolean  isnull=true;

		for(String key:result.keySet())
		{
			//空格、制表符
			if(!excludeFields.contains(key)&&result.get(key)!=null&&!result.get(key).replaceAll(" ", "").replaceAll("	", "").equals(""))
			{
				isnull=false;
				break;
			}
		}
		
		return isnull;
	}

	
	//随机获取一个ua
	public static  String   RandomUserAgent()
	{
		List<String> useragents=new ArrayList<String>(Arrays.asList(Config.Spider_All_userAgent));
		int  index=(int)(Math.random()*useragents.size());
		//System.out.println(index+" "+useragents.get(index));
		return useragents.get(index);
	}
	
	

	// 查看当前IP
	public static String getCurrentIP() {
		String currentIP = null;
		InputStream ins = null;
		try {
			URL url = new URL("http://1212.ip138.com/ic.asp");
			URLConnection con = url.openConnection();
			ins = con.getInputStream();
			InputStreamReader isReader = new InputStreamReader(ins, "GB2312");
			BufferedReader bReader = new BufferedReader(isReader);
			StringBuffer webContent = new StringBuffer();
			String str = null;
			while ((str = bReader.readLine()) != null) {
				webContent.append(str);
			}
			int start = webContent.indexOf("[") + 1;
			int end = webContent.indexOf("]");
			currentIP = webContent.substring(start, end);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return currentIP;
	}

	// 获得代理IP
	/*
	 * public static List<ProxyHost> getProxyIP() { List<ProxyHost>
	 * proxyhosts=new ArrayList<ProxyHost>();
	 * 
	 * return proxyhosts; }
	 */

	public static void main(String[] args) throws IOException {

		int number=0;
		int total=10000;
		//随机设置UA
		for(int i=0;i<total;i++)
		{
			if(Math.random()<Config.ChangeUA_probability)
			{
				System.out.println(SpiderUtil.RandomUserAgent());
				number++;
			}	
		}
		
		System.out.println("probability : "+(double)number/total);
	}
	
	
}
