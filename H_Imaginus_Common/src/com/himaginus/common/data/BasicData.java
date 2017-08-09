package com.himaginus.common.data;

public class BasicData<T> extends ResponseData {

	private static final long serialVersionUID = 1L;
	
	private T value;

	public BasicData() {}
	public BasicData(T value) {
		this.value = value;
	}
	
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
}
