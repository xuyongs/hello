package com.agent.czb.web.system.mapper;

import com.agent.czb.web.system.model.SysUserModel;

/**
 * SysUser Mapper
 * @author Administrator
 *
 */
public interface SysUserMapper<T> extends BaseMapper<T> {
	
	/**
	 * 检查登录
	 * @param model
	 * @return
	 */
	public T queryLogin(SysUserModel model);
	
	
	/**
	 * 查询邮箱总数，检查是否存在
	 * @param email
	 * @return
	 */
	public int getUserCountByUserName(String email);
}
