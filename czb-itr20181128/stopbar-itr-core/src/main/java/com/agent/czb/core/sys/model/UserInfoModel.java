package com.agent.czb.core.sys.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表模型层
 */
public class UserInfoModel implements Serializable { // 用户表
	private Long id;//标识
	private String loginName;//登录名称
	private String loginPwd;//登录密码
	private String nickName;//昵称
	private String phone;//手机
	private String signa;//个性签名
	private Integer age;//年龄
	private Integer sex;//性别，1： 男；2：女
	private String imgUrl;//头像地址
	private String email;//电子邮箱
	private String isAuth;//实名认证，0：未认证；1：已认证
	private String userName;//用户姓名
	private String idNumber;//身份证号
	private String plateNum;//车牌
	private String state;//状态
	private Long userId;//创建人
	private java.util.Date createTime;//创建时间
	private java.util.Date updateTime;//修改时间
	private Integer isDel;//是否删除

	private String sexStr;
	private String token; // 验证码
    private Integer scored; //积分


	public Long getId() {
		return id;
	}

	public Integer getScored() {
		return scored;
	}

	public void setScored(Integer scored) {
		this.scored = scored;
	}

	public UserInfoModel setId(Long id) {
		this.id = id;
		return this;
	}

	public String getLoginName() {
		return loginName;
	}

	public UserInfoModel setLoginName(String loginName) {
		this.loginName = loginName;
		return this;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public UserInfoModel setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
		return this;
	}

	public String getNickName() {
		return nickName;
	}

	public UserInfoModel setNickName(String nickName) {
		this.nickName = nickName;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public UserInfoModel setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public String getSigna() {
		return signa;
	}

	public UserInfoModel setSigna(String signa) {
		this.signa = signa;
		return this;
	}

	public Integer getAge() {
		return age;
	}

	public UserInfoModel setAge(Integer age) {
		this.age = age;
		return this;
	}

	public Integer getSex() {
		return sex;
	}

	public UserInfoModel setSex(Integer sex) {
		this.sex = sex;
		return this;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public UserInfoModel setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public UserInfoModel setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getIsAuth() {
		return isAuth;
	}

	public UserInfoModel setIsAuth(String isAuth) {
		this.isAuth = isAuth;
		return this;
	}

	public String getUserName() {
		return userName;
	}

	public UserInfoModel setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public UserInfoModel setIdNumber(String idNumber) {
		this.idNumber = idNumber;
		return this;
	}

	public String getPlateNum() {
		return plateNum;
	}

	public UserInfoModel setPlateNum(String plateNum) {
		this.plateNum = plateNum;
		return this;
	}

	public String getState() {
		return state;
	}

	public UserInfoModel setState(String state) {
		this.state = state;
		return this;
	}

	public Long getUserId() {
		return userId;
	}

	public UserInfoModel setUserId(Long userId) {
		this.userId = userId;
		return this;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public UserInfoModel setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public UserInfoModel setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
		return this;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public UserInfoModel setIsDel(Integer isDel) {
		this.isDel = isDel;
		return this;
	}

	public String getSexStr() {
		return sexStr;
	}

	public UserInfoModel setSexStr(String sexStr) {
		this.sexStr = sexStr;
		return this;
	}

	public String getToken() {
		return token;
	}

	public UserInfoModel setToken(String token) {
		this.token = token;
		return this;
	}
}
