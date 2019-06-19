package com.agent.czb.common.utils;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * @author linkai
 * @since 16/8/13
 */
public class CheckKeyUtils {
    public static final String KEY = "sdfjlsdkjfldsjlsdsldkjflk";

    public static String sign(Map<String, String> data) {
        TreeMap<String, String> params = new TreeMap<>(data);
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getKey().equals("sign")) {
                continue;
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        if (sb.length() > 0 ) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sign(sb.toString());
    }

    public static String sign(String str) {
        return MD5Utils.MD5(str + KEY);
    }

    public static boolean check(Map<String, String> data) {
        String sign = data.get("sign");
        return Objects.equals(sign, sign(data));
    }

}
