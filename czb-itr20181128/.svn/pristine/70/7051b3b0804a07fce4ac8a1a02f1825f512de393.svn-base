package com.agent.czb.core.sys.service;

import com.agent.czb.common.rest.mapper.BasisMapper;
import com.agent.czb.common.rest.service.BasisService;
import com.agent.czb.core.sys.model.NoticeMsgModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.agent.czb.core.sys.mapper.NoticeMsgMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公告消息服务层
 */
@Service("noticeMsgService")
public class NoticeMsgService extends BasisService<NoticeMsgMapper, NoticeMsgModel> {

    public List<NoticeMsgModel> getMsg(Long msgId, Integer limit) {
        Map<String, Object> map = new HashMap<>();
        map.put("msgId", msgId);
        map.put("pagerOrder", "order by id desc");
        map.put("pagerLimit", "limit " + limit);
        return this.mapper.pageList(map);
    }

    public List<NoticeMsgModel> getLastMsg(Integer limit) {
        return mapper.selectLastMsg(limit);
    }
}
