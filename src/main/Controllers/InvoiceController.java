package main.Controllers;

import main.Utils.WriteLog;

import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InvoiceController extends Controller {

    private static final Logger LOGGER = Logger.getLogger(InvoiceController.class.getName());
    private static InvoiceController instance = null;


    private InvoiceController() {
        instance = this;
        WriteLog.addHandler(LOGGER);
        LOGGER.log(Level.INFO, "Creating InvoiceController instance from constructor at: {0}\n", LocalTime.now());
    }

    public static InvoiceController getInstance() {
        if (instance == null) {
            synchronized (InvoiceController.class) {
                if (instance == null) {
                    return new InvoiceController();
                }
            }
        } else LOGGER.log(Level.WARNING, "Tried to create additional instance at: {0}\n", LocalTime.now());
        return instance;
    }



}
