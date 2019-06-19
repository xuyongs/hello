package com.agent.czb.core.sys.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 创建人 qany.
 * 创建时间  2016/12/1:15:40.
 * 描述 用户优惠券模型层
 */
@Data
public class UserCouponModel  implements Serializable {

    private  Long id;
    private  Long userId;//关联用户ID
    private   Long  amount;//优惠券金额
    private   Date  createTime;//创建时间
    private   Date  endTime;//优惠券到期时间
    private   Date  updateTime;//优惠券使用时间
    private   Integer couponType;//优惠券类型  0:抵扣券
    private   Integer isUsed;//优惠券是否已经使用  0:未使用 1:已经使用
}
