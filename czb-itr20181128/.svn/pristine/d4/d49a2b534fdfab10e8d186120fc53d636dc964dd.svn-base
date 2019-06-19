package com.agent.czb.server;

import com.agent.czb.common.utils.PropertiesUtils;

import java.util.Properties;

/**
 * @author linkai
 * @since 16/8/10
 */
public class Constants {
    public static int SERVER_PORT = 9092;
    public static String SERVER_HOME = "localhost";

    static {
        Properties p = PropertiesUtils.load("server-conf");
        SERVER_PORT = PropertiesUtils.getInt(p, "server.port", SERVER_PORT);
        SERVER_HOME = PropertiesUtils.get(p, "server.host", SERVER_HOME);
    }

    public static ChatServer server;
}
