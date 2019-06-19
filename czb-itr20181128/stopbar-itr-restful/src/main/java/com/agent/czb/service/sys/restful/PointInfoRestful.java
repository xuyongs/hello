package com.agent.czb.service.sys.restful;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.agent.czb.common.rest.PageHelp;
import com.agent.czb.common.rest.ResultHelp;
import com.agent.czb.common.rest.restful.BasisRestful;
import com.agent.czb.core.sys.model.PointInfoModel;
import com.agent.czb.core.sys.service.PointInfoService;

/**
 * 积分信息Restful接口
 */
@RestController
@RequestMapping("services/pointInfo")
public class PointInfoRestful extends BasisRestful {
    private static Logger log = LoggerFactory.getLogger(PointInfoRestful.class);

    @Autowired
    private PointInfoService pointInfoService;

    @RequestMapping(value = "/pageList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp pageList(HttpServletRequest request) {
        PageHelp pageHelp = PageHelp.newPageHelp(request);
        pageHelp.page(pointInfoService);
        List<PointInfoModel> list= (List<PointInfoModel>) pageHelp.getData();
        for (PointInfoModel model : list) {
            setInfo(model);
        }
        return ResultHelp.newResult(pageHelp);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp get(Long id) {
        PointInfoModel model = pointInfoService.selectById(id);
        setInfo(model);
        return ResultHelp.newResult(model);
    }

    public void setInfo(PointInfoModel model) {

    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp add(PointInfoModel model) {
        int rows = pointInfoService.insert(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp edit(PointInfoModel model) {
        int rows = pointInfoService.updateBySelective(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp del(Long id) {
        int rows = pointInfoService.delete(id);
        return ResultHelp.newResult(rows);
    }
    /**
     * 查询积分接口
     * @param userId
     * @return
     * @throws ParseException 
     */
    @RequestMapping(value = "/queryPointInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp queryPointInfo(Long userId) throws ParseException {
    	PointInfoModel queryPointInfo =new PointInfoModel();
    	int countPointInfo = pointInfoService.countPointInfo(userId);
    	
    	if(countPointInfo==1){
    		 queryPointInfo = pointInfoService.queryPointInfo(userId);
    	}else{
    		/*获取当前时间*/
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		Date date = sdf.parse(sdf.format(new Date()));
    		queryPointInfo.setUserId(userId);
    		queryPointInfo.setState("0");
    		queryPointInfo.setTotal(0);
    		queryPointInfo.setVersion(1);
    		queryPointInfo.setCreateTime(date);
    		queryPointInfo.setUpdateTime(date);
    		pointInfoService.insert(queryPointInfo);
    	}
        return ResultHelp.newResult(queryPointInfo);
    }
    
}
