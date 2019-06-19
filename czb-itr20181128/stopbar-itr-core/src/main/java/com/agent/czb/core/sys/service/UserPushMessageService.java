package com.agent.czb.core.sys.service;

import com.agent.czb.common.rest.service.BasisService;
import com.agent.czb.core.sys.mapper.UserPushMessageMapper;
import com.agent.czb.core.sys.model.UserPushMessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 创建人 qany.
 * 创建时间  2016/12/1:17:37.
 * 描述 消息记录
 */
@Service("userPushMessageService")
public class UserPushMessageService extends BasisService<UserPushMessageMapper, UserPushMessageModel> {
   
    /**
     * 创建人:qany.
     * 创建时间:2016/12/2:9:52.
     * 描述:根据用户id查询用户所有的消息记录
     */
    public  List<UserPushMessageModel> selectByUserId(Long id){
        return  mapper.selectUserId(id);
    }
    /**
     * 创建人:qany.
     * 创建时间:2016/12/2:10:52.
     * 描述:根据消息id设置已读未读
     */
    public int hadRead(Long id){
        UserPushMessageModel message=new UserPushMessageModel();
        message.setId(id);
        message.setIsRead(1);
        return mapper.updateBySelective(message);
    }
    /**
     * 创建人:qany.
     * 创建时间:2016/12/2:11:14.
     * 描述:根据用户id和消息类型查询
     */
    public List<UserPushMessageModel> selectMessageByType(Long id,Integer type){
        UserPushMessageModel message=new UserPushMessageModel();
        message.setUserId(id);
        message.setType(type);
        return  mapper.selectMessageByType(message);
    }


}
