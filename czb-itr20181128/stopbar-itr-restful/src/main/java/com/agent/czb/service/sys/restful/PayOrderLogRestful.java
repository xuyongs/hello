package com.agent.czb.service.sys.restful;

import com.agent.czb.common.park.call.CallRemoteDllUtils;
import com.agent.czb.common.rest.PageHelp;
import com.agent.czb.common.rest.ResultHelp;
import com.agent.czb.common.rest.restful.BasisRestful;
import com.agent.czb.common.utils.DomXMLUtil;
import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.core.park.enums.ParkCarStateEnum;
import com.agent.czb.core.park.enums.ParkOrderEnum;
import com.agent.czb.core.park.model.ParkCarStateModel;
import com.agent.czb.core.park.model.ParkOrderInfoModel;
import com.agent.czb.core.park.service.ParkCarStateService;
import com.agent.czb.core.park.service.ParkOrderInfoService;
import com.agent.czb.core.sys.enums.CarportInfoEnums;
import com.agent.czb.core.sys.enums.PayOrderEnums;
import com.agent.czb.core.sys.model.CarportInfoModel;
import com.agent.czb.core.sys.model.PayOrderLogModel;
import com.agent.czb.core.sys.model.UserCouponModel;
import com.agent.czb.core.sys.service.CarportInfoService;
import com.agent.czb.core.sys.service.PayOrderLogService;
import com.agent.czb.core.sys.service.UserCouponService;
import com.agent.czb.core.sys.service.UserInfoService;
import com.agent.wechat.util.WeChatUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.agent.czb.common.utils.DateUtils.F_YMDHMS_;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付操作日志Restful接口
 */
@RestController
@RequestMapping("services/payOrderLog")
public class PayOrderLogRestful extends BasisRestful {
    private static Logger log = LoggerFactory.getLogger(PayOrderLogRestful.class);

    @Autowired
    private PayOrderLogService payOrderLogService;
    @Autowired
    private ParkOrderInfoService parkOrderInfoService;
    @Autowired
    private CarportInfoService carportInfoService;
    @Autowired
    private UserInfoService userInfoService;
    //优惠券
    @Autowired
    private UserCouponService userCouponService;
    @Autowired
    private   ParkCarStateService   parkCarStateService;
   

    @RequestMapping(value = "/pageList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp pageList(HttpServletRequest request) {
        PageHelp pageHelp = PageHelp.newPageHelp(request);
        pageHelp.page(payOrderLogService);
        List<PayOrderLogModel> list = (List<PayOrderLogModel>) pageHelp.getData();
        for (PayOrderLogModel model : list) {
            setInfo(model);
        }
        return ResultHelp.newResult(pageHelp);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp get(Long id) {
        PayOrderLogModel model = payOrderLogService.selectById(id);
        setInfo(model);
        return ResultHelp.newResult(model);
    }

    public void setInfo(PayOrderLogModel model) {
        if (StringUtils.isNoneEmpty(model.getPayState())) {
            model.setPayStateStr(PayOrderEnums.State.getDesc(model.getPayState()));
        }
        if (StringUtils.isNoneEmpty(model.getPayType())) {
            String desc = PayOrderEnums.Type.getDesc(model.getPayType());
            if (desc == null) {
                model.setPayTypeStr(model.getPayType());
            } else {
                model.setPayTypeStr(desc);
            }
        }
        model.setUserName(userInfoService.getUserName(model.getUserId()));
    }

    /**
     * 支付宝充值(支付接口)
     */
    @RequestMapping(value = "/pay", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp pay(Long userId, Long amount) {
        if (userId == null || userId < 1) {
            return ResultHelp.newFialResult("用户ID不能为空");
        }
        if (amount == null || amount < 1) {
            return ResultHelp.newFialResult("添加金额不能为空");
        }

        PayOrderLogModel model = new PayOrderLogModel();
        model.setPayType(PayOrderEnums.Type.ZFB_RECHARGE.toValue());
        model.setPayState(PayOrderEnums.State.NOT_PAY.toValue());
        model.setQuantity(1);
        model.setPrice(amount);
        model.setUserId(userId);
        model.setAmount(amount);
        model.setPayId(FrameUtils.generatePayId());
        model.setCreateTime(new Date());
        // 添加支付接口
        payOrderLogService.insert(model);
        return ResultHelp.newResult(model.getPayId());
    }
    
    
    /**
     * 支付宝支付订单
     */
    @RequestMapping(value = "/zfbPayOrder", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp zfbPayOrder(Long orderId, Long userId,UserCouponModel coupon) {
        ParkOrderInfoModel orderInfo = parkOrderInfoService.selectById(orderId);
        if (orderInfo == null) {
            return ResultHelp.newFialResult("订单不存在");
        }
        if (orderInfo.getState().equals("402")||orderInfo.getState().equals("404")||orderInfo.getState().equals("4")) {
			return ResultHelp.newFialResult("该订单已支付，请勿重复缴费");
		}
        PayOrderLogModel model = new PayOrderLogModel();
        // 购买车位
        if (orderInfo.getType().equals(ParkOrderEnum.Type.BUY_CARPORT.toValue())) {
            CarportInfoModel carportInfo = carportInfoService.selectById(orderInfo.getRefId());
            if (!carportInfo.getState().equals(CarportInfoEnums.State.OPEN.toValue())) {
                return ResultHelp.newFialResult("该车位不能购买");
            }
            model.setPrice(orderInfo.getPrice());
            model.setAmount(orderInfo.getTotalPrice());
            model.setPayType(PayOrderEnums.Type.ZFB_CARPORT.toValue());
            model.setRefType(PayOrderEnums.RefType.车位.toValue());
            model.setRefId(orderId);
        }
        // 停车场缴费
        else if (orderInfo.getType().equals(ParkOrderEnum.Type.APP_PAY.toValue())) {
            //if (checkPayTimeOut(orderInfo)) return ResultHelp.newFialResult("订单超时");
            model.setPayType(PayOrderEnums.Type.ZFB_AMOUNT.toValue());
            model.setRefType(PayOrderEnums.RefType.停车场缴费.toValue());
            model.setRefId(orderId);
            model.setAmount(orderInfo.getTotalPrice());
            //扣除优惠券金额
            if (coupon!=null&&coupon.getId()!=null) {
                coupon=userCouponService.selectById(coupon.getId());
                model.setAmount(orderInfo.getTotalPrice() >= coupon.getAmount() ? (orderInfo.getTotalPrice() - coupon.getAmount()) : 0);
                model.setUserCouponId(coupon.getId());
            }
        } else {
            return ResultHelp.newFialResult("订单类型错误");
        }
        model.setUserId(userId);
        model.setPayId(FrameUtils.generatePayId());
        model.setPayState(PayOrderEnums.State.NOT_PAY.toValue());
        model.setCreateTime(new Date());
        //将payid放入订单
        orderInfo.setPayId(model.getPayId());
        parkOrderInfoService.update(orderInfo);
        // 添加支付接口
        payOrderLogService.insert(model);
        return ResultHelp.newResult(model);
    }
    
    /**
     * 支付宝支付订单
     */
    @RequestMapping(value = "/zfbPayOrderUpdeate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp zfbPayOrderUpdate(Long payId, Long userId,UserCouponModel coupon) {
    	PayOrderLogModel payOrderLogModel = payOrderLogService.selectById(payId);
        ParkOrderInfoModel orderInfo = parkOrderInfoService.selectById(payOrderLogModel.getRefId());
        if (orderInfo == null) {
            return ResultHelp.newFialResult("订单不存在");
        }
        if (payOrderLogModel.getPayState().equals("2")) {
        	return ResultHelp.newFialResult("订单已支付");
		}
        PayOrderLogModel model = payOrderLogService.selectById(orderInfo.getPayId());
        // 购买车位
        if (orderInfo.getType().equals(ParkOrderEnum.Type.BUY_CARPORT.toValue())) {
        	try {
				payOrderLogService.zfbBuyCarport(model, null, null);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        // 停车场缴费
        else if (orderInfo.getType().equals(ParkOrderEnum.Type.APP_PAY.toValue())) {
           payOrderLogService.zfbAMount(model, null, null);
        } else {
            return ResultHelp.newFialResult("订单更新错误！");
        }
        // 添加支付接口
        return ResultHelp.newResult(model);
    }


    
    private boolean checkPayTimeOut(ParkOrderInfoModel orderInfo) {
        // 是否超时
        if (new Date().after(DateUtils.addMinutes(orderInfo.getCreateTime(), 10))) {
            orderInfo.setUpdateTime(new Date());
            orderInfo.setState(ParkOrderEnum.State.TIMEOUT.toValue());
            parkOrderInfoService.update(orderInfo);
            return true;
        }
        return false;
    }

    /**
     * 余额支付订单
     */
    @RequestMapping(value = "/yePayOrder", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp yePayOrder(Long orderId, Long userId,UserCouponModel coupon) throws ParseException {
        ParkOrderInfoModel orderInfo = parkOrderInfoService.selectById(orderId);
        if (orderInfo == null) {
            return ResultHelp.newFialResult("订单不存在");
        }
        if (orderInfo.getState().equals("402")||orderInfo.getState().equals("404")||orderInfo.getState().equals("4")) {
			return ResultHelp.newFialResult("该订单已支付，请勿重复缴费");
		}
        Long payId = 0L;
        // 购买车位
        if (orderInfo.getType().equals(ParkOrderEnum.Type.BUY_CARPORT.toValue())) {

            //扣除优惠券金额
            if (coupon!=null&&coupon.getId()!=null){
                coupon=userCouponService.selectById(coupon.getId());
                orderInfo.setTotalPrice(orderInfo.getTotalPrice()>=coupon.getAmount()?(orderInfo.getTotalPrice()-coupon.getAmount()):0);
                userCouponService.hadUse(coupon.getId());
            }

            payId = payOrderLogService.yeBuyCarport(orderInfo);
            if(orderInfo!=null&&orderInfo.getPublishType()==1){
                Integer i = payOrderLogService.reservedPark(orderInfo,orderId);
                if (i==0){
                    ResultHelp.newFialResult("预租失败");
                }
            }
         // 停车场缴费
        }else if (orderInfo.getType().equals(ParkOrderEnum.Type.APP_PAY.toValue())) {
            //if (checkPayTimeOut(orderInfo)) return ResultHelp.newFialResult("订单超时");
            //扣除优惠券金额
            if (coupon!=null&&coupon.getId()!=null){
                coupon=userCouponService.selectById(coupon.getId());
                orderInfo.setTotalPrice(orderInfo.getTotalPrice()>=coupon.getAmount()?(orderInfo.getTotalPrice()-coupon.getAmount()):0);
                userCouponService.hadUse(coupon.getId());
            }

            payId = payOrderLogService.yeAMount(orderInfo);
           

        }

        return ResultHelp.newResult(payId);
    }

    /**
     * 订单支付返回
     */
    @RequestMapping(value = "/payReturn", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp payReturn(HttpServletRequest request, String resultStatus, String result,Long couponId) throws ParseException {
    	//支付宝
    	 resultStatus = resultStatus.replaceAll("\"", "");
    	 result = result.replaceAll("\"", "");
    	 if (!resultStatus.equals("9000")) {
    	       return ResultHelp.newFialResult("支付宝支付异常，状态：" + resultStatus);
    	    }
    	    Map<String, String> params = getParams(result);
    	    log.info("zfb pay notify! params : ", params.toString());
    	    //商户订单号（我们后台生成的订单号）
    	    String out_trade_no = params.get("out_trade_no");
    	    //支付宝支付交易号（支付流水号）
    	    String trade_no = params.get("partner");
		// 更新订单状态 为支付完成
        List<PayOrderLogModel> payOrderList = payOrderLogService.pageList(FrameUtils.newMap("payId", out_trade_no));
        if (payOrderList.isEmpty()) {
            log.error("pay order id not find! payId={}", out_trade_no);
            return ResultHelp.newFialResult("支付错误，未获取账单");
        }
        log.info("coupon______________________0");
        PayOrderLogModel payOrderLogModel = payOrderList.get(0);
		if (payOrderLogModel.getPayType().equals(PayOrderEnums.Type.ZFB_CARPORT.toValue())) {
            if (payOrderLogModel.getPayState().equals(PayOrderEnums.State.NOT_PAY.toValue())) {
                // 添加支付宝支付
              int i=payOrderLogService.zfbBuyCarport(payOrderLogModel, trade_no, trade_no);
                if (i==0){
                    ResultHelp.newFialResult("预租失败");
                }
            }
        } else if (payOrderLogModel.getPayType().equals(PayOrderEnums.Type.ZFB_RECHARGE.toValue())) {
            if (payOrderLogModel.getPayState().equals(PayOrderEnums.State.NOT_PAY.toValue())) {
                // 添加支付宝充值
                payOrderLogService.zfbRecharge(payOrderLogModel, trade_no, trade_no);
            }
        } else if (payOrderLogModel.getPayType().equals(PayOrderEnums.Type.ZFB_AMOUNT.toValue())) {
            if (payOrderLogModel.getPayState().equals(PayOrderEnums.State.NOT_PAY.toValue())) {
                // 添加支付宝缴费
                // 添加回调停车信息
                payOrderLogService.zfbAMount(payOrderLogModel, trade_no, trade_no);
            }
        }else {
            ResultHelp.newFialResult("支付类型错误，type=" + payOrderLogModel.getPayType());
        }
        log.info("coupon______________________1"+payOrderLogModel.getPayType());
        //支付宝充值赠送优惠券
        if (PayOrderEnums.Type.ZFB_RECHARGE.toValue().equals(payOrderLogModel.getPayType())){
            Integer num=(int)(payOrderLogModel.getAmount()/10000);
           /* if (num>0){
                //每满100送50优惠券(5张10元)
                num=num*5;
                for (int i = 0; i <num; i++) {
                    UserCouponModel coupon=new UserCouponModel();
                    coupon.setUserId(payOrderLogModel.getUserId());
                    coupon.setAmount(1000L);
                    userCouponService.savaCoupon(coupon);
                }

            }*/
        }
        //设置优惠券为使用状态
        log.info("coupon______________________2"+payOrderLogModel.getUserCouponId());
        if (payOrderLogModel.getUserCouponId()!=null){
            log.info("coupon______________________3"+payOrderLogModel.getUserCouponId());
            userCouponService.hadUse(payOrderLogModel.getUserCouponId());
        }
        return ResultHelp.newResult("支付成功");
    }

    private Map<String, String> getParams(String result) {
        Map<String, String> map = new HashMap<>();
        String[] split = result.split("&");
        for (String s : split) {
            String[] split1 = s.split("=");
            map.put(split1[0], split1[1]);
        }
        return map;
    }
    /*@RequestMapping(value = "/queryUnpaidList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp queryUnpaidList(HttpServletRequest request,Long userId) {
    	List<PayOrderLogModel> queryUnpaidList = payOrderLogService.queryUnpaidList(userId);
        return ResultHelp.newResult(queryUnpaidList);
    }*/

//
//    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.OK)
//    public ResultHelp edit(PayOrderLogModel model) {
//        int rows = payOrderLogService.updateBySelective(model);
//        return ResultHelp.newResult(rows);
//    }
//
//    @RequestMapping(value = "/del", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.OK)
//    public ResultHelp del(Long payId) {
//        int rows = payOrderLogService.delete(payId);
//        return ResultHelp.newResult(rows);
//    }
    /**
     * 生成微信购买车位订单
     */
    @RequestMapping(value = "/weChatGetPreyId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp getPreyId(Long orderId, Long userId,UserCouponModel coupon) {
        ParkOrderInfoModel orderInfo = parkOrderInfoService.selectById(orderId);
        if (orderInfo == null) {
            return ResultHelp.newFialResult("订单不存在");
        }
        if (orderInfo.getState().equals("402")||orderInfo.getState().equals("404")||orderInfo.getState().equals("4")) {
			return ResultHelp.newFialResult("该订单已支付，请勿重复缴费");
		}
        PayOrderLogModel model = new PayOrderLogModel();
        // 购买车位
        if (orderInfo.getType().equals(ParkOrderEnum.Type.BUY_CARPORT.toValue())) {
            CarportInfoModel carportInfo = carportInfoService.selectById(orderInfo.getRefId());
            if (!carportInfo.getState().equals(CarportInfoEnums.State.OPEN.toValue())) {
                return ResultHelp.newFialResult("该车位不能购买");
            }
            model.setPrice(orderInfo.getPrice());
            model.setAmount(orderInfo.getTotalPrice());
            model.setPayType(PayOrderEnums.Type.WECHAT_CARPORT.toValue());
            model.setRefType(PayOrderEnums.RefType.车位.toValue());
            model.setRefId(orderId);
        }
        // 停车场缴费
        else if (orderInfo.getType().equals(ParkOrderEnum.Type.APP_PAY.toValue())) {
            //if (checkPayTimeOut(orderInfo)) return ResultHelp.newFialResult("订单超时");
            model.setPayType(PayOrderEnums.Type.WECHAT_AMOUNT.toValue());
            model.setRefType(PayOrderEnums.RefType.停车场缴费.toValue());
            model.setRefId(orderId);
            model.setAmount(orderInfo.getTotalPrice());
            //扣除优惠券金额
            if (coupon!=null&&coupon.getId()!=null) {
                coupon=userCouponService.selectById(coupon.getId());
                model.setAmount(orderInfo.getTotalPrice() >= coupon.getAmount() ? (orderInfo.getTotalPrice() - coupon.getAmount()) : 0);
                model.setUserCouponId(coupon.getId());
            }
        } else {
            return ResultHelp.newFialResult("订单类型错误");
        }
        model.setUserId(userId);
        model.setPayId(FrameUtils.generatePayId());
        model.setPayState(PayOrderEnums.State.NOT_PAY.toValue());
        model.setCreateTime(new Date());
        //将payid放入订单
        orderInfo.setPayId(model.getPayId());
        parkOrderInfoService.update(orderInfo);
        // 添加支付接口
        payOrderLogService.insert(model);
        //获取微信支付统一下单返回结果
        try {
			//Map<String, String> preyId = WeChatUtil.getPreyId(model.getPayId().toString(), orderInfo.getTotalPrice().toString());
			Map<String, String> preyId = WeChatUtil.getPreyId(model.getPayId().toString(), "1");
			
			//回传给APP端
			return ResultHelp.newResult(preyId);
		} catch (Exception e) {
			log.error("获取与支付订单失败，订单号为：{}",model.getPayId());
			e.printStackTrace();
		}
        return ResultHelp.newResult("获取统一下单返回值失败");
    }
    /**
     * 更新微信支付订单
     */
    /*@RequestMapping(value = "/updateWechatPayOrder", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp updateWechatPayOrder(Long payId, Long userId,UserCouponModel coupon,String trade_no ) {
    	PayOrderLogModel payOrderLogModel = payOrderLogService.selectById(payId);
        ParkOrderInfoModel orderInfo = parkOrderInfoService.selectById(payOrderLogModel.getRefId());
        if (orderInfo == null) {
            return ResultHelp.newFialResult("订单不存在");
        }
        if (payOrderLogModel.getPayState().equals("2")) {
        	return ResultHelp.newFialResult("订单已支付");
		}
        PayOrderLogModel model = payOrderLogService.selectById(orderInfo.getPayId());
        // 购买车位
        if (orderInfo.getType().equals(ParkOrderEnum.Type.BUY_CARPORT.toValue())) {
        	try {
				payOrderLogService.weChatBuyCarport(model, trade_no, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        // 停车场缴费
        else if (orderInfo.getType().equals(ParkOrderEnum.Type.APP_PAY.toValue())) {
           payOrderLogService.weChatAMount(model, trade_no, null);
        } else {
            return ResultHelp.newFialResult("订单更新错误！");
        }
        // 添加支付接口
        return ResultHelp.newResult(model);
        }
    */
    /**
     * 微信充值生成订单(支付接口)
     */
    @RequestMapping(value = "/weChatpay", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp weChatPay(Long userId, Long amount) {
        if (userId == null || userId < 1) {
            return ResultHelp.newFialResult("用户ID不能为空");
        }
        if (amount == null || amount < 1) {
            return ResultHelp.newFialResult("添加金额不能为空");
        }
        //生成充值订单
        PayOrderLogModel model = new PayOrderLogModel();
        model.setPayType(PayOrderEnums.Type.WECHAT_RECHARGE.toValue());
        model.setPayState(PayOrderEnums.State.NOT_PAY.toValue());
        model.setQuantity(1);
        model.setPrice(amount);
        model.setUserId(userId);
        model.setAmount(amount);
        model.setPayId(FrameUtils.generatePayId());
        model.setCreateTime(new Date());
        // 添加支付信息
        payOrderLogService.insert(model);
        //获取微信支付统一下单返回结果
        try {
			Map<String, String> preyId = WeChatUtil.getPreyId(model.getPayId().toString(), amount.toString());
			//回传给APP端
			return ResultHelp.newResult(preyId);
		} catch (Exception e) {
			log.error("获取支付订单失败，订单号为：{}",model.getPayId());
			e.printStackTrace();
		}
        return ResultHelp.newResult("获取充值订单preyId失败");
    }
    
    @RequestMapping(value = "/weChatGetPreyId1", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp getPreyId1(String orderId,String totalFee) throws Exception {
		Map<String, String> preyId = WeChatUtil.getPreyId(orderId, totalFee);
		System.out.println("获取预支付的值："+preyId);
		return ResultHelp.newResult(preyId);
    }
    
    /**
     * 测试是否超时
     * @param args
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException {
        // 是否超时
        if (new Date().after(DateUtils.addMinutes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-09-06"), 10))) {
            System.out.println(true);
        }
        System.out.println(false);
    }
}
