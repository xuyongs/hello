package com.agent.czb.core.sys.model;

import com.agent.czb.core.park.model.ParkCarStateModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户车牌信息模型层
 */
@Data
public class UserPlateInfoModel implements Serializable { // 用户车牌信息
	private Long id;//标识
	private Long userId;//用户标识
	private String plateNum;//车牌
	private java.util.Date createTime;//创建时间
	private java.util.Date updateTime;//修改时间

	private String userName;
	private List<ParkCarStateModel> carStateModels;


}
