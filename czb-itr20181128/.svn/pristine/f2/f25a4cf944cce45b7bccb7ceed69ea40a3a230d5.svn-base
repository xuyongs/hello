package com.agent.czb.service.sys.restful;

import com.agent.czb.common.rest.PageHelp;
import com.agent.czb.common.rest.RestUtils;
import com.agent.czb.common.rest.ResultHelp;
import com.agent.czb.common.rest.restful.BasisRestful;
import com.agent.czb.core.sys.model.UserInfoModel;
import com.agent.czb.core.sys.service.NoticeMsgService;
import com.agent.czb.core.sys.model.NoticeMsgModel;
import com.agent.czb.core.sys.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 公告消息Restful接口
 */
@RestController
@RequestMapping("services/noticeMsg")
public class NoticeMsgRestful extends BasisRestful {
    private static Logger log = LoggerFactory.getLogger(NoticeMsgRestful.class);

    @Autowired
    private NoticeMsgService noticeMsgService;

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/pageList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp pageList(HttpServletRequest request) {
        PageHelp pageHelp = PageHelp.newPageHelp(request);
        pageHelp.page(noticeMsgService);
        List<NoticeMsgModel> list= (List<NoticeMsgModel>) pageHelp.getData();
        for (NoticeMsgModel model : list) {
            setInfo(model);
        }
        return ResultHelp.newResult(pageHelp);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp get(Long id) {
        NoticeMsgModel model = noticeMsgService.selectById(id);
        setInfo(model);
        return ResultHelp.newResult(model);
    }

    public void setInfo(NoticeMsgModel model) {
        model.setUserName(userInfoService.getUserName(model.getUserId()));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp add(NoticeMsgModel model) {
        if (model.getUserId() == null) {
            return ResultHelp.newFialResult("用户标识不能为空");
        }
        model.setCreateTime(new Date());
        int rows = noticeMsgService.insert(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/getMsg", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultHelp getMsg(Long msgId, Integer limit) {
        if (limit == null || limit == 0) {
            limit = 30;
        }
        List<NoticeMsgModel> list = noticeMsgService.getMsg(msgId, limit);
        for (NoticeMsgModel model : list) {
            setInfo(model);
        }
        return ResultHelp.newResult(list);
    }

    @RequestMapping(value = "/getMsgList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultHelp getMsgList(Long msgId, Integer limit) {
        if (limit == null || limit == 0) {
            limit = 30;
        }
        List<NoticeMsgModel> list = noticeMsgService.getMsg(msgId, limit);
        for (NoticeMsgModel model : list) {
            model.setContent(null);
            setInfo(model);
        }
        return ResultHelp.newResult(list);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp edit(NoticeMsgModel model) {
        int rows = noticeMsgService.updateBySelective(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp del(Long id) {
        int rows = noticeMsgService.delete(id);
        return ResultHelp.newResult(rows);
    }
}
