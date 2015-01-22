package com.xinyuan.haze.web.ui.bootstrap.css;

import com.xinyuan.haze.system.utils.Status;

public enum SpanType {
	
	DEFAULT("label"),Warning("label-warning"),Success("label-success"),Import("label-important"),Inverse("label-inverse"),Info("label-info");
	
	private String messageValue;
	
	private SpanType(String messageValue) {
		this.messageValue = messageValue;
	}
	public String getMessageValue() {
		return messageValue;
	}
	public void setMessageValue(String messageValue) {
		this.messageValue = messageValue;
	}
	
	public static SpanType getSpanTypeByStatus(Status status) {
		if (null == status) {
			return SpanType.DEFAULT;
		}
		if (status.equals(Status.E)) {
			return  SpanType.Success;
		} else if (status.equals(Status.D)) {
			return SpanType.DEFAULT;
		} else {
			return SpanType.DEFAULT;
		}
	}
}
