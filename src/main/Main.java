package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import main.Controllers.LoginController;
import main.Networking.JDBC;
import main.Utils.Loader;
import main.Utils.WriteLog;
import main.View.LogoView;
import java.io.IOException;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main extends Application {


    private Stage logoStage = null;
    private static Stage primaryStage = null;
    private final boolean loadAnimation = true;
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    // This method loads LOGO view for 2 seconds. On mean time program is connecting to mySQL server and creating Loader
    // instance
    private void loadLogo () {
        LOGGER.info("Loading logo\n");


        JDBC.createConnection();
        Loader.getInstance();

        logoStage = LogoView.getView();
        LogoView.addTransition(0, 1, 2000);

        ProgressBar loadingBar = LogoView.getProgressBar();
        loadingBar.progressProperty().addListener((observableValue, number, t1) -> {
            if (t1.doubleValue() == 1) loadLogin();
            else if (t1.doubleValue() == 0.8) LogoView.addTransition(1, 0, 800);
        });

        LogoView.updateProgressBar();

        logoStage.show();
    }

    private void loadLogin () {
        LOGGER.log(Level.INFO, "Loading Login page... at: {0}\n", LocalTime.now());
        if (logoStage != null) {
            logoStage.close();
            LogoView.Destroy();
        }

        FXMLLoader mLoader = new FXMLLoader(getClass().getResource("UI/login.fxml"));
        mLoader.setController(LoginController.getInstance());
        Parent root = null;
        try {
            root = mLoader.load();
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IOEXception at: {0}; message: {1}\n", new Object[]{LocalTime.now(), e.getMessage()});
            JDBC.Destroy();
            e.printStackTrace();
            System.exit(0);
        }

        primaryStage.setTitle("Welcome");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    @Override
    public void start(Stage stage){
        primaryStage = stage;
        WriteLog.of();
        WriteLog.addHandler(LOGGER);

        Runtime.getRuntime().addShutdownHook(new Thread(() ->{
            WriteLog.close();
        }));

        if (loadAnimation) loadLogo();
        else loadLogin();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
