package com.agent.czb.core.park.mapper;

import com.agent.czb.common.rest.mapper.BasisMapper;
import com.agent.czb.core.park.model.ParkGateLogModel;

import java.util.List;

/**
 * 停车场闸机日志数据层
 */
public interface ParkGateLogMapper extends BasisMapper<ParkGateLogModel> {
    //查询有进场并且有出场纪录的数据
    List<ParkGateLogModel>  queryInAndOut(ParkGateLogModel parkGateLogModel);
    //出入场分开纪录查询
    List<ParkGateLogModel>  queryInOrOut(ParkGateLogModel parkGateLogModel);
  //车辆出场新接口
  	ParkGateLogModel selectByOrderId(Object orderId);
  	//导出Excel
  	List<ParkGateLogModel> selectDataToExcel(ParkGateLogModel model);

}