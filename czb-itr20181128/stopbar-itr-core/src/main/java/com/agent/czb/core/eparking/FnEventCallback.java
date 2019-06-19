package com.agent.czb.core.eparking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agent.czb.common.spring.SpringContextUtil;
import com.agent.czb.core.eparking.model.CarInfoJsonModel;
import com.agent.czb.core.eparking.model.CarInfoJsonModel.CarInfo;
import com.agent.czb.core.eparking.model.PrepayAmountModel;
import com.agent.czb.core.park.enums.ParkWhiteEnum;
import com.agent.czb.core.park.model.ParkWhiteListModel;
import com.agent.czb.core.park.service.ParkSysInfoService;
import com.agent.czb.core.park.service.ParkWhiteListService;
import com.agent.czb.core.sys.model.NewParkWhiteList;
import com.agent.czb.core.user.service.NewParkWhiteListService;

import com.google.gson.Gson;
import com.sun.jna.Callback;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FnEventCallback implements Callback {
	@Autowired
	private NewParkWhiteListService newParkWhiteListService;
	@Autowired
	private ParkWhiteListService parkWhiteListService;
	@Autowired
	private ParkSysInfoService parkSysInfoService;
	// 将自己作为静态私有变量引入，使其在springmvc初始化前就被创建
	private static FnEventCallback fnEventCallback;

	// 通过@PostConstruct 和 @PreDestroy 方法 实现初始化和销毁bean之前进行的操
	@PostConstruct
	public void init() {
		fnEventCallback = this;
		fnEventCallback.newParkWhiteListService = this.newParkWhiteListService;
		fnEventCallback.parkWhiteListService = this.parkWhiteListService;
		fnEventCallback.parkSysInfoService = this.parkSysInfoService;
	}

	static final int _EP_EVENT_CONNECT_SERVER = 10001; // 连接到服务器
	static final int _EP_EVENT_DISCONNECT_SERVER = 10002; // 断开与服务器的连接
	static final int _EP_EVENT_RCV_TASKDATA = 10010; // 收到任意数据
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	// public static Long pricePrepayAmount=0L;
	public static PrepayAmountModel prepayAmount = null;

	/**
	 * 
	 * @param unEventType
	 * @param unIoType
	 *            任务类型
	 * @param psTaskId
	 *            任务id
	 * @param psEventBuf
	 *            事件内容 可为null
	 * @param unEventBufSize
	 *            事件内容缓存的大小
	 */
	public synchronized void callback(int unEventType, int unIoType, String psTaskId, String psEventBuf,
			int unEventBufSize) {
		// log.info("===========>"+unEventType);
		if (unEventType == _EP_EVENT_RCV_TASKDATA && unIoType == 20010016) {
			log.info("内部车 信息  unIoType=" + unIoType + " psTaskId=" + psTaskId + "  Rcev=" + psEventBuf);

			// GSON直接解析成对象
			CarInfoJsonModel carInfoJsonModel = new Gson().fromJson(psEventBuf, CarInfoJsonModel.class);
			// 对象中拿到集合
			List<CarInfoJsonModel.CarInfo> carInfos = carInfoJsonModel.getCar_info();
			// 判断停车场用户是否有效
			if (carInfoJsonModel.getError_des().equals("查询内部车成功")) {
				for (CarInfo carInfo : carInfos) {
					// 开始和结束时间不能为空
						if (carInfo.getBegin_date() == "" ||carInfo.getBegin_date() == null
								|| carInfo.getEnd_date() == null||carInfo.getEnd_date() == "" 
								||carInfo.getPlate_type().equals("临时车")) {
							continue;
						}
					//String carown_phone = carInfo.getCarown_phone();
					//判断本地数据库是否存在
					boolean queryParkWhiteList = queryParkWhiteList(carInfoJsonModel,carInfo);
					//不存在，添加
					try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (queryParkWhiteList) {
						log.info("存入本地数据库" + carInfo.getPlate_id());
						ParkWhiteListModel parkWhiteList = new ParkWhiteListModel();
						parkWhiteList.setState(ParkWhiteEnum.State.车位.toValue());
						parkWhiteList.setParkCode(carInfoJsonModel.getPark_id());
						parkWhiteList.setPlateNum(carInfo.getPlate_id());
						try {
							parkWhiteList.setStartTime(simpleDateFormat.parse(carInfo.getBegin_date()));
							parkWhiteList.setCreateTime(simpleDateFormat.parse(carInfo.getBegin_date()));
							parkWhiteList.setEndTime(simpleDateFormat.parse(carInfo.getEnd_date()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
						parkWhiteList.setUpdateTime(new Date());
						parkWhiteList.setAddr(fnEventCallback.parkSysInfoService
								.selectByParkCode(carInfoJsonModel.getPark_id()).getAddr());
						parkWhiteList.setPhone(carInfo.getCarown_phone());
						log.info("向本地添加数据！！！");
						fnEventCallback.parkWhiteListService.insert(parkWhiteList);
					}

				}
			}
		}

		if (unEventType == _EP_EVENT_RCV_TASKDATA && unIoType == 20010005) {
			prepayAmount = new Gson().fromJson(psEventBuf, PrepayAmountModel.class);
			log.info("提前缴费金额获取 unIoType=" + unIoType + " psTaskId=" + psTaskId + "  Rcev=" + psEventBuf);
		}
		if (unEventType == _EP_EVENT_RCV_TASKDATA && unIoType == 20010006) {
			log.info("支付完成 信息  unIoType=" + unIoType + " psTaskId=" + psTaskId + "  Rcev=" + psEventBuf);
		}
		if (unEventType == _EP_EVENT_RCV_TASKDATA && unIoType == 20010004) {
			log.info("添加内部车 = " + unIoType + " psTaskId=" + psTaskId + "  Rcev=" + psEventBuf);
			Gson gson = new Gson();
			Map fromJson = gson.fromJson(psEventBuf, Map.class);
			if (!fromJson.get("ErrorDes").equals("修改用户信息和车辆信息成功")) {
				
				//parkWhiteListService.insertToTmp();
			}
		}

	}

	public boolean queryParkWhiteList(CarInfoJsonModel carInfoJsonModel, CarInfo carInfo) {
		// 获取本地未过期的数据
		List<NewParkWhiteList> listNewParkWhiteListTheUse = fnEventCallback.newParkWhiteListService
				.listNewParkWhiteListTheUse();
		// 对数据进行判定，有则判断是否修改，无则添加。
		for (NewParkWhiteList newParkWhiteList : listNewParkWhiteListTheUse) {
			if (carInfoJsonModel.getPark_id().equals(newParkWhiteList.getPark_code())
					&& carInfo.getPlate_id().equals(newParkWhiteList.getPlate_num())) {
				return false;
			}
		}
		return true;
	}
}

//判定数据有无
			/*如果不需从云端修改本地，此代码无用
			 * if (carInfoJsonModel.getPark_id().equals(
			 * newParkWhiteList.getPark_code())&&carInfo.getPlate_id
			 * ().equals(newParkWhiteList.getPlate_num())) { long beginTime = 0;
			 * long endTime = 0; try { beginTime =
			 * simpleDateFormat.parse(carInfo.getBegin_date()). getTime();
			 * endTime = simpleDateFormat.parse(carInfo.getEnd_date()).getTime
			 * (); } catch (ParseException e) { e.printStackTrace(); } long
			 * startTime = newParkWhiteList.getStart_time().getTime(); long
			 * edtime = newParkWhiteList.getEnd_time().getTime(); String phone =
			 * newParkWhiteList.getPhone(); //判定是否需要修改 if
			 * (!(beginTime==startTime)||!(endTime==edtime)||!
			 * carown_phone.equals(phone)) { ParkWhiteListModel parkWhiteList =
			 * new ParkWhiteListModel();
			 * parkWhiteList.setState(ParkWhiteEnum.State.车位.toValue ());
			 * parkWhiteList.setParkCode(carInfoJsonModel.getPark_id ());
			 * parkWhiteList.setPlateNum(carInfo.getPlate_id()); try {
			 * parkWhiteList.setStartTime(simpleDateFormat.parse(
			 * carInfo.getBegin_date()));
			 * parkWhiteList.setCreateTime(simpleDateFormat.parse(
			 * carInfo.getBegin_date()));
			 * parkWhiteList.setEndTime(simpleDateFormat.parse(
			 * carInfo.getEnd_date())); } catch (ParseException e) {
			 * log.error("时间格式错误！！"+e.getMessage()); e.printStackTrace(); }
			 * parkWhiteList.setUpdateTime(new Date());
			 * parkWhiteList.setAddr(fnEventCallback.
			 * parkSysInfoService.selectByParkCode(carInfoJsonModel.
			 * getPark_id()).getAddr());
			 * parkWhiteList.setPhone(carInfo.getCarown_phone());
			 * parkWhiteList.setId(newParkWhiteList.getId());
			 * log.info("本地数据更新！"); fnEventCallback.newParkWhiteListService.
			 * updateEparking(parkWhiteList); } continue outLoop; }
			 */

