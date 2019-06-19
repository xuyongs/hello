package com.agent.czb.server.servlet;

import com.agent.czb.common.json.JSONUtils;
import com.agent.czb.common.park.call.CallRemoteDllUtils;
import com.agent.czb.common.rest.RestUtils;
import com.agent.czb.common.utils.CheckKeyUtils;
import com.agent.czb.server.Constants;
import com.agent.czb.server.ParkMsgHandle;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author linkai
 * @since 16/8/10
 */
public class ParkMessageServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Map<String, String> data = RestUtils.request2map(request);
        // 验证请求
//        if (CheckKeyUtils.check(data)) {
//            response.getOutputStream().print(JSONUtils.objectToDateFormatJson(ResultDTO.failed("签名验证错误")));
//            return;
//        }
        String parkCode = data.get("parkCode");
        String message = data.get("message");
        // 发送请求
        ParkMsgHandle.HandlerComplete<String> send = Constants.server.send(parkCode, message);
        if (send == null) {
            response.getOutputStream().print(JSONUtils.objectToDateFormatJson(ResultDTO.failed("客户端未上线:" + data.toString())));
            response.getOutputStream().flush();
            response.getOutputStream().close();
            return;
        }
        String result = send.getData();
        if (result != null) {
            response.getOutputStream().print(JSONUtils.objectToDateFormatJson(ResultDTO.success(result)));
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } else {
            response.getOutputStream().print(JSONUtils.objectToDateFormatJson(ResultDTO.failed("请求结果错误")));
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Data
    @Accessors(chain = true)
    public static class ResultDTO {
        String status;
        String data;

        public static ResultDTO success(String data) {
            return new ResultDTO().setData(data).setStatus("1");
        }
        public static ResultDTO failed(String data) {
            return new ResultDTO().setData(data).setStatus("0");
        }
    }
}
