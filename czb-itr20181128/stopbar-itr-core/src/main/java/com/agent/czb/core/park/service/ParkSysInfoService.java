package com.agent.czb.core.park.service;

import com.agent.czb.common.json.JSONUtils;
import com.agent.czb.common.park.call.CallRemoteDllUtils;
import com.agent.czb.common.rest.service.BasisService;
import com.agent.czb.common.utils.DateUtils;
import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.core.park.mapper.ParkSysInfoMapper;
import com.agent.czb.core.park.model.ParkSysInfoModel;
import com.agent.czb.core.sys.enums.LocaInfoEnums;
import com.agent.czb.core.sys.model.LocaInfoModel;
import com.agent.czb.core.sys.model.ReservedParkModel;
import com.agent.czb.core.sys.service.LocaInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 停车场系统信息服务层
 */
@Service("parkSysInfoService")
public class ParkSysInfoService extends BasisService<ParkSysInfoMapper, ParkSysInfoModel> {
    @Autowired
    private LocaInfoService locaInfoService;

    public ParkSysInfoModel selectByParkCode(String parkCode) {
        if (StringUtils.isBlank(parkCode)) {
            return null;
        }
        List<ParkSysInfoModel> list = pageList(FrameUtils.newMap("parkCode", parkCode));
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public void save(ParkSysInfoModel model) {
        if (model.getId() == null) {
            model.setMaxAmount((long)(model.getMaxAmountDob()*100));
            model.setTenMinutePrice((long)(model.getTenMinutePriceDob()*100));
            model.setCreateTime(new Date());
            insert(model);

            // 插入经纬度
            LocaInfoModel locaInfo = new LocaInfoModel();
            locaInfo.setMapLng(model.getMapLng());
            locaInfo.setMapLat(model.getMapLat());
            locaInfo.setType(LocaInfoEnums.Type.SYS_PARK.toValue());
            locaInfo.setRefId(model.getId());
            locaInfoService.add(locaInfo);
        } else {
            model.setMaxAmount((long)(model.getMaxAmountDob()*100));
            model.setTenMinutePrice((long)(model.getTenMinutePriceDob()*100));
            model.setUpdateTime(new Date());
            updateBySelective(model);
            // 更新经度维度
            LocaInfoModel locaInfo = new LocaInfoModel();
            locaInfo.setMapLng(model.getMapLng());
            locaInfo.setMapLat(model.getMapLat());
            locaInfo.setType(LocaInfoEnums.Type.SYS_PARK.toValue());
            locaInfo.setRefId(model.getId());
            locaInfoService.add(locaInfo);
        }
    }

    public int checkStates(String parkCode){
        Map<String, Object> map = CallRemoteDllUtils.post(parkCode, FrameUtils.newMap("interfacetype", "1", "orderid", "0"));
        if (!CallRemoteDllUtils.isSuccess(map)) {
           return 0;
        }Map<String, Object> data = JSONUtils.jsonToMap(map.get("data").toString());
        if (data.get("status") != null && data.get("status").toString().equals("0")) {
            return 1;
        }
        return 1;
    }
   /**
    * 创建人:qany.
    * 创建时间:2016/12/19:10:22.
    * 描述: 预租车通知停车场
    */
    public static int reservedPark(String parkCode,ReservedParkModel model){
        Map<String, Object> reservedPark = new HashMap<>();
        reservedPark.put("interfacetype","12");
        reservedPark.put("platenumber", model.getPlateNumber());
        reservedPark.put("starttime", model.getStartTime());
        reservedPark.put("endtime",DateUtils.format(model.getEndTime(),DateUtils.F_YMDHMS_));
        Map<String, Object> map = CallRemoteDllUtils.reservedPark(parkCode,reservedPark);
        if (!CallRemoteDllUtils.isSuccess(map)) {
            return 0;
        }Map<String, Object> data = JSONUtils.jsonToMap(map.get("data").toString());
        if (data.get("status") != null && data.get("status").toString().equals("0")) {
            return 1;
        }
        return 1;
    }
    
    public List<String> selParkCode(){
        return mapper.selParkCode();
    }

}
