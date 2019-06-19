package com.agent.czb.service.park.restful;

import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.service.TestBase;
import org.junit.Test;

import java.util.Map;

/**
 * Created by Administrator on 2016/1/24.
 */
public class ParkOrderInfoRestfulTest extends TestBase {

    @Test
    public void testPageList() throws Exception {

    }

    @Test
    public void testGet() throws Exception {

    }

    @Test
    public void testSetInfo() throws Exception {

    }

    @Test
    public void testAdd() throws Exception {

    }

    @Test
    public void testEdit() throws Exception {

    }

    @Test
    public void testCountPrice() throws Exception {
        Map<String, String> map = FrameUtils.newMap();
        map.put("userId", "3");
        map.put("plateNum", "苏R99999");
        String s = remotePost("services/parkOrderInfo/countPrice", map);
        System.out.println(s);
    }

    @Test
    public void testWaitCountPrice() throws Exception {
        Map<String, String> map = FrameUtils.newMap();
        map.put("userId", "3");
        map.put("orderId", "588");

        String s = remotePost("services/parkOrderInfo/waitCountPrice", map);
        System.out.println(s);
    }

    @Test
    public void testDel() throws Exception {

    }
}