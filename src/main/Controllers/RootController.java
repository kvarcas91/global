package main.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.Entities.User;

import javafx.scene.image.Image;

import javafx.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    @FXML
    JFXButton easter;

    private User user;
    private String testString;


    public void setUser (User user) {
        if (user != null) {
            this.user = user;
            System.out.println(this.user.toString());
        }
    }

    public void easterEgg (ActionEvent event) {
        System.out.println("easter");

        Image easterImg = null;
        ImageView imgW = new ImageView();
        try {
            easterImg = new Image(new FileInputStream("src/main/Resources/Drawable/easter.jpg"));
            imgW.setImage(easterImg);
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        Group root = new Group();
        Scene scene = new Scene(root);
        scene.setFill(Color.BLACK);
        HBox box = new HBox();
        box.getChildren().add(imgW);
        root.getChildren().add(box);

        double width = easterImg.getWidth();
        double height = easterImg.getHeight();

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setResizable(false);
        Rectangle2D primScreenBound = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBound.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBound.getHeight() - stage.getHeight()) / 4);

        stage.show();

    }

    public void initialize (URL url, ResourceBundle bundle) {



    }

}
