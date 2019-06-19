package com.agent.czb.core.park.service;

import com.agent.czb.common.rest.service.BasisService;
import com.agent.czb.core.park.mapper.ParkSysGateMapper;
import com.agent.czb.core.park.model.ParkSysGateModel;
import org.springframework.stereotype.Service;

/**
 * 停车场系统闸机信息服务层
 */
@Service("parkSysGateService")
public class ParkSysGateService extends BasisService<ParkSysGateMapper, ParkSysGateModel> {
}
