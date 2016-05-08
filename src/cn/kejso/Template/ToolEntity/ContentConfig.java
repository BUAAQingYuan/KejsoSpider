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
//	private  List<String> fields;
//	private  String  unique;
	
//	private  String  tablename;
	
	private String	pageUrlField;
	private String	notNullField;
	
	//常量字段
	private  List<Tag>  consttags;
	
	private String 	multicontentseparator;
	private List<Tag>	multicontenttags;
	
	private List<FileContentTag> filecontenttags;

	public ContentConfig(List<Tag> tags,String tablename,String mark,String code,
			List<String> field,List<String> markfield,List<String> fields,
			String unique, String pageUrlField, String notNullField,List<Tag> consttags,
			String multicontentseparator, List<Tag> multicontenttags, List<FileContentTag> filecontenttags)
	{
		this.tags=tags;
//		this.tablename=tablename;
		setTablename(tablename);
		this.mark=mark;
		this.code=code;
		this.field=field;
		this.markfield=markfield;
		
//		this.fields=fields;
		setFields(fields);
//		this.unique=unique;
		setUnique(unique);
		
		this.pageUrlField=pageUrlField;
		this.notNullField=notNullField;
		
		this.consttags=consttags;
		
		this.multicontentseparator = multicontentseparator;
		this.multicontenttags = multicontenttags;
		
		this.filecontenttags = filecontenttags;
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

//	public String getTablename() {
//		return tablename;
//	}
//
//	public void setTablename(String tablename) {
//		this.tablename = tablename;
//	}

	public List<String> getField() {
		return field;
	}

	public void setField(List<String> field) {
		this.field = field;
	}

//	public List<String> getFields() {
//		return fields;
//	}
//
//	public void setFields(List<String> fields) {
//		this.fields = fields;
//	}
//
//	public String getUnique() {
//		return unique;
//	}
//
//	public void setUnique(String unique) {
//		this.unique = unique;
//	}

	public List<String> getMarkfield() {
		return markfield;
	}

	public void setMarkfield(List<String> markfield) {
		this.markfield = markfield;
	}
	
	public void setPageUrlField(String pageUrlField) {
		this.pageUrlField = pageUrlField;
	}
	
	public String getPageUrlField() {
		return pageUrlField;
	}
	
	public void setNotNullField(String notNullField) {
		this.notNullField = notNullField;
	}
	
	public String getNotNullField() {
		return notNullField;
	}

	public List<Tag> getConsttags() {
		return consttags;
	}

	public void setConsttags(List<Tag> consttags) {
		this.consttags = consttags;
	}
	
	public String getMultiContentSeparator() {
		return multicontentseparator;
	}
	
	public void setMultiContentSeparator(String multicontentseparator) {
		this.multicontentseparator = multicontentseparator;
	}
	
	public List<Tag> getMultiContentTag() {
		return multicontenttags;
	}
	
	public void setMultiContentTag(List<Tag> multicontenttags) {
		this.multicontenttags = multicontenttags;
	}

	public List<FileContentTag> getFilecontenttags() {
		return filecontenttags;
	}

	public void setFilecontenttags(List<FileContentTag> filecontenttags) {
		this.filecontenttags = filecontenttags;
	}
}
