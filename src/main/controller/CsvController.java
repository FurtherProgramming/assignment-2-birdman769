package main.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import test.model.CsvModel;

import java.io.IOException;
import java.time.LocalDate;

public class CsvController {

    private CsvModel csvModel = new CsvModel();

    private LocalDate date = null;
    @FXML
    private DatePicker javaDate;
    @FXML
    private Label status;

    public void generateCsvEmployees(){
        csvModel.generateCsvEmployees();
        status.setText("csv exported!");
    }
    public void setDate(ActionEvent event)  {
        date= javaDate.getValue();
        System.out.println(date);
    }
    public void generateBookingCsv(ActionEvent event) {
        if(date!= null){
            csvModel.generateCsvBooking(date);
            status.setText("csv exported!");
        }else{
            status.setText("please choose a date");
        }
    }
    public void adminBack(ActionEvent event) throws IOException {
        SceneController sceneController = new SceneController();
        sceneController.switchToAdminLandingPage(event);
    }
}
