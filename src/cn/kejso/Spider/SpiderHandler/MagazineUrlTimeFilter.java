package cn.kejso.Spider.SpiderHandler;

import java.util.ArrayList;
import java.util.List;

public class MagazineUrlTimeFilter implements BasicUrlFilter {

	@Override
	public List<String> filter(List<String> urls) {
		
		List<String> afterurls=new ArrayList<String>();
		
		for(String one:urls)
		{
			if(one.contains("2014")||one.contains("2015"))
			{
				afterurls.add(one);
			}
		}
		
		return afterurls;
	}

	public static void main(String[] args) 
	{
		String url1="http://c.wanfangdata.com.cn/periodical/ahdlzgdxxb/2002-1.aspx";
		
		String url2="http://c.wanfangdata.com.cn/periodical/ahdlzgdxxb/2015-1.aspx";
		
		if(url2.contains("2014")||url2.contains("2015"))
		{
			System.out.println("success");
		}
	}
	
}
