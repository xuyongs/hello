package com.agent.czb.service.sys.restful;

import com.agent.czb.common.rest.PageHelp;
import com.agent.czb.common.rest.ResultHelp;
import com.agent.czb.common.rest.restful.BasisRestful;
import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.core.sys.enums.CarportInfoEnums;
import com.agent.czb.core.sys.enums.FileInfoEnums;
import com.agent.czb.core.sys.model.CarportCollectModel;
import com.agent.czb.core.sys.model.CarportInfoModel;
import com.agent.czb.core.sys.model.FileUpdateInfoModel;
import com.agent.czb.core.sys.model.UserInfoModel;
import com.agent.czb.core.sys.service.CarportCollectService;
import com.agent.czb.core.sys.service.CarportInfoService;
import com.agent.czb.core.sys.service.FileUpdateInfoService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 车位收藏Restful接口
 */
@RestController
@RequestMapping("services/carportCollect")
public class CarportCollectRestful extends BasisRestful {
    private static Logger log = LoggerFactory.getLogger(CarportCollectRestful.class);

    @Autowired
    private CarportCollectService carportCollectService;

    @Autowired
    private CarportInfoService carportInfoService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private FileUpdateInfoService fileUpdateInfoService;

    @RequestMapping(value = "/pageList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp pageList(HttpServletRequest request) {
        PageHelp pageHelp = PageHelp.newPageHelp(request);
        pageHelp.page(carportCollectService);
        List<CarportCollectModel> list = (List<CarportCollectModel>) pageHelp.getData();
        for (CarportCollectModel model : list) {
            setInfo(model, request);
        }
        return ResultHelp.newResult(pageHelp);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp get(Long id) {
        CarportCollectModel model = carportCollectService.selectById(id);
        setInfo(model);
        return ResultHelp.newResult(model);
    }

    public void setInfo(CarportCollectModel model) {
        setInfo(model, null);
    }

    public void setInfo(CarportCollectModel model, HttpServletRequest request) {
        Long carportId = model.getCarportId();

        CarportInfoModel carportInfo = carportInfoService.selectById(carportId);
        String userName = userInfoService.getUserName(carportInfo.getUserId());
        if (userName != null) {
            carportInfo.setUserName(userName);
        }
        if (carportInfo.getState() != null) {
            carportInfo.setStateStr(CarportInfoEnums.State.getDesc(model.getState()));
        }
        model.setCarportInfo(carportInfo);

        if (request != null) {
            String showImgs = request.getParameter("showImgs");
            if (StringUtils.equals(showImgs, "true") || StringUtils.equals(showImgs, "1")) {
                List<String> strings = fileUpdateInfoService.selectUrlsByTypeRvId(carportId, FileInfoEnums.RvType.CARPORT.name());
                carportInfo.setImgs(strings);
            }

            String showSeller = request.getParameter("showSeller");
            if (StringUtils.isNotEmpty(showSeller)) {
                UserInfoModel userInfo = userInfoService.selectById(carportInfo.getUserId());
                model.setSeller(userInfo);
            }
        }
        model.setUserName(userInfoService.getUserName(model.getUserId()));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp add(CarportCollectModel model) {
        if (model.getUserId() == null || model.getUserId() == 0) {
            return ResultHelp.newFialResult("用户标识不能为空");
        }
        if (model.getCarportId() == null || model.getCarportId() == 0) {
            return ResultHelp.newFialResult("车位标识不能为空");
        }
        Map<String, String> dataMap = FrameUtils.newMap("userId", model.getUserId().toString());
        dataMap.put("carportId", model.getCarportId().toString());
        List<CarportCollectModel> list = carportCollectService.pageList(dataMap);
        if (list.size() > 0) {
            CarportCollectModel temp = list.get(0);
            if (StringUtils.equals(temp.getState(), CarportInfoEnums.CollectState.COLLECT.toValue())) {
                return ResultHelp.newFialResult("车位已收藏");
            } else {
                // 修改状态，为收藏
                temp.setState(CarportInfoEnums.CollectState.COLLECT.toValue());
                temp.setUpdateTime(new Date());
                int rows = carportCollectService.updateBySelective(temp);
                return ResultHelp.newResult(rows);
            }
        }
        model.setState(CarportInfoEnums.CollectState.COLLECT.toValue());
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        int rows = carportCollectService.insert(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp edit(CarportCollectModel model) {
        int rows = carportCollectService.updateBySelective(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp del(Long id) {
        int rows = carportCollectService.delete(id);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp cancel(CarportCollectModel model) {
        int rows = 0 ;
        if (model.getUserId() == null || model.getUserId() == 0) {
            return ResultHelp.newFialResult("用户标识不能为空");
        }
        if (model.getCarportId() == null || model.getCarportId() == 0) {
            return ResultHelp.newFialResult("车位标识不能为空");
        }
        Map<String, String> dataMap = FrameUtils.newMap("userId", model.getUserId().toString());
        dataMap.put("carportId", model.getCarportId().toString());
        List<CarportCollectModel> list =carportCollectService.pageList(dataMap);
        if(list.size()>0){
            CarportCollectModel carportCollectModel=  list.get(0);
            carportCollectModel.setState(CarportInfoEnums.CollectState.NOT_COLLECT.toValue());
            carportCollectModel.setUpdateTime(new Date());
            rows = carportCollectService.update(carportCollectModel);
        }
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/isCollect", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp isCollect(Long carportId, Long userId) {
        //该车位是否被当前用户收藏
        Map<String, String> params = FrameUtils.newMap();
        params.put("carportId",carportId.toString());
        params.put("userId", userId.toString());
        List<CarportCollectModel> collectModelList = carportCollectService.pageList(params);
        if(collectModelList.size() > 0){
            if(collectModelList.get(0).getState().equals(CarportInfoEnums.CollectState.COLLECT.toValue())){
                return  ResultHelp.newResult(1);
            }else{
                return  ResultHelp.newResult(0);
            }
        }else{
            return  ResultHelp.newResult(0);
        }
    }
}
