package main.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FestivalController {

    private Loader loader = null;

    public FestivalController () {
        loader = new Loader(RootController.getInstance().getContent());
    }


    @FXML
    private JFXListView listView;


    @FXML
    private void addFestivals(ActionEvent event) {
        loader.loadPage("../UI/addFestivals.fxml");
    }


    @FXML
    private void cancelFestivals(ActionEvent event) {

    }

    @FXML
    private void editFestivals(ActionEvent event) {

    }


    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}