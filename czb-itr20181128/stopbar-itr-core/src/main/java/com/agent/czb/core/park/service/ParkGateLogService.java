package com.agent.czb.core.park.service;

import com.agent.czb.common.rest.service.BasisService;
import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.core.park.enums.ParkGateEnum;
import com.agent.czb.core.park.mapper.ParkGateLogMapper;
import com.agent.czb.core.park.model.ParkCarStateModel;
import com.agent.czb.core.park.model.ParkGateLogModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 停车场闸机日志服务层
 */
@Service("parkGateLogService")
public class ParkGateLogService extends BasisService<ParkGateLogMapper, ParkGateLogModel> {
    @Autowired
    private ParkCarStateService parkCarStateService;

    public void save(ParkGateLogModel parkGateLog) {
        if (parkGateLog.getId() == null) {
            mapper.insert(parkGateLog);
        } else {
            mapper.update(parkGateLog);
        }
        String parkCode = parkGateLog.getParkCode();// 停车场编码
        String plateNum = parkGateLog.getPlateNum();// 车牌
        ParkCarStateModel parkCarState;
        // 判断是否有车辆状态
        Map<String, String> params = FrameUtils.newMap();
        params.put("parkCode", parkCode);
        params.put("plateNum", plateNum);
        List<ParkCarStateModel> parkCarStateList = parkCarStateService.pageList(params);
        if (parkCarStateList.size() > 0) {
            // 如果有加入车辆
            parkCarState = parkCarStateList.get(0);
        } else {
            // 如果没有则添加
            parkCarState = new ParkCarStateModel();
            parkCarState.setParkCode(parkCode);
            parkCarState.setPlateNum(plateNum);
        }
        // 车辆进库
        if (parkGateLog.getIoState().equals(ParkGateEnum.State.IN.toValue())
                || parkGateLog.getIoState().equals(ParkGateEnum.State.RE_IN.toValue())) {
            parkCarState.setState(ParkGateEnum.State.IN.toValue());
            parkCarState.setInLogId(parkGateLog.getId());
            parkCarState.setInDate(parkGateLog.getIoDate());
            parkCarState.setOutDate(null);
            parkCarState.setOutLogId(null);
        } else {
            // 车辆出库
            parkCarState.setState(ParkGateEnum.State.OUT.toValue());
            parkCarState.setOutLogId(parkGateLog.getId());
            parkCarState.setOutDate(parkGateLog.getIoDate());
        }
        if (parkCarState.getId() == null) {
            parkCarStateService.insert(parkCarState);
        } else {
            parkCarStateService.update(parkCarState);
        }
    }
    //查询有进场并且有出场纪录的数据
    public  List<ParkGateLogModel> queryInAndOut(ParkGateLogModel parkGateLog){
       return  mapper.queryInAndOut(parkGateLog);
    }
    //出入场分开纪录查询
    public  List<ParkGateLogModel> queryInOrOut(ParkGateLogModel parkGateLog){
        return  mapper.queryInOrOut(parkGateLog);
    }
    
  //车辆出场新接口
    public ParkGateLogModel selectByOrderId(Object orderId) {
        return mapper.selectByOrderId(orderId);
    }
  //导出Excel
	public List<ParkGateLogModel> selectDataToExcel(ParkGateLogModel model) {
		return mapper.selectDataToExcel(model);
	}

}
