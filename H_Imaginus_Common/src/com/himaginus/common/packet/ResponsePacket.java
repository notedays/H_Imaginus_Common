package com.himaginus.common.packet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.himaginus.common.data.ResponseData;

public class ResponsePacket implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static int TEST = 0;
	public final static int REGIST = 1;
	
	private int code;
	private boolean success = true;
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
	
	public List<ResponseData> getResponseDataList() {
		return dataList;
	}
	
	/**
	 * @param index : the Index that you want to get a data from List
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public <T extends ResponseData> T getData(int index){
		return (T) dataList.get(index);
	}
}
