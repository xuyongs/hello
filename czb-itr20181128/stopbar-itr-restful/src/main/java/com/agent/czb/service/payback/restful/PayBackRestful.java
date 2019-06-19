package com.agent.czb.service.payback.restful;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.agent.czb.common.json.JSONUtils;
import com.agent.czb.common.rest.RestUtils;
import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.common.utils.HtmlUtil;
import com.agent.czb.core.sys.enums.PayOrderEnums;
import com.agent.czb.core.sys.model.PayOrderLogModel;
import com.agent.czb.core.sys.service.CarportInfoService;
import com.agent.czb.core.sys.service.PayOrderLogService;
import com.agent.czb.pub.zfb.util.AlipayNotify;

/**
 * 开闸申请日志Restful接口
 */
@RestController
@RequestMapping("services/payBack")
public class PayBackRestful {
    private static Logger log = LoggerFactory.getLogger(PayBackRestful.class);

    @Autowired
    private PayOrderLogService payOrderLogService;

    @Autowired
    private CarportInfoService carportInfoService;

    @RequestMapping(value = "/notify", method = RequestMethod.POST)
    public void notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	Map<String, String> params = RestUtils.request2map2(request);
        log.info("zfb pay notify! params : {}", JSONUtils.toJsonString(params));
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号
        String out_trade_no = params.get("out_trade_no");
        //支付宝交易号
        String trade_no = params.get("trade_no");
        //交易状态
        String trade_status = params.get("trade_status");

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        //计算得出通知验证结果
        boolean verify_result = AlipayNotify.verify(params);
        if (verify_result && StringUtils.isNoneEmpty(out_trade_no)) {//验证成功
            //请在这里加上商户的业务逻辑程序代码
            if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
                // 更新订单状态 为支付完成
                List<PayOrderLogModel> payOrderList = payOrderLogService.pageList(FrameUtils.newMap("payId", out_trade_no));
                if (payOrderList.isEmpty()) {
                    log.error("pay order id not find! payId={}", out_trade_no);
                    HtmlUtil.writerHtml(response, "fail");
                    return;
                }
                PayOrderLogModel payOrderLogModel = payOrderList.get(0);
                if (payOrderLogModel.getPayType().equals(PayOrderEnums.Type.ZFB_CARPORT.toValue())) {
                    if (payOrderLogModel.getPayState().equals(PayOrderEnums.State.NOT_PAY.toValue())) {
                        // 添加支付宝支付(购买车位)
                        payOrderLogService.zfbBuyCarport(payOrderLogModel, trade_no, trade_no);
                    }
                } else if (payOrderLogModel.getPayType().equals(PayOrderEnums.Type.ZFB_RECHARGE.toValue())) {
                    if (payOrderLogModel.getPayState().equals(PayOrderEnums.State.NOT_PAY.toValue())) {
                        // 添加支付宝充值(钱包充值)
                        payOrderLogService.zfbRecharge(payOrderLogModel, trade_no, trade_no);
                    }
                } else if (payOrderLogModel.getPayType().equals(PayOrderEnums.Type.ZFB_AMOUNT.toValue())) {
                    if (payOrderLogModel.getPayState().equals(PayOrderEnums.State.NOT_PAY.toValue())) {
                        // 添加支付宝缴费
                        // 添加回调停车信息

                        payOrderLogService.zfbAMount(payOrderLogModel, trade_no, trade_no);
                    }
                }else {
                    HtmlUtil.writerHtml(response, "fail");
                    return;
                }
            }
            HtmlUtil.writerHtml(response, "success");
        } else {
            HtmlUtil.writerHtml(response, "fail");
        }
    }

    @RequestMapping(value = "/weChatNotify", method = RequestMethod.POST)
    public void weChatNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	Map<String, String> params = RestUtils.request3map3(request);
        System.out.println("请求对的返回值是："+params);
        log.info("weixin pay notify! params : {}", JSONUtils.toJsonString(params));
        //获取微信的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号(我们的)
        String out_trade_no = params.get("out_trade_no");
        //微信交易号（支付流水号）
        String  trade_no= params.get("transaction_id");
        //交易状态
        String trade_status = params.get("result_code");
        //计算得出通知验证结果
        if ( StringUtils.isNoneEmpty(out_trade_no)) {//验证成功
            //请在这里加上商户的业务逻辑程序代码
            if (trade_status.equals("SUCCESS")) {
                // 更新订单状态 为支付完成
                List<PayOrderLogModel> payOrderList = payOrderLogService.pageList(FrameUtils.newMap("payId", out_trade_no));
                if (payOrderList.isEmpty()) {
                    log.error("pay order id not find! payId={}", out_trade_no);
                    HtmlUtil.writerHtml(response, "fail");
                    return;
                }
                PayOrderLogModel payOrderLogModel = payOrderList.get(0);
                if (payOrderLogModel.getPayType().equals(PayOrderEnums.Type.WECHAT_CARPORT.toValue())) {
                    if (payOrderLogModel.getPayState().equals(PayOrderEnums.State.NOT_PAY.toValue())) {
                        // 添加微信支付(购买车位)
                        payOrderLogService.weChatBuyCarport(payOrderLogModel, trade_no, trade_no);
                        log.info("微信支付(购买车位)payOrderLogModel订单参数"+payOrderLogModel.toString());
                    }
                } else if (payOrderLogModel.getPayType().equals(PayOrderEnums.Type.WECHAT_RECHARGE.toValue())) {
                    if (payOrderLogModel.getPayState().equals(PayOrderEnums.State.NOT_PAY.toValue())) {
                        // 添加微信充值(钱包充值)
                        payOrderLogService.weChatRecharge(payOrderLogModel, trade_no, trade_no);
                        log.info("微信充值payOrderLogModel订单参数"+payOrderLogModel.toString());
                    }
                } else if (payOrderLogModel.getPayType().equals(PayOrderEnums.Type.WECHAT_AMOUNT.toValue())) {
                    if (payOrderLogModel.getPayState().equals(PayOrderEnums.State.NOT_PAY.toValue())) {
                        // 添加微信缴费
                        payOrderLogService.weChatAMount(payOrderLogModel, trade_no, trade_no);
                        log.info("微信缴费payOrderLogModel订单参数"+payOrderLogModel.toString());
                    }
                }else {
                    HtmlUtil.writerHtml(response, "fail");
                    return;
                }
            }
            HtmlUtil.writerHtml(response, "success");
        } else {
            HtmlUtil.writerHtml(response, "fail");
        }
    }
    
    /**
     * 已不使用
     */
    @RequestMapping(value = "/return")
    public void returnInfo(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = RestUtils.request2map2(request);
        log.info("zfb pay return! params : ", params.toString());
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号
        String out_trade_no = params.get("out_trade_no");
        //支付宝交易号
        String trade_no = params.get("trade_no");
        //交易状态
        String trade_status = params.get("trade_status");

        //计算得出通知验证结果
        boolean verify_result = AlipayNotify.verify(params);
        if (verify_result) {//验证成功
            if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
                //请在这里加上商户的业务逻辑程序代码
                if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
                    // 更新订单状态 为支付完成
                    List<PayOrderLogModel> payOrderList = payOrderLogService.pageList(FrameUtils.newMap("payId", out_trade_no));
                    if (payOrderList.isEmpty()) {
                        log.error("pay order id not find! payId={}", out_trade_no);
                        HtmlUtil.writerHtml(response, "fail");
                        return;
                    }
                    PayOrderLogModel payorder = payOrderList.get(0);
                    // 设置已支付状态
                    payorder.setPayState(PayOrderEnums.State.BEN_PAY.toValue());
                    // 设置支付流水
                    payorder.setPayNo(trade_no);
                    // 设置支付时间
                    payorder.setPayTime(new Date());
                }
            }
            HtmlUtil.writerHtml(response, "success");
        } else {
            HtmlUtil.writerHtml(response, "fail");
        }
    }
}
