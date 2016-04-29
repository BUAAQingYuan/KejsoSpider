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
		String s="http://d.g.wanfangdata.com.cn/Expert.aspx?ID=Expert_16307";
		List<String> url = new ArrayList<String>();
		url.clear();
		
		url.add(s);
		
		Spider.create(this).startUrls(url).run(); 
	}

	@Override
	public Site getSite() {
		site = Site.me().setSleepTime(1000).setRetryTimes(5).setCycleRetryTimes(3).setTimeOut(60000);
		site.setCharset("utf8").setUserAgent(Config.Spider_userAgent);
		//add cookie
		//site.addCookie("Hm_lvt_f5e6bd27352a71a202024e821056162b", "1461747241");
		//site.addCookie("Hm_lpvt_f5e6bd27352a71a202024e821056162b", "1461852570");
		//site.addCookie("WFKS.Auth","%7b%22Context%22%3a%7b%22AccountIds%22%3a%5b%5d%2c%22Data%22%3a%5b%5d%2c%22SessionId%22%3a%226156d731-caa3-46f3-b11b-996dcbc9a153%22%2c%22Sign%22%3a%22hi+authserv%22%7d%2c%22LastUpdate%22%3a%222016-04-28T14%3a08%3a22Z%22%2c%22TicketSign%22%3a%22KRwAEJnFgE7qVe7owFwv2g%3d%3d%22%7d");
		//add header
		//site.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		//site.addHeader("Accept-Encoding", "gzip, deflate, sdch");
		//site.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		//site.addHeader("Cache-Control", "no-cache");
		//site.addHeader("Connection", "keep-alive");
		//site.addHeader("Cookie", "Hm_lvt_f5e6bd27352a71a202024e821056162b=1461747241; Hm_lpvt_f5e6bd27352a71a202024e821056162b=1461852570; WFKS.Auth=%7b%22Context%22%3a%7b%22AccountIds%22%3a%5b%5d%2c%22Data%22%3a%5b%5d%2c%22SessionId%22%3a%226156d731-caa3-46f3-b11b-996dcbc9a153%22%2c%22Sign%22%3a%22hi+authserv%22%7d%2c%22LastUpdate%22%3a%222016-04-28T14%3a08%3a22Z%22%2c%22TicketSign%22%3a%22KRwAEJnFgE7qVe7owFwv2g%3d%3d%22%7d");
		//site.addHeader("Host", "d.wanfangdata.com.cn");
		//site.addHeader("Pragma", "no-cache");
		//site.addHeader("Upgrade-Insecure-Requests", "1");
		//site.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36");
		return site;
	}

	@Override
	public void process(Page page) {
		
		System.out.println(page.getHtml().toString());
		
	}
	
	public static void main(String[] args) throws IOException {
		
		new ExpertSpider().run();
		
	}

}
