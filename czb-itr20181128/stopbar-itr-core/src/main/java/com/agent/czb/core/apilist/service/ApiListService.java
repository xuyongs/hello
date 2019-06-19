package com.agent.czb.core.apilist.service;

import com.agent.czb.common.rest.service.BasisService;
import com.agent.czb.core.apilist.mapper.ApiListMapper;
import com.agent.czb.core.apilist.model.ApiListModel;
import org.springframework.stereotype.Service;

/**
 * aip功能清单服务层
 */
@Service("apiListService")
public class ApiListService extends BasisService<ApiListMapper, ApiListModel> {
}
