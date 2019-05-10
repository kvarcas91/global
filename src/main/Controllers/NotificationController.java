package main.Controllers;

import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NotificationController extends Controller {

    private static final Logger LOGGER = Logger.getLogger(NotificationController.class.getName());
    private static NotificationController instance;

    private NotificationController () {
        instance = this;
    }

    public static NotificationController getInstance() {
        if (instance == null) {
            synchronized (NotificationController.class) {
                if (instance == null) {
                    return new NotificationController();
                }
            }
        }
        else LOGGER.log(Level.INFO, "Tried to create NotificationController instance at {0}\n", LocalTime.now());
        return instance;
    }

}
