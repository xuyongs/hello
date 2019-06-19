package com.agent.czb.core.sys.enums;

/**
 * Created by Administrator on 2016/1/4.
 */
public interface WalletInfoEnums {
    enum OpType {
        DEL("0", "减钱"), ADD("1", "加钱"), ZFB("401", "支付宝"),WECHAT("501", "微信");

        private String value;
        private String desc;
        OpType(String value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public static OpType get(String value){
            OpType[] ens = OpType.values();
            for (OpType en : ens) {
                if(en.toValue().equals(value)){
                    return en;
                }
            }
            return null;
        }

        public static String getDesc(String value){
            OpType state = get(value);
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

    enum Type {
        BUY_CARPORT("1", "买车位"), ADD_MONEY("2", "手动加钱"), AUTO_PAY("3", "自动扣款"), ORDER_PAY("4", "订单支付"),
        SELL_CARPORT("301", "卖车位"), ZFB_ADD("302", "支付宝充值"), USER_WITHDRAWAL("303", "用户提现"), WECHAT_ADD("502", "微信充值");

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
}
