package main.Interfaces;

import javafx.concurrent.Task;

public interface NotificationPane {

    void setNotificationPane(String message, String color);
    Task hideNotificationPane();

}
