package com.himaginus.common.util;

import java.util.Arrays;

import com.himaginus.common.callback.CallBack;

public class Variance<T> {
	T t;
	
	public Variance(T t){
		this.t = t;
	}
	
	/**
	 * # T 타입이 Null이 아니라면 callback 함수 동작
	 * @param callback
	 */
	public void ifNotNull(CallBack callback) {
		if(!isNull()) {
			callback.call();
		}
	}
	
	public boolean isNull() {
		return this.t == null;
	}
	
	/**
	 * # T 타입의 Null이 방지하는 변수를 찾기 위한 메소드
	 * @return
	 */
	public T nonNull() {
		if(isNull()) {
			throw new NullPointerException();
		}
		return this.t;
	}
	
	/**
	 * # T 타입이 파라미터의 배열에 포함된 값인지 여부 판단
	 * @param ts
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean isIn(T...ts) {
		return Arrays.asList(ts).contains(this.t);
	}
}
