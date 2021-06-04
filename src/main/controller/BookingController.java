package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Pair;
import main.Main;
import main.model.BookingModel;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//due to a large amount of shared variables and features, this booking controller functions for both the admin booking page
//and the user booking page

public class BookingController {

    //FXML and var for booking page
    @FXML
    private DatePicker date;
    @FXML
    private Text mainTitle, BookingStatus, confirmed;

    @FXML
    private Button tab1,tab2,tab3,tab4,tab5,tab6,tab7,tab8,tab9;

    private int tableNumber;

    private LocalDate javaDate;

    //fxml and variables for manageBooking page

    @FXML
    private Text bookingDate, bookingTableNumber,changeStatus;

    private int bookingTarget = 1;
    private Pair<Integer, String> pair= null;


    //admin ManageBooking variables
    @FXML
    private Text usernameTarget;
    //shared variables across all pages
    public SceneController SceneController;

    public BookingModel bookingModel = new BookingModel();

    private SessionController sessionController = Main.getSessionController();


    //our methods
    //initilize our bookings on the Manage Booking Page
    public void initialize() throws SQLException {
        try{
            getNextBooking();
        }catch(Exception e){
            //this prevents an error if we are loading the booking page and not the manage page
        }
        if(sessionController.isAdminEditing()){
            System.out.println("admin is editing");
            isBookingConfirmed(sessionController.getTableEdit(), sessionController.getDateEdit());
            System.out.print(sessionController.getTableEdit());
            System.out.print(sessionController.getDateEdit());
            bookingTableNumber.setText(String.valueOf(sessionController.getTableEdit()));


        }

    }

    //user booking page methods
    public void getNextBooking() throws SQLException {
        int totalBookings= bookingModel.getUserTotalBookings(sessionController.getUsername());
        if(bookingTarget >= totalBookings+2)
            bookingTarget= 2;
        pair = bookingModel.getNextBooking(sessionController.getUsername(), bookingTarget);
        bookingTarget++;
        bookingTableNumber.setText(String.valueOf(pair.getKey()));
        bookingDate.setText(pair.getValue());
        changeStatus.setText("");
        boolean isConfirmed= isBookingConfirmed(pair.getKey(),pair.getValue());
        if(isConfirmed)
            confirmed.setText("confirmed");
        else
            confirmed.setText("unconfirmed");

    }
    public boolean isBookingConfirmed(int table, String date) throws SQLException {
        boolean isConfirmed;
        isConfirmed= bookingModel.isBookingConfirmed(table,date.toString());
        System.out.println(isConfirmed);
        if(sessionController.isAdminEditing()){
            System.out.println("we are here");
            bookingDate.setText(date);
            if(isConfirmed)
            confirmed.setText("confirmed booking");
        }
        usernameTarget.setText(bookingModel.getBookingUsername(table,date));
        return isConfirmed;
    }



    public void setDate(ActionEvent event) throws IOException, SQLException {
        javaDate= date.getValue();
        mainTitle.setText("Pick A Table: "+ javaDate);
        this.showBookedTables(javaDate);

    }

    private void showBookedTables(LocalDate javaDate) throws SQLException {
        ArrayList<Integer> tablesBooked= new ArrayList<Integer>();
        tablesBooked = bookingModel.getBookedTables(javaDate);
        ArrayList<Button> buttons = new ArrayList<Button>();
        List<Button> buttonsList = Arrays.asList(tab1,tab2,tab3,tab4,tab5,tab6,tab7,tab8,tab9);
        buttons.addAll(buttonsList);
        for (int i=0; i < buttons.size(); i++)
            buttons.get(i).setTextFill(Color.BLACK);
        for (int i=0; i < tablesBooked.size(); i++)
            buttons.get(tablesBooked.get(i)-1).setTextFill(Color.RED);
        showCovidTables(javaDate);
    }
    public void showCovidTables(LocalDate javaDate) throws SQLException {
        ArrayList<Integer> tablesLocked= new ArrayList<Integer>();
        tablesLocked = bookingModel.getCovidTables(javaDate);
        ArrayList<Button> buttons = new ArrayList<Button>();
        List<Button> buttonsList = Arrays.asList(tab1,tab2,tab3,tab4,tab5,tab6,tab7,tab8,tab9);
        buttons.addAll(buttonsList);
        for (int i=0; i < tablesLocked.size(); i++)
            buttons.get(tablesLocked.get(i)-1).setTextFill(Color.ORANGE);
    }

    public void submitBooking(ActionEvent event) throws  SQLException {
        try {
            boolean otherUserBooking;
            boolean userBookingExist;
            boolean bookingSuccess;
            boolean covidLocked = false;
            userBookingExist = bookingModel.doesUserBookingExist(javaDate, sessionController.getUsername());
            covidLocked = bookingModel.isDateCovidLocked(javaDate, tableNumber);
            if(!covidLocked) {
                if (!userBookingExist) {
                    otherUserBooking = bookingModel.doesOtherUserBookingExist(javaDate, tableNumber);
                    if (!otherUserBooking) {
                        bookingSuccess = bookingModel.addBooking(javaDate, tableNumber, sessionController.getUsername());

                        if (bookingSuccess) {
                            BookingStatus.setText("successful booking");
                            this.showBookedTables(javaDate);
                            //if user is updating rather than making a new booking:
                            if (sessionController.getTableEdit() != 0 && !sessionController.getDateEdit().equals(null)) {
                                bookingModel.removeBooking(sessionController.getDateEdit(), sessionController.getTableEdit());
                            }
                        } else
                            BookingStatus.setText("booking failed");
                    } else {
                        BookingStatus.setText("another user has booked this");
                    }
                } else {
                    BookingStatus.setText("you already have a booking");

                }
            }else{
                BookingStatus.setText("this table is covid restricted");

            }
        }catch(Exception E){
            BookingStatus.setText("booking failed");
        }
    }
    public void editBooking(ActionEvent event) throws IOException {
        sessionController.setDateEdit(bookingDate.getText());
        sessionController.setTableEdit(Integer.parseInt(bookingTableNumber.getText()));
        SceneController = new SceneController();
        SceneController.switchToBookingPage((event));

    }
    public void cancelBooking(ActionEvent event) throws SQLException {
        boolean success;
        success= bookingModel.removeBooking(bookingDate.getText(),Integer.parseInt(bookingTableNumber.getText()));
        if(sessionController.isAdminEditing())
        this.getNextBooking();
        if(success)
        changeStatus.setText("booking deleted!");

    }

    //admin booking page methods

    @FXML
    private void adminCovid(ActionEvent event) throws SQLException {
        boolean lock= bookingModel.addCovidLock(javaDate, tableNumber);
        System.out.println(lock);
        if(lock){
            BookingStatus.setText("table " + tableNumber + " locked!!");
        }else {
            lock = bookingModel.removeCovidLock(javaDate, tableNumber);
            if(lock)
                BookingStatus.setText("table " + tableNumber + " is now unlocked!");
        }
        showBookedTables(javaDate);

    }
    //simple functionality and table assignment below


//these methods assign our table number on click by the user
    public void table1(ActionEvent event) { this.tableNumber=1; }
    public void table2(ActionEvent event)  { this.tableNumber=2; }
    public void table3(ActionEvent event)  { this.tableNumber=3; }
    public void table4(ActionEvent event)  { this.tableNumber=4; }
    public void table5(ActionEvent event)  { this.tableNumber=5; }
    public void table6(ActionEvent event)  { this.tableNumber=6; }
    public void table7(ActionEvent event)  { this.tableNumber=7; }
    public void table8(ActionEvent event)  { this.tableNumber=8; }
    public void table9(ActionEvent event)  { this.tableNumber=9; }

    public void back(ActionEvent event) throws IOException {
        SceneController = new SceneController();
        SceneController.switchToMenuLandingPage((event));

    }

    public void adminBack(ActionEvent event) throws IOException {
        sessionController.reset();
        SceneController = new SceneController();
        SceneController.switchToAdminLandingPage((event));
    }

    public void adminEditBooking(ActionEvent event) throws IOException {
        sessionController.setTableEdit(tableNumber);
        sessionController.setDateEdit(javaDate.toString());
        System.out.println("ADMIN SET "+ tableNumber+ " DATE "+ javaDate.toString());
        sessionController.setAdminEditing(true);
        SceneController = new SceneController();
        SceneController.switchToAdminManageBookingPage((event));

    }

    public boolean confirmBooking(ActionEvent event) {

        String user = usernameTarget.getText();
        if(user.equals("")){
            changeStatus.setText("no user booked on this date!");
            return false;
        }
        else {
            boolean confirm = bookingModel.confirmBooking(sessionController.getTableEdit(), sessionController.getDateEdit());
            changeStatus.setText("booking Confirmed");
            confirmed.setText("confirmed");
            sessionController.reset();
            return confirm;
        }


    }
}
