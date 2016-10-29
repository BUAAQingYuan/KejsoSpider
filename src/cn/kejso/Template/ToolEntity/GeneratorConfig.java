package cn.kejso.Template.ToolEntity;

import java.util.ArrayList;
import java.util.List;

import cn.kejso.Tool.SqlUtil;

public class GeneratorConfig extends BaseConfig implements ContainStartUrls{
	
	//name
	private String name;
	
	//构造url的模板
	private String  urlmoban;
	//来源表
	private String  sourcetable;
	//来源字段
	private String  sourcefield;
	
	
	
	public GeneratorConfig(String name,String urlmoban,String sourcetable,String sourcefield)
	{
		this.name = name;
		this.urlmoban = urlmoban;
		this.sourcetable = sourcetable;
		this.sourcefield = sourcefield;
	}
	
	
	@Override
	public List<String> getStartUrls() {
		//urls
		List<String> starturls = new ArrayList<String>();
		List<String> fields = new ArrayList<String>();
		if(this.sourcetable != null && this.sourcefield != null)
		{
			fields = SqlUtil.getField(this.sourcetable, this.sourcefield);
		}
		
		//construct urls
		for(String one:fields)
		{
			String temp = this.urlmoban.replaceFirst("#", one);
			starturls.add(temp);
		}
		
		return starturls;
	}

	public String getUrlmoban() {
		return urlmoban;
	}

	public void setUrlmoban(String urlmoban) {
		this.urlmoban = urlmoban;
	}

	public String getSourcetable() {
		return sourcetable;
	}

	public void setSourcetable(String sourcetable) {
		this.sourcetable = sourcetable;
	}

	public String getSourcefield() {
		return sourcefield;
	}

	public void setSourcefield(String sourcefield) {
		this.sourcefield = sourcefield;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

}
