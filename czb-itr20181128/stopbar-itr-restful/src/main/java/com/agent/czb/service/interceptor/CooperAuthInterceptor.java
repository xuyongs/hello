package com.agent.czb.service.interceptor;

import com.agent.czb.common.utils.HtmlUtil;
import com.agent.czb.core.cooper.model.CooperMsg;
import com.agent.czb.service.utils.SessionUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * Created by Administrator on 2016/1/20.
 */
public class CooperAuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!SessionUtils.isLoginByPartSysUser(request)) {
            HtmlUtil.writerJson(response, CooperMsg.newFailed(CooperMsg.NOT_LOGIN, "用户未登录"));
            return false;
        }
        return super.preHandle(request, response, handler);
    }
}
