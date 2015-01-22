package com.xinyuan.haze.web.utils;

public enum AlertType {
	
	SUCCESS("success"),
	
	ERROR("danger");
	
	private String messageValue;
	
	private AlertType(String messageValue) {
		this.messageValue = messageValue;
	}
	public String getMessageValue() {
		return messageValue;
	}
	public void setMessageValue(String messageValue) {
		this.messageValue = messageValue;
	}
	
}
