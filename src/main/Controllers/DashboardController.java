package main.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import main.Utils.WriteLog;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardController extends Controller{

    private static final Logger LOGGER = Logger.getLogger(DashboardController.class.getName());
    private static DashboardController instance = null;


    private DashboardController () {
        instance = this;
        WriteLog.addHandler(LOGGER);
        LOGGER.log(Level.INFO, "Creating DashboardController instance from constructor at: {0}\n", LocalTime.now());
    }

    public static DashboardController getInstance() {
        if (instance == null) {
            synchronized (DashboardController.class) {
                if (instance == null) {
                    return new DashboardController();
                }
            }
        }
        else LOGGER.log(Level.INFO, "Tried to create DashboardController instance at {0}\n", LocalTime.now());
        return instance;
    }


    public static void Destroy () {
        if (instance != null) {
            LOGGER.log(Level.INFO, "Destroying DashboardController instance at: {0}\n", LocalTime.now());
            instance = null;
        }
    }
}
