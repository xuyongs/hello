package com.agent.czb.core.park.mapper;

import com.agent.czb.common.rest.mapper.BasisMapper;
import com.agent.czb.core.park.model.ParkWhiteListModel;

import java.util.List;
import java.util.Map;

/**
 * 停车场白名单表数据层
 */
public interface ParkWhiteListMapper extends BasisMapper<ParkWhiteListModel> {

    ParkWhiteListModel selectByCarportid(Object CarportId);

    void updateWhiteListExp();
  //根据停车场编码和车牌号查询
  	ParkWhiteListModel selectByParkCodeAndPlateNum(ParkWhiteListModel parkWhiteList);
  	//查询所有未过期数据
  	List<ParkWhiteListModel> selectByEndtime();
	List<ParkWhiteListModel> selectByUserId(Long userId);
	/**
	 * @param parkWhiteList  查询共享车主的在停车场信息
	 * @return
	 */
	List<ParkWhiteListModel> selectByState(Object state);
	/**
	 * 查询共享车主的信息
	 */
	List<ParkWhiteListModel> selectByAdvance(Object state);
	//提交失败，添加到临时表
	int insertToTmp(ParkWhiteListModel parkWhiteListModel);
//根据车牌查询
	List<ParkWhiteListModel> selectByPlateNum(String plateNum);

}