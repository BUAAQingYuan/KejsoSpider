package cn.kejso.Spider.SpiderHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import cn.kejso.Tool.SpiderUtil;

public class ChangePagenumHandler implements BasicTableHandler {


	@Override
	public String handler(String tablename) {
		
		
		SqlSession session=SpiderUtil.getSession();
		String state="SqlMapper.UserHandlerMapper.getAllTargetField";
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("tablename", "keylabs");
		map.put("field", "url");
		
		List<Map> result = session.selectList(state,map);
		
		String regex="?transaction=%7b%22ExtraData%22%3a%5b%5d%2c%22IsCache%22%3afalse%2c%22Transaction%22%3a%7b%22DateTime%22%3a%22%5c%2fDate(1462290044562%2b0800)%5c%2f%22%2c%22Id%22%3a%222efd727a-f87d-4f63-aa38-a5fb018637fb%22%2c%22Memo%22%3anull%2c%22ProductDetail%22%3a%22Institution_csiN03555%22%2c%22SessionId%22%3a%2265e10e76-83ee-48ac-8f19-6afc42277650%22%2c%22Signature%22%3a%22t%2biwEX4JUnlMIwOGIJuNUmzKSkDkckcZkyemc6o4VYcPz5%5c%2f4JIP0FrohC%5c%2fd1eGm%2b%22%2c%22TransferIn%22%3a%7b%22AccountType%22%3a%22Income%22%2c%22Key%22%3a%22InstitutionDigest%22%7d%2c%22TransferOut%22%3a%7b%22AccountType%22%3a%22GTimeLimit%22%2c%22Key%22%3a%22bjhkht%22%7d%2c%22Turnover%22%3a1.00000%2c%22User%22%3a%7b%22AccountType%22%3a%22Person%22%2c%22Key%22%3a%22kejso%22%7d%2c%22UserIP%22%3a%22124.207.188.68%22%7d%2c%22TransferOutAccountsStatus%22%3a%5b%5d%7d";
		
		for(Map one:result)
		{
			String currenturl=(String) one.get("url");
			int currentid=(int)one.get("id");
			
			String update="SqlMapper.UserHandlerMapper.updateEntity";
			
			Map<String,Object> map2=new HashMap<String,Object>();
			map2.put("tablename", "keylabs");
			map2.put("field", "url");
			map2.put("fieldvalue",currenturl.replaceFirst(".aspx", regex));
			map2.put("id",String.valueOf(currentid));
			
			session.update(update, map2);
		}
		
		session.commit();
		return tablename;
	}
	
	public static void main(String[] args){
		
		new ChangePagenumHandler().handler(null);
	}
	

}
