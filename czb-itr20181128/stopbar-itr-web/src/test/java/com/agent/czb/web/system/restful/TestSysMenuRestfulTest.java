package com.agent.czb.web.system.restful;

import com.agent.czb.common.http.HttpClientUtils;
import com.agent.czb.common.json.JSONUtils;
import com.agent.czb.common.rest.HttpHelp;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


public class TestSysMenuRestfulTest {
    private String httpPash;
    private RestTemplate template;

    @Before
    public void init() {
        httpPash = "http://localhost:8080/kdb-jf-web";
        template = new RestTemplate();
    }

    @Test
    public void testDataList() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("id", "2");
        map.put("name", "张三");
        String body = JSONUtils.mapToJsonString(map);
        System.out.println(body);
        /*String post = HttpHelp.httpPost(httpPash + "/services/sysMenu/dataList", body);
        System.out.println(post);*/

        String post = HttpClientUtils.post(httpPash + "/services/sysMenu/dataList", body);
        System.out.println(post);
    }

    @Test
    public void testData() throws Exception {
        String s = HttpHelp.httpPost(httpPash + "/services/sysMenu/data/2", "");
        System.out.println(s);
    }

    @Test
    public void testDataById() throws Exception {
        String s = HttpHelp.httpGet(httpPash + "/services/sysMenu/dataById?sysMenusId=2");
        System.out.println(s);
    }
}