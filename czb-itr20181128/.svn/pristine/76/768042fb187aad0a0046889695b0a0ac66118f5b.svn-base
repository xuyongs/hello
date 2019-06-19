package com.agent.czb.core.park.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 停车场汽车状态模型层
 */
@Data
@Accessors(chain = true)
public class ParkCarStateModel implements Serializable { // 停车场汽车状态
	private Long id;//标识
	private String parkCode;//停车场编码
	private String plateNum;//车牌号码
	private String state;//状态， 1：车辆已进场； 2：车辆已出场； 3：车辆入场，并且已支付； 5：车辆停车时长超过48小时
	private java.util.Date inDate;//进库时间
	private Long inLogId;//进库日志标识
	private java.util.Date outDate;//出库时间
	private Long outLogId;//出库日志标识

	private Long price;
	private String cartype;
	private String starttime;
	private String spendtime;
	private String detailed;
	private String stateStr;
	private String parkName;
	private String  parkAddress;
	private ParkOrderInfoModel parkOrderInfoModel;
}
