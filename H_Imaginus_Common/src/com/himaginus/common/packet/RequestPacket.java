package com.himaginus.common.packet;

import java.nio.ByteBuffer;

import com.himaginus.common.util.CommonUtil;

public class RequestPacket {
	private int requestCode;
	private String context;
	
	public RequestPacket() {}
	
	public RequestPacket(int requestCode, String context) {
		this.requestCode = requestCode;
		this.context = context;
	};
	
	public int getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(int requestCode) {
		this.requestCode = requestCode;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public byte[] toByteArray() {
		byte[] ctxByteArray = CommonUtil.stringToByteArray(context);
		int capacity = CommonUtil.getLengthDataSize() + 4 + ctxByteArray.length;
		ByteBuffer buffer = ByteBuffer.allocate(capacity);
		buffer.putInt(capacity - CommonUtil.getLengthDataSize());
		buffer.putInt(requestCode);
		buffer.put(ctxByteArray);
		return buffer.array();
	}
}
