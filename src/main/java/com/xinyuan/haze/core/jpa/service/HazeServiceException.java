package com.xinyuan.haze.core.jpa.service;

/**
 * Service层公用的Exception. Spring默认对RuntimeException类型的异常进行事务回滚
 * 
 * 从由Spring管理事务的函数中抛出时会触发事务回滚.
 * 
 * @author sofar
 */
public class HazeServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public HazeServiceException() {
		super();
	}

	public HazeServiceException(String message) {
		super(message);
	}

	public HazeServiceException(Throwable cause) {
		super(cause);
	}

	public HazeServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
