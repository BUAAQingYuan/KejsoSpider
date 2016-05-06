package cn.kejso.Spider.SpiderHandler;

public class Test2Handler implements BasicTableHandler{

	@Override
	public String handler(String tablename) {
		System.out.println("Spider SqlTable handler test 2 ");
		return null;
	}

}
