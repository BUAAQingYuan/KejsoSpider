package cn.kejso.Template.ToolEntity;

import java.util.List;
import java.util.Map.Entry;

public class ContentConfig extends BaseConfig{
	//页面项
	private  List<Tag>  tags;
	//map映射field
	private  List<String> field;
	//map对应的mark
	private	 List<String> markfield;
	private  String  mark;
	private  String  code;
	
	//sql
	private  List<String> fields;
	private  String  unique;
	
	private  String  tablename;

	public ContentConfig(List<Tag> tags,String tablename,String mark,String code,List<String> field,List<String> markfield,List<String> fields,String unique)
	{
		this.tags=tags;
		this.tablename=tablename;
		this.mark=mark;
		this.code=code;
		this.field=field;
		this.markfield=markfield;
		
		this.fields=fields;
		this.unique=unique;
	}
	
	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public List<String> getField() {
		return field;
	}

	public void setField(List<String> field) {
		this.field = field;
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

	public List<String> getMarkfield() {
		return markfield;
	}

	public void setMarkfield(List<String> markfield) {
		this.markfield = markfield;
	}
}
