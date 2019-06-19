package com.agent.czb.core.sys.mapper;

import com.agent.czb.common.rest.mapper.BasisMapper;
import com.agent.czb.core.sys.model.LocaInfoModel;

import java.util.List;
import java.util.Map;

/**
 * 位置信息数据层
 */
public interface LocaInfoMapper extends BasisMapper<LocaInfoModel> {
    List<LocaInfoModel> locaList(Map map);
    int delByType(Object obj);
	LocaInfoModel selectByRefId(Long refId);
}