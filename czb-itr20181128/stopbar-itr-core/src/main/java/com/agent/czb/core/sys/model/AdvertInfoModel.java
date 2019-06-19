package com.agent.czb.core.sys.model;

import java.io.Serializable;

/**
 * 广告信息模型层
 */
public class AdvertInfoModel implements Serializable { // 广告信息
	private Long id;//标识
	private String type;//广告类型
	private Integer seq;//广告序号
	private String title;//广告标题
	private String linkUrl;//链接地址
	private String imgUrl;//图片地址
	private Long userId;//用户标识
	private java.util.Date createTime;//创建时间
	private java.util.Date updateTime;//修改时间

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

	public Integer getSeq() {
		return seq;
	}

 	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getTitle() {
		return title;
	}

 	public void setTitle(String title) {
		this.title = title;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

 	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getImgUrl() {
		return imgUrl;
	}

 	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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
