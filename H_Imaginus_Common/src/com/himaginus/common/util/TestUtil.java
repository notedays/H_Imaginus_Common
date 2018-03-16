package com.himaginus.common.util;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.himaginus.common.packet.RequestCode;
import com.himaginus.common.packet.RequestPacket;
import com.himaginus.common.packet.ResponseCode;
import com.himaginus.common.packet.ResponsePacket;

public class TestUtil {

	public static Map<Integer, String> requestMap = new HashMap<Integer, String>();
	public static Map<Integer, String> responseMap = new HashMap<Integer, String>();
	private static DateFormat formatter = new SimpleDateFormat("MM-dd hh:mm:ss");
	
	public static void printRequest(RequestPacket request, String userName) {
		StringBuilder builder = new StringBuilder(formatter.format(new Date(System.currentTimeMillis())));
		builder.append("   ");
		builder.append("[REQUEST ] %-60s");
		builder.append("FROM : %-33s");
		builder.append("TEXT : %s");
		String context = request.getContext() != null ? request.getContext().replace("\n", "\\n") : "";
		System.out.println(String.format(builder.toString(), getRequestFieldName(request), userName, context));
	}

	public static void printResponse(ResponsePacket response, String userName) {
		StringBuilder builder = new StringBuilder(formatter.format(new Date(System.currentTimeMillis())));
		builder.append("   ");
		builder.append("[RESPONSE] %-60s");
		builder.append("TO   : %-35s");
		builder.append("SIZE : %s");
		System.out.println(String.format(builder.toString(), getResponseFieldName(response),
				userName, response.getResponseDataList().size()));
	}
	
	public static void printMessage(String... messages) {
		StringBuilder builder = new StringBuilder(formatter.format(new Date(System.currentTimeMillis())));
		builder.append("   ");
		builder.append("[MESSAGE ] ");
		for(String message : messages) {
			builder.append("%-20s");
		}
		System.out.println(String.format(builder.toString(), messages));
	}

	/**
	 * Load Request Map!!
	 * 
	 * @param request
	 */
	public static void loadRequestMap(RequestCode request) {
		for (Field field : request.getClass().getDeclaredFields()) {
			try {
				field.setAccessible(true);
				if (field.getName() != null && !field.getName().equals("requestCode")) {
					requestMap.put(field.getInt(request), field.getName());
				}
			} catch (Exception e) {
			}
		}

		for (Field field : request.getClass().getSuperclass().getDeclaredFields()) {
			try {
				field.setAccessible(true);
				if (field.getName() != null && !field.getName().equals("requestCode")) {
					requestMap.put(field.getInt(request), field.getName());
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Load Response Map!!
	 * 
	 * @param response
	 */
	public static void loadResponseMap(ResponseCode response) {
		for (Field field : response.getClass().getDeclaredFields()) {
			try {
				field.setAccessible(true);
				if (field.getName() != null && !field.getName().equals("responseCode")) {
					responseMap.put(field.getInt(response), field.getName());
				}
			} catch (Exception e) {
			}
		}

		for (Field field : response.getClass().getSuperclass().getDeclaredFields()) {
			try {
				field.setAccessible(true);
				if (field.getName() != null && !field.getName().equals("responseCode")) {
					responseMap.put(field.getInt(response), field.getName());
				}
			} catch (Exception e) {
			}
		}
	}

	private static String getRequestFieldName(RequestPacket request) {
		if (requestMap.containsKey(request.getCode())) {
			return requestMap.get(request.getCode());
		} else {
			return Integer.toString(request.getCode());
		}
	}

	private static String getResponseFieldName(ResponsePacket response) {
		if (responseMap.containsKey(response.getCode())) {
			return responseMap.get(response.getCode());
		} else {
			return Integer.toString(response.getCode());
		}
	}
}