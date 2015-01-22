package com.xinyuan.haze.system.utils;

/**
 * 状态枚举类
 * 
 * @author Sofar
 * 
 */
public enum Status {

	/**
	 * 启用
	 */
	E("启用"), 
	
	/**
	 * 禁用
	 */
	D("禁用");
	
	private String statusName;

	private Status(String statusName) {
		this.statusName = statusName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
}
