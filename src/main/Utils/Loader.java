package main.Utils;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.Controllers.Controller;
import main.Controllers.LoginController;
import main.Controllers.RegisterController;
import main.Controllers.RootController;
import main.Entities.User;

import javax.naming.ldap.Control;
import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Loader{

    private static final Logger LOGGER = Logger.getLogger(Loader.class.getName());
    private static BorderPane borderPane = null;
    private static Loader instance = null;

    private Loader () {
        instance = this;
        WriteLog.addHandler(LOGGER);
        LOGGER.log(Level.INFO, "Creating Loader instance from constructor at: {0}\n", LocalTime.now());
    }

    private Loader (BorderPane pane) {
        LOGGER.log(Level.INFO, "Creating Loader instance with BorderPane from constructor at: {0}\n", LocalTime.now());
        borderPane = pane;
    }

    public static Loader getInstance() {
        if (instance == null) {
            synchronized (Loader.class) {
                if (instance == null) {
                    return new Loader();
                }
            }
        }
        else {
            LOGGER.log(Level.WARNING, "Tried to create additional instance at: {0}\n", LocalTime.now());
        }
        return instance;
    }

    // BorderPane cannot be null
    public static void createInstanceWithPane (BorderPane pane) {
        if (instance == null) {
            synchronized (Loader.class) {
                if (instance == null) {
                   new Loader(pane);
                }

            }
        }
        else {
            borderPane = pane;
            LOGGER.log(Level.WARNING, "Tried to create additional instance at: {0}. Skipping and adding just pane\n",
                    LocalTime.now());
        }
    }


    public void loadMain (Pane root, User user, Controller controller) {
        loader(root, "../UI/root.fxml", true, user, controller,  1000, 600, 800, 600);
    }


    public void loadLogin (Pane root, Controller controller){
        loader(root, "../UI/login.fxml", false, null, controller, 600, 450, 600, 450);
    }

    public void loadRegister (Pane root, Controller controller) {
        loader(root, "../UI/register.fxml", true, null, controller, 850, 650, 850, 650);
    }

    public void loadPage (String path, Controller controller) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        loader.setController(controller);
        Runtime runtime = Runtime.getRuntime();
        System.out.println("Total memory: " + runtime.totalMemory());
        System.out.println("Free memory: " + runtime.freeMemory());
        try {
            Pane pane = loader.load();
            borderPane.setCenter(pane);
        }
        catch (IOException e) {
            System.out.println("load page catch block");
            e.printStackTrace();
        }
    }

    @Deprecated
    public void loadPage (String path) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Runtime runtime = Runtime.getRuntime();
        System.out.println("Total memory: " + runtime.totalMemory());
        System.out.println("Free memory: " + runtime.freeMemory());
        try {
            Pane pane = loader.load();
            borderPane.setCenter(pane);
        }
        catch (IOException e) {
            System.out.println("load page catch block");
            e.printStackTrace();
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
    private void loader (Pane root, String path, boolean resizable, User user, Controller controller, int... geometry) {

        FXMLLoader mLoader = new FXMLLoader(getClass().getResource(path));
        mLoader.setController(controller);
        Parent parent;
        try {
            parent = mLoader.load();
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IOException at : {0}; message: {1}\n", new Object[]{LocalTime.now(), e.getMessage()});
            e.printStackTrace();
            return;
        }
        if (user != null) RootController.getInstance().setUser(user);

        Stage stage = new Stage();
        stage.setTitle("Global Music");
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
