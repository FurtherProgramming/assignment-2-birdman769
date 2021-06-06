package main.controller;

import javafx.event.ActionEvent;
import java.io.IOException;

public class MenuController {
    public SceneController SceneController;

    public void Logout(ActionEvent event) throws IOException {
        SceneController = new SceneController();
        SceneController.switchToLoginPage(event);
        System.out.println("sceneController logout Successful");

    }
    public void BookTablePage(ActionEvent event) throws IOException{
        SceneController = new SceneController();
        SceneController.switchToBookingPage(event);
    }
    public void manageBookingPage(ActionEvent event) throws IOException {
        SceneController = new SceneController();
        SceneController.switchToManageBooking(event);
    }

    public void manageAccountPage(ActionEvent event) throws IOException {
        SceneController = new SceneController();
        SceneController.switchToManageAccount(event);
    }

    public void adminBookingManager(ActionEvent event) throws IOException {
        SceneController = new SceneController();
        SceneController.switchToBookingManager(event);
    }

    public void adminUserManager(ActionEvent event) throws IOException {
        SceneController = new SceneController();
        SceneController.switchToAdminManageAccount(event);
    }

    public void adminCSV(ActionEvent event) {
    }
}
