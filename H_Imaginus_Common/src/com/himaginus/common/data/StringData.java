package com.himaginus.common.data;

import java.nio.ByteBuffer;

import com.himaginus.common.util.CommonUtil;

public class StringData implements ResponseData {

	public StringData() {}
	public StringData(String text) {
		this.text = text;
	}
	
	private String text;
	
	@Override
	public byte[] toByteArray() {
		byte[] bytes = CommonUtil.stringToByteArray(text);
		ByteBuffer bb = ByteBuffer.allocate(bytes.length);
		bb.putInt(bytes.length);
		bb.put(bytes);
		return bb.array();
	}

	@Override
	public void setFromByteBuffer(ByteBuffer bb) {
		byte[] bytes = new byte[bb.getInt()];
		bb.get(bytes);
		text = CommonUtil.byteArrayToString(bytes);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
