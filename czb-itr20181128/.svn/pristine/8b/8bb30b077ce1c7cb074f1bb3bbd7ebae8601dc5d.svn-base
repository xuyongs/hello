package com.agent.czb.web.sys.action;

import com.agent.czb.core.sys.service.UserInfoService;
import com.agent.czb.web.system.action.BaseAction;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

/**
 * 创建人:qany.
 * 创建时间: 2016/10/25:17:54.
 * 描述: 批量吧用户转为极光IM用户
 */
@Controller
@RequestMapping("/apilist/jmessageuser")
public class UserJMessageAction extends BaseAction{
    private final static Logger log = LoggerFactory.getLogger(UserInfoAction.class);

    @Autowired
    private UserInfoService userInfoService;


    /**
     * 创建人:qany.
     * 创建时间:2016/10/26:11:00.
     * 描述:替已存在的用户注册JMessage
     */
    @RequestMapping("/regiterim")
    public void regiterJMessageUser() {
        try {
            userInfoService.regiterJMessageUser();
            sendSuccessMessage(response,"转换成功");
        } catch (Exception e) {
            e.printStackTrace();
            sendFailureMessage(response,e.getMessage());
        }

    }

}
