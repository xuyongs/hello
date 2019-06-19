package com.agent.czb.core.park.model;

import com.agent.czb.core.sys.model.UserInfoModel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 停车场白名单表模型层
 */
@Data
@ToString
@Accessors(chain = true)
public class ParkWhiteListModel implements Serializable { // 停车场白名单表
    private Long id;//标识
    private Long userId;//用户标识
    private String code;//车位编码
    private String addr;//车位地址
    private String parkCode;//停车场编码
    private String plateNum;//车牌号码
    private String userName;//用户名
    private java.util.Date startTime;//开始时间
    private java.util.Date endTime;//结束时间
    private String state;//状态，0：车位；1：共享；
    private String model;//模式
    private String openTime; // 开放时间
    private String phone; // 手机号
    private String remark;//备注
    private Integer isExp; // 1 为失效
    private Date expTime; // 失效时间
    private Long carportId; // 车位标识
    private java.util.Date createTime;//创建时间
    private java.util.Date updateTime;//修改时间

    private Long carportPrice;
    private String cycle;
    private String stateStr;
    private ParkSysInfoModel parkSysInfo;
    private UserInfoModel userInfo;
    private String whiteplatenum;
    private String comment;
}
