package com.himaginus.common.packet;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.himaginus.common.data.ResponseData;
import com.himaginus.common.util.CommonUtil;

public class ResponsePacket {
	 protected int responseCode;
	    private List<ResponseData> responseData = new ArrayList<ResponseData>();

//	    public int getResponseProcessorType() {
//	        int responseProcessor = RESPONSE_PROCESSOR_NONE;
//	        return responseProcessor;
//	    }

	    public int getResponseCode() {
	        return responseCode;
	    }

	    public void setResponseCode(int responseCode) {
	        this.responseCode = responseCode;
	    }

	    public void addResponseData(ResponseData response) {
	        responseData.add(response);
	    }

	    public List<ResponseData> getResponseDataList() {
	        return responseData;
	    }

	    public static ResponsePacket fromByteArray(byte[] responseByte) {
	        return fromByteArray(responseByte, true, ResponsePacket.class);
	    }

	    public static <T extends ResponsePacket> T fromByteArray(byte[] responseByte, Class<T> typeClass) {
	        return fromByteArray(responseByte, true, typeClass);
	    }

	    public static <T extends ResponsePacket> T fromByteArray(byte[] responseByte, boolean skipLengthData, Class<T> typeClass) {
	        ByteBuffer bb = ByteBuffer.wrap(responseByte);
	        T returnPacket = null;
	        try {
	            returnPacket = typeClass.newInstance();
	        } catch (Exception e) {
	        }
	        if (!skipLengthData) {
	            bb.getInt();
	        }
	        returnPacket.setResponseCode(bb.getInt());
	        int byteDataSize = bb.getInt();
	        for (int i = 0; i < byteDataSize; i++) {
	            ResponseData responseData = null;
	            try {
	                Method m = typeClass.getDeclaredMethod("getEmptyMafiaResponseData", Integer.TYPE);
	                m.setAccessible(true);

	                responseData = (ResponseData) m.invoke(null, returnPacket.getResponseCode());
	            } catch (Exception e) {
	                RuntimeException rte = new RuntimeException(e);
	            }
	            responseData.setFromByteBuffer(bb);
	            returnPacket.addResponseData(responseData);
	        }
	        return returnPacket;
	    }

	    static protected ResponseData getEmptyResponseData(int responseCode) {
	        return null;
	    }

	    public byte[] toByteArray() {
	        int capacity = CommonUtil.getLengthDataSize() + 8;
	        List<byte[]> byteDataList = new ArrayList<byte[]>();
	        for (ResponseData subData : responseData) {
	            byte[] curByteData = subData.toByteArray();
	            capacity = capacity + curByteData.length;
	            byteDataList.add(curByteData);
	        }
	        ByteBuffer buffer = ByteBuffer.allocate(capacity);
	        buffer.putInt(capacity - CommonUtil.getLengthDataSize());
	        buffer.putInt(responseCode);
	        buffer.putInt(byteDataList.size());
	        for (byte[] curByteData : byteDataList) {
	            buffer.put(curByteData);
	        }
	        return buffer.array();
	    }
}
