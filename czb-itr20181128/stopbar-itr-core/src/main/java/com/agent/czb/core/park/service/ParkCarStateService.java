package com.agent.czb.core.park.service;

import com.agent.czb.common.rest.service.BasisService;
import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.core.park.mapper.ParkCarStateMapper;
import com.agent.czb.core.park.model.ParkCarStateModel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 停车场汽车状态服务层
 */
@Service("parkCarStateService")
public class ParkCarStateService extends BasisService<ParkCarStateMapper, ParkCarStateModel> {

    public ParkCarStateModel selectByPlateNum(String plateNum) {
        List<ParkCarStateModel> list = pageList(FrameUtils.newMap("plateNum", plateNum));
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

	public ParkCarStateModel selectByPlateNumAndParkCode(ParkCarStateModel parkCarStateModel) {
		return mapper.selectByPlateNumAndParkCode(parkCarStateModel);
		
	}
}
