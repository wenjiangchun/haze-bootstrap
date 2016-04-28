package com.xinyuan.haze.workflow.exception;
/**
 * 流程部署失败异常定义类
 * @author Sofar
 *
 */
public class DeployFailureException extends Exception{

	private static final long serialVersionUID = 1L;

	public DeployFailureException(String message) {
		super(message);
	}

}
