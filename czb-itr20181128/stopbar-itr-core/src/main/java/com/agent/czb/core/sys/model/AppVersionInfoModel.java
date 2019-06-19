package com.agent.czb.core.sys.model;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 应用版本信息模型层
 */
@Data
@Accessors(chain = true)
public class AppVersionInfoModel implements Serializable { // 应用版本信息
	private Long id;//标识
	private Integer type;//类型
	private String currentVersion;//当前版本
	private String lowestVersion;//最低版本
	private String apiVersion;//对应接口版本
	private String description;//升级说明
	private String downloadUrl;//下载地址
	private Integer isForce;//是否强制升级
	private Integer isActivate;//是否激活
	private Long sysUserId;//系统用户标识
	private java.util.Date createTime;//创建时间
	private java.util.Date updateTime;//修改时间
	private Integer version;//版本
	private Integer isDel;//是否删除
	
	private String typeStr;//类型枚举名称
}
