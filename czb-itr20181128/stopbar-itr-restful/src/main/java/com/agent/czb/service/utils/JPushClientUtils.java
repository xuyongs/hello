package com.agent.czb.service.utils;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import com.agent.czb.common.json.JSONUtils;
import com.agent.czb.core.park.model.JPushModel;
import org.apache.commons.collections.map.HashedMap;

import java.util.Map;

import static cn.jpush.api.push.model.notification.PlatformNotification.ALERT;

/**
 * 创建人:qany
 * 创建时间:2016/10/25.
 * 描述:极光推送
 */
public class JPushClientUtils {

public    static final String masterSecret="767547e80ec263a0668e1e91";
public    static final String appKey="5f029518801f8921c64ab135";

public     static final String masterSecret2="2fb273bf520f0ece2c3e7c11";
public     static final String appKey2="e4d67edb9be0c55c50612633";

    /**
     * 创建人:qany.
     * 创建时间:2016/10/25:10:36.
     * 描述: 推送所有
     */

    public static PushPayload buildPushObject_android_tag_alertWithTitle(JPushModel jp) {
        return PushPayload.newBuilder()
                .setPlatform(jp.getPlatform()==1?Platform.android():jp.getPlatform()==2?Platform.ios():Platform.all())//平台类型
                .setAudience(Audience.all())//设备名
                .setNotification(Notification.alert( "JPush test"))//推送内容
                .build();
     }

   /**
    * 创建人:qany.
    * 创建时间:2016/12/21:19:08.
    * 描述: 根据别名推送
    */
    public static void pushAlias(JPushModel jp,JPushClient jpushClient) {

        try {
            PushPayload   payload = PushPayload.newBuilder()
                    .setPlatform(jp.getPlatform()==1?Platform.android():jp.getPlatform()==2?Platform.ios():Platform.all())//平台类型
                    .setAudience(Audience.alias(jp.getAlias()))//设备名
                    .setNotification(Notification.alert(jp.getMessage()))//推送内容
                    .build();
              PushResult result = jpushClient.sendPush(payload);
            //  LOG.info("Got result - " + result);
            System.out.println(result);
        } catch (APIConnectionException e) {
            e.printStackTrace();
            // Connection error, should retry later
            // LOG.error("Connection error, should retry later", e);

        } catch (APIRequestException e) {
           // e.printStackTrace();
            // Should review the error, and fix the request
//            LOG.error("Should review the error, and fix the request", e);
//            LOG.info("HTTP Status: " + e.getStatus());
//            LOG.info("Error Code: " + e.getErrorCode());
//            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

    /**
     * 创建人:qany.
     * 创建时间:2016/12/21:19:08.
     * 描述: 根据别名推送自定义消息
     */
    public static void pushAlias(JPushModel jp,JPushClient jpushClient,String Data) {

        try {
            PushPayload   payload = PushPayload.newBuilder()
                    .setPlatform(jp.getPlatform()==1?Platform.android():jp.getPlatform()==2?Platform.ios():Platform.all())//平台类型
                    .setAudience(Audience.alias(jp.getAlias()))//设备名
                    .setMessage(Message.content(Data))
                    .build();
            PushResult result = jpushClient.sendPush(payload);
            //  LOG.info("Got result - " + result);
            System.out.println(result);
        } catch (APIConnectionException e) {
            e.printStackTrace();
            // Connection error, should retry later
            // LOG.error("Connection error, should retry later", e);

        } catch (APIRequestException e) {
            // e.printStackTrace();
            // Should review the error, and fix the request
//            LOG.error("Should review the error, and fix the request", e);
//            LOG.info("HTTP Status: " + e.getStatus());
//            LOG.info("Error Code: " + e.getErrorCode());
//            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

    public static void main(String[] args) {
        JPushModel jp=new JPushModel();
        jp.setPlatform(0);
        jp.setMessage("测试极光推送");
        jp.setAlias("17371319633");
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);
        JPushClient jpushClient2 = new JPushClient(masterSecret2, appKey2, 3);
        Map<String,Object> map=new HashedMap();
        map.put("name","hahhhha");
        map.put("is","推送的消息");
        pushAlias(jp,jpushClient,JSONUtils.toJsonString(map));
        pushAlias(jp,jpushClient2,JSONUtils.toJsonString(map));

    }


}

