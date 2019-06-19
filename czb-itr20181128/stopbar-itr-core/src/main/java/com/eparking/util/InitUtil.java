package com.eparking.util;

import java.util.HashMap;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agent.czb.common.utils.PropertiesUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eparking.api.WTY_EX;

public class InitUtil {
	
		private static String INIT_FAIL = "100";  //鍒濆鍖栧け璐�
		private static String INIT_SUCCESS = "101"; //鍒濆鍖栨垚鍔�
    	private static String LOGIN_FAIL = "0";   //鐧诲綍澶辫触
    	private static String LOGIN_SUCCESS = "1"; //鐧诲綍鎴愬姛
    	private static Logger logger =LoggerFactory.getLogger(InitUtil.class);;
    	private static String FILEPATH="EparkingConfig.properties";
    	private static Properties pro = PropertiesUtils.load(FILEPATH);
    	// 登录接口
    	static {
    		HashMap<String, String> map = new HashMap<String, String>();
    		map.put("username", PropertiesUtils.get(pro, "login_username"));
    		map.put("password", PropertiesUtils.get(pro, "login_password"));
    		String str = httpUtil.sendPostByHttpCient(PropertiesUtils.get(pro, "URI_LOGIN"), map);
    		System.out.println("登录返回值是：" + str);
    	}
    	// 加载配置文件
    	static {
    		//查看JNA路径
    		System.out.println(System.getProperty("java.library.path"));
    		// 载入动态链接库文件。（*.dll）
    		System.loadLibrary("EParkingNetClient");
    		//System.load("/usr/lib64/libEParkingNetClient.so");
    	}
	/**
	 * 初始化
	 * @return
	 */
	public  String initLoad(){
		try{
				//获取易泊服务器地址
				Properties pro=PropertiesUtils.load(FILEPATH);
				String server = PropertiesUtils.get(pro, "server");
				System.out.println("服务："+PropertiesUtils.get(pro, "server"));
				System.out.println("server:"+server);
				//获取易泊云端端口
				String port = PropertiesUtils.get(pro, "port");
			    System.out.println("port:"+port);
				//初始化
				int init=WTY_EX.INSTANCE.ep_netclient_init(server, Integer.parseInt(port));
				Thread.sleep(1000);
				System.out.println("初始化成功");
				INIT_SUCCESS="服务器地址："+server+"端口："+port+"初始化成功！";
				return INIT_SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			INIT_FAIL="初始化失败"+e.getMessage();
 			return INIT_FAIL;
		}
	}
	/**
	 * 登录
	 * @return 
	 * INIT_FAIL = 0;  //登录失败
	 * INIT_SUCCESS = 1; //登录成功
	 */
	public  String login(){
		byte host[]=null;
		try{
				//获取SDK用户名
				Properties pro=PropertiesUtils.load(FILEPATH);
				String user_id = PropertiesUtils.get(pro, "user_id");
				//获取SDK密码
				String user_password = PropertiesUtils.get(pro, "user_password");
				String json = "{\"user_id\":\""+user_id+"\",\"user_password\":\""+user_password+"\"}";
				 host = new byte[256];
				//登录
				int logintest=WTY_EX.INSTANCE.ep_netclient_login(json, host);
				String hostStr=new String(host);
				JSONObject hostJson=JSONObject.parseObject(hostStr.trim());
				//获取登录状态
				int loginState=Integer.parseInt(hostJson.getString("ret_code"));
				System.out.println("loginState:"+loginState);
				if(loginState==1){
					LOGIN_SUCCESS="用户ID:"+user_id+"用户密码："+user_password+"登录状态："+loginState;
					System.out.println("user_id:"+user_id+"登录成功!");
					return LOGIN_SUCCESS;	
				}else if(loginState==0){
					LOGIN_FAIL="登录失败!";
					return LOGIN_FAIL;
				}else{
					logger.info("用户名或密码错误！！");
				}
		}catch(Exception e){
			logger.info("网络连接错误===="+new String(host));
			return "网络连接错误";
		}
		return LOGIN_FAIL;
	}
			

	
}
