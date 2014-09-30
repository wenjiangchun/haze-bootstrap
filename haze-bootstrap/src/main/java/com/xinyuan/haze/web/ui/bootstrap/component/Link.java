package com.xinyuan.haze.web.ui.bootstrap.component;

import com.xinyuan.haze.common.utils.HazeStringUtils;

import java.util.HashSet;
import java.util.Set;

public class Link implements Component {

	private String id;
	private Set<String> classNames = new HashSet<String>();
	private String clickEvent;
	private String text;
	
	private String href;
	
	public Link(String id, String href, Set<String> classNames, String clickEvent,
			String text) {
		this.id = id;
		this.href = href;
		if(classNames != null) {
			this.classNames = classNames;
		}
		this.clickEvent = clickEvent;
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
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
		StringBuffer button = new StringBuffer("<a ");
		if (HazeStringUtils.isNotEmpty(id)) {
			button.append("id=\"");
			button.append(id);
			button.append("\" ");
		}
		if (HazeStringUtils.isNotEmpty(href)) {
			button.append("href=\"");
			button.append(href);
			button.append("\" ");
		} else {
			button.append("href=\"#\" ");
		}
		button.append("class=\"");
		for (String className : classNames) {
			button.append(" ");
			button.append(className);
		}
		button.append("\"");
		if (HazeStringUtils.isNotEmpty(clickEvent)) {
			button.append(" onclick=\"");
			button.append(clickEvent);
			button.append("\"");
		}
		button.append(">");
		if (HazeStringUtils.isNotEmpty(text)) {
			button.append(text);
		}
		button.append("</a>");
		return button.toString();
	}

	@Override
	public void addClass(String className) {
		classNames.add(className);
	}

}
