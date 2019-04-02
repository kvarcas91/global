package main.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.Entities.User;

import java.io.IOException;

class Loader{

    private BorderPane borderPane;

    Loader () {}

    Loader (BorderPane pane) {
        this.borderPane = pane;
    }

    void loadMain (Pane root, User user) {
        loader(root, "../UI/root.fxml", true, user, 1000, 600, 800, 600);
    }

    void loadLogin (Pane root){
        loader(root, "../UI/login_ui.fxml", false, null, 500, 300, 500, 300);
    }

    void loadRegister (Pane root) {
        loader(root, "../UI/register.fxml", false, null, 600, 600, 600, 600);
    }

    void loadPage (String path) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        try {
            Pane pane = loader.load();
            borderPane.setCenter(pane);
        }
        catch (IOException e) {
            return;
        }
    }

    /**
     *
     * @param root borderPane
     * @param path .fxml path
     * @param resizable boolean
     * @param user obj
     * @param geometry [0] width; [1] height; [2] minWidth; [3] minHeight
     */
    private void loader (Pane root, String path, boolean resizable, User user, int... geometry) {
        FXMLLoader mLoader = new FXMLLoader(getClass().getResource(path));
        Parent parent = null;
        try {
            parent = mLoader.load();
        }
        catch (IOException e) {
            return;
        }
        if (user != null) {
            RootController rootController = mLoader.getController();
            rootController.setUser(user);
        }
        Stage stage = new Stage();
        stage.setResizable(resizable);
        stage.setWidth(geometry[0]);
        stage.setHeight(geometry[1]);
        stage.setMinWidth(geometry[2]);
        stage.setMinHeight(geometry[3]);
        Rectangle2D primScreenBound = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBound.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBound.getHeight() - stage.getHeight()) / 4);
        stage.setScene(new Scene(parent));
        stage.show();

        Stage oldStage = (Stage) root.getScene().getWindow();
        oldStage.close();
    }

}
