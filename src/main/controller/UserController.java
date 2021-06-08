package main.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.Main;
import main.model.BookingModel;
import main.model.UserModel;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class UserController {

    @FXML
    private TextField account,firstName, lastName, age;
    @FXML
    private Label updateStatus;

    private UserModel userModel = new UserModel();
    private BookingModel bookingModel = new BookingModel();
    private SessionController sessionController = Main.getSessionController();
    private Map<String, String> userDetails = new HashMap<>();

    //our methods
    //initilize our bookings on the Manage Booking Page
    public void initialize() throws SQLException {
        if(!sessionController.isAdmin()){
            userDetails=userModel.getUserDetails(sessionController.getUsername());
            firstName.setText(userDetails.get("firstName"));
            lastName.setText(userDetails.get("lastName"));
            age.setText(userDetails.get("age"));
        }
    }
    public void submitDetails() {
        boolean success;
        success = userModel.updateUserDetails(sessionController.getUsername(),firstName.getText(), lastName.getText(), Integer.parseInt(age.getText()));
        if(success == true)
            updateStatus.setText("update success!");
        else
            updateStatus.setText("update failed!");
    }
    public void adminSubmitDetails(ActionEvent event) {
        boolean success;
        success = userModel.updateUserDetails(account.getText(),firstName.getText(), lastName.getText(), Integer.parseInt(age.getText()));
        if(success == true)
            updateStatus.setText("update success!");
        else
            updateStatus.setText("update failed!");
    }
    public void toggleAdmin() throws SQLException {
        userModel.toggleAdmin(account.getText());
        boolean isAdmin = userModel.isUserAdmin(account.getText());
        if(isAdmin)
            updateStatus.setText("user is now an admin!");
        else
            updateStatus.setText("user is no longer an admin!");
    }
    public void searchUser() throws SQLException {
        userDetails=userModel.getUserDetails(account.getText());
        firstName.setText(userDetails.get("firstName"));
        lastName.setText(userDetails.get("lastName"));
        age.setText(userDetails.get("age"));
    }
    public void back(ActionEvent event) throws IOException {
        SceneController sceneController = new SceneController();
        sceneController.switchToMenuLandingPage((event));
    }

    public void adminBack(ActionEvent event) throws IOException {
        SceneController sceneController = new SceneController();
        sceneController.switchToAdminLandingPage(event);
    }


    public void deleteUser(ActionEvent event) throws SQLException {
        boolean deleted= userModel.deleteUser(account.getText());
        System.out.println(deleted);
        if(deleted) {
            updateStatus.setText("user and their bookings deleted!");
            bookingModel.deleteAllBookings(account.getText());
        }
    }
}
