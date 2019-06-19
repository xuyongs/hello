package com.agent.czb.web.park.action;

import com.agent.czb.common.excel.ExcelUtils;
import com.agent.czb.common.rest.PageHelp;
import com.agent.czb.common.utils.DateUtils;
import com.agent.czb.core.park.enums.ParkGateEnum;
import com.agent.czb.core.park.enums.ParkIsVipEnum;
import com.agent.czb.core.park.enums.ParkOrderEnum;
import com.agent.czb.core.park.model.ParkGateLogModel;
import com.agent.czb.core.park.service.ParkGateLogService;
import com.agent.czb.web.system.action.BaseAction;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 停车场闸机日志控制层
 */
@Controller
@RequestMapping("/parkGateLog")
public class ParkGateLogAction extends BaseAction {
    private final static Logger log = LoggerFactory.getLogger(ParkGateLogAction.class);

    @Autowired
    private ParkGateLogService parkGateLogService;

    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request) throws Exception {
        Map<String, Object> context = getRootMap();
        context.put("dataList", Collections.EMPTY_LIST);
        return forword("park/parkGateLog", context);
    }

    @RequestMapping("/dataList")
    public void dataList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	PageHelp pageHelp = PageHelp.newPageHelp(request);
        pageHelp.params().put("pagerOrder", "order by id desc");
        pageHelp.page(parkGateLogService);
        List<ParkGateLogModel> list = (List<ParkGateLogModel>) pageHelp.getData();
        for (ParkGateLogModel model : list) {
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
        ParkGateLogModel model = parkGateLogService.selectById(id);
        if (model == null) {
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        setInfo(model);
        context.put(SUCCESS, true);
        context.put("data", model);
        HtmlUtil.writerJson(response, context);
    }

    public void setInfo(ParkGateLogModel model) {
        model.setIoStateStr(ParkGateEnum.State.getDesc(model.getIoState()));
        model.setIsVipStr(ParkIsVipEnum.State.getDesc(model.getIsVip()));
        model.setPayType(ParkOrderEnum.Type.getDesc(model.getPayType()));
        model.setCarType(ParkGateEnum.Type.getDesc(model.getCarType()));
        if (model.getPrice()!=null&&model.getPrice()!=0){
            model.setPriceDob((double)(model.getPrice()/100d));
        }
    }

    @RequestMapping("/add")
    public void add(ParkGateLogModel model, HttpServletResponse response) throws Exception {
        parkGateLogService.insert(model);
        sendSuccessMessage(response, "保存成功");
    }

    @RequestMapping("/save")
    public void save(ParkGateLogModel model, HttpServletResponse response) throws Exception {
        if (model.getId() == null) {
            parkGateLogService.insert(model);
        } else {
            parkGateLogService.updateBySelective(model);
        }
        sendSuccessMessage(response, "保存成功");
    }

    @RequestMapping("/delete")
    public void delete(Long[] id, HttpServletResponse response) throws Exception {
        parkGateLogService.delete(id);
        sendSuccessMessage(response, "删除成功");
    }
    
	@RequestMapping("/dataToExcel")
	public void dataToExcel(ParkGateLogModel model, HttpServletRequest request) throws Exception {
		List<ParkGateLogModel> datas = parkGateLogService.selectDataToExcel(model);
		for (ParkGateLogModel parkGateLogModel : datas) {
			parkGateLogModel.setCarType(ParkGateEnum.Type.getDesc(parkGateLogModel.getCarType()));
			parkGateLogModel.setPayType(ParkOrderEnum.Type.getDesc(parkGateLogModel.getPayType()));
		}
		if (datas.size() > 60000) {
			sendSuccessMessage(response, "导出数据量过大！建议减小查询范围。");
		}
		toExcel(datas);
		sendSuccessMessage(response, "导出成功！");
	}

	public void toExcel(List<ParkGateLogModel> datas) {
		ArrayList<String[]> headers = new ArrayList<>();
		headers.add(new String[] { "parkCode", "停车场编码" });
		headers.add(new String[] { "gateCode", "闸口编码" });
		headers.add(new String[] { "carNo", "停车编号" });
		headers.add(new String[] { "plateNum", "车牌号码" });
		headers.add(new String[] { "ioStateStr", "进出库状态" });
		headers.add(new String[] { "ioDate", "进出库时间" });
		headers.add(new String[] { "picture", "出入库图片名称" });
		headers.add(new String[] { "isVipStr", "是否月卡用户" });
		headers.add(new String[] { "carType", "车辆类型" });
		headers.add(new String[] { "payType", "支付类型" });
		headers.add(new String[] { "remainSpace", "剩余车位" });
		headers.add(new String[] { "totalSpace", "总车位" });
		headers.add(new String[] { "priceDob", "出场时的金额" });
		headers.add(new String[] { "orderId", "订单标识" });
		headers.add(new String[] { "createTime", "创建时间" });
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
			os = new FileOutputStream(dirPath+"/停车场出入库记录" + DateUtils.F_YMDHMS.format(new Date()) + ".xls");
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
