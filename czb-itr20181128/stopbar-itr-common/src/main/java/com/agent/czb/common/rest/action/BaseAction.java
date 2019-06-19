package com.agent.czb.common.rest.action;

import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author linkai
 */
public class BaseAction {

    protected HttpServletRequest request;
    protected HttpServletResponse response;

    /**
     * @Title: setReqAndResp
     * @Description: 初始化ServletAPI
     * @param request
     * @param response
     */
    @ModelAttribute
    public void setReqAndResp(HttpServletRequest request,
                              HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public HttpSession getSession() {
        return request.getSession();
    }

}
