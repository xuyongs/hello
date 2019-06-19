package com.agent.czb.core.user.enums;

/**
 * @author linkai
 */
public interface UserInfoEnum {
    enum Sex {
        MALE(1, "男"), FEMALE(2, "女");

        private Integer value;
        private String desc;
        Sex(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public static Sex get(Integer value){
            Sex[] ens = Sex.values();
            for (Sex en : ens) {
                if(en.toValue().equals(value)){
                    return en;
                }
            }
            return null;
        }

        public static String getDesc(Integer value){
            Sex state = get(value);
            if (state != null) {
                return state.toDesc();
            }
            return null;
        }

        public Integer toValue() {
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
