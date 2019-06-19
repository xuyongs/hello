package com.agent.wechat.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
		
	private static PropertiesUtil instance;
	 
	   private Properties properties = new Properties();
	 
	   public synchronized static PropertiesUtil getInstance() {
	      if(null == instance) {
	         instance = new PropertiesUtil();
	      }
	      return instance;
	   }
	 
	   /**
	    * 获取配置
	    * @param fileUrl
	    * @return
	    */
	   public Properties getProperties(String fileUrl){
	      readProperties(fileUrl);
	      return properties;
	   }
	 
	   /**
	    * 读取properties的全部信息
	    * @param filePath
	    */
	   public void readProperties(String filePath){
	      InputStream in = null;
	      try{
	         in = new BufferedInputStream(new FileInputStream(filePath));
	         properties.load(in);
	      }catch(Exception e){
	         e.printStackTrace();
	      }finally{
	         try{
	            if(in != null)
	               in.close();
	         }catch(IOException e){
	            e.printStackTrace();
	         }
	      }
	   }

}
