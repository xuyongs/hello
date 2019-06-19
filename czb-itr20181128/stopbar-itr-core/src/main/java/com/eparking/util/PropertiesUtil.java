package com.eparking.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
/**
 * 
 * @author Adios
 * @date 
 * @version
 */

public class PropertiesUtil {
	private static Properties prop = new Properties();
    /**
     * ���������ļ�
     * @param 
     * @param filepath
     * @return
     */
    public static Properties load(String filepath){
         InputStream in =null;
        try {
            in = new BufferedInputStream(new FileInputStream(filepath));
            prop.load(in);    
          
            return prop;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	if(!in.equals(null)){
        		try {
        			//�ر�
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
        return null;
}
}
