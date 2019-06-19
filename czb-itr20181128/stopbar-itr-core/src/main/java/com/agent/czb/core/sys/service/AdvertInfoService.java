package com.agent.czb.core.sys.service;

import com.agent.czb.common.rest.service.BasisService;
import com.agent.czb.core.sys.mapper.AdvertInfoMapper;
import com.agent.czb.core.sys.model.AdvertInfoModel;
import org.springframework.stereotype.Service;

/**
 * 广告信息服务层
 */
@Service("advertInfoService")
public class AdvertInfoService extends BasisService<AdvertInfoMapper, AdvertInfoModel> {
}
