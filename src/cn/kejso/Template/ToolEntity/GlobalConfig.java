package cn.kejso.Template.ToolEntity;

import java.util.List;

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
	
	//初始sleeptime
	private  static int			sleeptime;
	//是否在失败重试时增加重试时间
	private  static boolean		moresleeptime;
	
	//generators
	private  List<GeneratorConfig>  generators;
	
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
	
	public static void setCycleTimes(int cycleTimes) {
		GlobalConfig.cycleTimes = cycleTimes;
	}
	public static int getSleeptime() {
		return sleeptime;
	}
	public static void setSleeptime(int sleeptime) {
		GlobalConfig.sleeptime = sleeptime;
	}
	public static boolean isMoresleeptime() {
		return moresleeptime;
	}
	public static void setMoresleeptime(boolean moresleeptime) {
		GlobalConfig.moresleeptime = moresleeptime;
	}
	public List<GeneratorConfig> getGenerators() {
		return generators;
	}
	public void setGenerators(List<GeneratorConfig> generators) {
		this.generators = generators;
	}

	
}
