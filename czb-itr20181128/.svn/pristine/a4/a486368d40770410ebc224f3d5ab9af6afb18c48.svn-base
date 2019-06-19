package com.agent.czb.server;

import org.springframework.util.ConcurrentReferenceHashMap;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author linkai
 * @since 16/8/10
 */
public class ParkMsgHandle {
    private static Map<String, HandlerComplete<String>> map = new ConcurrentReferenceHashMap<>();

    public static HandlerComplete<String> create(String id) {
        HandlerComplete<String> handlerComplete = new HandlerComplete<>();
        map.put(id, handlerComplete);
        return handlerComplete;
    }

    public static void setData(String id, String data) {
        HandlerComplete<String> complete = map.remove(id);
        if (complete != null) {
            complete.setData(data);
        }
    }

    public static class HandlerComplete<T> {
        private CountDownLatch latch = new CountDownLatch(1);
        private T t;
        public void setData(T t) {
            try {
                this.t = t;
            } finally {
                latch.countDown();
            }
        }
        public T getData() {
            try {
                latch.await(10, TimeUnit.SECONDS);
            } catch (InterruptedException ignored) {
            }
            return t;
        }
    }
}
