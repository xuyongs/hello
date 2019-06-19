package com.agent.czb.common.park.call;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author linkai
 * @since 16/9/7
 */
public class CallRemoteDllUtilsTest {
    @Test
    public void amount() throws Exception {
        CallRemoteDllUtils.ParkAmount pd_999 = CallRemoteDllUtils.amount("PD_007", 3275L);
        System.out.println(pd_999.getPrice());
    }

}