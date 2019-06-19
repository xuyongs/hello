package com.agent.czb.core.sys.service;

import com.agent.czb.common.rest.service.BasisService;
import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.core.sys.mapper.LocaInfoMapper;
import com.agent.czb.core.sys.model.LocaInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 位置信息服务层
 */
@Service("locaInfoService")
public class LocaInfoService extends BasisService<LocaInfoMapper, LocaInfoModel> {
    private static Logger log = LoggerFactory.getLogger(LocaInfoService.class);

    public List<LocaInfoModel> locaList(Map map) {
        return mapper.locaList(map);
    }

    public LocaInfoModel getLoca(String type, Long refId) {
        Map<String, String> map = FrameUtils.newMap("type", type);
        map.put("refId", refId.toString());
        List<LocaInfoModel> ts = mapper.pageList(map);
        if (ts.size() == 0) {
            return null;
        }
        return ts.get(0);
    }

    public void add(String type, Long refId, String mapLng, String mapLat) {
        Map<String, String> map = FrameUtils.newMap("type", type);
        map.put("refId", refId.toString());
        List<LocaInfoModel> ts = mapper.pageList(map);
        LocaInfoModel locaInfoModel;
        if (ts.size() == 0) {
            locaInfoModel = new LocaInfoModel();
            locaInfoModel.setType(type);
            locaInfoModel.setRefId(refId);
            locaInfoModel.setMapLng(mapLng);
            locaInfoModel.setMapLat(mapLat);
            locaInfoModel.setCreateTime(new Date());
            locaInfoModel.setUpdateTime(new Date());
            insert(locaInfoModel);
        } else {
            locaInfoModel = ts.get(0);
            locaInfoModel.setMapLng(mapLng);
            locaInfoModel.setMapLat(mapLat);
            locaInfoModel.setUpdateTime(new Date());
            update(locaInfoModel);
        }
    }

    public void add(LocaInfoModel locaInfoModel) {
        Map<String, String> map = FrameUtils.newMap("type", locaInfoModel.getType());
        map.put("refId", locaInfoModel.getRefId().toString());
        List<LocaInfoModel> ts = mapper.pageList(map);
        if (ts.size() == 0) {
            locaInfoModel.setCreateTime(new Date());
            locaInfoModel.setUpdateTime(new Date());
            insert(locaInfoModel);
        } else {
            locaInfoModel = ts.get(0);
            locaInfoModel.setMapLng(locaInfoModel.getMapLng());
            locaInfoModel.setMapLat(locaInfoModel.getMapLat());
            locaInfoModel.setUpdateTime(new Date());
            update(locaInfoModel);
        }
    }



    public int delByType(String type, Long refId) {
        LocaInfoModel locaInfo = new LocaInfoModel();
        locaInfo.setType(type);
        locaInfo.setRefId(refId);
        return mapper.delByType(locaInfo);
    }
    //根据refid查询
	public LocaInfoModel selectByRefId(Long refId) {
		return mapper.selectByRefId(refId);
		
		
	}
}
