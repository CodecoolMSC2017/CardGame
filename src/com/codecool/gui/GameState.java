package com.codecool.gui;

import com.codecool.api.Player;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class GameState {
    private Player playerOne;
    private Player playerTwo;
    private Stage stage;
    private Parent root;

    /*private static GameState ourInstance = new GameState();

    public static GameState getInstance() {
        return ourInstance;
    }*/

    public GameState() {
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

    public Parent getRoot() {
        return root;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    /*public static GameState getOurInstance() {
        return ourInstance;
    }*/


}
