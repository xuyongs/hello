package com.agent.czb.core.park.enums;

import com.agent.czb.common.enums.BaseEnum;

/**
 * Created by Administrator on 2016/1/24.
 */
public interface ParkGateEnum {
    enum State implements BaseEnum {
        IN("1", "进"), OUT("2", "出"), RE_IN("3", "重复进"), RE_OUT ("4", "重复出"),
        PAY("100", "已支付");

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
    
    enum Type implements BaseEnum {
        IN("0", "短租车辆"), OUT("1", "月租车辆");

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
        	Type type = get(value);
            if (type != null) {
                return type.toDesc();
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
