package com.xinyuan.haze.system.exception;

import com.xinyuan.haze.core.jpa.service.HazeServiceException;

/**
 * 角色已存在异常类
 * @author Sofar
 *
 */
public class RoleExistException extends HazeServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RoleExistException() {
		super();
	}
	
	public RoleExistException(String message) {
		super(message);
	}
}
