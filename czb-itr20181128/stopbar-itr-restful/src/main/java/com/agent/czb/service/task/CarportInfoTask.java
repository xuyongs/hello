package com.agent.czb.service.task;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import org.apache.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.agent.czb.common.spring.SpringContextUtil;
import com.agent.czb.common.utils.SendMsgUtils;
import com.agent.czb.core.park.model.JPushModel;
import com.agent.czb.core.park.service.ParkWhiteListService;
import com.agent.czb.core.sys.service.CarportInfoService;
import com.agent.czb.core.sys.service.TransmaticLogService;
import com.agent.czb.service.utils.JPushClientUtils;
import com.eparking.test.CarInto;
import com.esotericsoftware.minlog.Log;

import cn.jpush.api.JPushClient;


/**
 * @author linkai
 * @since 16/9/13
 */
@Component
public class CarportInfoTask {
    private static Logger logger = LoggerFactory.getLogger(CarportInfoTask.class);
    //车位超时短信提醒模板ID
    public static final String OVERTIME_REMIND="373073";

    @Autowired
    private CarportInfoService carportInfoService;
    @Autowired
    private ParkWhiteListService parkWhiteListService;

    @Autowired
    private TransmaticLogService transmaticLogService;

    /**
     * 更新订单过期
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void updateOverdueStatus() {
        logger.info("CarprotInfo task update status start...");
        carportInfoService.updateOverdueStatus();
        logger.info("CarprotInfo task update status end...");
    }

    /**
     * 更新车位失效
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void updateWhiteListExp() {
        logger.info("CarprotInfo task update status start...");
        parkWhiteListService.updateWhiteListExp();
        logger.info("CarprotInfo task update status end...");
    }

/**
      @Scheduled(cron = "0 0 23 * * ? ")
    public void updateMoney() {
        logger.info("updateMoney update status start...");
        transmaticLogService.updateMoney();
        logger.info("updateMoney update status end...");
    }
*/	
    /**
     * 添加修改白名单
     */
   @Scheduled(cron = "0 0/59 * * * ?")
    public void newAddOrUpdateWhiteList(){
	   logger.info("【CarportInfoTask.newAddOrUpdateWhiteList】 eparking add or update local data start...");
    	CarInto.find();
    	logger.info("【CarportInfoTask.newAddOrUpdateWhiteList】 eparking add or update local data end...");
    }
   /**
    *删除白名单
    */
  @Scheduled(cron = "0 0/10 * * * ?")
   public void newDelWhiteList(){
	  logger.info(" 【CarportInfoTask.newDelWhiteList】local data del eparking start...");
   	CarInto.deleteMonthCar();
   	logger.info(" 【CarportInfoTask.newDelWhiteList】local data del eparking end...");
   }
  
  /**
   *每50分钟加载一次登录
   */
 @Scheduled(cron = "0 0/59 * * * ?")
  public void loginststic(){
	 CarInto.loginstatic();
	 
  }

 /**
  *定时更新月租车和临时车
  */
@Scheduled(cron = "0 0/5 * * * ?")
 public void updateEpWhite(){
	logger.info(" 【ParkWhiteListService.updateEpWhite】定时更新开始...");
	parkWhiteListService.updateEpWhite();
	logger.info(" 【ParkWhiteListService.updateEpWhite】定时更新结束...");
 }
/**
 * 超时推送短信给购买者
 * 容联短信推送
 * @throws Exception 
 */
@Scheduled(cron = "0 0/10 * * * ?")
synchronized public void overTimeSendMsg() throws Exception{
	 logger.info(" 【CarportInfoTask.overTimeSendMsg】发送车位超时短信开始...");
	 ParkWhiteListService parkWhiteListService = SpringContextUtil.getBean(ParkWhiteListService.class);
	 try {
		 List<Map<String, String>> selectByState = parkWhiteListService.selectByState();
		 //System.out.println("selectByState的容联短信超时值："+selectByState);
	 	 if(selectByState.size()>0){
	 		for (Map<String, String> map : selectByState) {
				try {
					//车位超时短信发送给购买者
					String phone=map.get("phone");
					String add="【"+map.get("add");
					boolean sendMsgRemind = SendMsgUtils.sendMsgRemind(phone, add, OVERTIME_REMIND,"】" );
					Log.info("需要发送的短信:"+map.toString());
				} catch (Exception e) {
					logger.error(map.get("phone")+"发送车位超时短信失败");
				}
			} 
	 	 }else{
		 logger.info("暂时没有超时的车位信息！！！");
	 	 }
	 } catch (ParseException e) {
		 logger.error("【CarportInfoTask.overTimeSendMsg】"+e.getMessage());
	 }
 logger.info(" 【CarportInfoTask.overTimeSendMsg】 发送车位超时短信结束...");
}	
/**
 * 车位开放提前十五分钟提醒（每个15分钟运行）
 * 极光推送
 * @throws Exception 
 */
@Scheduled(cron = "0 0/15 * * * ?")
synchronized public void advanceSendMsg() throws Exception{
	logger.info(" 【CarportInfoTask.advanceSendMsg】提前发送车位开放提醒信息开始...");
	 ParkWhiteListService parkWhiteListService = SpringContextUtil.getBean(ParkWhiteListService.class);
	 try {
		 List<Map<String, String>> selectByState = parkWhiteListService.selectByAdvance();
		 System.out.println("selectByState的提前值："+selectByState);
	 	 if(selectByState.size()>0&& !selectByState.equals("")){
	 		for (Map<String, String> map : selectByState) {
	 			JPushModel jp = new JPushModel();
	 			jp.setPlatform(0);
	            jp.setAlias(map.get("phone"));
	            jp.setMessage("您在"+map.get("add")+"购买的车位即将开放，请准时进场");
	            JPushClient jpushClient = new JPushClient(JPushClientUtils.masterSecret, JPushClientUtils.appKey, 3);
	            JPushClient jpushClient2 = new JPushClient(JPushClientUtils.masterSecret2, JPushClientUtils.appKey2, 3);
	            JPushClientUtils.pushAlias(jp, jpushClient);
	            JPushClientUtils.pushAlias(jp, jpushClient2);
			} 
	 	 }else{
	 		logger.info("该时段没有提前需要提醒的车位信息！！！");
	 	 }
	 } catch (ParseException e) {
		 e.printStackTrace();
	 }
 logger.info(" 【CarportInfoTask.advanceSendMsg】 提前发送车位开放信息结束...");
}
/**
 * 车位即将到时间提前十五分钟提醒（每个15分钟运行）
 * 极光推送
 * @throws Exception 
 */
@Scheduled(cron = "0 0/15 * * * ?")
synchronized public void arrivalSendMsg() throws Exception{
	logger.info(" 【CarportInfoTask.advanceSendMsg】提前发送车位到时提醒信息开始...");
	ParkWhiteListService parkWhiteListService = SpringContextUtil.getBean(ParkWhiteListService.class);
	try {
		List<Map<String, String>> selectByState = parkWhiteListService.selectByArrival();
		//System.out.println("selectByState的到时值："+selectByState);
		if(selectByState.size()>0){
			for (Map<String, String> map : selectByState) {
				JPushModel jp = new JPushModel();
				jp.setPlatform(0);
				jp.setAlias(map.get("phone"));
				jp.setMessage("您在"+map.get("add")+"购买的车位今天时段即将到时，请准时离场以免产生额外费用。");
				JPushClient jpushClient = new JPushClient(JPushClientUtils.masterSecret, JPushClientUtils.appKey, 3);
				JPushClient jpushClient2 = new JPushClient(JPushClientUtils.masterSecret2, JPushClientUtils.appKey2, 3);
				JPushClientUtils.pushAlias(jp, jpushClient);
				JPushClientUtils.pushAlias(jp, jpushClient2);
			} 
		}else{
			logger.info("该时段没有即将到期需要提醒的车位信息！！！");
		}
	} catch (ParseException e) {
		e.printStackTrace();
	}
	logger.info(" 【CarportInfoTask.arrivalSendMsg】 提前发送车位到时提醒信息结束...");
}
	public static void main(String[] args) {
		try {
			SendMsgUtils.sendMsgRemind("17371319633", "sdfsgfxvbbfdsdf", "364636", "数据没dfafds有问sdgsg题");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 
}
