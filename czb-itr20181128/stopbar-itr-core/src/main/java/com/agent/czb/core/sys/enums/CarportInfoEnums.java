package com.agent.czb.core.sys.enums;

/**
 * Created by Administrator on 2016/1/4.
 */
public interface CarportInfoEnums {
    enum State {
        OPEN("0", "上线"), BUY("1", "已购买"), CLOSE("2", "下线"),ADMOFFLINE("3","管理员手动下线"),USEROFFLINE("4","用户手动下线"),EXAMINE("5","待审核"),SHORT_BUY("6","短租已购买");

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
        CARPORT("1", "车位"),
        PRIVATE_CARPORT("5", "私人车位"), SYS_CARPORT("101", "系统车位");

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

    enum CollectState {
        NOT_COLLECT("0", "取消"), COLLECT("1", "收藏");

        private String value;
        private String desc;
        CollectState(String value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public static CollectState get(String value){
            CollectState[] ens = CollectState.values();
            for (CollectState en : ens) {
                if(en.toValue().equals(value)){
                    return en;
                }
            }
            return null;
        }

        public static String getDesc(String value){
            CollectState state = get(value);
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
