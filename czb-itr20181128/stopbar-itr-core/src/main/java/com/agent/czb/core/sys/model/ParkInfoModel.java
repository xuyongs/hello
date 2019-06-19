package com.agent.czb.core.sys.model;

import java.io.Serializable;

/**
 * 停车场信息模型层
 */
public class ParkInfoModel implements Serializable { // 停车场信息
    private Long id;//标识
    private Integer type;//停车场类型
    private String name;//停车场名称
    private String title;//停车场标题
    private String des;//停车场描述
    private String priceDes;//收费描述
    private String city;//所在城市
    private String area;//所在区域
    private String addr;//具体地址
    private String openTime;//营业时间
    private String contacter;//联系人
    private String phone;//联系电话
    private String parkCode;//系统停车场编码
    private Integer totalSpace;//总车位
    private Integer remainSpace;//剩余车位
    private String imgUrl;//图片地址
    private String state;//状态
    private Long userId;//上传人
    private java.util.Date createTime;//创建时间
    private java.util.Date updateTime;//修改时间
    private Integer isDel;//是否删除
    private String userName;
    private String distance;
    private String mapLng;//经度
    private String mapLat;//纬度

    private Long maxAmount;//封顶价格
    private Long tenMinutePrice;//分时 价格
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getPriceDes() {
		return priceDes;
	}
	public void setPriceDes(String priceDes) {
		this.priceDes = priceDes;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public String getContacter() {
		return contacter;
	}
	public void setContacter(String contacter) {
		this.contacter = contacter;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getParkCode() {
		return parkCode;
	}
	public void setParkCode(String parkCode) {
		this.parkCode = parkCode;
	}
	public Integer getTotalSpace() {
		return totalSpace;
	}
	public void setTotalSpace(Integer totalSpace) {
		this.totalSpace = totalSpace;
	}
	public Integer getRemainSpace() {
		return remainSpace;
	}
	public void setRemainSpace(Integer remainSpace) {
		this.remainSpace = remainSpace;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
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
	public Long getMaxAmount() {
		return maxAmount;
	}
	public void setMaxAmount(Long maxAmount) {
		this.maxAmount = maxAmount;
	}
	public Long getTenMinutePrice() {
		return tenMinutePrice;
	}
	public void setTenMinutePrice(Long tenMinutePrice) {
		this.tenMinutePrice = tenMinutePrice;
	}

}