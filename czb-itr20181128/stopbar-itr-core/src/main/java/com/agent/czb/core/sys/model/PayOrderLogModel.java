package com.agent.czb.core.sys.model;

import java.io.Serializable;

/**
 * 支付操作日志模型层
 */
public class PayOrderLogModel implements Serializable { // 支付操作日志
	private Long payId;//标识
	private String payType;//支付类型
	private String payState;//支付状态，未支付，已支付
	private String payNo;//支付流水号
	private String refType;//商品类型
	private Long refId;//商品标识
	private Integer quantity;//数量
	private Long price;//单价
	private Long amount;//金额
	private Long userId;//用户标识
	private java.util.Date createTime;//创建时间
	private java.util.Date payTime;//支付时间

	private String userName;
	private String payTypeStr;
	private String payStateStr;
	private String startTime;
	private String endTime;
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	
	public String getRefTypeStr() {
		return refTypeStr;
	}

	public void setRefTypeStr(String refTypeStr) {
		this.refTypeStr = refTypeStr;
	}

	private String refTypeStr;
	//优惠券id
    private Long userCouponId;

	public Long getUserCouponId() {
		return userCouponId;
	}

	public void setUserCouponId(Long userCouponId) {
		this.userCouponId = userCouponId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPayTypeStr() {
		return payTypeStr;
	}

	public void setPayTypeStr(String payTypeStr) {
		this.payTypeStr = payTypeStr;
	}

	public String getPayStateStr() {
		return payStateStr;
	}

	public void setPayStateStr(String payStateStr) {
		this.payStateStr = payStateStr;
	}

	public Long getPayId() {
		return payId;
	}

 	public void setPayId(Long payId) {
		this.payId = payId;
	}

	public String getPayType() {
		return payType;
	}

 	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayState() {
		return payState;
	}

 	public void setPayState(String payState) {
		this.payState = payState;
	}

	public String getPayNo() {
		return payNo;
	}

 	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public String getRefType() {
		return refType;
	}

 	public void setRefType(String refType) {
		this.refType = refType;
	}

	public Long getRefId() {
		return refId;
	}

 	public void setRefId(Long refId) {
		this.refId = refId;
	}

	public Integer getQuantity() {
		return quantity;
	}

 	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Long getPrice() {
		return price;
	}

 	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getAmount() {
		return amount;
	}

 	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getUserId() {
		return userId;
	}

 	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

 	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getPayTime() {
		return payTime;
	}

 	public void setPayTime(java.util.Date payTime) {
		this.payTime = payTime;
	}

	@Override
	public String toString() {
		return "PayOrderLogModel [payId=" + payId + ", payType=" + payType + ", payState=" + payState + ", payNo="
				+ payNo + ", refType=" + refType + ", refId=" + refId + ", quantity=" + quantity + ", price=" + price
				+ ", amount=" + amount + ", userId=" + userId + ", createTime=" + createTime + ", payTime=" + payTime
				+ ", userName=" + userName + ", payTypeStr=" + payTypeStr + ", payStateStr=" + payStateStr
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", refTypeStr=" + refTypeStr + ", userCouponId="
				+ userCouponId + "]";
	}
 	
}
