package com.agent.czb.core.sys.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;


public class UserFeedbackModel implements Serializable{
	private Long id;//标识
	private Long userId;//用户名
	private String note;//备注内容
	private  java.util.Date createTime;//创建时间
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "UserFeedbackModel [id=" + id + ", userId=" + userId + ", note=" + note + ", createTime=" + createTime
				+ "]";
	}
	
	

	
	
}
