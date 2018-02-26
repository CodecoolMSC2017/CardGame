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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

public class BattleController {

    private GameState gm = GameState.getInstance();
    private int recruited = 0;
    private int battlesStarted = 0;
    private Player activePlayer;
    private Player inactivePlayer;
    @FXML
    AnchorPane ap;
    @FXML
    FlowPane playerOneHand;
    @FXML
    FlowPane playerTwoHand;
    @FXML
    FlowPane playerOneBoard;
    @FXML
    FlowPane playerTwoBoard;
    @FXML
    ImageView recruit;
    @FXML
    ImageView battle;
    @FXML
    ImageView endStep;
    @FXML
    Button militaryPhaseButton;
    @FXML
    Button intriquePhaseButton;
    @FXML
    Button famePhaseButton;
    @FXML
    Label playerOneDeckSize;
    @FXML
    Label playerTwoDeckSize;
    @FXML
    Label nameFieldOne;
    @FXML
    Label nameFieldTwo;
    @FXML
    Button defense;
    private ImageView tmpImg;
    private DropShadow sh = new DropShadow();

    public void initialize() {
        sh.setWidth(50);
        sh.setHeight(50);
        sh.setColor(Color.RED);
        recruit.setEffect(sh);
        activePlayer = getActivePlayer();
        inactivePlayer = getInactivePlayer();
        nameFieldOne.setText(activePlayer.getName());
        nameFieldTwo.setText(inactivePlayer.getName());

        if (activePlayer.getBoard().getOnBoard().size() > 0 || inactivePlayer.getBoard().getOnBoard().size() > 0) {
            boardWipe();
            boardFill();
        }

        handFill();

        playerOneDeckSize.setText(Integer.toString(getActivePlayer().getDeck().getCardList().size()));
        playerTwoDeckSize.setText(Integer.toString(inactivePlayer.getDeck().getCardList().size()));
        for (Card c : getActivePlayer().getBoard().getOnBoard()) {
            if (!c.isState()) {
                c.setState();
            }
        }
        recruitPhase();
    }


    private void recruitPhase() {
        for (Node node : playerOneHand.getChildren()) {
            ImageView card = (ImageView) node;
            addPlayable(card);
        }
    }


    private void battlePhase() {
        for (Node node : playerOneBoard.getChildren()) {
            ImageView card = (ImageView) node;
            addDeclarable(card);
        }
    }


    private void declareBlockers() {
        defense.setText("Selection done");
        defense.setVisible(true);
        defense.setDisable(false);
        for (Node node : playerTwoBoard.getChildren()) {
            ImageView card = (ImageView) node;
            addBlockable(card);
        }
    }


    @FXML
    private void drawToFive() {
        battle.setEffect(null);
        endStep.setEffect(sh);
        int remainedCards = activePlayer.getHand().getCardsInHand().size();
        boolean deckHasCards = activePlayer.drawAfterTurn();
        if (!deckHasCards) {
            if (getActivePlayer().getBoard().getOnBoard().size() < 1 && getActivePlayer().getHand().getCardsInHand().size() < 1) {
                try {
                    lose();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Alert noCardAlert = new Alert(Alert.AlertType.INFORMATION);
                noCardAlert.setHeaderText("There are no cards to draw");
                noCardAlert.showAndWait();
                playerOneHand.setDisable(false);
                changeTurn();
            }

        } else {
            playerOneHand.setDisable(false);
            for (int i = remainedCards + 1; i < 5; i++) {
                ImageView card = new ImageView();
                card.setFitWidth(115);
                card.setFitHeight(150);
                addHoverEvent(card);
                addHoverOffEvent(card);
                addPlayable(card);
                card.setImage(new Image(activePlayer.getHand().getCardsInHand().get(i).getUrl()));
                playerOneHand.getChildren().addAll(card);
            }
            playerOneDeckSize.setText(Integer.toString(activePlayer.getDeck().getCardList().size()));
            for (Card c : activePlayer.getBoard().getOnBoard()) {
                if (!c.isState()) {
                    c.setState();
                    getImageViewByCard(c, playerOneBoard).setRotate(270);
                }
            }
            battlesStarted = 0;
            changeTurn();
        }
    }


    private void changeTurn() {

        activePlayer.setActive();
        inactivePlayer.setActive();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Changing turn");
        alert.showAndWait();
        militaryPhaseButton.setDisable(false);
        intriquePhaseButton.setDisable(false);
        famePhaseButton.setDisable(false);

        endStep.setEffect(null);

        initialize();
    }


    private void lose() throws Exception {
        Alert loseAlert = new Alert(Alert.AlertType.INFORMATION);
        loseAlert.setHeaderText("You Lost");
        loseAlert.showAndWait();

        Parent root = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        Stage mainS = new Stage();
        mainS.setScene(new Scene(root, 1280, 720));
        mainS.show();
    }




    //*************************************
    //Board and Hand Wipes and Re-Creations
    //*************************************

    private void boardWipe() {
        playerOneBoard.getChildren().clear();
        playerTwoBoard.getChildren().clear();
        playerOneHand.getChildren().clear();
        playerTwoHand.getChildren().clear();
    }


    private void boardFill() {
        for (int i = 0; i < activePlayer.getBoard().getOnBoard().size(); i++) {
            ImageView card = new ImageView();
            card.setFitWidth(115);
            card.setFitHeight(150);
            addHoverEvent(card);
            addHoverOffEvent(card);
            addDeclarable(card);
            card.setImage(new Image(activePlayer.getBoard().getOnBoard().get(i).getUrl()));
            playerOneBoard.getChildren().add(card);
        }

        for (int i = 0; i < inactivePlayer.getBoard().getOnBoard().size(); i++) {
            ImageView card = new ImageView(new Image(inactivePlayer.getBoard().getOnBoard().get(i).getUrl()));
            card.setFitWidth(115);
            card.setFitHeight(150);
            if (inactivePlayer.getBoard().getOnBoard().get(i).isState()) {
                card.setRotate(180);
            } else {
                card.setRotate(270);
            }
            addHoverEvent(card);
            addHoverOffEvent(card);
            addDeclarable(card);
            playerTwoBoard.getChildren().add(card);
        }
    }


    private void handFill() {
        for (int i = 0; i < activePlayer.getHand().getCardsInHand().size(); i++) {
            ImageView card = new ImageView();
            card.setFitWidth(115);
            card.setFitHeight(150);
            addHoverEvent(card);
            addHoverOffEvent(card);
            addPlayable(card);
            card.setImage(new Image(activePlayer.getHand().getCardsInHand().get(i).getUrl()));
            playerOneHand.getChildren().addAll(card);
        }

        for (int i = 0; i < inactivePlayer.getHand().getCardsInHand().size(); i++) {
            ImageView card = new ImageView(new Image(inactivePlayer.getHand().getCardsInHand().get(i).getUrl()));
            card.setFitWidth(115);
            card.setFitHeight(150);
            card.setRotate(180);
            addHoverEvent(card);
            addHoverOffEvent(card);
            addPlayable(card);
            playerTwoHand.getChildren().add(card);
        }
    }




    //**************************
    //Game Events, Board changes
    //**************************


    private void addPlayable(ImageView card) {
        card.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                if (!card.getParent().equals(playerOneBoard)) {
                    playerOneBoard.getChildren().add(card);
                    String[] path = card.getImage().getUrl().split("/");
                    String url = "";
                    url += path[path.length - 3] + "/" + path[path.length - 2] + "/" + path[path.length - 1];
                    for (int i = 0; i < activePlayer.getHand().getCardsInHand().size(); i++) {
                        if (activePlayer.getHand().getCardsInHand().get(i).getLink().equals(url)) {
                            activePlayer.playFromHand(i);
                        }
                    }
                    recruited++;
                    card.setFitHeight(card.getFitHeight() - 20);
                    card.setFitWidth(card.getFitWidth() - 20);
                    playerOneHand.getChildren().remove(card);

                    if (recruited == 2) {
                        playerOneHand.setDisable(true);
                        recruit.setEffect(null);
                        battle.setEffect(sh);
                        recruited = 0;
                        battlePhase();
                    }
                }
            }
        });
    }


    private void addDeclarable(ImageView card) {
        card.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                DropShadow sd = new DropShadow();
                sd.setColor(Color.TEAL);
                sd.setSpread(0.3);
                sd.setHeight(50);
                sd.setWidth(50);
                if (card.getEffect() != null) {
                    card.setEffect(null);
                    String[] path = card.getImage().getUrl().split("/");
                    String url = "";
                    url += path[path.length - 3] + "/" + path[path.length - 2] + "/" + path[path.length - 1];
                    for (int l = 0; l < activePlayer.getBoard().getOnBoard().size(); l++) {
                        if (url.equals(activePlayer.getBoard().getOnBoard().get(l).getLink())) {
                            gm.removeFromAttackers(activePlayer.getBoard().getOnBoard().get(l));
                            break;
                        }
                    }
                } else {
                    card.setEffect(sd);
                    String[] path = card.getImage().getUrl().split("/");
                    String url = "";
                    url += path[path.length - 3] + "/" + path[path.length - 2] + "/" + path[path.length - 1];
                    for (int l = 0; l < activePlayer.getBoard().getOnBoard().size(); l++) {
                        if (url.equals(activePlayer.getBoard().getOnBoard().get(l).getLink())) {
                            if (activePlayer.getBoard().getOnBoard().get(l).isState()) {
                                gm.addToAttackers(activePlayer.getBoard().getOnBoard().get(l));
                                card.setEffect(sd);
                            } else {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText("That unit is Tired");
                                alert.showAndWait();
                            }
                            break;
                        }
                    }
                }
            }
        });
    }


    private void addBlockable(ImageView card) {
        card.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                DropShadow sd = new DropShadow();
                sd.setColor(Color.TEAL);
                sd.setSpread(0.3);
                sd.setHeight(50);
                sd.setWidth(50);
                if (card.getEffect() != null) {
                    card.setEffect(null);
                    String[] path = card.getImage().getUrl().split("/");
                    String url = "";
                    url += path[path.length - 3] + "/" + path[path.length - 2] + "/" + path[path.length - 1];
                    for (int l = 0; l < inactivePlayer.getBoard().getOnBoard().size(); l++) {
                        if (url.equals(inactivePlayer.getBoard().getOnBoard().get(l).getLink())) {
                            gm.removeFromDefenders(inactivePlayer.getBoard().getOnBoard().get(l));
                            break;
                        }
                    }
                } else {
                    card.setEffect(sd);
                    String[] path = card.getImage().getUrl().split("/");
                    String url = "";
                    url += path[path.length - 3] + "/" + path[path.length - 2] + "/" + path[path.length - 1];
                    for (int l = 0; l < inactivePlayer.getBoard().getOnBoard().size(); l++) {
                        if (url.equals(inactivePlayer.getBoard().getOnBoard().get(l).getLink())) {
                            if (inactivePlayer.getBoard().getOnBoard().get(l).isState()) {
                                gm.addToDefenders(inactivePlayer.getBoard().getOnBoard().get(l));
                                card.setEffect(sd);
                            } else {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText("That unit is Tired");
                                alert.showAndWait();
                            }
                            break;
                        }
                    }
                }
            }
        });
    }


    public void printResult() {
        String winnerName = "";
        for (int k = 0; k < gm.getAttackers().size(); k++) {
            for (Node i : playerOneBoard.getChildren()) {
                ImageView tmp = (ImageView) i;
                String url = "";
                url += tmp.getImage().getUrl().split("/")[tmp.getImage().getUrl().split("/").length - 3] + "/";
                url += tmp.getImage().getUrl().split("/")[tmp.getImage().getUrl().split("/").length - 2] + "/";
                url += tmp.getImage().getUrl().split("/")[tmp.getImage().getUrl().split("/").length - 1];
                if (url.equals(gm.getAttackers().get(k).getLink())) {
                    tmp.setEffect(null);
                    tmp.setRotate(90);
                    declareBlockers();
                    gm.getAttackers().remove(getCardByImageView(tmp, gm.getAttackers()));
                    break;

                }
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(winnerName + " won the phase!");
        alert.show();
        playerTwoDeckSize.setText(Integer.toString(inactivePlayer.getDeck().getCardList().size()));
        if (battlesStarted == 2) {
            drawToFive();
        }
    }


    @FXML
    private Player evaluate() {
        int activePlayerStrength = 0;
        int inactivePlayerStrength = 0;
        battlesStarted++;
        if (gm.getPhase().equals("military")) {
            militaryPhaseButton.setDisable(true);
            for (Card c : gm.getAttackers()) {
                activePlayerStrength += c.getMilitary();
                c.setState();
            }
            for (Card cr : gm.getDefenders()) {
                inactivePlayerStrength += cr.getMilitary();
                cr.setState();
            }
            if (activePlayerStrength > inactivePlayerStrength) {
                if (inactivePlayer.getBoard().getOnBoard().size() > 0) {
                    Card tmpCard = inactivePlayer.getBoard().getRandomCard();
                    inactivePlayer.getBoard().getOnBoard().remove(tmpCard);
                    playerTwoBoard.getChildren().remove(getImageViewByCard(tmpCard, playerTwoBoard));
                }
                return activePlayer;
            }
            return inactivePlayer;
        } else if (gm.getPhase().equals("intrique")) {
            intriquePhaseButton.setDisable(true);
            for (Card c : gm.getAttackers()) {
                activePlayerStrength += c.getIntrique();
                c.setState();
            }
            for (Card cr : gm.getDefenders()) {
                inactivePlayerStrength += cr.getIntrique();
                cr.setState();
            }
            if (activePlayerStrength > inactivePlayerStrength) {
                for (int i = 0; i < 2; i++) {
                    if (inactivePlayer.getHand().getCardsInHand().size() != 0) {
                        Card tmpCard = inactivePlayer.getHand().getRandomCard();
                        playerTwoHand.getChildren().remove(getImageViewByCard(tmpCard, playerTwoHand));
                        inactivePlayer.getHand().discard();
                    }
                }
                return activePlayer;
            }
            return inactivePlayer;
        } else if (gm.getPhase().equals("fame")) {
            famePhaseButton.setDisable(true);
            for (Card c : gm.getAttackers()) {
                activePlayerStrength += c.getFame();
                c.setState();
            }
            for (Card cr : gm.getDefenders()) {
                inactivePlayerStrength += cr.getFame();
                cr.setState();
            }
            if (activePlayerStrength > inactivePlayerStrength) {
                for (int i = 0; i < 3; i++) {
                    if (inactivePlayer.getDeck().getCardList().size() > 0) {
                        inactivePlayer.getDeck().mill();
                    }
                }
                playerTwoDeckSize.setText(Integer.toString(inactivePlayer.getDeck().getCardList().size()));
                return activePlayer;
            }
            return inactivePlayer;
        }
        return null;
    }



    //*******************
    //Getters and Setters
    //*******************

    private ImageView getImageViewByCard(Card card, FlowPane hand) {
        for (int i = 0; i < hand.getChildren().size(); i++) {
            ImageView tmp = (ImageView) hand.getChildren().get(i);

            String[] path = tmp.getImage().getUrl().split("/");

            String url = "";
            url += path[path.length - 3] + "/" + path[path.length - 2] + "/" + path[path.length - 1];

            if (card.getLink().equals(url)) {
                return tmp;
            }
        }
        return null;
    }

    private Card getCardByImageView(ImageView img, List<Card> cardList) {
        String[] path = img.getImage().getUrl().split("/");
        String url = "";
        url += path[path.length - 3] + "/" + path[path.length - 2] + "/" + path[path.length - 1];

        for (int i = 0; i < cardList.size(); i++) {
            if (url.equals(cardList.get(i).getLink())) {
                return cardList.get(i);
            }
        }
        return null;
    }


    private Player getActivePlayer() {
        Player activePlayer = null;
        for (Player player : gm.getPlayers()) {
            if (player.isActive()) {
                activePlayer = player;
            }
        }
        return activePlayer;
    }

    private Player getInactivePlayer() {
        Player inactivePlayer = null;
        for (Player player : gm.getPlayers()) {
            if (!player.isActive()) {
                inactivePlayer = player;
            }
        }
        return inactivePlayer;
    }

    public void setMilitaryPhase() {
        gm.setPhase("military");
    }

    public void setIntriquePhase() {
        gm.setPhase("intrique");
    }

    public void setFamePhase() {
        gm.setPhase("fame");
    }



    //************************************************
    //Hover effects and possibly other optical tunings
    //************************************************

    private void addHoverEvent(ImageView card) {
        card.setOnMouseEntered(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                handHover();
            }
        });
    }


    private void addHoverOffEvent(ImageView card) {
        card.setOnMouseExited(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                handHoverOff();
            }
        });
    }


    private void handHover() {
        tmpImg = new ImageView();
        for (int i = 0; i < playerOneHand.getChildren().size(); i++) {
            if (playerOneHand.getChildren().get(i).isHover()) {

                tmpImg = (ImageView) playerOneHand.getChildren().get(i);
                break;
            }
        }
        for (int i = 0; i < playerOneBoard.getChildren().size(); i++) {
            if (playerOneBoard.getChildren().get(i).isHover()) {

                tmpImg = (ImageView) playerOneBoard.getChildren().get(i);
                break;
            }
        }
        ScaleTransition st = new ScaleTransition(Duration.millis(250), tmpImg);
        st.setByX(0.3f);
        st.setByY(0.3f);
        st.setCycleCount(1);
        if (!tmpImg.getParent().equals(playerOneBoard)) {
            TranslateTransition tt = new TranslateTransition(Duration.millis(250), tmpImg);
            tt.setToY(-60f);
            tt.setCycleCount(1);
            tt.play();
        }
        st.play();
    }

    private void handHoverOff() {
        ScaleTransition st = new ScaleTransition(Duration.millis(250), tmpImg);
        st.setByX(-(tmpImg.getScaleX() - 1));
        st.setByY(-(tmpImg.getScaleY() - 1));
        st.setCycleCount(1);
        TranslateTransition tt = new TranslateTransition(Duration.millis(250), tmpImg);
        tt.setToY(1);
        tt.setCycleCount(1);

        tt.play();
        st.play();
    }
}