package com.himaginus.common.data;

import java.nio.ByteBuffer;

public interface ResponseData {
	public byte[] toByteArray();
	public void setFromByteBuffer(ByteBuffer bb);
}
