package com.agent.czb.core.sys.mapper;

import com.agent.czb.common.rest.mapper.BasisMapper;
import com.agent.czb.core.sys.model.UserInfoModel;

import java.util.List;

/**
 * 用户表数据层
 */
public interface UserInfoMapper extends BasisMapper<UserInfoModel> {
    String sysUserById(Long id);

    List<UserInfoModel> userList();
    
    UserInfoModel selectUserIdByPhone(Long userId);
}