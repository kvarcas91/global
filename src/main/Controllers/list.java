package main.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Entities.Booking;

public class list {

    public static ObservableList<Booking> init() {
        ObservableList<Booking> bookinglist = FXCollections.observableArrayList(
                //new Booking("1|33|256|100|20/3/2019"),
                //new Booking("2|356|356|100|20/2/2019"),
                //new Booking("3|555|555|100|20/4/2019"),
                //new Booking("4|256|256|100|28/3/2019"));
                new Booking(1, 2, 3, 20, "2018/10/22"));

        return bookinglist;
    }
}