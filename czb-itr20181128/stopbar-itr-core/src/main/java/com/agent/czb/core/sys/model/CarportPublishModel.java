package com.agent.czb.core.sys.model;

import java.io.Serializable;

/**
 * 车位发布模型层
 */
public class CarportPublishModel implements Serializable { // 车位发布
	private Long id;//标识
	private Long carportId;//车位标识
	private String state;//状态
	private Long userId;//创建人
	private java.util.Date createTime;//创建时间
	private java.util.Date updateTime;//修改时间

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
