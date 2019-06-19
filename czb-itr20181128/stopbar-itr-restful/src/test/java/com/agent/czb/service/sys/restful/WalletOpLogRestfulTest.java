package com.agent.czb.service.sys.restful;

import com.agent.czb.service.TestBase;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/2/16.
 */
public class WalletOpLogRestfulTest extends TestBase {

    @Test
    public void testPageList() throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId","2");
        String s = remoteGet("services/walletOpLog/pageList", map);
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
}