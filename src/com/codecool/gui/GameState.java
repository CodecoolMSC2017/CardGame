package com.codecool.gui;

import com.codecool.api.Card;
import com.codecool.api.Player;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private List<Player> players = new ArrayList<>();
    private Player playerOne;
    private Player playerTwo;
    private List<Card> selectedCards = new ArrayList<>();
    private List<Card> enemySelected = new ArrayList<>();

    private static GameState ourInstance = null;

    public static GameState getInstance() {
        if (ourInstance == null) {
            ourInstance = new GameState();
        }
        return ourInstance;
    }

    private GameState() {
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

    public void addToSelected(Card card){
        selectedCards.add(card);
    }

    public void removeFromSelected(Card card){
        selectedCards.remove(card);
    }

    public List<Card> getSelectedCards() {
        return selectedCards;
    }

    public List<Card> getEnemySelected() {
        return enemySelected;
    }

    private String phase="";

    public List<Player> getPlayers() {
        return players;
    }

    public void addToPlayers(Player player) {
        players.add(player);
    }
}
