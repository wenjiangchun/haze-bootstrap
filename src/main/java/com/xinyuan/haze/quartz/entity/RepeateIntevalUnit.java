package com.xinyuan.haze.quartz.entity;

/**
 * 执行时间间隔枚举类
 * 
 * @author sofar
 *
 */
public enum RepeateIntevalUnit {

	HOUR("小时"),

	MINUTE("分钟"),

	SECOND("秒"),

	MILLISECOND("毫秒");

	private String unitName;

	private RepeateIntevalUnit(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

}
