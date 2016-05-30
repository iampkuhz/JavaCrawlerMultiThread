import crawler.CrawlerTaskManager;
import datatype.StrPair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by hanzhe on 16-5-30.
 */
public class DemoCrawler {

    public static void main(String [] args) throws IOException {

        //CrawlerTaskManager manager = new CrawlerTaskManager("/home/hanzhe/tmp/taskList");

        // 程序中初始化，如果目标文件存在，则跳过不抓取
        //ArrayList<StrPair> pair = new ArrayList<StrPair>();
        //pair.add(new StrPair("https://github.com/", "githubHome.html"));
        //CrawlerTaskManager manager = new CrawlerTaskManager(pair, System.currentTimeMillis(), false);

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String srcPath = classLoader.getResource("taskList").getFile();
        CrawlerTaskManager manager = new CrawlerTaskManager(srcPath);

        manager.startSpider(4, true);
    }
}
