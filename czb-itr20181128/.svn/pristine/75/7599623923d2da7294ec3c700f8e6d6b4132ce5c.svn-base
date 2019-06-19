package com.agent.czb.web.sys.action;

import java.util.*;

import com.agent.czb.common.rest.PageHelp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.agent.czb.core.sys.enums.FileInfoEnums;
import com.agent.czb.core.sys.model.FileUpdateInfoModel;
import com.agent.czb.core.sys.service.FileUpdateInfoService;
import com.agent.czb.web.system.bean.SysUser;
import com.agent.czb.web.system.utils.SessionUtils;
import com.agent.czb.web.system.utils.URLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.agent.czb.core.sys.model.AdvertInfoModel;
import com.agent.czb.core.sys.service.AdvertInfoService;
import com.agent.czb.web.system.action.BaseAction;
import com.agent.czb.web.system.utils.HtmlUtil;

/**
 * 广告信息控制层
 */
@Controller
@RequestMapping("/advertInfo")
public class AdvertInfoAction extends BaseAction {
    private final static Logger log = LoggerFactory.getLogger(AdvertInfoAction.class);

    @Autowired
    private AdvertInfoService advertInfoService;

    @Autowired
    private FileUpdateInfoService fileUpdateInfoService;

    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request) throws Exception{
        Map<String, Object> context = getRootMap();
        context.put("dataList", Collections.EMPTY_LIST);
        return forword("sys/advertInfo", context);
    }

    @RequestMapping("/dataList")
    public void dataList(HttpServletRequest request, HttpServletResponse response) throws Exception{
        PageHelp pageHelp = PageHelp.newPageHelp(request).page(advertInfoService);
        List<AdvertInfoModel> list= (List<AdvertInfoModel>) pageHelp.getData();
        for (AdvertInfoModel model : list) {
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
        AdvertInfoModel model = advertInfoService.selectById(id);
        if(model == null){
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        setInfo(model);
        context.put(SUCCESS, true);
        context.put("data", model);
        HtmlUtil.writerJson(response, context);
    }

    public void setInfo(AdvertInfoModel model) {

    }

    @RequestMapping("/add")
    public void add(AdvertInfoModel model, HttpServletResponse response) throws Exception{
        advertInfoService.insert(model);
        sendSuccessMessage(response, "保存成功");
    }

    @RequestMapping("/save")
    public void save(AdvertInfoModel model, HttpServletResponse response) throws Exception{
        if (model.getId() == null) {
            SysUser user = (SysUser) request.getSession(true).getAttribute(SessionUtils.SESSION_USER);
            if (user != null) {
                model.setUserId(Long.parseLong(user.getId().toString()));
            }
            model.setCreateTime(new Date());
            model.setUpdateTime(new Date());
            advertInfoService.insert(model);
        } else {
            model.setUpdateTime(new Date());
            advertInfoService.updateBySelective(model);
        }
        sendSuccessMessage(response, "保存成功");
    }

    @RequestMapping("/saveFile")
    public void saveFile(AdvertInfoModel model, @RequestParam MultipartFile imgFile, HttpServletRequest request, HttpServletResponse response) throws Exception{
        if (imgFile.isEmpty()) {
            sendFailureMessage(response, "没有找到对应的记录!");
            return;
        }
        SysUser user = (SysUser) request.getSession(true).getAttribute(SessionUtils.SESSION_USER);
        if (user != null) {
            model.setUserId(Long.parseLong(user.getId().toString()));
        }
        // 保存文件
        List<String> strings = fileUpdateInfoService.saveFile(model.getUserId(),
                new MultipartFile[]{imgFile},
                URLUtils.get("file.path"),
                FileInfoEnums.RvType.OTHER.name(),
                FileInfoEnums.Type.img.name());

        model.setImgUrl(strings.get(0));
        if (model.getId() == null) {
            model.setCreateTime(new Date());
            model.setUpdateTime(new Date());
            advertInfoService.insert(model);
        } else {
            model.setUpdateTime(new Date());
            advertInfoService.updateBySelective(model);
        }
        sendSaveFileSuccessMessage(response, "保存成功");
    }

    @RequestMapping("/delete")
    public void delete(Long[] id, HttpServletResponse response) throws Exception{
        advertInfoService.delete(id);
        sendSuccessMessage(response, "删除成功");
    }
}
