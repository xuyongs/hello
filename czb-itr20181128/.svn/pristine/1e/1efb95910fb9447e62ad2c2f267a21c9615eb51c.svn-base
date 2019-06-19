package com.agent.czb.core.park.model;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 停车场订单信息模型层
 */
@Data
@ToString
@Accessors(chain = true)
public class ParkOrderInfoModel implements Serializable { // 停车场订单信息
	private Long id;//标识
	private Long userId;//用户标识
	private String userName;//用户姓名
	private String parkCode;//停车场编码
	private String plateNum;//车牌号码
	private String code;//停车场车位编码
    private String model; // 租车模型
    private String openTime; // 开放时间
    private Integer quantity;//数量
    private Long price;//单价
	private Long totalPrice;//金额
	private String type;//订单类型
    private Long refId;//关联标识
    private Long payId; // 支付标识
    private String state;//状态，NOT_PAY("1", "未支付"), BEE_PAY("2", "已支付")
	private String remark;//备注
    private java.util.Date startTime;//开始时间
    private java.util.Date endTime;//结束时间
    private java.util.Date createTime;//创建时间
	private java.util.Date updateTime;//修改时间

	private String typeStr;
	private String stateStr;
	private Double totalPriceDob;
	
	private Integer publishType;//发布类型  1:按小时发布，0:按天发布
	
	private String startTimeNew;
	private Double endTimeNew;
	
	
	
}
