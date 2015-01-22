package com.xinyuan.haze.web.ui.bootstrap.component;

import com.xinyuan.haze.common.utils.HazeStringUtils;

/**
 * Bootstrap CheckBox
 * 
 * @author 刘伟国 2013-12-19
 * 
 */
public class CheckBox implements Component {

	private String id;
	private String name;
	private String className;
	private String clickEvent;
	private String value;

	public CheckBox(String id, String name, String className, String clickEvent, String value) {
		this.id = id;
		this.name = name;
		this.className = className;
		this.clickEvent = clickEvent;
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClickEvent() {
		return clickEvent;
	}

	public void setClickEvent(String clickEvent) {
		this.clickEvent = clickEvent;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String getHtml() {
		StringBuffer button = new StringBuffer("<input type=\"checkbox\"");
		if (HazeStringUtils.isNotEmpty(id)) {
			button.append(" id=\"");
			button.append(id);
			button.append("\" ");
		}
		
		if (HazeStringUtils.isNotEmpty(name)) {
			button.append(" name=\"");
			button.append(name);
			button.append("\" ");
		}

		if (HazeStringUtils.isNotEmpty(clickEvent)) {
			button.append(" onclick=\"");
			button.append(clickEvent);
			button.append("\"");
		}

		button.append(" value=\"");
		button.append(value);
		button.append("\"");
		button.append(" />");
		return button.toString();
	}

	@Override
	public void addClass(String className) {

	}
}
