package com.agent.czb.web.park.action;

import com.agent.czb.common.enums.EnumUtils;
import com.agent.czb.common.excel.ExcelUtils;
import com.agent.czb.common.rest.PageHelp;
import com.agent.czb.common.utils.DateUtils;
import com.agent.czb.core.park.enums.ParkOrderEnum;
import com.agent.czb.core.park.model.ParkOrderInfoModel;
import com.agent.czb.core.park.service.ParkOrderInfoService;
import com.agent.czb.web.system.action.BaseAction;
import com.agent.czb.web.system.bean.DataDict;
import com.agent.czb.web.system.utils.HtmlUtil;
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
 * 停车场订单信息控制层
 */
@Controller
@RequestMapping("/parkOrderInfo")
public class ParkOrderInfoAction extends BaseAction {
    private final static Logger log = LoggerFactory.getLogger(ParkOrderInfoAction.class);

    @Autowired
    private ParkOrderInfoService parkOrderInfoService;

    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request) throws Exception {
        Map<String, Object> context = getRootMap();
        context.put("dataList", Collections.EMPTY_LIST);
        // 获取状态
        context.put("stateList", EnumUtils.getEnumKeyVal(ParkOrderEnum.State.class));
        // 获取类型列表
        context.put("typeList", EnumUtils.getEnumKeyVal(ParkOrderEnum.Type.class));
        return forword("park/parkOrderInfo", context);
    }

    @RequestMapping("/dataList")
    public void dataList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageHelp pageHelp = PageHelp.newPageHelp(request).page(parkOrderInfoService);
        List<ParkOrderInfoModel> list = (List<ParkOrderInfoModel>) pageHelp.getData();
        for (ParkOrderInfoModel model : list) {
            setInfo(model);
        }
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("total", pageHelp.getTotal());
        jsonMap.put("rows", pageHelp.getData());
        HtmlUtil.writerJson(response, jsonMap);
    }

    @RequestMapping("/getId")
    public void getId(Long id, HttpServletResponse response) throws Exception {
        Map<String, Object> context = getRootMap();
        ParkOrderInfoModel model = parkOrderInfoService.selectById(id);
        if (model == null) {
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        setInfo(model);
        context.put(SUCCESS, true);
        context.put("data", model);
        HtmlUtil.writerJson(response, context);
    }

    public void setInfo(ParkOrderInfoModel model) {
        model.setTypeStr(ParkOrderEnum.Type.getDesc(model.getType()));
        model.setStateStr(ParkOrderEnum.State.getDesc(model.getState()));
        if (model.getTotalPrice()!=null&&model.getTotalPrice()!=0){
            model.setTotalPriceDob((double)(model.getTotalPrice()/100d));
        }
    }
    
    @RequestMapping("/dataToExcel")
	public void dataToExcel(ParkOrderInfoModel model, HttpServletRequest request) throws Exception {
		List<ParkOrderInfoModel> datas = parkOrderInfoService.selectDataToExcel(model);
		for (ParkOrderInfoModel ParkOrderInfoModel : datas) {
			setInfo(ParkOrderInfoModel);
		}
		if (datas.size() > 60000) {
			sendSuccessMessage(response, "导出数据量过大！建议减小查询范围。");
		}
		toExcel(datas);
		sendSuccessMessage(response, "导出成功！");
	}

	public void toExcel(List<ParkOrderInfoModel> datas) {
		ArrayList<String[]> headers = new ArrayList<>();
		headers.add(new String[] { "id", "ID" });
		headers.add(new String[] { "userId", "用户ID" });
		headers.add(new String[] { "userName", "用户姓名" });
		headers.add(new String[] { "parkCode", "停车场编码" });
		headers.add(new String[] { "plateNum", "车牌号码" });
		headers.add(new String[] { "totalPriceDob", "金额" });
		headers.add(new String[] { "typeStr", "订单类型" });
		headers.add(new String[] { "stateStr", "状态" });
		headers.add(new String[] { "refId", "关联标识" });
		headers.add(new String[] { "createTime", "创建时间" });
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
			os = new FileOutputStream(dirPath+"/订单管理" + DateUtils.F_YMDHMS.format(new Date()) + ".xls");
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
    public void add(ParkOrderInfoModel model, HttpServletResponse response) throws Exception {
        model.setCreateTime(new Date());
        parkOrderInfoService.insert(model);
        sendSuccessMessage(response, "保存成功");
    }

    @RequestMapping("/save")
    public void save(ParkOrderInfoModel model, HttpServletResponse response) throws Exception {
        if (model.getId() != null) {
            model.setUpdateTime(new Date());
            parkOrderInfoService.updateBySelective(model);
        } else {
            model.setCreateTime(new Date());
            parkOrderInfoService.insert(model);
        }
        sendSuccessMessage(response, "保存成功");
    }

    @RequestMapping("/delete")
    public void delete(Long[] id, HttpServletResponse response) throws Exception {
        parkOrderInfoService.delete(id);
        sendSuccessMessage(response, "删除成功");
    }
}
