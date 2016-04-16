package cn.kejso.Template;

import java.util.ArrayList;
import java.util.List;

import cn.kejso.Template.ToolEntity.BaseConfig;

public class SpiderConf {
	
		//爬虫名称
		private String  name;
		
		//配置
		private BaseConfig config;
		
		//依赖的爬虫名称
		private String dependname;
		
		//断点恢复的配置
		private RecoverConfig rconfig;
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public BaseConfig getConfig() {
			return config;
		}

		public void setConfig(BaseConfig config) {
			this.config = config;
		}

		public String getDependname() {
			return dependname;
		}

		public void setDependname(String dependname) {
			this.dependname = dependname;
		}
		
		
		//get depend urls
		public List<String> getDependUrls()
		{
			List<String> urls=new ArrayList<String>();
			
			return urls;
		}
		
		public RecoverConfig getRecoverConfig() {
			return rconfig;
		}
		
		public void setRecoverConfig(RecoverConfig value) {
			rconfig = value;
		}

}
