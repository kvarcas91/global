package main.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Entities.Booking;

import java.net.URL;
import java.util.ResourceBundle;

public class BookingController implements Initializable {

    /**
     *          ########################################################
     *          #############   How to create more pages   #############
     *          ########################################################
     *  Loader loader = new Loader(RootController.getInstance().getContent());
     *  loader.loadPage(fxml file);  // fxml file is a String of that file location. i.e.: ../UI/account.fxml
     *          ########################################################
     */

    @FXML
    public TableView<Booking> Bookings;

    @FXML public TableColumn<Booking, String> BookingID;
    @FXML public TableColumn<Booking, String> ConcertID;
    @FXML public TableColumn<Booking, String> bookingPrice;
    @FXML public TableColumn<Booking, String> BookingDate;

    /*public ObservableList<Booking> list = FXCollections.observableArrayList(
            new Booking("1|33|256|25|20/3/2019"),
            new Booking("2|356|356|55|20/2/2019"),
            new Booking("3|555|555|35|20/4/2019"),
            new Booking("4|256|256|45|28/3/2019")
    );*/



    @FXML
    private void handleButtonAction(ActionEvent event) {
        ObservableList<Booking> list = FXCollections.observableArrayList(
                new Booking("1|33|256|25|20/3/2019"),
                new Booking("2|356|356|55|20/2/2019"),
                new Booking("3|555|555|35|20/4/2019"),
                new Booking("4|256|256|45|28/3/2019"));

        System.out.println("Working");
        Bookings.setItems(list);
    }

    @FXML
    private void handleButtonAction2(ActionEvent event) {
        ObservableList<Booking> list1 = FXCollections.observableArrayList();
        list1 = list.init();

        System.out.println("Working");
        Bookings.setItems(list1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BookingID.setCellValueFactory(new PropertyValueFactory<Booking, String>("BookingID"));
        ConcertID.setCellValueFactory(new PropertyValueFactory<Booking, String>("concertID"));
        bookingPrice.setCellValueFactory(new PropertyValueFactory<Booking, String>("bookingPrice"));
        BookingDate.setCellValueFactory(new PropertyValueFactory<Booking, String>("BookingDate"));

    }

}
