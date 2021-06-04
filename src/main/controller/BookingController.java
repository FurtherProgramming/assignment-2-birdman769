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

public class BookingController {

    //FXML and var for booking page
    @FXML
    private DatePicker date;
    @FXML
    private Text mainTitle, BookingStatus;

    @FXML
    private Button tab1,tab2,tab3,tab4,tab5,tab6,tab7,tab8,tab9;

    private int tableNumber;

    private LocalDate javaDate;

    //fxml and variables for manageBooking page

    @FXML
    private Text bookingDate, bookingTableNumber,changeStatus;

    private int bookingTarget = 1;
    private Pair<Integer, String> pair= null;


    //shared variables across both pages
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


    }
    public void getNextBooking() throws SQLException {
        int totalBookings= bookingModel.getUserTotalBookings(sessionController.getUsername());
        if(bookingTarget >= totalBookings+2)
            bookingTarget= 2;
        pair = bookingModel.getNextBooking(sessionController.getUsername(), bookingTarget);
        bookingTarget++;
        bookingTableNumber.setText(String.valueOf(pair.getKey()));
        bookingDate.setText(pair.getValue());
        changeStatus.setText("");
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
    }

    public void submitBooking(ActionEvent event) throws IOException, SQLException {
        boolean otherUserBooking;
        boolean userBookingExist;
        boolean bookingSuccess;
        userBookingExist = bookingModel.doesUserBookingExist(javaDate, sessionController.getUsername());
        if (!userBookingExist){
            otherUserBooking = bookingModel.doesOtherUserBookingExist(javaDate,tableNumber);
            if(!otherUserBooking){
                bookingSuccess= bookingModel.addBooking(javaDate, tableNumber, sessionController.getUsername());

                if(bookingSuccess) {
                    BookingStatus.setText("successful booking");
                    this.showBookedTables(javaDate);
                    //if user is updating rather than making a new booking:
                    if(sessionController.getTableEdit() != 0 && !sessionController.getDateEdit().equals(null)){
                        bookingModel.removeBooking(sessionController.getDateEdit(),sessionController.getTableEdit());
                    }
                }
                else
                    BookingStatus.setText("booking failed");

            }else{
                BookingStatus.setText("another user has booked this");
            }

        }else{
            BookingStatus.setText("you already have a booking");

        }

    }


    public void back(ActionEvent event) throws IOException {
        SceneController = new SceneController();
        SceneController.switchToMenuLandingPage((event));

    }
    public LocalDate getDate(){
        return this.javaDate;
    }
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

    public void editBooking(ActionEvent event) throws IOException {
        sessionController.setDateEdit(bookingDate.getText());
        sessionController.setTableEdit(Integer.parseInt(bookingTableNumber.getText()));
        SceneController = new SceneController();
        SceneController.switchToBookingPage((event));

    }
    public void cancelBooking(ActionEvent event) throws SQLException {
        boolean success;
       success= bookingModel.removeBooking(bookingDate.getText(),Integer.parseInt(bookingTableNumber.getText()));
       this.getNextBooking();
       changeStatus.setText("booking deleted!");

    }
}
