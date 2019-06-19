package com.agent.czb.service.sys.restful;

import com.agent.czb.common.rest.PageHelp;
import com.agent.czb.common.rest.ResultHelp;
import com.agent.czb.common.rest.restful.BasisRestful;
import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.core.eparking.FnEventCallback;
import com.agent.czb.core.park.enums.ParkCarStateEnum;
import com.agent.czb.core.park.model.ParkCarStateModel;
import com.agent.czb.core.park.service.ParkCarStateService;
import com.agent.czb.core.sys.model.UserInfoModel;
import com.agent.czb.core.sys.model.UserPlateInfoModel;
import com.agent.czb.core.sys.service.UserInfoService;
import com.agent.czb.core.sys.service.UserPlateInfoService;
import com.agent.czb.service.park.restful.ParkCarStateRestful;
import com.eparking.test.CarInto;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
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
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
/**
 * 用户车牌信息Restful接口
 */
@RestController
@RequestMapping("services/userPlateInfo")
public class UserPlateInfoRestful extends BasisRestful {
    private static final int PLATE_BIND_LIMIT = 3;
    private static Logger log = LoggerFactory.getLogger(UserPlateInfoRestful.class);
    @Autowired
    private UserPlateInfoService userPlateInfoService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ParkCarStateRestful parkCarStateRestful;
    @Autowired
    private ParkCarStateService parkCarStateService;


    @RequestMapping(value = "/pageList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp pageList(HttpServletRequest request, Integer isFree) {
        PageHelp pageHelp = PageHelp.newPageHelp(request);
        pageHelp.page(userPlateInfoService);
        List<UserPlateInfoModel> list = (List<UserPlateInfoModel>) pageHelp.getData();
        for (UserPlateInfoModel model : list) {
            setInfo(model, isFree);
        }
        //按照降序排序
        Collections.sort(list, new Comparator<UserPlateInfoModel>(){
            public int compare(UserPlateInfoModel p1, UserPlateInfoModel p2) {
                if (p1.getCarStateModels()!=null&&p2.getCarStateModels()!=null) {
                	if(p1.getCarStateModels().get(0).getInDate().getTime() > p2.getCarStateModels().get(0).getInDate().getTime()){
                        return -1;
                    }
                    if(p1.getCarStateModels().get(0).getInDate().getTime() < p2.getCarStateModels().get(0).getInDate().getTime()){
                        return 1;
                    }
				}
                return 0;
            }
        });
        
        
        return ResultHelp.newResult(pageHelp);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp get(Long id) {
        UserPlateInfoModel model = userPlateInfoService.selectById(id);
        setInfo(model, null);
        return ResultHelp.newResult(model);
    }

    public void setInfo(UserPlateInfoModel model, Integer isFree) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        model.setUserName(userInfoService.getUserName(model.getUserId()));
        //首先获取用户的所有车牌的车辆状态
        List<ParkCarStateModel> carStateModels = parkCarStateService.pageList(FrameUtils.newMap("plateNum", model.getPlateNum(),"pagerOrder", "order by in_date desc"));
        //对车辆状态进行遍历  状态 1：车辆已进场  状态 2：车辆已出场 状态 3：车辆入场，并且已支付 状态 5：车辆停车时长超过48小时
        if (carStateModels.size() > 0) {
            for (ParkCarStateModel carStateModel : carStateModels) {
            	//System.out.println(carStateModel.toString());
                //首先判断车辆是否在场内
                if (carStateModel.getState().equals(ParkCarStateEnum.State.进.toValue())) {//车辆在场
                    try {
                        if (DateUtils.addDays(carStateModel.getInDate(), 2).before(new Date())) {
                            carStateModel.setState(ParkCarStateEnum.State.车辆超时.toValue());
                            parkCarStateService.update(carStateModel);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (carStateModel.getState().equals(ParkCarStateEnum.State.已支付.toValue())) {

                } else {//车辆不在场
                }
                if (!carStateModel.getState().equals(ParkCarStateEnum.State.出.toValue())) {
                    parkCarStateRestful.setInfo(carStateModel, isFree);
				}
            }
            model.setCarStateModels(carStateModels);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp add(Long userId, String plateNum) {
        if (userId == null || userId == 0) {
            return ResultHelp.newFialResult("用户标识不能为空");
        }
        if (StringUtils.isEmpty(plateNum)) {
            return ResultHelp.newFialResult("车牌不能为空");
        }
        if (isUserPlateExist(plateNum)) {
            return ResultHelp.newFialResult("车牌已被绑定");
        }
        UserInfoModel user = userInfoService.selectById(userId);
        if (user == null) {
            return ResultHelp.newFialResult("用户不存在");
        }
        Long count = userPlateInfoService.pageCount(FrameUtils.newMap("userId", user.getId().toString()));
        if (count >= PLATE_BIND_LIMIT) {
            return ResultHelp.newFialResult("车牌绑定数量上限:" + PLATE_BIND_LIMIT);
        } else {
            UserPlateInfoModel model = new UserPlateInfoModel();
            model.setUserId(userId);
            model.setPlateNum(plateNum);
            model.setCreateTime(new Date());
            userPlateInfoService.bindPlate(user, model);
        }
        return ResultHelp.newResult(null);
    }

    private boolean isUserPlateExist(String plateNum) {
        Map<String, String> dataMap = FrameUtils.newMap("plateNum", plateNum);
        Long aLong = userPlateInfoService.pageCount(dataMap);
        return aLong > 0;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp edit(UserPlateInfoModel model) {
        if (model.getId() == null) {
            return ResultHelp.newFialResult("用户车牌标识不能为空");
        }
        if (isUserPlateExist(model.getPlateNum())) {
            return ResultHelp.newFialResult("车牌已被绑定");
        }
        model.setCreateTime(null);
        model.setUserId(null);
        model.setUpdateTime(new Date());
        int rows = userPlateInfoService.updateBySelective(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp del(Long id) {
        UserPlateInfoModel userPlateInfo = userPlateInfoService.selectById(id);
        if (userPlateInfo == null) {
            return ResultHelp.newFialResult("未找到车牌绑定记录");
        }
        userPlateInfoService.delPlate(userPlateInfo);
        return ResultHelp.newResult("");
    }

}
