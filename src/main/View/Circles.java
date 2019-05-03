package main.View;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import main.Utils.WriteLog;

import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Circles {

    private static final String COLOUR = "#5375da";
    private static HBox layout = null;
    private static Circles instance = null;
    private static int circleCount = 3;
    private static final Logger LOGGER = Logger.getLogger(Circles.class.getName());


    private Circles (HBox mLayout) {
        instance = this;
        layout = mLayout;
        WriteLog.addHandler(LOGGER);
        LOGGER.log(Level.INFO, "Creating Circles instance from constructor at: {0}\n", LocalTime.now());
    }

    public static Circles getInstance() {
        if (instance != null) return instance;
        else return null;
    }

    public static Circles createInstance (HBox mLayout) {
        if (instance == null) {
            synchronized (Circles.class) {
                if (instance == null) {
                    return new Circles(mLayout);
                }
            }
        }
        else LOGGER.log(Level.INFO, "Tried to create Cirlces instance at {0}\n", LocalTime.now());
    return instance;
    }

    public static void draw (int position) {
        LOGGER.log(Level.INFO, "Drawing progressLayout at: {0}; position: {1}\n", new Object[]{LocalTime.now(), position});
        int size;
        layout.getChildren().clear();

        // draw Progress
        for (int i = 0; i < circleCount; i++) {
            Circle progress;
            if (i <= position) {
                size = 7;
            }
            else {
                size = 3;
            }
            progress = new Circle(0, 0, size);
            progress.setFill(Color.web(COLOUR));
            progress.setStroke(Color.web(COLOUR));

            layout.getChildren().add(progress);
        }
    }

    public static void draw (int position, String colour) {
        LOGGER.log(Level.INFO, "Drawing progressLayout at: {0}; position: {1}\n", new Object[]{LocalTime.now(), position});
        int size;
        layout.getChildren().clear();

        // draw Progress
        for (int i = 0; i < 3; i++) {
            Circle progress;
            if (i <= position) {
                size = 7;
            }
            else {
                size = 3;
            }
            progress = new Circle(0, 0, size);
            progress.setFill(Color.web(colour));
            progress.setStroke(Color.web(colour));

            layout.getChildren().add(progress);
        }
    }

    public static void injectLayout (HBox node) {
        layout = node;
    }
    public static void injectCount (int count) {
        circleCount = count;
    }
}
