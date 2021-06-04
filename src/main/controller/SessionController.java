package main.controller;

//simple controller for the current user data- allows current user details to be passed between
//scenes and controllers

public class SessionController {

    private String username;
    private String dateEdit = null;
    private int tableEdit = 0;
    private boolean isAdmin = false;
    private boolean adminEditing = false;


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

    public boolean isAdminEditing() {
        return adminEditing;
    }

    public void setAdminEditing(boolean adminEditing) {
        this.adminEditing = adminEditing;
    }
    public void reset(){
        setAdmin(false);
        setAdminEditing(false);
        setTableEdit(0);
        setDateEdit(null);
    }
}
