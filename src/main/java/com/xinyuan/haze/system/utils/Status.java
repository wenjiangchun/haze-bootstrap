package com.xinyuan.haze.system.utils;

/**
 * 状态枚举类
 * 
 * @author Sofar
 * 
 */
public enum Status {

	DISABLE("禁用"),

	ENABLE("启用"),

	LOCK("锁定");
	
	private String statusName;

	 Status(String statusName) {
		this.statusName = statusName;
	}

	public String getStatusName() {
		return statusName;
	}

}
