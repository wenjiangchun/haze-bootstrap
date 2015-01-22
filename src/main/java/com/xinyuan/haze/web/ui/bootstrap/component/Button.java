package com.xinyuan.haze.web.ui.bootstrap.component;

import com.xinyuan.haze.common.utils.HazeStringUtils;
import com.xinyuan.haze.web.ui.bootstrap.css.ButtonType;

import java.util.HashSet;
import java.util.Set;

/**
 * Bootstrap Button
 * @author wenjiangchun
 *
 */
public class Button implements Component {

	private String id;
	private Set<String> classNames = new HashSet<String>();
	private String clickEvent;
	private String text;
	
	public Button(String id, Set<String> classNames, String clickEvent,
			String text) {
		this.id = id;
		if(classNames != null) {
			this.classNames = classNames;
		}
		this.classNames.add(ButtonType.PRIMARY.getClassName());
		this.classNames.add("btn-small");
		this.clickEvent = clickEvent;
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<String> getClassNames() {
		return classNames;
	}

	public void setClassNames(Set<String> classNames) {
		this.classNames = classNames;
	}

	public String getClickEvent() {
		return clickEvent;
	}

	public void setClickEvent(String clickEvent) {
		this.clickEvent = clickEvent;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String getHtml() {
		StringBuffer button = new StringBuffer("<button ");
		if (HazeStringUtils.isNotEmpty(id)) {
			button.append("id='");
			button.append(id);
			button.append("' ");
		}
		button.append("class='" + ButtonType.DEFAUTL.getClassName());
		for (String className : classNames) {
			button.append(" ");
			button.append(className);
		}
		button.append("'");
		if (HazeStringUtils.isNotEmpty(clickEvent)) {
			button.append(" onclick='");
			button.append(clickEvent);
			button.append("'");
		}
		button.append(">");
		if (HazeStringUtils.isNotEmpty(text)) {
			button.append(text);
		}
		button.append("</button>");
		return button.toString();
	}

	@Override
	public void addClass(String className) {
		classNames.add(className);
	}

}
