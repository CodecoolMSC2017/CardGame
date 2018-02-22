package com.codecool.gui;

import com.codecool.api.CardReader;
import com.codecool.api.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.codecool.api.Player;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

public class NameController {

    private GameState gm = GameState.getInstance();
    @FXML
    private AnchorPane ap;
    @FXML
    TextField playerOneName;
    @FXML
    TextField playerTwoName;
    @FXML
    ImageView confirmButton;

    public void confirmButtonHover(){
        AudioClip mApplause = new AudioClip(this.getClass().getResource("../../../sound/hoverSound.wav").toExternalForm());
        mApplause.play();
        confirmButton.setScaleX(1.1);
        confirmButton.setScaleY(1.1);

    }

    public void confirmButtonHoverOff(){
        confirmButton.setScaleX(1);
        confirmButton.setScaleY(1);
    }

    public void confirmButton()throws Exception{
        Player playerOne = new Player(playerOneName.getText());
        Player playerTwo = new Player(playerTwoName.getText());

        CardReader cr = new CardReader();
        cr.loadDeck(playerOne);
        cr.loadDeck(playerTwo);
        playerOne.drawStartingHand();
        playerTwo.drawStartingHand();
        Parent root = FXMLLoader.load(getClass().getResource("battleScreen.fxml"));
        Stage stage = (Stage) ap.getScene().getWindow();
        stage.setScene(new Scene(root,1280,720));

        /*for (int i=0;i<playerOne.getHand().getCardsInHand().size();i++){
            ImageView card = new ImageView();
            card.setImage(new Image(playerOne.getHand().getCardsInHand().get(i).getUrl()));
            playerOneHand.getChildren().addAll(card);
        }


        for (int i=0;i<playerTwo.getHand().getCardsInHand().size();i++){
            ImageView card = new ImageView(new Image(playerTwo.getHand().getCardsInHand().get(i).getUrl()));
            playerTwoHand.getChildren().add(card);
        }*/

        gm.setPlayerOne(playerOne);
        gm.setPlayerTwo(playerTwo);
    }
}