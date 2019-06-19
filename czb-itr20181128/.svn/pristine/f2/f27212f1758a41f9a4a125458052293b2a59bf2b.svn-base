package com.agent.czb.service.payback.restful;

import com.agent.czb.common.http.HttpClientUtils;
import com.agent.czb.common.json.JSONUtils;
import org.apache.commons.httpclient.HttpClient;
import org.junit.Test;

import javax.servlet.http.HttpServlet;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author linkai
 * @since 16/9/8
 */
public class PayBackRestfulTest {

    @Test
    public void notify2() throws Exception {
        String url = "http://localhost:8080/stopbar-itr-restful/services/payBack/notify";
        String str = "{\"buyer_id\":\"2088002624372996\",\"trade_no\":\"2016090821001004990242609378\",\"body\":\"购买PD_999车位\",\"use_coupon\":\"N\",\"notify_time\":\"2016-09-08 10:37:45\",\"subject\":\"PD_999\",\"sign_type\":\"RSA\",\"is_total_fee_adjust\":\"N\",\"notify_type\":\"trade_status_sync\",\"out_trade_no\":\"160908101315015\",\"gmt_payment\":\"2016-09-08 10:13:32\",\"trade_status\":\"TRADE_SUCCESS\",\"discount\":\"0.00\",\"sign\":\"FAJWGciwZ4B86FhinkwU7Sn1XNz7SRFtovPvfgrz0YgtECp+ZAPo7MKpckhiiZeZ6GSKDKnBz3e9qWK+WHKu9Bz4DUoAh2RRSb0ioVmogj48aZWSMvaeZOsMgiM21isNTRvaRWJAPeoQVudMW4JAHJrCh7UG4+lILPUh2aqDa0I=\",\"buyer_email\":\"26560041@qq.com\",\"gmt_create\":\"2016-09-08 10:13:32\",\"price\":\"10.00\",\"total_fee\":\"10.00\",\"quantity\":\"1\",\"seller_id\":\"2088421444446531\",\"notify_id\":\"d5e1925cffdb4795156c14a413e3aednn2\",\"seller_email\":\"stopbar@stopbar.com.cn\",\"payment_type\":\"1\"}";
        Map data = JSONUtils.jsonToMap(str);
        String s = HttpClientUtils.post(url, data);
        System.out.println(s);
    }

    @Test
    public void returnInfo() throws Exception {

    }

}