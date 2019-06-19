package com.agent.czb.web.sys.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.agent.czb.common.excel.ExcelUtils;
import com.agent.czb.common.rest.PageHelp;
import com.agent.czb.common.utils.DateUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;

import com.agent.czb.core.sys.enums.PayOrderEnums;
import com.agent.czb.core.sys.model.UserInfoModel;
import com.agent.czb.core.sys.service.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.agent.czb.core.sys.model.PayOrderLogModel;
import com.agent.czb.core.sys.service.PayOrderLogService;
import com.agent.czb.web.system.action.BaseAction;
import com.agent.czb.web.system.utils.HtmlUtil;
import com.google.gson.Gson;

/**
 * 支付操作日志控制层
 */
@Controller
@RequestMapping("/payOrderLog")
public class PayOrderLogAction extends BaseAction {
    private final static Logger log = LoggerFactory.getLogger(PayOrderLogAction.class);

    @Autowired
    private PayOrderLogService payOrderLogService;

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request) throws Exception{
        Map<String, Object> context = getRootMap();
        context.put("dataList", Collections.EMPTY_LIST);
        return forword("sys/payOrderLog", context);
    }

    @RequestMapping("/dataList")
    public void dataList(HttpServletRequest request, HttpServletResponse response) throws Exception{
        PageHelp pageHelp = PageHelp.newPageHelp(request).page(payOrderLogService);
        List<PayOrderLogModel> list= (List<PayOrderLogModel>) pageHelp.getData();
        for (PayOrderLogModel model : list) {
            setInfo(model);
        }
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("total", pageHelp.getTotal());
        jsonMap.put("rows", pageHelp.getData());
        HtmlUtil.writerJson(response, jsonMap);
    }

    @RequestMapping("/getId")
    public void getId(Long payId, HttpServletResponse response) throws Exception{
        Map<String,Object> context = getRootMap();
        PayOrderLogModel model = payOrderLogService.selectById(payId);
        if(model == null){
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        setInfo(model);
        context.put(SUCCESS, true);
        context.put("data", model);
        HtmlUtil.writerJson(response, context);
    }

    public void setInfo(PayOrderLogModel model) {
        if (StringUtils.isNoneEmpty(model.getPayState())) {
            model.setPayStateStr(PayOrderEnums.State.getDesc(model.getPayState()));
        }
        if (StringUtils.isNoneEmpty(model.getPayType())) {
            String desc = PayOrderEnums.Type.getDesc(model.getPayType());
            if (desc == null) {
                model.setPayTypeStr(model.getPayType());
            } else {
                model.setPayTypeStr(desc);
            }
        }
        if (StringUtils.isNoneEmpty(model.getRefType())) {
            model.setRefTypeStr(PayOrderEnums.RefType.getDesc(model.getRefType()));
        }
        model.setUserName(userInfoService.getUserName(model.getUserId()));
    }

    @RequestMapping("/add")
    public void add(PayOrderLogModel model, HttpServletResponse response) throws Exception{
        payOrderLogService.insert(model);
        sendSuccessMessage(response, "保存成功");
    }

    @RequestMapping("/save")
    public void save(PayOrderLogModel model, HttpServletResponse response) throws Exception{
        if (model.getPayId() == null) {
            payOrderLogService.insert(model);
        } else {
            payOrderLogService.updateBySelective(model);
        }
        sendSuccessMessage(response, "保存成功");
    }

    @RequestMapping("/delete")
    public void delete(Long[] payId, HttpServletResponse response) throws Exception{
        payOrderLogService.delete(payId);
        sendSuccessMessage(response, "删除成功");
    }
    
    @RequestMapping("/dataToExcel")
	public void dataToExcel(PayOrderLogModel model, HttpServletRequest request) throws Exception {
		List<PayOrderLogModel> datas = payOrderLogService.selectDataToExcel(model);
		for (PayOrderLogModel payOrderLogModel : datas) {
			setInfo(payOrderLogModel);
		}
		if (datas.size() > 60000) {
			sendSuccessMessage(response, "导出数据量过大！建议减小查询范围。");
		}
		toExcel(datas);
		sendSuccessMessage(response, "导出成功！");
	}

	public void toExcel(List<PayOrderLogModel> datas) {
		ArrayList<String[]> headers = new ArrayList<>();
		headers.add(new String[] { "payId", "ID" });
		headers.add(new String[] { "payTypeStr", "支付类型" });
		headers.add(new String[] { "payStateStr", "支付状态" });
		headers.add(new String[] { "payNo", "支付流水号" });
		headers.add(new String[] { "refTypeStr", "商品类型" });
		headers.add(new String[] { "refId", "商品标识" });
		headers.add(new String[] { "quantity", "数量" });
		headers.add(new String[] { "price", "单价" });
		headers.add(new String[] { "amount", "支付金额" });
		headers.add(new String[] { "userName", "用户标识" });
		headers.add(new String[] { "createTime", "创建时间" });
		headers.add(new String[] { "payTime", "支付时间" });
		OutputStream os = null;
		FileSystemView fsv = FileSystemView.getFileSystemView();
		File com=fsv.getHomeDirectory();    //这便是读取桌面路径的方法了
		String dirPath = com.getPath();
		//String dirPath = "E:/excleDir";
		File file = new File(dirPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			os = new FileOutputStream(dirPath+"/支付日志" + DateUtils.F_YMDHMS.format(new Date()) + ".xls");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			ExcelUtils.export(os, headers, datas);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    
}
