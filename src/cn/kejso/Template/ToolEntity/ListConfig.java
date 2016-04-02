package cn.kejso.Template.ToolEntity;

import java.util.List;

public class ListConfig  extends BaseConfig {
	//列表url
	private  List<String>  starturls;
	
	private  boolean pageenable;
	
	//url表名
	private  String   tablename;
	//表的字段
	private  List<String> fields;
	//unique字段
	private  String   unique;
	
	private  String   listvalue;
	private  List<Tag>  tags;
	
	public ListConfig(List<String> starturls,String listvalue,String tablename,List<Tag> tags,List<String> fields,String unique)
	{
		this.starturls=starturls;
		this.listvalue=listvalue;
		this.tablename=tablename;
		this.tags=tags;
		
		this.fields=fields;
		this.unique=unique;
	}

	
	
	public boolean isPageenable() {
		return pageenable;
	}
	public void setPageenable(boolean pageenable) {
		this.pageenable = pageenable;
	}
	
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public String getListvalue() {
		return listvalue;
	}

	public void setListvalue(String listvalue) {
		this.listvalue = listvalue;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}



	public List<String> getStarturls() {
		return starturls;
	}



	public void setStarturls(List<String> starturls) {
		this.starturls = starturls;
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
}
