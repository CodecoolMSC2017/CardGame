package com.codecool.game;

public enum MainMenu {
    PLAY("New game"),
    EXIT("Exit");

    final private String value;

    MainMenu(String s) {
        value = s;
    }

    public String getValue() { return value; }
}
