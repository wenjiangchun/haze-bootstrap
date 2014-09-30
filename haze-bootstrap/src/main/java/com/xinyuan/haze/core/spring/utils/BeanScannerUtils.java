package com.xinyuan.haze.core.spring.utils;

/**
 * spring注解扫描工具类
 * 
 * @author sofar
 *
 */
public class BeanScannerUtils {

	/**
	 * 需要扫描的包名称
	 */
	private String packageName;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
}
