package com.agent.czb.core.apilist.model;

import java.io.Serializable;

/**
 * aip功能清单模型层
 */
public class ApiListModel implements Serializable { // aip功能清单
	private Long id;//编号，主键，自动增长
	private String title;//接口名称
	private String description;//使用说明
	private Short isDelete;//0:否，1：是，默认为0

	public Long getId() {
		return id;
	}

 	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

 	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

 	public void setDescription(String description) {
		this.description = description;
	}

	public Short getIsDelete() {
		return isDelete;
	}

 	public void setIsDelete(Short isDelete) {
		this.isDelete = isDelete;
	}

}
