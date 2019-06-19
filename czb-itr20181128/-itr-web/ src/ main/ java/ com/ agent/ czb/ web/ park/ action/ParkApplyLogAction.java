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

import com.agent.czb.core.park.model.ParkApplyLogModel;
import com.agent.czb.core.park.service.ParkApplyLogService;
import com.agent.czb.web.system.action.BaseAction;
import com.agent.czb.web.system.utils.HtmlUtil;

/**
 * 停车场申请日志控制层




 */
@Controller
@RequestMapping("/parkApplyLog")
public class ParkApplyLogAction extends BaseAction {
    private final static Logger log = LoggerFactory.getLogger(ParkApplyLogAction.class);

    @Autowired
    private ParkApplyLogService parkApplyLogService;

    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request) throws Exception{
        Map<String, Object> context = getRootMap();
        context.put("dataList", Collections.EMPTY_LIST);
        return forword("park/parkApplyLog", context);
    }

    @RequestMapping("/dataList")
    public void dataList(HttpServletRequest request, HttpServletResponse response) throws Exception{
        PageHelp pageHelp = PageHelp.newPageHelp(request).page(parkApplyLogService);
        List<ParkApplyLogModel> list= (List<ParkApplyLogModel>) pageHelp.getData();
        for (ParkApplyLogModel model : list) {
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
        ParkApplyLogModel model = parkApplyLogService.selectById(id);
        if(model == null){
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        setInfo(model);
        context.put(SUCCESS, true);
        context.put("data", model);
        HtmlUtil.writerJson(response, context);
    }

    public void setInfo(ParkApplyLogModel model) {

    }

    @RequestMapping("/add")
    public void add(ParkApplyLogModel model, HttpServletResponse response) throws Exception{
        parkApplyLogService.insert(model);
        sendSuccessMessage(response, "保存成功");
    }

    @RequestMapping("/save")
    public void save(ParkApplyLogModel model, HttpServletResponse response) throws Exception{
        if (model.getId() == null) {
            parkApplyLogService.insert(model);
        } else {
            parkApplyLogService.updateBySelective(model);
        }
        sendSuccessMessage(response, "保存成功");
    }

    @RequestMapping("/delete")
    public void delete(Long[] id, HttpServletResponse response) throws Exception{
        parkApplyLogService.delete(id);
        sendSuccessMessage(response, "删除成功");
    }
}
