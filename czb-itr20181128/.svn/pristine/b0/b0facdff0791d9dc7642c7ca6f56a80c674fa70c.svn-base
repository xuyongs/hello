package com.agent.czb.common.utils;

import com.agent.czb.common.json.JSONUtils;
import com.agent.czb.common.rest.Errors;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 容联API调用类
 */
public class SendMsgUtils {
    //测试模板
    public static final String MSG_TEMPLATE_TEST = "1";
    //验证码短信
    public static final String MSG_TEMPLATE_CODE = "200650";
    //发送短信通知
    public static final String MSG_TMEPLATE_NOTICE = "143920";
    //发布者短信提醒
    public static final String MSG_TMEPLATE_SALER_REMIND = "373063";
    //购买者的短信提示
    public static final String MSG_TMEPLATE_BUYER_REMIND = "373070";
    //购买车位超时提醒
    public static final String MSG_TMEPLATE_BUYER_OVERTIME = "373073";
    
    private static final Logger logger = LoggerFactory.getLogger(SendMsgUtils.class);
    // 是否测试环境
    private static final boolean isDebug = false;
    //测试环境
    private static final String httpPashDebug = "https://sandboxapp.cloopen.com:8883";
    //生产环境
    private static final String httpPashFormal = "https://app.cloopen.com:8883";
    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'A', 'B', 'C', 'D', 'E', 'F'};
    //主账号ID
//    public static String accountSid = "8a48b5514d9861c3014d990a15e300d8";
    public static String accountSid = "8aaf070855d8c1790155d8cff68e0026";
    //应用授权
//    public static String authToken = "4c6abfbf3fc8450888bb30e9722d9c1d";
    public static String authToken = "c0102864d5ab4feab1a582757b808027";
    //测试应用ID
//    public static String appId = "8a48b5514d9861c3014d9915f25300de";
    //正式应用ID
    private static final String appId = "8a216da85e0e48b2015e1722ff49030e";

    /**
     * 获取数字验证码
     */
    public static String randomNumber(int length) {
        if (length <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((int) Math.floor(Math.random() * 9 + 1));
        }
        return sb.reverse().toString();
    }

    public static String getMsgBody(String phone, String code, String effTime) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<>();
        String[] datas1 = new String[]{code, effTime};
        map.put("to", phone);
        map.put("appId", appId);
        map.put("MSG_TEMPLATE_TEST", MSG_TEMPLATE_TEST);
        map.put("datas", datas1);
        return JSONUtils.toJsonString(map);
    }

    public static String getMsgBody(String templateId, String phone, String... datas) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<>();
        map.put("to", phone);
        map.put("appId", appId);
        map.put("templateId", templateId);
        map.put("datas", datas);
        return JSONUtils.toJsonString(map);
    }

    //绑定白名单发送短信
    public static String getMsgBodys(String templateId, String phone, String data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<>();
        map.put("to", phone);
        map.put("appId", appId);
        map.put("templateId", templateId);
        map.put("data", data);
        return JSONUtils.toJsonString(map);
    }

    public static String getRegMsgBody(String phone, String code, String effTime) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return getMsgBody(phone, code, effTime);
    }

    public static String getSetPwdMsgBody(String phone, String code, String effTime) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return getMsgBody(phone, code, effTime);
    }

    public static boolean sendMsg(String phone, String code, String templateId) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        // 创建时间 1
        String currenTime = getCurrenTime();

        // 生成sig 1 无需修改
        String sig = getSig(currenTime);

        // url 2 已修改
        String url;
        if (isDebug) {
            url = getUrl(httpPashDebug, currenTime, sig);
        } else {
            url = getUrl(httpPashFormal, currenTime, sig);
        }
        // 发送短信 2 已修改
        String json;
        // 短信验证码
        if (templateId.equals(MSG_TEMPLATE_CODE)) {
            json = getMsgBody(MSG_TEMPLATE_CODE, phone, code, "5");
        } else {
            throw Errors.baseServiceException("未找到模板");
        }
        // authorization 1
        String authorization = getAuthorization(currenTime);
        String posts = posts(url, json, authorization);
        logger.info("message send response! phone: {}, data: {}", phone, posts);
        try {
            Map<String, Object> result = JSONUtils.jsonToMap(posts);
            if ("000000".equals(result.get("statusCode"))) {
                return true;
            } else {
                //异常返回输出错误码和错误信息
                logger.info("send error! code : {}, msg : {}", result.get("statusCode"), result.get("statusMsg"));
            }
        } catch (Exception e) {
            logger.info("send error!", e);
        }
        return false;
    }

    //发送白名单绑定信息
    public static boolean sendMsg(String phone, String code, String templateId, String data) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        // 创建时间 1
        String currenTime = getCurrenTime();

        // 生成sig 1 无需修改
        String sig = getSig(currenTime);

        // url 2 已修改
        String url;
        if (isDebug) {
            url = getUrl(httpPashDebug, currenTime, sig);
        } else {
            url = getUrl(httpPashFormal, currenTime, sig);
        }
        // 发送短信 2 已修改
        String json;
        // 短信验证码
        if (templateId.equals(MSG_TEMPLATE_CODE)) {
            json = getMsgBody(MSG_TEMPLATE_CODE, phone, code, "5");
        } else if (templateId.equals(MSG_TMEPLATE_NOTICE)) {
            json = getMsgBodys(MSG_TMEPLATE_NOTICE, phone, data);
        } else {
            throw Errors.baseServiceException("未找到模板");
        }
        // authorization 1
        String authorization = getAuthorization(currenTime);
        String posts = posts(url, json, authorization);
        if (logger.isDebugEnabled()) {
            logger.debug("result ==> " + posts);
        }
        try {
            Map<String, Object> result = JSONUtils.jsonToMap(posts);
            if ("000000".equals(result.get("statusCode"))) {
                return true;
            } else {
                //异常返回输出错误码和错误信息
                logger.info("send error! code : {}, msg : {}", result.get("statusCode"), result.get("statusMsg"));
            }
        } catch (Exception e) {
            logger.info("send error!", e);
        }
        return false;
    }
    /**
     * 
     * @param phone 手机号
     * @param code {1}短信内容
     * @param templateId
     * @param next {2}短信内容
     * @return 返回值 fales 发送失败  true 发送成功
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static boolean sendMsgRemind(String phone, String code, String templateId,String next) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        // 创建时间 1
        String currenTime = getCurrenTime();

        // 生成sig 1 无需修改
        String sig = getSig(currenTime);

        // url 2 已修改
        String url;
        if (isDebug) {
            url = getUrl(httpPashDebug, currenTime, sig);
        } else {
            url = getUrl(httpPashFormal, currenTime, sig);
        }
        // 发送短信 2 已修改
        String json;
        // 短信验证码
        if (templateId.equals(MSG_TMEPLATE_SALER_REMIND)||templateId.equals(MSG_TMEPLATE_BUYER_REMIND)||templateId.equals(MSG_TMEPLATE_BUYER_OVERTIME)) {
            json = getMsgBody(templateId, phone, code, next);
        } else {
            throw Errors.baseServiceException("未找到模板");
        }
        // authorization 1
        String authorization = getAuthorization(currenTime);
        String posts = posts(url, json, authorization);
        logger.info("message send response! phone: {}, data: {}", phone, posts);
        try {
            Map<String, Object> result = JSONUtils.jsonToMap(posts);
            if ("000000".equals(result.get("statusCode"))) {
                return true;
            } else {
                //异常返回输出错误码和错误信息
                logger.info("send error! code : {}, msg : {}", result.get("statusCode"), result.get("statusMsg"));
            }
        } catch (Exception e) {
            logger.info("send error!", e);
        }
        return false;
    }

    public static String getUrl(String serverUrl, String currentime, String sig) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // 生产URL
        return serverUrl + "/2013-12-26/Accounts/" + accountSid + "/SMS/TemplateSMS" + "?sig=" + sig;
    }

    private static String getCurrenTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return df.format(new Date());
    }

    private static String getSig(String currentime) {
        String sig = accountSid + authToken + currentime;
        return mmd532(sig);
    }

    private static String getAuthorization(String currentime) throws UnsupportedEncodingException {
        return EncoderBase64(accountSid, currentime);
    }

    public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        //加密后的字符串
        return Base64.encodeBase64String(md5.digest(str.getBytes("utf-8")));
    }

    //base64加密
    public static String EncoderBase64(String sid, String timestamp) throws UnsupportedEncodingException {
        String value = sid + ":" + timestamp;
        return Base64.encodeBase64String(value.getBytes("utf-8"));
    }

    public static boolean isPhoneNO(String mobiles) {
        if (StringUtils.isEmpty(mobiles)) {
            return false;
        }
        Pattern p = Pattern.compile("^1\\d{10}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();

    }

    public static String toHexString(byte[] b) {
        //String to  byte
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (byte aB : b) {
            sb.append(HEX_DIGITS[(aB & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[aB & 0x0f]);
        }
        return sb.toString();
    }

    public static String mmd532(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            return toHexString(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String posts(String url, String json, String authorization) {
        CloseableHttpClient hc = HttpClients.createDefault();
        String body = null;
        try {
            HttpPost post = new HttpPost(url);
            post.addHeader("Accept", "application/json");
            post.addHeader("Content-Type", "application/json;charset=utf-8");
            post.addHeader("Authorization", authorization);
            BasicHttpEntity requestBody = new BasicHttpEntity();
            requestBody.setContent(new ByteArrayInputStream(json.getBytes("utf-8")));
            requestBody.setContentLength(json.getBytes("utf-8").length);
            post.setEntity(requestBody);
            CloseableHttpResponse response = hc.execute(post);
            try {
                HttpEntity output = response.getEntity();
                // 设置参数
                body = EntityUtils.toString(output, Consts.UTF_8);
            } finally {
                EntityUtils.consumeQuietly(response.getEntity());
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                hc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return body;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String phone = "17371319633";
        String code = "我的车牌信息沪ASF001";
        boolean b = sendMsgRemind(phone, code, "361065","测试成功");
        System.out.println("result ==> " + b);
//        System.out.println(isPhoneNO("15317713190"));
    }
}
