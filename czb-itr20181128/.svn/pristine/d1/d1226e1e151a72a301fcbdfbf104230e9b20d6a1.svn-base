package com.agent.czb.service.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 判断用户是否登录
 * Created by Administrator on 2016/1/20.
 */
public class ServiceAuthInterceptor extends HandlerInterceptorAdapter {

    /**
     * 判断用户是否登录
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        UserInfoModel userInfo = SysSessionUtils.getUserInfo(request);
//        if (userInfo == null) {
//            HtmlUtil.writerJson(response, ResultHelp.newFialResult(ResultHelp.CODE_NOT_LOGIN, "用户未登录"));
//            return false;
//        }
        return super.preHandle(request, response, handler);
    }
}
