package cn.kejso.Template;

public class AbstractTemplate {
	
	private  String   taskname;
	private  int      threadnum;
	private  boolean  enableproxy;
	
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
	
}
