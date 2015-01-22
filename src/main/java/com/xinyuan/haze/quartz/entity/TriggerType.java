package com.xinyuan.haze.quartz.entity;

/**
 * 调度类型枚举类
 * 
 * @author sofar
 *
 */
public enum TriggerType {

	/**
	 * 简单调度
	 */
	SIMPLE("简单调度"),
	
	/**
	 * CRON表达式调度
	 */
	CRON("CRON表达式调度");
	
	/**
	 * 类型说明
	 */
	private String tName;
	
	private TriggerType(String tName) {
		this.tName = tName;
	}
	public String gettName() {
		return tName;
	}
	public void settName(String tName) {
		this.tName = tName;
	}
	
}
