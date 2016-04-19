package cn.kejso.Template;

public class RecoverConfig {
	
	// 是否开启断点恢复功能
	private boolean enable;

	public static enum RecoverMode {simple, delta};
	private RecoverMode mode;
	
	private String ref;
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
}
