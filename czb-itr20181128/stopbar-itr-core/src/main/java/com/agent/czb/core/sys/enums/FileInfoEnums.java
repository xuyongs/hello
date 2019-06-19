package com.agent.czb.core.sys.enums;

/**
 * @author linkai
 */
public interface FileInfoEnums {
    enum State {
        A("正常"), B("删除"), C("未使用");

        private String desc;
        State(String desc) {
            this.desc = desc;
        }
        public String toString() {
            return desc;
        }
    }

    enum RvType {
        ICON("头像"), CARPORT("车位"), PARK("停车场"), SERVE("服务设备"), OTHER("其他"), SPIMG("系统停车场图片");

        private String desc;
        RvType(String desc) {
            this.desc = desc;
        }
        public String toString() {
            return desc;
        }
    }

    enum Type {
        file("文件"), img("图片");

        private String desc;
        Type(String desc) {
            this.desc = desc;
        }
        public String toString() {
            return desc;
        }
    }
}
