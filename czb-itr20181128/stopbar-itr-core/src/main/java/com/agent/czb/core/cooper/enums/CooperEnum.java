package com.agent.czb.core.cooper.enums;

/**
 * Created by Administrator on 2016/1/7.
 */
public interface CooperEnum {
    enum IoState {
        INPUT("1", "进"), OUTPUT("2", "出"), CF_INPUT("3", "重复进"), CF_OUTPUT("4", "重复出"),SURE_OUT("5", "确认出");

        private String value;
        private String desc;
        IoState(String value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public static IoState get(String value){
            IoState[] ens = IoState.values();
            for (IoState en : ens) {
                if(en.toValue().equals(value)){
                    return en;
                }
            }
            return null;
        }

        public static String getDesc(String value){
            IoState state = get(value);
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
