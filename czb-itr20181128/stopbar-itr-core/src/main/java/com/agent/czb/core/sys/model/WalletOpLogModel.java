package com.agent.czb.core.sys.model;

import java.io.Serializable;

/**
 * 钱包操作日志模型层
 */
public class WalletOpLogModel implements Serializable { // 钱包操作日志
	private Long id;//标识
	private Long walletId;//钱包标识
	private String opType;//操作类型
	private String type;//记录类型
	private Long rfId;//关联标识
	private Long amount;//金额
	private Long balance;// 余额
	private Long userId;//用户标识
	private java.util.Date createTime;//创建时间

	private String userName;
	private String opTypeStr;
	private String typeStr;

	public String getOpTypeStr() {
		return opTypeStr;
	}

	public void setOpTypeStr(String opTypeStr) {
		this.opTypeStr = opTypeStr;
	}

	public String getTypeStr() {
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	public Long getId() {
		return id;
	}

 	public void setId(Long id) {
		this.id = id;
	}

	public Long getWalletId() {
		return walletId;
	}

 	public void setWalletId(Long walletId) {
		this.walletId = walletId;
	}

	public String getOpType() {
		return opType;
	}

 	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getType() {
		return type;
	}

 	public void setType(String type) {
		this.type = type;
	}

	public Long getRfId() {
		return rfId;
	}

 	public void setRfId(Long rfId) {
		this.rfId = rfId;
	}

	public Long getAmount() {
		return amount;
	}

 	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getUserId() {
		return userId;
	}

 	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

 	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

}
