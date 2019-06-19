package com.agent.czb.web.system.action;

import com.agent.czb.common.json.JSONUtils;
import com.agent.czb.web.system.bean.SysUser;
import com.agent.czb.web.system.edit.MyEditor;
import com.agent.czb.web.system.utils.HtmlUtil;
import com.agent.czb.web.system.utils.SessionUtils;
import com.agent.czb.web.system.utils.URLUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BaseAction {

    public final static String SUCCESS = "success";
    public final static String MSG = "msg";
    public final static String DATA = "data";
    public final static String LOGOUT_FLAG = "logoutFlag";

    protected HttpServletRequest request;
    protected HttpServletResponse response;


    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
        binder.registerCustomEditor(int.class, new MyEditor());
    }

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

    /**
     * 从session中获取用户信息
     * @return SysUser
     */
    public SysUser getSysUser(){
        return (SysUser) request.getSession(true).getAttribute(SessionUtils.SESSION_USER);
    }

    /**
     * 从session中获取用户名称
     * @return SysUser
     */
    public Integer getSysUserId(){
        SysUser user = (SysUser) request.getSession(true).getAttribute(SessionUtils.SESSION_USER);
        if (user != null) {
            return user.getId();
        }
        return null;
    }

    /**
     * 从session中获取用户名称
     * @return SysUser
     */
    public String getSysUserName(){
        SysUser user = (SysUser) request.getSession(true).getAttribute(SessionUtils.SESSION_USER);
        if (user != null) {
            return user.getUserName();
        }
        return null;
    }

    /**
     * 获取IP地址
     *
     * @param request
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    /**
     * 所有ActionMap 统一从这里获取
     *
     * @return
     */
    public Map<String, Object> getRootMap() {
        Map<String, Object> rootMap = new HashMap<String, Object>();
        //添加url到 Map中
        rootMap.putAll(URLUtils.getUrlMap());
        return rootMap;
    }

    public ModelAndView forword(String viewName, Map context) {
        return new ModelAndView(viewName, context);
    }

    public ModelAndView error(String errMsg) {
        return new ModelAndView("error");
    }

    /**
     * 提示成功信息
     *
     * @param message
     */
    public void sendSuccessMessage(HttpServletResponse response, String message) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(SUCCESS, true);
        result.put(MSG, message);
        HtmlUtil.writerJson(response, result);
    }

    /**
     * 提示成功信息
     *
     * @param message
     */
    public void sendSuccessMessage(String message) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(SUCCESS, true);
        result.put(MSG, message);
        HtmlUtil.writerJson(response, result);
    }

    /**
     * 提示保存文件成功信息
     *
     * @param message
     */
    public void sendSaveFileSuccessMessage(HttpServletResponse response, String message) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(SUCCESS, true);
        result.put(MSG, message);
        response.setContentType("text/html");
        HtmlUtil.writerJson(response, JSONUtils.objectToDateFormatJson(result));
    }

    /**
     * 提示失败信息
     *
     * @param message
     */
    public void sendFailureMessage(HttpServletResponse response, String message) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(SUCCESS, false);
        result.put(MSG, message);
        HtmlUtil.writerJson(response, result);
    }

    /**
     * 提示失败信息
     *
     * @param message
     */
    public void sendFailureMessage(String message) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(SUCCESS, false);
        result.put(MSG, message);
        HtmlUtil.writerJson(response, result);
    }
}
