package com.agent.czb.web.system.service;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agent.czb.web.system.mapper.SysMenuBtnMapper;

@Service("sysMenuBtnService")
public class SysMenuBtnService<T> extends BaseService<T> {
	private final static Logger log= Logger.getLogger(SysMenuBtnService.class);
	
	public List<T> queryByAll(){
		return getMapper().queryByAll();
	}
	
	
	
	public List<T> queryByMenuid(Integer menuid){
		return getMapper().queryByMenuid(menuid);
	}
	
	public List<T> queryByMenuUrl(String url){
		return getMapper().queryByMenuUrl(url);
	}
	
	public void deleteByMenuid(Integer menuid){
		getMapper().deleteByMenuid(menuid);
	}
	
	public List<T> getMenuBtnByUser(Integer userid){
		return getMapper().getMenuBtnByUser(userid);
	}

	@Autowired
    private SysMenuBtnMapper<T> mapper;

		
	public SysMenuBtnMapper<T> getMapper() {
		return mapper;
	}

}
