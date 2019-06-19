package com.agent.czb.core.sys.enums;

/**
 * 位置类型枚举
 * Created by Administrator on 2016/1/4.
 */
public interface LocaInfoEnums {
    enum Type {
        CARPORT("1", "车位"), CHARGING_PILE("2", "充电桩"), GAS_STATION("3", "加油站"), PARK("4", "停车场"),
        PRIVATE_CARPORT("5", "私人车位"), SYS_CARPORT("101", "系统车位"), SYS_PARK("102", "系统停车场");

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
