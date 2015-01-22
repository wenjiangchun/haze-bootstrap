package com.xinyuan.haze.web.ui.bootstrap.css;

public enum ButtonType {

	DEFAUTL("btn"),
	PRIMARY("btn-primary"),
	INFO("btn-info"),
	SUCCESS("btn-success"),
	WARING("btn-warning"),
	DANGER("btn-danger"),
	INVERSE("btn-inverse"),
	LINK("btn-link");
	
	private String className;
	
	private ButtonType(String className) {
		this.className = className;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
}
