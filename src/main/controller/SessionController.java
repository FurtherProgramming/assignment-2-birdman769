package main.controller;

//simple controller for the current user data- allows current user details to be passed between
//scenes and controllers

public class SessionController {

    private String username;
    private String dateEdit = null;
    private int tableEdit = 0;
    private boolean isAdmin = false;


    public void setCurrentUser(String currentUser) {
        this.username = currentUser;
    }
    public String getUsername(){
        return this.username;
    }

    public String getDateEdit() {
        return dateEdit;
    }

    public void setDateEdit(String dateEdit) {
        this.dateEdit = dateEdit;
    }

    public int getTableEdit() {
        return tableEdit;
    }

    public void setTableEdit(int tableEdit) {
        this.tableEdit = tableEdit;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
