package main.controller;

//simple controller for the current user data- allows current user details to be passed between
//scenes and controllers

public class UserController {

    private String username;


    public void setCurrentUser(String currentUser) {
        this.username = currentUser;
    }
    public String getUsername(){
        return this.username;
    }
}
