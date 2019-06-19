package com.agent.czb.client;

import com.agent.czb.common.utils.PropertiesUtils;

import java.util.Properties;

/**
 * @author linkai
 * @since 16/8/10
 */
public class Constants {
    public static String CLIENT_URI = "http://localhost:9092";
    public static String PARK_ID = "1";
    public static String PARK_CODE = "PD_001";

    static {
        Properties p = PropertiesUtils.load("client-conf");
        CLIENT_URI = PropertiesUtils.get(p, "client.uri", CLIENT_URI);
        PARK_ID = PropertiesUtils.get(p, "park.id", PARK_ID);
        PARK_CODE = PropertiesUtils.get(p, "park.code", PARK_CODE);
        
    }
}
