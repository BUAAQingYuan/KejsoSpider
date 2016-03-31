package cn.kejso.StoredEntity;

//带分类信息的url
public class ClassifyUrl extends BasicUrl{
	private  String    classname;

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}
}
