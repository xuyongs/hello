package com.agent.czb.common.park.call;

import com.agent.czb.common.http.HttpClientUtils;
import com.agent.czb.common.json.JSONUtils;
import com.agent.czb.common.rest.Errors;
import com.agent.czb.common.utils.FrameUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地停车场服务接口
 *
 * @author linkai
 * @since 16/8/7
 */
public class CallRemoteDllUtils {
    public static final String KEY = "e67a03f9-a845-4be8-967e-51b11888191e";
    private static final String REMOTE_URL = "http://139.196.174.197:8080/stopbar-itr-server/park-call";
    private static Logger logger = LoggerFactory.getLogger(CallRemoteDllUtils.class);

    /**
     * 创建人:qany.
     * 创建时间:2016/12/16:18:36.
     * 描述: 预约车位
     */
    public static Map<String, Object> reservedPark(String parkCode,Map map){

    	String message = JSONUtils.mapToJsonString(map);
        Map<String, String> data = new HashMap<>();
        data.put("parkCode", parkCode);
        data.put("message", message);
        logger.info("call dll request ! data : {}", map.toString());
        String post = HttpClientUtils.post(REMOTE_URL, data);
        logger.info("call dll result ! post : {}", post);
        return JSONUtils.jsonToMap(post);
    }
    public static Map<String, Object> post(String parkCode, Map map) {
        String message = JSONUtils.mapToJsonString(map);
        Map<String, String> data = new HashMap<>();
        data.put("parkCode", parkCode);
        data.put("message", message);
//        data.put("sign", CheckKeyUtils.sign(data));
        logger.info("call dll request ! data : {}", data.toString());
        String post = HttpClientUtils.post(REMOTE_URL, data);
        logger.info("call dll result ! post : {}", post);
        return JSONUtils.jsonToMap(post);
    }

    public static Map<String, Object> post(String parkCode, String map) {
        //String message = JSONUtils.mapToJsonString(map);
        Map<String, String> data = new HashMap<>();
        data.put("parkCode", parkCode);
        data.put("message", map);
//        data.put("sign", CheckKeyUtils.sign(data));
        logger.info("call dll request ! data : {}", data.toString());
        String post = HttpClientUtils.post(REMOTE_URL, data);
        System.out.println(data.toString());
        logger.info("call dll result ! post : {}", post);
        return JSONUtils.jsonToMap(post);
    }

    public static boolean isSuccess(Map<String, Object> map) {
        return map.get("status") != null && map.get("status").toString().equals("0");
    }

    /**
     * 8. 金额计算
     * 请求:
     * {
     * "interfacetype":"1",  //接口类型定义 1-金额计算 2-查询计费金额 3-车位分享预定
     * "orderid":"XXXX"  //订单号
     * }
     * 返回:
     * {
     * "interfacetype":"1",         / /接口类型定义 1-金额计算 2-查询计费金额 3-白名单请求修改
     * "orderid":"XXXX"           //订单ID
     * "platenumber"："京A12345"     //车牌号
     * "cartype"："1"                    //车辆类型 1-月租车 2-免费车 3-临时车 4-月租车（临时车） 5-月租车（月租车） 6-黑名单车辆 7-异常临时车（保留） 8-预约车 9-租赁车（保留） 10-充值车
     * "price":"10.00"                  //单位元
     * "starttime":"2016-07-12 12:12:12"  //开始时间
     * "spendtime":"150"               //停留时长 分钟
     * }
     */

    public static ParkAmount amount(String parkCode, Long orderId) {
        Map<String, Object> map = post(parkCode, FrameUtils.newMap("interfacetype", "1", "orderid", orderId.toString()));
        if (!isSuccess(map)) {
            logger.error("amout error! parkCode {}, orderId {}", parkCode, orderId);
            //throw Errors.baseServiceException("停车场连接错误");
            return null;
        }
        // 是否报错
        Map<String, Object> data = JSONUtils.jsonToMap(map.get("data").toString());
        if (data.get("status") != null && data.get("status").toString().equals("0")) {
            logger.error("计算金额错误");
            //throw Errors.baseServiceException("计算金额错误");
        }
        if (data.get("price") == null) {
            return null;
        }
        String price = getString(data, "price");
        String cartype = getString(data, "cartype");
        String starttime = getString(data, "starttime");
        String spendtime = getString(data, "spendtime");
        String detailed = getString(data, "detailed");

        ParkAmount parkAmount = new ParkAmount();
        parkAmount.setPrice(FrameUtils.yuan2fen(price));
        // 车辆类型 1-月租车 2-免费车 3-临时车 4-月租车(（临时车)） 5-月租车(（月租车)） 6-黑名单车辆
        //         7-异常临时车（保留） 8-预约车 9-租赁车（保留） 10-充值车 11-一人多车 12-错时停车
        parkAmount.setCartype(cartype);
        parkAmount.setStarttime(starttime);
        parkAmount.setSpendtime(spendtime);
        parkAmount.setDetailed(detailed);
        return parkAmount;
    }

    private static String getString(Map data, String key) {
        if (data != null && data.get(key) != null) {
            return data.get(key).toString();
        }
        return null;
    }

    /**
     * 9. 查询计费金额
     * 参数：
     * {
     * "interfacetype":"2",      //接口类型定义 1-金额计算 2-查询计费金额 3-白名单请求修改
     * "orderid":"XXXX"       //订单ID（车牌号和订单号，可以任意输入一个即可，默认优先订单号）
     * "platenumber":"XXXX"  //车牌号 (可以根据车牌号和订单ID来查询收费情况)
     * "starttime":""               //开始时间
     * "endtime":""              //结束时间
     * }
     * <p>
     * 返回值：
     * {
     * "interfacetype":"2",      //接口类型定义 1-金额计算 2-查询计费金额 3-白名单请求修改
     * "detailed":                 //收费明细数组
     * [
     * {"platenumber":"","cartype":"","starttime":"","endtime":"","price":""},
     * {...}
     * ]
     * }
     * // platenumber - 车牌号
     * // cartype      - 车辆类型
     * // starttime - 车辆类型
     * // endtime - 结束时间
     * // price     -收费金额
     */
    public static String queryAmount(String parkCode, String plateNum, String startTime, String endTime) {
        return "";
    }

    /**
     * 10. 车位错时处理(就是添加白名单)
     * 参数：
     * {
     * "interfacetype":"3", //接口类型定义 1-金额计算 2-查询计费金额 3-白名单请求修改
     * "platenumber":"XXXX" //车牌号 (可以根据车牌号和订单ID来查询收费情况)
     * "starttime":"11:11:11" //开始时间
     * "endtime":"11:11:11" //结束时间
     * "data": "{
     * "startdate": "2016-09-07", //分享车位的开始日期
     * "enddate": "2016-09-07",//分享车位的结束日期
     * "cycle": 1, //周期类型 0：全部日期 1：周一至周五 2：仅周六、周日
     * "des": "全日" //周期类型描述
     * }"
     * }
     * 返回值：
     * {
     * "interfacetype":"3",      //接口类型定义 1-金额计算 2-查询计费金额 3-车位错时
     * "status":  "1"               //返回值1-接收成功 0-处理失败
     * "msg":"ok"
     * }
     */
    public static void main(String[] args) {
        ParkAmount park =amount("PD_007",19812L);
        System.out.println(park);

    }

    public static String addWhiteList(String parkCode,  String platenumber, String whiteplatenum, String code, String starttime, String endtime, String des) {
        //Map<String, Object> params = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("interfacetype", "3");
        jsonObject.put("platenumber", platenumber);
        jsonObject.put("whiteplatenum",whiteplatenum);
        jsonObject.put("code", code);
        jsonObject.put("starttime", starttime);
        jsonObject.put("endtime", endtime);
        JSONArray jsons = new JSONArray();
        jsons.add(JSONUtils.jsonToMap(des));
        jsonObject.put("data",  jsons);
        Map<String, Object> map = post(parkCode, jsonObject.toString());
        if (!isSuccess(map)) {
            logger.error("addWhiteList error! parkCode {}, platenumber {}", parkCode, platenumber);
            throw Errors.baseServiceException("添加白名单错误");
        }
        return "";
    }

    /**
     * 11. 缴费成功
     * 参数：
     * {
     * "interfacetype":"4",      //接口类型定义 1-金额计算 2-查询计费金额 4-缴费成功
     * "orderid":"XXXX"       //订单ID
     * "platenumber"："京A12345" //车牌
     * "payType":""               //支付类型，1：线下支付，2：线上支付(微信支付)，3：线上支付(支付宝支付)
     * "payTime":""            //支付时间
     * "payPrice":""            //支付金额
     * }
     * 返回值：
     * {
     * "interfacetype":"4",      //接口类型定义 1-金额计算 2-查询计费金额4-缴费成功
     * "status":  "1"               //返回值1-接收成功 0-处理失败
     * "msg":"ok"
     * }
     */
    public static void paySuccess(String parkCode, String plateNum, String orderId, String payType, String payTime, Long payPrice) {
        String price = String.valueOf(FrameUtils.fen2yuan(payPrice));
        Map<String, String> data = new HashMap<>();
        data.put("interfacetype", "4");
        data.put("orderid", orderId);
        data.put("platenumber", plateNum);
        data.put("payType", payType);
        data.put("payTime", payTime);
        data.put("payPrice", price);
        Map<String, Object> map = post(parkCode, data);
        if (!StringUtils.equals(map.get("status").toString(), "1")) {
            logger.error("pay success error! post : ", map.toString());
        }
    }

    /**
     * 12. 白名单查询
     * 参数：
     * {
     * "interfacetype":"5",      //接口类型定义5-查询白名单书
     * "starttime":"",           //开始时间，按照白名单的有效期查询
     * "endtime":"",           //结束时间
     * }
     * 返回值：
     * {
     * "interfacetype":"5",      //接口类型定义 4-白名单查询
     * "detailed":                 //白名单明细
     * [
     * {"platenumber":"","cartype":"","starttime":"","endtime":""},
     * {...}
     * ]
     * }
     */
    public static String queryWhiteList(String parkCode, String startTIme, String endTime) {
        return "";
    }

    /**
     * 13. 白名单添加
     * 参数：
     * {
     * "interfacetype":"6",      //接口类型定义6-白名单添加
     * "platenumber":"",       //车牌号
     * "starttime":"",           //开始时间，按照白名单的有效期查询
     * "endtime":"",           //结束时间
     * "price":""               //缴费金额
     * }
     * 返回值：
     * {
     * "interfacetype":"6",      //接口类型定义 6 - 白名单添加
     * "status":  "1"               //返回值1-接收成功 0-处理失败
     * "msg":"ok"
     * }
     */
    public static String addWhiteList2(String parkCode, String plateNum, String startTIme, String endTime) {
        return "";
    }

    /**
     * 13. 白名单修改
     * 参数：
     * {
     * "interfacetype":"7",      //接口类型定义7-白名单修改
     * "platenumber":"",       //车牌号
     * "starttime":"",           //开始时间，按照白名单的有效期查询
     * "endtime":"",           //结束时间
     * "price":""               //缴费金额
     * }
     * 返回值：
     * {
     * "interfacetype":"6",      //接口类型定义 6 - 白名单添加
     * "status":  "1"               //返回值1-接收成功 0-处理失败
     * "msg":"ok"
     * }
     */
    public static String editWhiteList(String parkCode, String plateNum, String startTIme, String endTime, String price) {
        return "";
    }

    /**
     * 14. 白名单删除
     * 参数：
     * {
     * "interfacetype":"8",      //接口类型定义8-白名单删除
     * "platenumber":"",       //车牌号
     * }
     * 返回值：
     * {
     * "interfacetype":"8",      //接口类型定义 6 - 白名单添加
     * "status":  "1"               //返回值1-接收成功 0-处理失败
     * "msg":"ok"
     * }
     */
    public static String editWhiteList2(String parkCode, String plateNum) {
        return "";
    }

    @Data
    public static class ParkAmount {
        Long price;
        String cartype;
        String starttime;
        String spendtime;
        String detailed;
    }


}
