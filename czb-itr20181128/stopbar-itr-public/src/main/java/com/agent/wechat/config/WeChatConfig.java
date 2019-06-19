package com.agent.wechat.config;

import java.util.Properties;

import com.agent.wechat.util.PropertiesUtil;

public class WeChatConfig {
		
	/**
	    * 预支付请求地址
	    */
	   public static final String  PrepayUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	 
	   /**
	    * 查询订单地址
	    */
	   public static final String  OrderUrl = "https://api.mch.weixin.qq.com/pay/orderquery";
	 
	   /**
	    * 关闭订单地址
	    */
	   public static final String  CloseOrderUrl = "https://api.mch.weixin.qq.com/pay/closeorder";
	 
	   /**
	    * 申请退款地址
	    */
	   public static final String  RefundUrl = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	 
	   /**
	    * 查询退款地址
	    */
	   public static final String  RefundQueryUrl = "https://api.mch.weixin.qq.com/pay/refundquery";
	 
	   /**
	    * 下载账单地址
	    */
	   public static final String  DownloadBillUrl = "https://api.mch.weixin.qq.com/pay/downloadbill";
	 
	   /**
	    * 停巴商户APPID
	    */
	   public static final String  AppId = "wxb83cc58940b8d35d";
	 
	   /**
	    * 停巴商户账户
	    */
	   public static final String  MchId = "1515928041";
	 
	   /**
	    * 商户秘钥
	    */
	   public static final String  AppSercret = "2bf8591f364a4f84a6da044a0d696c4e";
	 
	   /**
	    * 服务器异步通知页面路径
	    */
	   public static String notify_url = "http://139.196.223.93:8080/stopbar-itr-restful/services/payBack/weChatNotify";
	 
	   /**
	    * 页面跳转同步通知页面路径
	    */
	   public static String return_url = "www.stopbar.com.cn";//getProperties().getProperty("return_url");
	 
	   /**
	    * 退款通知地址
	    */
	   public static String refund_notify_url = "www.stopbar.com.cn";//getProperties().getProperty("refund_notify_url");
	 
	   /**
	    * 退款需要证书文件，证书文件的地址
	    */
	  //public static String refund_file_path = getProperties().getProperty("refund_file_path");
	 
	   /**
	    * 商品名称
	    */
	   public static String subject =  "购买车位";//getProperties().getProperty("subject");
	 
	   /**
	    * 商品描述
	    */
	   public static String body = "停巴科技";
	 
	   private static  Properties properties;
	 
	   public static synchronized Properties getProperties(){
	      if(properties == null){
//	         String path = System.getenv(RSystemConfig.KEY_WEB_HOME_CONF) + "/weichart.properties";//自定义配置文件路径
	         String path = "stopbar-itr-public/src/main/java/resources/weChat.properties";//测试路径
	         properties = PropertiesUtil.getInstance().getProperties(path);
	      }
	      return properties;
	   }
public static void main(String[] args) {
	String property = getProperties().getProperty("body");
	System.out.println(property);
	
}
}
