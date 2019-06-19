package com.agent.czb.service.sys.restful;

import com.agent.czb.service.TestBase;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/3/24.
 */
public class AdvertInfoRestfulTest extends TestBase {

    @Test
    public void testPageList() throws Exception {
        HashMap<String, String> map = new HashMap<>();
//        map.put("id","3");

        String s = localGet("services/advertInfo/pageList", map);
        System.out.println(s);
    }
}