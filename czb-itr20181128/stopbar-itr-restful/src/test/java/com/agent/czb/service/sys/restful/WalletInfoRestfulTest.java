package com.agent.czb.service.sys.restful;

import com.agent.czb.common.http.HttpClientUtils;
import com.agent.czb.common.utils.FrameUtils;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/1/7.
 */
public class WalletInfoRestfulTest {
    private static String httpPath = "http://localhost:8081/stopbar-itr-restful/";
    private static String servicePath = "http://139.196.174.197:8080/stopbar-itr-restful/";

    @Test
    public void testPageList() throws Exception {
        String url = httpPath + "services/walletInfo/get";

        Map<String, String> map = FrameUtils.newMap("phone", "15317713190");
        String post = HttpClientUtils.get(url, map);
        System.out.println(post);
    }

    @Test
    public void testAddMoney() throws Exception {
        String url = httpPath + "services/walletInfo/addMoney";

        Map<String, String> map = FrameUtils.newMap("userId", "2");
        map.put("amount", "12");
        String post = HttpClientUtils.post(url, map);
        System.out.println(post);
    }
}