package com.agent.czb.core.sys.enums;

/**
 * @author linkai
 * @since 16/9/26
 */
public interface AppVersionEnums {
    enum Type {
        IOS(0, "IOS"), 安卓(1, "安卓"), 微信(2, "微信");

        private Integer value;
        private String desc;

        Type(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public static Type get(Integer value) {
            Type[] ens = Type.values();
            for (Type en : ens) {
                if (en.toValue().equals(value)) {
                    return en;
                }
            }
            return null;
        }

        public static String getDesc(Integer value) {
            Type state = get(value);
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
