package main.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Entities.Booking;
import main.Entities.Entity;
import main.Entities.User;
import main.Networking.JDBC;
import main.Utils.WriteLog;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookingController extends Controller implements Initializable{

    private static final Logger LOGGER = Logger.getLogger(BookingController.class.getName());
    private static BookingController instance = null;

    @FXML private TableView<Booking> Bookings;
    @FXML private TableColumn<Booking, Integer> bookingID, concertID, bookingPrice, bookingDate;
    private User user = null;

    private BookingController () {
        instance = this;
        WriteLog.addHandler(LOGGER);
        LOGGER.log(Level.INFO, "Creating BookingController instance from constructor at: {0}\n", LocalTime.now());
    }

    public static BookingController getInstance() {
        if (instance == null) {
            synchronized (FestivalController.class) {
                if (instance == null) {
                    return new BookingController();
                }
            }
        }
        else LOGGER.log(Level.WARNING, "Tried to create additional instance at: {0}\n", LocalTime.now());
        return instance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = RootController.getInstance().getUser();

        // TODO does user need to know all these ID's? What about cancel booking only for root and admin types?

        bookingID.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("bookingID"));
        concertID.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("eventID"));
        bookingPrice.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("ticketTypeID"));
        bookingDate.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("quantity"));

        ObservableList<Booking> list = FXCollections.observableArrayList();

        String query = "SELECT * FROM BOOKING WHERE User_ID = '" + user.getUserID() + "';";
        Booking temp;

        ArrayList<Entity> objects = JDBC.getAll(query, Booking.class.getName());
        for(Entity obj : objects) {
            temp = (Booking) obj;
            list.add(temp);
        }

        Bookings.setItems(list);
    }

    public static void Destroy () {
        if (instance != null) {
            LOGGER.log(Level.INFO, "Destroying BookingController instance at: {0}\n", LocalTime.now());
            getInstance().user = null;
            instance = null;
        }
    }
}
