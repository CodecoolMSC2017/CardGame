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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;


public class MainController {

    private Parent root;
    @FXML
    AnchorPane ap;
    @FXML
    Label startButton;
    @FXML
    Label exitButton;
    @FXML
    Label optionsButton;


    //Choosing exit option
    public void exit() {
        System.exit(0);
    }

    //Choosing Options option
    public void enterOptions()throws Exception{
        root = FXMLLoader.load(getClass().getResource("optionsScreen.fxml"));
        Stage stage = (Stage) ap.getScene().getWindow();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        stage.setScene(new Scene(root,screenSize.getWidth(), screenSize.getHeight()));
    }

/*    public void setFullscreen(){
        stage.setFullScreen(!stage.isFullScreen());
        background.setFitWidth(screenWidth);
        background.setFitHeight(screenHeight);
        background.setImage(new Image("startingScreen1920.jpg"));
    }
*/

    //Choosing New Game*/
    public void startGame() throws Exception{
        AudioClip audio = new AudioClip(this.getClass().getResource("../../../sound/gong.mp3").toExternalForm());
        audio.play();
        Thread.sleep(3000);
        root = FXMLLoader.load(getClass().getResource("playerNameScreen.fxml"));
        Stage stage = (Stage) ap.getScene().getWindow();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        stage.setScene(new Scene(root,screenSize.getWidth(), screenSize.getHeight()));
    }


    /*public void confirmButton()throws Exception{
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


    public void phaseChoose()throws Exception{
        phaseScreen=new Stage();
        phaseRoot = FXMLLoader.load(getClass().getResource("phaseChooseScreen.fxml"));
        phaseScreen.setScene(new Scene(phaseRoot,250,400));
        phaseScreen.show();
    }




    //Button Hover actions
*/    public void startButtonHover(){
        AudioClip audio = new AudioClip(this.getClass().getResource("../../../sound/hoverSound.wav").toExternalForm());
        audio.play();
        startButton.setScaleX(1.2);
        startButton.setScaleY(1.2);
    }

    public void startButtonHoverOff(){
        startButton.setScaleX(1);
        startButton.setScaleY(1);
    }

    public void exitButtonHover(){
        AudioClip audio = new AudioClip(this.getClass().getResource("../../../sound/hoverSound.wav").toExternalForm());
        audio.play();
        exitButton.setScaleX(1.2);
        exitButton.setScaleY(1.2);
    }

    public void exitButtonHoverOff(){
        exitButton.setScaleX(1);
        exitButton.setScaleY(1);
    }

    public void optionsButtonHover(){
        AudioClip audio = new AudioClip(this.getClass().getResource("../../../sound/hoverSound.wav").toExternalForm());
        audio.play();
        optionsButton.setScaleX(1.2);
        optionsButton.setScaleY(1.2);
    }

    public void optionsButtonHoverOff(){
        optionsButton.setScaleX(1);
        optionsButton.setScaleY(1);
    }

/*    public void confirmButtonHover(){
        AudioClip mApplause = new AudioClip(this.getClass().getResource("../../../sound/hoverSound.wav").toExternalForm());
        mApplause.play();
        confirmButton.setScaleX(1.1);
        confirmButton.setScaleY(1.1);

    }

    public void confirmButtonHoverOff(){
        confirmButton.setScaleX(1);
        confirmButton.setScaleY(1);
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


    public void goBackToMain()throws Exception{
        root = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        stage.setScene(new Scene(root,1366,768));

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


    public void handOneClicked(){
        playerOne.playFromHand(0);
        switch(onBoardCounter){
            case 0:

                onBoardOne.setImage(handOne.getImage());
                onBoardCounter++;
                break;
            case 1:
                onBoardTwo.setImage(handOne.getImage());
                onBoardCounter++;
                break;
            case 2:
                onBoardThree.setImage(handOne.getImage());
                onBoardCounter++;
                break;
            case 3:
                onBoardFour.setImage(handOne.getImage());
                onBoardCounter++;
                break;
            case 4:
                onBoardFive.setImage(handOne.getImage());
                onBoardCounter++;
                break;
            case 5:
                onBoardSix.setImage(handOne.getImage());
                onBoardCounter++;
                break;
            case 6:
                onBoardSeven.setImage(handOne.getImage());
                onBoardCounter++;
                break;
        }


        handOne.setImage(handTwo.getImage());
        handTwo.setImage(handThree.getImage());
        handThree.setImage(handFour.getImage());
        handFour.setImage(handFive.getImage());
        handFive.setImage(null);
    }
*/

}
