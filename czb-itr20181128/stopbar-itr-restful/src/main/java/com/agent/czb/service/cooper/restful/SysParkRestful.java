package com.agent.czb.service.cooper.restful;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.agent.czb.common.json.JSONUtils;
import com.agent.czb.common.rest.Errors;
import com.agent.czb.common.rest.RestUtils;
import com.agent.czb.common.rest.restful.BasisRestful;
import com.agent.czb.common.utils.DateUtils;
import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.common.utils.MD5Utils;
import com.agent.czb.common.utils.SendMsgUtils;
import com.agent.czb.core.cooper.enums.CooperEnum;
import com.agent.czb.core.cooper.model.CooperMsg;
import com.agent.czb.core.park.enums.ParkWhiteEnum;
import com.agent.czb.core.park.model.JPushModel;
import com.agent.czb.core.park.model.ParkGateLogModel;
import com.agent.czb.core.park.model.ParkSysInfoModel;
import com.agent.czb.core.park.model.ParkWhiteListModel;
import com.agent.czb.core.park.model.PartSysUserModel;
import com.agent.czb.core.park.service.ParkGateLogService;
import com.agent.czb.core.park.service.ParkOrderInfoService;
import com.agent.czb.core.park.service.ParkSysInfoService;
import com.agent.czb.core.park.service.ParkWhiteListService;
import com.agent.czb.core.park.service.PartSysUserService;
import com.agent.czb.core.sys.model.NewParkWhiteList;
import com.agent.czb.core.sys.model.UserInfoModel;
import com.agent.czb.core.sys.service.CarportInfoService;
import com.agent.czb.core.sys.service.FileUpdateInfoService;
import com.agent.czb.core.sys.service.UserInfoService;
import com.agent.czb.core.sys.service.UserPlateInfoService;
import com.agent.czb.core.sys.service.WalletInfoService;
import com.agent.czb.core.user.service.NewParkWhiteListService;
import com.agent.czb.service.utils.JPushClientUtils;
import com.agent.czb.service.utils.SessionUtils;
import com.agent.czb.service.utils.URLUtils;
import com.eparking.test.CarInto;
import com.eparking.test.PollingThread;
import com.eparking.util.InitUtil;

import cn.jpush.api.JPushClient;

/**
 * 停车场新接口
 * Created by Administrator on 2016/1/6.
 */
@RestController
@RequestMapping("/syspark")
public class SysParkRestful extends BasisRestful {
    private static Logger log = LoggerFactory.getLogger(SysParkRestful.class);
    @Autowired
    private PartSysUserService partSysUserService;
    @Autowired
    private ParkWhiteListService parkWhiteListService;
    @Autowired
    private ParkGateLogService parkGateLogService;
    @Autowired
    private ParkOrderInfoService parkOrderInfoService;
    @Autowired
    private UserPlateInfoService userPlateInfoService;
    @Autowired
    private WalletInfoService walletInfoService;
    @Autowired
    private FileUpdateInfoService fileUpdateInfoService;
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ParkSysInfoService parkSysInfoService;


    @Autowired
    private CarportInfoService carportInfoService;
    @Autowired
    private NewParkWhiteListService newParkWhiteListService;

    public static void main(String[] args) {
        /*Map<String, String> map = new HashMap<>();
        map.put("username", "admin");
        map.put("password", "123456");
        System.out.println(JSONUtils.mapToJsonString(map));*/
    	JPushModel model = new JPushModel();
    	/*jp.setPlatform(0);
        jp.setAlias("13488160731");
        jp.setMessage("您的车牌号:" + 355 + "的车已经成功入场");
        JPushClient jpushClient = new JPushClient(JPushClientUtils.masterSecret, JPushClientUtils.appKey, 3);
        JPushClient jpushClient2 = new JPushClient(JPushClientUtils.masterSecret2, JPushClientUtils.appKey2, 3);
        JPushClientUtils.pushAlias(jp, jpushClient);
        JPushClientUtils.pushAlias(jp, jpushClient2);*/
    }

    /**
     * 接口入口
     *
     * @param request
     * @param data
     * @return
     */
    @RequestMapping(value = "/service", method = RequestMethod.POST)
    public Object service(HttpServletRequest request, String data) {
        log.info("request data : " + data);
        return CooperMsg.newLoginSuccess("请求成功");
    }

    /**
     * 1. 登陆接口
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(HttpServletRequest request/*, @RequestBody Map<String, String> json*/) {
        Map<String, String> json = RestUtils.request2map2(request);
        String username = json.get("username");
        String password = json.get("password");
        log.info("sys prak -> login : {}", json);
        // 判断密码是否错误
        if (StringUtils.isEmpty(username)) {

            return CooperMsg.newFailed("用户名不能为空");
        }
        if (StringUtils.isEmpty(password)) {
            return CooperMsg.newFailed("密码不能为空");
        }
        List<PartSysUserModel> list = partSysUserService.pageList(FrameUtils.newMap("loginName", username));
        if (list.isEmpty()) {
            return CooperMsg.newFailed("用户不存在");
        }
        PartSysUserModel partSysUserModel = list.get(0);
        String pwd = MD5Utils.MD5(password);
        if (!StringUtils.equals(partSysUserModel.getLoginPwd(), pwd)) {
            return CooperMsg.newFailed("用户密码不对");
        }
        // 保持session
        SessionUtils.setPartSysUser(request, partSysUserModel);
        // 去掉密码
        partSysUserModel.setLoginPwd(null);
        return CooperMsg.newSuccess(partSysUserModel);
    }

    /**
     * 2. 获取车辆入场信息的接口
     * ioState  1：进；2：出；3:重复进；4：重复出；
     */
    @RequestMapping(value = "/into", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object into(HttpServletRequest request/*, @RequestBody Map<String, String> josn*/) throws UnsupportedEncodingException {
        Map<String, String> json = RestUtils.request2map2(request);
        log.info("sys prak -> into : {}", json);
        String parkCode = json.get("parkCode");
        String plateNum = json.get("plateNum");
        String ioDate = json.get("ioDate");
        Integer remainSpace = Integer.valueOf(json.get("remainSpace")); // 剩余车位
        String carType = json.get("carType");

        Errors.check(StringUtils.isNotEmpty(parkCode), "停车编号不能为空");
        if (StringUtils.isEmpty(plateNum)) {
            return CooperMsg.newFailed("车牌号码不能为空");
        }
        Errors.check(StringUtils.isNotEmpty(plateNum), "车牌号码不能为空");
        if (StringUtils.isEmpty(ioDate)) {
            return CooperMsg.newFailed("时间不能为空");
        }
        PartSysUserModel partSysUser = SessionUtils.getPartSysUser(request);
        if (partSysUser == null) {
            return CooperMsg.newFailed("用户未登录");
        }
        // 记录入库
        ParkGateLogModel model = new ParkGateLogModel();
        model.setParkCode(partSysUser.getParkCode());
        model.setGateCode(null);
        model.setCarNo(parkCode);
        model.setPlateNum(plateNum);
        model.setUserId(userInfoService.selectUserIdByPlateNum(plateNum));
        model.setIoState(CooperEnum.IoState.INPUT.toValue());
        model.setIoDate(DateUtils.parse(ioDate, DateUtils.F_YMDHMS_, DateUtils.F_YMD_));
        model.setInDate(DateUtils.parse(ioDate, DateUtils.F_YMDHMS_, DateUtils.F_YMD_));
        model.setCarType(carType);
        model.setRemainSpace(remainSpace);
        model.setCreateTime(new Date());
        parkGateLogService.save(model);
        //修改系统停车场剩余车位信息
        List<ParkSysInfoModel> list = parkSysInfoService.pageList(FrameUtils.newMap("parkCode", parkCode));
        if (list.size() > 0) {
            ParkSysInfoModel parkSysInfoModel = list.get(0);
            parkSysInfoModel.setRemainSpace(remainSpace);
            parkSysInfoService.updateBySelective(parkSysInfoModel);
        }
        log.info("uerid__________________________________" + model.getUserId());
        if (model.getUserId() != null) {
            UserInfoModel user = userInfoService.selectById(model.getUserId());
            JPushModel jp = new JPushModel();
            jp.setPlatform(0);
            jp.setAlias(user.getPhone());
            jp.setMessage("您的车牌号:" + model.getPlateNum() + "的车已经成功入场");
            JPushClient jpushClient = new JPushClient(JPushClientUtils.masterSecret, JPushClientUtils.appKey, 3);
            JPushClient jpushClient2 = new JPushClient(JPushClientUtils.masterSecret2, JPushClientUtils.appKey2, 3);
            JPushClientUtils.pushAlias(jp, jpushClient);
            JPushClientUtils.pushAlias(jp, jpushClient2);
        }
        return CooperMsg.newSuccess(model.getId());

    }

    
    /**
     * 2.1 获取车辆入场信息的新接口
     * ioState  1：进；2：出；3:重复进；4：重复出；
     */
    @RequestMapping(value = "/new/into", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object newInto(HttpServletRequest request/*, @RequestBody Map<String, String> josn*/) throws UnsupportedEncodingException {
        Map<String, String> json = RestUtils.request2map2(request);
        log.info("sys prak -> into : {}", json);
        String parkCode = json.get("parkCode");
        String plateNum = json.get("plateNum");
        String ioDate = json.get("ioDate");
        Integer remainSpace = Integer.valueOf(json.get("remainSpace")); // 剩余车位
        String carType = json.get("carType");
        String orderId = json.get("orderId");
        Errors.check(StringUtils.isNotEmpty(parkCode), "停车编号不能为空");
        if (StringUtils.isEmpty(plateNum)) {
            return CooperMsg.newFailed("车牌号码不能为空");
        }
        Errors.check(StringUtils.isNotEmpty(plateNum), "车牌号码不能为空");
        if (StringUtils.isEmpty(ioDate)) {
            return CooperMsg.newFailed("时间不能为空");
        }
        PartSysUserModel partSysUser = SessionUtils.getPartSysUser(request);
        if (partSysUser == null) {
            return CooperMsg.newFailed("用户未登录");
        }
        // 记录入库
        ParkGateLogModel model = new ParkGateLogModel();
        model.setParkCode(parkCode);
        //model.setParkCode(partSysUser.getParkCode());
        model.setGateCode(null);
        model.setCarNo(parkCode);
        model.setPlateNum(plateNum);
        model.setUserId(userInfoService.selectUserIdByPlateNum(plateNum));
        model.setIoState(CooperEnum.IoState.INPUT.toValue());
        model.setIoDate(DateUtils.parse(ioDate, DateUtils.F_YMDHMS_, DateUtils.F_YMD_));
        model.setInDate(DateUtils.parse(ioDate, DateUtils.F_YMDHMS_, DateUtils.F_YMD_));
        model.setCarType(carType);
        model.setOrderId(Long.valueOf(orderId));
        model.setRemainSpace(remainSpace);
        model.setCreateTime(new Date());
        parkGateLogService.save(model);
        //修改系统停车场剩余车位信息
        List<ParkSysInfoModel> list = parkSysInfoService.pageList(FrameUtils.newMap("parkCode", parkCode));
        if (list.size() > 0) {
            ParkSysInfoModel parkSysInfoModel = list.get(0);
            parkSysInfoModel.setRemainSpace(remainSpace);
            parkSysInfoService.updateBySelective(parkSysInfoModel);
        }
        log.info("uerid__________________________________" + model.getUserId());
        if (model.getUserId() != null) {
            UserInfoModel user = userInfoService.selectById(model.getUserId());
            JPushModel jp = new JPushModel();
            jp.setPlatform(0);
            jp.setAlias(user.getPhone());
            jp.setMessage("您的车牌号:" + model.getPlateNum() + "的车已经成功入场");
            JPushClient jpushClient = new JPushClient(JPushClientUtils.masterSecret, JPushClientUtils.appKey, 3);
            JPushClient jpushClient2 = new JPushClient(JPushClientUtils.masterSecret2, JPushClientUtils.appKey2, 3);
            JPushClientUtils.pushAlias(jp, jpushClient);
            JPushClientUtils.pushAlias(jp, jpushClient2);
        }
        return CooperMsg.newSuccess(model.getId());

    }
    
    
    /**
     * 3. 查询车辆信息的接口
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object query(HttpServletRequest request, @RequestBody Map<String, String> json) {
        log.info("sys park -> query : {}", json);
        return CooperMsg.newSuccess(null);
    }

    /**
     * 4. 获取车辆出场信息的接口
     * ioState  1：进；2：出；3:重复进；4：重复出；5：确认出；
     */
    @RequestMapping(value = "/out", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object out(HttpServletRequest request/*, @RequestBody Map<String, String> json*/) throws UnsupportedEncodingException {
        Map<String, String> json = RestUtils.request2map2(request);
        log.info("sys park -> out : {}", json);
        String parkCode = json.get("parkCode");
        String plateNum = json.get("plateNum");
        String carType = json.get("carType");
        String payType = json.get("payType");
        String ioDate = json.get("ioDate");
        String reserveOrderid = json.get("reserveOrderid");//
        Integer remainSpace = Integer.valueOf(json.get("remainSpace")); // 剩余车位
        Long price = FrameUtils.yuan2fen(json.get("price"));
        Long orderId = StringUtils.isNotEmpty(json.get("orderId")) ? Long.valueOf(json.get("orderId")) : null;
        // 计费记录
        String detailed = json.get("detailed");

        if (StringUtils.isEmpty(parkCode)) {
            return CooperMsg.newFailed("停车编号不能为空");
        }
        if (StringUtils.isEmpty(plateNum)) {
            return CooperMsg.newFailed("车牌号码不能为空");
        }
        if (StringUtils.isEmpty(ioDate)) {
            return CooperMsg.newFailed("时间不能为空");
        }
        PartSysUserModel partSysUser = SessionUtils.getPartSysUser(request);
        if (partSysUser == null) {
            return CooperMsg.newFailed("用户未登录");
        }
        /*if (!partSysUser.getParkCode().equals(parkCode)) {
            return CooperMsg.newFailed("停车编号与用户不匹配");
        }
        if (!partSysUser.getParkCode().equals(parkCode)) {
            return CooperMsg.newFailed("停车编号与用户不匹配");
        }*/
        ParkGateLogModel parkGateLogModel = parkGateLogService.selectById(orderId);
        log.info("reserveOrderid__________________________________" + reserveOrderid);
        if ((!StringUtils.isEmpty(reserveOrderid)) && reserveOrderid.length() < 12) {
            //按时租车位 自动上线
            Integer code = parkOrderInfoService.queryByCarportOrderId(parkGateLogModel.getParkCode(), parkGateLogModel.getPlateNum(), Long.parseLong(reserveOrderid));
            if (code == 0) {
                return CooperMsg.newFailed("按时出租车位自动上线失败");
            }
        }
        if (parkGateLogModel == null) {
            // 记录入库
            parkGateLogModel = new ParkGateLogModel();
            parkGateLogModel.setParkCode(parkCode);
            parkGateLogModel.setGateCode(null);
            parkGateLogModel.setCarNo(parkCode);
            parkGateLogModel.setPlateNum(plateNum);
            parkGateLogModel.setUserId(userInfoService.selectUserIdByPlateNum(plateNum));
            parkGateLogModel.setCreateTime(new Date());
        }
        parkGateLogModel.setCarType(carType);
        parkGateLogModel.setPayType(payType);
        parkGateLogModel.setIoState(CooperEnum.IoState.OUTPUT.toValue());
        parkGateLogModel.setIoDate(DateUtils.parse(ioDate, DateUtils.F_YMDHMS_, DateUtils.F_YMD_));
        parkGateLogModel.setOutDate(DateUtils.parse(ioDate, DateUtils.F_YMDHMS_, DateUtils.F_YMD_));
        parkGateLogModel.setRemainSpace(remainSpace);
        parkGateLogModel.setPrice(price);
        parkGateLogModel.setDetailed(detailed);


        parkGateLogService.save(parkGateLogModel);


        //修改系统停车场剩余车位信息
        List<ParkSysInfoModel> list = parkSysInfoService.pageList(FrameUtils.newMap("parkCode", parkCode));
        if (list.size() > 0) {
            ParkSysInfoModel parkSysInfoModel = list.get(0);
            parkSysInfoModel.setRemainSpace(remainSpace);
            parkSysInfoService.updateBySelective(parkSysInfoModel);
        }
        log.info(parkGateLogModel.getUserId() + "__________________________userId");
        if (parkGateLogModel.getUserId() != null) {
            UserInfoModel user = userInfoService.selectById(parkGateLogModel.getUserId());
            JPushModel model = new JPushModel();
            model.setPlatform(0);
            model.setAlias(user.getPhone());
            log.info("您的车牌号:" + parkGateLogModel.getPlateNum() + "的车已经成功出场");
            model.setMessage("您的车牌号:" + parkGateLogModel.getPlateNum() + "的车已经成功出场");
            JPushClient jpushClient = new JPushClient(JPushClientUtils.masterSecret, JPushClientUtils.appKey, 3);
            JPushClient jpushClient2 = new JPushClient(JPushClientUtils.masterSecret2, JPushClientUtils.appKey2, 3);
            JPushClientUtils.pushAlias(model, jpushClient);
            JPushClientUtils.pushAlias(model, jpushClient2);
        }


        return CooperMsg.newSuccess("出场成功");
    }

    /**
     * 4.1 获取车辆出场信息的接口  新接口
     * ioState  1：进；2：出；3:重复进；4：重复出；5：确认出；
     */
    @RequestMapping(value = "/new/out", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object newOut(HttpServletRequest request/*, @RequestBody Map<String, String> json*/) throws UnsupportedEncodingException {
        Map<String, String> json = RestUtils.request2map2(request);
        log.info("sys park -> out : {}", json);
        String parkCode = json.get("parkCode");
        String plateNum = json.get("plateNum");
        String carType = json.get("carType");
        String payType = json.get("payType");
        String ioDate = json.get("ioDate");
        String reserveOrderid = json.get("reserveOrderid");//
        Integer remainSpace = Integer.valueOf(json.get("remainSpace")); // 剩余车位
        Long price = FrameUtils.yuan2fen(json.get("price"));
        Long orderId = StringUtils.isNotEmpty(json.get("orderId")) ? Long.valueOf(json.get("orderId")) : null;
        // 计费记录
        String detailed = json.get("detailed");

        if (StringUtils.isEmpty(parkCode)) {
            return CooperMsg.newFailed("停车编号不能为空");
        }
        if (StringUtils.isEmpty(plateNum)) {
            return CooperMsg.newFailed("车牌号码不能为空");
        }
        if (StringUtils.isEmpty(ioDate)) {
            return CooperMsg.newFailed("时间不能为空");
        }
        PartSysUserModel partSysUser = SessionUtils.getPartSysUser(request);
        if (partSysUser == null) {
            return CooperMsg.newFailed("用户未登录");
        }
        
        /*if (!partSysUser.getParkCode().equals(parkCode)) {
            return CooperMsg.newFailed("停车编号与用户不匹配");
        }*/
        ParkGateLogModel parkGateLogModel = parkGateLogService.selectByOrderId(orderId);
        log.info("reserveOrderid__________________________________" + reserveOrderid);
        if ((!StringUtils.isEmpty(reserveOrderid)) && reserveOrderid.length() < 12) {
            //按时租车位 自动上线
            Integer code = parkOrderInfoService.queryByCarportOrderId(parkGateLogModel.getParkCode(), parkGateLogModel.getPlateNum(), Long.parseLong(reserveOrderid));
            if (code == 0) {
                return CooperMsg.newFailed("按时出租车位自动上线失败");
            }
        }
        if (parkGateLogModel == null) {
            // 记录入库
            parkGateLogModel = new ParkGateLogModel();
            parkGateLogModel.setParkCode(parkCode);
            parkGateLogModel.setGateCode(null);
            parkGateLogModel.setCarNo(parkCode);
            parkGateLogModel.setPlateNum(plateNum);
            parkGateLogModel.setUserId(userInfoService.selectUserIdByPlateNum(plateNum));
            parkGateLogModel.setCreateTime(new Date());
        }
        parkGateLogModel.setCarType(carType);
        parkGateLogModel.setPayType(payType);
        parkGateLogModel.setIoState(CooperEnum.IoState.OUTPUT.toValue());
        parkGateLogModel.setIoDate(DateUtils.parse(ioDate, DateUtils.F_YMDHMS_, DateUtils.F_YMD_));
        parkGateLogModel.setOutDate(DateUtils.parse(ioDate, DateUtils.F_YMDHMS_, DateUtils.F_YMD_));
        parkGateLogModel.setRemainSpace(remainSpace);
        parkGateLogModel.setPrice(price);
        parkGateLogModel.setDetailed(detailed);


        parkGateLogService.save(parkGateLogModel);


        //修改系统停车场剩余车位信息
        List<ParkSysInfoModel> list = parkSysInfoService.pageList(FrameUtils.newMap("parkCode", parkCode));
        if (list.size() > 0) {
            ParkSysInfoModel parkSysInfoModel = list.get(0);
            parkSysInfoModel.setRemainSpace(remainSpace);
            parkSysInfoService.updateBySelective(parkSysInfoModel);
        }
        log.info(parkGateLogModel.getUserId() + "__________________________userId");
        if (parkGateLogModel.getUserId() != null) {
            UserInfoModel user = userInfoService.selectById(parkGateLogModel.getUserId());
            JPushModel model = new JPushModel();
            model.setPlatform(0);
            model.setAlias(user.getPhone());
            log.info("您的车牌号:" + parkGateLogModel.getPlateNum() + "的车已经成功出场");
            model.setMessage("您的车牌号:" + parkGateLogModel.getPlateNum() + "的车已经成功出场");
            JPushClient jpushClient = new JPushClient(JPushClientUtils.masterSecret, JPushClientUtils.appKey, 3);
            JPushClient jpushClient2 = new JPushClient(JPushClientUtils.masterSecret2, JPushClientUtils.appKey2, 3);
            JPushClientUtils.pushAlias(model, jpushClient);
            JPushClientUtils.pushAlias(model, jpushClient2);
        }


        return CooperMsg.newSuccess("出场成功");
    }
    
    
    
    /**
     * 4.2确认车辆已经出场接口
     * ioState  1：进；2：出；3:重复进；4：重复出；5：确认出；
     */
    @RequestMapping(value = "/suerOut", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object suerOut(HttpServletRequest request/*, @RequestBody Map<String, String> json*/) throws UnsupportedEncodingException {
        Map<String, String> json = RestUtils.request2map2(request);

        Long orderId = StringUtils.isNotEmpty(json.get("orderId")) ? Long.valueOf(json.get("orderId")) : null;

        PartSysUserModel partSysUser = SessionUtils.getPartSysUser(request);
        if (partSysUser == null) {
            return CooperMsg.newFailed("用户未登录");
        }

        ParkGateLogModel parkGateLogModel = parkGateLogService.selectById(orderId);
        if (parkGateLogModel == null) {
            return CooperMsg.newFailed("该订单不存在");
        }
        parkGateLogModel.setIoState(CooperEnum.IoState.SURE_OUT.toValue());

        return CooperMsg.newSuccess("确认出场成功");
    }

    /**
     * 5. 添加白名单
     */
    @RequestMapping(value = "/addWhiteList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object addWhiteList(HttpServletRequest request/*, @RequestBody Map<String, String> json*/) {
        Map<String, String> json = RestUtils.request2map2(request);
        log.info("sys park -> addWhiteList : {}", json);
        String parkCode = json.get("parkCode");
        String plateNum = json.get("plateNum");
        String startTime = json.get("startTime");
        String endTime = json.get("endTime");
        String addr = json.get("addr");
        String code = json.get("code");
        String phone = json.get("phone");
        if (StringUtils.isEmpty(plateNum)) {
            return CooperMsg.newFailed("车牌号不能为空");
        }

        Long userId = null;
        UserInfoModel userInfo = parkWhiteListService.checkWhiteListUser(phone, plateNum);
        if (userInfo != null) {
            userId = userInfo.getId();
        }
        PartSysUserModel partSysUser = SessionUtils.getPartSysUser(request);
        if (partSysUser == null) {
            return CooperMsg.newFailed("用户未登录");
        }
        // 从登陆用户中获取停车场编码
        parkCode = partSysUser.getParkCode();
        Map<String, String> params = FrameUtils.newMap(
            "parkCode", parkCode,
            "plateNum", plateNum,
            "code", code,
            "state", ParkWhiteEnum.State.车位.toValue()
        );
        long count = parkWhiteListService.pageCount(params);
        if (count > 0) {
            return CooperMsg.newFailed("车位已存在");
        } else {
            createWhiteList(userId, phone, addr, code, parkCode, plateNum, startTime, endTime);
        }
        //发送车位绑定成功短信
        try {
            SendMsgUtils.sendMsg(phone, null, SendMsgUtils.MSG_TMEPLATE_NOTICE, plateNum);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return CooperMsg.newSuccess("添加白名单成功");
    }

    /**
     * 6. 修改白名单
     */
    @RequestMapping(value = "/editWhiteList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object editWhiteList(HttpServletRequest request/*, @RequestBody Map<String, String> json*/) {
        Map<String, String> json = RestUtils.request2map2(request);
        log.info("sys park -> editWhiteList : {}", json);
        String parkCode = json.get("parkCode");
        String plateNum = json.get("plateNum");
        String oldPlateNum = json.get("oldPlateNum");
        String startTime = json.get("startTime");
        String endtime = json.get("endtime");
        String phone = json.get("phone");

        Map<String, String> params = FrameUtils.newMap("parkCode", parkCode, "plateNum", oldPlateNum, "state", ParkWhiteEnum.State.车位.toValue());
        // 是否能找到白名单
        List<ParkWhiteListModel> whiteList = parkWhiteListService.pageList(params);
        if (whiteList.size() > 0) {
            ParkWhiteListModel parkWhiteListModel = whiteList.get(0);
            if (parkWhiteListModel.getUserId() == null) {
                if (StringUtils.isNotEmpty(phone)) {
                    parkWhiteListModel.setPhone(phone);
                }
                UserInfoModel userInfo = parkWhiteListService.checkWhiteListUser(phone, plateNum);
                parkWhiteListModel.setUserId(userInfo.getId());
            }
            parkWhiteListModel.setStartTime(DateUtils.parse(startTime, DateUtils.F_YMDHMS_, DateUtils.F_YMD_));
            parkWhiteListModel.setStartTime(DateUtils.parse(endtime, DateUtils.F_YMDHMS_, DateUtils.F_YMD_));
            parkWhiteListModel.setUpdateTime(new Date());
            parkWhiteListService.updateBySelective(parkWhiteListModel);
        } else {
            return CooperMsg.newFailed("白名单不存在");
        }
        return CooperMsg.newSuccess("修改白名单成功");
    }

    private void createWhiteList(Long userId, String phone, String addr, String code, String parkCode, String plateNum, String startTime, String endTime) {
        ParkWhiteListModel parkWhiteListModel = new ParkWhiteListModel();
        parkWhiteListModel.setPhone(phone);
        parkWhiteListModel.setUserId(userId);
        parkWhiteListModel.setState(ParkWhiteEnum.State.车位.toValue());
        parkWhiteListModel.setCode(code);
        parkWhiteListModel.setAddr(addr);
        parkWhiteListModel.setParkCode(parkCode);
        parkWhiteListModel.setPlateNum(plateNum);
        parkWhiteListModel.setStartTime(DateUtils.parse(startTime, DateUtils.F_YMDHMS_, DateUtils.F_YMD_));
        parkWhiteListModel.setEndTime(DateUtils.parse(endTime, DateUtils.F_YMDHMS_, DateUtils.F_YMD_));
        parkWhiteListModel.setCreateTime(new Date());
        parkWhiteListModel.setUpdateTime(new Date());
        parkWhiteListService.insert(parkWhiteListModel);
    }

    /**
     * 7. 删除白名单
     */
    @RequestMapping(value = "/delWhiteList", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object delWhiteList(HttpServletRequest request/*, @RequestBody Map<String, String> json*/) {
//        log.info("sys park -> delWhiteList : {}", json);
//        String parkCode = json.get("parkCode");
//        String plateNum = json.get("plateNum");

    	String parkCode = request.getParameter("parkCode");
    	String plateNum = request.getParameter("plateNum");
        Map<String, String> params = FrameUtils.newMap("parkCode", parkCode, "plateNum", plateNum);
        // 获取白名单
        List<ParkWhiteListModel> whiteList = parkWhiteListService.pageList(params);
        if (whiteList.isEmpty()) {
            return CooperMsg.newFailed("未找到白名单");
        }
        for (ParkWhiteListModel parkWhiteListModel : whiteList) {
            parkWhiteListService.delete(parkWhiteListModel.getId());
        }
        return CooperMsg.newSuccess("删除白名单成功");
    }

    /**
     * 上传图片地址
     */
    @RequestMapping(value = "/updateFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object updateFile(MultipartFile file, String filename, String filepath, String act) {
        if (file == null || file.isEmpty()) {
            return CooperMsg.newFailed("文件不能为空");
        }
        try {
            File toFile = new File(URLUtils.get("sys.park.img.path"), filename);
            if (!toFile.getParentFile().exists()) {
                toFile.getParentFile().mkdirs();
            }
            file.transferTo(toFile);
        } catch (IOException e) {
            return CooperMsg.newFailed("文件传输失败");
        }
        return CooperMsg.newSuccess(null);
    }
   
    @RequestMapping(value = "/eparking", method = RequestMethod.GET)
    public void eparking(){
    	PollingThread pollingThread=new PollingThread();
        pollingThread.start();
        System.setProperty("jna.encoding", "GBK");
        InitUtil initUtil = new InitUtil();
        String initLoad = initUtil.initLoad();
		String login = initUtil.login();
		CarInto carInto = new CarInto();
		CarInto carInto1 = new CarInto();
		CarInto carInto2 = new CarInto();
		
        while(true)
        {
            PollingThread.queue.offer(carInto);
            PollingThread.queue1.offer(carInto1);
            PollingThread.queue2.offer(carInto2);
            
            synchronized (Lock.class)
            {
                Lock.class.notify();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           
        }
   }
    @RequestMapping(value = "/newDelWhiteList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NewParkWhiteList> newDelWhiteList(){
    	return newParkWhiteListService.listNewParkWhiteList();
    }
}
