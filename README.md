##KejsoSpider

抽取常见的网页结构数据。

###设计说明

Pipeline 以mysql为基本的pipeline(MysqlPipeline)，pipeline队列中可以添加FilePipeline、SolrPipeline。



###常见网页结构和爬取模式



###配置文件说明

##### 全局配置

TaskName  任务名称

Thread    默认线程数

ProxyEnable 是否开启代理

##### 组件配置

**ListConfig 列表页面配置**

          ListUrl  列表页url模板

          PageEnable 开启多页

          PageStart  初始页码

          PageEnd    结束页码

如果开启多页，则列表页会包含一个url队列。

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


**Spiders 爬虫链配置**
          
          Spider  爬虫配置
    