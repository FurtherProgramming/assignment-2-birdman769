package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.controller.SessionController;

public class Main extends Application {
    //functions similar to a global databundle. This global userController can be passed between scenes/controllers after the user has logged in.
    //username is set in the controller during login, this can be accessed by a instantiating a local userController when information on user
    //is needed
    public static SessionController sessionController = new SessionController();
    public static SessionController getSessionController(){
        return sessionController;
    }
    public static void updateUserController(SessionController updated){
        sessionController = updated;
    }
    @Override
    public void start(Stage primaryStage) throws Exception{


        Parent root = FXMLLoader.load(getClass().getResource("ui/login.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
}
