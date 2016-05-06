package cn.kejso.Spider.Control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kejso.Config.Config;
import cn.kejso.Template.SpiderConf;
import cn.kejso.Template.ToolEntity.BaseConfig;
import cn.kejso.Tool.SpiderUtil;
import cn.kejso.Tool.SqlUtil;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.SpiderListener;

public class SqlCacheOnErrorListener implements SpiderListener {
	
	private static Logger logger = LoggerFactory.getLogger(SqlCacheOnErrorListener.class);
	
	private SqlSession session;

	private SpiderConf conf;

	public SqlCacheOnErrorListener(SpiderConf template) {
		//新建临时表
		this.conf = template;
		
		session=SpiderUtil.getSession();
		
		String exist=Config.CreateTable_statement;
		BaseConfig tmpconfig = template.getTempTableConfig();
		Map<String,Object> tmptable=new HashMap<String,Object>();
		tmptable.put("tableName", tmpconfig.getTablename());
		tmptable.put("uniqueField",tmpconfig.getUnique());
		tmptable.put("fields", tmpconfig.getFields());
		session.selectOne(exist, tmptable);
		
		logger.info("create table if table {} not exists.", tmpconfig.getTablename());
	}
	
	@Override
	public void onError(Request request) {
		
		SqlUtil.insertWrongItem(conf, request.getUrl());
		logger.info("fail to download page {} .", request.getUrl());

	}

	@Override
	public void onSuccess(Request request) {

	}

}
