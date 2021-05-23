package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

import java.io.IOException;

public class MenuController {
    public SceneController SceneController;


    public void Logout(ActionEvent event) throws IOException {
        SceneController = new SceneController();
        SceneController.switchToLoginPage(event);
        System.out.println("sceneController logout Successful");


    }
}
