package com.codecool.gui;

import com.codecool.api.CardReader;
import com.codecool.api.Player;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class NameController {
    public void confirmButton()throws Exception{
        playerOne = new Player(playerOneName.getText());
        playerTwo = new Player(playerTwoName.getText());

        CardReader cr = new CardReader();
        cr.loadDeck(playerOne);
        cr.loadDeck(playerTwo);
        playerOne.drawStartingHand();
        playerTwo.drawStartingHand();
        root = FXMLLoader.load(getClass().getResource("battleScreen.fxml"));
        stage.setScene(new Scene(root,1366,768));

        for (int i=0;i<playerOne.getHand().getCardsInHand().size();i++){
            ImageView card = new ImageView();
            card.setImage(new Image(playerOne.getHand().getCardsInHand().get(i).getUrl()));
            playerOneHand.getChildren().addAll(card);
        }

        for (int i=0;i<playerTwo.getHand().getCardsInHand().size();i++){
            ImageView card = new ImageView(new Image(playerTwo.getHand().getCardsInHand().get(i).getUrl()));
            playerTwoHand.getChildren().add(card);
        }

    }
}
