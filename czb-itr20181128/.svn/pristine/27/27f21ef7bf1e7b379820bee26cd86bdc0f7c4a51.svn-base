package com.agent.czb.web.sys.action;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.agent.czb.common.rest.PageHelp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.agent.czb.common.rest.ResultHelp;
import com.agent.czb.core.sys.enums.LocaInfoEnums;
import com.agent.czb.core.sys.model.LocaInfoModel;
import com.agent.czb.core.sys.service.LocaInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.agent.czb.core.sys.model.ParkInfoModel;
import com.agent.czb.core.sys.service.ParkInfoService;
import com.agent.czb.web.system.action.BaseAction;
import com.agent.czb.web.system.bean.SysUser;
import com.agent.czb.web.system.utils.HtmlUtil;
import com.agent.czb.web.system.utils.SessionUtils;

/**
 * 停车场信息控制层
 */
@Controller
@RequestMapping("/parkInfo")
public class ParkInfoAction extends BaseAction {
    private final static Logger log = LoggerFactory.getLogger(ParkInfoAction.class);
    @Autowired
    private ParkInfoService parkInfoService;
    @Autowired
    private LocaInfoService locaInfoService;

    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request) throws Exception{
        Map<String, Object> context = getRootMap();
        context.put("dataList", Collections.EMPTY_LIST);
        return forword("sys/parkInfo", context);
    }

    @RequestMapping("/dataList")
    public void dataList(HttpServletRequest request, HttpServletResponse response) throws Exception{
        PageHelp pageHelp = PageHelp.newPageHelp(request).page(parkInfoService);
        List<ParkInfoModel> list= (List<ParkInfoModel>) pageHelp.getData();
        for (ParkInfoModel model : list) {
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
        ParkInfoModel model = parkInfoService.selectById(id);
        if(model == null){
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        setInfo(model);
        context.put(SUCCESS, true);
        context.put("data", model);
        HtmlUtil.writerJson(response, context);
    }

    public void setInfo(ParkInfoModel model) {
         LocaInfoModel loca = locaInfoService.getLoca(LocaInfoEnums.Type.PARK.toValue(), model.getId());
    	 
    	if (loca != null) { 
            model.setMapLat(loca.getMapLat());
            model.setMapLng(loca.getMapLng());
        }
    }

    @RequestMapping("/add")
    public void add(ParkInfoModel model, HttpServletResponse response) throws Exception{
        model.setId(null);
        model.setUserId(1L);
        if (StringUtils.isEmpty(model.getTitle())) {
            sendFailureMessage("车位标题称不能为空");
            return;
        }
        if (StringUtils.isEmpty(model.getAddr())) {
            sendFailureMessage("车位地址不能为空");
            return;
        }
        if (StringUtils.isEmpty(model.getMapLng())) {
            sendFailureMessage("经度不能为空");
            return;
        }
        if (StringUtils.isEmpty(model.getMapLat())) {
            sendFailureMessage("纬度不能为空");
            return;
        }
        parkInfoService.insert(model, null, model.getMapLng(), model.getMapLat());
        sendSuccessMessage(response, "保存成功");
    }
 //添加修改停车场信息
    @RequestMapping("/save")
    public void save(ParkInfoModel model, String mapLat,String mapLng, HttpServletResponse response) throws Exception{
    	LocaInfoModel locaInfoModel=new LocaInfoModel();
        if (model.getId() == null) {
        	SysUser user = SessionUtils.getUser(request);
        	model.setUserId(Long.valueOf(user.getId()));
        	model.setCreateTime(new Date());
            parkInfoService.insert(model);
            locaInfoModel.setRefId(model.getId());
            locaInfoModel.setType(String.valueOf(model.getType()));
        	locaInfoModel.setMapLat(mapLat);
        	locaInfoModel.setMapLng(mapLng);
        	locaInfoModel.setCreateTime(new Date());
        	locaInfoModel.setUpdateTime(new Date());
        	locaInfoService.add(locaInfoModel);
        } else { 
            LocaInfoModel locaInfo= locaInfoService.selectByRefId(model.getId());
            locaInfo.setMapLat(model.getMapLat());
            locaInfo.setMapLng(model.getMapLng());
            locaInfo.setUpdateTime(new Date());
        	locaInfoService.update(locaInfo);
        	model.setUpdateTime(new Date());
            parkInfoService.updateBySelective(model);
        }
        sendSuccessMessage(response, "保存成功");
    }

    @RequestMapping("/delete")
    public void delete(Long[] id, HttpServletResponse response) throws Exception{
        parkInfoService.delete(id);
        sendSuccessMessage(response, "删除成功");
    }
}
