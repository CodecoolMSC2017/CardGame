package com.codecool.gui;

import com.codecool.api.Card;
import com.codecool.api.Hand;
import com.codecool.api.Player;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.event.MouseEvent;
import java.io.IOException;

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
    @FXML
    Button militaryPhaseButton;
    @FXML Button intriquePhaseButton;
    @FXML Button famePhaseButton;
    @FXML
    Label playerOneDeckSize;
    Stage phaseChooseStage;
    Parent phaseChoose;


    ImageView tmpImg;
    int cardsPlaced;
    DropShadow sh = new DropShadow();

    public void setMilitaryPhase(){
        gm.setPhase("military");


    }

    public void setIntriquePhase(){
        gm.setPhase("intrique");

    }

    public void setFamePhase(){
        gm.setPhase("fame");

    }

    public void printResult(){
        for(int k=0;k<gm.getSelectedCards().size();k++){
            for(Node i : playerOneBoard.getChildren()){
                ImageView tmp = (ImageView) i;
                String url = "";
                url+=tmp.getImage().getUrl().split("/")[tmp.getImage().getUrl().split("/").length-3]+"/";
                url+=tmp.getImage().getUrl().split("/")[tmp.getImage().getUrl().split("/").length-2]+"/";
                url+=tmp.getImage().getUrl().split("/")[tmp.getImage().getUrl().split("/").length-1];
                System.out.println(url);
                if(url.equals(gm.getSelectedCards().get(k).getLink())){
                    tmp.setEffect(null);
                    gm.getSelectedCards().remove(tmp);
                }
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(evaluate().getName());
        alert.show();
    }

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
            public void handle(javafx.scene.input.MouseEvent event){
                if(!card.getParent().equals(playerOneBoard)){
                    playerOneBoard.getChildren().add(card);
                    String [] path = card.getImage().getUrl().split("/");
                    String url="";
                    url+=path[path.length-3]+"/"+path[path.length-2]+"/"+path[path.length-1];
                    for(int i=0;i<gm.getPlayerOne().getHand().getCardsInHand().size();i++){
                        if(gm.getPlayerOne().getHand().getCardsInHand().get(i).getLink().equals(url)){
                            gm.getPlayerOne().playFromHand(i);
                        }
                    }
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
                    if(card.getEffect()!=null){
                        card.setEffect(null);
                        String [] path = card.getImage().getUrl().split("/");
                        String url="";
                        url+=path[path.length-3]+"/"+path[path.length-2]+"/"+path[path.length-1];
                        for(int l=0;l<gm.getPlayerOne().getBoard().getOnBoard().size();l++){
                            if(url.equals(gm.getPlayerOne().getBoard().getOnBoard().get(l).getLink())){
                                gm.removeFromSelected(gm.getPlayerOne().getBoard().getOnBoard().get(l));

                                break;
                            }
                        }
                    }else{

                        String [] path = card.getImage().getUrl().split("/");
                        String url="";
                        url+=path[path.length-3]+"/"+path[path.length-2]+"/"+path[path.length-1];
                        for(int l=0;l<gm.getPlayerOne().getBoard().getOnBoard().size();l++){
                            if(url.equals(gm.getPlayerOne().getBoard().getOnBoard().get(l).getLink())){
                                if(gm.getPlayerOne().getBoard().getOnBoard().get(l).isState()){
                                    gm.addToSelected(gm.getPlayerOne().getBoard().getOnBoard().get(l));
                                    card.setEffect(sd);
                                }else{
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setHeaderText("That unit is Tired");
                                    alert.showAndWait();
                                }

                                break;
                            }
                        }
                    }
                    System.out.println(gm.getSelectedCards().size());



                }

            }

        });
    }

    public ImageView getImageViewByCard(Card card,FlowPane hand){

        for(int i=0;i<hand.getChildren().size();i++){
            ImageView tmp = (ImageView) hand.getChildren().get(i);

            String [] path = tmp.getImage().getUrl().split("/");

            String url="";
            url+=path[path.length-3]+"/"+path[path.length-2]+"/"+path[path.length-1];

            if(card.getLink().equals(url)){
                return tmp;
            }


            }
        return null;
        }

        public void changeTurn() throws Exception{
            if (gm.getPlayerOne().getDeck().getCardList().size() == 0) {
                Alert loseAlert = new Alert(Alert.AlertType.INFORMATION);
                loseAlert.setHeaderText("You Lost");
                loseAlert.showAndWait();

                Parent root =FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
                Stage mainS = new Stage();
                mainS.setScene(new Scene(root,1280,720));
                mainS.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Changing turn");
                alert.showAndWait();
                militaryPhaseButton.setDisable(false);
                intriquePhaseButton.setDisable(false);
                famePhaseButton.setDisable(false);

                int remainedCards = gm.getPlayerOne().getHand().getCardsInHand().size();

                if (gm.getPlayerOne().getDeck().getCardList().size() != 0) {
                    for (int i = 0; i < 5 - remainedCards; i++) {
                        gm.getPlayerOne().draw();
                    }
                } else {

                }


                for (int i = remainedCards; i < 5; i++) {
                    ImageView card = new ImageView();
                    card.setFitWidth(115);
                    card.setFitHeight(150);
                    addHoverEvent(card);
                    addHoverOffEvent(card);
                    addClickEvent(card);
                    card.setImage(new Image(gm.getPlayerOne().getHand().getCardsInHand().get(i).getUrl()));
                    playerOneHand.getChildren().addAll(card);
                }
                playerOneDeckSize.setText(Integer.toString(gm.getPlayerOne().getDeck().getCardList().size()));
                for (Card c : gm.getPlayerOne().getBoard().getOnBoard()) {
                    if (c.isState() == false) {
                        c.setState();
                    }
                }
                cardsPlaced = 0;
                playerOneHand.setDisable(false);


            }
        }

    public Player evaluate() {
        int playerOnePoint = 0;
        int playerTwoPoint = 0;
        if (gm.getPhase().equals("military")) {
            for (Card c : gm.getSelectedCards()) {
                playerOnePoint += c.getMilitary();
                c.setState();
            }
            for (Card cr : gm.getEnemySelected()) {
                playerTwoPoint += cr.getMilitary();
                cr.setState();
            }
            if (playerOnePoint > playerTwoPoint) {
                playerOnePoint = 0;
                playerTwoPoint = 0;
                militaryPhaseButton.setDisable(true);
                if(gm.getPlayerTwo().getBoard().getOnBoard().size()>0){

                    gm.getPlayerTwo().getBoard().getOnBoard().remove(gm.getPlayerTwo().getBoard().getRandomCard());

                }
                return gm.getPlayerOne();

            } else {
                playerOnePoint = 0;
                playerTwoPoint = 0;
                militaryPhaseButton.setDisable(true);
                if(gm.getPlayerOne().getBoard().getOnBoard().size()>0){

                    gm.getPlayerOne().getBoard().getOnBoard().remove(gm.getPlayerOne().getBoard().getRandomCard());
                }
                return gm.getPlayerTwo();
            }
        } else if (gm.getPhase().equals("intrique")) {
            for (Card c : gm.getSelectedCards()) {
                playerOnePoint += c.getIntrique();
                c.setState();
            }
            for (Card cr : gm.getEnemySelected()) {
                playerTwoPoint += cr.getIntrique();
                cr.setState();
            }
            if (playerOnePoint > playerTwoPoint) {
                playerOnePoint = 0;
                playerTwoPoint = 0;
                intriquePhaseButton.setDisable(true);
                if(gm.getPlayerTwo().getHand().getCardsInHand().size()!=0){
                    Card tmpCard = gm.getPlayerTwo().getHand().getRandomCard();
                    playerTwoHand.getChildren().remove(getImageViewByCard(tmpCard,playerTwoHand));
                    gm.getPlayerTwo().getHand().discard();
                }
                return gm.getPlayerOne();
            } else {
                playerOnePoint = 0;
                playerTwoPoint = 0;
                intriquePhaseButton.setDisable(true);
                if(gm.getPlayerOne().getHand().getCardsInHand().size()!=0){
                    Card tmpCard = gm.getPlayerOne().getHand().getRandomCard();
                    playerOneHand.getChildren().remove(getImageViewByCard(tmpCard,playerOneHand));
                    gm.getPlayerOne().getHand().discard();
                }
                System.out.println(gm.getPlayerOne().getHand().getCardsInHand().size());
                return gm.getPlayerTwo();
            }
        } else if (gm.getPhase().equals("fame")) {
            for (Card c : gm.getSelectedCards()) {
                playerOnePoint += c.getFame();
                c.setState();
            }
            for (Card cr : gm.getEnemySelected()) {
                playerTwoPoint += cr.getFame();
                cr.setState();
            }
            if (playerOnePoint > playerTwoPoint) {
                playerOnePoint = 0;
                playerTwoPoint = 0;
                famePhaseButton.setDisable(true);
                return gm.getPlayerOne();
            } else {
                playerOnePoint = 0;
                playerTwoPoint = 0;
                famePhaseButton.setDisable(true);
                return gm.getPlayerTwo();
            }
        }
        return gm.getPlayerOne();
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

    public void phaseHandler(){
        if(gm.getPhase().equals("military")){

        }
    }


}
