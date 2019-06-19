package com.agent.czb.core.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.agent.czb.common.rest.service.BasisService;
import com.agent.czb.core.sys.mapper.PointLogMapper;
import com.agent.czb.core.sys.model.PointLogModel;

/**
 * 积分日记服务层
 */
@Service("pointLogService")
public class PointLogService extends BasisService<PointLogMapper, PointLogModel> {
	
	public List<PointLogModel> selectByUserId(Long userId){
		return mapper.selectByUserId(userId);
	}
}
