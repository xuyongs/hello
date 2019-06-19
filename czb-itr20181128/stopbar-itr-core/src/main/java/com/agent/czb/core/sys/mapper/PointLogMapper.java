package com.agent.czb.core.sys.mapper;

import java.util.List;

import com.agent.czb.common.rest.mapper.BasisMapper;
import com.agent.czb.core.sys.model.PointLogModel;

/**
 * 积分日记数据层
 */
public interface PointLogMapper extends BasisMapper<PointLogModel> {
		
	List<PointLogModel> selectByUserId(Long userId);
}