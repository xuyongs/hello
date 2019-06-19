package com.agent.czb.service.sys.restful;

import com.agent.czb.common.rest.ResultHelp;
import com.agent.czb.common.rest.restful.BasisRestful;
import com.agent.czb.core.sys.model.UserCouponModel;
import com.agent.czb.core.sys.service.UserCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 创建人 qany.
 * 创建时间  2016/12/2:16:39.
 * 描述 优惠券接口
 */
@RestController
@RequestMapping("services/userCouponRestful")
public class UserCouponRestful extends BasisRestful {

    @Autowired
    private UserCouponService userCouponService;

     /**
      * 创建人:qany.
      * 创建时间:2016/12/2:17:24.
      * 描述: 根据用户id查询优惠券
      */
    @RequestMapping(value = "/couponByUserId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp couponByUserId(Long userId) {
        return ResultHelp.newResult(userCouponService.selectByUserId(userId));
    }

    /**
     * 创建人:qany.
     * 创建时间:2016/12/2:17:25.
     * 描述: 传入优惠券id改变优惠券使用状态
     */
    @RequestMapping(value = "/hadUse", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp hadUse(Long couponId) {
        return ResultHelp.newResult(userCouponService.hadUse(couponId));
    }
        /**
         * 创建人:qany.
         * 创建时间:2016/12/5:14:21.
         * 描述: 根据用户id和优惠券类型
         */
    @RequestMapping(value = "/selectByType", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp selectMessageByType(Long userId,Integer type) {
        return ResultHelp.newResult(userCouponService.selectCouponByType(userId,type));
    }
    /**
     * 创建人:qany.
     * 创建时间:2016/12/5:14:22.
     * 描述:根据优惠券金额和类型
     */
    @RequestMapping(value = "/selectAvailableCoupons", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp selectAvailableCoupons(UserCouponModel coupon) {
        return ResultHelp.newResult(userCouponService.selectAvailableCoupons(coupon));
    }
}
