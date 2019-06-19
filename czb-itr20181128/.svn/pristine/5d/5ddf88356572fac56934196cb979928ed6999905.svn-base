package com.agent.czb.service.park.restful;

import com.agent.czb.common.rest.PageHelp;
import com.agent.czb.common.rest.RestUtils;
import com.agent.czb.common.rest.ResultHelp;
import com.agent.czb.common.rest.restful.BasisRestful;
import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.core.park.service.ParkSysInfoService;
import com.agent.czb.core.park.model.ParkSysInfoModel;
import com.agent.czb.core.sys.enums.LocaInfoEnums;
import com.agent.czb.core.sys.model.LocaInfoModel;
import com.agent.czb.core.sys.service.LocaInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 停车场系统信息Restful接口
 */
@RestController
@RequestMapping("services/parkSysInfo")
public class ParkSysInfoRestful extends BasisRestful {
    private static Logger log = LoggerFactory.getLogger(ParkSysInfoRestful.class);

    @Autowired
    private ParkSysInfoService parkSysInfoService;
    @Autowired
    private LocaInfoService locaInfoService;



    @RequestMapping(value = "/pageList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp pageList(HttpServletRequest request) {
        PageHelp pageHelp = PageHelp.newPageHelp(request);
        pageHelp.page(parkSysInfoService);
        List<ParkSysInfoModel> list= (List<ParkSysInfoModel>) pageHelp.getData();
        for (ParkSysInfoModel model : list) {
            setInfo(model);
        }
        return ResultHelp.newResult(pageHelp);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp get(Long id) {
        ParkSysInfoModel model = parkSysInfoService.selectById(id);
        setInfo(model);
        return ResultHelp.newResult(model);
    }

    @RequestMapping(value = "/getParkCode", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp get(String name) {
        if (StringUtils.isEmpty(name)) {
            return ResultHelp.newFialResult("名称不能空");
        }
        List<ParkSysInfoModel> list = parkSysInfoService.pageList(FrameUtils.newMap("name", name));
        if (list.isEmpty()) {
            return ResultHelp.newFialResult("未找到对应停车场编码");
        }
        ParkSysInfoModel model = list.get(0);
        return ResultHelp.newResult(model.getParkCode());
    }

    public void setInfo(ParkSysInfoModel model) {

        LocaInfoModel loca = locaInfoService.getLoca(LocaInfoEnums.Type.SYS_PARK.toValue(), model.getId());
        if (loca != null) {
            model.setMapLng(loca.getMapLng());
            model.setMapLat(loca.getMapLat());
        }

    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp add(ParkSysInfoModel model) {
        int rows = parkSysInfoService.insert(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp edit(ParkSysInfoModel model) {
        int rows = parkSysInfoService.updateBySelective(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp del(Long id) {
        int rows = parkSysInfoService.delete(id);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp check(HttpServletRequest request) {
        String parkCode =   request.getParameter("parkCode");
        if(StringUtils.isEmpty(parkCode)){
            return ResultHelp.newFialResult("停车场编码不能为空！");
        }
        int rows =  parkSysInfoService.checkStates(parkCode);
        return ResultHelp.newResult(rows);
    }
}
