package com.agent.czb.core.sys.service;

import com.agent.czb.common.rest.service.BasisService;
import com.agent.czb.core.sys.mapper.UserCouponMapper;
import com.agent.czb.core.sys.model.UserCouponModel;
import com.agent.czb.core.sys.model.UserPushMessageModel;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 创建人 qany.
 * 创建时间  2016/12/2:16:23.
 * 描述
 */
@Service("userCouponService")
public class UserCouponService extends BasisService<UserCouponMapper, UserCouponModel> {

    private Date now=new Date();
    /**
     * 创建人:qany.
     * 创建时间:2016/12/2:9:52.
     * 描述:根据用户id查询用户所有的优惠券
     */
    public  List<UserCouponModel> selectByUserId(Long id){
        return mapper.selectUserId(id);
    }
    /**
     * 创建人:qany.
     * 创建时间:2016/12/2:10:52.
     * 描述:根据消息id设置已读未读
     */
    public int hadUse(Long id){
        UserCouponModel coupon=new UserCouponModel();
        coupon.setId(id);
        coupon.setUpdateTime(now);
        coupon.setIsUsed(1);
        return mapper.updateBySelective(coupon);
    }
    /**
     * 创建人:qany.
     * 创建时间:2016/12/2:11:14.
     * 描述:根据用户id和优惠券类型查询
     */
    public List<UserCouponModel> selectCouponByType(Long id,Integer type){
        UserCouponModel coupon=new UserCouponModel();
        coupon.setId(id);
        coupon.setCouponType(type);
        return  mapper.selectCouponType(coupon);
    }
    /**
     * 创建人:qany.
     * 创建时间:2016/12/2:17:09.
     * 描述: 根据用户id,优惠券类型，优惠券金额查询。
     */
    public List<UserCouponModel> selectAvailableCoupons( UserCouponModel coupon){
        return  mapper.selectCouponType(coupon);
    }
    /**
     * 创建人:qany.
     * 创建时间:2016/12/4:15:07.
     * 描述:新增优惠券
     */
    public  int savaCoupon(UserCouponModel coupon){
        coupon.setUpdateTime(now);
        coupon.setCreateTime(now);
        Calendar date = Calendar.getInstance();
        date.setTime(now);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + 9);
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date endDate = dft.parse(dft.format(date.getTime())+" 23:59:59");
            coupon.setEndTime(endDate);
            coupon.setCouponType(0);
            coupon.setIsUsed(0);
            return  mapper.insert(coupon);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }


    }
}
