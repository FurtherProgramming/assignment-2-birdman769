package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.Main;
import main.model.UserModel;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class UserController {

    @FXML
    private TextField firstName, lastName, age;
    @FXML
    private Label updateStatus;

    UserModel userModel = new UserModel();
    SessionController sessionController = Main.getSessionController();
    Map<String, String> userDetails = new HashMap<>();


    //our methods
    //initilize our bookings on the Manage Booking Page
    public void initialize() throws SQLException {
        userDetails=userModel.getUserDetails(sessionController.getUsername());
        firstName.setText(userDetails.get("firstName"));
        lastName.setText(userDetails.get("lastName"));
        age.setText(userDetails.get("age"));
    }

    public void submitDetails(ActionEvent event) {
        boolean success;
        success = userModel.updateUserDetails(sessionController.getUsername(),firstName.getText(), lastName.getText(), Integer.parseInt(age.getText()));
        if(success == true)
            updateStatus.setText("update success!");
        else
            updateStatus.setText("update failed!");
    }

    public void back(ActionEvent event) throws IOException {
        SceneController sceneController = new SceneController();
        sceneController.switchToMenuLandingPage((event));
    }
}
