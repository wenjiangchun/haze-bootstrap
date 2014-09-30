package com.xinyuan.haze.system.exception;

import com.xinyuan.haze.core.jpa.service.HazeServiceException;

/**
 * 系统配置名称已存在异常定义类
 * @author sofar
 *
 */
public class ConfigNameExistException extends HazeServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConfigNameExistException() {
		super();
	}

	public ConfigNameExistException(String message) {
		super(message);
	}

}
