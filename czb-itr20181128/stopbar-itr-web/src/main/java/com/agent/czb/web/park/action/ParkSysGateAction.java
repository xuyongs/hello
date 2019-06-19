package com.agent.czb.web.park.action;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.agent.czb.common.rest.PageHelp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.agent.czb.core.park.model.ParkSysGateModel;
import com.agent.czb.core.park.service.ParkSysGateService;
import com.agent.czb.web.system.action.BaseAction;
import com.agent.czb.web.system.bean.SysUser;
import com.agent.czb.web.system.service.SysUserService;
import com.agent.czb.web.system.utils.HtmlUtil;
import com.agent.czb.web.system.utils.SessionUtils;
import com.sun.jna.platform.win32.Netapi32Util.User;

/**
 * 停车场系统闸机信息控制层
 */
@Controller
@RequestMapping("/parkSysGate")
public class ParkSysGateAction extends BaseAction {
    private final static Logger log = LoggerFactory.getLogger(ParkSysGateAction.class);

    @Autowired
    private ParkSysGateService parkSysGateService;
    
    @Autowired
    private SysUserService<SysUser> sysUserService;

    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request) throws Exception{
        Map<String, Object> context = getRootMap();
        context.put("dataList", Collections.EMPTY_LIST);
        return forword("park/parkSysGate", context);
    }

    @RequestMapping("/dataList")
    public void dataList(HttpServletRequest request, HttpServletResponse response) throws Exception{
        PageHelp pageHelp = PageHelp.newPageHelp(request).page(parkSysGateService);
        List<ParkSysGateModel> list= (List<ParkSysGateModel>) pageHelp.getData();
        for (ParkSysGateModel model : list) {
            setInfo(model);
        }
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("total", pageHelp.getTotal());
        jsonMap.put("rows", pageHelp.getData());
        HtmlUtil.writerJson(response, jsonMap);
    }

    @RequestMapping("/getId")
    public void getId(Long id, HttpServletResponse response) throws Exception{
        Map<String,Object> context = getRootMap();
        ParkSysGateModel model = parkSysGateService.selectById(id);
        if(model == null){
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        setInfo(model);
        context.put(SUCCESS, true);
        context.put("data", model);
        HtmlUtil.writerJson(response, context);
    }

    public void setInfo(ParkSysGateModel model) {
    	try {
			SysUser queryById = sysUserService.queryById(model.getUserId());
			model.setUserId(queryById.getNickName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @RequestMapping("/add")
    public void add(ParkSysGateModel model,HttpServletResponse response) throws Exception{
        parkSysGateService.insert(model);
        sendSuccessMessage(response, "保存成功");
    }

    @RequestMapping("/save")
    public void save(ParkSysGateModel model, HttpServletResponse response) throws Exception{
    	Date day=new Date();    
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    	if (model.getId() == null) {
        	SysUser user = SessionUtils.getUser(request);
        	model.setCreateTime(day);
        	model.setUpdateTime(day);
        	model.setUserId(String.valueOf(user.getId()));
            parkSysGateService.insert(model);
        } else {
        	model.setUpdateTime(day);
            parkSysGateService.updateBySelective(model);
        }
        sendSuccessMessage(response, "保存成功");
    }

    @RequestMapping("/delete")
    public void delete(Long[] id, HttpServletResponse response) throws Exception{
        parkSysGateService.delete(id);
        sendSuccessMessage(response, "删除成功");
    }
}
