package com.agent.czb.client.dll;

import com.lk.jna.JnaTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * @author linkai
 * @since 16/8/10
 */

public class ParkDllUtils {
    private static Logger log = LoggerFactory.getLogger(ParkDllUtils.class);

    public static String call(String json) {
        String re;
        log.info("park remote call params : {}", json);
        try {
//            json = JnaTest.ParkDll.INSTANCE.TB_GetAmount(json);
            CallDllThread callDllThread = new CallDllThread(new CountDownLatch(1), json);
            callDllThread.start();
            re = callDllThread.getJson();
        } catch (Exception e) {
            log.error("park remote call error!", e);
            re = "{\"message\" : \"调用异常\"}";
        }
        log.info("park remote call return params : {}", re);
        return re;
    }

    public static class CallDllThread extends Thread {
        private CountDownLatch cdl;
        private String str;

        public CallDllThread(CountDownLatch cdl, String str) {
            this.cdl = cdl;
            this.str = str;
        }
        private String json = null;
        @Override
        public void run() {
            try {
                json = JnaTest.ParkDll.INSTANCE.TB_GetAmount(str);
            } finally {
                cdl.countDown();
            }
        }

        public String getJson() {
            try {
                cdl.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return json;
        }
    }
}
