package cn.kejso.Template.ToolEntity;

public class Tag {
	private  String  Tagname;
	private  String  TagValue;
	
	
	public Tag(String tagname,String tagvalue)
	{
		this.Tagname=tagname;
		this.TagValue=tagvalue;
	}
	
	
	public String getTagname() {
		return Tagname;
	}
	public void setTagname(String tagname) {
		Tagname = tagname;
	}
	public String getTagValue() {
		return TagValue;
	}
	public void setTagValue(String tagValue) {
		TagValue = tagValue;
	}
}
