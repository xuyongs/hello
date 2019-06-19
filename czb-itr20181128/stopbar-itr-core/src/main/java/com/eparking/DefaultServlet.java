package com.eparking;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.eparking.api.WTY_EX;
import com.sun.jna.Callback;
import com.sun.jna.Platform;

public class DefaultServlet extends HttpServlet {
	
	static final int _EP_EVENT_CONNECT_SERVER = 10001;		//连接到服务器
	static final int _EP_EVENT_DISCONNECT_SERVER = 10002;		//断开与服务器的连接
	static final int _EP_EVENT_RCV_TASKDATA = 10010;
	
	String parkId = "00400040000500500";	//这个id要申请
	String portId = "1";
	
	public static class FnEventCallback implements Callback {
		public void callback(int unEventType, int unIoType, String psTaskId, String psEventBuf, int unEventBufSize){
			System.out.println("==========================>Event Type "+unEventType);
			switch (unEventType) {
			case _EP_EVENT_CONNECT_SERVER:
				//SDK_user和SDK_password要申请
				//String json = "{\"user_id\":\"SDK_user\",\"user_password\":\"SDK_password\",\"timestamp\":\"\",\"signature\":\"\"}";
				String json = "{\"user_id\":\"SDK_user\",\"user_password\":\"SDK_password\",\"timestamp\":\"\",\"signature\":\"\"}";
				byte host[] = new byte[256];
				int loginRet = WTY_EX.INSTANCE.ep_netclient_login(json,host);
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>"+new String(host));
				System.out.println(">>>>>>>>>>>>>>>>>login="+loginRet);
				break;
			case _EP_EVENT_DISCONNECT_SERVER:
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>disconnect server \n");
				break;
			case _EP_EVENT_RCV_TASKDATA:
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>unIoType="+unIoType+" psTaskId="+psTaskId+"  Rcev="+psEventBuf);
			default:
				break;
			}
		}
	}
	/**
	 * 进场回调
	 * @author haowangi
	 *
	 */
	public static class inparkend implements Callback {
		public void callback(String sParam){
			System.out.println("=================>inparkend"+sParam);
		}
	}
	/**
	 * 出场回调
	 * @author haowangi
	 *
	 */
	public static class outpark_end implements Callback {
		public void callback(String sParam){
			System.out.println("=================>outpark_end"+sParam);
		}
	}
	
	@Override  
    public void init() throws ServletException {          
        System.out.println("创建BeanFactory。。。。。");  
        //进场回调
		WTY_EX.INSTANCE.ep_netclient_regCallBack_inpark_end(new inparkend());
		//出场回调
		WTY_EX.INSTANCE.ep_netclient_regCallBack_outpark_end(new outpark_end());
		
		int regCallback = WTY_EX.INSTANCE.ep_netclient_regCallBack_netEvent(new FnEventCallback());
		System.out.println("=====================>regCallback="+regCallback);
		int initRet = WTY_EX.INSTANCE.ep_netclient_init("47.95.254.112", 9801);
		System.out.println("===================>init="+initRet);
    }  
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");
		String park_id = req.getParameter("park_id");
		String action = req.getParameter("action");
		System.out.println("server start===================>");
		try {
			
		
//		System.out.println("lib path=========>"+WTY_EX.INSTANCE.strFilePath);
		if (null == park_id || park_id.isEmpty()) {
			park_id = parkId;
		}
		String sParam = "";
		String psTaskId = "";
		//初始化
		if ("init".equals(action)) {
			//进场回调
			WTY_EX.INSTANCE.ep_netclient_regCallBack_inpark_end(new inparkend());
			//出场回调
			WTY_EX.INSTANCE.ep_netclient_regCallBack_outpark_end(new outpark_end());
			
			int regCallback = WTY_EX.INSTANCE.ep_netclient_regCallBack_netEvent(new FnEventCallback());
			System.out.println("=====================>regCallback="+regCallback);
			int initRet = WTY_EX.INSTANCE.ep_netclient_init("47.95.254.112", 9801);
			System.out.println("===================>init="+initRet);
		} else if ("close".equals(action)) {
			String sRet = "";
			int closeRet = WTY_EX.INSTANCE.ep_netclient_close(sRet);
			System.out.println("===================>close="+closeRet);
		} else if ("parknum".equals(action)) {
			int unIoType = 20010014;
			Map<String, String> map = new HashMap<>();
			map.put("park_id",park_id);
			sParam = JSON.toJSONString(map);
			psTaskId = new Long(new Date().getTime()).toString();
			int func = WTY_EX.INSTANCE.ep_netclient_function(unIoType, sParam, psTaskId);
			System.out.println("===================>parknum="+func);
			System.out.println("===================>parknum="+psTaskId);
		} else if ("inout_id".equals(action)) {
			int unIoType = 20010013;
			Map<String, String> map = new HashMap<>();
			map.put("park_id", park_id);
			sParam = JSON.toJSONString(map);
			System.out.println("sParams="+sParam);

			int func = WTY_EX.INSTANCE.ep_netclient_function(unIoType, sParam, psTaskId);
			System.out.println("===================>inout_id="+func);
			System.out.println("===================>inout_id="+psTaskId);
		} else if ("inout_record".equals(action)) {
			int unIoType = 20010015;
			Map<String, String> map = new HashMap<>();
			map.put("park_id", park_id);
			map.put("cario_id", "");
			map.put("plate_id", "");
			map.put("time_begin", "");
			map.put("time_end", "");
			sParam = JSON.toJSONString(map);
			System.out.println("sParams="+sParam);

			int func = WTY_EX.INSTANCE.ep_netclient_function(unIoType, sParam, psTaskId);
			System.out.println("===================>inout_record="+func);
			System.out.println("===================>inout_record="+psTaskId);
			
		} else if ("inner_car_month".equals(action)) {
			int unIoType = 20010004;
			Map<String, String> map = new HashMap<>();
			String plate = req.getParameter("plate");
			if (null == plate || plate.isEmpty()) {
				plate = "京A123456";
			}
			String begin_date = req.getParameter("begin_date");
			String end_date = req.getParameter("end_date");
			String tel = req.getParameter("tel");
			map.put("park_id", park_id);
			map.put("plate_id", plate);//非空
			map.put("isinout","");
			map.put("plate_color","");
			map.put("plate_type","月租车");//非空
			map.put("plate_state", "正常");//非空
			map.put("plate_subtype","");
			map.put("free_time", "60");//非空
			map.put("begin_date", begin_date);//非空
			map.put("end_date", end_date);//非空
			map.put("carown_name","");
			map.put("carown_sex","");
			map.put("carown_phone", tel);//非空 车主电话号码
			map.put("carown_address","");
			map.put("charg_scheme","");
			map.put("del_record","1");
			sParam = JSON.toJSONString(map);
			System.out.println("sParams="+sParam);

			int func = WTY_EX.INSTANCE.ep_netclient_function(unIoType, sParam, psTaskId);
			System.out.println("===================>inner_car_month="+func);
			System.out.println("===================>inner_car_month="+psTaskId);
		} else if ("inner_car_once".equals(action)) {
			int unIoType = 20010004;
			Map<String, String> map = new HashMap<>();
			String plate = req.getParameter("plate");
			if (null == plate || plate.isEmpty()) {
				plate = "京A123456";
			}
			String begin_date = req.getParameter("begin_date");
			String end_date = req.getParameter("end_date");
			String tel = req.getParameter("tel");
			map.put("park_id", park_id);
			map.put("plate_id", plate);//非空
			map.put("isinout","场外");
			map.put("plate_color","");
			map.put("plate_type","预约车");//非空
			map.put("plate_state", "正常");//非空
			map.put("plate_subtype","");
			map.put("free_time", "");//非空
			map.put("begin_date", begin_date);//非空
			map.put("end_date", end_date);//非空
			map.put("carown_name","");
			map.put("carown_sex","");
			map.put("carown_phone",tel);//非空 车主电话号码
			map.put("carown_address","");
			map.put("charg_scheme","");
			map.put("del_record","1");
			sParam = JSON.toJSONString(map);
			System.out.println("sParams="+sParam);

			int func = WTY_EX.INSTANCE.ep_netclient_function(unIoType, sParam, psTaskId);
			System.out.println("===================>inner_car_once="+func);
			System.out.println("===================>inner_car_once="+psTaskId);
		} else if ("break".equals(action)) {
			int unIoType = 20010007;
			Map<String, String> map = new HashMap<>();
			String port_id = req.getParameter("port_id");
			if (null == port_id || port_id.isEmpty()) {
				port_id = portId;
			}
			map.put("park_id", park_id);//非空
			map.put("port_id", port_id);//非空
			sParam = JSON.toJSONString(map);
			System.out.println("sParams="+sParam);

			int func = WTY_EX.INSTANCE.ep_netclient_function(unIoType, sParam, psTaskId);
			System.out.println("===================>break="+func);
			System.out.println("===================>break="+psTaskId);
		} else if ("led".equals(action)) {
			int unIoType = 20010008;
			Map<String, Object> map = new HashMap<>();
			String port_id = req.getParameter("port_id");
			if (null == port_id || port_id.isEmpty()) {
				port_id = portId;
			}
			String text = req.getParameter("text");
			if (null == text || text.isEmpty()) {
				text = "测试文字";
			}
			map.put("park_id", park_id);//非空
			map.put("port_id", port_id);//非空
			List<Map<String, String>> textList = new ArrayList<>();
			
			Map<String, String> text1 = new HashMap<>();
			text1.put("row_index", "1");
			text1.put("row_text", text);
			text1.put("row_color", "2");
			textList.add(text1);
			
			map.put("text", textList);//非空
			sParam = JSON.toJSONString(map);
			System.out.println("sParams="+sParam);

			int func = WTY_EX.INSTANCE.ep_netclient_function(unIoType, sParam, psTaskId);
			System.out.println("===================>led="+func);
			System.out.println("===================>led="+psTaskId);
		} else if ("voice".equals(action)) {
			int unIoType = 20010009;
			Map<String, Object> map = new HashMap<>();
			String port_id = req.getParameter("port_id");
			if (null == port_id || port_id.isEmpty()) {
				port_id = portId;
			}
			String text = req.getParameter("text");
			if (null == text || text.isEmpty()) {
				text = "测试文字";
			}
			map.put("park_id", park_id);//非空
			map.put("port_id", port_id);//非空
			map.put("voice_text", text);//非空
			sParam = JSON.toJSONString(map);
			System.out.println("sParams="+sParam);

			int func = WTY_EX.INSTANCE.ep_netclient_function(unIoType, sParam, psTaskId);
			System.out.println("===================>voice="+func);
			System.out.println("===================>voice="+psTaskId);
			
		} else {
			String home = Platform.isWindows() ? System.getProperty("user.dir") + "\\EParkingNetClient.dll" : "/home/wanghao/libEParkingNetClient.so";
			System.out.println(home);
			
			String allClassPath = System.getProperty("java.class.path");
			System.out.println(allClassPath);

			String strFilePath = System.getProperty("user.dir").replace("bin", "webapps")+ "\\parkingcome_device_cloud";
			strFilePath += Platform.isWindows() ? "\\EParkingNetClient.dll": "libEParkingNetClient.so";
			System.out.println(home);
			
			String reqpath = req.getContextPath();
			System.out.println(reqpath);
		}
		System.out.println("server end===================>");
		PrintWriter pw = resp.getWriter();
//		pw.print();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	
	}
	
}
