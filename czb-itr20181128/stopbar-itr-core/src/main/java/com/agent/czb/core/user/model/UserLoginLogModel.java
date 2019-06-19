package com.agent.czb.core.user.model;

import java.io.Serializable;

/**
 * 用户登陆日志模型层
 */
public class UserLoginLogModel implements Serializable { // 用户登陆日志
	private Long id;//日志ID
	private Long userId;//用户ID
	private String ipAddress;//登陆IP
	private java.util.Date loginDate;//登陆时间
	private String note;//说明

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

	public String getIpAddress() {
		return ipAddress;
	}

 	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public java.util.Date getLoginDate() {
		return loginDate;
	}

 	public void setLoginDate(java.util.Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getNote() {
		return note;
	}

 	public void setNote(String note) {
		this.note = note;
	}

}
