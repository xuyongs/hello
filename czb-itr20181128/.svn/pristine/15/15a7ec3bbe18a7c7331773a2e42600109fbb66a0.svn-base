package com.agent.czb.service.cooper.restful;

import com.agent.czb.common.http.HttpClientUtils;
import com.agent.czb.common.json.JSONUtils;
import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.common.utils.MD5Utils;
import com.agent.czb.core.cooper.model.Prices;
import com.agent.czb.core.park.enums.ParkGateEnum;
import com.agent.czb.core.park.enums.ParkOrderEnum;
import com.agent.czb.service.TestBase;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/1/6.
 */
public class CooperRestfulTest extends TestBase {

    @Test
    public void testLogin() throws Exception {
        System.out.println();
        String url = TestBase.LOCAL_SERVER + "cooper/login";
        Map<String, String> map = FrameUtils.newMap("username", "admin");
        map.put("password", "22");
        String body = HttpClientUtils.get(url, map);
        System.out.println(body);
    }


    @Test
    public void testIodata() throws Exception {
        Map<String, String> map = FrameUtils.newMap();
        map.put("picture", "000003.jpg");
        map.put("ioState", ParkGateEnum.State.IN.toValue());
        map.put("carNo", "sh00011");
        map.put("isVip", "false");
        map.put("carCode", "湘A88666");
        map.put("ioDate", "2016-01-25 20:11:22");
        String s = remoteGet("/cooper/iodata", map);
        System.out.println(s);
    }

    @Test
    public void testPayInfo() throws Exception {
        Map<String, String> map = FrameUtils.newMap();
        String s = remoteGet("/cooper/payInfo", map);
        System.out.println(s);

    }

    /**
     * 计算停车场所有正在停车的订单的价格的接口
     */
    @Test
    public void testCountPrice() throws Exception {
        String s = localGet("cooper/countPrice", null);
        System.out.println(s);

    }

    @Test
    public void testSettlement() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("carcode", "沪Y45893");
        map.put("totalPrice", "100");
        String s = localGet("cooper/settlement", map);
        System.out.println(s);
    }

    /**
     * 计算出订单价格后，将价格返回给服务器的接口
     */
    @Test
    public void testGetResult() throws Exception {
        Prices prices = new Prices();
        prices.setId("2");
        prices.setCarCode("湘A12345");
        prices.setTotalPrice("110");

        Map<String, String> map = FrameUtils.newMap();
        map.put("prices", JSONUtils.toJsonString(prices));
        String s = localGet("cooper/getResult", map);
        System.out.println(s);
    }

    @Test
    public void testAddWhiteList() throws Exception {
        String s = remoteGet("/cooper/addWhiteList", null);
        System.out.println(s);
    }

    @Test
    public void testDelWhiteList() throws Exception {
        String s = remoteGet("/cooper/delWhiteList", null);
        System.out.println(s);
    }

    @Test
    public void testSessionTest() throws Exception {
        CloseableHttpClient hc = HttpClients.createDefault();
        {
            List<NameValuePair> params = new LinkedList<>();
            params.add(new BasicNameValuePair("username", "admin"));
            params.add(new BasicNameValuePair("password", "22"));

            HttpGet httpget = new HttpGet(TestBase.LOCAL_SERVER + "cooper/login");
            // 设置参数
            String param = URLEncodedUtils.format(params, Consts.UTF_8);
            httpget.setURI(new URI(httpget.getURI().toString() + "?" + param));
            // 发送请求
            CloseableHttpResponse response = hc.execute(httpget);
            String body;
            try {
                // 获取返回数据
                HttpEntity entity = response.getEntity();
                // 获取消息体
                body = EntityUtils.toString(entity, Consts.UTF_8);
                // 释放连接
                EntityUtils.consume(entity);
            } finally {
                EntityUtils.consumeQuietly(response.getEntity());
                response.close();
            }
            System.out.println(body);
        }

        {
            HttpGet httpget = new HttpGet(TestBase.LOCAL_SERVER + "cooper/countPrice");
            // 发送请求
            CloseableHttpResponse response = hc.execute(httpget);
            String body;
            try {
                // 获取返回数据
                HttpEntity entity = response.getEntity();
                // 获取消息体
                body = EntityUtils.toString(entity, Consts.UTF_8);
                // 释放连接
                EntityUtils.consume(entity);
            } finally {
                EntityUtils.consumeQuietly(response.getEntity());
                response.close();
            }
            System.out.println(body);
        }

    }
}