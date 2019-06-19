package com.agent.czb.core.sys.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.agent.czb.common.rest.service.BasisService;
import com.agent.czb.core.sys.mapper.UserFeedbackMapper;
import com.agent.czb.core.sys.model.UserFeedbackModel;

@Service("userFeedBackService")
public class UserFeedBackService  extends  BasisService<UserFeedbackMapper, UserFeedbackModel>{
  /**
   * 添加反馈信息
   * @param model
   * @return
   */
	public int insertnote(UserFeedbackModel model) {
		int row=mapper.insert(model);
		return row;
	}
	
	/**
	 * 根据userID查询
	 * @param userId
	 */

	public List<UserFeedbackModel> selectByUserId(Long userId) {
		return mapper.selectByUserId(userId);
		
	}

	public int selectCount(Long userId) {
		// TODO Auto-generated method stub
		return mapper.selectCount(userId);
	}

}
