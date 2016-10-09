package cn.kejso.Test;

import java.io.IOException;

import cn.kejso.Config.Config;
import cn.kejso.Spider.BuildSpiderChain;

public class spiderchain {
	
public static void main(String[] args) throws IOException {
	
		String config="configs\\qx\\wanfangExpert_qx.xml";
		String jdbcconfig="configs\\qx\\jdbc.properties";
		
		Config.setJdbc_config(jdbcconfig);
		
		BuildSpiderChain bsc = new BuildSpiderChain(config);
		//bsc.startSpiders();
		bsc.startSpidersForErrorUrls();
	}
}
