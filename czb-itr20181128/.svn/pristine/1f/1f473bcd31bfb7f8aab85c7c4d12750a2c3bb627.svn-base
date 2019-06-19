package com.agent.czb.web.park.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.agent.czb.common.rest.PageHelp;
import com.agent.czb.core.park.enums.ParkSysInfoEnum;
import com.agent.czb.core.park.model.ParkSysInfoModel;
import com.agent.czb.core.park.service.ParkSysInfoService;
import com.agent.czb.core.sys.enums.LocaInfoEnums;
import com.agent.czb.core.sys.model.LocaInfoModel;
import com.agent.czb.core.sys.service.LocaInfoService;
import com.agent.czb.web.system.action.BaseAction;
import com.agent.czb.web.system.utils.HtmlUtil;

/**
 * 停车场系统信息控制层
 */
@Controller
@RequestMapping("/parkSysInfo")
public class ParkSysInfoAction extends BaseAction {
    private final static Logger log = LoggerFactory.getLogger(ParkSysInfoAction.class);

    @Autowired
    private ParkSysInfoService parkSysInfoService;

    @Autowired
    private LocaInfoService locaInfoService;

    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request) throws Exception {
        Map<String, Object> context = getRootMap();
        context.put("dataList", Collections.EMPTY_LIST);
        return forword("park/parkSysInfo", context);
    }

    @RequestMapping("/dataList")
    public void dataList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageHelp pageHelp = PageHelp.newPageHelp(request).page(parkSysInfoService);
        List<ParkSysInfoModel> list = (List<ParkSysInfoModel>) pageHelp.getData();
        List<ParkSysInfoModel> lists=new ArrayList<ParkSysInfoModel>();
        for (ParkSysInfoModel model : list) {
            if (model.getTenMinutePrice()!=null&&model.getTenMinutePrice()!=0){
                model.setTenMinutePriceDob((double)(model.getTenMinutePrice()/100d));
            }
            if (model.getMaxAmount()!=null&&model.getMaxAmount()!=0){
                model.setMaxAmountDob((double)(model.getMaxAmount()/100d));
            }
            setInfo(model);
            lists.add(model);
        }
        pageHelp.setData(lists);
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("total", pageHelp.getTotal());
        jsonMap.put("rows", pageHelp.getData());
        HtmlUtil.writerJson(response, jsonMap);
    }

    @RequestMapping("/getId")
    public void getId(Long id, HttpServletResponse response) throws Exception {
        Map<String, Object> context = getRootMap();
        ParkSysInfoModel model = parkSysInfoService.selectById(id);
        if (model == null) {
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        if (model.getTenMinutePrice()!=null&&model.getTenMinutePrice()!=0){
            model.setTenMinutePriceDob((double)(model.getTenMinutePrice()/100d));
        }
        if (model.getMaxAmount()!=null&&model.getMaxAmount()!=0){
            model.setMaxAmountDob((double)(model.getMaxAmount()/100d));
        }
        setInfo(model);
        context.put(SUCCESS, true);
        context.put("data", model);
        HtmlUtil.writerJson(response, context);
    }

    public void setInfo(ParkSysInfoModel model) {
        LocaInfoModel loca = locaInfoService.getLoca(LocaInfoEnums.Type.SYS_PARK.toValue(), model.getId());
        if (loca != null) {
            model.setMapLng(loca.getMapLng());
            model.setMapLat(loca.getMapLat());
        }
        //model.setConstructionTypeStr(ParkSysInfoEnum.State.getDesc(model.getConstructionType()));
    }

    @RequestMapping("/add")
    public void add(ParkSysInfoModel model, HttpServletResponse response) throws Exception {
        parkSysInfoService.save(model);
        sendSuccessMessage(response, "保存成功");
    }
 
    @RequestMapping("/save")
    public void save(ParkSysInfoModel model, HttpServletResponse response) throws Exception {
        parkSysInfoService.save(model);
        sendSuccessMessage(response, "保存成功");
    }

    @RequestMapping("/delete")
    public void delete(Long[] id, HttpServletResponse response) throws Exception {
        parkSysInfoService.delete(id);
        sendSuccessMessage(response, "删除成功");
    }
}
