package com.agent.czb.common.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.agent.czb.common.json.JSONUtils;

public class RestUtils {
    private static RestTemplate template;

    static {
        List<HttpMessageConverter<?>> converterList = new ArrayList<>();
        HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converterList.add(converter);
        template = new RestTemplate(converterList);
/*        List<HttpMessageConverter<?>> converterList = template.getMessageConverters();
        HttpMessageConverter<?> converterTarget = null;
        for (HttpMessageConverter<?> item : converterList) {
            if (item.getClass() == StringHttpMessageConverter.class) {
                converterTarget = item;
                break;
            }
        }

        if (converterTarget != null) {
            converterList.remove(converterTarget);
        }
        HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converterList.add(converter);*/
    }

    public static String post(String url) {
        return post(url, "{}", null);
    }

    public static String post(String url, String json) {
        return post(url, json, null);
    }

    public static String post(String url, Object obj) {
        return post(url, JSONUtils.toJsonString(obj), null);
    }

    public static String post(String url, Object obj, Map<String, ?> map) {
        return post(url, JSONUtils.toJsonString(obj), map);
    }

    public static String post(String url, String json, Map<String, ?> map) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity<String> output;
        if (map != null && !map.isEmpty()) {
            output = template.postForEntity(url, entity, String.class, map);
        } else {
            output = template.postForEntity(url, entity, String.class);
        }
        return output.getBody();
    }

    public static String get(String url, Map<String, ?> map) {
        ResponseEntity<String> output = template.getForEntity(url, String.class, map);
        return output.getBody();
    }

    public static String get(String url) {
        ResponseEntity<String> output = template.getForEntity(url, String.class);
        return output.getBody();
    }

    public static Map<String, String> request2map(HttpServletRequest request) {
        Enumeration<String> names = request.getParameterNames();
        Map<String, String> dataMap = new HashMap<>();
        while (names.hasMoreElements()) {
            String key = names.nextElement();
            String val = request.getParameter(key);
            if (val != null && !val.equals("")) {
                dataMap.put(key, val);
            }
        }
        // 添加url参数
        /*Map<String, String> urlParms = getUrlParms(request);
        for (String key : urlParms.keySet()) {
            if (dataMap.get(key) == null) {
                dataMap.put(key, urlParms.get(key));
            }
        }*/
        return dataMap;
    }

    public static Map<String, String> request2map2(HttpServletRequest request) {
        Map<String,String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        return params;
    }
    public static Map<String, String> request3map3(HttpServletRequest request) throws Exception  {
    	InputStream inputStream=null;
    	StringBuffer sb = new StringBuffer();
    	BufferedReader in=null;
    	String s;
    	try {
			inputStream = request.getInputStream();
	    	in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
	    	while ((s = in.readLine()) != null) {
	    	sb.append(s);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(in !=null){
				in.close();
			}
			if(inputStream==null){
			  inputStream.close();	
			}
		}
    	try{
	         Map<String, String> m = new HashMap<String, String>();
	         Document d = DocumentHelper.parseText(sb.toString());
	         Element root = d.getRootElement();
	         for ( Iterator<?> i = root.elementIterator(); i.hasNext(); ) {
	            Element element = (Element) i.next();
	            String name = element.getName();
	            if(!element.isTextOnly()){
	               continue;
	            }else{
	               m.put(name, element.getTextTrim());
	            }
	         }
	            return m;
	      }catch(DocumentException e){
	         e.printStackTrace();
	      }
    	return null;
    }

    public static Map<String, String> getUrlParms(HttpServletRequest request) {
        return getUrlParms(request.getQueryString());
    }

    public static Map<String, String> getUrlParms(String str) {
        Map<String, String> dataMap = new HashMap<>();
        if (StringUtils.isEmpty(str)) {
            return dataMap;
        }
        String[] split = str.split("&");
        for (String s : split) {
            if (s.contains("=")) {
                String[] split1 = s.split("=");
                if (StringUtils.isNotEmpty(split1[0]) && StringUtils.isNotEmpty(split1[1])) {
                    try {
                        dataMap.put(split1[0], URLDecoder.decode(split1[1], "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return dataMap;
    }
}
