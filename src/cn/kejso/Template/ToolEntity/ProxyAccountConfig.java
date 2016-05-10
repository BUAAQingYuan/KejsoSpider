package cn.kejso.Template.ToolEntity;

import java.util.List;

public class ProxyAccountConfig {
	
	private List<String[]> accounts;

	public ProxyAccountConfig(List<String[]> accounts) {
		
		this.accounts = accounts;
	}
	
	public List<String[]> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<String[]> accounts) {
		this.accounts = accounts;
	}
	
//	@Override
//	public String toString() {
//		
//		String result = new String("");
//		
//		for (Tag tag : accounts) {
//			result += String.format("%s:%s\n", tag.getTagname(), tag.getTagValue());
//		}
//		
//		return result;
//	}
	
}
