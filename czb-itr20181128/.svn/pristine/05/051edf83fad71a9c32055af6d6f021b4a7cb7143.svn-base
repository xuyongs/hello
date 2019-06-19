package com.agent.czb.service.sys.restful;

import com.agent.czb.service.TestBase;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/3/9.
 */
public class CarportCollectRestfulTest extends TestBase {

    @Test
    public void testPageList() {
        HashMap<String, String> map = new HashMap<>();
        map.put("showImgs", "true");
        map.put("showBuyer", "true");
        map.put("showSeller", "true");

        String s = remoteGet("services/carportCollect/pageList", map);
        System.out.println(s);
    }

}