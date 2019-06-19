package com.agent.czb.core.park.enums;

import com.agent.czb.common.enums.BaseEnum;

/**
 * Created by Administrator on 2016/1/21.
 */
public interface ParkOrderEnum {

    enum Type implements BaseEnum {
        APP_PAY("1", "APP缴费"), CASH_PAY("2", "现金缴费"),
        AUTO_PAY("3", "自动缴费"), NEW_PAY("4", "新订单支付"),
        CALCULATE_PRICE("5", "计算价格"), ZFB_PAY("6", "APP缴费"),
        BUY_CARPORT("100", "购买车位"),
        RESERVE("200", "预约");

        private String value;
        private String desc;
        Type(String value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public static Type get(String value){
            Type[] ens = Type.values();
            for (Type en : ens) {
                if(en.toValue().equals(value)){
                    return en;
                }
            }
            return null;
        }

        public static String getDesc(String value){
            Type state = get(value);
            if (state != null) {
                return state.toDesc();
            }
            return null;
        }

        public String toValue() {
            return value;
        }

        public String toDesc() {
            return desc;
        }

        public String toString() {
            return desc;
        }
    }

    enum State implements BaseEnum {
        NOT_PAY("1", "未支付"), BEE_PAY("2", "已支付"),
        WAIT_PAY("3", "等待支付"), PAY_COMPLETE ("4", "支付完成"),
        PAY_CLOSE("5", "支付结束"), PAY_ERROR("6", "支付错误"),
        CALCULATE_PRICE("7", "计算价格"), CALCULATE_COMPLETE("8", "计算完成"),

        RESERVE_INIT("100", "预约"), RESERVE_COMPLETE("101", "预约完成"),
        RESERVE_ERROR("102", "预约失败"), RESERVE_CANCEL("103", "预约取消"),
        RESERVE_TIMEOUT("104", "预约超时"),
        CANCEL("200", "已取消"), TIMEOUT("201", "超时"),
        BUY("300", "已购买"), CLOSE("301", "下线"),
        
        
    	ADVANCE__UNPAID("401","预缴费未支付"), ADVANCE__PAID ("402","预缴费已支付"),
        OVERTIME_UNPAID("403","超时未支付"), OVERTIME__PAID("404","超时已支付");
    	
    	
    	
        private String value;
        private String desc;
        State(String value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public static State get(String value){
            State[] ens = State.values();
            for (State en : ens) {
                if(en.toValue().equals(value)){
                    return en;
                }
            }
            return null;
        }

        public static String getDesc(String value){
            State state = get(value);
            if (state != null) {
                return state.toDesc();
            }
            return null;
        }

        public String toValue() {
            return value;
        }

        public String toDesc() {
            return desc;
        }

        public String toString() {
            return desc;
        }
    }
}
