package com.agent.czb.service.utils;

import com.agent.czb.common.rest.Errors;
import com.agent.czb.common.spring.SpringContextUtil;
import com.agent.czb.core.sys.model.UserInfoModel;
import com.agent.czb.common.rest.exception.NotLoginException;
import com.agent.czb.core.sys.service.UserInfoService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author linkai
 * @since 16/7/9
 */
public class SysSessionUtils {
    private static final String TOKEN = "token";
    private static EHCacheUtils ehCacheUtils;
    private static EHCacheUtils.CacheHelp sessionCache;
    private static final String USER_INFO = "USER_INFO";
    private static UserInfoService userInfoService;

    public static Map getSession(HttpServletRequest request) {
        String token = getToken(request);
        return getSession(token);
    }

    public static UserInfoModel isLogin(HttpServletRequest request) {
        UserInfoModel userInfo = SysSessionUtils.getUserInfo(request);
        if (userInfo == null) {
            throw Errors.notLoginException("用户未登录");
        }
        return userInfo;
    }

    public static void login(HttpServletRequest request, UserInfoModel userInfo) {
//        String token = UUID.randomUUID().toString();
        String token = userInfo.getLoginName();
        userInfo.setToken(token);
        Map<String, Object> map = new HashMap<>();
        map.put(USER_INFO, userInfo);
        setUserSession(request, token, map);
    }

    private static void setUserSession(HttpServletRequest request, String token, Map<String, Object> map) {
        sessionCache().put(token, map);
    }

    public static Object getAttribute(HttpServletRequest request, String name) {
        String token = getToken(request);
        return getSession(token).get(name);
    }

    public static void setAttribute(HttpServletRequest request, String name, Object obj) {
        String token = getToken(request);
        getSession(token).put(name, obj);
    }

    public static String getToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN);
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter(TOKEN);
            if (StringUtils.isEmpty(token)) {
                return null;
            }
        }
        return token;
    }

    public static void setUserInfo(HttpServletRequest request, UserInfoModel userInfo) {
        setAttribute(request, USER_INFO, userInfo);
    }

    public static UserInfoModel getUserInfo(HttpServletRequest request) {
        Object obj = getAttribute(request, USER_INFO);
        if (obj == null) {
            return null;
        }
        return (UserInfoModel) obj;
    }

    private static Map getSession(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new NotLoginException("用户未登陆");
        }
        if (sessionCache().isExist(token)) {
            return sessionCache().get(token, Map.class);
        } else {
            UserInfoModel userInfo = userInfoService.selectUserByToken(token);
            if (userInfo == null) {
                throw new NotLoginException("用户未登陆");
            }
            Map data = new HashMap();
            data.put(USER_INFO, userInfo);
            sessionCache().put(token, data);
            return data;
        }
    }

    private static EHCacheUtils.CacheHelp sessionCache() {
        if (ehCacheUtils == null) {
            ehCacheUtils = (EHCacheUtils) SpringContextUtil.getBean("eHCacheUtils");
            userInfoService = (UserInfoService) SpringContextUtil.getBean("userInfoService");
            sessionCache = ehCacheUtils.getCacheHelp(EHCacheUtils.SESSION_CACHE);
        }
        return sessionCache;
    }
}
