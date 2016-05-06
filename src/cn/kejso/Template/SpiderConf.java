package cn.kejso.Template;

import java.util.ArrayList;
import java.util.List;

import cn.kejso.Template.ToolEntity.BaseConfig;

public class SpiderConf {
	
		//爬虫名称-用于唯一标识爬虫
		private String  name;
		
		//爬虫名称-用于提示
		private String	cname;
		
		//是否动态网页爬虫
		private boolean  dynamic;
		
		//动态网页对应的js文件
		private String   casperjs;
		
		//配置
		private BaseConfig config;
		
		//依赖的爬虫名称或数据库 
		private String dependname;
		
		//当依赖的为数据表时的url字段
		private String field;
		
		//断点恢复的配置
		private RecoverConfig rconfig;
		
		//爬虫运行前数据库条目数量
		private int startpoint;
		
		
		//数据表handler
		private String beforehandler;
		private String afterhandler;
		
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
		
		public void setStartpoint(int startpoint) {
			this.startpoint = startpoint;
		}
		
		public int getStartpoint() {
			return startpoint;
		}
		
		public void setCname(String cname) {
			this.cname = cname;
		}
		
		public String getCname() {
			return cname;
		}

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		public boolean isDynamic() {
			return dynamic;
		}

		public void setDynamic(boolean dynamic) {
			this.dynamic = dynamic;
		}

		public String getCasperjs() {
			return casperjs;
		}

		public void setCasperjs(String casperjs) {
			this.casperjs = casperjs;
		}

		public String getBeforehandler() {
			return beforehandler;
		}

		public void setBeforehandler(String beforehandler) {
			this.beforehandler = beforehandler;
		}

		public String getAfterhandler() {
			return afterhandler;
		}

		public void setAfterhandler(String afterhandler) {
			this.afterhandler = afterhandler;
		}

}
