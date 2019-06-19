package com.agent.czb.common.image;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author linkai
 */
public class ImageUtils {
    private static Logger log = Logger.getLogger(ImageUtils.class);

    public final static long FILE_MAX = 50485760; // 文件最大值

    /**
     * 将base64码转换为文件
     *
     * @return 0：文件过大；1：成功；2：失败；
     */
    public static int base642image(String imagebase, File file, long fileSize) {
        byte[] data = Base64.decodeBase64(imagebase);
        if (data.length > fileSize) {
            return 0;
        }
        FileImageOutputStream imageOutput = null;
        try {
            imageOutput = new FileImageOutputStream(file);
            imageOutput.write(data);
            imageOutput.flush();
        } catch (Exception e) {
            log.error("上传文件失败，文件名为：" + file.getAbsolutePath(), e);
            return 2;
        } finally {
            if (imageOutput != null) {
                try {
                    imageOutput.close();
                } catch (IOException ignored) {
                }
            }
        }
        return 1;
    }

    public static String getFileName(String fileName) {
        int indexOf = fileName.lastIndexOf(".");
        String f;
        if (indexOf != -1) {
            f = fileName.substring(indexOf);
        } else {
            f = ".jpg";
        }
        return getCode() + f;
    }

    private static String toDay;
    private static int count;

    private static String getCode() {
        String jobCode;
        int num;
        SimpleDateFormat sdfShort = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = sdfShort.format(new Date());
        synchronized (ImageUtils.class) {
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
