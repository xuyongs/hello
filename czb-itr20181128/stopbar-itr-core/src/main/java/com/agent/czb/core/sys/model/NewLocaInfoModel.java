package com.agent.czb.core.sys.model;

import java.io.Serializable;

/**
 * 位置信息模型层
 */
public class NewLocaInfoModel implements Serializable { // 位置信息
   

	private String mapLng;//经度
	private String mapLat;//纬度

	

	

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
 	

	
	

	

}
