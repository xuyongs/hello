package com.agent.czb.service.sys.restful;

import com.agent.czb.common.rest.ResultHelp;
import com.agent.czb.common.rest.restful.BasisRestful;
import com.agent.czb.core.sys.service.UserPushMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 创建人 qany.
 * 创建时间  2016/12/1:19:03.
 * 描述：查询用户消息列表
 */
@RestController
@RequestMapping("services/userPushMessage")
public class UserPushMessageRestful  extends BasisRestful {

    @Autowired
    private UserPushMessageService userPushMessageService;


    @RequestMapping(value = "/messageByUserId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp getAllAppMenu(Long userId) {
        return ResultHelp.newResult(userPushMessageService.selectByUserId(userId));
    }

    @RequestMapping(value = "/hadRead", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp hadRead(Long messageId) {
        return ResultHelp.newResult(userPushMessageService.hadRead(messageId));
    }

    @RequestMapping(value = "/selectMessageByType", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp selectMessageByType(Long userId,Integer type) {
        return ResultHelp.newResult(userPushMessageService.selectMessageByType(userId,type));
    }

}
