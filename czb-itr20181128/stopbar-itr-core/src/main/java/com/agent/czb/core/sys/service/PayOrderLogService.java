package com.agent.czb.core.sys.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agent.czb.common.json.JSONUtils;
import com.agent.czb.common.park.call.CallRemoteDllUtils;
import com.agent.czb.common.rest.Errors;
import com.agent.czb.common.rest.ResultHelp;
import com.agent.czb.common.rest.service.BasisService;
import com.agent.czb.common.spring.SpringContextUtil;
import com.agent.czb.common.utils.DateUtils;
import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.common.utils.InterceptDateUtils;
import com.agent.czb.common.utils.SendMsgUtils;
import com.agent.czb.core.eparking.FnEventCallback;
import com.agent.czb.core.park.enums.ParkCarStateEnum;
import com.agent.czb.core.park.enums.ParkOrderEnum;
import com.agent.czb.core.park.model.ParkCarStateModel;
import com.agent.czb.core.park.model.ParkGateLogModel;
import com.agent.czb.core.park.model.ParkOrderInfoModel;
import com.agent.czb.core.park.model.ParkWhiteListModel;
import com.agent.czb.core.park.service.ParkCarStateService;
import com.agent.czb.core.park.service.ParkGateLogService;
import com.agent.czb.core.park.service.ParkOrderInfoService;
import com.agent.czb.core.park.service.ParkWhiteListService;
import com.agent.czb.core.sys.enums.CarportInfoEnums;
import com.agent.czb.core.sys.enums.PayOrderEnums;
import com.agent.czb.core.sys.enums.WalletInfoEnums;
import com.agent.czb.core.sys.mapper.PayOrderLogMapper;
import com.agent.czb.core.sys.model.CarportBuyLogModel;
import com.agent.czb.core.sys.model.CarportInfoModel;
import com.agent.czb.core.sys.model.PayOrderLogModel;
import com.agent.czb.core.sys.model.PointInfoModel;
import com.agent.czb.core.sys.model.PointLogModel;
import com.agent.czb.core.sys.model.UserInfoModel;
import com.agent.czb.core.sys.model.UserWithdrawalInfoModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eparking.test.CarInto;

/**
 * 支付操作日志服务层
 */
@Service("payOrderLogService")
public class PayOrderLogService extends BasisService<PayOrderLogMapper, PayOrderLogModel> {
    private static Logger log = LoggerFactory.getLogger(PayOrderLogService.class);
    //推送消息给分享者
    private static final String SALER_TEMPLATEIS="373063";
    //推送消息给购买者
    private static final String BUYER_TEMPLATEIS="373070";
    @Autowired
    private CarportBuyLogService carportBuyLogService;
    @Autowired
    private FileUpdateInfoService fileUpdateInfoService;
    @Autowired
    private ParkCarStateService parkCarStateService;
    @Autowired
    private LocaInfoService locaInfoService;
    @Autowired
    private UserPlateInfoService userPlateInfoService;
    @Autowired
    private ParkOrderInfoService parkOrderInfoService;
    @Autowired
    private ParkWhiteListService parkWhiteListService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private WalletInfoService walletInfoService;
    @Autowired
    private CarportInfoService carportInfoService;
    @Autowired
    private ParkOrderInfoService orderInfoService;
    @Autowired
    private TransmaticLogService tranLogService;
    @Autowired
    private ParkGateLogService parkGateLogService;
    @Autowired
    private PointInfoService pointInfoService;
    @Autowired
    PointLogService pointLogService;
    
    /**
     * 支付宝购买车位
     */
    public int zfbBuyCarport(PayOrderLogModel payLog, String trade_no, String trade_no1) throws ParseException {
        Long orderId = payLog.getRefId();
        Long userId = payLog.getUserId();
        ParkOrderInfoModel orderInfo = parkOrderInfoService.selectById(orderId);
        long carportId = orderInfo.getRefId();
        // 设置已支付状态
        payLog.setPayState(PayOrderEnums.State.BEN_PAY.toValue());
        // 设置支付流水
        payLog.setPayNo(trade_no);
        // 设置支付时间
        payLog.setPayTime(new Date());
        update(payLog);

        Date date = new Date();
        // 车位信息
        CarportInfoModel carportInfo = carportInfoService.selectById(carportId);
        if (carportInfo == null) {
            throw new RuntimeException("车位不存在");
        }
        if (!carportInfo.getState().equals(CarportInfoEnums.State.OPEN.toValue())) {
            throw new RuntimeException("该车位不能购买");
        }
        // 添加购买日志
        CarportBuyLogModel carportBuyLog = new CarportBuyLogModel();
        carportBuyLog.setSartTime(orderInfo.getStartTime());
        carportBuyLog.setEndTime(orderInfo.getEndTime());
        carportBuyLog.setCreateTime(date);
        carportBuyLog.setCarportId(carportId);
        carportBuyLog.setUserId(userId);
        if (orderInfo.getPublishType()==0){
            carportBuyLog.setPublishType(0);
            carportBuyLog.setState("1");//长租已经被购买状态
        }else{
            carportBuyLog.setPublishType(1);
            carportBuyLog.setState("6");//短租被购买状态
            //通知停车场预约租车
            if (orderInfo!=null&&orderInfo.getPublishType()==1){
                 int i= reservedPark(orderInfo,orderId);
                 if (i==0){
                     return 0;
                 }
            }
        }
        int carportBuyLogId = carportBuyLogService.insert(carportBuyLog);

        String rek = orderInfo.getRemark();
        JSONObject object =JSON.parseObject(rek);
        //生成自动转账记录
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> datelist=null;

        //判断订单类型是否属于按时租车
        if(orderInfo.getPublishType()==1){
            datelist = InterceptDateUtils.getInfo(orderInfo.getStartTime() ,orderInfo.getEndTime(),1) ;
        }else{
            datelist = InterceptDateUtils.getInfo(orderInfo.getStartTime() ,orderInfo.getEndTime(), object.getInteger("cycle")+1) ;
            log.info("orderInfo.getStartTime():"+orderInfo.getStartTime()+"==orderInfo.getEndTime():"+orderInfo.getEndTime()+"==object.getInteger(\"cycle\")+1:"+object.getInteger("cycle")+1);
        }
        log.info("datelist.size:"+datelist.size());
        /**
        if(datelist.size()>0){
            for( int i = 0; i<datelist.size();i++){

                TransmaticLogModel transmaticLogModel = new TransmaticLogModel();
                transmaticLogModel.setCarportId(carportInfo.getId());
                transmaticLogModel.setBuyerId(userId);
                transmaticLogModel.setSellerId(carportInfo.getUserId());
                transmaticLogModel.setPrice(payLog.getPrice());
                transmaticLogModel.setQunatity(1);
                transmaticLogModel.setCreateDate(new Date());
                Log.info("datelist.get(i)::::"+datelist.get(i));
                transmaticLogModel.setStartDate(sdf.parse(datelist.get(i)));
                transmaticLogModel.setRefId(new Long(carportBuyLog.getId()));
                transmaticLogModel.setIsDel(1); // 1 .未转正，待转账。  0.已经转帐
                int a =tranLogService.insert(transmaticLogModel);
                Log.info(a+"================"+carportInfo.getId()+"||"+userId+"||"+carportInfo.getUserId()+"||"+payLog.getPrice()+"||"+sdf.parse(datelist.get(i))+"||"+new Long(carportBuyLog.getId()));

            }
        }
         */
        //判断是否是余额支付 支付订单
        // 余额支付才扣钱包中的钱（记录日志）
        if (payLog.getPayType().equals(PayOrderEnums.Type.YE_CARPORT.toValue())) {
            walletInfoService.cutPayment(userId, payLog.getAmount(),
                    WalletInfoEnums.Type.BUY_CARPORT.toValue(),
                    // 购买日志ID
                    carportBuyLog.getId());
        }

        // 卖车位加钱操作(记录日志)
        //是否是已经被卖出过一次 如果已经被卖出过一次就不再转款
        log.info(carportInfo.getIsBuy()+"____________-");
        if (carportInfo.getIsBuy()==null){
            //转账记录添加完成并且买家钱扣完之后，把订单生成当天的钱转账给卖家，并生成记录
            //tranLogService.updateMoney(carportInfo.getUserId(),carportBuyLog.getId());

            double mon = payLog.getAmount()*0.7;
            walletInfoService.addMoney(carportInfo.getUserId(), Double.valueOf(mon).longValue(),
                    WalletInfoEnums.Type.SELL_CARPORT.toValue(),
                    // 购买日志ID
                    carportBuyLog.getId());
                //收支明细
                PayOrderLogModel payOrder = new PayOrderLogModel();
                payOrder.setPayType(PayOrderEnums.Type.CP_RECHARGE.toValue());
                payOrder.setPayState(PayOrderEnums.State.BEN_PAY.toValue());
                payOrder.setRefType(PayOrderEnums.RefType.车位.toValue());
                payOrder.setRefId(orderInfo.getId());
                payOrder.setPrice(carportInfo.getMinutePrice());
                payOrder.setAmount(Double.valueOf(mon).longValue());
                payOrder.setUserId(carportInfo.getUserId());
                payOrder.setCreateTime(new Date());
                payOrder.setPayTime(new Date());
                insert(payOrder);
                

          }


        // 修改车位信息的状态
        CarportInfoModel temp = new CarportInfoModel();
        // 0:未购买；1:已购买
        temp.setId(carportId);
        //判断是否是短租
        if(orderInfo.getPublishType()==1){
            temp.setState(CarportInfoEnums.State.SHORT_BUY.toValue());
        }else{
            temp.setState(CarportInfoEnums.State.BUY.toValue());
        }
        if (carportInfo.getIsBuy()==null) {
            temp.setBuyLogId(carportBuyLog.getId());
            temp.setUpdateTime(date);
            //设置为第二次购买
            temp.setIsBuy(1);
        }
        carportInfoService.updateBySelective(temp);
        // 修改订单状态
        ParkOrderInfoModel tempOrder = new ParkOrderInfoModel();
        tempOrder.setId(orderInfo.getId());
        tempOrder.setPayId(payLog.getPayId());
        tempOrder.setState(ParkOrderEnum.State.PAY_COMPLETE.toValue());
        tempOrder.setUpdateTime(new Date());
        parkOrderInfoService.updateBySelective(tempOrder);
        // 将用户的车牌号添加到白名单 非预约租车车位
        /*if (orderInfo.getPublishType()==0) {
            parkWhiteListService.saveWhiteList(orderInfo, orderInfo.getUserId());
        }*/
        //将购买的分享车位添加到白名单
        parkWhiteListService.saveWhiteList(orderInfo, orderInfo.getUserId());
        
      //准备发送短信给发布者和购买者
        Date date1 = new Date();
		SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        StringBuffer salerMsg=new StringBuffer();
        if(rek == null){
        	//车位为短租
        String salerStr=salerMsg.append(carportInfo.getCity()) //城市
 				   .append(carportInfo.getArea()) //区域
 				   .append(carportInfo.getAddr()) //地址
 				   .append(carportInfo.getName())	//停车场名称
 				   .append(carportInfo.getCode())  //车位编号
 				   .append("【")  
 				   .append(orderInfo.getOpenTime()) // 获取开始时间
 				   .append("至")
 				   .append(sdf.format(orderInfo.getEndTime()))
 				   .append("】").toString();
		Long saleUserid=carportInfo.getUserId();
		UserInfoModel salerByPhone = userInfoService.selectUserIdByPhone(saleUserid);
		UserInfoModel buyerByPhone = userInfoService.selectUserIdByPhone(userId);
		String phone1 = salerByPhone.getPhone();
		String phone2 = buyerByPhone.getPhone();
		try {
			//发送给发布者
			SendMsgUtils.sendMsgRemind(phone1, salerStr, SALER_TEMPLATEIS, sdf2.format(date1));
			//发送给购买者
			SendMsgUtils.sendMsgRemind(phone2, salerStr, BUYER_TEMPLATEIS, orderInfo.getOpenTime());
		} catch (Exception e) {
			log.error(phone1+"发送短信失败");
			log.error(phone2+"发送短信失败");
		}
        }else{
        	//当车位是长租车位时
        	JSONObject jsonStr = JSON.parseObject(rek);
        	String[] str = carportInfo.getOpenTime().split("-");
        	String openTime=str[0];
        	String endTime1=str[1];
        	if(!openTime.substring(2, 3).equals(":")){
        		openTime=" 0"+openTime;
        	}
        	openTime=jsonStr.get("startdate").toString()+openTime;
        	endTime1=jsonStr.get("enddate").toString()+" "+endTime1;
        	String salerStr=salerMsg.append(carportInfo.getCity()) //城市
 				   .append(carportInfo.getArea()) //区域
 				   .append(carportInfo.getAddr()) //地址
 				   .append(carportInfo.getName())	//停车场名称
 				   .append(carportInfo.getCode())  //车位编号
 				   .append("【")  //车位编号
 				   .append(openTime)// 获取开始时间
 				   .append("至")
 				   .append(endTime1)
 				   .append("】").toString();
			Long saleUserid=carportInfo.getUserId();
			
			UserInfoModel salerByPhone = userInfoService.selectUserIdByPhone(saleUserid);
			UserInfoModel buyerByPhone = userInfoService.selectUserIdByPhone(userId);
			String phone1 = salerByPhone.getPhone();
			String phone2 = buyerByPhone.getPhone();
			try {
				//发送给发布者
				SendMsgUtils.sendMsgRemind(phone1, salerStr, SALER_TEMPLATEIS, sdf.format(date));
				//发送给购买者
				SendMsgUtils.sendMsgRemind(phone2, salerStr, BUYER_TEMPLATEIS, openTime);
			} catch (Exception e) {
				log.error(phone1+"发送短信失败");
				log.error(phone2+"发送短信失败");
			}
        }
        //封装订单
        insertOrderInfo(orderInfo);
        CarportInfoModel carportInfoModel = carportInfoService.selectById(carportId);
        String openTime = carportInfoModel.getOpenTime();
    	ParkWhiteListModel selectByCarportId = parkWhiteListService.selectByCarportId(orderInfo.getRefId());
        if(orderInfo.getPublishType()==1&&timeMinute(new Date(), orderInfo.getStartTime())+10>0){
        	addMothCarToEP(orderInfo);
        	HashMap<String,String> hashMap = new HashMap<>();
        	hashMap.put("park_id", selectByCarportId.getParkCode());
        	hashMap.put("plate_id", selectByCarportId.getPlateNum());
        	CarInto.deleteMonthCar(hashMap);
        }else if(orderInfo.getPublishType()==0){
			String[] split = openTime.split("-");
			Date d = new Date();
			String dateNowStr = sdf.format(d);
			SimpleDateFormat sdfhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sTime = dateNowStr + " " + split[0] + ":00";
			if(timeMinute(new Date(), sdfhms.parse(sTime))+10>0){
	        	addMothCarToEP(orderInfo);
	        	HashMap<String,String> hashMap = new HashMap<>();
	        	hashMap.put("park_id", selectByCarportId.getParkCode());
	        	hashMap.put("plate_id", selectByCarportId.getPlateNum());
	        	CarInto.deleteMonthCar(hashMap);
	        }
		}
      
      //支付成功添加到积分日志中
        PointLogModel pointLogModel=new PointLogModel();
        pointLogModel.setUserId(userId);
        Long pointid = pointInfoService.queryPointInfo(userId).getId();
        pointLogModel.setPointId(pointid);
        pointLogModel.setOpType("0");//操作类型
        pointLogModel.setType("0");//积分类型 
        pointLogModel.setRelId(null);
        pointLogModel.setPoint(orderInfo.getTotalPrice().intValue()/100);
        pointLogModel.setDescription("购买车位");
        pointLogModel.setCreateTime(new Date());
        pointLogService.insert(pointLogModel);
        //查询积分情况
        PointInfoModel queryPointInfo = pointInfoService.queryPointInfo(userId);
        PointInfoModel pointInfo=new PointInfoModel();
        Integer queryPointtotal = queryPointInfo.getTotal();
        //修改积分
        Integer total =(int) (queryPointtotal+(orderInfo.getTotalPrice())/100);
        pointInfo.setUserId(orderInfo.getUserId());
        pointInfo.setTotal(total);
        pointInfo.setUpdateTime(new Date());
		//更新积分
        pointInfoService.updatePointInfo(pointInfo);
        return carportBuyLogId;
    }
    // 时间相差分钟数
 		public Long timeMinute(Date first, Date end) {
 			Long timeTM = first.getTime() - end.getTime();
 			return timeTM / (1000 * 60);
 		}
    public void addMothCarToEP(ParkOrderInfoModel orderInfo){
    	//添加到停车场白名单
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        Map<String, String> map=new HashMap<>();
        map.put("park_id", orderInfo.getParkCode());
        if (orderInfo.getPlateNum()==""||orderInfo.getPlateNum()==null) {
        	map.put("plate_id", "京A111111");
		}else{
			map.put("plate_id", orderInfo.getPlateNum());//非空
		}
		map.put("isinout","");
		map.put("plate_color","");
		map.put("plate_type","月租车");
        map.put("plate_state", "正常");//非空
		map.put("plate_subtype","");
		map.put("free_time", "60");//非空
		//判断时间是否为空
		if (orderInfo.getStartTime()!=null&&!orderInfo.getStartTime().equals("")) {
			map.put("begin_date",simpleDateFormat.format(orderInfo.getStartTime().getTime()).toString());
		}else{
			map.put("begin_date","2018-01-01 00:00:00");
		}
		if (orderInfo.getEndTime()!=null&&!orderInfo.getEndTime().equals("")) {
			map.put("end_date", simpleDateFormat.format(orderInfo.getEndTime().getTime()).toString());//非空
		} else {
			map.put("end_date", "2018-01-01 01:00:00");//非空
		}
		UserInfoModel userinfo=userInfoService.selectById(orderInfo.getUserId());
		map.put("carown_name",userinfo.getNickName());
		map.put("carown_sex",userinfo.getSexStr());
		map.put("carown_phone",userinfo.getPhone());//非空 车主电话号码
		map.put("carown_address","");//车主住址
		map.put("charg_scheme","");//计费方案
		map.put("del_record","0");//是否删除
		CarInto.addMonthCar(map);
    }
    /**
     * 余额购买车位（使用余额）
     */
    public Long yeBuyCarport(ParkOrderInfoModel orderInfo) throws ParseException {
        if (!orderInfo.getType().equals(ParkOrderEnum.Type.BUY_CARPORT.toValue())) {
            throw Errors.baseServiceException("订单类型错误");
        }
        // 添加支付订单日志消息
        PayOrderLogModel payOrder = new PayOrderLogModel();
        payOrder.setUserId(orderInfo.getUserId());
        payOrder.setPayId(FrameUtils.generatePayId());
        payOrder.setPayType(PayOrderEnums.Type.YE_CARPORT.toValue());
        payOrder.setPayState(PayOrderEnums.State.NOT_PAY.toValue());
        payOrder.setRefType(PayOrderEnums.RefType.车位.toValue());
        payOrder.setRefId(orderInfo.getId());
        payOrder.setQuantity(orderInfo.getQuantity());
        payOrder.setPrice(orderInfo.getPrice());
        payOrder.setAmount(orderInfo.getTotalPrice());
        payOrder.setUserId(orderInfo.getUserId());
        payOrder.setCreateTime(new Date());
        insert(payOrder);
        zfbBuyCarport(payOrder, null, null);
        return payOrder.getPayId();
    }

    /**
     * 支付宝充值
     */
    public long zfbRecharge(PayOrderLogModel payOrderLog, String trade_no, String trade_no1) {
        // 设置已支付状态
        payOrderLog.setPayState(PayOrderEnums.State.BEN_PAY.toValue());
        // 设置支付流水
        payOrderLog.setPayNo(trade_no);
        // 设置支付时间
        payOrderLog.setPayTime(new Date());
        update(payOrderLog);

        // 加钱操作(记录日志)
        return walletInfoService.addMoney(payOrderLog.getUserId(), payOrderLog.getAmount(),
                WalletInfoEnums.Type.ZFB_ADD.toValue(),
                // 购买日志ID
                payOrderLog.getPayId());
    }

    /**
     * 手动充值
     */
    public long sdRecharge(Long userId, Long amount) {
        // 添加订单消息（手动充值）
        PayOrderLogModel payOrder = new PayOrderLogModel();
        payOrder.setPayType(PayOrderEnums.Type.SD_RECHARGE.toValue());
        payOrder.setPayState(PayOrderEnums.State.BEN_PAY.toValue());
        payOrder.setRefType(null);
        payOrder.setRefId(null);
        payOrder.setQuantity(1);
        payOrder.setPrice(amount);
        payOrder.setAmount(amount);
        payOrder.setUserId(userId);
        payOrder.setCreateTime(new Date());
        payOrder.setPayTime(new Date());
        insert(payOrder);

        // 添加金额
        return walletInfoService.addMoney(userId, amount,
                WalletInfoEnums.Type.ADD_MONEY.toValue(), null);
    }

    /**
     * 支付宝缴费
     */
    public int zfbAMount(PayOrderLogModel payLog, String trade_no, String trade_no1) {
        // 如果余额支付则扣余额
        if (payLog.getPayType().equals(PayOrderEnums.Type.YE_AMOUNT.toValue())) {
            walletInfoService.cutPayment(payLog.getUserId(), payLog.getAmount(),
                    WalletInfoEnums.Type.BUY_CARPORT.toValue(),
                    // 购买日志ID
                    payLog.getPayId());

        }
        // 设置已支付状态
        payLog.setPayState(PayOrderEnums.State.BEN_PAY.toValue());
        // 设置支付流水
        payLog.setPayNo(trade_no);
        // 设置支付时间
        payLog.setPayTime(new Date());
        update(payLog);

        // 修改订单状态
        Long orderId = payLog.getRefId();
        ParkOrderInfoModel order = parkOrderInfoService.selectById(orderId);
        ParkOrderInfoModel orderInfo = new ParkOrderInfoModel();
        orderInfo.setId(orderId);
        if (order.getState().equals(ParkOrderEnum.State.ADVANCE__UNPAID.toValue())) {
        	orderInfo.setState(ParkOrderEnum.State.ADVANCE__PAID.toValue());
		}
        if (order.getState().equals(ParkOrderEnum.State.OVERTIME_UNPAID.toValue())) {
        	orderInfo.setState(ParkOrderEnum.State.OVERTIME__PAID.toValue());
		}
        orderInfo.setUpdateTime(new Date());
        orderInfo.setEndTime(new Date());
        orderInfo.setPayId(payLog.getPayId());
        orderInfoService.updateBySelective(orderInfo);
        //查询积分情况
        PointInfoModel queryPointInfo = pointInfoService.queryPointInfo(order.getUserId());
        PointInfoModel pointInfo=new PointInfoModel();
        Integer queryPointtotal = queryPointInfo.getTotal();
        //支付成功添加到积分日志中
        PointLogModel pointLogModel=new PointLogModel();
        pointLogModel.setUserId(order.getUserId());
        Long pointid = pointInfoService.queryPointInfo(order.getUserId()).getId();
        pointLogModel.setPointId(pointid);
        pointLogModel.setOpType("0");//操作类型
        pointLogModel.setType("0");//积分类型 
        pointLogModel.setRelId(null);
        pointLogModel.setPoint(order.getTotalPrice().intValue()/100);
        if(orderInfo.getState().equals("404")){
        	 pointLogModel.setDescription("车位超时");
        }else if(orderInfo.getState().equals("402")){
        	 pointLogModel.setDescription("预缴费");
        }
        pointLogModel.setCreateTime(new Date());
        pointLogService.insert(pointLogModel);
        //修改积分
        Integer total =(int) (queryPointtotal+(order.getTotalPrice())/100);
        pointInfo.setUserId(order.getUserId());
        pointInfo.setTotal(total);
        pointInfo.setUpdateTime(new Date());
		//更新积分
        pointInfoService.updatePointInfo(pointInfo);
     
        // 修改停车场车辆状态
        Long refId = order.getRefId();
        List<ParkCarStateModel> carStates = parkCarStateService.pageList(FrameUtils.newMap("inLogId", refId.toString()));
        if (carStates.size() > 0) {
            ParkCarStateModel parkCarStateModel = carStates.get(0);
            parkCarStateModel.setState(ParkCarStateEnum.State.已支付.toValue());
            parkCarStateService.update(parkCarStateModel);
        }
        if (StringUtils.equals(payLog.getRefType(), PayOrderEnums.RefType.停车场缴费.toValue())) {
            // 通知停车场支付成功
            //CallRemoteDllUtils.paySuccess(order.getParkCode(), order.getPlateNum(), order.getRefId().toString(), "3", DateUtils.format(order.getCreateTime(), F_YMDHMS_), payLog.getAmount());
          //调用对接接口，在收费端完成支付
            ParkCarStateModel parkCarStateModel = new ParkCarStateModel();
            parkCarStateModel.setParkCode(order.getParkCode());
            parkCarStateModel.setPlateNum(order.getPlateNum());
            ParkCarStateModel parkCarState = parkCarStateService.selectByPlateNumAndParkCode(parkCarStateModel);
            if (order.getState().equals("401")) {
            	toPay(parkCarState);
			}
			
            //FnEventCallback.prepayAmount=null;
        }else{
        }
        return 1;
    }
    public void toPay(ParkCarStateModel parkCarState){
    	ParkGateLogModel parkGateLog = parkGateLogService.selectById(parkCarState.getInLogId());
    	Map<String, String> map = new HashMap<>();
		map.put("park_id", parkCarState.getParkCode());//停车场ID  非空
		map.put("port_id", "");//进出口ID  提前支付可不填
		map.put("plate_id",parkCarState.getPlateNum());//车牌号  车牌和订单号二选一
		map.put("order_id","");//订单号 
		map.put("cario_id",String.valueOf(parkGateLog.getOrderId()));//停车场本地进出ID  非空
		map.put("pay_time", DateUtils.F_YMDHMS_.format(new Date()));//支付时间非空
		map.put("pay_amount",FnEventCallback.prepayAmount.getPay_amount());//支付金额非空
		map.put("pay_id", "");//支付流水号
		map.put("pay_finish_type","0" );//0预支付完成  1出场支付完成
		map.put("accept_account_id", "");//接受帐号ID
		map.put("pay_type","2");//支付账户类型 1 微信 2 支付宝  非空
		CarInto.payOk(map);
    }
    /**
     * 余额缴费
     */
    public Long yeAMount(ParkOrderInfoModel orderInfo) {
        if (!orderInfo.getType().equals(ParkOrderEnum.Type.APP_PAY.toValue())) {
            throw Errors.baseServiceException("订单类型错误");
        }
        // 添加支付订单日志消息
        PayOrderLogModel payOrder = new PayOrderLogModel();
        payOrder.setUserId(orderInfo.getUserId());
        payOrder.setPayId(FrameUtils.generatePayId());
        payOrder.setPayType(PayOrderEnums.Type.YE_AMOUNT.toValue());
        payOrder.setPayState(PayOrderEnums.State.NOT_PAY.toValue());
        payOrder.setRefType(PayOrderEnums.RefType.停车场缴费.toValue());
        payOrder.setRefId(orderInfo.getId());
        payOrder.setQuantity(orderInfo.getQuantity());
        payOrder.setPrice(orderInfo.getPrice());
        payOrder.setAmount(orderInfo.getTotalPrice());
        payOrder.setUserId(orderInfo.getUserId());
        payOrder.setCreateTime(new Date());
        insert(payOrder);
        zfbAMount(payOrder, null, null);
        return payOrder.getPayId();
    }

    /**
     * 提现
     */
    public Long withdrawal(UserWithdrawalInfoModel userWithdrawalInfo) {
        // 添加支付订单日志消息
        PayOrderLogModel payOrder = new PayOrderLogModel();
        payOrder.setUserId(userWithdrawalInfo.getUserId());
        payOrder.setPayId(FrameUtils.generatePayId());
        payOrder.setPayType(PayOrderEnums.Type.USER_WITHDRAW.toValue());
        payOrder.setPayState(PayOrderEnums.State.BEN_PAY.toValue());
        payOrder.getRefId();
        payOrder.setQuantity(1);
        payOrder.setPrice(userWithdrawalInfo.getAmount());
        payOrder.setAmount(userWithdrawalInfo.getAmount());
        payOrder.setCreateTime(new Date());
        insert(payOrder);

        walletInfoService.cutPayment(userWithdrawalInfo.getUserId(), userWithdrawalInfo.getAmount(),
                WalletInfoEnums.Type.USER_WITHDRAWAL.toValue(),
                // 购买日志ID
                payOrder.getPayId());
        return payOrder.getPayId();
    }

    /**
     * 创建人:qany.
     * 创建时间:2016/12/19:11:34.
     * 描述: 停车场记录 预约车
     */
    public int reservedPark(ParkOrderInfoModel order,Long orderId){
        Map<String, Object> reservedPark = new HashMap<>();
        reservedPark.put("interfacetype","12");
        reservedPark.put("platenumber", order.getPlateNum());
        reservedPark.put("starttime", order.getOpenTime());
        reservedPark.put("endtime", DateUtils.format(order.getEndTime(),DateUtils.F_YMDHMS_));
        reservedPark.put("orderid", orderId+"");
        Map<String, Object> map = CallRemoteDllUtils.reservedPark(order.getParkCode(),reservedPark);
        if (!CallRemoteDllUtils.isSuccess(map)) {
            return 0;
        }
        Map<String, Object> data = JSONUtils.jsonToMap(map.get("data").toString());
        if (data.get("status") != null && data.get("status").toString().equals("0")) {
            return 1;
        }
        return 1;
    }

	public List<PayOrderLogModel> selectByRefId(Long id) {
		// TODO Auto-generated method stub
		return mapper.selectByRefId(id);
	}
	
	/**
	 * 微信支付生成订单
	 * @param payOrderLog
	 * @param trade_no
	 * @param trade_no1
	 * @return
	 * @throws ParseException 
	 */
	public int weChatBuyCarport(PayOrderLogModel payOrderLog, String trade_no, String trade_no1) throws ParseException{
        Long orderId = payOrderLog.getRefId();
        Long userId = payOrderLog.getUserId();
        ParkOrderInfoModel orderInfo = parkOrderInfoService.selectById(orderId);
        long carportId = orderInfo.getRefId();
        // 设置已支付状态
        payOrderLog.setPayState(PayOrderEnums.State.BEN_PAY.toValue());
        // 设置支付流水
        payOrderLog.setPayNo(trade_no);
        // 设置支付时间
        payOrderLog.setPayTime(new Date());
        update(payOrderLog);
        Date date = new Date();
        // 车位信息
        CarportInfoModel carportInfo = carportInfoService.selectById(carportId);
        if (carportInfo == null) {
            throw new RuntimeException("车位不存在");
        }
        if (!carportInfo.getState().equals(CarportInfoEnums.State.OPEN.toValue())) {
            throw new RuntimeException("该车位不能购买");
        }
        // 添加购买日志
        CarportBuyLogModel carportBuyLog = new CarportBuyLogModel();
        carportBuyLog.setSartTime(orderInfo.getStartTime());
        carportBuyLog.setEndTime(orderInfo.getEndTime());
        carportBuyLog.setCreateTime(date);
        carportBuyLog.setCarportId(carportId);
        carportBuyLog.setUserId(userId);
        if (orderInfo.getPublishType()==0){
            carportBuyLog.setPublishType(0);
            carportBuyLog.setState("1");//长租已经被购买状态
        }else{
            carportBuyLog.setPublishType(1);
            carportBuyLog.setState("6");//短租被购买状态
            //通知停车场预约租车
            if (orderInfo!=null&&orderInfo.getPublishType()==1){
                 int i= reservedPark(orderInfo,orderId);
                 if (i==0){
                     return 0;
                 }
            }
        }
        int carportBuyLogId = carportBuyLogService.insert(carportBuyLog);
        String rek = orderInfo.getRemark();
        JSONObject object =JSON.parseObject(rek);
        //生成自动转账记录
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> datelist=null;

        //判断订单类型是否属于按时租车
        if(orderInfo.getPublishType()==1){
            datelist = InterceptDateUtils.getInfo(orderInfo.getStartTime() ,orderInfo.getEndTime(),1) ;
        }else{
            datelist = InterceptDateUtils.getInfo(orderInfo.getStartTime() ,orderInfo.getEndTime(), object.getInteger("cycle")+1) ;
            log.info("orderInfo.getStartTime():"+orderInfo.getStartTime()+"==orderInfo.getEndTime():"+orderInfo.getEndTime()+"==object.getInteger(\"cycle\")+1:"+object.getInteger("cycle")+1);
        }
        log.info("datelist.size:"+datelist.size());
        //判断是否是余额支付 支付订单
        // 余额支付才扣钱包中的钱（记录日志）
        if (payOrderLog.getPayType().equals(PayOrderEnums.Type.YE_CARPORT.toValue())) {
            walletInfoService.cutPayment(userId, payOrderLog.getAmount(),
                    WalletInfoEnums.Type.BUY_CARPORT.toValue(),
                    // 购买日志ID
                    carportBuyLog.getId());
        }
        // 卖车位加钱操作(记录日志)
        //是否是已经被卖出过一次 如果已经被卖出过一次就不再转款
        log.info(carportInfo.getIsBuy()+"____________-");
        if (carportInfo.getIsBuy()==null){
            //转账记录添加完成并且买家钱扣完之后，把订单生成当天的钱转账给卖家，并生成记录
            //tranLogService.updateMoney(carportInfo.getUserId(),carportBuyLog.getId());
            double mon = payOrderLog.getAmount()*0.7;
            walletInfoService.addMoney(carportInfo.getUserId(), Double.valueOf(mon).longValue(),
            		WalletInfoEnums.Type.SELL_CARPORT.toValue(),
                    // 购买日志ID
                    carportBuyLog.getId());
                //收支明细
                PayOrderLogModel payOrder = new PayOrderLogModel();
                payOrder.setPayType(PayOrderEnums.Type.CP_RECHARGE.toValue());
                payOrder.setPayState(PayOrderEnums.State.BEN_PAY.toValue());
                payOrder.setRefType(PayOrderEnums.RefType.车位.toValue());
                payOrder.setRefId(orderInfo.getId());
                payOrder.setPrice(carportInfo.getMinutePrice());
                payOrder.setAmount(Double.valueOf(mon).longValue());
                payOrder.setUserId(carportInfo.getUserId());
                payOrder.setCreateTime(new Date());
                payOrder.setPayTime(new Date());
                insert(payOrder);
          }
        // 修改车位信息的状态
        CarportInfoModel temp = new CarportInfoModel();
        // 0:未购买；1:已购买
        temp.setId(carportId);
        //判断是否是短租
        if(orderInfo.getPublishType()==1){
            temp.setState(CarportInfoEnums.State.SHORT_BUY.toValue());
        }else{
            temp.setState(CarportInfoEnums.State.BUY.toValue());
        }
        if (carportInfo.getIsBuy()==null) {
            temp.setBuyLogId(carportBuyLog.getId());
            temp.setUpdateTime(date);
            //设置为第二次购买
            temp.setIsBuy(1);
        }
        carportInfoService.updateBySelective(temp);
        // 修改订单状态
        ParkOrderInfoModel tempOrder = new ParkOrderInfoModel();
        tempOrder.setId(orderInfo.getId());
        tempOrder.setPayId(payOrderLog.getPayId());
        tempOrder.setState(ParkOrderEnum.State.PAY_COMPLETE.toValue());
        tempOrder.setUpdateTime(new Date());
        parkOrderInfoService.updateBySelective(tempOrder);
        // 将用户的车牌号添加到白名单 非预约租车车位
        /*if (orderInfo.getPublishType()==0) {
            parkWhiteListService.saveWhiteList(orderInfo, orderInfo.getUserId());
        }*/
        //将购买的分享车位添加到白名单
        parkWhiteListService.saveWhiteList(orderInfo, orderInfo.getUserId());
      //准备发送短信给发布者和购买者
        Date date1 = new Date();
		SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        StringBuffer salerMsg=new StringBuffer();
        if(rek == null){
        	//车位为短租
        String salerStr=salerMsg.append(carportInfo.getCity()) //城市
 				   .append(carportInfo.getArea()) //区域
 				   .append(carportInfo.getAddr()) //地址
 				   .append(carportInfo.getName())	//停车场名称
 				   .append(carportInfo.getCode())  //车位编号
 				   .append("【")  
 				   .append(orderInfo.getOpenTime()) // 获取开始时间
 				   .append("至")
 				   .append(sdf.format(orderInfo.getEndTime()))
 				   .append("】").toString();
		Long saleUserid=carportInfo.getUserId();
		UserInfoModel salerByPhone = userInfoService.selectUserIdByPhone(saleUserid);
		UserInfoModel buyerByPhone = userInfoService.selectUserIdByPhone(userId);
		String phone1 = salerByPhone.getPhone();
		String phone2 = buyerByPhone.getPhone();
		try {
			//发送给发布者
			SendMsgUtils.sendMsgRemind(phone1, salerStr, SALER_TEMPLATEIS, sdf2.format(date1));
			//发送给购买者
			SendMsgUtils.sendMsgRemind(phone2, salerStr, BUYER_TEMPLATEIS, orderInfo.getOpenTime());
		} catch (Exception e) {
			log.error(phone1+"发送短信失败");
			log.error(phone2+"发送短信失败");
		}
        }else{
        	//当车位是长租车位时
        	JSONObject jsonStr = JSON.parseObject(rek);
        	String[] str = carportInfo.getOpenTime().split("-");
        	String openTime=str[0];
        	String endTime1=str[1];
        	if(!openTime.substring(2, 3).equals(":")){
        		openTime=" 0"+openTime;
        	}
        	openTime=jsonStr.get("startdate").toString()+openTime;
        	endTime1=jsonStr.get("enddate").toString()+" "+endTime1;
        	String salerStr=salerMsg.append(carportInfo.getCity()) //城市
 				   .append(carportInfo.getArea()) //区域
 				   .append(carportInfo.getAddr()) //地址
 				   .append(carportInfo.getName())	//停车场名称
 				   .append(carportInfo.getCode())  //车位编号
 				   .append("【")  //车位编号
 				   .append(openTime)// 获取开始时间
 				   .append("至")
 				   .append(endTime1)
 				   .append("】").toString();
			Long saleUserid=carportInfo.getUserId();
			
			UserInfoModel salerByPhone = userInfoService.selectUserIdByPhone(saleUserid);
			UserInfoModel buyerByPhone = userInfoService.selectUserIdByPhone(userId);
			String phone1 = salerByPhone.getPhone();
			String phone2 = buyerByPhone.getPhone();
			try {
				//发送给发布者
				SendMsgUtils.sendMsgRemind(phone1, salerStr, SALER_TEMPLATEIS, sdf.format(date));
				//发送给购买者
				SendMsgUtils.sendMsgRemind(phone2, salerStr, BUYER_TEMPLATEIS, openTime);
			} catch (Exception e) {
				log.error(phone1+"发送短信失败");
				log.error(phone2+"发送短信失败");
			}
        }
        //封装订单
        insertOrderInfo(orderInfo);
        CarportInfoModel carportInfoModel = carportInfoService.selectById(carportId);
        String openTime = carportInfoModel.getOpenTime();
    	ParkWhiteListModel selectByCarportId = parkWhiteListService.selectByCarportId(orderInfo.getRefId());
        if(orderInfo.getPublishType()==1&&timeMinute(new Date(), orderInfo.getStartTime())+10>0){
        	addMothCarToEP(orderInfo);
        	HashMap<String,String> hashMap = new HashMap<>();
        	hashMap.put("park_id", selectByCarportId.getParkCode());
        	hashMap.put("plate_id", selectByCarportId.getPlateNum());
        	CarInto.deleteMonthCar(hashMap);
        }else if(orderInfo.getPublishType()==0){
			String[] split = openTime.split("-");
			Date d = new Date();
			String dateNowStr = sdf.format(d);
			SimpleDateFormat sdfhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sTime = dateNowStr + " " + split[0] + ":00";
			if(timeMinute(new Date(), sdfhms.parse(sTime))+10>0){
	        	addMothCarToEP(orderInfo);
	        	HashMap<String,String> hashMap = new HashMap<>();
	        	hashMap.put("park_id", selectByCarportId.getParkCode());
	        	hashMap.put("plate_id", selectByCarportId.getPlateNum());
	        	CarInto.deleteMonthCar(hashMap);
	        }
		}
       
      //支付成功添加到积分日志中
        PointLogModel pointLogModel=new PointLogModel();
        pointLogModel.setUserId(userId);
        Long pointid = pointInfoService.queryPointInfo(userId).getId();
        pointLogModel.setPointId(pointid);
        pointLogModel.setOpType("0");//操作类型
        pointLogModel.setType("0");//积分类型 
        pointLogModel.setRelId(null);
        pointLogModel.setPoint(orderInfo.getTotalPrice().intValue()/100);
        pointLogModel.setDescription("购买车位");
        pointLogModel.setCreateTime(new Date());
        pointLogService.insert(pointLogModel);
        //查询积分情况
        PointInfoModel queryPointInfo = pointInfoService.queryPointInfo(userId);
        PointInfoModel pointInfo=new PointInfoModel();
        Integer queryPointtotal = queryPointInfo.getTotal();
        //修改积分
        Integer total =(int) (queryPointtotal+(orderInfo.getTotalPrice())/100);
        pointInfo.setUserId(orderInfo.getUserId());
        pointInfo.setTotal(total);
        pointInfo.setUpdateTime(new Date());
		//更新积分
        pointInfoService.updatePointInfo(pointInfo);
        return carportBuyLogId;
    
	}
	
	public void insertOrderInfo(ParkOrderInfoModel orderInfo){
		FnEventCallback.prepayAmount=null;
    	Map<String, String> mapToCar = new HashMap<>();
    	mapToCar.put("park_id", orderInfo.getParkCode());
    	mapToCar.put("plate_id", orderInfo.getPlateNum());
    	mapToCar.put("order_id", "");
        CarInto.prepayAmount(mapToCar);
        try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (FnEventCallback.prepayAmount!=null&&FnEventCallback.prepayAmount.getError_des().equals("查询预支付金额成功！")) {
        	//停车费用
            Long payAmount = Long.valueOf(FnEventCallback.prepayAmount.getPay_amount().replace(".", ""));
            if (payAmount>0) {
            	// 封装订单
            	ParkCarStateModel parkCarState = new ParkCarStateModel();
    			parkCarState.setParkCode(orderInfo.getParkCode());
    			parkCarState.setPlateNum(orderInfo.getPlateNum());
            	ParkCarStateModel selectByPlateNumAndParkCode = parkCarStateService.selectByPlateNumAndParkCode(parkCarState);
            	
                ParkOrderInfoModel parkOrderInfoModel = new ParkOrderInfoModel();
                parkOrderInfoModel.setUserId(orderInfo.getUserId());
                parkOrderInfoModel.setUserName(null);
                parkOrderInfoModel.setParkCode(orderInfo.getParkCode());
                parkOrderInfoModel.setPlateNum(orderInfo.getPlateNum());
                parkOrderInfoModel.setModel("4");
                parkOrderInfoModel.setCode(FnEventCallback.prepayAmount.getPark_name());
                //设置价格
                parkOrderInfoModel.setTotalPrice(payAmount);
                parkOrderInfoModel.setRefId(selectByPlateNumAndParkCode.getInLogId());
                parkOrderInfoModel.setType(ParkOrderEnum.Type.APP_PAY.toValue());
                parkOrderInfoModel.setState(ParkOrderEnum.State.ADVANCE__UNPAID.toValue());
                parkOrderInfoModel.setCreateTime(new Date());
                parkOrderInfoModel.setUpdateTime(new Date());
                if (orderInfo.getCode()!=null||orderInfo.getCode()!="") {
                    parkOrderInfoModel.setCode(orderInfo.getCode());
    			}
                if (orderInfo.getRemark()!=null&&orderInfo.getRemark()!="") {
    				parkOrderInfoModel.setRemark(orderInfo.getRemark());
    			}
                    
                if (orderInfo.getOpenTime()!=null&&orderInfo.getOpenTime()!="") {
                    parkOrderInfoModel.setOpenTime(orderInfo.getOpenTime());
    			}
                parkOrderInfoModel.setStartTime(selectByPlateNumAndParkCode.getInDate());
                parkOrderInfoModel.setEndTime(new Date());
                //生成订单
                parkOrderInfoService.insert(parkOrderInfoModel);
			}
		}
        
	}
	
	/**
     * 微信充值充值
     */
    public long weChatRecharge(PayOrderLogModel payOrderLog, String trade_no, String trade_no1) {
        // 设置已支付状态
        payOrderLog.setPayState(PayOrderEnums.State.BEN_PAY.toValue());
        // 设置支付流水
        payOrderLog.setPayNo(trade_no);
        // 设置支付时间
        payOrderLog.setPayTime(new Date());
        update(payOrderLog);

        // 加钱操作(记录日志)
        return walletInfoService.addMoney(payOrderLog.getUserId(), payOrderLog.getAmount(),
                WalletInfoEnums.Type.WECHAT_ADD.toValue(),
                // 购买日志ID
                payOrderLog.getPayId());
    }
    public List<PayOrderLogModel> selectDataToExcel(PayOrderLogModel model) {
		return mapper.selectDataToExcel(model);
	}
    /**
	 * 微信缴费
	 * @param payLog
	 * @param trade_no
	 * @param trade_no1
	 * @return
	 */
	public int weChatAMount(PayOrderLogModel payLog, String trade_no, String trade_no1) {
        // 如果余额支付则扣余额
        if (payLog.getPayType().equals(PayOrderEnums.Type.YE_AMOUNT.toValue())) {
            walletInfoService.cutPayment(payLog.getUserId(), payLog.getAmount(),
                    WalletInfoEnums.Type.BUY_CARPORT.toValue(),
                    // 购买日志ID
                    payLog.getPayId());
        }
        // 设置已支付状态
        payLog.setPayState(PayOrderEnums.State.BEN_PAY.toValue());
        // 设置支付流水
        payLog.setPayNo(trade_no);
        // 设置支付时间
        payLog.setPayTime(new Date());
        update(payLog);
        // 修改订单状态
        Long orderId = payLog.getRefId();
        ParkOrderInfoModel order = parkOrderInfoService.selectById(orderId);
        ParkOrderInfoModel orderInfo = new ParkOrderInfoModel();
        orderInfo.setId(orderId);
        if (order.getState().equals(ParkOrderEnum.State.ADVANCE__UNPAID.toValue())) {
        	orderInfo.setState(ParkOrderEnum.State.ADVANCE__PAID.toValue());
		}
        if (order.getState().equals(ParkOrderEnum.State.OVERTIME_UNPAID.toValue())) {
        	orderInfo.setState(ParkOrderEnum.State.OVERTIME__PAID.toValue());
		}
        orderInfo.setUpdateTime(new Date());
        orderInfo.setEndTime(new Date());
        orderInfo.setPayId(payLog.getPayId());
        orderInfoService.updateBySelective(orderInfo);
        //查询积分情况
        PointInfoModel queryPointInfo = pointInfoService.queryPointInfo(order.getUserId());
        PointInfoModel pointInfo=new PointInfoModel();
        Integer queryPointtotal = queryPointInfo.getTotal();
        //支付成功添加到积分日志中
        PointLogModel pointLogModel=new PointLogModel();
        pointLogModel.setUserId(order.getUserId());
        Long pointid = pointInfoService.queryPointInfo(order.getUserId()).getId();
        pointLogModel.setPointId(pointid);
        pointLogModel.setOpType("0");//操作类型
        pointLogModel.setType("0");//积分类型 
        pointLogModel.setRelId(null);
        pointLogModel.setPoint(order.getTotalPrice().intValue()/100);
        if(orderInfo.getState().equals("404")){
        	 pointLogModel.setDescription("车位超时");
        }else if(orderInfo.getState().equals("402")){
        	 pointLogModel.setDescription("预缴费");
        }
        pointLogModel.setCreateTime(new Date());
        pointLogService.insert(pointLogModel);
        //修改积分
        Integer total =(int) (queryPointtotal+(order.getTotalPrice())/100);
        pointInfo.setUserId(order.getUserId());
        pointInfo.setTotal(total);
        pointInfo.setUpdateTime(new Date());
		//更新积分
        pointInfoService.updatePointInfo(pointInfo);
        // 修改停车场车辆状态
        Long refId = order.getRefId();
        List<ParkCarStateModel> carStates = parkCarStateService.pageList(FrameUtils.newMap("inLogId", refId.toString()));
        if (carStates.size() > 0) {
            ParkCarStateModel parkCarStateModel = carStates.get(0);
            parkCarStateModel.setState(ParkCarStateEnum.State.已支付.toValue());
            parkCarStateService.update(parkCarStateModel);
        }
        if (StringUtils.equals(payLog.getRefType(), PayOrderEnums.RefType.停车场缴费.toValue())) {
            // 通知停车场支付成功
            //CallRemoteDllUtils.paySuccess(order.getParkCode(), order.getPlateNum(), order.getRefId().toString(), "3", DateUtils.format(order.getCreateTime(), F_YMDHMS_), payLog.getAmount());
          //调用对接接口，在收费端完成支付
            ParkCarStateModel parkCarStateModel = new ParkCarStateModel();
            parkCarStateModel.setParkCode(order.getParkCode());
            parkCarStateModel.setPlateNum(order.getPlateNum());
            ParkCarStateModel parkCarState = parkCarStateService.selectByPlateNumAndParkCode(parkCarStateModel);
			toPay(parkCarState);
        }else{
        	
        }
        return 1;
    }
	/**
     * 微信充值充值
     */
    public long weChatRecharge(PayOrderLogModel payOrderLog, String trade_no) {
        // 设置已支付状态
        payOrderLog.setPayState(PayOrderEnums.State.BEN_PAY.toValue());
        // 设置支付流水
        payOrderLog.setPayNo(trade_no);
        // 设置支付时间
        payOrderLog.setPayTime(new Date());
        update(payOrderLog);
        // 加钱操作(记录日志)
        return walletInfoService.addMoney(payOrderLog.getUserId(), payOrderLog.getAmount(),
                WalletInfoEnums.Type.WECHAT_ADD.toValue(),
                // 购买日志ID
                payOrderLog.getPayId());
    }


}
