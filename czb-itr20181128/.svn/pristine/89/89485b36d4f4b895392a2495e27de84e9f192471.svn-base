package com.agent.czb.web.apilist.action;

import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.core.apilist.model.ApiListModel;
import com.agent.czb.core.apilist.service.ApiListService;
import com.agent.czb.web.system.action.BaseAction;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * aip功能清单Restful接口
 */
@Controller
@RequestMapping("/apilist")
public class ApiListAction extends BaseAction {
    private static final String loginName = "pwd";
    private static final String loginPwd = "1314";
    private static final String isLogin = "isLogin";
    private static Logger log = LoggerFactory.getLogger(ApiListAction.class);

    @Autowired
    private ApiListService apiListService;

    /**
     * api接口列表
     */
    @RequestMapping("index")
    public String index(HttpServletRequest request) throws Exception {
        String pwd = request.getParameter(loginName);
        if (StringUtils.equals(pwd, loginPwd)) {
            request.getSession().setAttribute(isLogin, "1");
        }
        request.setAttribute("list", apiListService.pageList(FrameUtils.newMap("is_delete", "0")));
        return getResult("index");
    }

    /**
     * 新增
     */
    @RequestMapping("/toadd")
    public String toadd() throws Exception {
        if (!isLogin(request)) {
            throw new RuntimeException("没有权限");
        }
        return getResult("edit");
    }

    /**
     * 修改
     */
    @RequestMapping("/edit/{id}")
    public String toedit(HttpServletRequest request, @PathVariable("id") Long id) throws Exception {
        if (!isLogin(request)) {
            throw new RuntimeException("没有权限");
        }
        if (id == null) {
            throw new RuntimeException("参数有误");
        }
        request.setAttribute("model", this.apiListService.selectById(id));
        return getResult("edit");
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public String save(HttpServletRequest request, ApiListModel al) throws Exception {
        if (!isLogin(request)) {
            throw new RuntimeException("没有权限");
        }
        if (al.getId() != null) {
            this.apiListService.update(al);
        } else {
            this.apiListService.insert(al);
        }
        return "redirect:/apilist/index.do";
    }

    /**
     * 保存
     */
    @RequestMapping("/del/{id}")
    public String del(@PathVariable("id") Long id) throws Exception {
        if (!isLogin(request)) {
            throw new RuntimeException("没有权限");
        }
        if (id == null) {
            throw new RuntimeException("id不能为空");
        }
        ApiListModel am = this.apiListService.selectById(id);
        if (am != null) {
            am.setIsDelete(Short.valueOf("1"));
            this.apiListService.update(am);
        }
        return "redirect:/apilist/index.do";
    }

    public String getResult(String s) {
        return "/apilist/" + s;
    }

    private boolean isLogin(HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(isLogin);
        return attribute != null && attribute.toString().equals("1");
    }
}
