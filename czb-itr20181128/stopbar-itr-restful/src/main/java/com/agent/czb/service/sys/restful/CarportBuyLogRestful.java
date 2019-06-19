package com.agent.czb.service.sys.restful;

import com.agent.czb.common.rest.PageHelp;
import com.agent.czb.common.rest.ResultHelp;
import com.agent.czb.common.rest.restful.BasisRestful;
import com.agent.czb.core.sys.enums.CarportInfoEnums;
import com.agent.czb.core.sys.enums.FileInfoEnums;
import com.agent.czb.core.sys.model.CarportBuyLogModel;
import com.agent.czb.core.sys.model.CarportInfoModel;
import com.agent.czb.core.sys.model.FileUpdateInfoModel;
import com.agent.czb.core.sys.model.UserInfoModel;
import com.agent.czb.core.sys.service.CarportBuyLogService;
import com.agent.czb.core.sys.service.CarportInfoService;
import com.agent.czb.core.sys.service.FileUpdateInfoService;
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
import java.util.List;

/**
 * 车位购买日志Restful接口
 */
@RestController
@RequestMapping("services/carportBuyLog")
public class CarportBuyLogRestful extends BasisRestful {
    private static Logger log = LoggerFactory.getLogger(CarportBuyLogRestful.class);

    @Autowired
    private CarportBuyLogService carportBuyLogService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private CarportInfoService carportInfoService;

    @Autowired
    private FileUpdateInfoService fileUpdateInfoService;

    @RequestMapping(value = "/pageList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp pageList(HttpServletRequest request) {
        PageHelp pageHelp = PageHelp.newPageHelp(request);
        pageHelp.addParam("pagerOrder", "order by end_time desc");
        pageHelp.page(carportBuyLogService);
        List<CarportBuyLogModel> list= (List<CarportBuyLogModel>) pageHelp.getData();
        for (CarportBuyLogModel model : list) {
            setInfo(model, request);
        }
        return ResultHelp.newResult(pageHelp);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp get(Long id) {
        CarportBuyLogModel model = carportBuyLogService.selectById(id);
        setInfo(model);
        return ResultHelp.newResult(model);
    }

    public void setInfo(CarportBuyLogModel model) {
        setInfo(model, null);
    }

    public void setInfo(CarportBuyLogModel model, HttpServletRequest request) {
        model.setUserName(userInfoService.getUserName(model.getUserId()));
        Long carportId = model.getCarportId();
        CarportInfoModel carportInfo = carportInfoService.selectById(carportId);
        if (carportInfo.getState() != null) {
            carportInfo.setStateStr(CarportInfoEnums.State.getDesc(model.getState()));
        }
        model.setCarportInfo(carportInfo);
        carportInfo.setUserName(userInfoService.getUserName(carportInfo.getUserId()));
        if (request != null) {
            String showImgs = request.getParameter("showImgs");
            if (StringUtils.equals(showImgs, "true") || StringUtils.equals(showImgs, "1")) {
                List<String> strings = fileUpdateInfoService.selectUrlsByTypeRvId(carportId, FileInfoEnums.RvType.CARPORT.name());
                carportInfo.setImgs(strings);
            }
            String showSeller = request.getParameter("showSeller");
            if (StringUtils.isNotEmpty(showSeller)) {
                UserInfoModel userInfo = userInfoService.selectById(carportInfo.getUserId());
                model.setSeller(userInfo);
            }
        }
        model.setUserName(userInfoService.getUserName(model.getUserId()));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp add(CarportBuyLogModel model) {
        int rows = carportBuyLogService.insert(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp edit(CarportBuyLogModel model) {
        int rows = carportBuyLogService.updateBySelective(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp del(Long id) {
        int rows = carportBuyLogService.delete(id);
        return ResultHelp.newResult(rows);
    }
}
