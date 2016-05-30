package crawler;


import datatype.StrPair;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import util.Outputer;
import util.hzUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by hanzhe on 16-2-24.
 */
public class hzCrawlerThread implements Runnable {
    private static final Logger logger = hzUtil.getLogger();
    private final CrawlerTaskManager taskList;
    private final boolean hint;
    private final int id;
    private int PAUSE_start = 1000;

    public hzCrawlerThread(CrawlerTaskManager ctl, boolean hint, int id) {
        this.taskList = ctl;
        this.hint = hint;
        this.id = id;
    }

    public void run() {
        logger.info("线程" + id + "启动");
        hzUtil.pause(300);
        while(true){
            StrPair sp = taskList.getNext();
            if(sp == null || sp.s1 == null) break;
            if(!taskList.overwrite){
                File tarF = new File(sp.s2);
                if(tarF.exists() && tarF.isFile()) continue; //已经存在,不用抓取了
            }
            String url = sp.s1;
            String tarPath = sp.s2;
            try {
                Document doc = Jsoup.connect(url).data("query", "Java").userAgent("Mozilla").timeout(100000).ignoreHttpErrors(true).post();
                File file = new File(tarPath);
                file.getParentFile().mkdirs();
                Outputer o = new Outputer(tarPath);
                o.append(doc.outerHtml());
                o.close();
                if(hint) logger.info("线程" + id + "\t" + url + "\t" + tarPath + " get! cost:" + taskList.getTimeInfo());
                hzUtil.pause(300);
            } catch (IOException e) {
                e.printStackTrace();
                hzUtil.pause(10000);
            }
        }
        logger.info("线程 " + id + " 结束");
    }
}
