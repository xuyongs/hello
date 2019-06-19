package com.agent.czb.service.park.restful;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.agent.czb.common.rest.PageHelp;
import com.agent.czb.common.rest.ResultHelp;
import com.agent.czb.common.rest.restful.BasisRestful;
import com.agent.czb.core.park.enums.ParkOrderEnum;
import com.agent.czb.core.park.model.ParkGateLogModel;
import com.agent.czb.core.park.model.ParkOrderInfoModel;
import com.agent.czb.core.park.model.ParkSysInfoModel;
import com.agent.czb.core.park.service.ParkGateLogService;
import com.agent.czb.core.park.service.ParkOrderInfoService;
import com.agent.czb.core.park.service.ParkSysInfoService;
import com.agent.czb.core.sys.service.UserInfoService;

/**
 * 停车场闸机日志Restful接口
 */
@RestController
@RequestMapping("services/parkGateLog")
public class ParkGateLogRestful extends BasisRestful {
    private static Logger log = LoggerFactory.getLogger(ParkGateLogRestful.class);

    @Autowired
    private ParkGateLogService parkGateLogService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ParkSysInfoService parkSysInfoService;
    @Autowired
    private ParkOrderInfoService parkOrderInfoService;

    @RequestMapping(value = "/pageList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp pageList(HttpServletRequest request) {
        PageHelp pageHelp = PageHelp.newPageHelp(request);
        if (StringUtils.isNotEmpty(request.getParameter("phone"))) {
            Long userId = userInfoService.selectUserIdByPhone(request.getParameter("phone"));
            pageHelp.addParam("userId", userId);
        }

        pageHelp.page(parkGateLogService);
        List<ParkGateLogModel> list= (List<ParkGateLogModel>) pageHelp.getData();
        for (ParkGateLogModel model : list) {
            setInfo(model);
        }
        return ResultHelp.newResult(pageHelp);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp get(Long id) {
        ParkGateLogModel model = parkGateLogService.selectById(id);
        setInfo(model);
        return ResultHelp.newResult(model);
    }

    public void setInfo(ParkGateLogModel model) {
        ParkSysInfoModel parkSysInfo = parkSysInfoService.selectByParkCode(model.getParkCode());
        if (parkSysInfo != null) {
            model.setParkName(parkSysInfo.getName());
        }
        List<ParkOrderInfoModel> selectByRefId = parkOrderInfoService.selectByRefId(model.getId());
        if (selectByRefId.equals(null)) {
        	model.setPrice(model.getPrice());
		}else {
			for (ParkOrderInfoModel parkOrderInfoModel : selectByRefId) {
				if (parkOrderInfoModel.getState().equals(ParkOrderEnum.State.ADVANCE__PAID.toValue())) {
					model.setPrice(model.getPrice()+parkOrderInfoModel.getTotalPrice());
				}
			}
		}
        
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp add(ParkGateLogModel model) {
        int rows = parkGateLogService.insert(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp edit(ParkGateLogModel model) {
        int rows = parkGateLogService.updateBySelective(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp del(Long id) {
        int rows = parkGateLogService.delete(id);
        return ResultHelp.newResult(rows);
    }
        /**
         * 创建人:qany.
         * 创建时间:2016/12/10:20:48.
         * 描述: 查询有进场并且有出场纪录的数据
         */
    @RequestMapping(value = "/querInAndOut", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp querInAndOut(ParkGateLogModel model) {
        List<ParkGateLogModel> list = parkGateLogService.queryInAndOut(model);
        for (ParkGateLogModel parkGateLogModel: list) {
            setInfo(parkGateLogModel);
        }
        return ResultHelp.newResult(list);
    }

    /**
     * 创建人:qany.
     * 创建时间:2016/12/10:20:48.
     * 描述:出入场分开纪录查询
     */
    @RequestMapping(value = "/queryInOrOut", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp queryInOrOut(ParkGateLogModel model) {
        List<ParkGateLogModel> list = parkGateLogService.queryInOrOut(model);
        for (ParkGateLogModel parkGateLogModel: list) {
            setInfo(parkGateLogModel);
        }
        return ResultHelp.newResult(list);
    }
}
