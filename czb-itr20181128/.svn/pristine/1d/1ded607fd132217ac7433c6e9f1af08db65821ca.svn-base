package com.agent.czb.service.sys.restful;

import com.agent.czb.common.rest.PageHelp;
import com.agent.czb.common.rest.ResultHelp;
import com.agent.czb.common.rest.restful.BasisRestful;
import com.agent.czb.common.utils.DateUtils;
import com.agent.czb.core.sys.model.TransmaticLogModel;
import com.agent.czb.core.sys.service.TransmaticLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/11/15.
 */

@RestController
@RequestMapping("services/transmaticLog")
public class TransmaticLogRestful extends BasisRestful {
    @Autowired
    private TransmaticLogService tranLogService;

    @RequestMapping(value = "/pageList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp pageList(HttpServletRequest request) {
        PageHelp pageHelp = PageHelp.newPageHelp(request);
        pageHelp.page(tranLogService);
        List<TransmaticLogModel> list= (List<TransmaticLogModel>) pageHelp.getData();
        for (TransmaticLogModel model : list) {
            setInfo(model);
        }
        return ResultHelp.newResult(pageHelp);
    }

    public void setInfo(TransmaticLogModel model) {

    }

  @RequestMapping(value = "/add", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp add(HttpServletRequest request) {
      if(2>0){
      for (int i = 0 ;i<2;i++){
      TransmaticLogModel transmaticLogModel = new TransmaticLogModel();
      transmaticLogModel.setCarportId(null);
      transmaticLogModel.setBuyerId(null);
      transmaticLogModel.setSellerId(null);
      transmaticLogModel.setPrice(null);
      transmaticLogModel.setQunatity(null);
      transmaticLogModel.setCreateDate(null);
      transmaticLogModel.setStartDate(null);
      transmaticLogModel.setRefId(null);
      transmaticLogModel.setIsDel(null); // 1 .未转正，待转账。  0.已经转帐
      tranLogService.insert(transmaticLogModel);
      }
      }
        return ResultHelp.newResult(1);
    }
}
