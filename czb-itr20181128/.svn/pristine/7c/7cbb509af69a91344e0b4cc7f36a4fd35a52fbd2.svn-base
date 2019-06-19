package com.agent.czb.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ParseString {
    public static String parseString(String value) throws UnsupportedEncodingException {
        String encoding = getEncoding(value);
        return new String(value.getBytes(encoding), "UTF-8");
    }

    private static List<String> charsets = new ArrayList<>();

    static {
        charsets = new ArrayList<>();
        charsets.add("ISO-8859-1");
        charsets.add("UTF-8");
    }

    public static String getEncoding(String str) {
        try {
            for (String charset : charsets) {
                if (check(str, charset))
                    return charset;
            }
        } catch (Exception ignored) {
        }
        return "";
    }

    public static boolean check(String str, String cs) throws UnsupportedEncodingException {
        String s1 = new String(str.getBytes(cs), cs);
        return str.equals(s1);
    }
}
