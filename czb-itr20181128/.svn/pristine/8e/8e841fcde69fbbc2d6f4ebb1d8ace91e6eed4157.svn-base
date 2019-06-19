package com.agent.czb.core.sys.mapper;

import com.agent.czb.common.rest.mapper.BasisMapper;
import com.agent.czb.core.sys.model.UserCouponModel;
import com.agent.czb.core.sys.model.UserPushMessageModel;

import java.util.List;

/**
 * 创建人 qany.
 * 创建时间  2016/12/2:15:01.
 * 描述:优惠券数据层
 */
public interface UserCouponMapper extends BasisMapper<UserCouponModel> {
    List<UserCouponModel> selectUserId(Long id);
    List<UserCouponModel> selectCouponType(UserCouponModel userCouponModel);
    List<UserCouponModel> selectAvailableCoupons(UserCouponModel userCouponModel);
}
