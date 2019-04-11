package main.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Entities.Booking;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class list {

    public static ObservableList<Booking> init() {
        ObservableList<Booking> bookinglist = FXCollections.observableArrayList(
                //new Booking("1|33|256|100|20/3/2019"),
                //new Booking("2|356|356|100|20/2/2019"),
                //new Booking("3|555|555|100|20/4/2019"),
                //new Booking("4|256|256|100|28/3/2019"));
                new Booking(1, 2, 3, 20));

        return bookinglist;
    }

}