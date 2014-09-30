package com.xinyuan.haze.web.utils;

public enum ActionType {

    ADD("add"),

    DELETE("delete"),

    EDIT("edit");

    private String messageValue;

    private ActionType(String messageValue) {
        this.messageValue = messageValue;
    }

    public String getMessageValue() {
        return messageValue;
    }

    public void setMessageValue(String messageValue) {
        this.messageValue = messageValue;
    }

}
