package main.Controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.Entities.User;

import javafx.event.ActionEvent;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class RootControllerTemp implements Initializable {

    private static RootControllerTemp instance;

    public RootControllerTemp() {
        instance = this;
    }



    @FXML
    private BorderPane content;

    private User user;


    public void setUser (User user) {
        if (user != null) {
            this.user = user;
            System.out.println(this.user.toString());
        }
    }

    public User getUser () {return this.user;}

    public BorderPane getContent () {
        return this.content;
    }

    @FXML
    private void play (ActionEvent event) {
        Media media = new Media(new File(
                "src/main/Resources/Drawable/easter.mp4").toURI().toString()
        );
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        Group root = new Group();
        Scene scene = new Scene(root);
        scene.setFill(Color.BLACK);
        HBox box = new HBox();
        box.getChildren().add(mediaView);
        root.getChildren().add(box);

        Stage stage = new Stage();

        mediaPlayer.setAutoPlay(true);
        stage.setScene(scene);

        stage.setResizable(false);
        Rectangle2D primScreenBound = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBound.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBound.getHeight() - stage.getHeight()) / 4);
        mediaPlayer.setOnReady(() -> stage.sizeToScene());
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                mediaPlayer.stop();
            }
        });

    }

    public void initialize (URL url, ResourceBundle bundle) {

    }

}
