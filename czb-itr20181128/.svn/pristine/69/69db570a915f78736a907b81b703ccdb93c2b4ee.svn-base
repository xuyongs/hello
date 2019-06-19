package com.agent.czb.service.interceptor;

import com.agent.czb.common.rest.RestUtils;
import com.agent.czb.common.rest.ResultHelp;
import com.agent.czb.common.utils.HtmlUtil;
import com.agent.czb.common.rest.exception.BaseServiceException;
import com.agent.czb.common.rest.exception.NotLoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

public class RestfulLogInterceptor extends HandlerInterceptorAdapter {
    private final static Logger log = LoggerFactory.getLogger(RestfulLogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("method={}, url={}, params={}", request.getMethod(), getUri(request), RestUtils.request2map(request).toString());
        return super.preHandle(request, response, handler);
    }

    /**
     * 异常处理
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (ex != null) {
            logger(request, ex);
            ResultHelp resultHelp;
            if (ex instanceof NotLoginException) {
                resultHelp = ResultHelp.newFialResult(ResultHelp.CODE_NOT_LOGIN, ex.getMessage());
            } else if (ex instanceof BaseServiceException) {
                resultHelp = ResultHelp.newFialResult(ResultHelp.CODE_BASE_ERROR, ex.getMessage());
            } else {
                resultHelp = ResultHelp.newFialResult(ex.getMessage());
            }
            HtmlUtil.writerJson(response, resultHelp);
        } else {
            super.afterCompletion(request, response, handler, null);
        }
    }

    /**
     * 记录日志
     */
    public void logger(HttpServletRequest request, Exception ex) {
        StringBuilder msg = new StringBuilder();
        msg.append("[uri＝").append(request.getRequestURI()).append("]");
        Enumeration<String> enumer = request.getParameterNames();
        while (enumer.hasMoreElements()) {
            String name = enumer.nextElement();
            String[] values = request.getParameterValues(name);
            msg.append("异常拦截日志 [").append(name).append("=");
            if (values != null) {
                int i = 0;
                for (String value : values) {
                    i++;
                    msg.append(value);
                    if (i < values.length) {
                        msg.append("｜");
                    }
                }
            }
            msg.append("]");
        }
        log.error(msg.toString(), ex);
    }

    private String getUri(HttpServletRequest request) {
//        System.out.println("request.getRequestURI() == >" + request.getRequestURI());
//        System.out.println("request.getRequestURL() == >" + request.getRequestURL().toString());
//        System.out.println("request.getContextPath() == >" + request.getContextPath());
//        System.out.println("request.getServletPath() == >" + request.getServletPath());
//        System.out.println("request.getServerName() == >" + request.getServerName());
//        request.getRequestURI() == >/kdb-jf-service/services/exchGoods/exchLogList
//        request.getRequestURL() == >http://localhost:8081/kdb-jf-service/services/exchGoods/exchLogList
//        request.getContextPath() == >/kdb-jf-service
//        request.getServletPath() == >/services/exchGoods/exchLogList
//        request.getServerName() == >localhost
        return request.getRequestURI();
    }

}
