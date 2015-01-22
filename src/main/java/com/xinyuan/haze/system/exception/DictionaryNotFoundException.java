package com.xinyuan.haze.system.exception;

/**
 * 字典未找到异常类。当系统未配置对应字典代码时抛出该异常。
 *
 * @作者 温江春
 * @创建日期 2014年4月9日
 * @工程 12110短信报警平台
 */
public class DictionaryNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DictionaryNotFoundException() {
		super();
	}
	
	public DictionaryNotFoundException(String message) {
		super(message);
	}
}
