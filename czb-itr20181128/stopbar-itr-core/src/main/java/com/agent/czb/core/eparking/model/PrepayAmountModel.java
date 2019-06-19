package com.agent.czb.core.eparking.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PrepayAmountModel {
	private String access_time_in;//入场时间
	private String access_time_out;//出场时间
	private String cario_id;//停车场进出口id完整一次停车标识
	private String error_des;//失败描述
	private String order_id;//订单号
	private String park_name;//停车场名称
	private String pay_amount;//支付金额
	private String pay_finish_type;//0 预支付完成 1 出场支付完成
	private String pay_state;//
	private String plate_state;//车辆状态
	private String plate_subtype;//车辆子类型
	private String plate_type;//车辆类型
	private String port_name;//进出口名称
	private String ret_code;//0 失败 1 成功
	private String signature;//
	private String timestamp;//
	private String total_time;//停留时长
}
