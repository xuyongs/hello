package com.agent.czb.service.sys.restful;
import java.awt.List;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.agent.czb.common.rest.ResultHelp;
import com.agent.czb.common.rest.restful.BasisRestful;
import com.agent.czb.common.utils.DateUtils;
import com.agent.czb.core.sys.model.UserFeedbackModel;
import com.agent.czb.core.sys.service.UserFeedBackService;
@RestController
@RequestMapping("services/userFeedBack")
public class UserFeedBackRestful extends BasisRestful{
	@Autowired
    private UserFeedBackService userFeedBackService;
	/**
	 * 添加意见反馈
	 * @param userId 用户Id
	 * @param note 反馈内容
	 * @return
	 */
	  @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	    @ResponseStatus(HttpStatus.OK)
	    public ResultHelp add(Long userId,String note) {
		  UserFeedbackModel userFeedbackModel=new UserFeedbackModel();
		  userFeedbackModel.setUserId(userId);
		  if (note.equals(null)||note.equals("")) {
			  userFeedbackModel.setNote("此用户填写的反馈意见为空");
		  }else{
			userFeedbackModel.setNote(note);
		  }
		  
		  userFeedbackModel.setCreateTime(new Date());
	      userFeedBackService.insertnote(userFeedbackModel);
	        return ResultHelp.newResult("反馈意见提交成功");
	    }
	
   /**
   * 查询意见反馈
   * @param userId 用户Id
   * @return 反馈内容
   */
	  @RequestMapping(value = "/get", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	    @ResponseStatus(HttpStatus.OK)
	    public ResultHelp get(Long userId) {  
		 int count=userFeedBackService.selectCount(userId);
		 if (count==0) {
			return ResultHelp.newFialResult("没有此用户!");
		}else{
	    java.util.List<UserFeedbackModel> list = userFeedBackService.selectByUserId(userId);
	   for (UserFeedbackModel userFeedbackModel : list) {
		  if (userFeedbackModel.getNote().equals(null)||userFeedbackModel.getNote().equals("")) {
			  return ResultHelp.newFialResult("您反馈的建议内容为空");
		  }  
	    }
	     return ResultHelp.newResult(list);
	     }
	    }
}
