package com.agent.czb.core.sys.service;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import com.agent.czb.common.rest.service.BasisService;
import com.agent.czb.core.sys.mapper.PointInfoMapper;
import com.agent.czb.core.sys.model.PointInfoModel;

/**
 * 积分信息服务层
 */
@Service("pointInfoService")
public class PointInfoService extends BasisService<PointInfoMapper, PointInfoModel> {
	
	/**
	 * 查询积分
	 * @param userId
	 * @return
	 */
	public  PointInfoModel queryPointInfo(Long userId){
		return mapper.queryPointInfo(userId);
	}
	/**
	 * 修改积分
	 * @param userId
	 * @param updatePoint
	 */
	public void updatePointInfo(PointInfoModel pointInfo ){
	         mapper.updatePointInfo(pointInfo);
	    }
	/**
	 * 查询是否存在用户积分信息
	 * @param userId
	 * @return
	 */
	public int countPointInfo(Long userId ){
		return mapper.countPointInfo(userId);
	}
	/**
	 * 添加积分信息
	 */
	public int insert(PointInfoModel pointInfo){
		return mapper.insert(pointInfo);
	}
	
}
