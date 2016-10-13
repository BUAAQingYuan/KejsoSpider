package cn.kejso.Example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import cn.kejso.Config.Config;
import cn.kejso.Example.Entity.MagazineURLInfo;
import cn.kejso.Spider.BuildSpiderChain;
import cn.kejso.Tool.FileUtil;

//从文件中读取url、类别、页数(每行一条，以空格分隔)等信息，并启动爬虫
//期刊
public class RunSpiderChainForMagazineFromFile {

	//从文件中读取URLinfo
	public static List<MagazineURLInfo>  readUrlinfoFromfile(String file) throws IOException
	{
		List<MagazineURLInfo> urlinfos=new ArrayList<MagazineURLInfo>();
		List<String> tempurls=FileUtil.ExcepUrls(file);
		for(String one:tempurls)
		{
			String[] tempinfos = one.split(" ");
			MagazineURLInfo info=new MagazineURLInfo();
			//修改url
			info.setUrl(tempinfos[0]);
			info.setSubclass(tempinfos[1]);
			info.setClassify(tempinfos[2]);
			urlinfos.add(info);
			//System.out.println(info.toString());
		}
		
		return urlinfos;
	}
	
	
	//从文件中读取url
	public static List<String>  readUrlFromfile(String file)
	{
		List<String> urls=new ArrayList<String>();
		return urls;
	}
	
	
	//修改配置文件，并启动爬虫链
	public static void  StartSpiderChain(String sourcefile,String configfile) throws IOException, ConfigurationException
	{
		List<MagazineURLInfo> urlinfo=readUrlinfoFromfile(sourcefile);
		
		for(MagazineURLInfo one:urlinfo)
		{
			XMLConfiguration xml = null;
			try {
				xml = new XMLConfiguration(configfile);
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println(xml.configurationAt("ListConfig").getString("ListUrl"));
			xml.configurationAt("PreConfig").setProperty("PreUrl",one.getUrl());
			xml.configurationAt("ListConfig.ConstTag(0)").setProperty("TagValue",one.getSubclass());
			xml.configurationAt("ListConfig.ConstTag(1)").setProperty("TagValue",one.getClassify());
			
			xml.configurationAt("ContentConfig.ConstTag(0)").setProperty("TagValue",one.getSubclass());
			xml.configurationAt("ContentConfig.ConstTag(1)").setProperty("TagValue",one.getClassify());
			//System.out.println(xml.configurationAt("ListConfig").getString("ListUrl"));
			xml.save();
			//xml.reload();
			xml.reload();	
			//System.out.println(xml.configurationAt("ListConfig").getString("ListUrl")+"  "+xml.configurationAt("ListConfig").getString("PageEnd")+"  "+xml.configurationAt("ContentConfig").getString("ConstTag.TagValue"));
			//spdier
			BuildSpiderChain bsc = new BuildSpiderChain(configfile);
			bsc.startSpiders(false);
		}
		
	}
	
	public static void main(String[] args) throws IOException, ConfigurationException {
		
		if(args.length!=3)
		{
			System.out.println("Usage: java -jar KejsoSpider.jar sourceurl  configfile  jdbc-config .");
		}
		
		String sourceurl=args[0];
		String config=args[1];
		String jdbcconfig=args[2];
		
		//设置mybatis 配置文件
		Config.setJdbc_config(jdbcconfig);
		
		RunSpiderChainForMagazineFromFile.StartSpiderChain(sourceurl,config);
		
		//RunSpiderChainForMagazineFromFile.StartSpiderChain("magazineurls.txt","configs\\WanfangMagazine.xml");
	}
}
