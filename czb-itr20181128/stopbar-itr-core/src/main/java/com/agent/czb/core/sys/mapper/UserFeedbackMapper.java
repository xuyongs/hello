package com.agent.czb.core.sys.mapper;

import java.util.List;

import com.agent.czb.common.rest.mapper.BasisMapper;
import com.agent.czb.core.sys.model.UserFeedbackModel;

public interface UserFeedbackMapper extends BasisMapper<UserFeedbackModel> {

	List<UserFeedbackModel> selectByUserId(Long userId);

	int selectCount(Long userId);

}
