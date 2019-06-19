package com.agent.czb.core.park.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agent.czb.common.rest.service.BasisService;
import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.core.park.enums.ParkOrderEnum;
import com.agent.czb.core.park.mapper.ParkOrderInfoMapper;
import com.agent.czb.core.park.model.ParkOrderInfoModel;
import com.agent.czb.core.sys.enums.CarportInfoEnums;
import com.agent.czb.core.sys.enums.WalletInfoEnums;
import com.agent.czb.core.sys.model.CarportBuyLogModel;
import com.agent.czb.core.sys.model.CarportInfoModel;
import com.agent.czb.core.sys.model.UserPlateInfoModel;
import com.agent.czb.core.sys.service.CarportBuyLogService;
import com.agent.czb.core.sys.service.CarportInfoService;
import com.agent.czb.core.sys.service.UserPlateInfoService;
import com.agent.czb.core.sys.service.WalletInfoService;

/**
 * 停车场订单信息服务层
 */
@Service("parkOrderInfoService")
public class ParkOrderInfoService extends BasisService<ParkOrderInfoMapper, ParkOrderInfoModel> {
    private static Logger log = LoggerFactory.getLogger(ParkOrderInfoService.class);
    @Autowired
    private WalletInfoService walletInfoService;
    @Autowired
    private UserPlateInfoService userPlateInfoService;
    @Autowired
    private ParkWhiteListService parkWhiteListService;
    @Autowired
    private CarportInfoService carportInfoService;
    @Autowired
    private CarportBuyLogService carportBuyLogService;

    public long orderPay(Long orderId, Long userId, Long price) {
        ParkOrderInfoModel t = selectById(orderId);
        if (t == null) {
            throw new RuntimeException("订单不存在，orderId=" + orderId);
        }
        return orderPay(t, userId, price);
    }

    /**
     * 支付订单
     */
    public long orderPay(ParkOrderInfoModel t, Long userId, Long price) {
        long payId;
        if (!Objects.equals(t.getUserId(), userId)) {
            log.error("order pay failed, user id error! userId={}, orderUserId={}, orderId={}", userId, t.getUserId(), t.getId());
            throw new RuntimeException("用户标识不一致");
        }
        // 使用钱包支付订单
        payId = walletInfoService.cutPayment(userId, price, WalletInfoEnums.Type.ORDER_PAY.toValue(), t.getId());
        // 更新订单关联ID
        ParkOrderInfoModel temp = new ParkOrderInfoModel();
        temp.setId(t.getId());
        temp.setPayId(payId);
        temp.setUpdateTime(new Date());
        updateBySelective(temp);
        // 购买系统车位成功, 生成白名单
        if (t.getType().equals(ParkOrderEnum.Type.BUY_CARPORT.toValue())) {
            parkWhiteListService.saveWhiteList(t, userId);
        }
        return payId;
    }

    /**
     * 自动支付
     */
    public long autoPay(String parkCode, String plateNum, Long price) {
        Map<String, String> params = FrameUtils.newMap();
        params.put("plateNum", plateNum);
        List<UserPlateInfoModel> userPlateInfos = userPlateInfoService.pageList(params);
        if (userPlateInfos.isEmpty()) {
            log.error("auto pay failed! carcode not exist! plateNum={}", plateNum);
            throw new RuntimeException("该车牌用户不存在");
        }
        // 获取用户ID
        UserPlateInfoModel model = userPlateInfos.get(0);
        Long userId = model.getUserId();
        // 新建订单号
        ParkOrderInfoModel orderInfo = new ParkOrderInfoModel();
        orderInfo.setUserId(userId);
        orderInfo.setUserName(null);
        orderInfo.setParkCode(parkCode);
        orderInfo.setPlateNum(plateNum);
        orderInfo.setTotalPrice(price);
        orderInfo.setType(ParkOrderEnum.Type.AUTO_PAY.toValue());
        orderInfo.setState(ParkOrderEnum.State.NOT_PAY.toValue());
        orderInfo.setCreateTime(new Date());
        insert(orderInfo);
        // 通过用户钱包支付订单
        long payId = walletInfoService.cutPayment(userId, price, WalletInfoEnums.Type.AUTO_PAY.toValue(), orderInfo.getId());
        // 支付成功，更新状态
        orderInfo.setState(ParkOrderEnum.State.PAY_COMPLETE.toValue());
        orderInfo.setUpdateTime(new Date());
        orderInfo.setRefId(payId);
        update(orderInfo);
        return payId;
    }

    /**
     *  自动上架按时租车位出租
     * @param parkCode 停车场编码
     * @param plateNum 车牌号
     */
    public int queryByCarportOrderId(String parkCode,String plateNum,Long orderId){
        log.info(orderId+"_____________车位id");
       ParkOrderInfoModel model= mapper.selectById(orderId);
        CarportInfoModel carport=null;
        if (model==null){
           // return 0;
             carport=carportInfoService.selectById(842l);
        }else{
             carport=carportInfoService.selectById(model.getRefId());
             model.setState(ParkOrderEnum.State.CLOSE.toValue());

            List<CarportBuyLogModel> list = carportBuyLogService.pageList(FrameUtils.newMap("carportId", carport.getId().toString()));
            if (list.size() > 0) {
                CarportBuyLogModel buylog =list.get(0);
                buylog.setState("7");//把订单状态设置成已经结束
            }
        }
        log.info(model.getRefId()+"_____________车位id");


        if (carport==null){
            return 0;
        }else if (carport.getPublishType()==0){//判断是否是 按时出租
            return 1;
        }
        //车位最少剩余一小时才能被再次上架
         if((carport.getEffTime().getTime()-60000)<=(new Date().getTime())){
           return 1;
         }
         carport.setState(CarportInfoEnums.State.OPEN.toValue());
         carport.setIsBuy(1);
         return carportInfoService.updateBySelective(carport);
    }

	public Long selectIdByRefId(Long refId) {
		return mapper.selectIdByRefId(refId);
	}

	public List<ParkOrderInfoModel> selectByRefId(Long inLogId) {
		return mapper.selectByRefId(inLogId);
	}
	/**
	 * 查询订单信息
	 * @param userId
	 * @param state 1-所有，2-未支付，3-支付
	 * @return
	 */
	public List<ParkOrderInfoModel> selectBystate(Long userId,int state) {
		Map map=new HashMap();
		map.put("userId", userId);
		//查询所有
		if(state==1){
			List all=Arrays.asList("4","401","402","403","404");
			map.put("state", all);
		}else if(state==2){
			List unpaid=Arrays.asList("401","403");
			map.put("state", unpaid);
		}else if(state==3){
			List paid=Arrays.asList("4","402","404");
			//model.setState(paid);
			map.put("state", paid);
		}
		return mapper.selectByState(map);
	}
//根据userID查询
	public List<ParkOrderInfoModel> selectByUserId(Long userId) {
		// TODO Auto-generated method stub
		 return mapper.selectByUserId(userId);
	}
/**
 * 根据车牌查找订单
 * @param plateNum
 * @return
 */
	public List<ParkOrderInfoModel> selectByPlateNum(String plateNum) {
		// TODO Auto-generated method stub
		return mapper.selectByPlateNum(plateNum);
	}

public List<ParkOrderInfoModel> selectDataToExcel(ParkOrderInfoModel model) {
	return mapper.selectDataToExcel(model);
}

}
