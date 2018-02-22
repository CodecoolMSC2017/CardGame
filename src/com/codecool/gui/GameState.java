package com.codecool.gui;

import com.codecool.api.Player;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class GameState {
    private Player playerOne;
    private Player playerTwo;
    private Stage stage;

    private static GameState ourInstance = null;

    public static GameState getInstance() {
        if (ourInstance == null) {
            ourInstance = new GameState();
        }
        return ourInstance;
    }

    private GameState() {
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

    public static GameState getOurInstance() {
        return ourInstance;
    }
}
