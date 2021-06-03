package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import main.Main;
import main.model.LoginModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public SceneController SceneController;
    private String userName;

    public LoginModel loginModel = new LoginModel();
    public UserController userController = new UserController();
    @FXML
    private Label isConnected;
    @FXML
    public TextField txtUsername;
    @FXML
    private TextField txtPassword;



    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){
        if (loginModel.isDbConnected()){
            isConnected.setText("Connected");
        }else{
            isConnected.setText("Not Connected");
        }

    }
    /* login Action method
       check if user input is the same as database.
     */
    public void Login(ActionEvent event){
        SceneController = new SceneController();

        try {
            if (loginModel.isLogin(txtUsername.getText(),txtPassword.getText())){
                this.userName= txtUsername.getText();
                userController.setCurrentUser(txtUsername.getText());
                Main.updateUserController(userController);
                userController = Main.getUserController();
                System.out.println("current user:  "+ userController.getUsername());
                SceneController.switchToMenuLandingPage(event);

            }else{
                isConnected.setText("username and password is incorrect");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }


    public String getUserName() {
        return userName;
    }

    public void Register(ActionEvent event) throws IOException {
        SceneController = new SceneController();
        SceneController.switchToRegisterPage(event);
    }

    public void ForgotPassword(ActionEvent event) throws IOException {
        SceneController = new SceneController();
        SceneController.switchToResetPage(event);
    }
}
