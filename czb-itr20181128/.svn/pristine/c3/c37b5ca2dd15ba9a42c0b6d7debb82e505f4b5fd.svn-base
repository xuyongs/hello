package com.agent.czb.service.sys.restful;

import com.agent.czb.service.TestBase;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/1/28.
 */
public class PayOrderLogRestfulTest extends TestBase {

    @Test
    public void testPageList() throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId","2");
        String s = remoteGet("services/payOrderLog/pageList", map);
        System.out.println(s);
    }

    @Test
    public void testGet() throws Exception {
        // 是否超时
        if (new Date().after(DateUtils.addMinutes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-09-06 11:09:00"), 10))) {
            System.out.println(true);
        }
        System.out.println(false);

    }

    @Test
    public void testSetInfo() throws Exception {

    }

    @Test
    public void testPay() throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("payType","zfb");
        map.put("refType","1");
        map.put("refId","31");
        map.put("quantity","1");
        map.put("price","100");
        map.put("amount","1");
        map.put("userId","2");
        String s = remotePost("services/payOrderLog/pay", map);
        System.out.println(s);
    }

    @Test
    public void testPayReturn() throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("resultStatus","9000");
        map.put("result","out_trade_no=74&partner=34535345345354");
        String s = localPost("services/payOrderLog/payReturn", map);
        System.out.println(s);
    }

    @Test
    public void testEdit() throws Exception {

    }

    @Test
    public void testDel() throws Exception {

    }
}