package com.agent.czb.core.eparking.model;

import java.util.List;

import lombok.Data;
import lombok.ToString;
/**
 * 
 * @author 陈权权
 *	从云端查询的内部车信息的json模型
 */
@Data
@ToString
public class CarInfoJsonModel {
	private List<CarInfo> car_info;//
	private String error_des; //错误描述
	private String park_id;//停车场ID
	private String park_name;//
	private String recoder_count;//一共有多少数据
	private String ret_code;//返回值参数：1成功  0 失败
	private String row_count;//本次实际查询出多少条数据
	private String signature;//
	private String timestamp;//
	@Data
	@ToString
	public class CarInfo{
		private String allow_inouts;//预约车时是否允许多次进出 0 不允许 1 允许
		private String begin_date;//开始时间
		private String carown_address;//住址
		private String carown_birsday;//出生日期
		private String carown_cardnum;//
		private String carown_cardtype;//车主证件类型
		private String carown_name;//车主姓名
		private String carown_phone;//车主电话号码  不能null
		private String carown_sex;//车主性别
		private String charg_scheme;//计费方案
		private String del_record;//是否删除之前的约租车记录 0 否 1 删除
		private String end_date;//结束时间
		private String free_time;//免费时长
		private String isinout;//是否进入，场内、场外
		private String park_num;//共享泊位个数
		private String plate_color;//车牌颜色
		private String plate_id;//车牌号
		private String plate_state;//缴费状态
		private String plate_subtype;//缴费子类型
		private String plate_type;//缴费类型
		
	}
}
