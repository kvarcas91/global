package main;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import main.Controllers.LoginController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Main extends Application {

    private BorderPane box = null;
    private Stage logoStage = null;
    private Stage primaryStage = null;
    private boolean isConnected = true;
    private String errorMessage = null;
    private final boolean loadAnimation = false;

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;

        if (loadAnimation) loadLogo();
        else loadLogin();
    }

    private void loadLogo () {
        Image image = null;
        try {
            image = new Image(new FileInputStream("src/main/Resources/Drawable/logo.png"));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView imageView = new ImageView(image);
        Group root = new Group();
        Scene scene = new Scene(root);
        box = new BorderPane();
        box.setPrefWidth(400);
        box.setPrefHeight(250);

        ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefWidth(400);
        progressBar.setPrefHeight(10);
        progressBar.setProgress(0);

        box.setBottom(progressBar);
        box.setCenter(imageView);
        root.getChildren().add(box);
        logoStage = new Stage();
        logoStage.initStyle(StageStyle.TRANSPARENT);
        logoStage.setScene(scene);
        logoStage.setResizable(false);

        addTransition(0, 1, 2000);
        checkNetworkConnection(progressBar);
        logoStage.show();
    }

    private void addTransition (int from, int to, int duration) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(duration));
        fadeTransition.setNode(this.box);
        fadeTransition.setFromValue(from);
        fadeTransition.setToValue(to);
        fadeTransition.play();
    }

    private void loadLogin () {
        if (logoStage != null) logoStage.close();

        FXMLLoader mLoader = new FXMLLoader(getClass().getResource("UI/login.fxml"));
        Parent root = null;
        try {
            root = mLoader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        if (!isConnected) {
            LoginController loginController = mLoader.getController();
            loginController.setError("No network connection");
        }
        //Parent root = FXMLLoader.load(getClass().getResource("UI/root.fxml"));
        primaryStage.setTitle("Welcome");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        //primaryStage.setScene(new Scene(root, 1000, 600));
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

    private void checkNetworkConnection (ProgressBar progressBar) {
        progressBar.progressProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if (t1.doubleValue() == 1) loadLogin();
                else if (t1.doubleValue() == 0.8) addTransition(1, 0, 800);
            }
        });
        Task task = taskCreator (400);
        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    private Task taskCreator (int seconds) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                for (int i = 0; i < seconds; i++) {
                    Thread.sleep(10);
                    updateProgress(i+1, seconds);
                }
                return true;
            }
        };
    }


    public static void main(String[] args) {
        launch(args);
/*
        Event aevent;
        aevent = new Event("Event Name","6 6 666","Luton","Super Event","Leo");

        aevent.ShowEvent();

        aevent.AddBand("Mona Lisa","Da Wino Ci");
        aevent.AddBand("Bolek i lolek","Reksio");

        aevent.ShowBands();

        JDBC conec;
        String query = "Select * FROM ITEM WHERE wiek = 21";
        conec = new JDBC(query, 4);
*/


    }
}
