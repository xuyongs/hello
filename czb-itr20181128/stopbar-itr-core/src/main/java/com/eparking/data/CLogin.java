package com.eparking.data;

import java.util.List;

import com.sun.jna.Structure;

public class CLogin extends Structure {
	public static class ByReference extends CLogin implements Structure.ByReference {}  
	public static class ByValue extends CLogin implements Structure.ByValue {}  
	public byte[] user_id = new byte[16];
	public byte[] user_password = new byte[16];
	@Override
	protected List getFieldOrder() {
		// TODO Auto-generated method stub
		return null;
	}

//	public CLogin(byte[] userId, byte[] userPassword){
//		this.user_id = userId;
//		this.user_password = userPassword;
//	}
//	
	

}
