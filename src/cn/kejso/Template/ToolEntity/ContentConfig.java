package cn.kejso.Template.ToolEntity;

import java.util.List;
import java.util.Map.Entry;

public class ContentConfig {
	//页面项
	private  List<Tag>  tags;
	//map
	private  List<String> field;
	private  String  mark;
	private  String  code;
	
	private  String  item;
	private  String  tablename;

	public ContentConfig(List<Tag> tags,String tablename,String item,String mark,String code,List<String> field)
	{
		this.tags=tags;
		this.tablename=tablename;
		this.item=item;
		this.mark=mark;
		this.code=code;
		this.field=field;
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

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public List<String> getField() {
		return field;
	}

	public void setField(List<String> field) {
		this.field = field;
	}
}
