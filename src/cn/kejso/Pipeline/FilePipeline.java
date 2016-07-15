package cn.kejso.Pipeline;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kejso.Config.Config;
import cn.kejso.Template.SpiderConf;
import cn.kejso.Template.ToolEntity.BaseConfig;
import cn.kejso.Tool.FileUtil;
import cn.kejso.Tool.SpiderUtil;
import cn.kejso.Tool.SqlUtil;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

//输出爬取结果到文件
public class FilePipeline implements Pipeline {

	private SpiderConf  template;
	private static Logger logger = LoggerFactory.getLogger(FilePipeline.class);
	//计数
	private int  count=0;
	
	public FilePipeline(SpiderConf  template)
	{
		this.template=template;
		
		BaseConfig config=template.getConfig();
		
		//新建文件
		String filename=config.getStorefile();
		
		try{
			File file=new File(filename); 
			if(!file.exists()) 
				file.createNewFile(); 
		}catch(Exception e){
			System.out.print("创建失败");
		} 
		
		
	}
	
	@Override
	public void process(ResultItems resultItems, Task task) {
		
		//判断存储类型
		if(resultItems.get(Config.PipeLine_Type).toString().equals(Config.PipeLine_TypeList))
		{
			InsertTypeList(resultItems,task);
		}
		else if(resultItems.get(Config.PipeLine_Type).toString().equals(Config.PipeLine_TypeOne))
		{
			InsertTypeOne(resultItems,task);
		}
		
		count++;	
		
	}
	
	//list类
	private void  InsertTypeList(ResultItems resultItems, Task task)
	{
		BaseConfig config=template.getConfig();
		
		List<Map<String,String>> urls=resultItems.get(Config.PipeLine_Entity);
		
		if(count%Config.Inertinfo_per_number==0)
			logger.info(FileUtil.getFileinsertInfo(config.getStorefile()),count);
		
		for(Map<String,String> one:urls)
		{
			//首先将Map转换为List
			List<String> result=SqlUtil.MapToListByFields(one, config.getFields());
			//构造字符串 一行一条记录
			String content="";
			for(String field:result)
			{
				content=content+" "+field;
			}
			
			FileUtil.PrintURL(config.getStorefile(), content);
		}
		
	}	
		
	//存储单个实体
	private void  InsertTypeOne(ResultItems resultItems, Task task) 
	{	
		BaseConfig config= template.getConfig();
		
		Map<String,String> result=resultItems.get(Config.PipeLine_Entity);
		
		if(count%Config.Inertinfo_per_number==0)
			logger.info(FileUtil.getFileinsertInfo(config.getStorefile()),count);
		
		List<String> fields=SqlUtil.MapToListByFields(result,config.getFields());
		String content="";
		for(String field:fields)
		{
			content=content+" "+field;
		}
		
		FileUtil.PrintURL(config.getStorefile(), content);
	}	
	

}
