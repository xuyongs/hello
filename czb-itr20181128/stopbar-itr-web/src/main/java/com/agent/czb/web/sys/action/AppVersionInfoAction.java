package com.agent.czb.web.sys.action;

import com.agent.czb.common.rest.PageHelp;
import com.agent.czb.core.sys.enums.AppVersionEnums;
import com.agent.czb.core.sys.model.AppVersionInfoModel;
import com.agent.czb.core.sys.service.AppVersionInfoService;
import com.agent.czb.web.system.action.BaseAction;
import com.agent.czb.web.system.bean.SysUser;
import com.agent.czb.web.system.utils.HtmlUtil;
import com.agent.czb.web.system.utils.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 应用版本信息控制层
 */
@Controller
@RequestMapping("/appVersionInfo")
public class AppVersionInfoAction extends BaseAction {
    private final static Logger log = LoggerFactory.getLogger(AppVersionInfoAction.class);

    @Autowired
    private AppVersionInfoService appVersionInfoService;

    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request) throws Exception {
        Map<String, Object> context = getRootMap();
        context.put("dataList", Collections.EMPTY_LIST);
        return forword("sys/appVersionInfo", context);
    }

    @RequestMapping("/dataList")
    public void dataList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageHelp pageHelp = PageHelp.newPageHelp(request).page(appVersionInfoService);
        List<AppVersionInfoModel> list = (List<AppVersionInfoModel>) pageHelp.getData();
        for (AppVersionInfoModel model : list) {
            setInfo(model);
        }
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("total", pageHelp.getTotal());
        jsonMap.put("rows", pageHelp.getData());
        HtmlUtil.writerJson(response, jsonMap);
    }

    @RequestMapping("/getId")
    public void getId(Long id, HttpServletResponse response) throws Exception {
        Map<String, Object> context = getRootMap();
        AppVersionInfoModel model = appVersionInfoService.selectById(id);
        if (model == null) {
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        setInfo(model);
        context.put(SUCCESS, true);
        context.put("data", model);
        HtmlUtil.writerJson(response, context);
    }

    public void setInfo(AppVersionInfoModel model) {
        if (model.getType() != null) {
            model.setTypeStr(AppVersionEnums.Type.getDesc(model.getType()));
        }
    }

    @RequestMapping("/add")
    public void add(AppVersionInfoModel model, HttpServletResponse response) throws Exception {
        appVersionInfoService.insert(model);
        sendSuccessMessage(response, "保存成功");
    }

    @RequestMapping("/save")
    public void save(AppVersionInfoModel model, HttpServletResponse response) throws Exception {
        SysUser user = SessionUtils.getUser(request);
        if (model.getId() == null) {
            model.setCreateTime(new Date());
            model.setUpdateTime(new Date());
            model.setSysUserId((long) user.getId());
            appVersionInfoService.insert(model);
        } else {
            model.setUpdateTime(new Date());
            appVersionInfoService.updateBySelective(model);
        }
        sendSuccessMessage(response, "保存成功");
    }

    @RequestMapping("/delete")
    public void delete(Long[] id, HttpServletResponse response) throws Exception {
        appVersionInfoService.delete(id);
        sendSuccessMessage(response, "删除成功");
    }
}
