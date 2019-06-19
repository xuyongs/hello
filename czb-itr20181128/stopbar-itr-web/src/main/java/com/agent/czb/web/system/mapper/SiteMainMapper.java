package com.agent.czb.web.system.mapper;

import java.util.Map;


public interface SiteMainMapper<T> extends BaseMapper<T>{
	/**
	 * 管理站点类型
	 * @param map
	 */	
	public void addTypeRel(Map<String, Object> map);
	
	
	/**
	 * 删除关联的数据
	 * @param siteId
	 */	
	public void deleteTypeRel(Integer siteId);
}
