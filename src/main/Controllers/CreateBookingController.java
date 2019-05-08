package main.Controllers;

import main.Entities.Event;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateBookingController extends Controller {

    private static final Logger LOGGER = Logger.getLogger(CreateBookingController.class.getName());
    private static CreateBookingController instance;
    private Event event = null;

    private CreateBookingController () {
        instance = this;
    }

    public static CreateBookingController getInstance() {
        if (instance == null) {
            synchronized (CreateBookingController.class) {
                if (instance == null) {
                    return new CreateBookingController();
                }
            }
        }
        else LOGGER.log(Level.WARNING, "Tried to create additional instance at: {0}. Skipping and adding just pane\n",
                LocalTime.now());
        return instance;
    }

    public static void setEvent (Event event) {
        getInstance().event = event;
    }



}
