package com.agent.czb.core.park.mapper;

import java.util.List;

import com.agent.czb.common.rest.mapper.BasisMapper;
import com.agent.czb.core.park.model.ParkSysInfoModel;

/**
 * 停车场系统信息数据层
 */
public interface ParkSysInfoMapper extends BasisMapper<ParkSysInfoModel> {
	//查询停车场编码
	public List<String> selParkCode();
}