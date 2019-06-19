package com.agent.czb.service.sys.restful;

import com.agent.czb.common.rest.PageHelp;
import com.agent.czb.common.rest.ResultHelp;
import com.agent.czb.common.rest.restful.BasisRestful;
import com.agent.czb.core.sys.enums.FileInfoEnums;
import com.agent.czb.core.sys.model.FileUpdateInfoModel;
import com.agent.czb.core.sys.service.FileUpdateInfoService;
import com.agent.czb.service.utils.URLUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 文件上传Restful接口
 */
@RestController
@RequestMapping("services/fileUpdateInfo")
public class FileUpdateInfoRestful extends BasisRestful {
    private static Logger log = LoggerFactory.getLogger(FileUpdateInfoRestful.class);

    @Autowired
    private FileUpdateInfoService fileUpdateInfoService;

    @RequestMapping(value = "/pageList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp pageList(HttpServletRequest request) {
        PageHelp pageHelp = PageHelp.newPageHelp(request);
        pageHelp.page(fileUpdateInfoService);
        List<FileUpdateInfoModel> list= (List<FileUpdateInfoModel>) pageHelp.getData();
        for (FileUpdateInfoModel model : list) {
            setInfo(model);
        }
        return ResultHelp.newResult(pageHelp);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp get(Long id) {
        FileUpdateInfoModel model = fileUpdateInfoService.selectById(id);
        setInfo(model);
        return ResultHelp.newResult(model);
    }

    public void setInfo(FileUpdateInfoModel model) {

    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp add(FileUpdateInfoModel model) {
        int rows = fileUpdateInfoService.insert(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/saveImg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Object saveImg(Long userId, @RequestParam MultipartFile[] files, @RequestParam MultipartFile file, String reType) {
        return saveFile(userId, files, file, reType, FileInfoEnums.Type.img.name());
    }

    @RequestMapping(value = "/saveFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Object saveFile(Long userId, @RequestParam MultipartFile[] files, @RequestParam MultipartFile file, String reType) {
        return saveFile(userId, files, file, reType, FileInfoEnums.Type.file.name());
    }

    public Object saveFile(Long userId, @RequestParam MultipartFile[] files, @RequestParam MultipartFile file, String reType, String type) {
        if (userId == null || userId == 0) {
            return ResultHelp.newFialResult("用户ID不能为空");
        }
        if (file == null || file.isEmpty()) {
            if (files == null || files.length == 0) {
                return ResultHelp.newFialResult("未上传任何文件");
            }
        } else {
            // 如果 file 不为空，则用file替换掉files
            files = new MultipartFile[1];
            files[0] = file;
        }
        if (StringUtils.isEmpty(reType)) {
            return ResultHelp.newFialResult("类型不能为空");
        }
        FileInfoEnums.RvType rvType = FileInfoEnums.RvType.valueOf(reType);
        if (rvType == null) {
            return ResultHelp.newFialResult("类型不对");
        }
        List<String> fileNames = fileUpdateInfoService.saveFile(userId, files, URLUtils.get(URLUtils.FILES_PATH), reType, type);
        return ResultHelp.newResult(fileNames);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp edit(FileUpdateInfoModel model) {
        int rows = fileUpdateInfoService.updateBySelective(model);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp del(Long id) {
        int rows = fileUpdateInfoService.delete(id);
        return ResultHelp.newResult(rows);
    }
}
