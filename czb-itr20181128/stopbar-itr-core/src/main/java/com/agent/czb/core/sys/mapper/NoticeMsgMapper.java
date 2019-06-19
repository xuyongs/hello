package com.agent.czb.core.sys.mapper;

import com.agent.czb.common.rest.mapper.BasisMapper;
import com.agent.czb.core.sys.model.NoticeMsgModel;

import java.util.List;

/**
 * 公告消息数据层
 */
public interface NoticeMsgMapper extends BasisMapper<NoticeMsgModel> {
    List<NoticeMsgModel> selectLastMsg(Integer limit);
}