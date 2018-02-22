package com.codecool.gui;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.awt.event.MouseEvent;

public class BattleController {

    private GameState gm = GameState.getInstance();
    @FXML
    FlowPane playerOneHand;
    @FXML
    FlowPane playerTwoHand;
    @FXML
    FlowPane playerOneBoard;
    @FXML
    FlowPane playerTwoBoard;

    @FXML ImageView recruit;
    @FXML ImageView battle;

    ImageView tmpImg;
    int cardsPlaced;
    DropShadow sh = new DropShadow();



    public void initialize() {

        sh.setWidth(50);
        sh.setHeight(50);
        sh.setColor(Color.RED);
        recruit.setEffect(sh);

        for (int i = 0; i < gm.getPlayerOne().getHand().getCardsInHand().size(); i++) {
            ImageView card = new ImageView();
            card.setFitWidth(115);
            card.setFitHeight(150);
            addHoverEvent(card);
            addHoverOffEvent(card);
            addClickEvent(card);
            card.setImage(new Image(gm.getPlayerOne().getHand().getCardsInHand().get(i).getUrl()));
            playerOneHand.getChildren().addAll(card);
        }








        for (int i = 0; i < gm.getPlayerTwo().getHand().getCardsInHand().size(); i++) {
            ImageView card = new ImageView(new Image(gm.getPlayerTwo().getHand().getCardsInHand().get(i).getUrl()));
            card.setFitWidth(115);
            card.setFitHeight(150);
            card.setRotate(180);
            playerTwoHand.getChildren().add(card);
        }
    }

    public void addHoverEvent(ImageView card){
        card.setOnMouseEntered(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                handHover();
            }

        });

    }

    public void addClickEvent(ImageView card){
        card.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                if(!card.getParent().equals(playerOneBoard)){
                    playerOneBoard.getChildren().add(card);
                    cardsPlaced++;
                    card.setFitHeight(card.getFitHeight()-20);
                    card.setFitWidth(card.getFitWidth()-20);
                    playerOneHand.getChildren().remove(card);
                    if(cardsPlaced==2){
                        System.out.println("next phase");
                        playerOneHand.setDisable(true);
                        recruit.setEffect(null);
                        battle.setEffect(sh);


                    }
                }else{
                    DropShadow sd = new DropShadow();
                    sd.setColor(Color.TEAL);
                    sd.setSpread(0.3);
                    sd.setHeight(50);
                    sd.setWidth(50);

                    card.setEffect(sd);
                }

            }

        });
    }

    public void addHoverOffEvent(ImageView card){
        card.setOnMouseExited(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                handHoverOff();
            }

        });

    }

    public void handHover(){

        tmpImg = new ImageView();

        for(int i=0;i<playerOneHand.getChildren().size();i++){
            if(playerOneHand.getChildren().get(i).isHover()){

                tmpImg=(ImageView)playerOneHand.getChildren().get(i);
                break;
            }
        }
        for(int i=0;i<playerOneBoard.getChildren().size();i++){
            if(playerOneBoard.getChildren().get(i).isHover()){

                tmpImg=(ImageView)playerOneBoard.getChildren().get(i);
                break;
            }
        }
        ScaleTransition st = new ScaleTransition(Duration.millis(250),tmpImg );
        st.setByX(0.3f);
        st.setByY(0.3f);
        st.setCycleCount(1);
        if(!tmpImg.getParent().equals(playerOneBoard)){
            TranslateTransition tt = new TranslateTransition(Duration.millis(250),tmpImg );
            tt.setToY(-60f);
            tt.setCycleCount(1);
            tt.play();
        }
        st.play();


    }

    public void handHoverOff(){
        ScaleTransition st = new ScaleTransition(Duration.millis(250), tmpImg);
        st.setByX(-(tmpImg.getScaleX()-1));
        st.setByY(-(tmpImg.getScaleY()-1));
        st.setCycleCount(1);
        TranslateTransition tt = new TranslateTransition(Duration.millis(250),tmpImg );
        tt.setToY(1);
        tt.setCycleCount(1);

        tt.play();
        st.play();


    }


}
