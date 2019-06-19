package com.agent.czb.service;

import com.agent.czb.common.http.HttpClientUtils;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.FormBodyPartBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/12.
 */
public class TestBase {
    protected static String LOCAL_SERVER = "http://localhost:8081/stopbar-itr-restful/";
    protected static String REMOTE_SERVER = "http://139.196.174.197:8080/stopbar-itr-restful/";

    protected String callHttp(String serverUrl, String service, Map<String, String> map, boolean isPost) {
        String url = serverUrl + service;
        String post;
        if (isPost) {
            post = HttpClientUtils.post(url, map);
        } else {
            post = HttpClientUtils.get(url, map);
        }
        return post;
    }

    protected String postMultipart(String serverUrl, String service, List<FormBodyPart> params) {
        String url = serverUrl + service;
        return HttpClientUtils.postMultipart(url, params);
    }

    protected FormBodyPart addMultPart(String name, String str) throws UnsupportedEncodingException {
        return FormBodyPartBuilder.create(name, new StringBody(str, Charset.forName("UTF-8"))).build();    }

    protected FormBodyPart addMultPart(String name, File file) {
        return FormBodyPartBuilder.create(name, new FileBody(file)).build();
    }

    protected String callHttp(String serverUrl, String service, Map<String, String> map) {
        return callHttp(serverUrl, service, map, false);
    }

    protected String localGet(String service, Map<String, String> map) {
        return callHttp(LOCAL_SERVER, service, map);
    }

    protected String localPost(String service, Map<String, String> map) {
        return callHttp(LOCAL_SERVER, service, map, true);
    }

    protected String remoteGet(String service, Map<String, String> map) {
        return callHttp(REMOTE_SERVER, service, map);
    }


    protected String remotePost(String service, Map<String, String> map) {
        return callHttp(REMOTE_SERVER, service, map, true);
    }

}
