/*
package com.agent.czb.service.cooper.restful;

import com.agent.czb.common.json.JSONUtils;
import com.agent.czb.common.rest.restful.BasisRestful;
import com.agent.czb.common.utils.DateUtils;
import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.common.utils.MD5Utils;
import com.agent.czb.core.cooper.enums.CooperEnum;
import com.agent.czb.core.cooper.model.CooperMsg;
import com.agent.czb.core.cooper.model.CooperReturn;
import com.agent.czb.core.cooper.model.Prices;
import com.agent.czb.core.park.enums.ParkOrderEnum;
import com.agent.czb.core.park.enums.ParkWhiteEnum;
import com.agent.czb.core.park.model.ParkGateLogModel;
import com.agent.czb.core.park.model.ParkOrderInfoModel;
import com.agent.czb.core.park.model.ParkWhiteListModel;
import com.agent.czb.core.park.model.PartSysUserModel;
import com.agent.czb.core.park.service.ParkGateLogService;
import com.agent.czb.core.park.service.ParkOrderInfoService;
import com.agent.czb.core.park.service.ParkWhiteListService;
import com.agent.czb.core.park.service.PartSysUserService;
import com.agent.czb.core.sys.enums.WalletInfoEnums;
import com.agent.czb.core.sys.model.FileUpdateInfoModel;
import com.agent.czb.core.sys.model.UserPlateInfoModel;
import com.agent.czb.core.sys.model.WalletInfoModel;
import com.agent.czb.core.sys.service.FileUpdateInfoService;
import com.agent.czb.core.sys.service.UserPlateInfoService;
import com.agent.czb.core.sys.service.WalletInfoService;
import com.agent.czb.service.utils.SessionUtils;
import com.agent.czb.service.utils.URLUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

*/
/**
 * Created by Administrator on 2016/1/6.
 *//*

@RestController
@RequestMapping("cooper")
public class CooperRestful extends BasisRestful {
    private static Logger log = LoggerFactory.getLogger(CooperRestful.class);

    @Autowired
    private PartSysUserService<PartSysUserModel> partSysUserService;

    @Autowired
    private ParkWhiteListService<ParkWhiteListModel> parkWhiteListService;

    @Autowired
    private ParkGateLogService<ParkGateLogModel> parkGateLogService;

    @Autowired
    private ParkOrderInfoService<ParkOrderInfoModel> parkOrderInfoService;

    @Autowired
    private UserPlateInfoService<UserPlateInfoModel> userPlateInfoService;

    @Autowired
    private WalletInfoService<WalletInfoModel> walletInfoService;

    @Autowired
    private FileUpdateInfoService<FileUpdateInfoModel> fileUpdateInfoService;

    public static boolean isWhiteList = true;

    */
/**
     * 1. 登陆接口
     *//*

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object login(HttpServletRequest request, String username, String password) {
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
        return CooperMsg.newLoginSuccess("登录成功");
    }

    */
/**
     * 2. 计算停车场所有正在停车的订单的价格的接口
     *//*

    @RequestMapping(value = "/countPrice", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object countPrice(HttpServletRequest request) {
        PartSysUserModel partSysUser = SessionUtils.getPartSysUser(request);
        if (partSysUser == null) {
            return CooperMsg.newFailed("用户未登录");
        }
        Map<String, String> params = FrameUtils.newMap();
        params.put("parkCode", partSysUser.getParkCode());
        // 类型：计算价格
        params.put("type", ParkOrderEnum.Type.CALCULATE_PRICE.toValue());
        params.put("state", ParkOrderEnum.State.CALCULATE_PRICE.toValue());
        List<ParkOrderInfoModel> orderInfos = parkOrderInfoService.pageList(params);

        List<CooperReturn> list = new ArrayList<>();
        for (ParkOrderInfoModel orderInfo : orderInfos) {
            CooperReturn cooperReturn = new CooperReturn();
            cooperReturn.setId(orderInfo.getId().toString());
            cooperReturn.setCarcode(orderInfo.getPlateNum());
            cooperReturn.setStarttime(orderInfo.getCreateTime());
            cooperReturn.setEndtime(new Date());
            list.add(cooperReturn);
        }
        return CooperMsg.newSuccess(list);
    }

    */
/**
     * 3. 计算出订单价格后，将价格返回给服务器的接口
     *//*

    @RequestMapping(value = "/getResult", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getResult(HttpServletRequest request, String prices) {
        if (StringUtils.isEmpty(prices)) {
            return CooperMsg.newFailed("参数为空");
        }
        Prices prices1 = JSONUtils.jsonToObject(prices, Prices.class);
        log.info("calculate price return! {}", prices1.toString());
        // 获取订单
        ParkOrderInfoModel parkOrderInfo = parkOrderInfoService.selectById(prices1.getId());
        if (parkOrderInfo == null) {
            return CooperMsg.newFailed("未找到该订单");
        }
        // 修改订单信息
        parkOrderInfo.setTotalPrice(FrameUtils.yuan2fen(prices1.getTotalPrice()));
        parkOrderInfo.setState(ParkOrderEnum.State.CALCULATE_COMPLETE.toValue());
        parkOrderInfo.setUpdateTime(new Date());
        parkOrderInfoService.update(parkOrderInfo);
        return CooperMsg.newSuccess(null);
    }

    */
/**
     * 4. 获取车辆出入库信息的接口
     * ioState  1：进；2：出；3:重复进；4：重复出；
     *//*

    @RequestMapping(value = "/iodata", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object iodata(HttpServletRequest request, String carNo, String carCode, String ioState, String ioDate,
                         String picture, String isVip) throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(carNo)) {
            return CooperMsg.newFailed("停车编号不能为空");
        }
        carCode = URLDecoder.decode(carCode, "GBK");
        if (StringUtils.isEmpty(carCode)) {
            return CooperMsg.newFailed("车牌号码不能为空");
        }
        if (StringUtils.isEmpty(ioState)) {
            return CooperMsg.newFailed("进出库类型不能为空");
        }
        CooperEnum.IoState ioState1 = CooperEnum.IoState.get(ioState);
        if (ioState1 == null) {
            return CooperMsg.newFailed("进出库类型错误");
        }
        ioDate = URLDecoder.decode(ioDate, "GBK");
        if (StringUtils.isEmpty(ioDate)) {
            return CooperMsg.newFailed("时间不能为空");
        }
        if (StringUtils.isEmpty(picture)) {
            return CooperMsg.newFailed("出入库图片的文件名不能为空");
        }
        PartSysUserModel partSysUser = SessionUtils.getPartSysUser(request);
        if (partSysUser == null) {
            return CooperMsg.newFailed("用户未登录");
        }
        // 记录入库
        ParkGateLogModel model = new ParkGateLogModel();
        model.setParkCode(partSysUser.getParkCode());
        model.setGateCode(null);
        model.setCarNo(carNo);
        model.setPlateNum(carCode);
        model.setIoState(ioState);
        model.setIoDate(DateUtils.parse(ioDate, DateUtils.F_YMDHMS_));
        model.setPicture(picture);
        model.setIsVip(isVip);
        model.setCreateTime(new Date());
        parkGateLogService.insert(model);
        return CooperMsg.newSuccess(null);
    }

    */
/**
     * 5. 获取已支付但未出库的车辆订单信息的方法
     *//*

    @RequestMapping(value = "/payInfo", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object payInfo(HttpServletRequest request) {
        PartSysUserModel partSysUser = SessionUtils.getPartSysUser(request);
        if (partSysUser == null) {
            return CooperMsg.newFailed("用户未登录");
        }
        Map<String, String> params = FrameUtils.newMap();
        params.put("parkCode", partSysUser.getParkCode());
        // 类型：APP支付
        params.put("type", ParkOrderEnum.Type.APP_PAY.toValue());
        params.put("state", ParkOrderEnum.State.BEE_PAY.toValue());
        List<ParkOrderInfoModel> orderInfos = parkOrderInfoService.pageList(params);

        List<CooperReturn> list = new ArrayList<>();
        for (ParkOrderInfoModel orderInfo : orderInfos) {
            CooperReturn cooperReturn = new CooperReturn();
            cooperReturn.setId(orderInfo.getId().toString());
            cooperReturn.setCarcode(orderInfo.getPlateNum());
            cooperReturn.setTime(orderInfo.getCreateTime());
            list.add(cooperReturn);
            // 更新订单状态
            orderInfo.setState(ParkOrderEnum.State.PAY_COMPLETE.toValue());
            orderInfo.setUpdateTime(new Date());
            parkOrderInfoService.update(orderInfo);
        }
        return CooperMsg.newSuccess(list);
    }

    */
/**
     * 6. 将新的订单信息传递到服务器的接口
     * orderDate：yyyy-MM-dd HH:mm:ss
     * totalPrice：缴费金额（首段收费金额）
     *//*

    @RequestMapping(value = "/newOrder", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object newOrder(HttpServletRequest request, String carCode, String totalPrice, String orderDate) throws UnsupportedEncodingException {
        PartSysUserModel partSysUser = SessionUtils.getPartSysUser(request);
        if (partSysUser == null) {
            return CooperMsg.newFailed("用户未登录");
        }

        carCode = URLDecoder.decode(carCode, "GBK");
        orderDate = URLDecoder.decode(orderDate, "GBK");

        if (StringUtils.isEmpty(carCode)) {
            return CooperMsg.newFailed("车牌号码不能为空");
        }
        if (StringUtils.isEmpty(totalPrice)) {
            return CooperMsg.newFailed("缴费金额不能为空");
        }
        if (StringUtils.isEmpty(orderDate)) {
            return CooperMsg.newFailed("订单时间不能为空");
        }
        Long price = FrameUtils.yuan2fen(totalPrice);
        log.info("newOrder : carCode={}, totalPrice={}, orderDate={}", carCode, price, orderDate);
        return CooperMsg.newSuccess(null);
    }

    */
/**
     * 7. 获取在中央缴费区缴费的车辆信息 ------------ 目前不做
     * orderDate：yyyy-MM-dd HH:mm:ss
     * totalPrice：缴费金额（首段收费金额）
     *//*

    @RequestMapping(value = "/payed/order", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object payedOrder(String carCode, String finishDate, String totalPrice, String type, String name) throws UnsupportedEncodingException {
        carCode = URLDecoder.decode(carCode, "GBK");
        finishDate = URLDecoder.decode(finishDate, "GBK");
        name = URLDecoder.decode(name, "GBK");

        if (StringUtils.isEmpty(carCode)) {
            return CooperMsg.newFailed("车牌号码不能为空");
        }
        if (StringUtils.isEmpty(finishDate)) {
            return CooperMsg.newFailed("缴费时间不能为空");
        }
        if (StringUtils.isEmpty(totalPrice)) {
            return CooperMsg.newFailed("缴费金额不能为空");
        }
        if (StringUtils.isEmpty(type)) {
            return CooperMsg.newFailed("类型不能为空");
        }
        Long price = FrameUtils.yuan2fen(totalPrice);

        return CooperMsg.newSuccess(null);
    }

    */
/**
     * 8. 自动扣费接口
     *//*

    @RequestMapping(value = "/settlement", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object settlement(HttpServletRequest request, String carcode, String carCode, String totalPrice) throws UnsupportedEncodingException {
        if (carcode == null) {
            carcode = carCode;
        }
        carcode = URLDecoder.decode(carcode, "GBK");

        PartSysUserModel partSysUser = SessionUtils.getPartSysUser(request);
        if (partSysUser == null) {
            return CooperMsg.newFailed("用户未登录");
        }
        if (StringUtils.isEmpty(carcode)) {
            return CooperMsg.newFailed("车牌号码不能为空");
        }
        if (StringUtils.isEmpty(totalPrice)) {
            return CooperMsg.newFailed("缴费不能为空");
        }
        // 将元转为分
        Long price = FrameUtils.yuan2fen(totalPrice);
        // 调用自动支付
        parkOrderInfoService.autoPay(partSysUser.getParkCode(), carcode, price);
        return CooperMsg.newSuccess(null);
    }

    */
/**
     * 9. 现场收现金接口
     *//*

    */
/*@RequestMapping(value = "/payed/order", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object payedOrder(String carCode, String totalPrice) {
        if (StringUtils.isEmpty(carCode)) {
            return CooperMsg.newFailed("车牌号码不能为空");
        }
        if (StringUtils.isEmpty(totalPrice)) {
            return CooperMsg.newFailed("缴费不能为空");
        }

        return CooperMsg.newSuccess(null);
    }*//*


    */
/**
     * 10. 获取App预约信息接口
     *//*

    @RequestMapping(value = "/payed/reserve", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object payedReserve(HttpServletRequest request) {
        PartSysUserModel partSysUser = SessionUtils.getPartSysUser(request);
        if (partSysUser == null) {
            return CooperMsg.newFailed("用户未登录");
        }
        Map<String, String> params = FrameUtils.newMap();
        params.put("parkCode", partSysUser.getParkCode());
        // 类型：APP支付
        params.put("type", ParkOrderEnum.Type.RESERVE.toValue());
        params.put("state", ParkOrderEnum.State.RESERVE_INIT.toValue());
        List<ParkOrderInfoModel> orderInfos = parkOrderInfoService.pageList(params);

        List<CooperReturn> list = new ArrayList<>();
        for (ParkOrderInfoModel orderInfo : orderInfos) {
            CooperReturn cooperReturn = new CooperReturn();
            cooperReturn.setId(orderInfo.getId().toString());
            cooperReturn.setCarcode(orderInfo.getPlateNum());
            cooperReturn.setTime(orderInfo.getCreateTime());
            list.add(cooperReturn);
            // 更新预约状态
            orderInfo.setState(ParkOrderEnum.State.RESERVE_COMPLETE.toValue());
            orderInfo.setUpdateTime(new Date());
            parkOrderInfoService.update(orderInfo);
        }
        return CooperMsg.newSuccess(list);
    }

    */
/**
     * 11. 检查已缴费车辆是否超时接口
     *//*

    @RequestMapping(value = "/payed/payInfo", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object payedPayInfo(HttpServletRequest request) {
        List<CooperReturn> list = new ArrayList<>();
//        CooperReturn cooperReturn = new CooperReturn();
//        cooperReturn.setId("001");
//        cooperReturn.setCarcode("粤B12345");
//        cooperReturn.setTime(new Date());
//        list.add(cooperReturn);
//
//        cooperReturn = new CooperReturn();
//        cooperReturn.setId("002");
//        cooperReturn.setCarcode("沪A88345");
//        cooperReturn.setTime(new Date());
//        list.add(cooperReturn);
//
//        cooperReturn.setId("003");
//        cooperReturn.setCarcode("鄂A88322");
//        cooperReturn.setTime(new Date());
//        list.add(cooperReturn);
        return CooperMsg.newSuccess(list);
    }

    */
/**
     * 12. 预约超时未入场违约接口
     *//*

    @RequestMapping(value = "/payed/overtime", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object payedOvertime(HttpServletRequest request, Long id, String carCode, String totalPrice) throws UnsupportedEncodingException {
        carCode = URLDecoder.decode(carCode, "GBK");

        PartSysUserModel partSysUser = SessionUtils.getPartSysUser(request);
        if (partSysUser == null) {
            return CooperMsg.newFailed("用户未登录");
        }

        if (StringUtils.isEmpty(carCode)) {
            return CooperMsg.newFailed("车牌号码不能为空");
        }
        if (StringUtils.isEmpty(totalPrice)) {
            return CooperMsg.newFailed("缴费不能为空");
        }
        if (id == null || id == 0) {
            return CooperMsg.newFailed("订单号不能为空");
        }
//        long price = FrameUtils.yuan2fen(totalPrice);

        ParkOrderInfoModel orderInfo = parkOrderInfoService.selectById(id);
        // 设置超时状态
        orderInfo.setState(ParkOrderEnum.State.RESERVE_TIMEOUT.toValue());
        orderInfo.setUpdateTime(new Date());
        parkOrderInfoService.update(orderInfo);

        log.info("reserve timeout! carCode={}", carCode);
        return CooperMsg.newSuccess(null);
    }

    */
/**
     * 13. 取消预约接口
     *//*

    @RequestMapping(value = "/cancel/order", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object cancelOrder(HttpServletRequest request) {
        PartSysUserModel partSysUser = SessionUtils.getPartSysUser(request);
        if (partSysUser == null) {
            return CooperMsg.newFailed("用户未登录");
        }
        Map<String, String> params = FrameUtils.newMap();
        params.put("parkCode", partSysUser.getParkCode());
        // 类型：APP支付
        params.put("type", ParkOrderEnum.Type.RESERVE.toValue());
        params.put("state", ParkOrderEnum.State.RESERVE_CANCEL.toValue());
        List<ParkOrderInfoModel> orderInfos = parkOrderInfoService.pageList(params);

        List<CooperReturn> list = new ArrayList<>();
        for (ParkOrderInfoModel orderInfo : orderInfos) {
            CooperReturn cooperReturn = new CooperReturn();
            cooperReturn.setId(orderInfo.getId().toString());
            cooperReturn.setCarcode(orderInfo.getPlateNum());
            cooperReturn.setTime(orderInfo.getCreateTime());
            list.add(cooperReturn);
            // 更新预约状态
            orderInfo.setState(ParkOrderEnum.State.CANCEL.toValue());
            orderInfo.setUpdateTime(new Date());
            parkOrderInfoService.update(orderInfo);
        }
        return CooperMsg.newSuccess(list);
    }


    */
/**
     * 14. 超时新订单接口
     *//*

    */
/*@RequestMapping(value = "/newOrder", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object newOrder(String carCode, String orderDate) {
        if (StringUtils.isEmpty(carCode)) {
            return CooperMsg.newFailed("车牌号码不能为空");
        }
        if (StringUtils.isEmpty(orderDate)) {
            return CooperMsg.newFailed("订单时间不能为空");
        }
        return CooperMsg.newSuccess(null);
    }*//*


    */
/**
     * 添加白名单
     *//*

    @RequestMapping(value = "/addWhiteList", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object addWhiteList(HttpServletRequest request) {
        PartSysUserModel partSysUser = SessionUtils.getPartSysUser(request);
        if (partSysUser == null) {
            return CooperMsg.newFailed("用户未登录");
        }
        Map<String, String> params = FrameUtils.newMap("parkCode", partSysUser.getParkCode());
        params.put("state", ParkWhiteEnum.State.ADD.toValue());
        // 每次最多取10个白名单，防止停车场处理效率慢
        List<ParkWhiteListModel> whiteList = parkWhiteListService.pageList(params);
        List<CooperReturn> list = new ArrayList<>();
        for (ParkWhiteListModel white : whiteList) {
            CooperReturn cooperReturn = new CooperReturn();
            cooperReturn.setId(white.getId().toString());
            cooperReturn.setUsername(white.getUserName());
            cooperReturn.setCarcode(white.getPlateNum());
            cooperReturn.setTime(new Date());
            list.add(cooperReturn);
        }
        // 更新白名单状态
        parkWhiteListService.updateBySelective(whiteList, ParkWhiteEnum.State.共享.toValue());
        return CooperMsg.newSuccess(list);
    }

    */
/**
     * 删除白名单
     *//*

    @RequestMapping(value = "/delWhiteList", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object delWhiteList(HttpServletRequest request) {
        PartSysUserModel partSysUser = SessionUtils.getPartSysUser(request);
        if (partSysUser == null) {
            return CooperMsg.newFailed("用户未登录");
        }
        Map<String, String> params = FrameUtils.newMap("parkCode", partSysUser.getParkCode());
        params.put("state", ParkWhiteEnum.State.CANCEL.toValue());
        // 每次最多取10个白名单，防止停车场处理效率慢
        List<ParkWhiteListModel> whiteList = parkWhiteListService.pageList(params);
        List<CooperReturn> list = new ArrayList<>();
        for (ParkWhiteListModel white : whiteList) {
            CooperReturn cooperReturn = new CooperReturn();
            cooperReturn.setId(white.getId().toString());
            cooperReturn.setUsername(white.getUserName());
            cooperReturn.setCarcode(white.getPlateNum());
            cooperReturn.setTime(new Date());
            list.add(cooperReturn);
        }
        // 更新白名单状态
        parkWhiteListService.updateBySelective(whiteList, ParkWhiteEnum.State.BEEN_CANCEL.toValue());
        return CooperMsg.newSuccess(list);
    }

    */
/**
     * 白名单列表
     *//*

    @RequestMapping(value = "/whiteList", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object whiteList(HttpServletRequest request, String carcodelist) throws UnsupportedEncodingException {
        PartSysUserModel partSysUser = SessionUtils.getPartSysUser(request);
        if (partSysUser == null) {
            return CooperMsg.newFailed("用户未登录");
        }
        if (request.getMethod().toLowerCase().equals("get")) {
            // orders，返回参数，0：表示不做处理；1：表示请求返回所有白名单
            if (isWhiteList) {
                isWhiteList = false;
                return CooperMsg.newSuccess("1");
            }
            return CooperMsg.newSuccess("0");
        }
        carcodelist = URLDecoder.decode(carcodelist, "GBK");
        // 检验白名单
        String[] carCodes = carcodelist.split(",");
        log.info("return park all white list! carCodes={}", Arrays.toString(carCodes));
        parkWhiteListService.checkPlateNums(partSysUser.getParkCode(), carCodes);
        return CooperMsg.newSuccess(null);
    }

    */
/**
     * 开闸机接口
     *//*

    @RequestMapping(value = "/openGate", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object openGate(HttpServletRequest request) {
        List<CooperReturn> list = new ArrayList<>();

//        CooperReturn cooperReturn = new CooperReturn();
//        // id表示，开闸机请求标识
//        cooperReturn.setId("001");
//        cooperReturn.setUsername("张三");
//        cooperReturn.setGate("01-1");
//        cooperReturn.setTime(new Date());
//        list.add(cooperReturn);
//
//        cooperReturn = new CooperReturn();
//        cooperReturn.setId("002");
//        cooperReturn.setUsername("张三");
//        cooperReturn.setGate("01-2");
//        cooperReturn.setTime(new Date());
//        list.add(cooperReturn);

        return CooperMsg.newSuccess(list);
    }

    */
/**
     * 开闸反馈接口
     *//*

    @RequestMapping(value = "/replyOpenGate", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object replyOpenGate(HttpServletRequest request, String id, String state) {
        if (StringUtils.isEmpty(id)) {
            return CooperMsg.newFailed("ID标识不能为空");
        }
        // state，状态，1:成功；-1：失败；
        if (StringUtils.isEmpty(state)) {
            return CooperMsg.newFailed("状态不能为空");
        }
        return CooperMsg.newSuccess(null);
    }

    */
/**
     * 上传图片地址
     *//*

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
}
*/
