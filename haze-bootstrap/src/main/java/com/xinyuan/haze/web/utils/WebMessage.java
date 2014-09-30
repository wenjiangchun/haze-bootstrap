package com.xinyuan.haze.web.utils;

import com.xinyuan.haze.core.spring.utils.SpringContextUtils;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Locale;

/**
 * Web信息类，使用该类向页面输出系统信息
 * 
 * @author sofar
 *
 */
public class WebMessage implements Serializable {

    public static final String ACTION_SUCCESS_MESSAGE = "action_success";

    public static final String ACTION_ERROR_MESSAGE = "action_failure";

	private static final long serialVersionUID = 1L;
	
	/**
	 * 信息内容
	 */
	private String content;
	
	/**
	 * 信息类型
	 */
	private AlertType alertType;
	
	public WebMessage() {
		
	}
	public WebMessage(String content, AlertType alertType) {
		this.content = content;
		this.alertType = alertType;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public AlertType getAlertType() {
		return alertType;
	}

    public void setAlertType(AlertType alertType) {
		this.alertType = alertType;
	}

    /**
     * 创建操作成功信息提示
     * @return WebMessage
     */
    public static WebMessage createSuccessWebMessage() {
        return new WebMessage(ACTION_SUCCESS_MESSAGE, AlertType.SUCCESS);
    }

    /**
     * 创建操作失败信息提示
     * @param errorMessage 错误信息内容
     * @return WebMessage
     */
    public static WebMessage createErrorWebMessage(String errorMessage) {
        return new WebMessage(ACTION_ERROR_MESSAGE + errorMessage, AlertType.ERROR);
    }

    /**
     * 创建国际化操作成功信息提示
     * @return WebMessage
     */
    public static WebMessage createLocaleSuccessWebMessage(Locale locale) {
        String message = SpringContextUtils.getMessage(ACTION_SUCCESS_MESSAGE, null, locale);
        return new WebMessage(message, AlertType.SUCCESS);
    }

    /**
     * 创建国际化操作失败信息提示
     * @param errorMessage 错误信息内容
     * @return WebMessage
     */
    public static WebMessage createErrorWebMessage(String errorMessage, Locale locale) {
        String message = SpringContextUtils.getMessage(ACTION_ERROR_MESSAGE, new String[]{errorMessage}, locale);
        return new WebMessage(message, AlertType.ERROR);
    }
}
