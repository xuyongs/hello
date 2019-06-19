package com.agent.czb.core.park.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.agent.czb.common.rest.mapper.BasisMapper;
import com.agent.czb.core.park.model.ParkOrderInfoModel;

/**
 * 停车场订单信息数据层
 */
public interface ParkOrderInfoMapper extends BasisMapper<ParkOrderInfoModel> {

    //根据车牌号，停车场编码查询租车位订单。返回车位发布id-
   Long queryByCarportOrderId(ParkOrderInfoModel parkOrderInfoModel);

   Long selectIdByRefId(Long refId);

   List<ParkOrderInfoModel> selectByRefId(Long refId);
   /**
    * 查询订单
    * @param userId 用户id
    * @param state 1-所有，2-未支付，3-已支付
    * @return
    */
   List<ParkOrderInfoModel> selectByState(Map map);
  /**
   * 根据userid查询订单
   * @param userId
   * @return
   */
  List<ParkOrderInfoModel> selectByUserId(Long userId);
/**
 * 根据车牌查找
 * @param plateNum
 * @return
 */
List<ParkOrderInfoModel> selectByPlateNum(String plateNum);

List<ParkOrderInfoModel> selectDataToExcel(ParkOrderInfoModel model);

}