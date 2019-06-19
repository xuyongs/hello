package com.agent.czb.core.park.mapper;

import com.agent.czb.common.rest.mapper.BasisMapper;
import com.agent.czb.core.park.model.ParkCarStateModel;

/**
 * 停车场汽车状态数据层
 */
public interface ParkCarStateMapper extends BasisMapper<ParkCarStateModel> {
/**
 * 查询车辆状态信息
 * @param parkCarStateModel
 */
	ParkCarStateModel selectByPlateNumAndParkCode(ParkCarStateModel parkCarStateModel);

}