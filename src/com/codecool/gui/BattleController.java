package com.codecool.gui;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class BattleController {


    @FXML
    FlowPane playerOneHand;
    @FXML
    FlowPane playerTwoHand;
    @FXML
    FlowPane playerOneBoard;
    @FXML
    FlowPane playerTwoBoard;
    static GameState gm;
    public void initialize() {

        gm=GuiMain.gm;

        for (int i = 0; i < gm.getPlayerOne().getHand().getCardsInHand().size(); i++) {
            ImageView card = new ImageView();
            card.setImage(new Image(gm.getPlayerOne().getHand().getCardsInHand().get(i).getUrl()));
            playerOneHand.getChildren().addAll(card);
        }


        for (int i = 0; i < gm.getPlayerTwo().getHand().getCardsInHand().size(); i++) {
            ImageView card = new ImageView(new Image(gm.getPlayerTwo().getHand().getCardsInHand().get(i).getUrl()));
            playerTwoHand.getChildren().add(card);
        }
    }
}
