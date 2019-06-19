package com.agent.czb.service.sys.restful;

import com.agent.czb.common.rest.PageHelp;
import com.agent.czb.common.rest.ResultHelp;
import com.agent.czb.common.rest.restful.BasisRestful;
import com.agent.czb.core.sys.enums.LocaInfoEnums;
import com.agent.czb.core.sys.model.LocaInfoModel;
import com.agent.czb.core.sys.model.ServeInfoModel;
import com.agent.czb.core.sys.model.UserInfoModel;
import com.agent.czb.core.sys.service.LocaInfoService;
import com.agent.czb.core.sys.service.ServeInfoService;
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
 * 设备服务信息Restful接口
 */
@RestController
@RequestMapping("services/serveInfo")
public class ServeInfoRestful extends BasisRestful {
    private static Logger log = LoggerFactory.getLogger(ServeInfoRestful.class);

    @Autowired
    private ServeInfoService serveInfoService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private LocaInfoService locaInfoService;

    @RequestMapping(value = "/locaList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultHelp locaList(HttpServletRequest request, String type, String mapLng, String mapLat) {
        if (StringUtils.isEmpty(mapLng)) {
            return ResultHelp.newFialResult("经度不为空");
        }
        if (StringUtils.isEmpty(mapLat)) {
            return ResultHelp.newFialResult("纬度不为空");
        }
        LocaInfoEnums.Type tempType = LocaInfoEnums.Type.get(type);
        if (tempType == null || !(tempType.equals(LocaInfoEnums.Type.CHARGING_PILE) || tempType.equals(LocaInfoEnums.Type.GAS_STATION))) {
            return ResultHelp.newFialResult("类型不正确");
        }
        PageHelp pageHelp = PageHelp.newPageHelp(request);
        pageHelp.params().put("type", type);
        List<LocaInfoModel> list = locaInfoService.locaList(pageHelp.params());
        List<ServeInfoModel> parkInfolList = new ArrayList<>();
        for (LocaInfoModel model : list) {
            ServeInfoModel serveInfo = serveInfoService.selectById(model.getRefId());
            serveInfo.setDistance(model.getDistance());
            serveInfo.setMapLng(model.getMapLng());
            serveInfo.setMapLat(model.getMapLat());
            setInfo(serveInfo);
            parkInfolList.add(serveInfo);
        }
        return ResultHelp.newResult(parkInfolList);
    }

    @RequestMapping(value = "/pageList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp pageList(HttpServletRequest request) {
        PageHelp pageHelp = PageHelp.newPageHelp(request);
        pageHelp.page(serveInfoService);
        List<ServeInfoModel> list= (List<ServeInfoModel>) pageHelp.getData();
        for (ServeInfoModel model : list) {
            setInfo(model);
        }
        return ResultHelp.newResult(pageHelp);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp get(Long id) {
        ServeInfoModel model = serveInfoService.selectById(id);
        setInfo(model);
        return ResultHelp.newResult(model);
    }

    public void setInfo(ServeInfoModel model) {
        model.setUserName(userInfoService.getUserName(model.getUserId()));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp add(ServeInfoModel model, String type, String fileUrls, String mapLng, String mapLat) {
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
        LocaInfoEnums.Type tempType = LocaInfoEnums.Type.get(type);
        if (tempType == null || !(tempType.equals(LocaInfoEnums.Type.CHARGING_PILE) || tempType.equals(LocaInfoEnums.Type.GAS_STATION))) {
            return ResultHelp.newFialResult("类型不正确");
        }
        int rows = serveInfoService.insert(model, type, urls, mapLng, mapLat);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp edit(ServeInfoModel model) {
        int rows = serveInfoService.updateBySelective(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp del(Long id) {
        int rows = serveInfoService.delete(id);
        return ResultHelp.newResult(rows);
    }
}
