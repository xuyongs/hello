package com.agent.czb.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;

/**
 * @author linkai
 */
public class MD5Utils {
    /**
     * MD5 加密
     */
    public static String MD5(String origin) {
        String resultString = MD5Encode(origin, "UTF-8");
        if (StringUtils.isNotBlank(resultString)) {
            return resultString.toUpperCase();
        }
        return resultString;
    }

    /**
     * MD5 比较 匹配origin 加密后是否等于md5
     *
     * @param origin 密码， 未加密
     * @param md5    已加密字符串
     * @return
     */
    public static boolean ecompareMD5(String origin, String md5) {
        String result = MD5(origin);
        return md5.equals(result);
    }

    /*
     *  MD5 加密
     * */
    public static String MD5Encode(String origin, String charsetname) {
        String resultString = origin;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname)){
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
            }
        } catch (Exception ignored) {
        }
        return resultString;
    }

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private static String byteArrayToHexString(byte b[]) {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) resultSb.append(byteToHexString(aB));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static void main(String[] args) {
        System.out.println(MD5Utils.MD5("123456"));
    }
}
