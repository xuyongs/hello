package com.agent.czb.core.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agent.czb.core.park.model.ParkWhiteListModel;
import com.agent.czb.core.sys.model.NewParkWhiteList;
import com.agent.czb.core.user.mapper.NewParkWhiteListMapper;
import com.sun.jna.platform.win32.Netapi32Util.User;

/**
 * 删除白名单服务层
 * @author Adios
 * @date 
 * @version
 */
@Service
public class NewParkWhiteListService {
	
	@Autowired
	private NewParkWhiteListMapper newParkWhiteListMapper;
	public List<NewParkWhiteList> listNewParkWhiteList(){
		return  newParkWhiteListMapper.delList();
	}
	
	/*
	 * 查询未过期的白名单数据
	 */
	public List<NewParkWhiteList> listNewParkWhiteListTheUse(){
		return  newParkWhiteListMapper.selUseList();
	}
	
	/*
	 * 更新本地数据
	 */
	public void updateEparking(ParkWhiteListModel parkWhiteListModel){
		newParkWhiteListMapper.updateEparking(parkWhiteListModel);
	}
	
}
