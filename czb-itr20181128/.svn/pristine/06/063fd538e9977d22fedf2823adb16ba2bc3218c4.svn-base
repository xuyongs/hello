package com.agent.czb.service.park.restful;

import com.agent.czb.common.rest.PageHelp;
import com.agent.czb.common.rest.RestUtils;
import com.agent.czb.common.rest.ResultHelp;
import com.agent.czb.common.rest.restful.BasisRestful;
import com.agent.czb.core.park.service.PartSysUserService;
import com.agent.czb.core.park.model.PartSysUserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 停车场系统用户Restful接口
 */
@RestController
@RequestMapping("services/partSysUser")
public class PartSysUserRestful extends BasisRestful {
    private static Logger log = LoggerFactory.getLogger(PartSysUserRestful.class);

    @Autowired
    private PartSysUserService partSysUserService;

    @RequestMapping(value = "/pageList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp pageList(HttpServletRequest request) {
        PageHelp pageHelp = PageHelp.newPageHelp(request);
        pageHelp.page(partSysUserService);
        List<PartSysUserModel> list= (List<PartSysUserModel>) pageHelp.getData();
        for (PartSysUserModel model : list) {
            setInfo(model);
        }
        return ResultHelp.newResult(pageHelp);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp get(Long id) {
        PartSysUserModel model = partSysUserService.selectById(id);
        setInfo(model);
        return ResultHelp.newResult(model);
    }

    public void setInfo(PartSysUserModel model) {

    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp add(PartSysUserModel model) {
        int rows = partSysUserService.insert(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp edit(PartSysUserModel model) {
        int rows = partSysUserService.updateBySelective(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp del(Long id) {
        int rows = partSysUserService.delete(id);
        return ResultHelp.newResult(rows);
    }
}
