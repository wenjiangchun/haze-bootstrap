package com.xinyuan.haze.web.ui.bootstrap;

import com.xinyuan.haze.web.ui.bootstrap.component.Button;
import com.xinyuan.haze.web.ui.bootstrap.component.CheckBox;
import com.xinyuan.haze.web.ui.bootstrap.component.Component;
import com.xinyuan.haze.web.ui.bootstrap.component.Link;
import com.xinyuan.haze.web.ui.bootstrap.component.Span;
import com.xinyuan.haze.web.ui.bootstrap.css.SpanType;

import java.util.Set;

/**
 * BootStrap组建生成类
 * @author Sofar
 *
 */
public class BootStrapComponentUtils {

	/**
	 * 根据提供Id和文本text创建Span组件，其中SpanType默认为SpanType.DEFAULT
	 * @param id 组件Id
	 * @param text 组件文本内容
	 * @return Component
	 */
	public static Component createSpan(String id, String text) {
		 return new Span(id, text);
	}
	
	/**
	 * 根据提供Id,Span类别，文本text创建Span组件，其中生成的SpanType为SpanType.DEFAULT加上spanType
	 * @param id 组件Id
	 * @param spanType Span类别
	 * @param text 组件文本内容
	 * @return Component
	 */
	public static Component createSpan(String id, SpanType spanType, String text) {
	   return new Span(id,spanType,text);
	}
	
	/**
	 * 创建Span组件，其中Span组件的SpanType为SpanType.DEFAULT,文本内容为text
	 * @param text 文本内容
	 * @return Component
	 */
	public static Component createSpan(String text) {
		   return new Span(text);
	}
	
	public static Component createButton(String id, Set<String> classNames,String clickEvent,String text) {
		return new Button(id, classNames, clickEvent, text);
	}
	public static Component createButton(String text) {
		return new Button(null, null, null, text);
	}
	public static Component createButton(String id, String text) {
		return new Button(id, null, null, text);
	}
	public static Component createButton(String id,Set<String> classNames, String text) {
		return new Button(id, classNames, null, text);
	}
	
	public static Component createLink(String id, String href, Set<String> classNames, String text) {
		return new Link(id, href, classNames, null, text);
	}
	
	public static Component createLink(String id, String text) {
		return new Link(id, null, null, null, text);
	}
	public static Component createLink(String id, String href, String text) {
		return new Link(id, href, null, null, text);
	}
	public static Component createLink(String id, String href, String clickEvent, String text) {
		return new Link(id, href, null, clickEvent, text);
	}
	/**
	 * 创建CheckBox组件
	 * @author 刘伟国 2013-12-19
	 * @param id CheckBox的id
	 * @param name CheckBox的name
	 * @param className CheckBox的class
	 * @param clickEvent CheckBox的onClick事件
	 * @param value CheckBox的value
	 * @return
	 */
	public static Component createCheckBox(String id, String name, String className, String clickEvent, String value) {
		return new CheckBox(id, name, className, clickEvent, value);
	}
}
