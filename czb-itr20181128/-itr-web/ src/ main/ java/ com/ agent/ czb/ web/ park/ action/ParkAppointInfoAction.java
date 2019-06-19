package com.agent.czb.web.park.action;

import java.util.Collections;
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

import com.agent.czb.core.park.model.ParkAppointInfoModel;
import com.agent.czb.core.park.service.ParkAppointInfoService;
import com.agent.czb.web.system.action.BaseAction;
import com.agent.czb.web.system.utils.HtmlUtil;

/**
 * 停车场预约信息控制层









 */
@Controller
@RequestMapping("/parkAppointInfo")
public class ParkAppointInfoAction extends BaseAction {
    private final static Logger log = LoggerFactory.getLogger(ParkAppointInfoAction.class);

    @Autowired
    private ParkAppointInfoService parkAppointInfoService;

    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request) throws Exception{
        Map<String, Object> context = getRootMap();
        context.put("dataList", Collections.EMPTY_LIST);
        return forword("park/parkAppointInfo", context);
    }

    @RequestMapping("/dataList")
    public void dataList(HttpServletRequest request, HttpServletResponse response) throws Exception{
        PageHelp pageHelp = PageHelp.newPageHelp(request).page(parkAppointInfoService);
        List<ParkAppointInfoModel> list= (List<ParkAppointInfoModel>) pageHelp.getData();
        for (ParkAppointInfoModel model : list) {
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
        ParkAppointInfoModel model = parkAppointInfoService.selectById(id);
        if(model == null){
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        setInfo(model);
        context.put(SUCCESS, true);
        context.put("data", model);
        HtmlUtil.writerJson(response, context);
    }

    public void setInfo(ParkAppointInfoModel model) {

    }

    @RequestMapping("/add")
    public void add(ParkAppointInfoModel model, HttpServletResponse response) throws Exception{
        parkAppointInfoService.insert(model);
        sendSuccessMessage(response, "保存成功");
    }

    @RequestMapping("/save")
    public void save(ParkAppointInfoModel model, HttpServletResponse response) throws Exception{
        if (model.getId() == null) {
            parkAppointInfoService.insert(model);
        } else {
            parkAppointInfoService.updateBySelective(model);
        }
        sendSuccessMessage(response, "保存成功");
    }

    @RequestMapping("/delete")
    public void delete(Long[] id, HttpServletResponse response) throws Exception{
        parkAppointInfoService.delete(id);
        sendSuccessMessage(response, "删除成功");
    }
}
