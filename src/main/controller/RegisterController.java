package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.model.RegisterModel;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class RegisterController {

    public SceneController SceneController;
    public RegisterModel registerModel = new RegisterModel();

    @FXML
    private TextField usernameRegister;

    @FXML
    private TextField firstNameRegister;
    @FXML
    private TextField lastNameRegister;
    @FXML
    private TextField ageRegister;
    @FXML
    private TextField passwordRegister;
    @FXML
    private Text registerError;

    public void changeToLogin(ActionEvent event) throws IOException{
        SceneController = new SceneController();
        SceneController.switchToLoginPage(event);
    }

    public void registerUser(ActionEvent event) throws IOException, SQLException {
        //validate the user input in the form
        int errors = 0;
       String username = usernameRegister.getText();
       if (username.equals("")) {
           registerError.setText("input username!");
           errors++;
       }
       String firstName = firstNameRegister.getText();
       if(firstName.equals("")){
           registerError.setText("input first name!");
           errors++;
       }
       String lastName = lastNameRegister.getText();
        if(lastName.equals("")){
            registerError.setText("input last name!");
            errors++;
        }
       String age = ageRegister.getText();
        if(age.equals("")){
            registerError.setText("input age");
            errors++;
        }
        try{
            int test = Integer.parseInt(age);
        }catch(Exception e){
            registerError.setText("input integer only!");
        }
       String password = passwordRegister.getText();
        if(password.equals("")){
            registerError.setText("input password");
            errors++;
        }
       if(errors ==0) {
           System.out.println(username + firstName+lastName+age+password);
           registerModel.RegisterUser(firstName,lastName,Integer.parseInt(age),username,password);
           this.changeToLogin(event);
       }

    }

}
