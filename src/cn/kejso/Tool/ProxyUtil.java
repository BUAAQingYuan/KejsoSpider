package cn.kejso.Tool;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;

import cn.kejso.Config.Config;
import cn.kejso.Template.ToolEntity.ProxyAccountConfig;
import cn.kejso.Tool.Proxy.MimvpProxy;

public class ProxyUtil {
	/*
	 * //获得代理IP public static List<ProxyHost> getProxyIP() { List<ProxyHost>
	 * proxyhosts=new ArrayList<ProxyHost>();
	 * 
	 * File confdir=new File("configs\\ProxyConfig"); try { ProxyIPFactory
	 * factory=new ProxyIPFactory(confdir);
	 * 
	 * long startTime=System.currentTimeMillis();
	 * proxyhosts=ProxyIPFactory.getProxyHosts(); long
	 * endTime=System.currentTimeMillis(); System.out.println("程序运行时间： "
	 * +(endTime-startTime)+"ms"); } catch (ConfigurationException | IOException
	 * | InterruptedException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * return proxyhosts; }
	 * 
	 * public static void main(String[] args){ ProxyUtil.getProxyIP(); }
	 */
	
	//Mimvp如果换了IP，即使免费上限已到，也可以提取到代理
	
	private static List<String[]> proxys;

	public static List<String[]> getMimvpProxyList() {

		if (proxys!=null && !proxys.isEmpty()) {
			return proxys;
		}

		String proxyList = Config.mimvpProxyDir + new SimpleDateFormat("yyyyMMdd").format(new Date());
		
		// 如果存在则读取
		if (FileUtil.isFileExisted(proxyList)) {
			proxys = FileUtil.readProxy(proxyList);
		}
		
		//如果未能成功读取或文件不存在
		if (proxys == null || proxys.isEmpty()) {
			
			String accountConfigPath = Config.mimvpProxyDir + Config.mimvpProxyAccount;
			ProxyAccountConfig config = TemplateConstructor.getProxyAccountConfig(accountConfigPath);
			proxys = MimvpProxy.getProxy(config);
			FileUtil.PrintProxy(proxyList, proxys);
			
		}

		return proxys;

	}

	public static void main(String[] args) {

		List<String[]> list = ProxyUtil.getMimvpProxyList();
		
		for (String[] item : list) {
			System.out.println(item[0]+":"+item[1]);
		}
		
	}
}
