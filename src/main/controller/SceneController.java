package main.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class SceneController {

    public void switchToMenuLandingPage(ActionEvent event)  throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../ui/menuPage.fxml"));
        Scene menuPageScene = new Scene(root);
        stage.setScene(menuPageScene);
        stage.show();

    }

    //used after a logout request
    public void switchToLoginPage(ActionEvent event)  throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../ui/login.fxml"));
        Scene loginPageScene = new Scene(root);
        stage.setScene(loginPageScene);
        stage.show();
    }
    //when user presses book a table, load the booking page
    public void switchToBookingPage(ActionEvent event) throws IOException {
        System.out.println("switching");
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../ui/booking.fxml"));
        Scene BookingPageScene = new Scene(root);
        stage.setScene(BookingPageScene);
        stage.show();
    }
}



