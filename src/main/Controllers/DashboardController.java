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

public class DashboardController extends Controller implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(DashboardController.class.getName());
    private static DashboardController instance = null;

    @FXML private JFXButton btnEaster;

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

    private void easter () {

        String url = "https://youtu.be/dQw4w9WgXcQ?t=43&autoplay=1";

        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnEaster.setOnAction(e -> easter());
    }

    public static void Destroy () {
        if (instance != null) {
            LOGGER.log(Level.INFO, "Destroying DashboardController instance at: {0}\n", LocalTime.now());
            instance = null;
        }
    }
}
