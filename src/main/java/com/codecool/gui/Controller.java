package com.codecool.gui;

import com.codecool.api.CardReader;
import com.codecool.api.Player;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;


public class Controller extends Application {

    private static Parent root;
    private static Player playerOne;
    private static Player playerTwo;
    private static Stage stage;
    @FXML TextField playerOneName;
    @FXML TextField playerTwoName;
    @FXML Label startButton;
    @FXML Label exitButton;
    @FXML Label optionsButton;

    @FXML ImageView confirmButton;
    @FXML ImageView onBoardOne;
    @FXML ImageView onBoardTwo;
    @FXML ImageView onBoardThree;
    @FXML ImageView onBoardFour;
    @FXML ImageView onBoardFive;
    @FXML ImageView onBoardSix;
    @FXML ImageView onBoardSeven;
    @FXML ImageView background;
    @FXML ImageView handOne;
    @FXML ImageView handTwo;
    @FXML ImageView handThree;
    @FXML ImageView handFour;
    @FXML ImageView handFive;
    private static ImageView tmpImg;
    private static ImageView tmpImgHand;
    private static double screenWidth;
    private static double screenHeight;


    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception{
        Controller.stage=primaryStage;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Controller.screenWidth = screenSize.getWidth();
        Controller.screenHeight = screenSize.getHeight();

        Controller.root = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        Controller.stage.setTitle("Medieval Warfare");
        Controller.stage.setScene(new Scene(root, 1366, 768));
        Controller.stage.setResizable(false);
        Controller.stage.show();
    }

    public void startGame() throws Exception{
        AudioClip audio = new AudioClip(this.getClass().getResource("gong.mp3").toExternalForm());
        audio.play();
        Thread.sleep(3000);
        Controller.root = FXMLLoader.load(getClass().getResource("playerNameScreen.fxml"));
        Controller.stage.setScene(new Scene(root,1366,768));


    }

    public void confirmButton()throws Exception{
        Controller.playerOne = new Player(playerOneName.getText());
        Controller.playerTwo = new Player(playerTwoName.getText());
        Controller.root = FXMLLoader.load(getClass().getResource("battleScreen.fxml"));
        CardReader cr = new CardReader();
        cr.loadDeck(Controller.playerOne);
        System.out.println(Controller.playerOne.getDeck().getCardList().size());
        playerOne.drawStartingHand();
        handOne.setImage(new Image(playerOne.getHand().getCardsInHand().get(0).getUrl()));
        handTwo.setImage(new Image(playerOne.getHand().getCardsInHand().get(1).getUrl()));
        handThree.setImage(new Image(playerOne.getHand().getCardsInHand().get(2).getUrl()));
        handFour.setImage(new Image(playerOne.getHand().getCardsInHand().get(3).getUrl()));
        handFive.setImage(new Image(playerOne.getHand().getCardsInHand().get(4).getUrl()));
        Controller.stage.setScene(new Scene(root,1366,768));


    }

    public void startButtonHover(){
        AudioClip audio = new AudioClip(this.getClass().getResource("hoverSound.wav").toExternalForm());
        audio.play();
        startButton.setScaleX(1.2);
        startButton.setScaleY(1.2);



    }

    public void startButtonHoverOff(){
        startButton.setScaleX(1);
        startButton.setScaleY(1);
    }

    public void exitButtonHover(){
        AudioClip audio = new AudioClip(this.getClass().getResource("hoverSound.wav").toExternalForm());
        audio.play();
        exitButton.setScaleX(1.2);
        exitButton.setScaleY(1.2);


    }

    public void exitButtonHoverOff(){
        exitButton.setScaleX(1);
        exitButton.setScaleY(1);
    }

    public void optionsButtonHover(){
        AudioClip audio = new AudioClip(this.getClass().getResource("hoverSound.wav").toExternalForm());
        audio.play();
        optionsButton.setScaleX(1.2);
        optionsButton.setScaleY(1.2);



    }

    public void optionsButtonHoverOff(){
        optionsButton.setScaleX(1);
        optionsButton.setScaleY(1);
    }

    public void confirmButtonHover(){
        AudioClip mApplause = new AudioClip(this.getClass().getResource("hoverSound.wav").toExternalForm());
        mApplause.play();
        confirmButton.setScaleX(1.1);
        confirmButton.setScaleY(1.1);

    }

    public void confirmButtonHoverOff(){
        confirmButton.setScaleX(1);
        confirmButton.setScaleY(1);
    }

    public void exit(){
        System.exit(0);
    }

    public void enterOptions()throws Exception{
        Controller.root = FXMLLoader.load(getClass().getResource("optionsScreen.fxml"));
        Controller.stage.setScene(new Scene(root,1366,768));
    }

    public void onBoardHover(){
        tmpImg = new ImageView();
        ImageView[] cardsOnBoard = {onBoardOne,onBoardTwo,onBoardThree,onBoardFour,onBoardFive,onBoardSix,onBoardSeven};

        for(ImageView i : cardsOnBoard){
            if(i.isHover()){
                tmpImg=i;
                break;
            }
        }
        ScaleTransition st = new ScaleTransition(Duration.millis(250),tmpImg );
        st.setByX(0.3f);
        st.setByY(0.3f);
        st.setCycleCount(1);
        st.play();





    }

    public void onBoardHoverOff(){
        ScaleTransition st = new ScaleTransition(Duration.millis(250), tmpImg);
        st.setByX(-(tmpImg.getScaleX()-1));
        st.setByY(-(tmpImg.getScaleY()-1));
        st.setCycleCount(1);
        st.play();


    }

    public void setFullscreen(){
        Controller.stage.setFullScreen(!Controller.stage.isFullScreen());
        background.setFitWidth(screenWidth);
        background.setFitHeight(screenHeight);
        background.setImage(new Image("startingScreen1920.jpg"));


    }

    public void goBackToMain()throws Exception{
        Controller.root = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        Controller.stage.setScene(new Scene(root,1366,768));
    }

    public void handHover(){
        tmpImgHand = new ImageView();
        ImageView[] cardsInHand = {handOne,handTwo,handThree,handFour,handFive};

        for(ImageView i : cardsInHand){
            if(i.isHover()){
                tmpImgHand=i;
                break;
            }
        }
        ScaleTransition st = new ScaleTransition(Duration.millis(250),tmpImgHand );
        st.setByX(0.3f);
        st.setByY(0.3f);
        st.setCycleCount(1);
        TranslateTransition tt = new TranslateTransition(Duration.millis(250), tmpImgHand);
        tt.setByY(-50f);
        tt.setCycleCount(1);


        tt.play();
        st.play();
    }

    public void handHoverOff(){
        ScaleTransition st = new ScaleTransition(Duration.millis(250), tmpImgHand);
        st.setByX(-(tmpImgHand.getScaleX()-1));
        st.setByY(-(tmpImgHand.getScaleY()-1));
        st.setCycleCount(1);
        TranslateTransition tt = new TranslateTransition(Duration.millis(250), tmpImgHand);
        tt.setToY(1);
        tt.setCycleCount(1);


        tt.play();
        st.play();

    }



}
