package com.agent.czb.service.park.restful;

import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.service.TestBase;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/3/2.
 */
public class ParkSysInfoRestfulTest extends TestBase {

    @Test
    public void testPageList() throws Exception {
        Map<String, String> params = FrameUtils.newMap();
//        params.put("name", "京B00003");
        String s = remoteGet("services/parkSysInfo/pageList", params);
        System.out.println(s);

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
    public void testDel() throws Exception {

    }

    @Test
    public void testGetParkCode() throws Exception {
        Map<String, String> params = FrameUtils.newMap();
        params.put("name", "三鑫花苑");
        String s = remotePost("services/parkSysInfo/getParkCode", params);
        System.out.println(s);
    }
}