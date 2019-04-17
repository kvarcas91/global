package main.View;

import javafx.animation.FadeTransition;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import main.Utils.WriteLog;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogoView {

    private static BorderPane box;
    private static ProgressBar progressBar;
    private static LogoView instance = null;
    private static final Logger LOGGER = Logger.getLogger(LogoView.class.getName());

    private LogoView() {
        instance = this;
        WriteLog.addHandler(LOGGER);
        LOGGER.log(Level.INFO, "Creating LogoView instance from constructor at: {0}\n", LocalTime.now());
    }

    private static LogoView getInstance() {
        if (instance == null) {
            synchronized (LogoView.class) {
                if (instance == null) {
                    return new LogoView();
                }
            }
        }
        else LOGGER.log(Level.INFO, "Tried to create LogoView instance at {0}\n", LocalTime.now());
        return instance;
    }

    public static Stage getView () {
        LOGGER.log(Level.INFO, "Creating LOGO view at: {0}\n", LocalTime.now());
        Image image = null;

        try {
            image = new Image(new FileInputStream("src/main/Resources/Drawable/logo.png"));
        }
        catch (FileNotFoundException e) {
            LOGGER.log(Level.WARNING, "FileNotFoundException at: {0}; error message: {1}\n", new Object[]{LocalTime.now(), e.getMessage()});
        }

        ImageView imageView = new ImageView(image);
        Group root = new Group();
        Scene scene = new Scene(root);
        box = new BorderPane();
        box.setPrefWidth(400);
        box.setPrefHeight(250);

        progressBar = new ProgressBar();
        progressBar.setPrefWidth(400);
        progressBar.setPrefHeight(10);
        progressBar.setProgress(0);

        box.setBottom(progressBar);
        box.setCenter(imageView);
        root.getChildren().add(box);
        Stage logoStage = new Stage();
        logoStage.initStyle(StageStyle.TRANSPARENT);
        logoStage.setScene(scene);
        logoStage.setResizable(false);

        return logoStage;
    }

    public static void addTransition (int from, int to, int duration) {
        LOGGER.log(Level.INFO, "Adding logo transition at: {0}\n", LocalTime.now());
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(duration));
        fadeTransition.setNode(box);
        fadeTransition.setFromValue(from);
        fadeTransition.setToValue(to);
        fadeTransition.play();
    }

    public static void updateProgressBar () {
        Task task = getInstance().updateProgressBarTask(400);
        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    private Task updateProgressBarTask (int seconds) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                for (int i = 0; i < seconds; i++) {
                    updateProgress(i + 1, seconds);
                    Thread.sleep(8);
                }
                return true;
            }
        };
    }

    public static ProgressBar getProgressBar () {
        return progressBar;
    }

    public static void Destroy () {
        if (instance != null) {
            LOGGER.log(Level.INFO, "Destroying LogoView instance at: {0}\n", LocalTime.now());
            instance = null;
        }
    }
}
