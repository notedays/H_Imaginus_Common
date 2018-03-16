package com.himaginus.common.packet;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.himaginus.common.data.ResponseData;

public class ResponsePacket implements Externalizable, ResponseCode {

	private static final long serialVersionUID = 1L;

	private int code = -1;
	private boolean success = true;
	private List<ResponseData> dataList = new ArrayList<ResponseData>();
	
	public ResponsePacket() {
	}
	public ResponsePacket(int responseCode) {
		this.code = responseCode;
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(code);
		out.writeBoolean(success);
		out.writeInt(dataList.size());
		if (dataList.isEmpty()) {
			out.writeUTF(dataList.get(0).getClass().getName());
			for (ResponseData data : dataList) {
				data.writeExternal(out);
			}
		}
		out.flush();
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		code = in.readInt();
		success = in.readBoolean();
		int dataSize = in.readInt();
		if (dataSize > 0) {
			Class<?> dataClass = Class.forName(in.readUTF());
			for (int i = 0; i < dataSize; i++) {
				try {
					ResponseData data = (ResponseData) dataClass.newInstance();
					data.readExternal(in);
					dataList.add(data);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param index : the Index that you want to get a data from List
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public <T extends ResponseData> T getData(int index) {
		return (T) dataList.get(index);
	}

	// # Getter / Setter 
	public int getCode() {
		return code;
	}

	public ResponsePacket setCode(int code) {
		this.code = code;
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public ResponsePacket setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public ResponsePacket addResponseData(ResponseData response) {
		dataList.add(response);
		return this;
	}
	
	public ResponsePacket addAll(Collection<? extends ResponseData> datas) {
		dataList.addAll(datas);
		return this;
	}

	public List<ResponseData> getResponseDataList() {
		return dataList;
	}

}
