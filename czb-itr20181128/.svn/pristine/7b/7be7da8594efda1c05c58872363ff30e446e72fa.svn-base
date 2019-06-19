package com.agent.czb.service.sys.restful;

import com.agent.czb.core.sys.enums.FileInfoEnums;
import com.agent.czb.service.TestBase;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.junit.Test;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/1/12.
 */
public class FileUpdateInfoRestfulTest extends TestBase {

    @Test
    public void testPageList() throws UnsupportedEncodingException {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("fileRvtype","车位");
        map.put("fileRvid", "81");
        String s = remoteGet("services/fileUpdateInfo/pageList", map);
        System.out.println(s);
    }

    @Test
    public void testUpdateImg() throws UnsupportedEncodingException {
        File img = new File("D:\\temp\\file\\20160112105635.png");
        List<FormBodyPart> list = new ArrayList<>();
        list.add(new FormBodyPart("file", new FileBody(img)));
        list.add(new FormBodyPart("userId", new StringBody("-5", Charset.forName("UTF-8"))));
        list.add(new FormBodyPart("reType", new StringBody(FileInfoEnums.RvType.OTHER.name(), Charset.forName("UTF-8"))));
        String s = postMultipart(REMOTE_SERVER, "services/fileUpdateInfo/saveFile", list);
        System.out.println(s);
    }

    @Test
    public void testUpdateFile() throws UnsupportedEncodingException {
        File img = new File("D:\\temp\\file\\20160112105635.png");
//        File img = new File("D:\\QQ图片20151201094708.png");
        List<FormBodyPart> list = new ArrayList<>();
        list.add(new FormBodyPart("file", new FileBody(img)));
        list.add(new FormBodyPart("userId", new StringBody("-5", Charset.forName("UTF-8"))));
        list.add(new FormBodyPart("reType", new StringBody(FileInfoEnums.RvType.CARPORT.name(), Charset.forName("UTF-8"))));
        String s = postMultipart(LOCAL_SERVER, "services/fileUpdateInfo/saveFile", list);
        System.out.println(s);
    }
}