package com.agent.czb.core.park.service;

import com.agent.czb.common.rest.service.BasisService;
import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.core.park.enums.ParkOrderEnum;
import com.agent.czb.core.park.mapper.NewParkOrderInfoMapper;
import com.agent.czb.core.park.mapper.ParkOrderInfoMapper;
import com.agent.czb.core.park.model.NewParkOrderInfoModel;
import com.agent.czb.core.park.model.ParkOrderInfoModel;
import com.agent.czb.core.sys.enums.CarportInfoEnums;
import com.agent.czb.core.sys.enums.WalletInfoEnums;
import com.agent.czb.core.sys.model.CarportBuyLogModel;
import com.agent.czb.core.sys.model.CarportInfoModel;
import com.agent.czb.core.sys.model.NewLocaInfoModel;
import com.agent.czb.core.sys.model.UserPlateInfoModel;
import com.agent.czb.core.sys.service.CarportBuyLogService;
import com.agent.czb.core.sys.service.CarportInfoService;
import com.agent.czb.core.sys.service.UserPlateInfoService;
import com.agent.czb.core.sys.service.WalletInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 查询经纬度服务层
 */
@Service
public class NewParkOrderInfoService extends BasisService<NewParkOrderInfoMapper, NewParkOrderInfoModel>{
   @Autowired
   private NewParkOrderInfoMapper newParkOrderInfoMapper;

	public NewParkOrderInfoModel listNewParkOrderInfo(String plateNum, String parkCode, Long userId) {
		NewParkOrderInfoModel model = new NewParkOrderInfoModel();
		model.setPlateNum(plateNum);
		model.setParkCode(parkCode);
		model.setUserId(userId);
		List<NewParkOrderInfoModel> list = newParkOrderInfoMapper.queryMapLngAndMapLatInformation(model);
		if (list!=null&&!list.isEmpty()) {
			model = list.get(0);
		}
		return model;
	}

}
