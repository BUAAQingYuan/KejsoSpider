package cn.kejso.Template.ToolEntity;

import java.util.ArrayList;
import java.util.List;

//获得列表页url
public class PreConfig extends BaseConfig implements ContainStartUrls{
	
	//数据表
	private  String tablename;
	//字段
	private  List<String> fields;
	//unique字段
	private  String  unique;
	//preurl
	private  String  preurl;
	//prevalue
	private  String  prevalue;
	
	
	public PreConfig(String preurl,String prevalue,String tablename,List<String> fields,String unique)
	{
		this.preurl=preurl;
		this.prevalue=prevalue;
		this.tablename=tablename;
		this.fields=fields;
		this.unique=unique;
	}
	
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
	public String getPreurl() {
		return preurl;
	}
	public void setPreurl(String preurl) {
		this.preurl = preurl;
	}
	public String getPrevalue() {
		return prevalue;
	}
	public void setPrevalue(String prevalue) {
		this.prevalue = prevalue;
	}

	@Override
	public List<String> getStartUrls() {
		
		List<String> list = new ArrayList<String>();
		list.add(getPreurl());
		
		return list;
	}
	
}
