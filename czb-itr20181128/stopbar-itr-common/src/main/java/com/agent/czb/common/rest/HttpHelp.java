package com.agent.czb.common.rest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HttpHelp {
    public static final String CHARSET_UTF_8 = "UTF-8";


    public static String httpPost(String url, Map<String, String> dataMap) throws IOException {
        String postData;
        HttpURLConnection con = null;
        URL dataUrl = new URL(url);
        try {
            if (dataMap == null || dataMap.isEmpty()) {
                postData = "";
            } else {
                postData = map2params(dataMap);
            }
            con = (HttpURLConnection) dataUrl.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.connect();  // 可以不用

            OutputStream os = con.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            dos.write(postData.getBytes(CHARSET_UTF_8));
            dos.flush();
            dos.close();

            InputStream is = con.getInputStream();
            DataInputStream dis = new DataInputStream(is);

            byte d[] = new byte[dis.available()];
            is.read(d);
            return new String(d, CHARSET_UTF_8);
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    public static String httpPost2(String url, Map<String, String> dataMap) throws IOException {
        String postData;
        HttpURLConnection con = null;
        URL dataUrl = new URL(url);
        try {
            if (dataMap == null || dataMap.isEmpty()) {
                postData = "";
            } else {
                postData = map2params(dataMap);
            }
            con = (HttpURLConnection) dataUrl.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.connect();  // 可以不用

            OutputStream os = con.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            dos.write(postData.getBytes(CHARSET_UTF_8));
            dos.flush();
            dos.close();

            InputStream is = con.getInputStream();
            DataInputStream dis = new DataInputStream(is);

            byte d[] = new byte[dis.available()];
            is.read(d);
            return new String(d, CHARSET_UTF_8);
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    public static String httpPost(String url, String postData, String charset) throws IOException {
        HttpURLConnection con = null;
        URL dataUrl = new URL(url);
        try {
            if (postData == null) {
                postData = "";
            }
            con = (HttpURLConnection) dataUrl.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json;charset=" + charset);
//            con.setRequestProperty("Content-type","application/x-java-serialized-object"); // 传输JAVA对象
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.connect();  // 可以不用

            OutputStream os = con.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            dos.write(postData.getBytes(charset));
            dos.flush();
            dos.close();

            InputStream is = con.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            byte d[] = new byte[dis.available()];
            is.read(d);
            return new String(d, charset);
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    public static String httpPost(String url, String postData) throws IOException {
        return httpPost(url, postData, CHARSET_UTF_8);
    }

    public static String httpPost(String url) throws IOException {
        return httpPost(url, "", CHARSET_UTF_8);
    }

    public static String httpGet(String url, String charset) throws IOException {
        HttpURLConnection con = null;
        URL dataUrl = new URL(url);
        try {
            con = (HttpURLConnection) dataUrl.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json;charset=" + charset);
            con.setDoOutput(false);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.connect();  // 可以不用

            InputStream is = con.getInputStream();
            byte d[] = new byte[is.available()];
            is.read(d);
            return new String(d, charset);
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    public static String httpGet(String url) throws IOException {
        return httpGet(url, CHARSET_UTF_8);
    }

    /**
     * Url参数转化
     */
    public static String Urlencode(Map<String, String> map) {
        StringBuilder params = new StringBuilder();
        boolean b = false;
        for (String key : map.keySet()) {
            if (b) {
                params.append("&");
            } else {
                b = true;
            }
            String val = map.get(key);
            if (val == null) {
                val = "";
            }
            params.append(Urlencode(key)).append("=").append(Urlencode(val));
        }
        return params.toString();
    }

    /**
     * Url参数转化
     */
    public static String map2params(Map<String, String> map) {
        StringBuilder params = new StringBuilder();
        boolean b = false;
        for (String key : map.keySet()) {
            if (b) {
                params.append("&");
            } else {
                b = true;
            }
            String val = map.get(key);
            if (val == null) {
                continue;
            }
            params.append(key).append("=").append(val);
        }
        return params.toString();
    }

    /**
     * Url参数转化
     */
    public static String Urlencode(String str) {
        try {
            return URLEncoder.encode(str, CHARSET_UTF_8);
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }
}
