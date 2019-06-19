package com.agent.czb.core.sys.service;

import com.agent.czb.common.image.ImageUtils;
import com.agent.czb.common.rest.service.BasisService;
import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.core.sys.enums.FileInfoEnums;
import com.agent.czb.core.sys.mapper.FileUpdateInfoMapper;
import com.agent.czb.core.sys.model.FileUpdateInfoModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 文件上传服务层
 */
@Service("fileUpdateInfoService")
public class FileUpdateInfoService extends BasisService<FileUpdateInfoMapper, FileUpdateInfoModel> {
    private static final Logger log = LoggerFactory.getLogger(FileUpdateInfoService.class);

    public List<String> saveFile(Long uerId, MultipartFile[] files, String fileDir, String reType, String type) {
        List<String> list = new ArrayList<>();
        FileUpdateInfoModel fileInfo = new FileUpdateInfoModel();
        fileInfo.setUserId(uerId);
        fileInfo.setFileRvtype(reType);
        fileInfo.setFileType(type);
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            String fileName = file.getOriginalFilename();
            String substring = fileName.substring(fileName.lastIndexOf("."));
            long seq = FrameUtils.generateFileSeq();
            // 文件名
            fileName = seq + substring;
            fileInfo.setId(seq);
            fileInfo.setFileRank(i);
            fileInfo.setFileName(fileName);
            // 获取URL
            String fileUrl = getFileUrl(fileInfo);
            fileInfo.setFileUrl(fileUrl);
            File fileTo = new File(fileDir + fileUrl);
            if (!fileTo.getParentFile().exists()) {
                fileTo.getParentFile().mkdirs();
            }
            // 保存文件
            try {
                file.transferTo(fileTo);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
            // 如果是系统停车场图片，状态设置为正常
            if (reType.equals(FileInfoEnums.RvType.SPIMG.name())) {
                fileInfo.setState(FileInfoEnums.State.A.name());
            } else {
                fileInfo.setState(FileInfoEnums.State.C.name());
            }
            fileInfo.setCreateDate(new Date());
            insert(fileInfo);

            list.add(fileUrl);
        }
        return list;
    }

    /**
     * 上传文件（字符串类型）
     */
    public int saveFile(FileUpdateInfoModel model, String fileDir, String fileType, String imagebase) {
        // 没有图片
        if (StringUtils.isBlank(imagebase)) {
            return -1;
        }
        // 添加文件
        insert(model);
        Long id = model.getId();
        String fileName = model.getFileName();
        String substring = fileName.substring(fileName.lastIndexOf("."));
        fileName = id + substring;

        model.setFileName(fileName);
        model.setFileType(fileType);
        String fielUrl = getFileUrl(model);
        model.setFileUrl(fielUrl);
        File file = new File(fileDir + fielUrl);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        int i = ImageUtils.base642image(imagebase, file, ImageUtils.FILE_MAX);
        if (i == 0) {
            throw new RuntimeException("文件过大！");
        } else if (i == 2) {
            throw new RuntimeException("文件上传异常！");
        }
        model.setState(FileInfoEnums.State.A.name());
        model.setCreateDate(new Date());
        return update(model);
    }

    /**
     * 上传文件（MultipartFile类型）
     */
    public int saveFile(FileUpdateInfoModel model, String fileDir, String fileType, MultipartFile fileUrl) {
        // 添加文件
        insert(model);
        Long id = model.getId();
        String fileName = fileUrl.getOriginalFilename();
        String substring = fileName.substring(fileName.lastIndexOf("."));
        fileName = id + substring;

        model.setFileName(fileName);
        model.setFileType(fileType);
        String fielUrl = getFileUrl(model);
        model.setFileUrl(fielUrl);
        File file = new File(fileDir + fielUrl);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            fileUrl.transferTo(file);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        model.setState(FileInfoEnums.State.A.name());
        model.setCreateDate(new Date());
        return update(model);
    }

    private String getFileUrl(FileUpdateInfoModel fileInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("/");
        sb.append(fileInfo.getFileType());
        sb.append("/");
        sb.append(fileInfo.getFileRvtype());
        sb.append("/");
        sb.append(fileInfo.getFileName());
        return sb.toString();
    }

    public FileUpdateInfoModel selectByTypeRvId(Long rvId, String rvType) {
        List<FileUpdateInfoModel> ts = selectFilesByTypeRvId(rvId, rvType);
        if (!ts.isEmpty()) {
            return ts.get(0);
        }
        return null;
    }

    public List<String> selectUrlsByTypeRvId(Long rvId, String rvType) {
        List<FileUpdateInfoModel> ts = selectFilesByTypeRvId(rvId, rvType);
        if (ts!=null&&!ts.isEmpty()) {
            List<String> list = new ArrayList<>();
            for (FileUpdateInfoModel t : ts) {
                list.add(t.getFileUrl());
            }
            return list;
        }
        return Collections.emptyList();
    }

    public List<FileUpdateInfoModel> selectFilesByTypeRvId(Long rvId, String rvType) {
        Map<String, Object> map = new HashMap<>();
        map.put("fileRvid", rvId);
        map.put("fileRvtype", rvType);
        List<FileUpdateInfoModel> ts = (List<FileUpdateInfoModel>) this.pageList(map);
        if (!ts.isEmpty()) {
            return ts;
        }
        return null;
    }

    /**
     * 查询图片ID
     *
     * @param rvId
     * @param rvType
     * @return
     */
    public String selectFileUrl(Long rvId, String rvType) {
        FileUpdateInfoModel fileUpdateInfoModel = selectByTypeRvId(rvId, rvType);
        if (fileUpdateInfoModel != null) {
            return fileUpdateInfoModel.getFileUrl();
        }
        return null;
    }

    /**
     * 更新图片的关联类型
     */
    public int update(Long id, String fileRvtyp, Long fileRvid) {
        FileUpdateInfoModel fileInfo = new FileUpdateInfoModel();
        fileInfo.setId(id);
        fileInfo.setFileRvtype(fileRvtyp);
        fileInfo.setFileRvid(fileRvid);
        return updateBySelective(fileInfo);
    }

    /**
     * 更新文件序号状态
     */
    public int updateFileSeqState(String imgUrl, boolean isQuietly) {
        // 获取文件序号
        long fileSeq = 0;
        try {
            fileSeq = FrameUtils.getFileSeq(imgUrl);
        } catch (Exception e) {
            if (isQuietly) {
                return 0;
            } else {
                throw new RuntimeException("file url error! url=" + imgUrl);
            }
        }
        // 更新文件状态
        FileUpdateInfoModel fileInfo = new FileUpdateInfoModel();
        fileInfo.setId(fileSeq);
        fileInfo.setState(FileInfoEnums.State.A.name());
        return updateBySelective(fileInfo);
    }


    /**
     * 更新文件状态，设置fileReId 和 状态
     * 返回第一个文件的URL
     */
    public String updateFileState(String[] fileUrls, Long fileRvid) {
        if (fileUrls == null || fileUrls.length == 0) {
            return null;
        }
        String reFileUrl = null;
        for (String fileUrl : fileUrls) {
            if (StringUtils.isEmpty(fileUrl)) {
                break;
            }
            long fileSeq = 0;
            try {
                // 通过图片URL获取 文件序号
                fileSeq = FrameUtils.getFileSeq(fileUrl);
            } catch (Exception e) {
                log.error("carport img url error! imgUrl={}", fileUrl, e);
            }
            if (fileSeq > 0) {
                FileUpdateInfoModel fileInfo = new FileUpdateInfoModel();
                fileInfo.setId(fileSeq);
                fileInfo.setFileRvid(fileRvid);
                fileInfo.setState(FileInfoEnums.State.A.name());
                updateBySelective(fileInfo);

                // 如果没有图片，则默认使用上传的第一个图片
                if (reFileUrl == null) {
                    reFileUrl = fileUrl;
                }
            }
        }
        return reFileUrl;
    }
}
