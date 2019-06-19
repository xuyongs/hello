package com.agent.czb.web.sys.action;

import com.agent.czb.common.excel.ExcelUtils;
import com.agent.czb.common.rest.PageHelp;
import com.agent.czb.common.utils.DateUtils;
import com.agent.czb.core.sys.enums.UserWithdrawalInfoEnums;
import com.agent.czb.core.sys.model.UserInfoModel;
import com.agent.czb.core.sys.model.UserWithdrawalInfoModel;
import com.agent.czb.core.sys.service.UserInfoService;
import com.agent.czb.core.sys.service.UserWithdrawalInfoService;
import com.agent.czb.web.system.action.BaseAction;
import com.agent.czb.web.system.utils.HtmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * 用户提现信息控制层
 */
@Controller
@RequestMapping("/userWithdrawalInfo")
public class UserWithdrawalInfoAction extends BaseAction {
    private final static Logger log = LoggerFactory.getLogger(UserWithdrawalInfoAction.class);

    @Autowired
    private UserWithdrawalInfoService userWithdrawalInfoService;
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request) throws Exception{
        Map<String, Object> context = getRootMap();
        context.put("dataList", Collections.EMPTY_LIST);
        return forword("sys/userWithdrawalInfo", context);
    }

    @RequestMapping("/dataList")
    public void dataList(HttpServletRequest request, HttpServletResponse response) throws Exception{
        PageHelp pageHelp = PageHelp.newPageHelp(request).page(userWithdrawalInfoService);
        List<UserWithdrawalInfoModel> list= (List<UserWithdrawalInfoModel>) pageHelp.getData();
        for (UserWithdrawalInfoModel model : list) {
            setInfo(model);
        }
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("total", pageHelp.getTotal());
        jsonMap.put("rows", pageHelp.getData());
        HtmlUtil.writerJson(response, jsonMap);
    }

    @RequestMapping("/getId")
    public void getId(Long id, HttpServletResponse response) throws Exception{
        Map<String,Object> context = getRootMap();
        UserWithdrawalInfoModel model = userWithdrawalInfoService.selectById(id);
        if(model == null){
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        setInfo(model);
        context.put(SUCCESS, true);
        context.put("data", model);
        HtmlUtil.writerJson(response, context);
    }

    public void setInfo(UserWithdrawalInfoModel model) {
        model.setStateStr(UserWithdrawalInfoEnums.State.getDesc(model.getState()));
        UserInfoModel userInfoModel = userInfoService.selectById(model.getUserId());
        if (userInfoModel != null) {
            model.setUserName(userInfoModel.getNickName());
        }
    }
    
    @RequestMapping("/dataToExcel")
	public void dataToExcel(UserWithdrawalInfoModel model, HttpServletRequest request) throws Exception {
		List<UserWithdrawalInfoModel> datas = userWithdrawalInfoService.selectDataToExcel(model);
		for (UserWithdrawalInfoModel UserWithdrawalInfoModel : datas) {
			setInfo(UserWithdrawalInfoModel);
		}
		if (datas.size() > 60000) {
			sendSuccessMessage(response, "导出数据量过大！建议减小查询范围。");
		}
		toExcel(datas);
		sendSuccessMessage(response, "导出成功！");
	}

	public void toExcel(List<UserWithdrawalInfoModel> datas) {
		ArrayList<String[]> headers = new ArrayList<>();
		headers.add(new String[] { "id", "ID" });
		headers.add(new String[] { "userId", "用户ID" });
		headers.add(new String[] { "userName", "用户名称" });
		headers.add(new String[] { "amount", "提现金额" });
		headers.add(new String[] { "description", "提现说明" });
		headers.add(new String[] { "stateStr", "状态" });
		headers.add(new String[] { "remark", "备注" });
		headers.add(new String[] { "createTime", "创建日期" });
		headers.add(new String[] { "updateTime", "修改时间" });
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
			os = new FileOutputStream(dirPath+"/用户提现" + DateUtils.F_YMDHMS.format(new Date()) + ".xls");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			ExcelUtils.export(os, headers, datas);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    

    @RequestMapping("/add")
    public void add(UserWithdrawalInfoModel model, HttpServletResponse response) throws Exception{
        userWithdrawalInfoService.insert(model);
        sendSuccessMessage(response, "保存成功");
    }

    /**
     * 提现
     */
    @RequestMapping("/save")
    public void save(UserWithdrawalInfoModel model, HttpServletResponse response) throws Exception{
        if(model.getId() == null){
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        if(StringUtils.isEmpty(model.getRemark())){
            sendFailureMessage(response, "提现备注不能为空");
            return;
        }
        UserWithdrawalInfoModel userWithdrawalInfo = userWithdrawalInfoService.selectById(model.getId());
        if (userWithdrawalInfo == null) {
            sendFailureMessage(response, "没有找到对应的记录!");
        }
        assert userWithdrawalInfo != null;
        if (!userWithdrawalInfo.getState().equals(UserWithdrawalInfoEnums.State.申请.toValue())) {
            sendFailureMessage(response, "该记录已被操作!");
        }
        userWithdrawalInfo.setState(UserWithdrawalInfoEnums.State.同意.toValue());
        userWithdrawalInfo.setRemark(model.getRemark());
        userWithdrawalInfo.setUpdateTime(new Date());
        userWithdrawalInfoService.update(userWithdrawalInfo);
        sendSuccessMessage(response, "保存成功");
    }

    @RequestMapping("/delete")
    public void delete(Long[] id, HttpServletResponse response) throws Exception{
        userWithdrawalInfoService.delete(id);
        sendSuccessMessage(response, "删除成功");
    }
}
