package cn.kejso.Example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import cn.kejso.Example.Entity.URLInfo;
import cn.kejso.Spider.BuildSpiderChain;
import cn.kejso.Tool.FileUtil;

//从文件中读取url、类别、页数(每行一条，以空格分隔)等信息，并启动爬虫
//会议
public class RunSpiderChainFromFile {

	//从文件中读取URLinfo
	public static List<URLInfo>  readUrlinfoFromfile(String file) throws IOException
	{
		List<URLInfo> urlinfos=new ArrayList<URLInfo>();
		List<String> tempurls=FileUtil.ExcepUrls(file);
		for(String one:tempurls)
		{
			String[] tempinfos = one.split(" ");
			URLInfo info=new URLInfo();
			//修改url
			info.setUrl(tempinfos[0]+"&p=#");
			info.setClassify(tempinfos[1]);
			info.setPagenum(tempinfos[2]);
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
		List<URLInfo> urlinfo=readUrlinfoFromfile(sourcefile);
		
		for(URLInfo one:urlinfo)
		{
			XMLConfiguration xml = null;
			try {
				xml = new XMLConfiguration(configfile);
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println(xml.configurationAt("ListConfig").getString("ListUrl"));
			xml.configurationAt("ListConfig").setProperty("ListUrl",one.getUrl());
			xml.configurationAt("ListConfig").setProperty("PageEnd",one.getPagenum());
			xml.configurationAt("ListConfig").setProperty("ConstTag.TagValue",one.getClassify());
			xml.configurationAt("ContentConfig").setProperty("ConstTag.TagValue",one.getClassify());
			//System.out.println(xml.configurationAt("ListConfig").getString("ListUrl"));
			xml.save();
			//xml.reload();
			xml.reload();	
			//System.out.println(xml.configurationAt("ListConfig").getString("ListUrl")+"  "+xml.configurationAt("ListConfig").getString("PageEnd")+"  "+xml.configurationAt("ContentConfig").getString("ConstTag.TagValue"));
			//spdier
			BuildSpiderChain bsc = new BuildSpiderChain(configfile);
			bsc.startSpiders();
		}
		
	}
	
	public static void main(String[] args) throws IOException, ConfigurationException {
		
		if(args.length!=2)
		{
			System.out.println("Usage: java -jar KejsoSpider.jar sourceurl  configfile .");
		}
		
		String sourceurl=args[0];
		String config=args[1];
		
		RunSpiderChainFromFile.StartSpiderChain(sourceurl,config);
		
		//RunSpiderChainFromFile.StartSpiderChain("paper.txt","configs\\wanfangmeeting.xml");
	}
}
