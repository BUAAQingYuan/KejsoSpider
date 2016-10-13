package cn.kejso.Example.Entity;

public class MagazineURLInfo {
	private String  url;
	private String  classify;
	private String  subclass;
	private String  pagenum;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}
	public String getPagenum() {
		return pagenum;
	}
	public void setPagenum(String pagenum) {
		this.pagenum = pagenum;
	}
	
	public String toString()
	{
		return this.url+"  "+this.classify+"  "+this.pagenum;
	}
	public String getSubclass() {
		return subclass;
	}
	public void setSubclass(String subclass) {
		this.subclass = subclass;
	}
	
}
