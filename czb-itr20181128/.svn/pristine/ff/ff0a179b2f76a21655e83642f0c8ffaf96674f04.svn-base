package com.agent.czb.common.code;

import com.agent.czb.common.code.coder.*;

import java.util.Map;

/**
 * SDACoder的工厂类
 *
 * @author duanyy
 */
public class CoderFactory {

    private static Map<String, Class<? extends Coder>> map = null;

    static {
        map.put("AES", AES.class);
        map.put("DES", DES.class);
        map.put("DES3", DES3.class);
        map.put("MD5", MD5.class);
        map.put("SHA1", SHA1.class);
    }

    synchronized public static Coder newCoder(String module) {
        if (map.get(module) != null) {
            try {
                return map.get(module).newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
