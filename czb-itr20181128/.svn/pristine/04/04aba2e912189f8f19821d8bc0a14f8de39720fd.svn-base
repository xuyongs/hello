package com.agent.czb.common.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class DomXMLUtil {
	/**
	 * 获取XML节点数据
	 * 单节点
	 * @param result
	 * @param key 节点名称
	 * @return 节点数据
	 * @throws DocumentException
	 */
	public static String strToXml(String result,String key) throws DocumentException{
		 Document dom=DocumentHelper.parseText(result);
		  Element root=dom.getRootElement();
		  String value=root.element(key).getText();
		return value ;
	}
	
	public static void main(String[] args) {
		String xml="<xml><appid><![CDATA[wx2421b1c4370ec43b]]></appid><attach><![CDATA[支付测试]]></attach><bank_type><![CDATA[CFT]]></bank_type><fee_type><![CDATA[CNY]]></fee_type></xml>";
		try {
			String strToXml = strToXml(xml,"attach");
			System.out.println(strToXml);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
