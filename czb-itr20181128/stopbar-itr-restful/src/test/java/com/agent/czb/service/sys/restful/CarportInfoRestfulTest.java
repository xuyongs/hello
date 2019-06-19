package com.agent.czb.service.sys.restful;

import com.agent.czb.common.http.HttpClientUtils;
import com.agent.czb.common.json.JSONUtils;
import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.service.TestBase;
import org.junit.Test;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/1/7.
 */
public class CarportInfoRestfulTest extends TestBase {

    @Test
    public void testPageList() throws Exception {
        String url = "services/carportInfo/pageList";
        Map<String, String> map = FrameUtils.newMap();
//        map.put("pagerOrder", "order by id desc");
//        map.put("id", "");
        map.put("showImgs", "1");
        map.put("showBuyer", "1");
        String post = remoteGet(url, map);
        Map<String, Object> maps = JSONUtils.jsonToMap(post);
        Map<String, Object> datas = (Map<String, Object>) maps.get("data");
        List<Map<String, Object>> data = (List<Map<String, Object>>) datas.get("data");
        System.out.println(data.size());
        System.out.println(post);
    }

    @Test
    public void testLocaList() throws Exception {
        Map<String, String> map = FrameUtils.newMap("phone", "15317713190");
        map.put("mapLng", "121.1434");
        map.put("mapLat", "31.2555");

//        String post = localGet("services/carportInfo/get", map);
        String post = localGet("services/carportInfo/locaList", map);
        Map<String, Object> maps = JSONUtils.jsonToMap(post);
        Map<String, Object> datas = (Map<String, Object>) maps.get("data");
        List<Map<String, Object>> data = (List<Map<String, Object>>) datas.get("data");
        System.out.println(data.size());
        System.out.println(post);
    }

    @Test
    public void testRelease() throws Exception {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("code","cw_001");
        map.put("userId","2");
        map.put("months","10");
        map.put("parkCode","PD_001");
        map.put("name", "测试车位名称06");
        map.put("title","测试title06");
        map.put("des","非常编译的车位22");
        map.put("price", "10");
        map.put("addr", "测试地址");
        map.put("city", "上海");
        map.put("area", "浦东新区");
        map.put("openTime","0924");
        map.put("contacter","张三");
        map.put("phone","13554723736");
        map.put("mapLng", "121.1434");
        map.put("mapLat","31.2555");
        map.put("mapLat","31.2555");
        map.put("effTime","2016-07-12 02:00:01");
        map.put("fileUrls", "/file/车位/1454085527262001.png,/file/车位/1454085574318002.png");

        String s = remotePost("services/carportInfo/release", map);
        System.out.println(s);

    }

    @Test
    public void testName() throws Exception {

        String s = FrameUtils.newNickName("1");
        System.out.println(s);

    }
}