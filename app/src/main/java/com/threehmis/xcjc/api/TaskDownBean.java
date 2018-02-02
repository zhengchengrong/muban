package com.threehmis.xcjc.api;

public class TaskDownBean {

	private String jcTaskXdManId;
	private String customerId;

	public String getJcTaskXdManId() {
		return jcTaskXdManId;
	}

	public void setJcTaskXdManId(String jcTaskXdManId) {
		this.jcTaskXdManId = jcTaskXdManId;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	private String pageNum;
	private String pageSize;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
}
