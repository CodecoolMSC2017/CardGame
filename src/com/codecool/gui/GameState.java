package com.codecool.gui;

import com.codecool.api.Card;
import com.codecool.api.Player;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private Player playerOne;
    private Player playerTwo;
    private Stage stage;
    private boolean military,pride,intrique;
    private boolean recruit = true;
    private List<Card> selectedCards = new ArrayList<>();
    private List<Card> enemySelected = new ArrayList<>();

    public void addToSelected(Card card){
        selectedCards.add(card);
    }

    public void removeFromSelected(Card card){
        selectedCards.remove(card);
    }

    public void addToEnemySelected(Card card){
        enemySelected.add(card);
    }

    public void setMilitary(boolean military) {
        this.military = military;
    }

    public void setPride(boolean pride) {
        this.pride = pride;
    }

    public void setIntrique(boolean intrique) {
        this.intrique = intrique;
    }

    public void setRecruit(boolean recruit) {
        this.recruit = recruit;
    }

    public boolean isMilitary() {
        return military;
    }

    public boolean isPride() {
        return pride;
    }

    public boolean isIntrique() {
        return intrique;
    }

    public boolean isRecruit() {
        return recruit;
    }

    public List<Card> getSelectedCards() {
        return selectedCards;
    }

    public List<Card> getEnemySelected() {
        return enemySelected;
    }

    private String phase="";


    private static GameState ourInstance = null;

    public static GameState getInstance() {
        if (ourInstance == null) {
            ourInstance = new GameState();
        }
        return ourInstance;
    }

    public GameState() {
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public static void setOurInstance(GameState ourInstance) {
        GameState.ourInstance = ourInstance;
    }

    /*public static GameState getOurInstance() {
        return ourInstance;
    }*/


}
