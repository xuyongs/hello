package com.agent.czb.core.sys.mapper;

import java.util.List;

import com.agent.czb.common.rest.mapper.BasisMapper;
import com.agent.czb.core.sys.model.PayOrderLogModel;

/**
 * 支付操作日志数据层
 */
public interface PayOrderLogMapper extends BasisMapper<PayOrderLogModel> {

	List<PayOrderLogModel> selectByRefId(Long parkOrderInfoId);

	List<PayOrderLogModel> selectDataToExcel(PayOrderLogModel model);


}