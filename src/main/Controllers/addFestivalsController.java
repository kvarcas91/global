package main.Controllers;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import java.util.ResourceBundle;

public class addFestivalsController implements Initializable {


    private Loader loader = null;

    public addFestivalsController () {
        loader = new Loader(RootController.getInstance().getContent());
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


