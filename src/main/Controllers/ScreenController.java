package main.Controllers;

import javafx.scene.layout.StackPane;

public abstract class ScreenController extends StackPane {

    private ScreenController next;

    ScreenController (ScreenController next) {
        this.next = next;
        createGUI();
    }

    abstract void createGUI();

    protected void callNext () {
        getScene().setRoot(next);
    }

}
