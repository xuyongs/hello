package com.agent.czb.core.sys.service;

import com.agent.czb.common.rest.service.BasisService;
import com.agent.czb.core.sys.enums.LocaInfoEnums;
import com.agent.czb.core.sys.mapper.ParkInfoMapper;
import com.agent.czb.core.sys.model.LocaInfoModel;
import com.agent.czb.core.sys.model.ParkInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 停车场信息服务层
 */
@Service("parkInfoService")
public class ParkInfoService extends BasisService<ParkInfoMapper, ParkInfoModel> {
    private static final Logger log = LoggerFactory.getLogger(ParkInfoService.class);

    @Autowired
    private FileUpdateInfoService fileUpdateInfoService;

    @Autowired
    private LocaInfoService locaInfoService;

    public int insert(ParkInfoModel model, String[] fileUrls, String mapLng, String mapLat) {
        insert(model);
        // 插入经纬度
        LocaInfoModel locaInfo = new LocaInfoModel();
        locaInfo.setMapLng(mapLng);
        locaInfo.setMapLat(mapLat);
        locaInfo.setType(LocaInfoEnums.Type.PARK.toValue());
        locaInfo.setRefId(model.getId());
        locaInfo.setCreateTime(new Date());
        locaInfoService.insert(locaInfo);

        // 更新文件状态
        String fileUrl = fileUpdateInfoService.updateFileState(fileUrls, model.getId());
        if (fileUrl != null) {
            ParkInfoModel temp = new ParkInfoModel();
            temp.setId(model.getId());
            temp.setImgUrl(fileUrl);
            model.setImgUrl(fileUrl);
            updateBySelective(temp);
        }
        return 1;
    }

    public int deleteAndLocaInfo(Long id) {
        locaInfoService.delByType(LocaInfoEnums.Type.PARK.toValue(), id);
        return delete(id);
    }

    public void add(ParkInfoModel model) {
        insert(model);
    }
}
