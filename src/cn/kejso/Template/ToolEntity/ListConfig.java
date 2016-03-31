package cn.kejso.Template.ToolEntity;

import java.util.List;

public class ListConfig {
	//列表url
	private  List<String>  starturls;
	
	private  boolean pageenable;
	
	//url实体
	private  String   urlitem;
	//url表名
	private  String   tablename;
	
	private  String   listvalue;
	private  List<Tag>  tags;
	
	public ListConfig(List<String> starturls,String listvalue,String urlitem,String tablename,List<Tag> tags)
	{
		this.starturls=starturls;
		this.listvalue=listvalue;
		this.urlitem=urlitem;
		this.tablename=tablename;
		this.tags=tags;
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

	public String getUrlitem() {
		return urlitem;
	}

	public void setUrlitem(String urlitem) {
		this.urlitem = urlitem;
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
}
