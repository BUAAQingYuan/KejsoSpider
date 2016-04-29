package cn.kejso.Template;

public class RecoverConfig {
	
	// 是否开启断点恢复功能
	private boolean enable;
	
	
	/*
	 * simple，获得url表中后续的url,更新内容表到最新的位置。
	 * delta,获取url表比content表多出的url
	 * listdelta,感觉和simple效果一样
	 */
	public static enum RecoverMode {simple, delta, listdelta};
	private RecoverMode mode;
	
	//列表页中和内容页一致的字段
	private String ref;
	//内容页中与列表页中一致的字段
	private String field;

	public void setEnable(boolean value) {
		enable = value;
	}

	public boolean getEnable() {
		return enable;
	}

	public void setRef(String value) {
		ref = value;
	}

	public String getRef() {
		return ref;
	}

	public void setField(String value) {
		field = value;
	}

	public String getField() {
		return field;
	}
	
	public void setMode(RecoverMode mode) {
		this.mode = mode;
	}
	
	public RecoverMode getMode() {
		return mode;
	}
	
	public boolean isSimpleRecover() {
		return mode.equals(RecoverMode.simple);
	}
	
	public boolean isDeltaRecover() {
		return mode.equals(RecoverMode.delta);
	}
	
	public boolean isListDeltaRecover() {
		return mode.equals(RecoverMode.listdelta);
	}
}
