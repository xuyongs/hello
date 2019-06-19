package com.agent.czb.core.park.enums;

import com.agent.czb.common.enums.BaseEnum;

/**
 * Created by Administrator on 2016/1/20.
 */
public interface ParkWhiteEnum {
    enum State implements BaseEnum {
        车位("0", "车主"), 共享("1", "共享");

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
