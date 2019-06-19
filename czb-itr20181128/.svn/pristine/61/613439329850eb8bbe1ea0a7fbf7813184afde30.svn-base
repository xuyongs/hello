package com.agent.czb.common.rest.restful;

import com.agent.czb.common.rest.RestUtils;
import com.agent.czb.common.spring.MyCustomDateEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class BasisRestful {

    /**
     * 请求参数格式化
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        // 日期参数格式化
        try {
            binder.registerCustomEditor(Date.class, new MyCustomDateEditor(
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
                    new SimpleDateFormat("yyyy-MM-dd")));
        } catch (Exception e) {
            binder.registerCustomEditor(Date.class, new CustomDateEditor(
                    new SimpleDateFormat("yyyy-MM-dd"), true));
        }
    }

    /**
     * 统一异常处理接口
     */
//    @ExceptionHandler
//    public Object exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
//        return ResultHelp.newFialResult(ex.getMessage());
//    }

    /**
     * request转换为map
     */
    public Map<String, String> request2map(HttpServletRequest request) {
        return RestUtils.request2map(request);
    }
}
