package com.agent.czb.core.sys.service;

import com.agent.czb.common.rest.service.BasisService;
import com.agent.czb.core.sys.mapper.ServeInfoMapper;
import com.agent.czb.core.sys.model.LocaInfoModel;
import com.agent.czb.core.sys.model.ServeInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 设备服务信息服务层
 */
@Service("serveInfoService")
public class ServeInfoService extends BasisService<ServeInfoMapper, ServeInfoModel> {
    private static final Logger log = LoggerFactory.getLogger(ParkInfoService.class);
    @Autowired
    private FileUpdateInfoService fileUpdateInfoService;

    @Autowired
    private LocaInfoService locaInfoService;

    public int insert(ServeInfoModel model, String type, String[] fileUrls, String mapLng, String mapLat) {
        insert(model);
        // 插入经纬度
        LocaInfoModel locaInfo = new LocaInfoModel();
        locaInfo.setMapLng(mapLng);
        locaInfo.setMapLat(mapLat);
        locaInfo.setType(type);
        locaInfo.setRefId(model.getId());
        locaInfo.setCreateTime(new Date());
        locaInfoService.insert(locaInfo);

        // 更新文件状态
        String fileUrl = fileUpdateInfoService.updateFileState(fileUrls, model.getId());
        if (fileUrl != null) {
            ServeInfoModel temp = new ServeInfoModel();
            temp.setId(model.getId());
            temp.setImgUrl(fileUrl);
            model.setImgUrl(fileUrl);
            updateBySelective(temp);
        }
        return 1;
    }

    public int deleteAndLocaInfo(Long id) {
        ServeInfoModel info = selectById(id);
        locaInfoService.delByType(info.getType(), id);
        return delete(id);
    }
}
