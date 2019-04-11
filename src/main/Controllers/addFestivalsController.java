package main.Controllers;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import javafx.fxml.Initializable;
import main.Main;
import main.Utils.Loader;

import java.util.ResourceBundle;

public class addFestivalsController implements Initializable {

    /**
     *          ########################################################
     *          #############   How to create more pages   #############
     *          ########################################################
     *  loader.loadPage(fxml file);  // fxml file is a String of that file location. i.e.: ../UI/account.fxml
     *          ########################################################
     */

    private Loader loader = null;

    public addFestivalsController () {
        loader = Main.getPageLoader();
    }

    @FXML
    private JFXTextField typeName;

    @FXML
    private JFXTextField typeLocation;

   /** @FXML
    private DatePicker datePicker; **/


    @FXML
    void newDatePicker(ActionEvent event) {

    }

    @FXML
    void typeNewLocation(ActionEvent event) {

    }

    @FXML
    void typeNewName(ActionEvent event) {

    }

    @FXML
    private void back (MouseEvent event) {
        loader.loadPage("../UI/festivals.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}


