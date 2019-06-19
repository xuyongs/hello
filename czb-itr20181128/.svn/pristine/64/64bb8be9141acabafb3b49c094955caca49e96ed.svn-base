package com.agent.czb.common.rest;

import com.agent.czb.common.rest.exception.BaseServiceException;
import com.agent.czb.common.rest.exception.NotLoginException;

/**
 * @author linkai
 * @since 16/7/15
 */
public class Errors {

    public static BaseServiceException baseServiceException(String message) {
        return new BaseServiceException(message);
    }

    public static NotLoginException notLoginException(String message) {
        return new NotLoginException(message);
    }

    public static RuntimeException runtimeException(String message) {
        return new RuntimeException(message);
    }

    public static void check(boolean b, String msg) {
        if (!b) {
            throw baseServiceException(msg);
        }
    }
}
