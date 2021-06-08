package test.model;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BookingModelTest {
    String user = "test";

    private BookingModel bookingModel = new BookingModel();
    private JFXPanel panel = new JFXPanel();
    //test case booking
    private  LocalDate date= LocalDate.of(2000, 1, 1);


    @Test
    void getBookingCount() throws SQLException {
        int x= bookingModel.getBookingCount();
       assertNotSame(0, x);
    }

    @Test
    void doesUserBookingExist() throws SQLException {

        boolean exists = bookingModel.doesUserBookingExist(date, user);
        assertTrue(exists);
    }

    @Test
    void doesOtherUserBookingExist() throws SQLException {
        boolean exists = bookingModel.doesOtherUserBookingExist(date, 1);
        assertTrue(exists);
    }

    @Test
    void getBookedTables() throws SQLException {
        ArrayList<Integer> tablesBooked = bookingModel.getBookedTables(date);
        assertEquals(1,tablesBooked.size());
    }
    @Test
    void getCovidTables() throws SQLException {
        ArrayList<Integer> tablesBooked = bookingModel.getCovidTables(date);
        assertEquals(1,tablesBooked.size());
    }


    @Test
    void isDateCovidLocked() throws SQLException {
        boolean locked = bookingModel.isDateCovidLocked(date, 5);
        assertTrue(locked);
    }

    @Test
    void isBookingConfirmed() throws SQLException {
        boolean unconfirmed = bookingModel.isBookingConfirmed(1, date.toString());
        assertFalse(unconfirmed);
    }

    @Test
    void getBookingUsername() throws SQLException {
        String user = "test";
        String testUser = bookingModel.getBookingUsername(1, date.toString());
        assertEquals(user, testUser);
    }

//we have to add one day to the date for these tests, as our model subtracts a day in its methods to automatically check the
//previous date!
    @Test
    void checkIfSatYesterday() throws SQLException {
        boolean sat= bookingModel.checkIfSatYesterday(user, 1, date.plusDays(1));
        assertTrue(sat);
    }


    @Test
    void checkIfExceptionExists() throws SQLException {
        boolean exception = bookingModel.checkIfExceptionExists(1, date.plusDays(1));
        assertTrue(exception);

    }

}