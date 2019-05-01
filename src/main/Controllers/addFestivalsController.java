package main.Controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import java.net.URL;
import javafx.fxml.Initializable;
import main.Utils.Loader;
import main.Utils.WriteLog;

import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class addFestivalsController extends Controller implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(addFestivalsController.class.getName());
    private static addFestivalsController instance = null;

    @FXML private JFXTextField typeName, typeLocation;
    @FXML private ImageView btnBack;

    private addFestivalsController () {
        instance = this;
        WriteLog.addHandler(LOGGER);
        LOGGER.log(Level.INFO, "Creating addFestivalsController instance from constructor at: {0}\n", LocalTime.now());
    }


    public static addFestivalsController getInstance() {
        if (instance == null) {
            synchronized (FestivalController.class) {
                if (instance == null) {
                    return new addFestivalsController();
                }
            }
        }
        else LOGGER.log(Level.WARNING, "Tried to create additional instance at: {0}\n", LocalTime.now());
        return instance;
    }


   /** @FXML
    private DatePicker datePicker; **/


    void newDatePicker(ActionEvent event) {

    }

    void typeNewLocation(ActionEvent event) {

    }

    void typeNewName(ActionEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBack.setOnMousePressed(e -> Loader.getInstance().loadPage("../UI/festivals.fxml", FestivalController.getInstance()));
    }
}


