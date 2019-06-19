package com.agent.czb.core.sys.service;

import com.agent.czb.common.rest.service.BasisService;
import com.agent.czb.core.sys.mapper.CarportBuyLogMapper;
import com.agent.czb.core.sys.model.CarportBuyLogModel;
import org.springframework.stereotype.Service;

/**
 * 车位购买日志服务层
 */
@Service("carportBuyLogService")
public class CarportBuyLogService extends BasisService<CarportBuyLogMapper, CarportBuyLogModel> {

	public CarportBuyLogModel selectByCarportId(Long id) {
		return mapper.selectByCarportId(id);
	}
}
