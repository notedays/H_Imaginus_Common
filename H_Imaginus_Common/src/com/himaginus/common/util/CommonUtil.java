package com.himaginus.common.util;

import java.io.UnsupportedEncodingException;

public class CommonUtil {
	public static int getLengthDataSize(){
		return 4;
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

	public static String filterBlame(String str, boolean isReplaceNewline){
		String returnString = str.replaceAll("씨발|시발|시팔|개시팔|개시발|개씨발|개새끼|니기미|좇|섹스|보지|자지|씹질|창년|창녀|엠창|앰창|sex|Sex|SEX|병신|지랄|염병|애미|니엄마|갈보|좃|좇", "**");
		if(isReplaceNewline){
			return returnString.replace("\n","");
		}
		return returnString;
	}
	public static String filterBlame(String str){
		return CommonUtil.filterBlame(str,true);
	}
}