package com.agent.czb.service.sys.restful;

import com.agent.czb.common.rest.PageHelp;
import com.agent.czb.common.rest.ResultHelp;
import com.agent.czb.common.rest.restful.BasisRestful;
import com.agent.czb.core.sys.enums.AppVersionEnums;
import com.agent.czb.core.sys.model.AppVersionInfoModel;
import com.agent.czb.core.sys.service.AppVersionInfoService;
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
 * 应用版本信息Restful接口
 */
@RestController
@RequestMapping("services/appVersionInfo")
public class AppVersionInfoRestful extends BasisRestful {
    private static Logger log = LoggerFactory.getLogger(AppVersionInfoRestful.class);

    @Autowired
    private AppVersionInfoService appVersionInfoService;

    @RequestMapping(value = "/pageList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp pageList(HttpServletRequest request) {
        PageHelp pageHelp = PageHelp.newPageHelp(request);
        pageHelp.page(appVersionInfoService);
        List<AppVersionInfoModel> list = (List<AppVersionInfoModel>) pageHelp.getData();
        for (AppVersionInfoModel model : list) {
            setInfo(model);
        }
        return ResultHelp.newResult(pageHelp);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp get(Long id) {
        AppVersionInfoModel model = appVersionInfoService.selectById(id);
        setInfo(model);
        return ResultHelp.newResult(model);
    }

    public void setInfo(AppVersionInfoModel model) {
        if (model.getType() != null) {
            model.setTypeStr(AppVersionEnums.Type.getDesc(model.getType()));
        }
    }

    /*@RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp add(AppVersionInfoModel model) {
        int rows = appVersionInfoService.insert(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp edit(AppVersionInfoModel model) {
        int rows = appVersionInfoService.updateBySelective(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp del(Long id) {
        int rows = appVersionInfoService.delete(id);
        return ResultHelp.newResult(rows);
    }*/
}
