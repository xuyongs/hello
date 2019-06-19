package com.eparking.api;

import com.eparking.data.CRet;
import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface WTY_EX extends Library {
	
//	public static String strFilePath = System.getProperty("user.dir").replace("bin", "webapps")+ "\\parkingcome_device_cloud\\EParkingNetClient.dll";
//	public static String strFilePath = System.getProperty("user.dir").replace("bin", "webapps")+ "\\parkingcome_device_cloud\\libEParkingNetClient.so" ;
//	public static WTY_EX INSTANCE = (WTY_EX)Native.loadLibrary("/home/wanghao/libEParkingNetClient.so", WTY_EX.class); 
	public static WTY_EX INSTANCE = (WTY_EX)Native.loadLibrary("EParkingNetClient", WTY_EX.class);
	/**
	 * 调用流程：
	 * 	1.初始化 ep_netclient_init
	 * 	2.登录 ep_netclient_login
	 * 	3.调用功能函数 ep_netclient_function
	 * 	4.关闭
	 */
	
	/**
	 * init
	 * @param psServerIp
	 * @param unServerPort
	 * @return
	 */
	int ep_netclient_init(String psServerIp, int unServerPort);
	/**
	 * 注册通信事件回调函数
	 * @param callback
	 * @return
	 */
	int ep_netclient_regCallBack_netEvent(Callback callback);
	/**
	 * 注册进场回调函数
	 * @param callback
	 * @return
	 */
	int ep_netclient_regCallBack_inpark_end(Callback callback);
	/**
	 * 注册出场回调函数
	 * @param callback
	 * @return
	 */
	int ep_netclient_regCallBack_outpark_end(Callback callback);
    /**
     * login
     * @param sParam
     * @param sRet
     * @return
     */
	int ep_netclient_login(String sParam, byte[] pBuffer);
	/**
	 * 功能方法
	 * @param unIoType 
	 * 		20010004-
	 * 		20010007- 
	 * 		
	 * 		20010013-
	 * 		20010014-获取停车场车位数
	 * @param sParam
	 * @param psTaskId
	 * @return
	 */
	int ep_netclient_function(int unIoType, String sParam, String psTaskId);
	
	/**
	 * close
	 * @return
	 */
	int ep_netclient_close(String sRet);
}
