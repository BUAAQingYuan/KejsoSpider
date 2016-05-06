package cn.kejso.Spider.SpiderHandler;

public class Test1Handler implements BasicTableHandler {

	@Override
	public String handler(String tablename) {
		System.out.println("Spider SqlTable handler test 1 ");
		return null;
	}

}
