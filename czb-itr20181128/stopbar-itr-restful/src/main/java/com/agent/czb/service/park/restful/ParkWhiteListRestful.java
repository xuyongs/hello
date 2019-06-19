package com.agent.czb.service.park.restful;

import com.agent.czb.common.rest.PageHelp;
import com.agent.czb.common.rest.ResultHelp;
import com.agent.czb.common.rest.restful.BasisRestful;
import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.core.park.enums.ParkWhiteEnum;
import com.agent.czb.core.park.model.ParkWhiteListModel;
import com.agent.czb.core.park.service.ParkSysInfoService;
import com.agent.czb.core.park.service.ParkWhiteListService;
import com.agent.czb.core.sys.model.CarportInfoModel;
import com.agent.czb.core.sys.model.UserInfoModel;
import com.agent.czb.core.sys.service.CarportInfoService;
import com.agent.czb.core.sys.service.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 停车场白名单表Restful接口
 */
@RestController
@RequestMapping("services/parkWhiteList")
public class ParkWhiteListRestful extends BasisRestful {
    private static Logger log = LoggerFactory.getLogger(ParkWhiteListRestful.class);

    @Autowired
    private ParkWhiteListService parkWhiteListService;

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ParkSysInfoService parkSysInfoService;
    @Autowired
    private CarportInfoService carportInfoService;


    @RequestMapping(value = "/pageList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp pageList(HttpServletRequest request) {
        PageHelp pageHelp = PageHelp.newPageHelp(request);
        if (StringUtils.isNotEmpty(request.getParameter("phone"))) {
            Long userId = userInfoService.selectUserIdByPhone(request.getParameter("phone"));
            pageHelp.addParam("userId", userId);
        }
        pageHelp.page(parkWhiteListService);
        List<ParkWhiteListModel> list= (List<ParkWhiteListModel>) pageHelp.getData();
        for (ParkWhiteListModel model : list) {
            setInfo(model);
        }
        return ResultHelp.newResult(pageHelp);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp get(Long id) {
        ParkWhiteListModel model = parkWhiteListService.selectById(id);
        setInfo(model);
        return ResultHelp.newResult(model);
    }

    public void setInfo(ParkWhiteListModel model) {
        Long carportid= model.getCarportId();
        if(carportid != null){
         CarportInfoModel carportInfoModel= carportInfoService.selectById(carportid);
         model.setCycle(carportInfoModel.getModel());
         model.setCarportPrice(carportInfoModel.getPrice());

        }

        model.setStateStr(ParkWhiteEnum.State.getDesc(model.getState()));
        model.setParkSysInfo(parkSysInfoService.selectByParkCode(model.getParkCode()));
        model.setUserInfo(userInfoService.selectById(model.getUserId()));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp add(ParkWhiteListModel model) {
        if (model.getUserId() == null || model.getUserId() == 0) {
            return ResultHelp.newFialResult("用户标识符不能为空");
        }
        if (StringUtils.isEmpty(model.getParkCode())) {
            return ResultHelp.newFialResult("停车场编码不能为空");
        }
        if (StringUtils.isEmpty(model.getPlateNum())) {
            return ResultHelp.newFialResult("车牌号不能为空");
        }
        // 设置用户姓名
        String userName = userInfoService.getUserName(model.getUserId());
        model.setUserName(userName);

        model.setId(null);
        model.setState(ParkWhiteEnum.State.共享.toValue());
        model.setCreateTime(new Date());
        int rows = parkWhiteListService.insert(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp edit(ParkWhiteListModel model) {
        int rows = parkWhiteListService.updateBySelective(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp del(Long id) {
        int rows = parkWhiteListService.delete(id);
        return ResultHelp.newResult(rows);
    }

}
