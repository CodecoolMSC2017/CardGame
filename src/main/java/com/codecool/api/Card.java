package com.codecool.api;

public class Card {
    private String name;
    private int military;
    private int intrique;
    private int fame;
    private boolean state;

    public Card(String name, int military, int intrique, int fame) {
        this.name = name;
        this.military = military;
        this.intrique = intrique;
        this.fame = fame;
        this.state = false;
    }

    public String getName() {
        return name;
    }

    public int getMilitary() {
        return military;
    }

    public int getIntrique() {
        return intrique;
    }

    public int getFame() {
        return fame;
    }

    public boolean isState() {
        return state;
    }

    public void setState(){
        state=!state;
    }

    @Override
    public String toString() {
        return name + "{m:" + military + ", i:" + intrique + ", f:" + fame + "} " + (state ? "Active" : "Tired");
    }
}
