package com.agent.czb.common.rest;

import java.io.Serializable;

public class ResultHelp implements Serializable {
    private static final long serialVersionUID = 5667043577288458232L;

    public static final int STATUS_FAIL = 0;
    public static final int STATUS_SUCCESS = 1;
    public static final String CODE_NOT_LOGIN = "403";
    public static final String CODE_BASE_ERROR = "401";

    /**
     * 接口查询状态，0：表示失败，1：表示成功
     * 默认值：成功
     */
    private int status = STATUS_SUCCESS;
    /**
     * 查询数据
     */
    private Object data;

    /**
     * 查询返回代码
     */
    private String code;
    /**
     * 查询返回错误消息（只有当 status 为0时，该字段才有值）
     */
    private Object msg;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static ResultHelp newResult(Object data) {
        ResultHelp resultHelp = new ResultHelp();
        resultHelp.setData(data);
        return resultHelp;
    }

    public static ResultHelp newFialResult(String msg) {
        ResultHelp resultHelp = new ResultHelp();
        resultHelp.setStatus(STATUS_FAIL);
        resultHelp.setMsg(msg);
        return resultHelp;
    }

    public static ResultHelp newResult(String msg) {
        ResultHelp resultHelp = new ResultHelp();
        resultHelp.setStatus(STATUS_SUCCESS);
        resultHelp.setMsg(msg);
        return resultHelp;
    }

    public static ResultHelp newFialResult(String code, String msg) {
        ResultHelp resultHelp = new ResultHelp();
        resultHelp.setCode(code);
        resultHelp.setStatus(STATUS_FAIL);
        resultHelp.setMsg(msg);
        return resultHelp;
    }
}
