package com.javaschool.microecare.utils;

public enum EntityActions {
    CREATE("created"),
    UPDATE("updated"),
    DELETE("deleted"),
    READ("viewed");

    private final String text;

    EntityActions(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
