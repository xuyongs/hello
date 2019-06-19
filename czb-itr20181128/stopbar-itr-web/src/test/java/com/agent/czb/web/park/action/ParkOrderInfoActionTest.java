package com.agent.czb.web.park.action;

import com.agent.czb.common.enums.EnumUtils;
import com.agent.czb.core.park.enums.ParkOrderEnum;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/1/22.
 */
public class ParkOrderInfoActionTest {

    @Test
    public void testList() throws Exception {
        List<Map<String, String>> enumKeyVal = EnumUtils.getEnumKeyVal(ParkOrderEnum.State.class);
        for (Map<String, String> stringStringMap : enumKeyVal) {
            System.out.println(stringStringMap.toString());
        }
    }

    @Test
    public void testDataList() throws Exception {

    }
}