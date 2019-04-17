package main.View;

import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import main.Utils.WriteLog;

import java.time.LocalTime;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NotificationPane {

    private static Pane pane;
    private static Text textField;
    private static NotificationPane instance;
    private final static Logger LOGGER = Logger.getLogger(NotificationPane.class.getName());

    private NotificationPane (Pane mPane, Text mTextField) {
        pane = mPane;
        textField = mTextField;
        instance = this;
        WriteLog.addHandler(LOGGER);
        LOGGER.log(Level.INFO, "Creating NotificationPane instance from constructor at: {0}\n", LocalTime.now());
    }


    public static NotificationPane getInstance() {
        return instance;
    }

    /**
     * Dependency injection
     * These parameters cannot be null
     * @param pane layout
     * @param mTextField Text Field
     */
    public static void createInstance(Pane pane, Text mTextField){
        Objects.requireNonNull(pane, "Node cannot be null");
        Objects.requireNonNull(mTextField, "Text widget cannot be null");

        if (instance == null) {
            synchronized (NotificationPane.class) {
                if (instance == null) {
                    try {
                        new NotificationPane(pane, mTextField);
                    }
                    catch (NullPointerException e) {
                        LOGGER.log(Level.SEVERE, "NullPointerException at: {0}. Params cannot be null. Message: {1}\n",
                                new Object[]{LocalTime.now(), e.getMessage()});
                    }
                }
            }
        }
        else {
            NotificationPane.pane = pane;
            textField = mTextField;
        }
    }

    public static void show (String message, String colour) {

        if (message != null) {
            getInstance().showNotification(message, colour);
        }
    }

    public static void show (String message) {

        if (message != null) {
            getInstance().showNotification(message, null);
        }
    }


    private void showNotification (String message, String colour) {
        LOGGER.log(Level.INFO, "Showing NotificationPane with message: {0} at: {1}\n", new Object[]{message, LocalTime.now()});

        pane.setVisible(true);
        textField.setText(message);

        if (colour != null) pane.setStyle(String.format("-fx-background-color: %s;", colour));

        Task task = hideNotificationPane();
        new Thread(task).start();

    }

    private Task hideNotificationPane() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                Thread.sleep(2000);
                pane.setVisible(false);
                return true;
            }
        };

    }

    public static void Destroy () {
        if (instance != null) {
            LOGGER.log(Level.INFO, "Destroying NotificationPane instance at: {0}\n", LocalTime.now());
            instance = null;
        }
    }

}
