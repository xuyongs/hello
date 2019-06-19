package com.agent.czb.service.sys.restful;

import com.agent.czb.common.rest.PageHelp;
import com.agent.czb.common.rest.RestUtils;
import com.agent.czb.common.rest.ResultHelp;
import com.agent.czb.common.rest.restful.BasisRestful;
import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.core.sys.enums.WalletInfoEnums;
import com.agent.czb.core.sys.model.PayOrderLogModel;
import com.agent.czb.core.sys.service.PayOrderLogService;
import com.agent.czb.core.sys.service.WalletInfoService;
import com.agent.czb.core.sys.model.WalletInfoModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 钱包信息Restful接口
 */
@RestController
@RequestMapping("services/walletInfo")
public class WalletInfoRestful extends BasisRestful {
    private static Logger log = LoggerFactory.getLogger(WalletInfoRestful.class);

    @Autowired
    private WalletInfoService walletInfoService;

    @Autowired
    private PayOrderLogService payOrderLogService;

    @RequestMapping(value = "/pageList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp pageList(HttpServletRequest request) {
        PageHelp pageHelp = PageHelp.newPageHelp(request);
        pageHelp.page(walletInfoService);
        List<WalletInfoModel> list = (List<WalletInfoModel>) pageHelp.getData();
        for (WalletInfoModel model : list) {
            setInfo(model);
        }
        return ResultHelp.newResult(pageHelp);
    }

    @RequestMapping(value = "/get2", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp get2(Long id) {
        WalletInfoModel model = walletInfoService.selectById(id);
        setInfo(model);
        return ResultHelp.newResult(model);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp get(String userId) {
    	if (StringUtils.isEmpty(userId)) {
            return ResultHelp.newFialResult("用户标识不能为空");
        }
        List<WalletInfoModel> list = walletInfoService.pageList(FrameUtils.newMap("userId", userId));
        if (list.isEmpty()) {
            return ResultHelp.newFialResult("用户钱包信息不存在");
        }
        return ResultHelp.newResult(list.get(0));
    }

    public void setInfo(WalletInfoModel model) {

    }

    /**
     * 手动充值
     */
    @RequestMapping(value = "/addMoney", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp addMoney(Long userId, Long amount) {
        if (userId == null || userId < 1) {
            return ResultHelp.newFialResult("用户ID不能为空");
        }
        if (amount == null || amount < 1) {
            return ResultHelp.newFialResult("添加金额不能为空");
        }
        long i = payOrderLogService.sdRecharge(userId, amount);
        if (i != 1) {
            return ResultHelp.newFialResult("添加金额失败");
        }
        return ResultHelp.newResult(i);
    }

/*    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp edit(WalletInfoModel model) {
        int rows = walletInfoService.updateBySelective(model);
        return ResultHelp.newResult(rows);
    }*/

/*    @RequestMapping(value = "/del", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp del(Long id) {
        int rows = walletInfoService.delete(id);
        return ResultHelp.newResult(rows);
    }*/
}
