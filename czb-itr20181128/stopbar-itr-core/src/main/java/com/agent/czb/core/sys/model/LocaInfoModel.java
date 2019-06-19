package com.agent.czb.core.sys.model;

import java.io.Serializable;

/**
 * 位置信息模型层
 */
public class LocaInfoModel implements Serializable { // 位置信息
	private Long id;//标识
	private String type;//类型，1：车位；2：充电桩
	private Long refId;//关联id
	private String mapLng;//经度
	private String mapLat;//纬度
	private java.util.Date createTime;//创建时间
	private java.util.Date updateTime;//修改时间
	private String distance;

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public Long getId() {
		return id;
	}

 	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

 	public void setType(String type) {
		this.type = type;
	}

	public Long getRefId() {
		return refId;
	}

 	public void setRefId(Long refId) {
		this.refId = refId;
	}

	public String getMapLng() {
		return mapLng;
	}

 	public void setMapLng(String mapLng) {
		this.mapLng = mapLng;
	}

	public String getMapLat() {
		return mapLat;
	}

 	public void setMapLat(String mapLat) {
		this.mapLat = mapLat;
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
