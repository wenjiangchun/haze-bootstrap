package com.xinyuan.haze.system.utils;

/**
 * 系统配置类型枚举类
 * 
 * @author sofar
 *
 */
public enum ConfigType {

	/**
	 * 系统配置
	 */
	S("系统配置"), 
	
	/**
	 * 业务配置
	 */
	B("业务配置");
	private String typeName;

	private ConfigType(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

    @Override
    public String toString() {
        return getTypeName();
    }
}
