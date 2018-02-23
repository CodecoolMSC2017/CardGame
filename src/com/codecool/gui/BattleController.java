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
    private int recruited = 0;
    private int battlesStarted = 0;
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
    private ImageView tmpImg;
    private DropShadow sh = new DropShadow();

    public void initialize() {
        for (Player player : gm.getPlayers()) {
            System.out.println(player.getName());
        }

        sh.setWidth(50);
        sh.setHeight(50);
        sh.setColor(Color.RED);
        recruit.setEffect(sh);
        Player activePlayer = getActivePlayer();
        Player inactivePlayer = getInactivePlayer();
        nameFieldOne.setText(activePlayer.getName());
        nameFieldTwo.setText(inactivePlayer.getName());

        for (int i = 0; i < activePlayer.getHand().getCardsInHand().size(); i++) {
            ImageView card = new ImageView();
            card.setFitWidth(115);
            card.setFitHeight(150);
            addHoverEvent(card);
            addHoverOffEvent(card);
            card.setImage(new Image(getActivePlayer().getHand().getCardsInHand().get(i).getUrl()));
            playerOneHand.getChildren().addAll(card);
        }

        for (int i = 0; i < inactivePlayer.getHand().getCardsInHand().size(); i++) {
            ImageView card = new ImageView(new Image(getInactivePlayer().getHand().getCardsInHand().get(i).getUrl()));
            card.setFitWidth(115);
            card.setFitHeight(150);
            card.setRotate(180);
            addHoverEvent(card);
            addHoverOffEvent(card);
            playerTwoHand.getChildren().add(card);
        }
        playerOneDeckSize.setText(Integer.toString(getActivePlayer().getDeck().getCardList().size()));
        playerTwoDeckSize.setText(Integer.toString(inactivePlayer.getDeck().getCardList().size()));
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

    private void drawToFive() {
        battle.setEffect(null);
        endStep.setEffect(sh);
        int remainedCards = getActivePlayer().getHand().getCardsInHand().size();
        boolean deckHasCards = getActivePlayer().drawAfterTurn();
        if (!deckHasCards) {
            try {
                lose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            for (int i = remainedCards; i < 5; i++) {
                ImageView card = new ImageView();
                card.setFitWidth(115);
                card.setFitHeight(150);
                addHoverEvent(card);
                addHoverOffEvent(card);
                addPlayable(card);
                card.setImage(new Image(getActivePlayer().getHand().getCardsInHand().get(i).getUrl()));
                playerOneHand.getChildren().addAll(card);
            }
            playerOneDeckSize.setText(Integer.toString(getActivePlayer().getDeck().getCardList().size()));
            for (Card c : getActivePlayer().getBoard().getOnBoard()) {
                if (!c.isState()) {
                    c.setState();
                    getImageViewByCard(c, playerOneBoard).setRotate(270);
                }
            }
            battlesStarted = 0;
            playerOneHand.setDisable(false);
            changeTurn();
        }
    }


    private void addPlayable(ImageView card) {
        card.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                if (!card.getParent().equals(playerOneBoard)) {
                    playerOneBoard.getChildren().add(card);
                    String[] path = card.getImage().getUrl().split("/");
                    String url = "";
                    url += path[path.length - 3] + "/" + path[path.length - 2] + "/" + path[path.length - 1];
                    for (int i = 0; i < getActivePlayer().getHand().getCardsInHand().size(); i++) {
                        if (getActivePlayer().getHand().getCardsInHand().get(i).getLink().equals(url)) {
                            getActivePlayer().playFromHand(i);
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
                    for (int l = 0; l < getActivePlayer().getBoard().getOnBoard().size(); l++) {
                        if (url.equals(getActivePlayer().getBoard().getOnBoard().get(l).getLink())) {
                            gm.removeFromSelected(getActivePlayer().getBoard().getOnBoard().get(l));
                            break;
                        }
                    }
                } else {
                    card.setEffect(sd);
                    String[] path = card.getImage().getUrl().split("/");
                    String url = "";
                    url += path[path.length - 3] + "/" + path[path.length - 2] + "/" + path[path.length - 1];
                    for (int l = 0; l < getActivePlayer().getBoard().getOnBoard().size(); l++) {
                        if (url.equals(getActivePlayer().getBoard().getOnBoard().get(l).getLink())) {
                            if (getActivePlayer().getBoard().getOnBoard().get(l).isState()) {
                                gm.addToSelected(getActivePlayer().getBoard().getOnBoard().get(l));
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



    private void lose() throws Exception {
        Alert loseAlert = new Alert(Alert.AlertType.INFORMATION);
        loseAlert.setHeaderText("You Lost");
        loseAlert.showAndWait();

        Parent root = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        Stage mainS = new Stage();
        mainS.setScene(new Scene(root, 1280, 720));
        mainS.show();
    }


    @FXML
    private void changeTurn(){
        Player activePlayer = getActivePlayer();
        Player inactivePlayer = getInactivePlayer();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Changing turn");
        alert.showAndWait();
        militaryPhaseButton.setDisable(false);
        intriquePhaseButton.setDisable(false);
        famePhaseButton.setDisable(false);

        endStep.setEffect(null);

        activePlayer.setActive();
        inactivePlayer.setActive();
        initialize();
    }





    //Graphics Stuff
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


    //Helper methods
    public void printResult() {
        for (int k = 0; k < gm.getSelectedCards().size(); k++) {
            for (Node i : playerOneBoard.getChildren()) {
                ImageView tmp = (ImageView) i;
                String url = "";
                url += tmp.getImage().getUrl().split("/")[tmp.getImage().getUrl().split("/").length - 3] + "/";
                url += tmp.getImage().getUrl().split("/")[tmp.getImage().getUrl().split("/").length - 2] + "/";
                url += tmp.getImage().getUrl().split("/")[tmp.getImage().getUrl().split("/").length - 1];
                if (url.equals(gm.getSelectedCards().get(k).getLink())) {
                    tmp.setEffect(null);
                    tmp.setRotate(90);
                    gm.getSelectedCards().remove(tmp);
                }
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(evaluate().getName() + " won the battle!");
        alert.show();
        if (battlesStarted == 2) {
            drawToFive();
        }
    }


    private Player evaluate() {
        int activePlayerStrength = 0;
        int playerTwoPoint = 0;
        battlesStarted++;
        if (gm.getPhase().equals("military")) {
            militaryPhaseButton.setDisable(true);
            for (Card c : gm.getSelectedCards()) {
                activePlayerStrength += c.getMilitary();
                c.setState();
            }
            for (Card cr : gm.getEnemySelected()) {
                playerTwoPoint += cr.getMilitary();
                cr.setState();
            }
            if (activePlayerStrength > playerTwoPoint) {
                if (getInactivePlayer().getBoard().getOnBoard().size() > 0) {
                    getInactivePlayer().getBoard().getOnBoard().remove(getInactivePlayer().getBoard().getRandomCard());
                }
                return getActivePlayer();
            }
            return getInactivePlayer();
        } else if (gm.getPhase().equals("intrique")) {
            intriquePhaseButton.setDisable(true);
            for (Card c : gm.getSelectedCards()) {
                activePlayerStrength += c.getIntrique();
                c.setState();
            }
            for (Card cr : gm.getEnemySelected()) {
                playerTwoPoint += cr.getIntrique();
                cr.setState();
            }
            if (activePlayerStrength > playerTwoPoint) {
                for (int i = 0; i < 2; i++) {
                    if (getInactivePlayer().getHand().getCardsInHand().size() != 0) {
                        Card tmpCard = getInactivePlayer().getHand().getRandomCard();
                        playerTwoHand.getChildren().remove(getImageViewByCard(tmpCard, playerTwoHand));
                        getInactivePlayer().getHand().discard();
                    }
                }
                return getActivePlayer();
            }
            return getInactivePlayer();
        } else if (gm.getPhase().equals("fame")) {
            famePhaseButton.setDisable(true);
            for (Card c : gm.getSelectedCards()) {
                activePlayerStrength += c.getFame();
                c.setState();
            }
            for (Card cr : gm.getEnemySelected()) {
                playerTwoPoint += cr.getFame();
                cr.setState();
            }
            if (activePlayerStrength > playerTwoPoint) {
                for (int i = 0; i < 3; i++) {
                    if (getInactivePlayer().getDeck().getCardList().size() != 0) {
                        getInactivePlayer().getDeck().mill();
                    }
                }
                playerTwoDeckSize.setText(Integer.toString(getInactivePlayer().getDeck().getCardList().size()));
                return getActivePlayer();
            }
            return getInactivePlayer();
        }
        return null;
    }



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
}
