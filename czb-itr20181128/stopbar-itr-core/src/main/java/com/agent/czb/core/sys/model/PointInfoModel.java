package com.agent.czb.core.sys.model;

import java.io.Serializable;

/**
 * 积分信息模型层
 */
public class PointInfoModel implements Serializable { // 积分信息
	private Long id;//标识
	private Integer total;//积分数
	private Integer version;//版本号
	private String state;//状态
	private Long userId;//用户标识
	private java.util.Date createTime;//创建时间
	private java.util.Date updateTime;//修改时间

	public Long getId() {
		return id;
	}

 	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTotal() {
		return total;
	}

 	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getVersion() {
		return version;
	}

 	public void setVersion(Integer version) {
		this.version = version;
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
