package com.agent.czb.core.sys.model;

import java.io.Serializable;

/**
 * 车位购买日志模型层
 */
public class CarportBuyLogModel implements Serializable { // 车位购买日志
	private Long id;//标识
	private Long carportId;//车位标识
	private java.util.Date sartTime;//开始时间
	private java.util.Date endTime;//到期时间
	private String state;//状态  1:长租已经购买 ，6：短租已经购买，7：订单完成
	private Long userId;//用户标识
	private java.util.Date createTime;//创建时间
	private java.util.Date updateTime;//修改时间

	private UserInfoModel seller;
	private String userName;
	private CarportInfoModel carportInfo;
	private Integer publishType;//发布类型  1:按小时发布，0:按天发布

	public Integer getPublishType() {
		return publishType;
	}

	public void setPublishType(Integer publishType) {
		this.publishType = publishType;
	}

	public UserInfoModel getSeller() {
		return seller;
	}

	public void setSeller(UserInfoModel seller) {
		this.seller = seller;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public CarportInfoModel getCarportInfo() {
		return carportInfo;
	}

	public void setCarportInfo(CarportInfoModel carportInfo) {
		this.carportInfo = carportInfo;
	}

	public Long getId() {
		return id;
	}

 	public void setId(Long id) {
		this.id = id;
	}

	public Long getCarportId() {
		return carportId;
	}

 	public void setCarportId(Long carportId) {
		this.carportId = carportId;
	}

	public java.util.Date getSartTime() {
		return sartTime;
	}

 	public void setSartTime(java.util.Date sartTime) {
		this.sartTime = sartTime;
	}

	public java.util.Date getEndTime() {
		return endTime;
	}

 	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}

	public String getState() {
		return state;
	}

 	public void setState(String state) {
		this.state = state;
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

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

 	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

}
