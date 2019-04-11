package main.Controllers;

import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import main.Main;
import main.Utils.Loader;

import java.net.URL;
import java.util.ResourceBundle;

public class FestivalController {

    private Loader loader = null;

    public FestivalController () {
        loader = Main.getPageLoader();
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