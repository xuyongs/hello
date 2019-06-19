package com.agent.czb.common.enums;

import com.agent.czb.common.utils.FrameUtils;
import org.apache.commons.beanutils.PropertyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author linkai
 */
public class EnumUtils {

    /**
     * 获取
     */
    public static List<Map<String, String>> getEnumKeyVal(Class c) {
        List<Map<String, String>> list = new ArrayList<>();
        if (c.isEnum()) {
            for (Object obj : c.getEnumConstants()) {
                if (obj instanceof BaseEnum) {
                    BaseEnum value = (BaseEnum) obj;
                    list.add(FrameUtils.newMap("key", value.toValue(), "val", value.toDesc()));
                }
            }
        }
        return list;
    }

    public static <T> T  render(T obj, Map<String, Class<? extends Enum>> enumMap) {
        for (String key : enumMap.keySet()) {
            if (obj instanceof Map) {
                Map rowData= (Map) obj;
                if (rowData.containsKey(key)) {
                    Object state = rowData.get(key);
                    String desc = getDesc(key, state, enumMap);
                    rowData.put(key, desc);
                }
            } else {
                try {
                    Object state = PropertyUtils.getProperty(obj, key);
                    String desc = getDesc(key, state, enumMap);
                    PropertyUtils.setProperty(obj, key, desc);
                } catch (Exception ignored) {
                }
            }
        }
        return obj;
    }

    private static String getDesc(String key, Object state, Map<String, Class<? extends Enum>> enumMap) {
        if (state == null) {
            return "";
        }
        return getDesc(key, state.toString(), enumMap);
    }

    private  static String getDesc(String key, String state, Map<String, Class<? extends Enum>> enumMap) {
        if (state == null) {
            return "";
        }
        String desc;
        try {
            desc = Enum.valueOf(enumMap.get(key), state).toString();
        } catch (Exception e) {
            desc = state;
        }
        return desc;
    }
}
