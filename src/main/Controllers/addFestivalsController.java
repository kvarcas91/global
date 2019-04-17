package main.Controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;

public class addFestivalsController implements Initializable {


    private Loader loader = null;

    public addFestivalsController() {
        loader = new Loader(RootController.getInstance().getContent());
    }

    @FXML
    private JFXTextField typeName;

    @FXML
    private JFXTextField typeLocation;

    /**
     * @FXML private DatePicker datePicker;
     **/


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
    private void back(MouseEvent event) {
        loader.loadPage("../UI/festivals.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}