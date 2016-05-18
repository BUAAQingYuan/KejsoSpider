package cn.kejso.PageProcess.ProcessHandler;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;
import cn.kejso.Config.Config;
import cn.kejso.Template.ListAndContentTemplate;
import cn.kejso.Template.SpiderConf;
import cn.kejso.Template.ToolEntity.ContentConfig;
import cn.kejso.Template.ToolEntity.Tag;
import cn.kejso.Tool.FileUtil;
import cn.kejso.Tool.SpiderUtil;
import cn.kejso.Tool.SqlUtil;

//处理内容页
public class ContentMapProcessHandler {

	private static Logger logger = LoggerFactory.getLogger(ContentMapProcessHandler.class);

	public Map<String, String> processContentPage(Page page, SpiderConf template) {

		ContentConfig config = (ContentConfig) template.getConfig();

		// 页面项
		List<Tag> items = config.getTags();
		List<String> itemcontents = new ArrayList<String>();
		for (Tag one : items) {
			itemcontents.add(page.getHtml().xpath(one.getTagValue()).toString());
		}

		// map项
		List<String> marks = new ArrayList<String> ();
		if (config.getMark() != null && !config.getMark().isEmpty())
			marks = page.getHtml().xpath(config.getMark()).all();

		List<String> code = new ArrayList<String> ();
		if (config.getCode() != null && !config.getCode().isEmpty())
			code = page.getHtml().xpath(config.getCode()).all();
		
		List<String> attrs = config.getField();
		List<String> markfield = config.getMarkfield();

		// multicontent项
		List<Tag> multicontent = config.getMultiContentTag();
		String separator = config.getMultiContentSeparator();
		List<String> multicontentitems = new ArrayList<String>();
		for (Tag one : multicontent) {
			List<Selectable> nodes = page.getHtml().xpath(one.getTagValue()).nodes();
			String resultstr = new String();
			for (Iterator<Selectable> it = nodes.iterator(); it.hasNext();) {
				resultstr += it.next().toString();
				if (it.hasNext()) {
					resultstr += separator;
				}
			}
			multicontentitems.add(resultstr);
		}

		Map<String, String> result = new HashMap<String, String>();

		if (config.getPageUrlField() != null) {
			result.put(config.getPageUrlField(), page.getUrl().toString());
		}

		// 页面项
		for (int i = 0; i < items.size(); i++) {
			result.put(items.get(i).getTagname(), itemcontents.get(i));
		}
		
		//常量字段
		List<Tag> consts=config.getConsttags();
		for(int i=0;i<consts.size();i++)
		{
			result.put(consts.get(i).getTagname(), consts.get(i).getTagValue());
		}
		
		
		// multicontent项
		for (int i=0; i<multicontent.size(); i++) {
			result.put(multicontent.get(i).getTagname(), multicontentitems.get(i));
		}

		// map属性
		for (int i = 0; i < attrs.size(); i++) {
			// 得到对应的mark
			String currentmark = markfield.get(i);
			// 得到mark的配置i
			int pos = marks.indexOf(currentmark);
			if (pos != -1) {
				result.put(attrs.get(i), code.get(pos));
			}
		}

		String cacheFile = Config.Spider_ErrorDir + config.getTablename();

		// 如果该页面存在异常，或是抓取失败
		// 判断非空字段,如果非空字段为空，则判断为error，立即返回
		String notNullField = config.getNotNullField();
		if (notNullField != null && result.get(notNullField) == null) {

			// FileUtil.PrintURL(cacheFile, page.getUrl().toString());
			SqlUtil.insertWrongItem(template, page.getRequest().getUrl());
			logger.info("fail to process page {} .", page.getRequest().getUrl());
			page.setSkip(true);
			return result;
		}

		/*
		 * 判断抓取的动态字段是否为空，如果有常量字段要排除静态字段。如果没有抓取到内容，将其按照error url处理
		 */

		// 排除字段
		List<String> excludeFields = new ArrayList<String>();

		if (config.getPageUrlField() != null) {
			excludeFields.add(config.getPageUrlField());
		}

		if (config.getConsttags().size() > 0) {
			for (Tag one : config.getConsttags()) {
				excludeFields.add(one.getTagname());
			}
		}

		if (SpiderUtil.ResultIsNull(result, excludeFields)) {
//			FileUtil.PrintURL(cacheFile, page.getUrl().toString());
			SqlUtil.insertWrongItem(template, page.getRequest().getUrl());
			logger.info("process a null page {} .", page.getRequest().getUrl());
			page.setSkip(true);
		}

		return result;
	}
}
