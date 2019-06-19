package com.agent.czb.web.park.action;

import java.util.*;

import com.agent.czb.common.rest.PageHelp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.agent.czb.common.utils.MD5Utils;
import com.agent.czb.web.system.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.agent.czb.core.park.model.PartSysUserModel;
import com.agent.czb.core.park.service.PartSysUserService;
import com.agent.czb.core.sys.service.UserInfoService;
import com.agent.czb.web.system.action.BaseAction;
import com.agent.czb.web.system.bean.SysUser;
import com.agent.czb.web.system.service.SysUserService;
import com.agent.czb.web.system.utils.HtmlUtil;
import com.agent.czb.web.system.utils.SessionUtils;

/**
 * 停车场系统用户控制层
 */
@Controller
@RequestMapping("/partSysUser")
public class PartSysUserAction extends BaseAction {
    private final static Logger log = LoggerFactory.getLogger(PartSysUserAction.class);

    @Autowired
    private PartSysUserService partSysUserService;
    
    @Autowired
    private SysUserService<SysUser> sysUserService;

    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request) throws Exception {
        Map<String, Object> context = getRootMap();
        context.put("dataList", Collections.EMPTY_LIST);
        return forword("park/partSysUser", context);
    }

    @RequestMapping("/dataList")
    public void dataList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageHelp pageHelp = PageHelp.newPageHelp(request).page(partSysUserService);
        List<PartSysUserModel> list = (List<PartSysUserModel>) pageHelp.getData();
        for (PartSysUserModel model : list) {
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
        PartSysUserModel model = partSysUserService.selectById(id);
        if (model == null) {
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        setInfo(model);
        context.put(SUCCESS, true);
        context.put("data", model);
        HtmlUtil.writerJson(response, context);
    }

    public void setInfo(PartSysUserModel model) {
    	try {
			SysUser queryById = sysUserService.queryById(model.getUserId());
			model.setUserId(queryById.getNickName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @RequestMapping("/add")
    public void add(PartSysUserModel model, HttpServletResponse response) throws Exception {
        model.setCreateTime(new Date());
        partSysUserService.insert(model);
        sendSuccessMessage(response, "保存成功");
    }

    @RequestMapping("/save")
    public void save(PartSysUserModel model, HttpServletResponse response) throws Exception {
        if (model.getId() == null) {
        	SysUser user = SessionUtils.getUser(request);
            model.setLoginPwd(MD5Utils.MD5(model.getLoginPwd()));
            model.setCreateTime(new Date());
            model.setUserId(String.valueOf(user.getId()));;
            partSysUserService.insert(model);
        } else {
            PartSysUserModel partSysUser = partSysUserService.selectById(model.getId());
            if (!StringUtil.equals(model.getLoginPwd(), partSysUser.getLoginPwd())) {
                model.setLoginPwd(MD5Utils.MD5(model.getLoginPwd()));
            }
            model.setUpdateTime(new Date());
            partSysUserService.updateBySelective(model);
        }
        sendSuccessMessage(response, "保存成功");
    }

    @RequestMapping("/delete")
    public void delete(Long[] id, HttpServletResponse response) throws Exception {
        partSysUserService.delete(id);
        sendSuccessMessage(response, "删除成功");
    }
}
