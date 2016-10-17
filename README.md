##KejsoSpider

抽取常见的网页结构数据。

###设计说明

Pipeline 以mysql为基本的pipeline(MysqlPipeline)，pipeline队列中可以添加FilePipeline、SolrPipeline。



###常见网页结构和爬取模式


###增量策略

增量策略在recover中配置。

目前共有三种增量策略。

1. simple

  从当前spider对应的数据表的最后一条记录开始，获得depend table中剩余的url，可能包含上次没有爬到的url。
  
  ```
  <div  align="center">   
       <img src="https://github.com/BUAANLSDE/KejsoSpider/raw/master/doc/images/simple.png" width = "100" height = "80" alt="simple策略" align=center />
  </div>
  
  ```
  
  ![simple 策略](https://github.com/BUAANLSDE/KejsoSpider/raw/master/doc/images/simple.png)
  
2. delta

  对当前spider对应的数据表和depend table作对比，获得相差的url。
  
  ![delta 策略](https://github.com/BUAANLSDE/KejsoSpider/raw/master/doc/images/delta.png)
  
3. listdelta

  只对depend table中新增的部分抓取，更新到当前spider对应的数据表中。
  
  ![listdelta 策略](https://github.com/BUAANLSDE/KejsoSpider/raw/master/doc/images/listdelta.png)


###配置文件说明

##### 全局配置

TaskName  任务名称

Thread    默认线程数

ProxyEnable 是否开启代理

CycleTimes 爬虫循环重试次数

SleepTime  抓取间隔

MoreSleepTime  [True | False]  重试时是否增加抓取间隔
 

##### 组件配置

**ListConfig 列表页面配置**

  ListUrl      列表页url模板
          
 PageEnable   开启多页.如果开启多页,则列表页会包含一个url队列
          
  PageStart    初始页码
          
  PageEnd      结束页码
          
  StoreFile     
          
  ListValue  列表项定位，目前只支持XPATH。(CSS选择器扩展)

  SqlTable   存储到数据库的表名。(索引库扩展)

  TableFields  数据表的字段，默认包含id为主键

  UniqueField  unique索引字段，用来标识记录的唯一性，重复的记录不再存储

  ListTag    网页动态字段定位

       ——TagName  对应的字段
        
       ——TagValue 字段定位
         
  ConstTag   常量字段


**ContentConfig 内容面配置**

  ContentTable  表名
   
  TableFields  数据表字段

  UniqueField  唯一索引字段

  NotNullField 非空字段(允许多个)

  PageUrlField 内容页url字段

  ContentTag   网页动态字段定位

      ——TagName  对应的字段

      ——TagValue 字段定位               
  
  ContentList  map字段映射
                
      ——Field  对应字段列表
                
      ——MarkField 对应页面标记

      ——Mark 页面标记定位
    
      ——Code 页面内容定位


**Spiders 爬虫链配置**
          
Spider  爬虫配置 。

        @name 属性定义Spider名称，供其他的Spider引用。
        
        @cname , 爬虫别称。
    
conf-def  爬虫依赖的配置。

        @class 属性指定类别
        
        @name 属性指定特定名称

depend  当前爬虫所依赖的爬虫链中的上一个爬虫。

        @ref属性引用所依赖爬虫的name，也可以是数据表名。
        
        @field 属性通常指定依赖爬虫提供的url字段。
        
        @filter属性指定对field过滤的方式。

recover 爬虫的增量策略。enable="true"开启 ; mode属性指定选择的策略，有三种策略可供选择。field和url是前后爬虫对应的字段。
                
before-table-handler  启动当前爬虫之前使用func处理数据表(当前爬虫对应的数据表)。
                
after-table-handler   启动当前爬虫之前使用func处理数据表(当前爬虫对应的数据表)。



###使用说明

Usage: java -jar BuildSpiderChain.jar  configfile  jdbc-config [fetch | retry | continue]

使用JConsole连接,查看运行情况。

Usage: java -jar RunSpiderChainForMagazineFromFile.jar sourceurl  configfile  jdbc-config 

