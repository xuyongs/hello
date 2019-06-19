package com.agent.czb.core.sys.service;

import com.agent.czb.common.http.HttpClientUtils;
import com.agent.czb.common.rest.service.BasisService;
import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.core.sys.mapper.UserInfoMapper;
import com.agent.czb.core.sys.model.UserCouponModel;
import com.agent.czb.core.sys.model.UserInfoModel;
import com.agent.czb.core.sys.model.UserPlateInfoModel;
import com.agent.czb.core.sys.model.WalletInfoModel;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户表服务层
 */
@Service("userInfoService")
public class UserInfoService extends BasisService<UserInfoMapper, UserInfoModel> {
    private static final Logger log = LoggerFactory.getLogger(UserInfoService.class);
    @Autowired
    private WalletInfoService walletInfoService;
    @Autowired
    private FileUpdateInfoService fileUpdateInfoService;
    @Autowired
    private UserPlateInfoService userPlateInfoService;
    @Autowired
    private  UserCouponService userCouponService;

    public String sysUserById(Long id) {
        return mapper.sysUserById(id);
    }

    public String getUserName(long userId) {
        if (userId == 0) {
            return null;
        }
        String userName = null;
        if (userId > 0) {
            UserInfoModel model = selectById(userId);
            if (model != null) {
                userName = model.getNickName();
            }
        } else {
            userName = sysUserById(userId);
        }
        return userName;
    }

    public UserInfoModel selectUserByPhone(String phone) {
        if (StringUtils.isNotEmpty(phone)) {
            List<UserInfoModel> userInfoModels = pageList(FrameUtils.newMap("phone", phone));
            if (userInfoModels.size() > 0) {
                return userInfoModels.get(0);
            }
        }
        return null;
    }

    public Long selectUserIdByPhone(String phone) {
        if (StringUtils.isNotEmpty(phone) ) {
            UserInfoModel userInfoModel = selectUserByPhone(phone);
            if (userInfoModel != null) {
                return userInfoModel.getId();
            }
        }

        return null;
    }

    public List<String> selectUserPlateByPhone(String phone) {
        if (StringUtils.isNotEmpty(phone) ) {
            Long userId = selectUserIdByPhone(phone);
            if (userId != null) {
                return selectUserPlateByUserId(userId);
            }
        }
        return null;
    }

    public List<String> selectUserPlateByUserId(Long userId) {
        if (userId != null) {
            List<UserPlateInfoModel> userPlateInfoModels = userPlateInfoService.pageList(FrameUtils.newMap("userId", userId.toString()));
            if (userPlateInfoModels.size() > 0) {
                List<String> list = new ArrayList<>(userPlateInfoModels.size());
                for (UserPlateInfoModel userPlateInfoModel : userPlateInfoModels) {
                    list.add(userPlateInfoModel.getPlateNum());
                }
                return list;
            }
        }
        return null;
    }

    public UserPlateInfoModel selectUserPlateByPlateNum(String plateNum) {
        if (StringUtils.isNotEmpty(plateNum)) {
            List<UserPlateInfoModel> userPlateInfoModels = userPlateInfoService.pageList(FrameUtils.newMap("plateNum", plateNum));
            if (userPlateInfoModels.size() > 0) {
                return userPlateInfoModels.get(0);
            }
        }
        return null;
    }

    public Long selectUserIdByPlateNum(String plateNum) {
        if (StringUtils.isNotEmpty(plateNum)) {
            UserPlateInfoModel userPlateInfoModel = selectUserPlateByPlateNum(plateNum);
            if (userPlateInfoModel != null) {
                return userPlateInfoModel.getUserId();
            }
        }
        return null;
    }


    /**
     * 注册用户
     */
    public int regist(UserInfoModel t) {
        insert(t);
        // 添加钱袋金额
        WalletInfoModel walletInfoModel = new WalletInfoModel();
        walletInfoModel.setUserId(t.getId());
        walletInfoModel.setTotal(0L);
        walletInfoModel.setVersion(1);
        walletInfoModel.setCreateTime(new Date());
        walletInfoModel.setState("0");
        walletInfoService.insert(walletInfoModel);


        //新用户送优惠券 两张5元
        UserCouponModel coupon=new UserCouponModel();
        coupon.setUserId(t.getId());
        coupon.setAmount(500L);
        userCouponService.savaCoupon(coupon);
        coupon.setId(null);
        userCouponService.savaCoupon(coupon);


        // 设置昵称
        String nickName = FrameUtils.newNickName(t.getId().toString());
        t.setNickName(nickName);
        updateNickNameById(t.getId(), nickName);
        return 1;
    }

    public void updateNickNameById(Long id, String nickName) {
        UserInfoModel temp = new UserInfoModel();
        temp.setId(id);
        temp.setNickName(nickName);
        updateBySelective(temp);
    }

    /**
     * 修改用户信息
     */
    public int updateUserInfo(UserInfoModel t) {
        int i = updateBySelective(t);
        if (i == 0) {
            return 0;
        }
        // 修改图片状态
        fileUpdateInfoService.updateFileState(new String[]{t.getImgUrl()}, t.getId());
        return 1;
    }

    /**
     * 修改用户车牌号
     */
    public void updateUserPlateInfo(Long userId, String plateNum) {
        Long count = userPlateInfoService.pageCount(FrameUtils.newMap("plateNum", plateNum));
        if (count > 0) {
            throw new RuntimeException("该车牌已存在");
        }
        List<UserPlateInfoModel> list = userPlateInfoService.pageList(FrameUtils.newMap("userId", userId.toString()));
        if (list.size() > 0) {
            UserPlateInfoModel userPlateInfo = list.get(0);
            // 修改牌号码
            userPlateInfo.setPlateNum(plateNum);
            userPlateInfo.setUpdateTime(new Date());
            userPlateInfo.setCreateTime(null);
            userPlateInfo.setUserId(null);
            userPlateInfoService.updateBySelective(userPlateInfo);
        } else {
            // 设置牌号码
            UserPlateInfoModel userPlateInfo = new UserPlateInfoModel();
            userPlateInfo.setUserId(userId);
            userPlateInfo.setCreateTime(new Date());
            userPlateInfo.setPlateNum(plateNum);
            userPlateInfoService.insert(userPlateInfo);
        }
    }

    public UserInfoModel selectUserByToken(String token) {
        List<UserInfoModel> list = pageList(FrameUtils.newMap("loginName", token));
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 创建人:qany.
     * 创建时间:2016/10/26:10:51.
     * 描述: 替已存在的用户注册JMessage
     */
    public void  regiterJMessageUser() throws Exception{
        List<UserInfoModel> userList=mapper.userList();
        JSONArray userjson=new JSONArray();
        JSONObject json=null;
        if (userList.size()<=500){
            for (UserInfoModel usernfo:userList) {
                json=new JSONObject();
                json.put("username",usernfo.getPhone());
                json.put("password","StopbarIMPwd");
                userjson.add(json);
            }
            String jsonString=userjson.toJSONString();
            String msg= HttpClientUtils.postJMessage("https://api.im.jpush.cn/v1/users/",jsonString);

        }else{
            for (int i = 0; i < userList.size(); i++) {
                json.put("username",userList.get(i).getLoginName());
                json.put("password",userList.get(i).getLoginPwd());
                userjson.add(json);
                if (userjson.size()==500){
                    String msg= HttpClientUtils.postJMessage("https://api.im.jpush.cn/v1/users/",userjson.toString());
                    userjson=new JSONArray();
                }
            }
        }
        userjson=null;
        userList=null;
    }
    public UserInfoModel selectUserIdByPhone(Long userId){
    	return mapper.selectUserIdByPhone(userId);
    }
}
