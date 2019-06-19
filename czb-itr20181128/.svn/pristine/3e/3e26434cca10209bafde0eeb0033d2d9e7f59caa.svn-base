package com.agent.czb.core.sys.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agent.czb.common.rest.Errors;
import com.agent.czb.common.rest.service.BasisService;
import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.core.park.enums.ParkOrderEnum;
import com.agent.czb.core.park.enums.ParkWhiteEnum;
import com.agent.czb.core.park.model.ParkOrderInfoModel;
import com.agent.czb.core.park.model.ParkSysInfoModel;
import com.agent.czb.core.park.model.ParkWhiteListModel;
import com.agent.czb.core.park.service.ParkOrderInfoService;
import com.agent.czb.core.park.service.ParkSysInfoService;
import com.agent.czb.core.park.service.ParkWhiteListService;
import com.agent.czb.core.sys.enums.CarportInfoEnums;
import com.agent.czb.core.sys.enums.LocaInfoEnums;
import com.agent.czb.core.sys.mapper.CarportInfoMapper;
import com.agent.czb.core.sys.model.CarportInfoModel;
import com.agent.czb.core.sys.model.LocaInfoModel;
import com.agent.czb.core.sys.model.PointInfoModel;
import com.agent.czb.core.sys.model.UserInfoModel;

/**
 * 视频信息服务层
 */
@Service("carportInfoService")
public class CarportInfoService extends BasisService<CarportInfoMapper, CarportInfoModel> {
    private static final Logger log = LoggerFactory.getLogger(CarportInfoService.class);

    @Autowired
    private FileUpdateInfoService fileUpdateInfoService;
    @Autowired
    private LocaInfoService locaInfoService;
    @Autowired
    private ParkOrderInfoService parkOrderInfoService;
    @Autowired
    private ParkWhiteListService parkWhiteListService;
    @Autowired
    private ParkSysInfoService parkSysInfoService;
    @Autowired
    private PointInfoService pointInfoService;

    public List<CarportInfoModel> findByIds(List<Long> list) {
        return mapper.findByIds(list);
    }

    /**
     * 发布车位
     */
    public int releaseCarport(CarportInfoModel info, String[] fileUrls, String mapLng, String mapLat) {
        ParkWhiteListModel parkWhiteList = null;
        // 设置停车场类型
        if (StringUtils.isEmpty(info.getParkCode())) {
            info.setType(CarportInfoEnums.Type.CARPORT.toValue());
        } else {
            if (StringUtils.isEmpty(info.getCode())) {
                throw Errors.baseServiceException("车位不能为空");
            }
            // 获取车主车位白名单
            parkWhiteList = parkWhiteListService.findBySateAndCode(ParkWhiteEnum.State.车位.toValue(), info.getParkCode(), info.getCode(),info.getId());
            log.info("CarportInfoService________________"+parkWhiteList.getIsExp());
            log.info("CarportInfoService________________"+parkWhiteList.getUserId());
            log.info("CarportInfoService________________"+parkWhiteList.getPhone());
            log.info("CarportInfoService________________"+parkWhiteList.getPlateNum());
            log.info("CarportInfoService________________"+parkWhiteList.getId());
            if (parkWhiteList == null) {
                log.info("CarportInfoService________________"+parkWhiteList.getUserId());
                throw Errors.baseServiceException("车位不存在");
            }
            if (!info.getUserId().equals(parkWhiteList.getUserId())) {
                log.info("CarportInfoService________________"+parkWhiteList.getUserId());
                throw Errors.baseServiceException("未拥有该车位");
            }
            if (parkWhiteList.getIsExp() != null && parkWhiteList.getIsExp() == 1) {
                log.info("CarportInfoService________________"+parkWhiteList.getIsExp());
                throw Errors.baseServiceException("车位已失效");
            }
            parkWhiteList.setIsExp(1);
            parkWhiteList.setExpTime(info.getEffTime());
            // 设置车位状态
            info.setType(CarportInfoEnums.Type.SYS_CARPORT.toValue());
            info.setId(null);
            //设置车位价格
            ParkSysInfoModel parkSysInfoModel=parkSysInfoService.selectByParkCode(info.getParkCode());
            if (parkSysInfoModel != null) {
                info.setMaxAmount(parkSysInfoModel.getMaxAmount());
                info.setMinutePrice(parkSysInfoModel.getTenMinutePrice());
            }
        }
        //设置发布停车场经纬度
        info.setMapLat(mapLat);
        info.setMapLng(mapLng);
        // 添加车位信息
        insert(info);
        // 插入经纬度
        LocaInfoModel locaInfo = new LocaInfoModel();
        locaInfo.setMapLng(mapLng);
        locaInfo.setMapLat(mapLat);
        if (StringUtils.isEmpty(info.getParkCode())) {
            locaInfo.setType(LocaInfoEnums.Type.CARPORT.toValue());
        } else {
            locaInfo.setType(LocaInfoEnums.Type.SYS_CARPORT.toValue());
        }
        locaInfo.setRefId(info.getId());
        locaInfo.setCreateTime(new Date());
        locaInfoService.insert(locaInfo);

        // 更新文件状态
        String fileUrl = fileUpdateInfoService.updateFileState(fileUrls, info.getId());
        if (fileUrl != null) {
            CarportInfoModel temp = new CarportInfoModel();
            temp.setId(info.getId());
            temp.setImgUrl(fileUrl);
            info.setImgUrl(fileUrl);
            updateBySelective(temp);
        }
        // 更新白名单状态
        if (parkWhiteList != null) {
            // 白名单(车主)关联车位标识
            parkWhiteList.setCarportId(info.getId());
            parkWhiteList.setUpdateTime(new Date());
            parkWhiteListService.update(parkWhiteList);
        }
        return 1;
    }

    public int deleteAndLocaInfo(Long id) {
        locaInfoService.delByType(LocaInfoEnums.Type.CARPORT.toValue(), id);
        return delete(id);
    }
    public ParkOrderInfoModel buy(Long userId,CarportInfoModel carportInfo, UserInfoModel userInfo, String plateNum, Long price, Integer time, String remark, Date endTime) {
        // 只是系统车位才能购买
        try {
         Errors.check(carportInfo.getType().equals(CarportInfoEnums.Type.SYS_CARPORT.toValue()), "只有系统车位才能购买");
        // 生成停订单
        ParkOrderInfoModel parkOrderInfo = new ParkOrderInfoModel();
        parkOrderInfo.setState(ParkOrderEnum.State.NOT_PAY.toValue());
        parkOrderInfo.setType(CarportInfoEnums.Type.SYS_CARPORT.toValue());
        parkOrderInfo.setParkCode(carportInfo.getParkCode());
        parkOrderInfo.setPlateNum(plateNum);
        parkOrderInfo.setCode(carportInfo.getCode());
        parkOrderInfo.setRemark(remark);
        parkOrderInfo.setOpenTime(carportInfo.getOpenTime());
        parkOrderInfo.setModel(carportInfo.getModel());
      
      //判断是否按时发布
        if (carportInfo.getPublishType()==1){
        	
            price=carportInfo.getMinutePrice();
            Long openTime= DateUtils.parseDate(parkOrderInfo.getOpenTime(),"yyyy-MM-dd HH:mm:ss").getTime();
            Long  times = (carportInfo.getEffTime().getTime())-openTime;
            //判断当前时间是否超过预租时间
            if(openTime<=(new Date().getTime())){
                times=(carportInfo.getEffTime().getTime())-(new Date().getTime());
            }
            times=times/1000/60;
            //判断是否超过最高金额
            parkOrderInfo.setTotalPrice((times * price)>carportInfo.getMaxAmount()?carportInfo.getMaxAmount():(times * price));
            parkOrderInfo.setPublishType(1);
        }else{
            parkOrderInfo.setPublishType(0);
            parkOrderInfo.setTotalPrice(time * price);
        }
        if (time!=null){
            parkOrderInfo.setQuantity(time);
        }
        parkOrderInfo.setPrice(price);
        parkOrderInfo.setType(ParkOrderEnum.Type.BUY_CARPORT.toValue());
        parkOrderInfo.setRefId(carportInfo.getId());
        parkOrderInfo.setUserId(userInfo.getId());
        parkOrderInfo.setUserName(userInfo.getUserName());
        parkOrderInfo.setStartTime(DateUtils.parseDate(parkOrderInfo.getOpenTime(),"yyyy-MM-dd HH:mm:ss"));
        parkOrderInfo.setEndTime(carportInfo.getEffTime());
        parkOrderInfo.setCreateTime(new Date());
        parkOrderInfo.setUpdateTime(new Date());
        parkOrderInfoService.insert(parkOrderInfo);
        return parkOrderInfo;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 更新订单超时状态(将超时的订单下线)
     */
    public void updateOverdueStatus() {
        List<CarportInfoModel> carportInfolList = mapper.findOverdueStatus();
        for (CarportInfoModel carportInfo : carportInfolList) {
            offline(carportInfo);
        }
    }

    /**
     * 车位下线
     */
    public void offline(Long carportId, Long userId) {
        Errors.check(carportId != null, "车位标识不能为空");
        Errors.check(userId != null, "用户标识不能为空");
        CarportInfoModel carportInfo = selectById(carportId);
        Errors.check(carportInfo.getUserId().equals(userId), "用户未拥有该车位");
        Errors.check(carportInfo.getState().equals(CarportInfoEnums.State.OPEN.toValue()), "车位状态错误");
        offline(carportInfo);
    }

    /**
     * 车位下线
     */
    private void offline(CarportInfoModel carportInfo) {
        // 修改白名单失效状态
        parkWhiteListService.updateExpTime(carportInfo.getId());
        // 修改车位状态为下线
        carportInfo.setState(CarportInfoEnums.State.CLOSE.toValue());
        CarportInfoModel temp = new CarportInfoModel();
        temp.setId(carportInfo.getId());
        temp.setState(CarportInfoEnums.State.CLOSE.toValue());
        temp.setUpdateTime(new Date());
        updateBySelective(temp);
    }

    /**
     * 车位上线
     * 管理段调用
     */
    public void pass(Long carportId, Long userId) {
        Errors.check(carportId != null, "车位标识不能为空");
        Errors.check(userId != null, "用户标识不能为空");
        CarportInfoModel carportInfo = selectById(carportId);
        Errors.check(carportInfo.getUserId().equals(userId), "用户未拥有该车位");
        Errors.check(carportInfo.getState().equals(CarportInfoEnums.State.EXAMINE.toValue()), "车位状态错误");
        carportInfo.setState(CarportInfoEnums.State.OPEN.toValue());
        update(carportInfo);
    }


    public void admOffline(Long carportId, Long userId) {
        Errors.check(carportId != null, "车位标识不能为空");
        Errors.check(userId != null, "用户标识不能为空");
        CarportInfoModel carportInfo = selectById(carportId);
        Errors.check(carportInfo.getUserId().equals(userId), "用户未拥有该车位");
        Errors.check(carportInfo.getState().equals(CarportInfoEnums.State.OPEN.toValue()), "车位状态错误");
        //修改白名单Isexp状态
        parkWhiteListService.updateExpTime(carportId);
        // 修改车位状态为下线
        carportInfo.setState(CarportInfoEnums.State.ADMOFFLINE.toValue());
        carportInfo.setUpdateTime(new Date());
        //将车位所属白名单状态修改为可发布状态
        updateParkWhiteList(carportId);
        //修改车位信息
        update(carportInfo);
    }

    public void isDel(Long carportId, Long userId){
        Errors.check(carportId != null, "车位标识不能为空");
        Errors.check(userId != null, "用户标识不能为空");
        CarportInfoModel carportInfo = selectById(carportId);
        Errors.check(carportInfo.getUserId().equals(userId), "用户未拥有该车位");
        Errors.check(carportInfo.getIsDel().equals(0), "车辆已经被删除，不能重复操作");
        Errors.check(!carportInfo.getBuyLogId().equals(null), "车辆已经被购买，不能删除");

        carportInfo.setIsDel(1);
        carportInfo.setUpdateTime(new Date());
        //将车位所属白名单状态修改为可发布状态
        updateParkWhiteList(carportId);
        //修改车位信息
        updateBySelective(carportInfo);
    }

    //修改白名单信息，
    public void updateParkWhiteList(Long carportId){
        List<ParkWhiteListModel> parkWhiteListModelList =parkWhiteListService.pageList(FrameUtils.newMap("carportId", carportId.toString()));
        if(parkWhiteListModelList.size()>0) {
            ParkWhiteListModel parkWhiteListModel = parkWhiteListModelList.get(0);
            parkWhiteListModel.setIsExp(0);
            parkWhiteListModel.setExpTime(null);
            parkWhiteListModel.setUpdateTime(new Date());
            parkWhiteListModel.setCarportId(null);
            parkWhiteListService.update(parkWhiteListModel);
        }

        
    }

    /*
	 * 取消发布信息
	 * @param carportId 车位Id
	 * @return
	 */
	public CarportInfoModel cancelPublish(Long carportId){	
    	 CarportInfoModel carportInfo = selectById(carportId);
    	 return carportInfo;
    }
	 /*
		 * 查询车位总数
		 * @param carportId 车位Id
		 * @return
		 */
	public int selectCount(Long carportId) {
		// TODO Auto-generated method stub
		int count=mapper.selectCount(carportId);
		return count;
		
	}  
    
}
