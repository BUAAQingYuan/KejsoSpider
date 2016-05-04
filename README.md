###KejsoSpider

####配置文件说明

##### 全局配置

TaskName  任务名称
Thread    默认线程数
ProxyEnable 是否开启代理

##### 组件配置

ListConfig 列表页面配置

----------ListUrl  列表页url模板
----------PageEnable 开启多页
----------PageStart  初始页码
----------PageEnd    结束页码

如果开启多页，则列表页会包含一个url队列。

----------ListValue  列表项定位，目前只支持XPATH。(CSS选择器扩展)
----------SqlTable   存储到数据库的表名。(索引库扩展)


  