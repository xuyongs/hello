package com.agent.czb.service.user.restful;

import com.agent.czb.common.http.HttpClientUtils;
import com.agent.czb.common.utils.DateUtils;
import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.core.sys.model.UserInfoModel;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2015/12/13.
 */
public class UserInfoRestfulTest {
    private static String httpPath = "http://localhost:8081/stopbar-itr-restful/";
    private static String servicePath = "http://139.196.174.197:8080/stopbar-itr-restful/";

    @Test
    public void testIsPhone() throws Exception {
        String url = httpPath + "services/userInfo/isPhone";

        Map<String, String> map = FrameUtils.newMap("phone", "12312312312");
        String post = HttpClientUtils.post(url, map);
        System.out.println(post);
    }

    @Test
    public void testName() throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("select timeKey,");
        sb.append("ROUND(6378.138*2*ASIN(SQRT(POW(SIN((?*PI()/180-lat*PI()/180)/2),2)");
        sb.append("+COS(?*PI()/180)*COS(lat*PI()/180)*POW(SIN((?*PI()/");
        sb.append("180-lng*PI()/180)/2),2)))*1000) AS jl ");
        sb.append("from t_news having jl <= 3000 order by jl asc limit ?,?");
        System.out.println(sb.toString());
        List<Object> params = new ArrayList<Object>();
//        params.add(lat);
//        params.add(lat);
//        params.add(lng);
//        params.add((curPage - 1) * 20);
//        params.add(curPage * 20);

    }

    // http://my.oschina.net/alexgaoyh/blog/392123


    @Test
    public void testEdit() throws Exception {
        String url = httpPath + "services/userInfo/edit";

        Map<String, String> map = FrameUtils.newMap("userId", "3");
        map.put("nickName", "堕落天使");
        map.put("signa", "我是一枝花");
        map.put("email", "sssss45@163.com");
        map.put("isAuth", "1");
        map.put("userName", "张三");
        map.put("idNumber", "");
        map.put("plateNum", "鄂A33434");
        String post = HttpClientUtils.post(url, map);
        System.out.println(post);
    }

    @Test
    public void testGenerate() throws Exception {
        for (int i = 0; i < 50; i++) {
            System.out.println(FrameUtils.generateFileSeq());
        }
    }
}