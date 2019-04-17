package main.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import main.Entities.Booking;
import main.Interfaces.Notifications;
import main.Utils.Loader;
import main.Utils.WriteLog;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookingController extends Controller implements Initializable{

    private static final Logger LOGGER = Logger.getLogger(BookingController.class.getName());
    private static BookingController instance = null;

    @FXML private TableView<Booking> Bookings;
    @FXML private TableColumn<Booking, Integer> bookingID, concertID, bookingPrice, bookingDate;
    @FXML private JFXButton handle1, handle2;

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

    private void handleButtonAction() {
        ObservableList<Booking> list = FXCollections.observableArrayList(
                new Booking(1, 4,6, 100));

        System.out.println("Working");
        Bookings.setItems(list);
    }

    private void handleButtonAction2() {
        ObservableList<Booking> list1 = FXCollections.observableArrayList();
        list1 = list.init();

        System.out.println("Working");
        Bookings.setItems(list1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookingID.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("bookingID"));
        concertID.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("eventID"));
        bookingPrice.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("ticketTypeID"));
        bookingDate.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("quantity"));

        handle1.setOnAction(e -> handleButtonAction());
        handle2.setOnAction(e -> handleButtonAction2());

    }

    public static void Destroy () {}
}
