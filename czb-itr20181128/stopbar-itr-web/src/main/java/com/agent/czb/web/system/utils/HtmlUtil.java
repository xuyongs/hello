package com.agent.czb.web.system.utils;

import com.agent.czb.common.json.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <b>功能：</b>详细的功能描述<br>
 */
public class HtmlUtil {

    /**
     * 输出json格式
     *
     * @param response
     * @param jsonStr
     * @throws Exception
     */
    public static void writerJson(HttpServletResponse response, String jsonStr) {
        writer(response, jsonStr);
    }

    public static void writerJson(HttpServletResponse response, Object object) {
        response.setContentType("application/json");
        writer(response, JSONUtils.objectToDateFormatJson(object));
    }

    /**
     * 输出HTML代码
     *
     * @param response
     * @param htmlStr
     * @throws Exception
     */
    public static void writerHtml(HttpServletResponse response, String htmlStr) {
        writer(response, htmlStr);
    }

    public static void writer(HttpServletResponse response, String str) {
        try {
            //设置页面不缓存
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(str);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
