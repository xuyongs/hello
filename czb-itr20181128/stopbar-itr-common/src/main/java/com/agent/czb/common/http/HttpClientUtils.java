package com.agent.czb.common.http;

import com.agent.czb.common.json.JSONUtils;
import com.agent.czb.common.utils.Base64Utils;
import com.alibaba.fastjson.util.Base64;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HttpClientUtils {
    public static final Charset DEFAULT_CHARSET = Consts.UTF_8;

    /**
     * Post请求
     */
    public static String get(String url) {
        return get(url, Collections.<NameValuePair>emptyList());
    }

    /**
     * Get请求
     */
    public static String get(String url, Map<String, String> params) {
        return get(url, map2list(params));
    }

    /**
     * Get请求
     */
    public static String get(String url, List<NameValuePair> params) {
        CloseableHttpClient hc = HttpClients.createDefault();
        String body = null;
        try {
            // Get请求
            HttpGet httpget = new HttpGet(url);
            httpget.addHeader("Accept", "application/json");
            if (params != null && !params.isEmpty()) {
                // 设置参数
                String param = URLEncodedUtils.format(params, DEFAULT_CHARSET);
                httpget.setURI(new URI(httpget.getURI().toString() + "?" + param));
            }
            // 发送请求
            CloseableHttpResponse response = hc.execute(httpget);
            try {
                // 获取返回数据
                HttpEntity entity = response.getEntity();
                // 获取消息体
                body = EntityUtils.toString(entity, DEFAULT_CHARSET);
                // 释放连接
                EntityUtils.consume(entity);
            } finally {
                EntityUtils.consumeQuietly(response.getEntity());
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                hc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return body;
    }

    /**
     * Post请求
     */
    public static String post(String url) {
        return post(url, "{}");
    }

    /**
     * Post请求
     */
    public static String post(String url, Object obj) {
        return post(url, JSONUtils.toJsonString(obj));
    }

    /**
     * Post请求
     */
    public static String post(String url, String json) {
        CloseableHttpClient hc = HttpClients.createDefault();
        String body = null;
        try {
            HttpPost post = new HttpPost(url);
            post.addHeader("Accept", "application/json");

            HttpEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            post.setEntity(entity);
            CloseableHttpResponse response = hc.execute(post);
            try {
                HttpEntity output = response.getEntity();
                // 设置参数
                body = EntityUtils.toString(output, Consts.UTF_8);
            } finally {
                EntityUtils.consumeQuietly(response.getEntity());
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                hc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return body;
    }
    
    /**
     * Post请求
     */
    public static String post(String url, String json,String authorization) {
        CloseableHttpClient hc = HttpClients.createDefault();
        String body = null;
        try {
            HttpPost post = new HttpPost(url);
            
            post.addHeader("Accept", "application/json");
            post.addHeader("Content-Type","application/json;charset=utf-8");
            post.addHeader("Authorization",authorization);
            HttpEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            post.setEntity(entity);
            CloseableHttpResponse response = hc.execute(post);
            try {
                HttpEntity output = response.getEntity();
                // 设置参数
                body = EntityUtils.toString(output, Consts.UTF_8);
            } finally {
                EntityUtils.consumeQuietly(response.getEntity());
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                hc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return body;
    }

    /**
     * Post请求
     * enctype=multipart/form-data
     * 需要 httpmime jar 包的支持
     */
    public static String postMultipart(String url, List<FormBodyPart> params) {
        CloseableHttpClient hc = HttpClients.createDefault();
        String body = null;
        try {
            HttpPost post = new HttpPost(url);

            MultipartEntityBuilder entity = MultipartEntityBuilder.create();
            entity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            for (FormBodyPart bodyPart : params) {
                entity.addPart(bodyPart);
//            entity.addPart("param1", new StringBody("中国", Charset.forName("UTF-8")));
//            entity.addPart("param3", new FileBody(new File("C:\\1.txt")));
            }

            post.setEntity(entity.build());
            CloseableHttpResponse response = hc.execute(post);
            try {
                HttpEntity output = response.getEntity();
                // 设置参数
                body = EntityUtils.toString(output, Consts.UTF_8);
            } finally {
                EntityUtils.consumeQuietly(response.getEntity());
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                hc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return body;
    }

    /**
     * Post请求
     */
    public static String post(String url, Map<String, String> params) {
        return post(url, map2list(params));
    }

    /**
     * Post请求
     */
    public static String post(String url, List<NameValuePair> params) {
        CloseableHttpClient hc = HttpClients.createDefault();
        String body = null;
        try {
            // Post请求
            HttpPost httppost = new HttpPost(url);
            httppost.addHeader("Accept", "application/json");
            // 设置参数
            httppost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
            // 发送请求
            CloseableHttpResponse httpresponse = hc.execute(httppost);
            try {
                // 获取返回数据
                HttpEntity output = httpresponse.getEntity();
                // 获取消息体
                body = EntityUtils.toString(output, Consts.UTF_8);
            } finally {
                EntityUtils.consumeQuietly(httpresponse.getEntity());
                httpresponse.close();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                hc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return body;
    }

    /**
     * Url参数转化
     */
    public static String Urlencode(String str, String charset) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, charset);
    }

    public static List<NameValuePair> map2list(Map<String, String> params) {
        List<NameValuePair> list = null;
        if (params != null && !params.isEmpty()) {
            list = new ArrayList<>();
            for (String key : params.keySet()) {
                if (params.get(key) != null) {
                    list.add(new BasicNameValuePair(key, params.get(key)));
                }
            }
        }
        return list;
    }

    /**
     * Url参数转换
     * @param params
     * @return
     */
    public static String map2urlparam(Map<String, String> params) {
        return URLEncodedUtils.format(map2list(params), DEFAULT_CHARSET);
    }

    /**
     * Url参数转化
     */
    public static String Urlencode(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, DEFAULT_CHARSET.toString());
    }


    /**
     * 创建人:qany.
     * 创建时间:2016/10/27:12:55.
     * 描述: 用于请求 极光IM API
     */
    public static String postJMessage(String url, String json) {
        CloseableHttpClient hc = HttpClients.createDefault();
        String body = null;
        try {
            HttpPost post = new HttpPost(url);
            post.addHeader("Accept", "application/json");
            //极光认证
            post.addHeader("Authorization","Basic "+(Base64Utils.getBase64("f37741a0cdd44f65ab342956:9d1c21f407f5ad87468a9b4c")));

            HttpEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            post.setEntity(entity);
            CloseableHttpResponse response = hc.execute(post);
            try {
                HttpEntity output = response.getEntity();
                // 设置参数
                body = EntityUtils.toString(output, Consts.UTF_8);
            } finally {
                EntityUtils.consumeQuietly(response.getEntity());
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                hc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return body;
    }
}
