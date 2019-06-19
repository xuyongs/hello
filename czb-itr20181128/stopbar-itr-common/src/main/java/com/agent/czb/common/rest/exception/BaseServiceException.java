package com.agent.czb.common.rest.exception;

/**
 * @author linkai
 * @since 16/7/15
 */
public class BaseServiceException extends RuntimeException {
    public BaseServiceException() {
    }

    public BaseServiceException(String message) {
        super(message);
    }

}
