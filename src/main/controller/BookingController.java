package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
    private Text bookingDate, bookingTableNumber, bookingTitle;


    //shared variables across both pages
    public SceneController SceneController;

    public BookingModel bookingModel = new BookingModel();

    private UserController userController = Main.getUserController();


    //our methods
    //initilize our bookings on the Manage Booking Page
    public void initialize() {

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
       // tab1.setBackground(new Background(new BackgroundFill(Color.BLUE, new CornerRadii(0), Insets.EMPTY)));
        boolean otherUserBooking;
        boolean userBookingExist;
        boolean bookingSuccess;
        userBookingExist = bookingModel.doesUserBookingExist(javaDate,userController.getUsername());
        if (!userBookingExist){
            otherUserBooking = bookingModel.doesOtherUserBookingExist(javaDate,tableNumber);
            if(!otherUserBooking){
                bookingSuccess= bookingModel.addBooking(javaDate, tableNumber, userController.getUsername());

                if(bookingSuccess) {
                    BookingStatus.setText("successful booking");
                    this.showBookedTables(javaDate);
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

    public void editBooking(ActionEvent event) {
    }

    public void cancelBooking(ActionEvent event) {
    }
}
