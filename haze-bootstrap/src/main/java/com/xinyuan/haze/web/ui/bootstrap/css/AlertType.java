package com.xinyuan.haze.web.ui.bootstrap.css;

public enum AlertType {
	
	BLOCK("block"),SUCCESS("success"),ERROR("error"),INFO("info");
	
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
