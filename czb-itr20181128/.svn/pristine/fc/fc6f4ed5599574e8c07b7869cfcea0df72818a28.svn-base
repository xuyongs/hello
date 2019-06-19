package com.agent.czb.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author linkai
 */
public class JobCodeUtils {
    private static String toDay;
    private static int count;

    public static String getJobCode() {
        String jobCode;
        int num;
        SimpleDateFormat sdfShort = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = sdfShort.format(new Date());
        synchronized (JobCodeUtils.class) {
            if (toDay == null) {
                toDay = format;
                count = 1;
            } else {
                if (!toDay.equals(format)) {
                    toDay = format;
                    count = 1;
                }
            }
            jobCode = toDay;
            num = count++;
        }
        return jobCode + "_" + fillStr(num, 3);
    }

    private static String fillStr(int num, int length) {
        String str = String.valueOf(num);
        int strLen = str.length();
        if (strLen >= length) {
            return str;
        }
        for (int i = 0; i < length - strLen; i++) {
            str = "0" + str;
        }
        return str;
    }
}
