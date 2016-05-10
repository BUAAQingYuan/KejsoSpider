package cn.kejso.Tool.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MimvpProxy {

	// 说明：在某一段时间内返回的数据是固定的，不管登录什么帐号。故只返回得到结果的一项
	public static List<String[]> getProxy(List<String[]> user_info) {

		for (String[] info : user_info) {
			List<String[]> result = getProxy(info[0], info[1]);
			if (!result.isEmpty()) {
				return result;
			}
		}

		return new ArrayList<String[]>();
	}

	public static List<String[]> getProxy(String user_email, String user_pwd) {

		String content = getProxyContent(user_email, user_pwd);
		List<String> groupContent = parseContent(content);
		List<String[]> proxy = convert2List(groupContent);

		return proxy;
	}

	public static String getProxyContent(String user_email, String user_pwd) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("user_email", user_email);
		map.put("user_pwd", user_pwd);

		String cookie = SimpleHttpClient.httpPost("http://proxy.mimvp.com/user_login_check.php", null, map,
				SimpleHttpClient.resultType.Cookie);

		String content = SimpleHttpClient.httpGet("http://proxy.mimvp.com/api/fetch.php?orderid=" + user_email, cookie,
				SimpleHttpClient.resultType.Content);

		return content;
	}

	private static List<String> parseContent(String content) {

		String trimContent = content.replace("\n", " ").replace("\r", "").replace("\t", "").trim();
		String[] set = trimContent.split(" ");
		List<String> result = Arrays.asList(set);

		return result;
	}

	private static List<String[]> convert2List(List<String> content) {

		List<String[]> list = new ArrayList<String[]>();

		if (content.size() > 0) {
			for (Iterator<String> it = content.iterator(); it.hasNext();) {
				String[] strArr = convert2StringArr(it.next());
				if (strArr != null) {
					list.add(strArr);
				} else {
					return list;
				}
			}
		}

		return list;
	}

	private static String[] convert2StringArr(String content) {

		String[] piece = content.split(":");
		if (piece.length == 2) {
			try {
				Integer.parseInt(piece[1]);
			} catch (NumberFormatException e) {
				return null;
			}
			return piece;
		} else {
			return null;
		}
	}

	public static String register(String user_email, String user_pwd, String user_rcode, String cookie) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("forurl", "login.php");
		map.put("user_email", user_email);
		map.put("user_pwd", user_pwd);

		String result = SimpleHttpClient.httpPost("http://mimvp.com/lib/user_regist_check.function.php", cookie, map,
				SimpleHttpClient.resultType.Content);

		return result;
	}

	public static void register(List<String[]> user_info, String user_rcode, String cookie) {

		for (String[] info : user_info) {
			String rvalue = register(info[0], info[1], user_rcode, cookie);
			System.out.format("regist %s: %s\n", info[0], rvalue);
		}

	}

	public static void main(String[] args) {

		//valid only for a short period
		String user_rcode = "p7kp2";
		String cookie = "PHPSESSID=8fomh6n9pdnfdvgo6ef8f0sem4";
		String pwd = "aaa__1";
		
		List<String[]> user_info = new ArrayList<String[]> ();
		
		for (int i = 51; i <= 51; i++) {
			user_info.add(new String[] {String.format("%06d@1.cn", i), pwd} );
		}
		
		MimvpProxy.register(user_info, user_rcode, cookie);
	}
}
