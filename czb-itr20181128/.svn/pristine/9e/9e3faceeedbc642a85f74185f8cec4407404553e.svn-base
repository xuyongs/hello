package com.agent.wechat.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agent.wechat.config.WeChatConfig;
import com.agent.wechat.sdk.WXPayRequest;
import com.agent.wechat.sdk.WXPayUtil;

public class WeChatUtil {
		private static Logger log=LoggerFactory.getLogger(WeChatUtil.class);
	/**
	    * 返回状态码
	    */
	   public static final String ReturnCode = "return_code";
	 
	   /**
	    * 返回信息
	    */
	   public static final String ReturnMsg = "return_msg";
	 
	   /**
	    * 业务结果
	    */
	   public static final String ResultCode = "result_code";
	 
	   /**
	    * 预支付交易会话标识
	    */
	   public static final String PrepayId = "prepay_id";
	   
	   /**
	    * 微信统一下单
	    * @param orderId  商户自己的订单号
	    * @param totalFee  总金额  （分）
	    * @return
	 * @throws Exception 
	    */
	   public static Map<String, String> getPreyId(String orderId,String totalFee) throws Exception{
	      Map<String, String> reqMap = new HashMap<String, String>();
	      SortedMap<String, String> secReqMap = new TreeMap<String, String>();
	      reqMap.put("appid", WeChatConfig.AppId);
	      reqMap.put("mch_id", WeChatConfig.MchId);
	      reqMap.put("nonce_str", getRandomString());
	      reqMap.put("body", "stopbar");
	      reqMap.put("out_trade_no", orderId); //商户系统内部的订单号,
	      reqMap.put("total_fee", totalFee); //订单总金额，单位为分
	      reqMap.put("spbill_create_ip", getHostIp()); //用户端实际ip
	      reqMap.put("notify_url", WeChatConfig.notify_url); //通知地址
	      reqMap.put("trade_type", "APP"); //交易类型
	      reqMap.put("sign", WXPayUtil.generateSignature(reqMap, WeChatConfig.AppSercret));//获取签名
	      String reqStr = creatXml(reqMap);
	      //String retStr = WeChatHttpClientUtil.postHttps(WeChatConfig.PrepayUrl, reqStr);
	      String retStr1="";
		try {
		     retStr1 = WXPayRequest.httpsRequest(WeChatConfig.PrepayUrl, reqStr);//统一下单
			 Map<String, String> infoByXml = getInfoByXml(retStr1);
			 secReqMap.put("appid", WeChatConfig.AppId);
			 secReqMap.put("noncestr", getRandomString());
			 secReqMap.put("package", "Sign=WXPay");
			 secReqMap.put("partnerid",WeChatConfig.MchId );
			 secReqMap.put("prepayid",infoByXml.get("prepay_id"));
			 secReqMap.put("timestamp",String.valueOf(System.currentTimeMillis()/1000) );
			 secReqMap.put("sign", WXPayUtil.generateSignature(secReqMap, WeChatConfig.AppSercret));//二次验签
			 secReqMap.put("packageValue","Sign=WXPay");
		} catch (Exception e) {
			log.error("【WeChatUtil】获取与支付订单号失败{}",reqStr);
			e.printStackTrace();
		}
	      return secReqMap;
	   }
	 
	   /**
	    * 关闭订单
	    * @param orderId  商户自己的订单号
	    * @return
	    */
	   public static Map<String, String> closeOrder(String orderId){
	      Map<String, String> reqMap = new HashMap<String, String>();
	      reqMap.put("appid", WeChatConfig.AppId);
	      reqMap.put("mch_id", WeChatConfig.MchId);
	      reqMap.put("nonce_str", getRandomString());
	      reqMap.put("out_trade_no", orderId); //商户系统内部的订单号,
	      reqMap.put("sign", getSign(reqMap));
	 
	      String reqStr = creatXml(reqMap);
	      String retStr = WeChatHttpClientUtil.postHttps(WeChatConfig.CloseOrderUrl, reqStr);
	      return getInfoByXml(retStr);
	   }
	 
	 
	   /**
	    * 查询订单
	    * @param orderId 商户自己的订单号
	    * @return
	    */
	   public static String getOrder(String orderId){
	      Map<String, String> reqMap = new HashMap<String, String>();
	      reqMap.put("appid", WeChatConfig.AppId);
	      reqMap.put("mch_id", WeChatConfig.MchId);
	      reqMap.put("nonce_str", getRandomString());
	      reqMap.put("out_trade_no", orderId); //商户系统内部的订单号,
	      reqMap.put("sign", getSign(reqMap));
	 
	      String reqStr = creatXml(reqMap);
	      String retStr = WeChatHttpClientUtil.postHttps(WeChatConfig.OrderUrl, reqStr);
	      return retStr;
	   }
	 
	 
	   /**
	    * 退款
	    * @param orderId  商户订单号
	    * @param refundId  退款单号
	    * @param totralFee 总金额（分）
	    * @param refundFee 退款金额（分）
	    * @param opUserId 操作员ID
	    * @return
	    */
	   public static Map<String, String> refundWei(String orderId,String refundId,String totralFee,String refundFee,String opUserId){
	      Map<String, String> reqMap = new HashMap<String, String>();
	      reqMap.put("appid", WeChatConfig.AppId);
	      reqMap.put("mch_id", WeChatConfig.MchId);
	      reqMap.put("nonce_str", getRandomString());
	      reqMap.put("out_trade_no", orderId); //商户系统内部的订单号,
	      reqMap.put("out_refund_no", refundId); //商户退款单号
	      reqMap.put("total_fee", totralFee); //总金额
	      reqMap.put("refund_fee", refundFee); //退款金额
	      reqMap.put("op_user_id", opUserId); //操作员
	      reqMap.put("sign", getSign(reqMap));
	 
	      String reqStr = creatXml(reqMap);
	      String retStr = "";
	      try{
	        // retStr = WeChatHttpClientUtil.postHttplientNeedSSL(WeChatConfig.RefundUrl, reqStr, WeChatConfig.refund_file_path, WeChatConfig.MchId);
	      }catch(Exception e){
	         e.printStackTrace();
	         return null;
	      }
	      return getInfoByXml(retStr);
	   }
	 
	 
	   /**
	    * 退款查询
	    * @param refundId  退款单号
	    * @return
	    */
	   public static Map<String, String> getRefundWeiInfo(String refundId){
	      Map<String, String> reqMap = new HashMap<String, String>();
	      reqMap.put("appid", WeChatConfig.AppId);
	      reqMap.put("mch_id", WeChatConfig.MchId);
	      reqMap.put("nonce_str", getRandomString());
	      reqMap.put("out_refund_no", refundId); //商户退款单号
	      reqMap.put("sign", getSign(reqMap));
	 
	      String reqStr = creatXml(reqMap);
	      String retStr = WeChatHttpClientUtil.postHttps(WeChatConfig.RefundQueryUrl, reqStr);
	      return getInfoByXml(retStr);
	   }
	 
	   /**这个方法 可以自己写，以前我使用的是我公司封装的类，后来很多人找我要JAR包，所以我改成了这样，方便部分人直接使用代码，我自己未测试，不过应该问题不大，欢迎使用有问题的找我。
	    * 传入map  生成头为XML的xml字符串，例：<xml><key>123</key></xml>
	    * @param reqMap
	    * @return
	    */
	   public static String creatXml(Map<String, String> reqMap){
	      Set<String> set = reqMap.keySet();
	      StringBuffer b = new StringBuffer();
	      b.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	      b.append("<xml>");
	      for(String key : set){
	         b.append("<"+key+">").append(reqMap.get(key)).append("</"+key+">");
	      }
	      b.append("</xml>");
	      return b.toString();
	   }
	 
	   /**
	    * 得到加密值
	    * @param map
	    * @return
	    */
	   public static String getSign(Map<String, String> map){
	      String[] keys = map.keySet().toArray(new String[0]);
	      Arrays.sort(keys);
	      StringBuffer reqStr = new StringBuffer();
	      for(String key : keys){
	         String v = map.get(key);
	         if(v != null && !v.equals("")){
	            reqStr.append(key).append("=").append(v).append("&");
	         }
	      }
	      reqStr.append("key").append("=").append(WeChatConfig.AppSercret);
	 
	      return WeiMd5.encode(reqStr.toString()).toUpperCase();
	   }
	 
	   /**
	    * 得到10 位的时间戳
	    * 如果在JAVA上转换为时间要在后面补上三个0 
	    * @return
	    */
	   public static String getTenTimes(){
	      String t = new Date().getTime()+"";
	      t = t.substring(0, t.length()-3);
	      return t;
	   }
	 
	   /**
	    * 得到随机字符串
	    * @param length
	    * @return
	    */
	   public static String getRandomString(){
	      int length = 24;
	      String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	      Random random = new Random();
	      StringBuffer sb = new StringBuffer();
	 
	      for(int i = 0; i < length; ++i){
	         int number = random.nextInt(62);//[0,62)  
	         sb.append(str.charAt(number));
	      }
	      return sb.toString();
	   }
	 
	   /**
	    * 得到本地机器的IP
	    * @return
	    */
	   private static String getHostIp(){
	      String ip = "";
	      try{
	         ip = InetAddress.getLocalHost().getHostAddress();
	      }catch(UnknownHostException e){
	         e.printStackTrace();
	      }
	      return ip;
	   }
	   public static Map<String, String> getInfoByXml(String xmlStr){
	      try{
	         Map<String, String> m = new HashMap<String, String>();
	         Document d = DocumentHelper.parseText(xmlStr);
	         Element root = d.getRootElement();
	         for ( Iterator<?> i = root.elementIterator(); i.hasNext(); ) {
	            Element element = (Element) i.next();
	            String name = element.getName();
	            if(!element.isTextOnly()){
	               //不是字符串 跳过。确定了微信放回的xml只有根目录
	               continue;
	            }else{
	               m.put(name, element.getTextTrim());
	            }
	         }
	         //对返回结果做校验.去除sign 字段再去加密
	         /*String retSign = m.get("sign");
	         m.remove("sign");*/
	         /*String rightSing = getSign(m);
	         if(rightSing.equals(retSign)){*/
	            return m;
	         /*}*/
	      }catch(DocumentException e){
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	      return null;
	}
	 
	   /**
	    * 将金额转换成分
	    * @param fee 元格式的
	    * @return 分
	    */
	   public static String changeToFen(Double fee){
	      String priceStr = "";
	      if(fee != null){
	          int p = (int)(fee * 100); //价格变为分
	          priceStr = Integer.toString(p);
	      }
	      return priceStr;
	   }

}
