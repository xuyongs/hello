package com.agent.czb.common;

import org.apache.commons.beanutils.BeanUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 解析str语句
 */
public class ParseStrExpUtils {

    public static String process(String str, Object obj) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            int indexStart = str.indexOf("${");
            if (indexStart != -1) {
                int indexEnd = str.indexOf("}", indexStart);
                if (indexEnd != -1) {
                    sb.append(str.substring(0, indexStart));
                    // 添加属性
                    String field = str.substring(indexStart + 2, indexEnd);
                    Object value = getValue(obj, field);
                    if (value != null) {
                        sb.append(value);
                    }
                    str = str.substring(indexEnd + 1);
                }
            } else {
                sb.append(str);
                break;
            }
        }
        return sb.toString();
    }

    public static Object getValue(Object obj, String field) {
        if (obj instanceof Map) {
            return ((Map) obj).get(field);
        } else {
            return getJavaBeanValue(obj, field);
        }
    }

    public static Object getJavaBeanValue(Object obj, String field) {
        Object reObj = null;
        Class<?> aClass = obj.getClass();
        String methodName = "get" + field.substring(0, 1).toUpperCase(Locale.ENGLISH) + field.substring(1);
        try {
            Method method = aClass.getMethod(methodName);
            reObj = method.invoke(obj);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return reObj;
    }

    public static void setJavaBeanValue(Object obj, String field, Object val) {
        Class<?> aClass = obj.getClass();
        String methodName = "set" + field.substring(0, 1).toUpperCase(Locale.ENGLISH) + field.substring(1);
        try {
            Method method = aClass.getMethod(methodName, val.getClass());
            method.invoke(obj, val);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Map --> Bean
     * 利用org.apache.commons.beanutils 工具类实现 Map --> Bean
     */
    public static void transMap2Bean2(Map<String, Object> map, Object obj) {
        if (map == null || obj == null) {
            return;
        }
        try {
            BeanUtils.populate(obj, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Map --> Bean
     * 利用Introspector,PropertyDescriptor实现 Map --> Bean
     */
    public static void transMap2Bean(Map<String, Object> map, Object obj) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    // 得到property对应的setter方法
                    Method setter = property.getWriteMethod();
                    setter.invoke(obj, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Bean --> Map
     * 利用Introspector和PropertyDescriptor 将Bean --> Map
     */
    public static Map<String, Object> transBean2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Bean --> Map(String, String)
     * 利用Introspector和PropertyDescriptor 将Bean --> Map
     */
    public static Map<String, String> transBean2MapStr(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    if (value != null) {
                        map.put(key, value.toString());
                    } else {
                        map.put(key, null);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    public static Document processXml(String xml) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(xml.getBytes()));
    }

    public static Element xmlToElement(String xml) throws IOException, SAXException, ParserConfigurationException {
        Document document = processXml(xml);
        return document.getDocumentElement();
    }

    public static String xmlToTextContent(Element element) {
        return element.getTextContent();
    }

    public static String xmlToAttribute(Element element, String attrName) {
        return element.getAttribute(attrName);
    }
}
