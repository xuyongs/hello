package com.agent.czb.core.user.model;

import java.io.Serializable;

/**
 * 签到信息模型层
 */
public class UserSignInfoModel implements Serializable { // 签到信息
	private Integer id;//签到ID
	private String userId;//用户id
	private String signType;//签到类型   0正常签到，1补签
	private String signDate;//签到时间  格式(yyyyMMdd)
	private String type;//备用

	public Integer getId() {
		return id;
	}

 	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

 	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSignType() {
		return signType;
	}

 	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSignDate() {
		return signDate;
	}

 	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public String getType() {
		return type;
	}

 	public void setType(String type) {
		this.type = type;
	}

}
