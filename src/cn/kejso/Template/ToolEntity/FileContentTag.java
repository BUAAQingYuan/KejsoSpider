package cn.kejso.Template.ToolEntity;

public class FileContentTag {

	//下载内容的数据表来源
	private String sourceField;
	
	//文件存放位置的数据表位置
	private String TargetField;
	
	//文件存放路径
	private String savePath;
	
	//强制指定扩展名
	private String forceExt;
	
	public FileContentTag(String sourceField, String TargetField, String savePath, String forceExt) {
		
		this.sourceField = sourceField;
		this.TargetField = TargetField;
		this.savePath = savePath;
		this.forceExt = forceExt;
	}

	public String getSourceField() {
		return sourceField;
	}

	public void setSourceField(String sourceField) {
		this.sourceField = sourceField;
	}

	public String getTargetField() {
		return TargetField;
	}

	public void setTargetField(String targetField) {
		TargetField = targetField;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getForceExt() {
		return forceExt;
	}

	public void setForceExt(String forceExt) {
		this.forceExt = forceExt;
	}
}
