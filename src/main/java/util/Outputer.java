package util;

import org.apache.log4j.Logger;

import java.io.*;

public class Outputer {
	private static final Logger logger = hzUtil.getLogger();
	public static void writeStr2File(String body, String tarPath){
		Outputer o = new Outputer(tarPath);
		o.append(body);
		o.close();
	}


	private String Path;
	private StringBuffer sb;
	private int lineNr = 0;
	private OutputStreamWriter osw;
	private String encoding = "utf-8";
	private int batchSize = 1000;
	private String c = Outputer.class.getSimpleName();

	public Outputer(String path, String encoding, boolean deleteF)
	{
		this.encoding = encoding;
		init(path, deleteF, encoding);
	}

	public Outputer(String path, boolean deleteF)
	{
		init(path, deleteF, "utf-8");
	}
	public Outputer(String path){
		init(path, true, "utf-8");
	}

	public void setBatchSize(int sz){
		if(sz < 1)
			logger.info("batchsize error" + sz);
		else batchSize = sz;
	}

	private void init(String path, boolean deleteF, String encoding2) {
		Path = path;
		sb = new StringBuffer();
		if(deleteF) deleteFile();
		File file=new File(path);
		if(!file.exists()){
			try {
				if(file.createNewFile() == false)
					logger.info("path not exist: " + path);
				path = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			osw = new OutputStreamWriter(new FileOutputStream(Path, true), encoding);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			osw = null;
		}
	}
	
	public void append(String oneLineWithEnter){
		sb.append(oneLineWithEnter);
		lineNr ++;
		if(lineNr % batchSize == 0){
			add2File();
			sb.setLength(0);
		}
	}
	
	public void close(){
		add2File();
		sb.setLength(0);
		try {
			if(osw != null)
				osw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int recordNr(){
		return lineNr;
	}
	
	public void deleteFile(){
		deleteFile(Path, false);
	}

	public void add2File() {
		if(sb.length() < 1 || Path == null)
			return;
		if(this.encoding.equals("utf-8") == false)
		{
			try {
				osw.write(sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		FileWriter fw;
		try {
			fw = new FileWriter(Path, true);
			fw.append(sb.toString());
			fw.close();
			sb.setLength(0);
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("Outputer: write error");
		} 
	}

	private static void deleteFile(String path, boolean info) {
		try{
			File file=new File(path);
			if(!file.exists() && info){
				logger.info("file " + file.getName() + " not exist, can't delete!");
			}
			else if (file.isFile() == true){
				String name = file.getName();
				file.delete();
				if(info) logger.info("delete " + name + " successfully!");
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	public String getTarPath()
	{
		return Path;
	}

	/**
	 * 将现在的buffer写到文件中，不管现在的buffer多大
	 */
	public void forceWriteBuffer() {
		add2File();
		sb.setLength(0);;
	}

}
