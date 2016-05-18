package cn.kejso.Tool.Proxy;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class SimpleHttpClient {

	//一个非常简易的httpclient，封装get/set cookie和传参数功能
	
	public static enum resultType {
		Cookie, Content
	};

	public static void main(String[] args) {
		
		Map<String, String> map = new HashMap<String, String> ();
		//测试时发现只要登录随意帐号就行，无需登录帐号和订单帐号对应
		map.put("user_email", "000000@1.cn");
		map.put("user_pwd", "aaa__1");
		String re1 = httpPost("http://proxy.mimvp.com/user_login_check.php", null, map, resultType.Cookie);
		String re2 = httpGet("http://proxy.mimvp.com/api/fetch.php?orderid=000000@1.cn", re1, resultType.Content);
		System.out.println(re1);
		System.out.println(re2);
	}
	
	public static String httpPost(String url, String cookie, Map<String, String> params, resultType type) {
		
		String result = null;
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);

		//准备表单数据
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		for (Entry<String, String> para : params.entrySet()) {
			formparams.add(new BasicNameValuePair(para.getKey(), para.getValue()));
		}
		
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			
			// Set-Cookie
			if (cookie != null && !cookie.isEmpty()) {
				httppost.setHeader("Cookie", cookie);
			}
			
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				if (type.equals(resultType.Content)) {
					HttpEntity entity = response.getEntity();
					if (entity!=null)
						result = EntityUtils.toString(entity);
				} else if (type.equals(resultType.Cookie)) {
					Header[] headers = response.getHeaders("Set-Cookie");
					if (headers.length>0) {
						result = headers[0].getValue();
					}
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public static String httpGet(String url, String cookie, resultType type) {

		String result = null;

		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(url);

			// Set-Cookie
			if (cookie != null && !cookie.isEmpty()) {
				httpget.setHeader("Cookie", cookie);
			}
			httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko");

			CloseableHttpResponse response = httpclient.execute(httpget);

			try {
				if (type.equals(resultType.Content)) {
					HttpEntity entity = response.getEntity();
					if (entity!=null)
						result = EntityUtils.toString(entity);
				} else if (type.equals(resultType.Cookie)) {
					Header[] headers = response.getHeaders("Set-Cookie");
					if (headers.length>0) {
						result = headers[0].getValue();
					}
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

}
