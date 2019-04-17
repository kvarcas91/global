package main.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import main.Utils.Loader;
import main.Utils.WriteLog;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FestivalController extends Controller implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(FestivalController.class.getName());

    private static FestivalController instance = null;

    @FXML private JFXListView listView;
    @FXML private JFXButton btnAdd, btnEdit;


    private FestivalController () {
        instance = this;
        WriteLog.addHandler(LOGGER);
        LOGGER.log(Level.INFO, "Creating FestivalController instance from constructor at: {0}\n", LocalTime.now());
    }

    public static FestivalController getInstance() {
        if (instance == null) {
            synchronized (FestivalController.class) {
                if (instance == null) {
                    return new FestivalController();
                }
            }
        }
        else LOGGER.log(Level.WARNING, "Tried to create additional instance at: {0}\n", LocalTime.now());
        return instance;
    }

    private void addFestivals() {
        Loader.getInstance().loadPage("../UI/addFestivals.fxml", addFestivalsController.getInstance());
    }



    private void cancelFestivals() {

    }


    private void editFestivals() {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnAdd.setOnAction(e -> addFestivals());
        btnEdit.setOnAction(e -> editFestivals());
    }

    public static void Destroy () {}

}