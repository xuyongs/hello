package com.agent.czb.core.sys.enums;

/**
 * @author linkai
 * @since 16/8/8
 */
public interface UserWithdrawalInfoEnums {
    enum State {
        申请("0", "申请"), 同意("1", "同意"), 拒绝("2", "拒绝");

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
