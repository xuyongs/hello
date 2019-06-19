package com.agent.czb.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;

/**
 * @author linkai
 * @since 16/8/14
 */
public class Main {
    private static Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        // 设置jnl路径
        System.setProperty("java.library.path", "C:\\Program Files (x86)\\EParkingManage\\x64");

        // 启动客户端
        ChatClient client = new ChatClient(Constants.CLIENT_URI);
        try {
            client.connect();
            log.info("socket client start!");
        } catch (URISyntaxException e) {
            log.error("socket client start error!", e);
        }

        CountDownLatch downLatch = new CountDownLatch(1);
        try {
            downLatch.await();
        } catch (InterruptedException ignored) {
            log.info("stop app !", ignored);
        }
    }
}
