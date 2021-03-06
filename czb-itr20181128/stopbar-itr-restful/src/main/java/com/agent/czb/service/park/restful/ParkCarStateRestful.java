package com.agent.czb.service.park.restful;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.agent.czb.common.rest.PageHelp;
import com.agent.czb.common.rest.ResultHelp;
import com.agent.czb.common.rest.restful.BasisRestful;
import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.core.eparking.FnEventCallback;
import com.agent.czb.core.eparking.model.PrepayAmountModel;
import com.agent.czb.core.park.enums.ParkCarStateEnum;
import com.agent.czb.core.park.enums.ParkOrderEnum;
import com.agent.czb.core.park.enums.ParkWhiteEnum;
import com.agent.czb.core.park.model.ParkCarStateModel;
import com.agent.czb.core.park.model.ParkOrderInfoModel;
import com.agent.czb.core.park.model.ParkSysInfoModel;
import com.agent.czb.core.park.model.ParkWhiteListModel;
import com.agent.czb.core.park.service.ParkCarStateService;
import com.agent.czb.core.park.service.ParkOrderInfoService;
import com.agent.czb.core.park.service.ParkSysInfoService;
import com.agent.czb.core.park.service.ParkWhiteListService;
import com.agent.czb.core.sys.service.CarportBuyLogService;
import com.agent.czb.core.sys.service.CarportInfoService;
import com.alibaba.fastjson.JSONObject;
import com.eparking.test.CarInto;

/**
 * 停车场汽车状态Restful接口
 */
@RestController
@RequestMapping("services/parkCarState")
public class ParkCarStateRestful extends BasisRestful {
	private static Logger log = LoggerFactory.getLogger(ParkCarStateRestful.class);

	@Autowired
	private ParkCarStateService parkCarStateService;
	@Autowired
	private ParkSysInfoService parkSysInfoService;
	@Autowired
	private ParkOrderInfoService parkOrderInfoService;
	@Autowired
	private ParkWhiteListService parkWhiteListService;
	@Autowired
	private CarportBuyLogService carportBuyLogService;
	@Autowired
	private CarportInfoService carportInfoService;

	@RequestMapping(value = "/pageList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResultHelp pageList(HttpServletRequest request, Integer isFree) {
		PageHelp pageHelp = PageHelp.newPageHelp(request);
		pageHelp.page(parkCarStateService);
		List<ParkCarStateModel> list = (List<ParkCarStateModel>) pageHelp.getData();
		for (ParkCarStateModel model : list) {
			setInfo(model, isFree);
		}
		return ResultHelp.newResult(pageHelp);
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResultHelp get(Long id) {
		ParkCarStateModel model = parkCarStateService.selectById(id);
		setInfo(model, null);
		return ResultHelp.newResult(model);
	}

	@RequestMapping(value = "/getCarState", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResultHelp getCarState(Long userId, String plateNum) {
		ParkCarStateModel model = null;
		List<ParkCarStateModel> list = parkCarStateService.pageList(FrameUtils.newMap("plateNum", plateNum));
		if (list.size() > 0) {
			model = list.get(0);
		}
		if (model == null || !(model.getState().equals(ParkCarStateEnum.State.进.toValue())
				|| model.getState().equals(ParkCarStateEnum.State.已支付.toValue()))) {
			return ResultHelp.newFialResult("车不在停车场中");
		}
		setInfo(model, null);
		return ResultHelp.newResult(model);
	}

	public void setInfo(ParkCarStateModel model, Integer isFree) {
		if (model.getState() != null) {
			model.setStateStr(ParkCarStateEnum.State.getDesc(model.getState()));
		}
		ParkSysInfoModel parkSysInfo = parkSysInfoService.selectByParkCode(model.getParkCode());
		if (parkSysInfo != null) {
			model.setParkName(parkSysInfo.getName());
		}
		if (!model.getState().equals(ParkCarStateEnum.State.出.toValue()) && isFree != null && isFree == 1) {
			// 计算费用
			/*
			 * CallRemoteDllUtils.ParkAmount amount =
			 * CallRemoteDllUtils.amount(model.getParkCode(),
			 * model.getInLogId()); if (amount == null) { return; }
			 */
			// 根据订单判断是否有超时费用
			ParkCarStateModel parkCarState = parkCarStateService.selectByPlateNumAndParkCode(model);
			// 根据车牌查询未支付的订单
			// 根据进场id查找订单信息
			List<ParkOrderInfoModel> parkOrderInfoModels = parkOrderInfoService
					.selectByRefId(parkCarState.getInLogId());
			if (parkOrderInfoModels != null) {
				for (ParkOrderInfoModel parkOrderInfoModel : parkOrderInfoModels) {
					// 遍历出该车牌超时未支付的信息
					if (parkOrderInfoModel.getState().equals(ParkOrderEnum.State.OVERTIME_UNPAID.toValue())) {
						model.setParkOrderInfoModel(parkOrderInfoModel);
					}
				}

			}

			SimpleDateFormat sdfhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// 根据白名单判断是车主或共享车主
			List<ParkWhiteListModel> list = parkWhiteListService.seleByPlateNum(model.getPlateNum());
			Date nowtime = new Date();
			for (ParkWhiteListModel parkWhiteListModel : list) {
				// 本停车场中有效的时间
				if (parkWhiteListModel.getParkCode().equals(model.getParkCode())
						&& nowtime.before(parkWhiteListModel.getEndTime())) {
					// 车主已经分享
					if (parkWhiteListModel.getState().equals("0") && parkWhiteListModel.getCarportId() != null) {
						// 长租
						if (parkWhiteListModel.getRemark() != null) {
							String string = parkWhiteListModel.getRemark();
							JSONObject jsonObject = JSONObject.parseObject(string);
							String startdate = jsonObject.getString("startdate");
							String enddate = jsonObject.getString("enddate");
							String cycle = jsonObject.getString("cycle");
							String des = jsonObject.getString("des");
							// 截取opentime时间
							String openTime = parkWhiteListModel.getOpenTime();
							String[] split = openTime.split("-");
							Date d = new Date();
							String dateNowStr = sdf.format(d);
							String startTime = dateNowStr + " " + split[0] + ":00";
							String endTime = dateNowStr + " " + split[1] + ":00";
							Date opentimeStart = null;
							Date opentimeEnd = null;
							try {
								opentimeStart = sdfhms.parse(startTime);
								opentimeEnd = sdfhms.parse(endTime);
							} catch (ParseException e) {
								e.printStackTrace();
							}
							// 判断当前是周几
							String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
							Calendar calendar = Calendar.getInstance();
							String wek = weekDays[calendar.get(Calendar.DAY_OF_WEEK) - 1];
							// 全日
							if (cycle.equals("0")) {
								if (nowtime.after(opentimeStart)) {
									model.setCartype("5");
								} else {
									model.setCartype("3");
								}
							} else if (cycle.equals("2")) {// 周六、周日
								String[] splitWeek = des.split("、");
								if (wek.equals(splitWeek[0]) || wek.equals(splitWeek[1])) {
									if (nowtime.after(opentimeStart)) {
										model.setCartype("5");
									} else {
										model.setCartype("3");
									}
								} else {
									if (nowtime.after(opentimeStart)) {
										model.setCartype("5");
									} else {
										model.setCartype("3");
									}
								}
							} else {// 周一至周五
								if (!wek.equals("周六") || !wek.equals("周日")) {
									if (nowtime.after(opentimeStart)) {
										model.setCartype("5");
									} else {
										model.setCartype("3");
									}
								} else {
									if (nowtime.after(opentimeStart)) {
										model.setCartype("5");
									} else {
										model.setCartype("3");
									}
								}
							}
						}
						// 为短租车
						else {
							Date openTime = null;
							try {
								openTime = sdfhms.parse(parkWhiteListModel.getOpenTime());
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (nowtime.after(openTime)) {
								model.setCartype("5");
							} else {
								model.setCartype("3");
							}
						}
					}
					// 车主未分享
					if (parkWhiteListModel.getCarportId() == null) {
						model.setCartype("5");
					}
					// 共享车主
					if (parkWhiteListModel.getState().equals("1")) {
						// 判断为长租车辆
						if (parkWhiteListModel.getRemark() != null) {
							String string = parkWhiteListModel.getRemark();
							JSONObject jsonObject = JSONObject.parseObject(string);
							String startdate = jsonObject.getString("startdate");
							String enddate = jsonObject.getString("enddate");
							String cycle = jsonObject.getString("cycle");
							String des = jsonObject.getString("des");
							// 截取opentime时间
							String openTime = parkWhiteListModel.getOpenTime();
							String[] split = openTime.split("-");
							Date d = new Date();
							String dateNowStr = sdf.format(d);
							String startTime = dateNowStr + " " + split[0] + ":00";
							String endTime = dateNowStr + " " + split[1] + ":00";
							Date opentimeStart = null;
							Date opentimeEnd = null;
							try {
								opentimeStart = sdfhms.parse(startTime);
								opentimeEnd = sdfhms.parse(endTime);
							} catch (ParseException e) {
								e.printStackTrace();
							}
							// 判断当前是周几
							String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
							Calendar calendar = Calendar.getInstance();
							String wek = weekDays[calendar.get(Calendar.DAY_OF_WEEK) - 1];
							// 全日
							if (cycle.equals("0")) {
								if (nowtime.after(opentimeStart)) {
									model.setCartype("13");
								} else {
									model.setCartype("3");
								}
							} else if (cycle.equals("2")) {// 周六、周日
								String[] splitWeek = des.split("、");
								if (wek.equals(splitWeek[0]) || wek.equals(splitWeek[1])) {
									if (nowtime.after(opentimeStart)) {
										model.setCartype("13");
									} else {
										model.setCartype("3");
									}
								}
							} else {// 周一至周五
								if (!wek.equals("周六") || !wek.equals("周日")) {
									if (nowtime.after(opentimeStart)) {
										model.setCartype("13");
									} else {
										model.setCartype("3");
									}
								}
							}
						}
						// 为短租车
						else {

							Date openTime = null;
							try {
								openTime = sdfhms.parse(parkWhiteListModel.getOpenTime());
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (nowtime.after(openTime)) {
								model.setCartype("13");
							} else {
								model.setCartype("3");
							}

						}

					}

				}
				
			}
			Map<String, String> map = new HashMap<>();
			map.put("park_id", model.getParkCode());
			map.put("plate_id", model.getPlateNum());
			map.put("order_id", "");
			CarInto.prepayAmount(map);
			try {
				Thread.sleep(550);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PrepayAmountModel prepayAmount = FnEventCallback.prepayAmount;
			if (prepayAmount != null) {
				// 根据易泊数据判断该车是内部车还是临停车
				// 判断为临停车
				if (prepayAmount.getError_des().equals("查询预支付金额成功！")) {
					model.setPrice(Long.valueOf(prepayAmount.getPay_amount().replace(".", "")));
					model.setCartype("3");
				} else if (prepayAmount.getError_des().equals("已支付！！")) {
					model.setPrice(Long.valueOf("0"));
					model.setCartype("3");
				}else if(prepayAmount.getError_des().equals("提前支付只支持临时车！")){
					ParkWhiteListModel parkWhiteListModel = new ParkWhiteListModel();
					parkWhiteListModel.setParkCode(model.getParkCode());
					parkWhiteListModel.setPlateNum(model.getPlateNum());
					ParkWhiteListModel parkWhiteList = parkWhiteListService.selectByParkCodeAndPlateNum(parkWhiteListModel);
					if (parkWhiteList.getState().equals(ParkWhiteEnum.State.车位.toValue())) {
						model.setCartype("5");
					}
					if (parkWhiteList.getState().equals(ParkWhiteEnum.State.共享.toValue())) {
						model.setCartype("13");
					}
				}

				// model.setStarttime(prepayAmount.getAccess_time_in());
			}
			/*
			 * model.setSpendtime(amount.getSpendtime());
			 * model.setDetailed(amount.getDetailed());
			 */
			// FnEventCallback.prepayAmount=null;
			model.setParkAddress(parkSysInfoService.selectByParkCode(model.getParkCode()).getAddr());
		}

		
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResultHelp add(ParkCarStateModel model) {
		int rows = parkCarStateService.insert(model);
		return ResultHelp.newResult(rows);
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResultHelp edit(ParkCarStateModel model) {
		int rows = parkCarStateService.updateBySelective(model);
		return ResultHelp.newResult(rows);
	}

	@RequestMapping(value = "/del", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResultHelp del(Long id) {
		int rows = parkCarStateService.delete(id);
		return ResultHelp.newResult(rows);
	}
}
