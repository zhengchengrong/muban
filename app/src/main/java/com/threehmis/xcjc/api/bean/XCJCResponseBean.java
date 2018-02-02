package com.threehmis.xcjc.api.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XCJCResponseBean<T> {
	private T beans;
	private Boolean verification;
	/** 提示信息 */
	private String message;

	public T getBeans() {
		return beans;
	}

	public void setBeans(T beans) {
		this.beans = beans;
	}

	public Boolean getVerification() {
		return verification;
	}

	public void setVerification(Boolean verification) {
		this.verification = verification;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}
