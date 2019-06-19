package com.agent.czb.core.sys.mapper;

import org.apache.poi.ss.formula.functions.T;

import com.agent.czb.common.rest.mapper.BasisMapper;
import com.agent.czb.core.sys.model.PointInfoModel;

/**
 * 积分信息数据层
 */
public interface PointInfoMapper extends BasisMapper<PointInfoModel> {
		//查询积分信息
	   PointInfoModel queryPointInfo(Long userId);
	   //修改积分
	   void updatePointInfo(PointInfoModel pointInfo);
	   //查询是否存在用户积分信息
	   int countPointInfo(Long userId);
	   
}