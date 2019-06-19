package com.agent.czb.core.sys.enums;

/**
 * Created by Administrator on 2016/1/27.
 */
public interface PayOrderEnums {
    enum State {
        NOT_PAY("1", "未支付"), BEN_PAY("2", "已支付");

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

    enum Type {
        ZFB_CARPORT("1", "支付宝购买车位"), ZFB_RECHARGE("2", "支付宝充值"),
        YE_CARPORT("3", "余额购买车位"), SD_RECHARGE("4", "手动充值"),
        ZFB_AMOUNT("5", "支付宝缴费"), YE_AMOUNT("6", "余额缴费"), USER_WITHDRAW("7", "提现"),CP_RECHARGE("8", "车位收入"),
    	WECHAT_CARPORT("11", "微信购买车位"), WECHAT_RECHARGE("12", "微信充值"),WECHAT_AMOUNT("15", "微信缴费");

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

    enum RefType {
        车位("1", "车位"), 停车场缴费("2", "停车场缴费");

        private String value;
        private String desc;
        RefType(String value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public static RefType get(String value){
            RefType[] ens = RefType.values();
            for (RefType en : ens) {
                if(en.toValue().equals(value)){
                    return en;
                }
            }
            return null;
        }

        public static String getDesc(String value){
            RefType state = get(value);
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
