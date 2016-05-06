package cn.kejso.Template.ToolEntity;

//配置文件中的全局配置
public class GlobalConfig {
	
	private  String   taskname;
	
	//线程数
	private  int      threadnum;
	
	//开启代理
	private  boolean  enableproxy;
	
	//casperjs路径
	private  String  casperjsPath;
	
	public String getTaskname() {
		return taskname;
	}
	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}
	public int getThreadnum() {
		return threadnum;
	}
	public void setThreadnum(int threadnum) {
		this.threadnum = threadnum;
	}
	public boolean isEnableproxy() {
		return enableproxy;
	}
	public void setEnableproxy(boolean enableproxy) {
		this.enableproxy = enableproxy;
	}
	public String getCasperjsPath() {
		return casperjsPath;
	}
	public void setCasperjsPath(String casperjsPath) {
		this.casperjsPath = casperjsPath;
	}

	
}
