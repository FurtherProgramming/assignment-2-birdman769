package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class BookingController {
    @FXML
    private DatePicker date;
    @FXML
    private Text mainTitle;
    @FXML
    private Text bookingDate;
    @FXML
    private Text bookingTableNumber;

    private LocalDate javaDate;

    public SceneController SceneController;

    public void setDate(ActionEvent event) throws IOException{
        javaDate= date.getValue();
        mainTitle.setText("Pick A Table: "+ javaDate);

    }
    public void submitBooking(ActionEvent event) throws IOException{
        SceneController = new SceneController();
        SceneController.switchToMenuLandingPage(event);


    }
    public LocalDate getDate(){
        return this.javaDate;
    }
}
