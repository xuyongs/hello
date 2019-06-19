package com.agent.czb.service.sys.restful;

import com.agent.czb.common.http.HttpClientUtils;
import com.agent.czb.common.utils.FrameUtils;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/1/11.
 */
public class ParkInfoRestfulTest {
    private static String httpPath = "http://localhost:8081/stopbar-itr-restful/";
    private static String servicePath = "http://139.196.174.197:8080/stopbar-itr-restful/";

    @Test
    public void testLocaList() throws Exception {
        String url = httpPath + "services/parkInfo/locaList";
        Map<String, String> map = FrameUtils.newMap("pageNum", "1");
        map.put("mapLng", "121.42517253158896");
        map.put("mapLat", "31.224567232499627");
        map.put("distance", "3000");
        map.put("pageNum", "0");
        String post = HttpClientUtils.get(url, map);
        System.out.println(post);
    }
}