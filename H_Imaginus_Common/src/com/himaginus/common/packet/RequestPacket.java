package com.himaginus.common.packet;

import java.io.Serializable;

public class RequestPacket implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public final static int TEST = 1;
	
	private int code;
	private String context;
	
	public RequestPacket() {}
	public RequestPacket(int code, String context) {
		this.code = code;
		this.context = context;
	};
	
	
	// # Getter / Setter ========================================================
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
}
