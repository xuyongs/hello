package com.agent.czb.core.sys.mapper;

import com.agent.czb.common.rest.mapper.BasisMapper;
import com.agent.czb.core.sys.model.CarportInfoModel;

import java.util.List;

/**
 * 视频信息数据层
 */
public interface CarportInfoMapper extends BasisMapper<CarportInfoModel> {
    List<CarportInfoModel> findByIds(List<Long> list);

    void updateOverdueStatus();

    List<CarportInfoModel> findOverdueStatus();
    /*
	 * 查询车位总数
	 * @param carportId 车位Id
	 */
	int selectCount(Long carportId);
}