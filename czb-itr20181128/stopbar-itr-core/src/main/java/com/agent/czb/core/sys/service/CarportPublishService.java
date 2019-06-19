package com.agent.czb.core.sys.service;

import com.agent.czb.common.rest.service.BasisService;
import com.agent.czb.core.sys.mapper.CarportPublishMapper;
import com.agent.czb.core.sys.model.CarportPublishModel;
import org.springframework.stereotype.Service;

/**
 * 车位发布服务层
 */
@Service("carportPublishService")
public class CarportPublishService extends BasisService<CarportPublishMapper, CarportPublishModel> {
}
