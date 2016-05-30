package crawler;


import datatype.StrPair;
import org.apache.log4j.Logger;
import util.hzTimeUtil;
import util.hzUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 分配抓取列表给每个线程
 * Created by hanzhe on 16-2-24.
 */
public class CrawlerTaskManager {
    private static final Logger logger = hzUtil.getLogger();
    private ArrayList<StrPair> urls;
    private long t0;
    public  boolean overwrite;
    private int index;

    public CrawlerTaskManager(String taskListPath) throws IOException {
        BufferedReader br = hzUtil.getBufferedReader(taskListPath, "UTF-8");
        this.urls = new ArrayList<StrPair>();
        String line;
        while((line = br.readLine()) != null){
            String [] ss = line.split("\t");
            if(ss.length != 2){
                logger.error("数据不是两列:" + ss.length + "\t" + line);
                continue;
            }
            urls.add(new StrPair(ss[0], ss[1]));
        }
        init(urls, System.currentTimeMillis(), true);
    }

    public CrawlerTaskManager(ArrayList<StrPair> urls){
        init(urls, System.currentTimeMillis(), true);
    }

    /**
     *
     * @param urls
     * @param t0 表示开始时的时间，用来计算程序运行耗时
     */
    public CrawlerTaskManager(ArrayList<StrPair> urls, long t0) {
        init(urls, t0, true);
    }

    public CrawlerTaskManager(ArrayList<StrPair> urls, long t0, boolean overwrite) {
        init(urls, t0, true);
    }

    private void init(ArrayList<StrPair> urls, long t0, boolean overwrite) {
        this.overwrite = overwrite;
        this.urls = urls;
        this.index = 0;
        this.t0 = t0;
        logger.info(urls.size() + "个网址待爬取");
    }

    /**
     * 指定线程数量，开始抓取
     * @param threadNr
     * @param outputLog
     */
    public void startSpider(int threadNr, boolean outputLog){
        for(int i = 0 ;i < threadNr; i ++)
            new Thread(new hzCrawlerThread(this, outputLog, i+1)).start();
    }

    /**
     * 线程使用的函数, 用来判断是否需要
     * @return
     */
    public StrPair getNext() {
        if(index < urls.size()) return urls.get(index++);
        return null;
    }

    public String getTimeInfo() {
        return "get " + index + ", cost:" + hzTimeUtil.millisToShortDHMS(System.currentTimeMillis()-t0);
    }
}
