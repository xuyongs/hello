package com.agent.czb.core.sys.model;

import java.io.Serializable;

/**
 * 积分日记模型层
 */
public class PointLogModel implements Serializable { // 积分日记
	private Long id;//标识
	private Long pointId;//积分标识
	private String opType;//操作类型
	private String type;//积分类型
	private Long relId;//关联标识
	private Integer point;//操作积分
	private Long userId;//用户标识
	private java.util.Date createTime;//创建时间
	private String description; //描述
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPointId() {
		return pointId;
	}
	public void setPointId(Long pointId) {
		this.pointId = pointId;
	}
	public String getOpType() {
		return opType;
	}
	public void setOpType(String opType) {
		this.opType = opType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getRelId() {
		return relId;
	}
	public void setRelId(Long relId) {
		this.relId = relId;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
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
	@Override
	public String toString() {
		return "PointLogModel [id=" + id + ", pointId=" + pointId + ", opType=" + opType + ", type=" + type + ", relId="
				+ relId + ", point=" + point + ", userId=" + userId + ", createTime=" + createTime + ", description="
				+ description + "]";
	}
	

}