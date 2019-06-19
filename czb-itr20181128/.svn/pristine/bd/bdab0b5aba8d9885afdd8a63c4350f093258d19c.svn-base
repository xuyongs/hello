package com.agent.czb.service.utils;

import com.agent.czb.core.park.model.PartSysUserModel;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * Created by Administrator on 2016/1/20.
 */
public class SessionUtils {
    public static final String PART_SYS_USER = "PartSysUser";

    public static PartSysUserModel getPartSysUser(HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(PART_SYS_USER);
        if (attribute != null) {
            return (PartSysUserModel) attribute;
        }
        return null;
    }

    public static void setPartSysUser(HttpServletRequest request, PartSysUserModel user) {
        request.getSession().setAttribute(PART_SYS_USER, user);
    }

    public static boolean isLoginByPartSysUser(HttpServletRequest request) {
        PartSysUserModel partSysUser = SessionUtils.getPartSysUser(request);
        return partSysUser != null && partSysUser.getId() != null;
    }
}
