package com.agent.czb.core.sys.service;

import com.agent.czb.common.rest.Errors;
import com.agent.czb.common.rest.service.BasisService;
import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.core.park.model.ParkWhiteListModel;
import com.agent.czb.core.park.service.ParkWhiteListService;
import com.agent.czb.core.sys.mapper.UserPlateInfoMapper;
import com.agent.czb.core.sys.model.UserInfoModel;
import com.agent.czb.core.sys.model.UserPlateInfoModel;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.formula.functions.Count;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 用户车牌信息服务层
 */
@Service("userPlateInfoService")
public class UserPlateInfoService extends BasisService<UserPlateInfoMapper, UserPlateInfoModel> {
    @Autowired
    private CarportInfoService carportInfoService;
    @Autowired
    private ParkWhiteListService parkWhiteListService;

    public void delPlate(UserPlateInfoModel userPlateInfo) {
        String plateNum = userPlateInfo.getPlateNum();
        List<ParkWhiteListModel> list = parkWhiteListService.pageList(FrameUtils.newMap("plateNum", plateNum, "userId", userPlateInfo.getUserId().toString()));
        long count = 0;
        if (!CollectionUtils.isEmpty(list)) {
            for (ParkWhiteListModel parkWhiteList : list) {
                if (parkWhiteList.getEndTime().before(new Date())) {
                    count++;
                }
            }
        }
        if (count > 0) {
            throw Errors.baseServiceException("用户已购买车位");
        }
        delete(userPlateInfo.getId());
    }

    public void bindPlate(UserInfoModel user, UserPlateInfoModel model) {
        model.setUserId(user.getId());
        // 判断该用户下面是否有白名单
        List<ParkWhiteListModel> list = parkWhiteListService.pageList(FrameUtils.newMap("phone", user.getPhone(), "plateNum", model.getPlateNum()));
        for (ParkWhiteListModel parkWhiteListModel : list) {
            // 如果白名单没有绑定用户,则绑定为当前用户
            if (parkWhiteListModel.getUserId() == null) {
                parkWhiteListModel.setUserId(user.getId());
                parkWhiteListModel.setUpdateTime(new Date());
                parkWhiteListService.update(parkWhiteListModel);
            }
        }
        insert(model);
    }

    public static void main(String[] args) throws Exception {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        boolean b = UserPlateInfoService.judgmentDate("2017-05-20 12:57:34", simpleDateFormat.format(new Date()));
//        System.out.println(b);
        Date startTime = DateUtils.parseDate("2017-09-04 12:57:34", "yyyy-MM-dd HH:mm:ss");
        System.out.println(DateUtils.addDays(startTime, 2).before(new Date()));

    }
}
