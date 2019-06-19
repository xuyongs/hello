package com.agent.czb.service.utils;

import com.agent.czb.common.ParseStrExpUtils;
import org.apache.commons.lang3.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class URLUtils {
	public static final String FILES_PATH = "file.path";
	public static final String FILES_URL = "file.url";
	private static ResourceBundle res = ResourceBundle.getBundle("urls");
	private static 	Map<String,String> urlsMap = null;

	/**
	 *
	 * 获取urlMap
	 * @return
	 */
	public static Map<String,String> getUrlMap(){
		if(urlsMap != null && !urlsMap.isEmpty()){
			return urlsMap;
		}
		urlsMap= new HashMap<>();
		Enumeration e = res.getKeys();
		while(e.hasMoreElements()){
			String key = e.nextElement().toString();
			String value = get(key);
			urlsMap.put(key, value);
		}
		Set<String> strs = urlsMap.keySet();
		for (String key : strs) {
			String value = ParseStrExpUtils.process(urlsMap.get(key), urlsMap);
			urlsMap.put(key, value);
//			System.out.println(key + "---" + value);
		}
		return urlsMap;
	}

	public static String get(String key){
		return res.getString(key);
	}

	public static String getReqUri(String reqUrl){
		try {
			URL url  = new URL(reqUrl);
			/*System.out.println("getDefaultPort = "+url.getDefaultPort());
			System.out.println("getAuthority = "+url.getAuthority());
			System.out.println("getFile = "+url.getFile());
			System.out.println("getProtocol"+ " = "+url.getProtocol());
			System.out.println("getHost"+ " = "+url.getHost());
			System.out.println("getPort"+ " = "+url.getPort());
			// 获取path
			System.out.println("getPath"+ " = "+url.getPath());
			// 获取属性
			System.out.println("getQuery"+ " = "+url.getQuery());
			System.out.println("getRef"+ " = "+url.getRef());
			System.out.println("getUserInfo"+ " = "+url.getUserInfo());*/
			return url.getPath();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 组装按钮下URL
	 * @param menuUrl
	 * @param actionUrls
	 * @return
	 */
	public static void getBtnAccessUrls(String menuUrl,String actionUrls,List<String> accessUrls){
		if(menuUrl == null || actionUrls == null || accessUrls == null ){
			return;
		}
		String url = "save.do| action.do |/user/manger/abcd.do";
		String[] actions =StringUtils.split(actionUrls , "|");
		String menuUri = StringUtils.substringBeforeLast(menuUrl, "/");
		for(String action : actions){
			action = StringUtils.trim(action);
			if(StringUtils.startsWith(action, "/"))
				accessUrls.add(action);
			else
				accessUrls.add(menuUri+"/"+action);
		}
	}

	public static void main(String[] args) {
//		getUrlMap();
//		String requrl = "http://127.0.0.1:8080/ms/sysMenu/menu.shtml";
		String requrl = "https://127.0.0.1:8080/ms/sysMenu/menu.shtml?A=1&B=2#111";
		String uri = getReqUri(requrl);
		System.out.println("uri" + "--------" + uri);
		System.out.println("remove" + "--------" + StringUtils.remove(uri, "/ms/"));
		System.out.println("getReqUri" + "--------" + getReqUri(requrl));
		String menu = "/sysMneu/dataList.do";
		String url = "save.do| action.do |/user/manger/abcd.do";
		String[] actions =StringUtils.split(url, "|");
		String menuUri = StringUtils.substringBeforeLast(menu, "/");
		for(String action : actions){
			action = StringUtils.trim(action);
			if(StringUtils.startsWith(action, "/"))
				System.out.println(action);
			else
				System.out.println(menuUri+"/"+action);
		}
	}
}