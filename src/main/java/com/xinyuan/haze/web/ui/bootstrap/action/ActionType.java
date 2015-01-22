package com.xinyuan.haze.web.ui.bootstrap.action;

public enum ActionType {

	ADD("增加"),EDIT("编辑"),DELETE("删除"),BATCHDELETE("批量删除"),VIEW("查看");
    
	private String actionName;
	
	private ActionType(String actionName) {
		this.actionName = actionName;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	
	
	
}
