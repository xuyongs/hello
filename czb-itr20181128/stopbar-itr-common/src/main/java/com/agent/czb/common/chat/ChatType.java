package com.agent.czb.common.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author linkai
 * @since 16/8/10
 */
public interface ChatType {
    String USER_CHAT = "user:chat";
    String USER_LOGIN = "user:login";
    String PARK_CHAT = "park:chat";
    String PARK_LOGIN = "park:login";
    String SYS_CHAT = "sys:chat";

    @Data
    @ToString
    @Accessors(chain = true)
    class UserMsg {
        private String from;
        private String to;
        private String room;
        private String message;

        public UserMsg() {}

    }

    @Data
    @ToString
    @Accessors(chain = true)
    class ParkMsg {
        private String id;
        private String type; // 100:正常; 101:失败;
        private String parkCode;
        private String message;

        public ParkMsg() {}
    }

    @Data
    @ToString
    @Accessors(chain = true)
    class SysMsg {
        private String message;
        public SysMsg() {}
    }
}
