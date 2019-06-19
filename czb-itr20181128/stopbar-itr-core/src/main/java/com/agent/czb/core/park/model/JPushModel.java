package com.agent.czb.core.park.model;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

/**
 * 创建人 qany.
 * 创建时间  2016/10/27:16:16.
 * 描述
 */
@Data
public class JPushModel {
    // 推送平台类别：0 所有平台，1 ios平台，2 android平台
    private Integer platform;
    // 推送标签
    private JSONArray tag;
    //标签别名
    private String alias;
    //推送内容
    private String message;
    //推送标题
    private String title;
    //短信补充内容
    private String sms_messageContent;
    //补充短信发送延时 单位为秒，不能超过24小时。设置为0，表示立即发送短信。该参数仅对android平台有效，iOS 和 Winphone平台则会立即发送短信
    private Integer sms_messagedelay_time;
}
