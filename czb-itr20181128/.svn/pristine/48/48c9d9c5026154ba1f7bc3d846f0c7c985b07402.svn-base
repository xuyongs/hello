package com.agent.czb.server.restful;

import com.agent.czb.common.rest.RestUtils;
import com.agent.czb.server.Constants;
import com.agent.czb.server.ParkMsgHandle;
import com.agent.czb.server.servlet.ParkMessageServlet;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author linkai
 * @since 16/8/14
 */
@RestController
@RequestMapping(value = "/")
public class PrakRestful {
    private static Logger log = LoggerFactory.getLogger(PrakRestful.class);

    @RequestMapping(method = RequestMethod.POST, value = "/park-call")
    public Object call(HttpServletRequest request) throws Exception {
        System.out.println(IOUtils.toString(request.getInputStream()));
        Map<String, String> data = RestUtils.request2map(request);
        log.info("park call params : {}", data.toString());
        // 验证请求
//        if (CheckKeyUtils.check(data)) {
//            response.getOutputStream().print(JSONUtils.objectToDateFormatJson(ResultDTO.failed("签名验证错误")));
//            return;
//        }
        String parkCode = data.get("parkCode");
        if (StringUtils.isEmpty(parkCode)) {
            return ParkMessageServlet.ResultDTO.failed("停车场编码不能为空");
        }
        String message = data.get("message");
        if (StringUtils.isEmpty(message)) {
            return ParkMessageServlet.ResultDTO.failed("消息不能为空");
        }
        // 发送请求
        ParkMsgHandle.HandlerComplete<String> send = Constants.server.send(parkCode, message);
        if (send == null) {
            return ParkMessageServlet.ResultDTO.failed("客户端未上线:" + data.toString());
        }
        String result = send.getData();
        if (result != null) {
            return ParkMessageServlet.ResultDTO.success(result);
        } else {
            return ParkMessageServlet.ResultDTO.failed("请求结果错误");
        }
    }
}
