package cn.kejso.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpHost;

import cn.kejso.Config.Config;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

public class ExpertSpider implements  PageProcessor,Runnable{

	private Site site;

	@Override
	public void run() {
	
		//String  s="http://d.wanfangdata.com.cn/Expert/16307?transaction=%7b%22ExtraData%22%3a%5b%5d%2c%22IsCache%22%3afalse%2c%22Transaction%22%3a%7b%22DateTime%22%3a%22%5c%2fDate(1461847393328%2b0800)%5c%2f%22%2c%22Id%22%3a%2278e078a0-d381-427d-ba85-a5f601557610%22%2c%22Memo%22%3anull%2c%22ProductDetail%22%3a%22Expert_16307%22%2c%22SessionId%22%3a%22ff43a8c6-2596-4201-9360-476715641767%22%2c%22Signature%22%3a%22HB%5c%2f641kWsM2zT6bgjUyhv4Oq91ptfSBfntkXRzpTWMXPtQxHO97rh8d%2b0L%5c%2fwwgBv%22%2c%22TransferIn%22%3a%7b%22AccountType%22%3a%22Income%22%2c%22Key%22%3a%22ExpertDigest%22%7d%2c%22TransferOut%22%3a%7b%22AccountType%22%3a%22GTimeLimit%22%2c%22Key%22%3a%22bjhkht%22%7d%2c%22Turnover%22%3a1.00000%2c%22User%22%3anull%2c%22UserIP%22%3a%22124.207.188.68%22%7d%2c%22TransferOutAccountsStatus%22%3a%5b%5d%7d";
		//String  s="http://d.wanfangdata.com.cn/Expert/8975?transaction=%7b%22ExtraData%22%3a%5b%5d%2c%22IsCache%22%3afalse%2c%22Transaction%22%3a%7b%22DateTime%22%3a%22%5c%2fDate(1461847393328%2b0800)%5c%2f%22%2c%22Id%22%3a%2278e078a0-d381-427d-ba85-a5f601557610%22%2c%22Memo%22%3anull%2c%22ProductDetail%22%3a%22Expert_8975%22%2c%22SessionId%22%3a%22ff43a8c6-2596-4201-9360-476715641767%22%2c%22Signature%22%3a%22HB%5c%2f641kWsM2zT6bgjUyhv4Oq91ptfSBfntkXRzpTWMXPtQxHO97rh8d%2b0L%5c%2fwwgBv%22%2c%22TransferIn%22%3a%7b%22AccountType%22%3a%22Income%22%2c%22Key%22%3a%22ExpertDigest%22%7d%2c%22TransferOut%22%3a%7b%22AccountType%22%3a%22GTimeLimit%22%2c%22Key%22%3a%22bjhkht%22%7d%2c%22Turnover%22%3a1.00000%2c%22User%22%3anull%2c%22UserIP%22%3a%22124.207.188.68%22%7d%2c%22TransferOutAccountsStatus%22%3a%5b%5d%7d";
		//String s="http://d.g.wanfangdata.com.cn/Expert.aspx?ID=Expert_12374&transaction=%7b%22ExtraData%22%3a%5b%5d%2c%22IsCache%22%3afalse%2c%22Transaction%22%3a%7b%22DateTime%22%3a%22%5c%2fDate(1461856827544%2b0800)%5c%2f%22%2c%22Id%22%3a%22c538ba45-f606-4276-97c4-a5f60180a5c9%22%2c%22Memo%22%3anull%2c%22ProductDetail%22%3a%22Expert_12374%22%2c%22SessionId%22%3a%226156d731-caa3-46f3-b11b-996dcbc9a153%22%2c%22Signature%22%3a%22Iu8ZaUtAjK%2b2%2bwk6CKSd3%5c%2fSbQngTNNTKDTuBaYrAvSl5eLKaTSvJCwYl9he0I150%22%2c%22TransferIn%22%3a%7b%22AccountType%22%3a%22Income%22%2c%22Key%22%3a%22ExpertDigest%22%7d%2c%22TransferOut%22%3a%7b%22AccountType%22%3a%22GTimeLimit%22%2c%22Key%22%3a%22bjhkht%22%7d%2c%22Turnover%22%3a1.00000%2c%22User%22%3a%7b%22AccountType%22%3a%22Person%22%2c%22Key%22%3a%22kejso%22%7d%2c%22UserIP%22%3a%22124.207.188.68%22%7d%2c%22TransferOutAccountsStatus%22%3a%5b%5d%7d";
		//String s="http://d.wanfangdata.com.cn/Institution/csiN07770?transaction=%7b%22ExtraData%22%3a%5b%5d%2c%22IsCache%22%3afalse%2c%22Transaction%22%3a%7b%22DateTime%22%3a%22%5c%2fDate(1462288868958%2b0800)%5c%2f%22%2c%22Id%22%3a%22d1226243-3b30-461d-b1ca-a5fb0180d651%22%2c%22Memo%22%3anull%2c%22ProductDetail%22%3a%22Institution_csiN07770%22%2c%22SessionId%22%3a%2265e10e76-83ee-48ac-8f19-6afc42277650%22%2c%22Signature%22%3a%22B3XOlOre0cxVDRic8aEcxiVMZoLgGyQrqlbrCb9NQk3QInIU8uD1FAmeh82eef%5c%2f2%22%2c%22TransferIn%22%3a%7b%22AccountType%22%3a%22Income%22%2c%22Key%22%3a%22InstitutionDigest%22%7d%2c%22TransferOut%22%3a%7b%22AccountType%22%3a%22GTimeLimit%22%2c%22Key%22%3a%22bjhkht%22%7d%2c%22Turnover%22%3a1.00000%2c%22User%22%3a%7b%22AccountType%22%3a%22Person%22%2c%22Key%22%3a%22kejso%22%7d%2c%22UserIP%22%3a%22124.207.188.68%22%7d%2c%22TransferOutAccountsStatus%22%3a%5b%5d%7d";
		//String s="http://c.wanfangdata.com.cn/ResearchEmphasisInstitution.aspx?type=%E5%9B%BD%E5%AE%B6%E9%87%8D%E7%82%B9%E8%AF%95%E9%AA%8C%E5%AE%A4";
		String s="http://d.wanfangdata.com.cn/Institution_csiS17981.aspx";
		//String  s="http://d.wanfangdata.com.cn/Institution/csiN07926?transaction=%7b%22ExtraData%22%3a%5b%5d%2c%22IsCache%22%3afalse%2c%22Transaction%22%3a%7b%22DateTime%22%3a%22%5c%2fDate(1462290044562%2b0800)%5c%2f%22%2c%22Id%22%3a%222efd727a-f87d-4f63-aa38-a5fb018637fb%22%2c%22Memo%22%3anull%2c%22ProductDetail%22%3a%22Institution_csiN03555%22%2c%22SessionId%22%3a%2265e10e76-83ee-48ac-8f19-6afc42277650%22%2c%22Signature%22%3a%22t%2biwEX4JUnlMIwOGIJuNUmzKSkDkckcZkyemc6o4VYcPz5%5c%2f4JIP0FrohC%5c%2fd1eGm%2b%22%2c%22TransferIn%22%3a%7b%22AccountType%22%3a%22Income%22%2c%22Key%22%3a%22InstitutionDigest%22%7d%2c%22TransferOut%22%3a%7b%22AccountType%22%3a%22GTimeLimit%22%2c%22Key%22%3a%22bjhkht%22%7d%2c%22Turnover%22%3a1.00000%2c%22User%22%3a%7b%22AccountType%22%3a%22Person%22%2c%22Key%22%3a%22kejso%22%7d%2c%22UserIP%22%3a%22124.207.188.68%22%7d%2c%22TransferOutAccountsStatus%22%3a%5b%5d%7d";
		//String s="http://d.wanfangdata.com.cn/Institution/csiN03555?transaction=%7b%22ExtraData%22%3a%5b%5d%2c%22IsCache%22%3afalse%2c%22Transaction%22%3a%7b%22DateTime%22%3a%22%5c%2fDate(1462369071261%2b0800)%5c%2f%22%2c%22Id%22%3a%221fc47805-22be-44aa-87ba-a5fc01647764%22%2c%22Memo%22%3anull%2c%22ProductDetail%22%3a%22Institution_csiN03555%22%2c%22SessionId%22%3a%22f7dd327e-bee7-405b-b513-d6707c951604%22%2c%22Signature%22%3a%22HTSdpyxZSz7BNPVjAUaJUZoEw%2bBcQjGyhEHxLhXB0E9e7mC0TQMAQIzxMiThToTn%22%2c%22TransferIn%22%3a%7b%22AccountType%22%3a%22Income%22%2c%22Key%22%3a%22InstitutionDigest%22%7d%2c%22TransferOut%22%3a%7b%22AccountType%22%3a%22GTimeLimit%22%2c%22Key%22%3a%22bjhkht%22%7d%2c%22Turnover%22%3a1.00000%2c%22User%22%3a%7b%22AccountType%22%3a%22Person%22%2c%22Key%22%3a%22kejso%22%7d%2c%22UserIP%22%3a%22124.207.188.68%22%7d%2c%22TransferOutAccountsStatus%22%3a%5b%5d%7d";
		String s2="https://api.ipify.org/?format=json";
		List<String> url = new ArrayList<String>();
		url.clear();
		
		url.add(s);
		//url.add(s2);
		
		Spider.create(this).startUrls(url).run(); 
	}

	@Override
	public Site getSite() {
		site = Site.me().setSleepTime(5000).setRetryTimes(5).setCycleRetryTimes(3).setTimeOut(60000);
		site.setCharset("utf8").setUserAgent(Config.Spider_Google_userAgent1);
		site.setHttpProxy(new HttpHost("14.18.236.100",80));
		
		//add cookie
		//site.addCookie("WFKS.Auth.IsAutoLogin", "");
		//site.addCookie("Hm_lpvt_f5e6bd27352a71a202024e821056162b", "1461852570");
		//site.addCookie("WFKS.Auth","%7b%22Context%22%3a%7b%22AccountIds%22%3a%5b%22Group.bjhkht%22%2c%22GTimeLimit.bjhkht%22%2c%22IsticBalanceLimit.bjhkht%22%5d%2c%22Data%22%3a%5b%7b%22Key%22%3a%22Group.bjhkht.DisplayName%22%2c%22Value%22%3a%22%e5%8c%97%e4%ba%ac%e8%88%aa%e7%a9%ba%e8%88%aa%e5%a4%a9%e5%a4%a7%e5%ad%a6%22%7d%5d%2c%22SessionId%22%3a%22366a2944-8e5e-456f-8f54-7365d62955ee%22%2c%22Sign%22%3a%22qCsOYPDgaH5v4g6LIvXQiWC40WN0G44dw3B%2bLDIC5n97Ij1%2bKJpQOLuC6J0qSYMA%22%7d%2c%22LastUpdate%22%3a%222016-05-05T15%3a19%3a08Z%22%2c%22TicketSign%22%3a%22WyB2eojGZiI4YOWFcHxP1w%3d%3d%22%7d");
		//add header
		//site.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		/*
		site.addHeader("Accept-Encoding", "gzip, deflate, sdch");
		site.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		site.addHeader("Cache-Control", "no-cache");
		site.addHeader("Connection", "keep-alive");
		site.addHeader("Cookie", "WFKS.Auth.IsAutoLogin=; WFKS.Auth=%7b%22Context%22%3a%7b%22AccountIds%22%3a%5b%22Group.bjhkht%22%2c%22GTimeLimit.bjhkht%22%2c%22IsticBalanceLimit.bjhkht%22%5d%2c%22Data%22%3a%5b%7b%22Key%22%3a%22Group.bjhkht.DisplayName%22%2c%22Value%22%3a%22%e5%8c%97%e4%ba%ac%e8%88%aa%e7%a9%ba%e8%88%aa%e5%a4%a9%e5%a4%a7%e5%ad%a6%22%7d%5d%2c%22SessionId%22%3a%22366a2944-8e5e-456f-8f54-7365d62955ee%22%2c%22Sign%22%3a%22qCsOYPDgaH5v4g6LIvXQiWC40WN0G44dw3B%2bLDIC5n97Ij1%2bKJpQOLuC6J0qSYMA%22%7d%2c%22LastUpdate%22%3a%222016-05-05T15%3a19%3a08Z%22%2c%22TicketSign%22%3a%22WyB2eojGZiI4YOWFcHxP1w%3d%3d%22%7d");
		site.addHeader("Host", "d.wanfangdata.com.cn");
		site.addHeader("Pragma", "no-cache");
		site.addHeader("Upgrade-Insecure-Requests", "1");
		site.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36");
		*/
		return site;
	}

	@Override
	public void process(Page page) {
		
		System.out.println(page.getHtml().toString());
		
		String name=page.getHtml().xpath("//div[@class='section-baseinfo']/h1/text()").toString();
		
		System.out.println(name);
		
		List<String> content = page.getHtml().xpath("//div[@class='fixed-width baseinfo-feild institution-baseinfo-feild']/table/tbody/tr/td[@class='text']/allText()").all();
		
		for(String one:content)
		{
			System.out.println(one);
		}
		
		
	}
	
	public static void main(String[] args) throws IOException {
		
		new ExpertSpider().run();
		
	}

}
