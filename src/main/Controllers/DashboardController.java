package main.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class DashboardController {


    @FXML
    private void easter (ActionEvent event) {

        String url = "https://youtu.be/dQw4w9WgXcQ?t=43&autoplay=1";
        //String url = "https://www.youtube.com/embed/dQw4w9WgXcQ?autoplay=1&controls=0&amp;start=43";

        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }


    }

}
