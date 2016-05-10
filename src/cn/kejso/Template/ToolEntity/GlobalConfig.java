package cn.kejso.Template.ToolEntity;

//配置文件中的全局配置
public class GlobalConfig {
	
	private  static String   taskname;
	
	//线程数
	private  static int      threadnum;
	
	//开启代理
	private  static boolean  enableproxy;
	
	//casperjs路径
	private  static String  casperjsPath;
	
	//循环重试次数
	private  static int      cycleTimes;
	
	public static String getTaskname() {
		return taskname;
	}
	public static void setTaskname(String taskname) {
		GlobalConfig.taskname = taskname;
	}
	public static int getThreadnum() {
		return threadnum;
	}
	public static void setThreadnum(int threadnum) {
		GlobalConfig.threadnum = threadnum;
	}
	public static boolean isEnableproxy() {
		return enableproxy;
	}
	public static void setEnableproxy(boolean enableproxy) {
		GlobalConfig.enableproxy = enableproxy;
	}
	public static String getCasperjsPath() {
		return casperjsPath;
	}
	public static void setCasperjsPath(String casperjsPath) {
		GlobalConfig.casperjsPath = casperjsPath;
	}
	
	public static int getCycleTimes() {
		return cycleTimes;
	}
	
	public void setCycleTimes(int cycleTimes) {
		this.cycleTimes = cycleTimes;
	}

	
}
