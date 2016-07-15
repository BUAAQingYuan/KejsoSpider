package cn.kejso.Template.ToolEntity;

import java.util.List;

public class BaseConfig {
	
	//数据表名
	private  String  tablename;
	//文件名
	private  String  storefile;
	
	private  List<String> fields;
	
	private  String  unique;


	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public String getUnique() {
		return unique;
	}

	public void setUnique(String unique) {
		this.unique = unique;
	}

	public String getStorefile() {
		return storefile;
	}

	public void setStorefile(String storefile) {
		this.storefile = storefile;
	}
}
