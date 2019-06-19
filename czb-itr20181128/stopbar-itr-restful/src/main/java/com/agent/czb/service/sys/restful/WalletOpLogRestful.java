package com.agent.czb.service.sys.restful;

import com.agent.czb.common.rest.PageHelp;
import com.agent.czb.common.rest.ResultHelp;
import com.agent.czb.common.rest.restful.BasisRestful;
import com.agent.czb.core.sys.enums.WalletInfoEnums;
import com.agent.czb.core.sys.model.UserInfoModel;
import com.agent.czb.core.sys.model.WalletOpLogModel;
import com.agent.czb.core.sys.service.UserInfoService;
import com.agent.czb.core.sys.service.WalletOpLogService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 钱包操作日志Restful接口
 */
@RestController
@RequestMapping("services/walletOpLog")
public class WalletOpLogRestful extends BasisRestful {
    private static Logger log = LoggerFactory.getLogger(WalletOpLogRestful.class);

    @Autowired
    private WalletOpLogService walletOpLogService;

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/pageList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp pageList(HttpServletRequest request) {
        PageHelp pageHelp = PageHelp.newPageHelp(request);
        pageHelp.page(walletOpLogService);
        List<WalletOpLogModel> list= (List<WalletOpLogModel>) pageHelp.getData();
        for (WalletOpLogModel model : list) {
            setInfo(model);
        }
        return ResultHelp.newResult(pageHelp);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp get(Long id) {
        WalletOpLogModel model = walletOpLogService.selectById(id);
        setInfo(model);
        return ResultHelp.newResult(model);
    }

    public void setInfo(WalletOpLogModel model) {
        model.setUserName(userInfoService.getUserName(model.getUserId()));
        if (StringUtils.isNoneEmpty(model.getOpType())) {
            model.setOpTypeStr(WalletInfoEnums.OpType.getDesc(model.getOpType()));
        }
        if (StringUtils.isNoneEmpty(model.getType())) {
            model.setTypeStr(WalletInfoEnums.Type.getDesc(model.getType()));
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp add(WalletOpLogModel model) {
        int rows = walletOpLogService.insert(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp edit(WalletOpLogModel model) {
        int rows = walletOpLogService.updateBySelective(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp del(Long id) {
        int rows = walletOpLogService.delete(id);
        return ResultHelp.newResult(rows);
    }
}
