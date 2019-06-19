package com.agent.czb.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author linkai
 */
public class DateUtils {
    public static SimpleDateFormat F_YMD = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat F_YMD_ = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat F_YMD_STR = new SimpleDateFormat("yyyy年MM月dd日");
    public static SimpleDateFormat F_YMDHMS = new SimpleDateFormat("yyyyMMddHHmmss");
    public static SimpleDateFormat F_YMDHMS2 = new SimpleDateFormat("yyMMddHHmmss");
    public static SimpleDateFormat F_YMDHMS_ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat F_YMDHMS_STR = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

    /**
     * 格式化日期
     */
    public static String format(Date date, SimpleDateFormat sdf) {
        return sdf.format(date);
    }

    /**
     * 字符串转换为日期
     */
    public static Date parse(String str, SimpleDateFormat... sdfs) {
        for (SimpleDateFormat sdf : sdfs) {
            try {
                return sdf.parse(str);
            } catch (ParseException ignored) {
            }
        }
        return null;
    }

    /**
     * 判断日志是否为今天
     */
    public static boolean isToday(Date date) {
        String format = format(date, F_YMD);
        String format2 = format(new Date(), F_YMD);
        return format.equals(format2);
    }

    public static String format(SimpleDateFormat sdf) {
        return format(new Date(), sdf);
    }

    public static String formatYMD(Date date) {
        return format(date, F_YMD);
    }

    public static String formatYMD() {
        return format(new Date(), F_YMD);
    }

    public static String formatYMD_(Date date) {
        return format(date, F_YMD_);
    }

    public static String formatYMD_() {
        return format(new Date(), F_YMD_);
    }

    public static String formatYMDHMS(Date date) {
        return format(date, F_YMDHMS);
    }

    public static String formatYMDHMS() {
        return format(new Date(), F_YMDHMS);
    }

    public static String formatYMDHMS_(Date date) {
        return format(date, F_YMDHMS_);
    }

    public static String formatYMDHMS_() {
        return format(new Date(), F_YMDHMS_);
    }
}
