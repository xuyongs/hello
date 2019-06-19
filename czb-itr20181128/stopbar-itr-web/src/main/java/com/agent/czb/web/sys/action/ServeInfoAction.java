package com.agent.czb.web.sys.action;

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

import com.agent.czb.core.sys.model.ServeInfoModel;
import com.agent.czb.core.sys.service.ServeInfoService;
import com.agent.czb.web.system.action.BaseAction;
import com.agent.czb.web.system.utils.HtmlUtil;

/**
 * 设备服务信息控制层
 */
@Controller
@RequestMapping("/serveInfo")
public class ServeInfoAction extends BaseAction {
    private final static Logger log = LoggerFactory.getLogger(ServeInfoAction.class);

    @Autowired
    private ServeInfoService serveInfoService;

    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request) throws Exception{
        Map<String, Object> context = getRootMap();
        context.put("dataList", Collections.EMPTY_LIST);
        return forword("sys/serveInfo", context);
    }

    @RequestMapping("/dataList")
    public void dataList(HttpServletRequest request, HttpServletResponse response) throws Exception{
        PageHelp pageHelp = PageHelp.newPageHelp(request).page(serveInfoService);
        List<ServeInfoModel> list= (List<ServeInfoModel>) pageHelp.getData();
        for (ServeInfoModel model : list) {
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
        ServeInfoModel model = serveInfoService.selectById(id);
        if(model == null){
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        setInfo(model);
        context.put(SUCCESS, true);
        context.put("data", model);
        HtmlUtil.writerJson(response, context);
    }

    public void setInfo(ServeInfoModel model) {

    }

    @RequestMapping("/add")
    public void add(ServeInfoModel model, HttpServletResponse response) throws Exception{
        serveInfoService.insert(model);
        sendSuccessMessage(response, "保存成功");
    }

    @RequestMapping("/save")
    public void save(ServeInfoModel model, HttpServletResponse response) throws Exception{
        if (model.getId() == null) {
            serveInfoService.insert(model);
        } else {
            serveInfoService.updateBySelective(model);
        }
        sendSuccessMessage(response, "保存成功");
    }

    @RequestMapping("/delete")
    public void delete(Long[] id, HttpServletResponse response) throws Exception{
        serveInfoService.delete(id);
        sendSuccessMessage(response, "删除成功");
    }
}
