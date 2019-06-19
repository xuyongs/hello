package com.agent.czb.core.sys.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 车位信息模型层
 */
@Data
@Accessors(chain = true)
public class CarportInfoModel implements Serializable { // 车位信息
	private Long id;//标识
	private String code;//车位编码
	private String type;//车位类型
	private String name;//车位名称
	private String title;//车位标题
	private String model;//租车模式
	private String des;//车位描述
	private Long price;//车位价钱
	private String city;//所在城市
	private String area;//所在区域
	private String addr;//车位地址
	private String openTime;//营业时间
	private String contacter;//联系人
	private String parkCode;//系统停车场编码
	private String phone;//联系电话
	private String imgUrl;//图片地址
	private String state;//状态，0：发布；1：购买； 2:过期下线；3：管理员手动下线；4，用户手动下架 ；5。待审核；6：短租已购买。
	private Long buyLogId;//购买日志标识
	private Long userId;//上传人
	private Integer months;//发布月数
	private java.util.Date effTime;//失效时间
	private java.util.Date createTime;//创建时间
	private java.util.Date updateTime;//修改时间
	private Integer visits; //车位被查看次数
	private Integer isDel;//是否删除

	private String stateStr;
	private UserInfoModel userInfo;
	private UserInfoModel buyer;
	private String userName;
	private List<String> imgs;
	private String distance;
	private String mapLng;//经度
	private String mapLat;//纬度
	private String parkSysAddr;
	private Long collectNum; //车位收藏数量
	private Integer isCollect; //该车位是否被当前用户收藏

	private Integer publishType;//发布类型  1:按小时发布，0:按天发布
	private Long maxAmount;//封顶价格
	private Long minutePrice;//分时 价格
	private Integer isBuy;//是否是第二次被卖出了  0：未被购买；1：已经被购买；
}
