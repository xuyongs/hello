package com.agent.czb.web.system.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.agent.czb.web.system.bean.DataDict;
import com.agent.czb.web.system.model.DataDictModel;
import com.agent.czb.web.system.service.DataDictService;
import com.agent.czb.web.system.utils.HtmlUtil;

@Controller
@RequestMapping("/dataDict")
public class DataDictAction extends BaseAction {
    private final static Logger log = Logger.getLogger(DataDictAction.class);

    @Autowired
    private DataDictService<DataDict> dataDictService;

    @RequestMapping("/list")
    public ModelAndView list(DataDictModel model, HttpServletRequest request) throws Exception{
        Map<String, Object> context = getRootMap();
        List<DataDict> dataList = dataDictService.queryByList(model);
        context.put("dataList", dataList);
        return forword("system/dataDict", context);
    }

    @RequestMapping("/dataList")
    public void dataList(DataDictModel model, HttpServletResponse response) throws Exception{
        List<DataDict> dataList = dataDictService.queryByList(model);
        Map<String, Object> jsonMap = new HashMap<String,Object>();
        jsonMap.put("total", model.getPager().getRowCount());
        jsonMap.put("rows", dataList);
        HtmlUtil.writerJson(response, jsonMap);
    }

    @RequestMapping("/getId")
    public void getId(Integer dictId, HttpServletResponse response) throws Exception{
        Map<String,Object> context = getRootMap();
        DataDict bean = dataDictService.queryById(dictId);
        if(bean == null){
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        context.put(SUCCESS, true);
        context.put("data", bean);
        HtmlUtil.writerJson(response, context);
    }

    @RequestMapping("/add")
    public void add(DataDict bean, HttpServletResponse response) throws Exception{
        dataDictService.add(bean);
        sendSuccessMessage(response, "保存成功");
    }

    @RequestMapping("/save")
    public void save(DataDict bean, HttpServletResponse response) throws Exception{
        if (bean.getDictId() == null) {
            dataDictService.add(bean);
        } else {
            dataDictService.updateBySelective(bean);
        }
        sendSuccessMessage(response, "保存成功");
    }

    @RequestMapping("/delete")
    public void delete(Integer[] dictId, HttpServletResponse response) throws Exception{
        dataDictService.delete(dictId);
        sendSuccessMessage(response, "删除成功");
    }
}
