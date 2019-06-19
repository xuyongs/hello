package com.agent.czb.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Properties;

/**
 * @author linkai
 * @since 16/8/10
 */
public class PropertiesUtils {
    private static final String suffix = ".properties";

    public static Properties load(File file) {
        Properties properties = new Properties();
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
            properties.load(is);
        } catch (IOException ignored) {
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {
                }
            }
        }
        return properties;
    }

    public static Properties load(String p) {
        if (!p.endsWith(suffix)) {
            p = p + suffix;
        }
        Properties properties = new Properties();
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(p);
            properties.load(is);
        } catch (IOException ignored) {
        }
        return properties;
    }

    public static String get(Properties p, String key) {
        return get(p, key, null);
    }

    public static String get(Properties p, String key, String def) {
        if (StringUtils.isNotEmpty(def)) {
            return p.getProperty(key, def);
        }
        return p.getProperty(key);
    }

    public static Integer getInt(Properties p, String key) {
        return getInt(p, key, null);
    }

    public static Integer getInt(Properties p, String key, Integer def) {
        String s = get(p, key);
        if (StringUtils.isNotEmpty(s)) {
            return Integer.valueOf(s);
        }
        return def;
    }

    public static Long getLong(Properties p, String key) {
        return getLong(p, key, null);
    }

    public static Long getLong(Properties p, String key, Long def) {
        String s = get(p, key);
        if (StringUtils.isNotEmpty(s)) {
            return Long.valueOf(s);
        }
        return def;
    }
}
