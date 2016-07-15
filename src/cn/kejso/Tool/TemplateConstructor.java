package cn.kejso.Tool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import cn.kejso.Template.SpiderConf;
import cn.kejso.Template.RecoverConfig;
import cn.kejso.Template.RecoverConfig.RecoverMode;
import cn.kejso.Template.ToolEntity.BaseConfig;
import cn.kejso.Template.ToolEntity.ContentConfig;
import cn.kejso.Template.ToolEntity.FileContentTag;
import cn.kejso.Template.ToolEntity.GlobalConfig;
import cn.kejso.Template.ToolEntity.ListConfig;
import cn.kejso.Template.ToolEntity.PreConfig;
import cn.kejso.Template.ToolEntity.ProxyAccountConfig;
import cn.kejso.Template.ToolEntity.Tag;

//模板构造
public class TemplateConstructor {

	// 解析ListConfig
	public static ListConfig getListConfig(HierarchicalConfiguration sub) {
		// ListConfig
		String listurl = sub.getString("ListUrl");
		List<String> starturls = new ArrayList<String>();
		boolean pageenable = sub.getBoolean("PageEnable");
		if (pageenable) {
			int pagebegin = sub.getInt("PageStart");
			int pageend = sub.getInt("PageEnd");
			starturls = SpiderUtil.getListUrls(listurl, pagebegin, pageend);
		} else {
			starturls.add(listurl);
		}

		String listvalue = sub.getString("ListValue");

		String tablename = sub.getString("SqlTable");
		
		String storefile = sub.getString("StoreFile");
		
		String fields = sub.getString("TableFields");

		String unique = sub.getString("UniqueField");

		List tags = sub.configurationsAt("ListTag");
		List<Tag> listtags = new ArrayList<Tag>();
		for (Iterator it = tags.iterator(); it.hasNext();) {
			HierarchicalConfiguration one = (HierarchicalConfiguration) it.next();
			listtags.add(new Tag(one.getString("TagName"), one.getString("TagValue")));
		}

		List consttags = sub.configurationsAt("ConstTag");
		List<Tag> listtags2 = new ArrayList<Tag>();
		for (Iterator it = consttags.iterator(); it.hasNext();) {
			HierarchicalConfiguration one = (HierarchicalConfiguration) it.next();
			listtags2.add(new Tag(one.getString("TagName"), one.getString("TagValue")));
		}

		List othertags = sub.configurationsAt("OtherTag");
		List<Tag> listtags3 = new ArrayList<Tag>();
		for (Iterator it = othertags.iterator(); it.hasNext();) {
			HierarchicalConfiguration one = (HierarchicalConfiguration) it.next();
			listtags3.add(new Tag(one.getString("TagName"), one.getString("TagValue")));
		}

		return new ListConfig(starturls, listvalue, tablename,storefile, listtags, SpiderUtil.getMapFields(fields), unique,
				listtags2, listtags3);

	}

	// 解析ContentConfig
	/*
	 * Tag种类:ContentTag(页面项)、ContentList.Field(Map项)
	 */
	public static ContentConfig getContentConfig(HierarchicalConfiguration sub) {
		String contenttable = sub.getString("ContentTable");
		
		String storefile = sub.getString("StoreFile");
		
		String fields = sub.getString("TableFields");
		String unique = sub.getString("UniqueField");

		List contentitem = sub.configurationsAt("ContentTag");

		List<Tag> contenttags = new ArrayList<Tag>();
		for (Iterator it = contentitem.iterator(); it.hasNext();) {
			HierarchicalConfiguration one = (HierarchicalConfiguration) it.next();
			contenttags.add(new Tag(one.getString("TagName"), one.getString("TagValue")));
		}

		String multicontentseparator = sub.getString("MultiContentSeparator");
		List multicontentitem = sub.configurationsAt("MultiContentTag");
		List<Tag> multicontenttags = new ArrayList<Tag>();
		for (Iterator it = multicontentitem.iterator(); it.hasNext();) {
			HierarchicalConfiguration one = (HierarchicalConfiguration) it.next();
			multicontenttags.add(new Tag(one.getString("TagName"), one.getString("TagValue")));
		}

		List filecontentitem = sub.configurationsAt("FileContent");
		List<FileContentTag> filecontenttags = new ArrayList<FileContentTag>();
		for (Iterator it = filecontentitem.iterator(); it.hasNext();) {
			HierarchicalConfiguration one = (HierarchicalConfiguration) it.next();
			filecontenttags.add(new FileContentTag(one.getString("SourceField"), one.getString("TargetField"),
					one.getString("SavePath"), one.getString("ForceExt")));
		}

		String mark = sub.getString("ContentList.Mark");
		String code = sub.getString("ContentList.Code");
		String field = sub.getString("ContentList.Field");
		String markfield = sub.getString("ContentList.MarkField");

		String pageUrlField = sub.getString("PageUrlField");
		String notNullField = sub.getString("NotNullField");

		// consttag
		List consttags = sub.configurationsAt("ConstTag");
		List<Tag> listtags2 = new ArrayList<Tag>();
		for (Iterator it = consttags.iterator(); it.hasNext();) {
			HierarchicalConfiguration one = (HierarchicalConfiguration) it.next();
			listtags2.add(new Tag(one.getString("TagName"), one.getString("TagValue")));
		}
		
		return new ContentConfig(contenttags, contenttable,storefile, mark, code, SpiderUtil.getMapFields(field),
				SpiderUtil.getMapFields(markfield), SpiderUtil.getMapFields(fields), unique, pageUrlField, notNullField,
				listtags2, multicontentseparator, multicontenttags, filecontenttags);
	}

	/*
	 * 读取配置文件中的config 参数cls为config的类型 参数name为config的name属性值
	 */
	public static BaseConfig getDetailConfig(XMLConfiguration xml, String cls, String name) {
		BaseConfig base = null;

		List items = xml.configurationsAt(cls);

		// 找到指定的name
		for (Iterator it = items.iterator(); it.hasNext();) {
			HierarchicalConfiguration sub = (HierarchicalConfiguration) it.next();
			if (sub.getString("[@name]").equals(name)) {
				// 解析
				if (cls.equals("ListConfig")) {
					base = getListConfig(sub);
				} else if (cls.equals("ContentConfig")) {
					base = getContentConfig(sub);
				} else if (cls.equals("PreConfig")) {
					base = getPreConfig(sub);
				}
			}
		}

		return base;
	}

	public static RecoverConfig getRecoverConfig(HierarchicalConfiguration sub) {

		RecoverConfig config = new RecoverConfig();

		boolean enable = sub.getBoolean("recover[@enable]", false);
		config.setEnable(enable);

		if (enable) {
			config.setMode(RecoverMode.valueOf(sub.getString("recover[@mode]")));
			config.setRef(sub.getString("recover[@ref]"));
			config.setField(sub.getString("recover[@field]"));
		}

		return config;
	}

	// 读取配置的通用接口
	public static List<SpiderConf> getSpiderConf(String configfile) {
		XMLConfiguration xml = null;
		try {
			xml = new XMLConfiguration(configfile);
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<SpiderConf> spiders = new ArrayList<SpiderConf>();

		List items = xml.configurationsAt("Spiders.Spider");
		// List<Tag> contenttags=new ArrayList<Tag>();
		for (Iterator it = items.iterator(); it.hasNext();) {
			// 解析每个Spider配置
			HierarchicalConfiguration sub = (HierarchicalConfiguration) it.next();

			SpiderConf spider = new SpiderConf();
			spider.setName(sub.getString("[@name]"));
			spider.setCname(sub.getString("[@cname]"));
			// spider.setDynamic(sub.getBoolean("[@dynamic]"));
			// spider.setCasperjs(sub.getString("[@casperjs]"));

			String confclass = sub.getString("conf-def[@class]");
			String confname = sub.getString("conf-def[@name]");
			spider.setConfig(getDetailConfig(xml, confclass, confname));

			spider.setRecoverConfig(getRecoverConfig(sub));

			spider.setDependname(sub.getString("depend[@ref]"));
			spider.setDependField(sub.getString("depend[@field]"));
			spider.setUrlfilter(sub.getString("depend[@filter]"));
			
			spider.setBeforehandler(sub.getString("before-table-handler[@func]"));
			spider.setAfterhandler(sub.getString("after-table-handler[@func]"));

			spiders.add(spider);
		}

		return spiders;

	}

	// 获得全局配置
	public static GlobalConfig getGlobalConf(String configfile) {
		XMLConfiguration xml = null;
		try {
			xml = new XMLConfiguration(configfile);
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		GlobalConfig global = new GlobalConfig();
		
		GlobalConfig.setTaskname(xml.getString("TaskName"));
		GlobalConfig.setThreadnum(xml.getInt("Thread"));
		GlobalConfig.setEnableproxy(xml.getBoolean("ProxyEnable"));
		GlobalConfig.setCycleTimes(xml.getInt("CycleTimes"));
		GlobalConfig.setCasperjsPath(xml.getString("CasperJsPath"));
		GlobalConfig.setSleeptime(xml.getInt("SleepTime", 1000));
		GlobalConfig.setMoresleeptime(xml.getBoolean("MoreSleepTime", false));

		return global;
	}

	// 获得PreConfig
	public static PreConfig getPreConfig(HierarchicalConfiguration sub) {
		String preurl = sub.getString("PreUrl");

		String prevalue = sub.getString("PreValue");

		String tablename = sub.getString("SqlTable");

		String fields = sub.getString("TableFields");

		String unique = sub.getString("UniqueField");

		return new PreConfig(preurl, prevalue, tablename, SpiderUtil.getMapFields(fields), unique);
	}

	public static ProxyAccountConfig getProxyAccountConfig(String configfile) {
		
		XMLConfiguration xml = null;
		try {
			xml = new XMLConfiguration(configfile);
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<String[]> accounts = new ArrayList<String[]>();

		List<HierarchicalConfiguration> items = xml.configurationsAt("ProxyAccount");
		
		for (Iterator<HierarchicalConfiguration> it = items.iterator(); it.hasNext();) {
			HierarchicalConfiguration item = it.next();
			accounts.add(new String[] {item.getString("Account"), item.getString("Password")});
		}
		
		return new ProxyAccountConfig(accounts);
	}

	public static void main(String[] args) {
		// TemplateConstructor.getListAndContentTemplate("configs\\wanfangpaper.xml");
//		System.out.println(getProxyAccountConfig("configs\\MimvpProxy\\account.xml"));
		// TemplateConstructor.getSpiderConf("configs\\wanfangpaper.xml");

	}
}
