package com.eparking.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.agent.czb.common.spring.SpringContextUtil;
import com.agent.czb.common.utils.PropertiesUtils;
import com.agent.czb.core.eparking.FnEventCallback;
import com.agent.czb.core.park.service.ParkSysInfoService;
import com.agent.czb.core.sys.model.NewParkWhiteList;
import com.agent.czb.core.user.service.NewParkWhiteListService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eparking.api.WTY_EX;
import com.eparking.util.httpUtil;
import com.sun.jna.Callback;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public class CarInto {
	private static Logger logger = LoggerFactory.getLogger(CarInto.class);;
	// private static Properties pro =new Properties();
	private static String FILEPATH = "EparkingConfig.properties";
	private static Properties pro = PropertiesUtils.load(FILEPATH);

	/*// 登录接口
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
		// 修改JNA调用DLL编码问题

	}*/

	/**
	 * 车辆进场回调 函数
	 */
	public static class Inparkend implements Callback {
		public void callback(String sParam) throws Exception {
			Map<String, String> IntoMap = new HashMap<String, String>();
			JSONObject intoJson = JSONObject.parseObject(new String(sParam.getBytes("utf-8"), "utf-8"));
			System.out.println("进场输出是：" + sParam);
			IntoMap.put("parkCode", intoJson.getString("park_id").trim()); // 停车场编码park_id
			IntoMap.put("gateCode", intoJson.getString("port_id").trim()); // 闸机编码
			IntoMap.put("carNo", intoJson.getString("cario_id").trim()); // 停车编码
			IntoMap.put("plateNum", intoJson.getString("plate_id").trim()); // 车牌号
			IntoMap.put("userId", null); // 用户ID
			IntoMap.put("ioState", intoJson.getString("cario_id").trim()); // 进出库类型（1：进；2：出；3：重复；4：重复出；5：已经出场）
			IntoMap.put("ioDate", intoJson.getString("access_time").trim());// 出入库时间
																			// 该字段不适用
			IntoMap.put("inDate", intoJson.getString("access_time").trim()); // 入场时间
			IntoMap.put("remainSpace", intoJson.getString("parking_spaceNum").trim()); // 剩余车位
			if (intoJson.getString("plate_type").equals("临时车")) {
				IntoMap.put("carType", "1"); // 临时车
			} else {
				IntoMap.put("carType", "0"); // 月租车
			}
			IntoMap.put("orderId", intoJson.getString("cario_id").trim());// orderId
			IntoMap.put("createTime", intoJson.getString("access_time").trim()); // 创建时间
			// 进场记录
			try {
				String str = httpUtil.sendPostByHttpCient(PropertiesUtils.get(pro, "URI_INTO"), IntoMap);
				JSONObject strJson = JSONObject.parseObject(str);
				if (strJson.getString("status").equals("1")) { // 进场成功
					logger.info("【" + IntoMap.toString() + "】。。进场成功");
					System.out.println("进场数据：" + IntoMap.toString());
				} else {
					logger.info("【" + IntoMap.toString() + "】。。无法进场");
				}
			} catch (Exception e) {
				logger.error("车辆进场异常，请联系管理员" + e.getMessage());
			}
		}
	}

	/**
	 * 
	 * 车辆进场回调
	 * 
	 * @throws Exception
	 */
	public void into() throws Exception {
		try {
			Inparkend inparkend = new Inparkend();
			int i = WTY_EX.INSTANCE.ep_netclient_regCallBack_inpark_end(inparkend);
		} catch (Exception e) {
			logger.error("【CarInto.into】进场异常" + e.getMessage());
		}

	}

	/**
	 * 车辆出场回调函数
	 */
	public static class Outparkend implements Callback {
		public void callback(String sParam) throws Exception {
			Map<String, String> outMap = new HashMap<String, String>();
			System.out.println("出场数据=" + sParam);
			JSONObject outJson = JSONObject.parseObject(new String(sParam.getBytes("Utf-8"), "Utf-8"));
			outMap.put("parkCode", outJson.getString("park_id").trim()); // 停车场编码park_id
			outMap.put("plateNum", outJson.getString("plate_id").trim()); // 车牌号
			// outMap.put("payType",outJson.getString("plate_type").trim() );
			// //支付类型
			outMap.put("ioDate", outJson.getString("access_time_out").trim()); // 出场时间
			outMap.put("remainSpace", outJson.getString("parking_spaceNum").trim()); // 剩余车位
			outMap.put("price", outJson.getString("amount_spaid").trim()); // 实收金额
																			// //进出库类型（1：进；2：出；3：重复；4：重复出；5：已经出场）
			outMap.put("orderId", outJson.getString("cario_id").trim()); // 出场orderId
			outMap.put("detailed", "");// 出入库记录
			if (outJson.getString("plate_type").equals("临时车")) {// 车辆类型
				outMap.put("carType", "1"); // 临时车
			} else {
				outMap.put("carType", "0"); // 月租车
			}
			// 出场记录
			try {
				String str = httpUtil.sendPostByHttpCient(PropertiesUtils.get(pro, "URI_OUT"), outMap);
				JSONObject strJson = JSONObject.parseObject(str);
				System.out.println("==出场=======" + str);
				if (strJson.getString("status").equals("1")) { // 出场成功
					logger.info("【" + outMap.toString() + "】。。出场成功");
					System.out.println("json出场数据：" + outMap.toString());
				} else {
					logger.info("【" + outMap.toString() + "】。。无法出场");
				}
			} catch (Exception e) {
				logger.error("车辆出场异常，请联系管理员" + e.getMessage());
			}
		}
	}

	/**
	 * 车辆出场回调
	 */
	public void out() {
		try {
			WTY_EX.INSTANCE.ep_netclient_regCallBack_outpark_end(new Outparkend());
		} catch (Exception e) {
			logger.error("【CarInto.out】进场异常" + e.getMessage());
		}

	}

	/**
	 * 注册通信回调函数
	 */
	public void reg() {
		FnEventCallback fen = new FnEventCallback();
		int regCallback = WTY_EX.INSTANCE.ep_netclient_regCallBack_netEvent(fen);

	}

	/**
	 * 添加停车场内部车功能函数
	 * 
	 * @param map
	 */
	public static int addMonthCar(Map<String, String> map) {
		int unIoType = Integer.parseInt(PropertiesUtils.get(pro, "_EP_CMD_SDK_addInnerCar"));
		String sParam = JSON.toJSONString(map);
		String psTaskId = null;
		int func = WTY_EX.INSTANCE.ep_netclient_function(unIoType, sParam, psTaskId);
		logger.info("{}添加内部车：{}",func,sParam);
		return func;
	}
	/**
	 * 查询月租车功能函数
	 */
	public static void find() {
		int unIoType = Integer.parseInt(PropertiesUtils.get(pro, "_EP_CMD_SDK_FindInnerCar"));
		String sParam = null;
		String psTaskId = null;
		//查询停车场的parkCode
		ParkSysInfoService parkSysInfoService = SpringContextUtil.getBean(ParkSysInfoService.class);//调用parkSysInfoService
		List<String> parkCodeList = parkSysInfoService.selParkCode();
		if (!parkCodeList.equals(null)||!parkCodeList.equals("")) {//遍历判断停车场数据是否为null
			for (String parkCode : parkCodeList) {
				for (int i = 0; i < 200; i += 100) {
					Map<String, String> map = new HashMap<>();
					map.put("park_id", parkCode.trim());
					map.put("start_row_num", String.valueOf(i));
					map.put("row_count", "100");
					sParam = JSON.toJSONString(map);
					//执行查询的功能函数
					int func = WTY_EX.INSTANCE.ep_netclient_function(unIoType, sParam, psTaskId);
				}
			}
		}else {
			logger.info("查询停车场数据为空！");
		}
		

	}
	/**
	 * 删除停车场内部车功能函数
	 * @param map
	 */
	public static void deleteMonthCar(){
		int unIoType = Integer.parseInt(PropertiesUtils.get(pro, "_EP_CMD_SDK_DeleteInnerCar"));
		Map<String, String> mapdel = new HashMap<>();
		//查找当前时间所要删除的车辆信息
		NewParkWhiteListService newParkWhiteListService= SpringContextUtil.getBean(NewParkWhiteListService.class);
		List<NewParkWhiteList> listNewParkWhiteList = newParkWhiteListService.listNewParkWhiteList();
		System.out.println("需要删除的车辆"+listNewParkWhiteList.toString());
		//获取单个要删除的车辆信息进行删除操作
		if(!listNewParkWhiteList.equals(null)){
			for (NewParkWhiteList newParkWhiteList : listNewParkWhiteList) {
				mapdel.put("park_id",newParkWhiteList.getPark_code() );   //停车场ID
				mapdel.put("plate_id", newParkWhiteList.getPlate_num()); // 车牌号码
				mapdel.put("plate_color", "蓝牌");
				String sParam = JSON.toJSONString(mapdel);
				String psTaskId=null;
				//易泊删除内部车函数
				int func = WTY_EX.INSTANCE.ep_netclient_function(unIoType, sParam, psTaskId);
			}
		}else {
			logger.info("【CarInto.deleteMonthCar】 没有可以删除的车辆!");
		}
	}
	
	/**
	 * 删除停车场内部车功能函数
	 * @param map
	 */
	public static void deleteMonthCar(Map<String, String> mapdel){
		int unIoType = Integer.parseInt(PropertiesUtils.get(pro, "_EP_CMD_SDK_DeleteInnerCar"));
				mapdel.put("park_id",mapdel.get("park_id"));   //停车场ID
				mapdel.put("plate_id", mapdel.get("plate_id")); // 车牌号码
				mapdel.put("plate_color", "蓝牌");
				String sParam = JSON.toJSONString(mapdel);
				String psTaskId=null;
				//易泊删除内部车函数
				int func = WTY_EX.INSTANCE.ep_netclient_function(unIoType, sParam, psTaskId);
	}
	/*
	 * 加载登录静态块方法
	 */
	public static void loginstatic(){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("username", PropertiesUtils.get(pro, "login_username"));
		map.put("password", PropertiesUtils.get(pro, "login_password"));
		String str = httpUtil.sendPostByHttpCient(PropertiesUtils.get(pro, "URI_LOGIN"), map);
		System.out.println("登录返回值是：" + str);
		
	}
	//预交费金额获取
	public static void prepayAmount(Map<String, String> map){
		int unIoType = 20010005;
		String sParam = null;
		String psTaskId = null;
		sParam = JSON.toJSONString(map);
		System.out.println("sParams="+sParam);

		int func = WTY_EX.INSTANCE.ep_netclient_function(unIoType, sParam, psTaskId);
	}
	//支付完成
	public static void payOk(Map<String, String> map){
		int unIoType = 20010006;
		String sParam = null;
		String psTaskId = null;
		sParam = JSON.toJSONString(map);
		System.out.println("sParams="+sParam);

		int func = WTY_EX.INSTANCE.ep_netclient_function(unIoType, sParam, psTaskId);
	}
	
	
}
