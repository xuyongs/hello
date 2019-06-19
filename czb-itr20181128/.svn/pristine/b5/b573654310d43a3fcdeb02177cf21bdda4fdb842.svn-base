package com.agent.czb.service.sys.restful;

import com.agent.czb.common.rest.PageHelp;
import com.agent.czb.common.rest.ResultHelp;
import com.agent.czb.common.rest.restful.BasisRestful;
import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.core.sys.enums.LocaInfoEnums;
import com.agent.czb.core.sys.model.LocaInfoModel;
import com.agent.czb.core.sys.model.ParkInfoModel;
import com.agent.czb.core.sys.model.UserInfoModel;
import com.agent.czb.core.sys.service.LocaInfoService;
import com.agent.czb.core.sys.service.ParkInfoService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 停车场信息Restful接口
 */
@RestController
@RequestMapping("services/parkInfo")
public class ParkInfoRestful extends BasisRestful {
    private static Logger log = LoggerFactory.getLogger(ParkInfoRestful.class);

    @Autowired
    private ParkInfoService parkInfoService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private LocaInfoService locaInfoService;

    @RequestMapping(value = "/locaList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp locaList(HttpServletRequest request, String mapLng, String mapLat) {
        if (StringUtils.isEmpty(mapLng)) {
            return ResultHelp.newFialResult("经度不为空");
        }
        if (StringUtils.isEmpty(mapLat)) {
            return ResultHelp.newFialResult("纬度不为空");
        }
        PageHelp pageHelp = PageHelp.newPageHelp(request);
        pageHelp.params().put("type", LocaInfoEnums.Type.PARK.toValue());
        List<LocaInfoModel> list = locaInfoService.locaList(pageHelp.params());
        List<ParkInfoModel> parkInfolList = new ArrayList<>();
        for (LocaInfoModel model : list) {
            ParkInfoModel parkInfo = parkInfoService.selectById(model.getRefId());
            parkInfo.setMapLng(model.getMapLng());
            parkInfo.setMapLat(model.getMapLat());
            parkInfo.setDistance(model.getDistance());
            setInfo(parkInfo);
            parkInfolList.add(parkInfo);
        }
        return ResultHelp.newResult(parkInfolList);
    }

    @RequestMapping(value = "/pageList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp pageList(HttpServletRequest request) {
        PageHelp pageHelp = PageHelp.newPageHelp(request);
        pageHelp.page(parkInfoService);
        List<ParkInfoModel> list= (List<ParkInfoModel>) pageHelp.getData();
        for (ParkInfoModel model : list) {
            setInfo(model);
        }
        return ResultHelp.newResult(pageHelp);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp get(Long id) {
        ParkInfoModel model = parkInfoService.selectById(id);
        setInfo(model);
        return ResultHelp.newResult(model);
    }

    public void setInfo(ParkInfoModel model) {
        model.setUserName(userInfoService.getUserName(model.getUserId()));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp add(ParkInfoModel model, String fileUrls, String mapLng, String mapLat) {
        if (model.getUserId() == null) {
            return ResultHelp.newFialResult("用户标识不能为空");
        }
        if (StringUtils.isEmpty(model.getTitle())) {
            return ResultHelp.newFialResult("车位标题称不能为空");
        }
        if (StringUtils.isEmpty(model.getAddr())) {
            return ResultHelp.newFialResult("车位地址不能为空");
        }
        if (StringUtils.isEmpty(mapLng)) {
            return ResultHelp.newFialResult("经度不能为空");
        }
        if (StringUtils.isEmpty(mapLat)) {
            return ResultHelp.newFialResult("纬度不能为空");
        }
        String[] urls = null;
        if (StringUtils.isNotEmpty(fileUrls)) {
            urls = fileUrls.split(",");
        }
        model.setId(null);
        int rows = parkInfoService.insert(model, urls, mapLng, mapLat);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp edit(ParkInfoModel model) {
        int rows = parkInfoService.updateBySelective(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp del(Long id) {
        int rows = parkInfoService.deleteAndLocaInfo(id);
        return ResultHelp.newResult(rows);
    }
}
