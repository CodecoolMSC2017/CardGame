package com.codecool.gui;

import com.codecool.api.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import java.awt.*;

public class GuiMain extends Application {

    public void start(Stage primaryStage) throws Exception{
        Stage stage=primaryStage;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();

        Parent root = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        stage.setTitle("Medieval Warfare");
        stage.setScene(new Scene(root, screenWidth, screenHeight));
        stage.setResizable(false);
        stage.show();
    }


    public void guimain() {
        AudioClip audio = new AudioClip(getClass().getResource("../../../sound/backgroundMusic.wav").toExternalForm());
        audio.setVolume(5f);
        audio.setCycleCount(100);
        audio.play();
        launch();
    }

}
