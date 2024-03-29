package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import test.model.RegisterModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class RegisterController {

    private SceneController SceneController;
    private RegisterModel registerModel = new RegisterModel();
    @FXML
    private TextField usernameRegister, firstNameRegister, lastNameRegister, ageRegister, passwordRegister,
            questionRegister, answerRegister, resetUserInput;
    @FXML
    private Text registerError, resetMode;
    //for password reset
    private String usernameReset = null;
    private boolean resetPasswordMode= false;
    private boolean validPass = false;
    private Map<String, String> question= null;




    public void changeToLogin(ActionEvent event) throws IOException{
        SceneController = new SceneController();
        SceneController.switchToLoginPage(event);
    }
    public void switchToQuestion(ActionEvent event) throws IOException{
        SceneController = new SceneController();
        SceneController.switchToQuestionPage(event);
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
        String question = questionRegister.getText();
        if(question.equals("")){
            registerError.setText("input question");
            errors++;
        }
        String answer = answerRegister.getText();
        if(answer.equals("")){
            registerError.setText("input question answer!");
            errors++;
        }
       if(errors ==0) {
           registerModel.RegisterUser(firstName,lastName,Integer.parseInt(age),username,password, question, answer);
           this.changeToLogin(event);
       }

    }
    public void resetPassword(ActionEvent event) throws IOException{


        if(resetMode.getText().equals("Enter Username")){
            usernameReset = resetUserInput.getText();
            question = registerModel.getQuestion(resetUserInput.getText());
            resetMode.setText(question.get("question"));
            resetUserInput.setText("");
        }else{
            if(resetUserInput.getText().equals(question.get("answer"))){
                resetPasswordMode = true;
                resetMode.setText("enter new password: ");
                resetUserInput.setText("");

            }

        }
        if(resetPasswordMode ==true && !resetUserInput.getText().equals("")){
            validPass= registerModel.setNewPassword(usernameReset, resetUserInput.getText());
            if(validPass == true)
                resetMode.setText("success");

            else
                resetMode.setText("failed");
            resetPasswordMode = false;
        }



    }

}
