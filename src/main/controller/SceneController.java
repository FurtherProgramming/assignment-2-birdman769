package main.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
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

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../ui/booking.fxml"));
        Scene BookingPageScene = new Scene(root);
        stage.setScene(BookingPageScene);
        stage.show();
    }
    public void switchToManageBooking(ActionEvent event) throws IOException {

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../ui/manageBooking.fxml"));
        Scene BookingPageScene = new Scene(root);
        stage.setScene(BookingPageScene);
        stage.show();
    }

    public void switchToManageAccount(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../ui/manageAccount.fxml"));
        Scene BookingPageScene = new Scene(root);
        stage.setScene(BookingPageScene);
        stage.show();
    }

    public void switchToRegisterPage(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../ui/register.fxml"));
        Scene BookingPageScene = new Scene(root);
        stage.setScene(BookingPageScene);
        stage.show();
    }

    public void switchToResetPage(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../ui/reset.fxml"));
        Scene BookingPageScene = new Scene(root);
        stage.setScene(BookingPageScene);
        stage.show();
    }

    public void switchToQuestionPage(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../ui/question.fxml"));
        Scene BookingPageScene = new Scene(root);
        stage.setScene(BookingPageScene);
        stage.show();
    }


}



