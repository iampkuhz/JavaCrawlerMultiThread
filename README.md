# Java 多线程 爬虫

使用 Jsoup 实现的多线程爬虫。

代码结构如下

```
├── JavaCrawlerMultiThread.iml
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   ├── crawler
    │   │   │   ├── CrawlerTaskManager.java (主类)
    │   │   │   └── hzCrawlerThread.java (爬虫线程)
    │   │   ├── datatype
    │   │   │   └── StrPair.java (保存待抓取的链接的数据结构）
    │   │   └── util
    │   │       ├── hzTimeUtil.java (输出耗时信息）
    │   │       ├── hzUtil.java 
    │   │       └── Outputer.java (将爬虫抓取的结果写到文件）
    │   └── resources
    │       └── log4j.properties
    └── test
        └── java
            └── DemoCrawler.java (样例程序）
```

输入的文件格式：
> 每行2列，按`\t`隔开，第一个是url，第二个要存储的文件路径
样例如下:

```
http://baike.baidu.com/subview/9242/5245915.htm	5245915.html
http://baike.baidu.com/subview/43912/8770055.htm	data/8770055.html
http://baike.baidu.com/view/5479.htm	data/5479.htm
http://baike.baidu.com/item/%E5%BC%A0%E7%AB%A5/17896425#viewPageContent	data/张童.html
http://baike.baidu.com/view/1615798.htm	data/路卡利欧
```
