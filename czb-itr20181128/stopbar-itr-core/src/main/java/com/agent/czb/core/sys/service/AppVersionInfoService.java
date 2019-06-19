package com.agent.czb.core.sys.service;

import com.agent.czb.common.rest.service.BasisService;
import com.agent.czb.core.sys.mapper.AppVersionInfoMapper;
import com.agent.czb.core.sys.model.AppVersionInfoModel;
import org.springframework.stereotype.Service;

/**
 * 应用版本信息服务层
 */
@Service("appVersionInfoService")
public class AppVersionInfoService extends BasisService<AppVersionInfoMapper, AppVersionInfoModel> {
}
