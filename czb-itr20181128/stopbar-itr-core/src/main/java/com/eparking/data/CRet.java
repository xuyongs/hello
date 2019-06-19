package com.eparking.data;


import java.util.List;

import com.sun.jna.Structure;

public class CRet extends Structure {

	public static class ByReference extends CRet implements Structure.ByReference {}  
	public static class ByValue extends CRet implements Structure.ByValue {}  
	
	public byte[] ret_code = new byte[16];
	public byte[] error_des = new byte[255];
	@Override
	protected List getFieldOrder() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
