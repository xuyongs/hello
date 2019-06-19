package com.agent.czb.service.sys.restful;

import com.agent.czb.common.rest.PageHelp;
import com.agent.czb.common.rest.RestUtils;
import com.agent.czb.common.rest.ResultHelp;
import com.agent.czb.common.rest.restful.BasisRestful;
import com.agent.czb.core.sys.service.PointLogService;
import com.agent.czb.core.sys.model.PointLogModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * 积分日记Restful接口
 */
@RestController
@RequestMapping("services/pointLog")
public class PointLogRestful extends BasisRestful {
    private static Logger log = LoggerFactory.getLogger(PointLogRestful.class);

    @Autowired
    private PointLogService pointLogService;

    @RequestMapping(value = "/pageList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp pageList(HttpServletRequest request) {
        PageHelp pageHelp = PageHelp.newPageHelp(request);
        pageHelp.page(pointLogService);
        List<PointLogModel> list= (List<PointLogModel>) pageHelp.getData();
        for (PointLogModel model : list) {
            setInfo(model);
        }
        return ResultHelp.newResult(pageHelp);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp get(Long id) {
        PointLogModel model = pointLogService.selectById(id);
        setInfo(model);
        return ResultHelp.newResult(model);
    }

    public void setInfo(PointLogModel model) {

    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp add(PointLogModel model) {
        int rows = pointLogService.insert(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp edit(PointLogModel model) {
        int rows = pointLogService.updateBySelective(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp del(Long id) {
        int rows = pointLogService.delete(id);
        return ResultHelp.newResult(rows);
    }
    /**
     * 获取积分列表
     * @param request
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getPointLog", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp getPointLog(HttpServletRequest request,Long userId) {
    	/* //PageHelp pageHelp = PageHelp.newPageHelp(request);
    	 List<PointLogModel> pointLogList =new ArrayList<PointLogModel>();
         //pageHelp.page(pointLogService);
         List<PointLogModel> list= pointLogService.pageList(map);
         for (PointLogModel model : list) {
        	 if(!model.equals(null)&& model.getUserId().equals(userId)){
        		 pointLogList.add(model);
        	 }
         }*/
    	List<PointLogModel> pointLogList =pointLogService.selectByUserId(userId);
         return ResultHelp.newResult(pointLogList);
    }
}
