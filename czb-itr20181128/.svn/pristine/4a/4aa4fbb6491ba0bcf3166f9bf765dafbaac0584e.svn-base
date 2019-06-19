package com.agent.czb.core.sys.service;

import com.agent.czb.common.rest.service.BasisService;
import com.agent.czb.core.sys.enums.UserWithdrawalInfoEnums;
import com.agent.czb.core.sys.mapper.UserWithdrawalInfoMapper;
import com.agent.czb.core.sys.model.UserWithdrawalInfoModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户提现信息服务层
 */
@Service("userWithdrawalInfoService")
public class UserWithdrawalInfoService extends BasisService<UserWithdrawalInfoMapper, UserWithdrawalInfoModel> {
    @Autowired
    private PayOrderLogService payOrderLogService;
    @Autowired
    private WalletInfoService walletInfoService;

    public Long withdrawal(UserWithdrawalInfoModel model) {
        insert(model);
        return payOrderLogService.withdrawal(model);
    }

	public List<UserWithdrawalInfoModel> selectDataToExcel(UserWithdrawalInfoModel model) {
		return mapper.selectDataToExcel(model);
	}
}
