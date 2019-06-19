package com.agent.czb.common.rest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author linkai
 */
public class RestfulSesssionUtils {
    // 注册验证码
    public final static String REGIST_CODE = "REGIST_CODE";
    // 重置密码验证码
    public final static String RESET_PWD_CODE = "RESET_PWD_CODE";

    private static Map<String, Object> session;
    static {
        session = new HashMap<>();
    }

    public static void setAttribute(String key, Object data) {
        session.put(key, data);
    }

    public static Object getAttribute(String key) {
        return session.get(key);
    }

    public static void removeAttribute(String key) {
        if (session.containsKey(key)) {
            session.remove(key);
        }
    }
}
