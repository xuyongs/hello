package com.agent.czb.service.park.restful;

import com.agent.czb.common.rest.Errors;
import com.agent.czb.common.rest.PageHelp;
import com.agent.czb.common.rest.ResultHelp;
import com.agent.czb.common.rest.restful.BasisRestful;
import com.agent.czb.common.utils.DateUtils;
import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.core.eparking.FnEventCallback;
import com.agent.czb.core.park.enums.ParkGateEnum;
import com.agent.czb.core.park.enums.ParkOrderEnum;
import com.agent.czb.core.park.model.NewParkOrderInfoModel;
import com.agent.czb.core.park.model.ParkCarStateModel;
import com.agent.czb.core.park.model.ParkGateLogModel;
import com.agent.czb.core.park.service.NewParkOrderInfoService;
import com.agent.czb.core.park.service.ParkCarStateService;
import com.agent.czb.core.park.service.ParkGateLogService;
import com.agent.czb.core.park.service.ParkOrderInfoService;
import com.agent.czb.core.park.service.ParkWhiteListService;
import com.eparking.test.CarInto;
import com.agent.czb.core.park.model.ParkOrderInfoModel;
import com.agent.czb.core.park.model.ParkWhiteListModel;
import com.agent.czb.common.park.call.CallRemoteDllUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 停车场订单信息Restful接口
 */
@RestController
@RequestMapping("services/parkOrderInfo")
public class ParkOrderInfoRestful extends BasisRestful {
    private static Logger log = LoggerFactory.getLogger(ParkOrderInfoRestful.class);

    @Autowired
    private ParkOrderInfoService parkOrderInfoService;

    @Autowired
    private ParkCarStateService parkCarStateService;
    @Autowired
    private  NewParkOrderInfoService newParkOrderInfoService;
    @Autowired
    private ParkWhiteListService parkWhiteListService;
    
    
    @RequestMapping(value = "/pageList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp pageList(HttpServletRequest request) {
        PageHelp pageHelp = PageHelp.newPageHelp(request);
        pageHelp.page(parkOrderInfoService);
        List<ParkOrderInfoModel> list= (List<ParkOrderInfoModel>) pageHelp.getData();
        for (ParkOrderInfoModel model : list) {
            setInfo(model);
        }
        return ResultHelp.newResult(pageHelp);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp get(Long id) {
        ParkOrderInfoModel model = parkOrderInfoService.selectById(id);
        setInfo(model);
        return ResultHelp.newResult(model);
    }

    public void setInfo(ParkOrderInfoModel model) {
        model.setTypeStr(ParkOrderEnum.Type.getDesc(model.getType()));
        model.setStateStr(ParkOrderEnum.State.getDesc(model.getState()));
    }

    /**
     * 添加订单
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp add(ParkOrderInfoModel model) {
        int rows = parkOrderInfoService.insert(model);
        return ResultHelp.newResult(rows);
    }

    /**
     * 修改订单
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp edit(ParkOrderInfoModel model) {
        int rows = parkOrderInfoService.updateBySelective(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/realTimePrice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp realTimePrice(Long userId, String parkCode, String plateNum) {
    	FnEventCallback.prepayAmount=null;
    	Map<String, String> mapToCar = new HashMap<>();
    	mapToCar.put("park_id", parkCode);
    	mapToCar.put("plate_id", plateNum);
    	mapToCar.put("order_id", "");
        CarInto.prepayAmount(mapToCar);
        try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (FnEventCallback.prepayAmount==null) {
        	return ResultHelp.newFialResult("未查询到数据！");
		}
        if (FnEventCallback.prepayAmount.getError_des().equals("查询预支付金额成功！")) {
        	return ResultHelp.newResult(FnEventCallback.prepayAmount);
		}else {
			return ResultHelp.newFialResult("查询错误");
		}
    }
    
    /**
     * 停车场缴费订单(停车场计费)
     */
    @RequestMapping(value = "/countPrice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp countPrice(Long userId, String parkCode, String plateNum) {
        if (userId == null || userId == 0) {
            return ResultHelp.newFialResult("用户标识不能为空");
        }
        if (StringUtils.isEmpty(plateNum)) {
            return ResultHelp.newFialResult("车牌号不能为空");
        }
        ParkOrderInfoModel parkOrderInfoModel = null;
        List<ParkCarStateModel> carStateList = parkCarStateService.pageList(FrameUtils.newMap("parkCode", parkCode, "plateNum", plateNum));
        if (carStateList.size() > 0) {
            // 获取停车场状态
            ParkCarStateModel parkCarState = carStateList.get(0);
            if (!parkCarState.getState().equals(ParkGateEnum.State.OUT.toValue())) {
                // 调用远端接口生成价格
               //CallRemoteDllUtils.ParkAmount amount = CallRemoteDllUtils.amount(parkCarState.getParkCode(), parkCarState.getInLogId());
                 /*if (amount == null) {
                    throw Errors.baseServiceException("计算价钱错误!");
                }*/
            	FnEventCallback.prepayAmount=null;
            	Map<String, String> mapToCar = new HashMap<>();
            	mapToCar.put("park_id", parkCarState.getParkCode());
            	mapToCar.put("plate_id", parkCarState.getPlateNum());
            	mapToCar.put("order_id", "");
                CarInto.prepayAmount(mapToCar);
                try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
             // 从云端获取，设置价格
                Long payAmount = Long.valueOf(FnEventCallback.prepayAmount.getPay_amount().replace(".", ""));
                if (payAmount<=0) {
                	return ResultHelp.newResult("本次停车免费");
				}
             // 封装订单
                ParkWhiteListModel parkWhiteListModel = new ParkWhiteListModel();
                parkWhiteListModel.setParkCode(parkCarState.getParkCode());
                parkWhiteListModel.setPlateNum(parkCarState.getPlateNum());
                ParkWhiteListModel parkWhiteList = parkWhiteListService.selectByParkCodeAndPlateNum(parkWhiteListModel);
                
                
                parkOrderInfoModel = new ParkOrderInfoModel();
                parkOrderInfoModel.setUserId(userId);
                parkOrderInfoModel.setUserName(null);
                parkOrderInfoModel.setParkCode(parkCarState.getParkCode());
                parkOrderInfoModel.setPlateNum(parkCarState.getPlateNum());
                parkOrderInfoModel.setModel("4");
                parkOrderInfoModel.setCode(FnEventCallback.prepayAmount.getPark_name());
                //设置价格
                parkOrderInfoModel.setTotalPrice(payAmount);
                parkOrderInfoModel.setRefId(parkCarState.getInLogId());
                parkOrderInfoModel.setType(ParkOrderEnum.Type.APP_PAY.toValue());
                parkOrderInfoModel.setState(ParkOrderEnum.State.ADVANCE__UNPAID.toValue());
                parkOrderInfoModel.setCreateTime(new Date());
                parkOrderInfoModel.setUpdateTime(new Date());
                if (parkWhiteList!=null) {
                	if (parkWhiteList.getCode()!=null||parkWhiteList.getCode()!="") {
                    	parkOrderInfoModel.setCode(parkWhiteList.getCode());
    				}
                    if (parkWhiteList.getRemark()!=null&&parkWhiteList.getRemark()!="") {
    					parkOrderInfoModel.setRemark(parkWhiteList.getRemark());
    				}
                    
                    if (parkWhiteList.getOpenTime()!=null&&parkWhiteList.getOpenTime()!="") {
                    	parkOrderInfoModel.setOpenTime(parkWhiteList.getOpenTime());
    				}
				}
                parkOrderInfoModel.setStartTime(parkCarState.getInDate());
                //判断当前缴费是否已经生成，已生成则修改，未生成怎添加
                List<ParkOrderInfoModel> selectByRefId = parkOrderInfoService.selectByRefId(parkOrderInfoModel.getRefId());
                if (selectByRefId!=null) {
                	for (ParkOrderInfoModel parkOrderInfoM : selectByRefId) {
                    	if (parkOrderInfoM.getState().equals(ParkOrderEnum.State.ADVANCE__UNPAID.toValue())) {
                    		parkOrderInfoModel.setId(parkOrderInfoM.getId());
        				}
                    	if (parkOrderInfoM.getState().equals(ParkOrderEnum.State.ADVANCE__PAID.toValue())) {
                    		return ResultHelp.newFialResult("该订单已支付");
        				}
    				}
				}
                if (parkOrderInfoModel.getId()!=null) {
					parkOrderInfoService.update(parkOrderInfoModel);
				}else {
					parkOrderInfoService.insert(parkOrderInfoModel);
				}
            } else if (parkCarState.getState().equals(ParkGateEnum.State.PAY.toValue())) {
                return ResultHelp.newFialResult("该订单已支付");
            }
        }
        if (parkOrderInfoModel == null || parkOrderInfoModel.getId() == null) {
            return ResultHelp.newFialResult("您的车未在停车场中");
        }
        return ResultHelp.newResult(parkOrderInfoModel);
    }
    
    @RequestMapping(value = "/del", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp del(Long id) {
        int rows = parkOrderInfoService.delete(id);
        return ResultHelp.newResult(rows);
    }
  
    /**
     * 查询经纬度
     * @param plateNum
     * @param parkCode
     * @param userId
     * @return
     */
    @RequestMapping(value = "/location", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp localtion (String plateNum,String parkCode,Long userId) {
    	NewParkOrderInfoModel rows=newParkOrderInfoService.listNewParkOrderInfo(plateNum,parkCode,userId);
    	if(!rows.equals(null)){
    		return ResultHelp.newResult(rows);
    	}else{
    		return ResultHelp.newFialResult("没有位置信息");
    	}
    }
    /**
     * 查询停车场订单
     * @param userId
     * @param state 1-所有，2-未支付，3-已支付
     * @return
     */
    @RequestMapping(value = "/queryParkOrderInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp queryParkOrderInfo (Long userId ,int state) {
    	List<ParkOrderInfoModel> selectBystate = parkOrderInfoService.selectBystate(userId, state);
    	return ResultHelp.newResult(selectBystate);
    }

    /**
     * 查询停车场订单详细
     * @param ParkOrderInfo.id 订单Id
     * @return ParkOrderInfoModel
     */
    @RequestMapping(value = "/queryParkOrderDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp queryParkOrderDetail (Long id) {
    	ParkOrderInfoModel model = parkOrderInfoService.selectById(id);
    	return ResultHelp.newResult(model);
    }
    
}
