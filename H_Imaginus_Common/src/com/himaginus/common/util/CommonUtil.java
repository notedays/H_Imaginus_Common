package com.himaginus.common.util;

import java.io.UnsupportedEncodingException;

public class CommonUtil {
	private CommonUtil() {
	}
	
	public static byte[] stringToByteArray(String str){
		if(str==null){
			return new byte[0];
		}
		try {
			return str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str.getBytes();
	}
	
	public static String byteArrayToString(byte[] ba){
		if(ba==null){
			return new String();
		}
		try {
			return new String(ba, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new String(ba);
	}
	
}