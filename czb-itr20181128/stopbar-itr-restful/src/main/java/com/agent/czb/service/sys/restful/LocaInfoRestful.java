package com.agent.czb.service.sys.restful;

import com.agent.czb.common.rest.PageHelp;
import com.agent.czb.common.rest.ResultHelp;
import com.agent.czb.common.rest.restful.BasisRestful;
import com.agent.czb.core.sys.enums.LocaInfoEnums;
import com.agent.czb.core.sys.model.LocaInfoModel;
import com.agent.czb.core.sys.service.LocaInfoService;
import com.alibaba.fastjson.JSONObject;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 位置信息Restful接口
 */
@RestController
@RequestMapping("services/locaInfo")
public class LocaInfoRestful extends BasisRestful {
    private static Logger log = LoggerFactory.getLogger(LocaInfoRestful.class);

    @Autowired
    private LocaInfoService locaInfoService;

    @RequestMapping(value = "/pageList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp pageList(HttpServletRequest request) {
        PageHelp pageHelp = PageHelp.newPageHelp(request);
        pageHelp.page(locaInfoService);
        List<LocaInfoModel> list= (List<LocaInfoModel>) pageHelp.getData();
        for (LocaInfoModel model : list) {
            setInfo(model);
        }
        return ResultHelp.newResult(pageHelp);
    }

    @RequestMapping(value = "/loca", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp loca(String type, Long refId) {
        LocaInfoEnums.Type type1 = LocaInfoEnums.Type.get(type);
        if (type1 == null) {
            return ResultHelp.newFialResult("类型不正确");
        }
        if (refId == null) {
            return ResultHelp.newFialResult("关联id不能为空");
        }
        LocaInfoModel model = locaInfoService.getLoca(type, refId);
        return ResultHelp.newResult(model);
    }

    /**
     * 创建人:qany.
     * 创建时间:2017/1/3:16:44.
     * 描述:跨域
     */
    @RequestMapping(value = "/locaJsop", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void loca(HttpServletRequest request,HttpServletResponse response,String type, Long refId) {
        LocaInfoEnums.Type type1 = LocaInfoEnums.Type.get(type);
        if (type1 == null) {
            ResultHelp.newFialResult("类型不正确");
        }
        if (refId == null) {
           ResultHelp.newFialResult("关联id不能为空");
        }

        LocaInfoModel model = locaInfoService.getLoca(type, refId);
        String jsonp=request.getParameter("callback");
        response.setContentType("application/x-javascript");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("P3P", "CP=CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR");
        String jsoncallback = jsonp + "("+ JSONObject.toJSON(ResultHelp.newResult(model))+")";
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }

        out.print(jsoncallback);

        out.flush();
        out.close();
    }


    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp get(Long id) {
        LocaInfoModel model = locaInfoService.selectById(id);
        setInfo(model);
        return ResultHelp.newResult(model);
    }

    public void setInfo(LocaInfoModel model) {

    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp add(LocaInfoModel model) {
        int rows = locaInfoService.insert(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp edit(LocaInfoModel model) {
        int rows = locaInfoService.updateBySelective(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp del(Long id) {
        int rows = locaInfoService.delete(id);
        return ResultHelp.newResult(rows);
    }
}
