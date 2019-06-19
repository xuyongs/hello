package com.agent.czb.core.sys.service;

import com.agent.czb.common.rest.Errors;
import com.agent.czb.common.rest.mapper.BasisMapper;
import com.agent.czb.common.rest.service.BasisService;
import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.core.park.model.ParkWhiteListModel;
import com.agent.czb.core.sys.enums.PayOrderEnums;
import com.agent.czb.core.sys.enums.WalletInfoEnums;
import com.agent.czb.core.sys.mapper.PayOrderLogMapper;
import com.agent.czb.core.sys.model.PayOrderLogModel;
import com.agent.czb.core.sys.model.WalletInfoModel;
import com.agent.czb.core.sys.model.WalletOpLogModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.agent.czb.core.sys.mapper.WalletInfoMapper;

import java.util.Date;
import java.util.List;

/**
 * 钱包信息服务层
 */
@Service("walletInfoService")
public class WalletInfoService extends BasisService<WalletInfoMapper, WalletInfoModel> {
    @Autowired
    private WalletOpLogService walletOpLogService;

    /**
     * 扣款
     */
    public long cutPayment(Long userId, Long amount, String type, Long rfId) {
        return updateTotal(userId, 0 - amount, WalletInfoEnums.OpType.DEL.toValue(), type, rfId);
    }

    /**
     * 加钱
     */
    public long addMoney(Long userId, Long amount, String type, Long rfId) {
        return updateTotal(userId, amount, WalletInfoEnums.OpType.ADD.toValue(), type, rfId);
    }

    /**
     * 更新总数
     */
    private long updateTotal(Long userId, Long amount, String opType, String type, Long rfId) {
        // 根据用户ID获取钱包信息
        List<WalletInfoModel> ts = mapper.pageList(FrameUtils.newMap("userId", userId.toString()));
        WalletInfoModel walletInfo = ts.get(0);
        // 修改 钱包
        Long walletId = walletInfo.getId();
        WalletInfoModel temp = new WalletInfoModel();
        temp.setId(walletId);
        temp.setTotal(amount);
        // 修改 用户钱包总金额
        int count = 0;
        while (true) {
            count++;
            walletInfo = mapper.selectById(walletId);
            // 如果是扣钱，则判断 钱包余额是否够扣钱
            if ((walletInfo.getTotal() + temp.getTotal()) >= 0 || temp.getTotal() > 0) {
                temp.setVersion(walletInfo.getVersion());
                temp.setNewVersion(FrameUtils.newVersion(walletInfo.getVersion()));
                int i = mapper.updateTotal(temp);
                // 如果更新成功，则退出循环
                if (i > 0) {
                    break;
                }
            } else  {
                throw Errors.baseServiceException("用户金额不足");
            }
            if (count > 5) {
                throw Errors.baseServiceException("钱包操作失败");
            }
        }

        // 插入 钱包操作日志
        WalletOpLogModel walletOpLog = new WalletOpLogModel();
        walletOpLog.setUserId(userId);
        walletOpLog.setWalletId(walletId);
        walletOpLog.setAmount(amount);
        walletOpLog.setBalance(walletInfo.getTotal() + amount);
        walletOpLog.setType(type);
        walletOpLog.setOpType(opType);
        walletOpLog.setRfId(rfId);
        walletOpLog.setCreateTime(new Date());
        walletOpLogService.insert(walletOpLog);


        return walletOpLog.getId();
    }
}
