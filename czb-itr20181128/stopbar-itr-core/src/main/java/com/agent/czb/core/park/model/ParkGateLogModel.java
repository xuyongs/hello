package com.agent.czb.core.park.model;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 停车场闸机日志模型层
 */
@Data
@ToString
@Accessors(chain = true)
public class ParkGateLogModel implements Serializable { // 停车场闸机日志
    private Long id;//标识
    private String parkCode;//停车场编码
    private String gateCode;//闸口编码
    private String carNo;//停车编号
    private String plateNum;//车牌号码
    private String ioState;//进出库类型，1：进；2：待出场；3:重复进；4：重复出；5：已经出场；
    private java.util.Date ioDate;//出入库时间 该字段不使用
    private java.util.Date inDate;//入场时间
    private java.util.Date outDate;// 出场时间
    private String carType;//车辆类型
    private String payType;//支付类型
    private Integer totalSpace;//总车位
    private Integer remainSpace;//剩余车位
    private Long price;//金额
    private Long orderId;//订单标识, 该字段不使用
    private Long userId;// 用户标识
    private String picture;//出入库图片名称
    private String detailed;//出入库记录
    private String isVip;//是否月卡用户
    private java.util.Date createTime;//创建时间
    private Integer isDel;//记录是否删除 0：正常，1：删除状态；

    private String isVipStr;
	private String ioStateStr;
    private String parkName;
    private Double priceDob;
    private String startTime;
    private String endTime;


}
