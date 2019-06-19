package com.agent.czb.pub.zfb.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088421444446531";
	
	// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
	public static String seller_id = partner;
	// 商户的私钥 Appid：2016011901105241
	public static String private_key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANdwzO/uHcRltBXg" +
			"QERk8TRydfy9YxDlm59XWMzVDd+soSloLO5IHzM/Ycmh0wpjuG8WufU6HTEUFBZz" +
			"5tt96ee79y9hyZ1AVp3gSX4X1inskSRpHYtN5BpoAcZdN7k6cO23FzSP+L3Tv9nr" +
			"iTPftNnn+QZx4KPsk0BOewmtcOHtAgMBAAECgYEArTi/LrMQMBduIqC6S8O1xOu7" +
			"PkmwW6eh5w4+EgPEMfIFatueNMC8hWIS6CI7I4Fmi7uELU5apll0hVEPb6/f3r9w" +
			"mVZvPf2xMJHW7uaANATpgsyTqkET+MB634Mh8xVd52o3Y2loxL8VTV9TL6cKqfid" +
			"ATJrcpEB8e/cxvddmUECQQDz5PH2k6TSkdTw4n9aKnyjQ0kWDR25N7qnFmDgsxET" +
			"JKRPmA5+Ae8ptoJEk8ZRuFgBgKgMU5Bi44810+mJ0CG1AkEA4iJOpB4OXTJE1azu" +
			"QTnxabEBS8jGU9MQ3QL88mG0pHrzgBXN8pQ5INvqaCOTkvgow8wMRpxWC371hUoO" +
			"nqLCWQJBAO/g/AKzUGcyu6d4fh1msFO7eATSPavQHir8nagjNuYHIfyO56ITsPDr" +
			"6tAcL/BsSVKhAaeuANLiUhZ1KLs9FAkCQGKrfYz24GWh7Y8eqcckNUZcyaf7ry4c" +
			"RptpFl3cbCnozB7zZK2YJ2VDEADopRfX2FG20xRsWs8hUpgaPE7TzeECQCmmzDAn" +
			"t6BfIFCTtg1b54YkDIPZ3GysF7sMPCHZTnj2dM3Ca1i1gkgLKdo4/X8TQ4FE+B7v" +
			"tpVIiGq7epMGNH4=";
	
	// 支付宝的公钥，无需修改该值
	public static String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	public static String public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "RSA";

}
