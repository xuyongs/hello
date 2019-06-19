package com.agent.czb.web.park.action;

import com.agent.czb.common.enums.EnumUtils;
import com.agent.czb.common.rest.PageHelp;
import com.agent.czb.common.spring.SpringContextUtil;
import com.agent.czb.core.park.enums.ParkWhiteEnum;
import com.agent.czb.core.park.model.ParkWhiteListModel;
import com.agent.czb.core.park.service.ParkSysInfoService;
import com.agent.czb.core.park.service.ParkWhiteListService;
import com.agent.czb.core.sys.model.CarportInfoModel;
import com.agent.czb.core.sys.model.UserInfoModel;
import com.agent.czb.core.sys.service.CarportInfoService;
import com.agent.czb.core.sys.service.UserInfoService;
import com.agent.czb.web.system.action.BaseAction;
import com.agent.czb.web.system.utils.HtmlUtil;
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
 * 停车场白名单表控制层
 */
@Controller
@RequestMapping("/parkWhiteList")
public class ParkWhiteListAction extends BaseAction {
    private final static Logger log = LoggerFactory.getLogger(ParkWhiteListAction.class);

    @Autowired
    private ParkWhiteListService parkWhiteListService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ParkSysInfoService parkSysInfoService;
    @Autowired
    CarportInfoService carportInfoService;

    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request) throws Exception{
        Map<String, Object> context = getRootMap();
        context.put("dataList", Collections.EMPTY_LIST);
        context.put("stateList", EnumUtils.getEnumKeyVal(ParkWhiteEnum.State.class));
        return forword("park/parkWhiteList", context);
    }

    @RequestMapping("/dataList")
    public void dataList(HttpServletRequest request, HttpServletResponse response) throws Exception{
        PageHelp pageHelp = PageHelp.newPageHelp(request);
        pageHelp.addParam("pagerOrder", "order by id desc");
        pageHelp.page(parkWhiteListService);
        List<ParkWhiteListModel> list= (List<ParkWhiteListModel>) pageHelp.getData();
        for (ParkWhiteListModel model : list) {
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
        ParkWhiteListModel model = parkWhiteListService.selectById(id);
        if(model == null){
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        setInfo(model);
        context.put(SUCCESS, true);
        context.put("data", model);
        HtmlUtil.writerJson(response, context);
    }

    public void setInfo(ParkWhiteListModel model) {
        model.setStateStr(ParkWhiteEnum.State.getDesc(model.getState()));
//        model.setParkSysInfo(parkSysInfoService.selectByParkCode(model.getParkCode()));
        if (model.getUserId() != null) {
            UserInfoModel userInfoModel = userInfoService.selectById(model.getUserId());
            if (userInfoModel != null) {
                model.setUserName(userInfoModel.getNickName());
            }
        }
    }

    @RequestMapping("/add")
    public void add(ParkWhiteListModel model, HttpServletResponse response) throws Exception{
        model.setCreateTime(new Date());
        parkWhiteListService.insert(model);
        sendSuccessMessage(response, "保存成功");
    }

    @RequestMapping("/save")
    public void save(ParkWhiteListModel model, HttpServletResponse response) throws Exception{
        if (model.getId() == null) {
            
           List<ParkWhiteListModel> parkWhiteListModels = parkWhiteListService.selectByUserId(model.getUserId());
      	  for (ParkWhiteListModel pwlm : parkWhiteListModels) {
            	if (pwlm.getParkCode().equals(model.getParkCode())&&pwlm.getCarportId()!=null) {
            		//CarportInfoService carportInfoService=SpringContextUtil.getBean(CarportInfoService.class);
            		CarportInfoModel carportInfoModel = carportInfoService.selectById(pwlm.getCarportId());
            		Date currentDate=new Date();
            		//查出发布有效的车位
            		if (currentDate.before(carportInfoModel.getEffTime())) {
            			String plateNum = parkWhiteListService.selectByCarportId(carportInfoModel.getId()).getPlateNum();
            			if (model.getPlateNum().equals(plateNum)) { 
            				sendFailureMessage(response, "您的车牌在本停车场已经发布了一个有效的车位");
						}
            			/*else {
							    model.setCreateTime(new Date());
					            parkWhiteListService.insert(model);
						}*/
    				}
    			}
    		}
      	model.setCreateTime(new Date());
        parkWhiteListService.insert(model);
      	  
      	  
            	
        } else {
        	
        	model.setUpdateTime(new Date());
            parkWhiteListService.updateBySelective(model);
        }
        sendSuccessMessage(response, "保存成功");
    }
    
    @RequestMapping("/delete")
    public void delete(Long[] id, HttpServletResponse response) throws Exception{
        parkWhiteListService.delete(id);
        sendSuccessMessage(response, "删除成功");
    }
}
