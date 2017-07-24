package com.himaginus.common.data;

public class BasicData<T> implements ResponseData {

	private static final long serialVersionUID = 1L;
	
	private T msg;

	public BasicData() {}
	public BasicData(T msg) {
		this.msg = msg;
	}
	
	public T getMsg() {
		return msg;
	}
	public void setMsg(T msg) {
		this.msg = msg;
	}
}
