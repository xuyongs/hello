package com.agent.czb.core.cooper.model;

import com.agent.czb.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/1/6.
 */
public class CooperMsg {
    private static Logger log = LoggerFactory.getLogger(CooperMsg.class);
    public static final String SUCCESS = "1";
    public static final String FAILED = "0";
    public static final String NOT_LOGIN = "404";

    private String status;
    private String code;
    private String message;
    private Object orders;
    private Integer ordersNum;
    private String time;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getOrders() {
        return orders;
    }

    public void setOrders(Object orders) {
        this.orders = orders;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCode() {
        return code;
    }

    public CooperMsg setCode(String code) {
        this.code = code;
        return this;
    }

    public Integer getOrdersNum() {
        return ordersNum;
    }

    public void setOrdersNum(Integer ordersNum) {
        this.ordersNum = ordersNum;
    }

    public static CooperMsg newLoginSuccess(String msg) {
        CooperMsg cooperMsg = new CooperMsg();
        cooperMsg.setStatus(SUCCESS);
        cooperMsg.setMessage(msg);
        cooperMsg.setTime(DateUtils.format(new Date(), DateUtils.F_YMDHMS_));
        return cooperMsg;
    }

    public static CooperMsg newSuccess(Object obj) {
        return newSuccess(null, obj);
    }

    public static CooperMsg newSuccessMsg(String msg) {
        return newSuccess(msg, null);
    }

    public static CooperMsg newSuccess(String msg, Object obj) {
        CooperMsg cooperMsg = new CooperMsg();
        cooperMsg.setStatus(SUCCESS);
        cooperMsg.setMessage(msg);
        if (obj != null) {
            if (obj instanceof Collection) {
                cooperMsg.setOrdersNum(((Collection) obj).size());
            } else if (obj instanceof Object[]) {
                cooperMsg.setOrdersNum(((Object[]) obj).length);
            }
        }
        cooperMsg.setTime(DateUtils.format(new Date(), DateUtils.F_YMDHMS_));
        cooperMsg.setOrders(obj);
        log.info("CooperMsg Success! {}", cooperMsg.toString());
        return cooperMsg;
    }

    public static CooperMsg newFailed(String msg) {
        CooperMsg cooperMsg = new CooperMsg();
        cooperMsg.setStatus(FAILED);
        cooperMsg.setCode("0");
        cooperMsg.setMessage(msg);
        log.info("CooperMsg failed! {}", cooperMsg.toString());
        return cooperMsg;
    }

    public static CooperMsg newFailed(String code, String msg) {
        CooperMsg cooperMsg = new CooperMsg();
        cooperMsg.setStatus(FAILED);
        cooperMsg.setCode(code);
        cooperMsg.setMessage(msg);
        log.info("CooperMsg failed! {}", cooperMsg.toString());
        return cooperMsg;
    }

    @Override
    public String toString() {
        return "CooperMsg{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", orders=" + orders +
                ", ordersNum=" + ordersNum +
                ", time='" + time + '\'' +
                '}';
    }
}
