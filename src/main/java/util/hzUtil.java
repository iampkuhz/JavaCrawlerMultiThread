package util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * 主要的函数文件
 * Created by hanzhe on 16-5-28.
 */
public class hzUtil {
    private static final Logger logger = hzUtil.getLogger();

    /**
     * 指定编码， 获取文件的 BufferedReader
     * @param path
     * @param encoding
     * @return
     */
    public static BufferedReader getBufferedReader(String path, String encoding) {
        try{
            File file = new File(path);
            if(file.exists() == false){
                logger.info("read file not exist:" + path);
                return null;
            }
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), encoding));
            return reader;
        }catch( Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 程序暂停
     * @param milliseconds
     */
    public static void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 得到一个Logger
     * @return Logger
     */
    public static Logger getLogger() {
        return LogManager.getRootLogger();
    }


}
