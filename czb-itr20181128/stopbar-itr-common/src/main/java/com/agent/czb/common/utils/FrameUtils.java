package com.agent.czb.common.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.time.*;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author linkai
 */
public class FrameUtils {
    private static final AtomicLong seq = new AtomicLong();
    public static String generateSeq() {
        if (seq.longValue() > 900) {
            seq.set(0);
        }
        StringBuilder sb = new StringBuilder(DateUtils.format(new Date(), DateUtils.F_YMDHMS2));
        String s = String.valueOf(seq.incrementAndGet());
        for (int i = 0; i < (3 - s.length()); i++) {
            sb.append("0");
        }
        sb.append(s);
        return sb.toString();
    }

    public static Long generatePayId() {
        return Long.valueOf(generateSeq());
    }

    public static Map<String, Object> newHashMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    @SuppressWarnings("rawtypes")
    public static Map newHashMap(Object... args) {
        HashMap<Object, Object> paramMap = new HashMap<>();
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                paramMap.put(args[i], args[++i]);
            }
        }
        return paramMap;
    }

    public static Map<String, String> newMap(String... strs) {
        if (strs.length == 0) {
            return new HashMap<>();
        }
        if (strs.length % 2 != 0) {
            throw new IllegalArgumentException("parameter must be a multiple of 2");
        }
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < strs.length; i += 2) {
            map.put(strs[i], strs[i + 1]);
        }
        return map;
    }

    public static Map<String, String> obj2map(Object obj) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return BeanUtils.describe(obj);
    }

    public static Map<String, Object> obj2map2(Object obj) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        Map<String, Object> map = new HashMap<>();
        PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : properties) {
            String name = property.getName();
            if (name.equals("class")) {
                continue;
            }
            Method method = property.getReadMethod();
            method.setAccessible(true);
            Object retVal = method.invoke(obj);
            if (retVal != null) {
                map.put(property.getName(), retVal);
            }
        }
        return map;
    }

    public static <T> T map2obj(Map<String, Object> map, Class<T> obj) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        T t = obj.newInstance();
        BeanUtils.populate(t, map);
        return t;
    }

    public static AtomicInteger FILE_SQL = new AtomicInteger();

    public static long generateFileSeq() {
        long l = System.currentTimeMillis();
        int i = FILE_SQL.incrementAndGet();
        if (i > 800) {
            FILE_SQL.set(1);
        }
        return l * 1000 + i;
    }

    public static long getFileSeq(String fileUrl) {
        return Long.parseLong(fileUrl.substring(fileUrl.lastIndexOf("/") + 1, fileUrl.indexOf(".")));
    }

    public static int newVersion(int version) {
        if (version > 9999) {
            version = 1;
        } else {
            version++;
        }
        return version;
    }

    /**
     * 新建昵称
     */
    public static String newNickName(String userId) {
        int num = 5 - userId.length();
        for (int i = 0; i < num; i++) {
            userId = "0" + userId;
        }
        return "S" + userId;
    }

    /**
     * 元转分
     */
    public static long yuan2fen(String prices) {
        if (prices == null) {
            return 0;
        }
        Double aDouble = Double.valueOf(prices);
        aDouble = aDouble * 100;
        return aDouble.longValue();
    }

    /**
     * 分转元
     */
    public static double fen2yuan(long prices) {
        if (prices == 0) {
            return 0;
        }
        double aDouble = (double) prices;
        return aDouble / 100;
    }
}
