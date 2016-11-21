package com.xinyuan.haze.system.utils;

/**
 * 资源类型枚举类
 * @author Sofar
 *
 */
public enum ResourceType {

	/**
	 * 操作资源
	 */
	A("操作资源"), 
	
	/**
	 * 菜单资源
	 */
	M("菜单资源");
	private String typeName;
	
	ResourceType(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

}
