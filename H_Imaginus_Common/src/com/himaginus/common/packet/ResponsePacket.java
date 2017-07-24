package com.himaginus.common.packet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.himaginus.common.data.ResponseData;

public class ResponsePacket implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static int TEST = 1;

	private int code;
	private boolean success;
	private List<ResponseData> dataList = new ArrayList<ResponseData>();

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void addResponseData(ResponseData response) {
		dataList.add(response);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends ResponseData> List<T> getResponseDataList() {
		return (List<T>) dataList;
	}
	
	/**
	 * @param index : List에서 뽑아낼 데이터의 번지수
	 * @return T : <T> return T
	 */
	@SuppressWarnings("unchecked")
	public <T extends ResponseData> T getData(int index){
		return (T) dataList.get(index);
	}
}
