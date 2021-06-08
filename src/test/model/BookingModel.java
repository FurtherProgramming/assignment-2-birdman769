package test.model;

import javafx.util.Pair;
import main.SQLConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class BookingModel {


    private Connection connection;

    public BookingModel() {

        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);

    }

    //gets bookingId of last entry in database for bookings
    public int getBookingCount() throws SQLException {
        int id = 0;
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String query = "select * from Bookings";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt("bookingId");

            }
            preparedStatement.close();
            resultSet.close();
            connection.close();

        } catch (Exception e) {

        }
        return id;
    }

    public boolean doesUserBookingExist(LocalDate date, String user) throws SQLException {
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String query = "select * from Bookings where username = ? and bookingDate= ?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, String.valueOf(date));

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                preparedStatement.close();
                resultSet.close();
                connection.close();

                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }

    public boolean doesOtherUserBookingExist(LocalDate date, int table) throws SQLException {
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from Bookings where tableNumber = ? and bookingDate= ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, table);
            preparedStatement.setString(2, String.valueOf(date));

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        } finally {
            preparedStatement.close();
            resultSet.close();
            connection.close();
        }

    }

    public boolean addBooking(LocalDate date, int table, String user) {
        connection = SQLConnection.connect();
        try { // the mysql insert statement
            String query = " insert into Bookings (username, bookingId, tableNumber, bookingDate)"
                    + " values (?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, user);
            preparedStmt.setInt(2, this.getBookingCount() + 1);
            preparedStmt.setInt(3, table);
            preparedStmt.setString(4, String.valueOf(date));
            // execute the preparedstatement
            preparedStmt.execute();

            connection.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;

        }
        return true;
    }
    private ArrayList<Integer> getTables(LocalDate javaDate, ArrayList<Integer> tablesBooked, PreparedStatement preparedStatement, ResultSet resultSet, String query) throws SQLException {
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(javaDate));
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                tablesBooked.add(resultSet.getInt("tableNumber"));
            }
        } catch (Exception e) {
            System.out.println(e);

        } finally {
            preparedStatement.close();
            resultSet.close();
            connection.close();
        }
        return tablesBooked;
    }

    public ArrayList<Integer> getBookedTables(LocalDate javaDate) throws SQLException {
        ArrayList<Integer> tablesBooked = new ArrayList<Integer>();
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from Bookings where bookingDate= ?";
        return getTables(javaDate, tablesBooked, preparedStatement, resultSet, query);
    }
    public ArrayList<Integer> getCovidTables(LocalDate javaDate) throws SQLException {
        ArrayList<Integer> tablesLocked = new ArrayList<Integer>();
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from Covid where dateLock= ?";
        return getTables(javaDate, tablesLocked, preparedStatement, resultSet, query);
    }

    public Pair<Integer, String> getNextBooking(String user, int bookingTarget) throws SQLException {
        int control =0;
        Pair<Integer, String> pair = new Pair<>(1, "One");
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from Bookings where username=?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            resultSet = preparedStatement.executeQuery();
            while(control < bookingTarget) {
              pair = new Pair<>((resultSet.getInt("tableNumber")),resultSet.getString("bookingDate"));
              resultSet.next();
              control ++;
            }
        } catch (Exception e) {
            System.out.println(e);

        } finally {

            preparedStatement.close();
            resultSet.close();
            connection.close();
        }
        return pair;
    }
    public int getUserTotalBookings(String user) throws SQLException {
        int count =0;
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from Bookings where username=?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            resultSet = preparedStatement.executeQuery();
            while( resultSet.next()) {
                count++;
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            preparedStatement.close();
            resultSet.close();
            connection.close();
        }
        return count;
    }
    public boolean removeBooking(String date,int table){
        String sql = "DELETE FROM Bookings WHERE tableNumber = ? and bookingDate =?";
        connection = SQLConnection.connect();
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, table);
            pstmt.setString(2, date);
            // execute the delete statement
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    public boolean addCovidLock(LocalDate javaDate, int tableNumber) {

        connection = SQLConnection.connect();
        try { // the mysql insert statement
            String query = " insert into Covid (tableNumber, dateLock)"
                    + " values (?, ?)";
            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, tableNumber);
            preparedStmt.setString(2, String.valueOf(javaDate));
            // execute the preparedstatement
            preparedStmt.execute();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
    public boolean removeCovidLock(LocalDate javaDate, int tableNumber){
    String sql = "DELETE FROM Covid WHERE tableNumber = ? and dateLock =?";
    connection = SQLConnection.connect();

        try {
        PreparedStatement pstmt = connection.prepareStatement(sql);

        pstmt.setInt(1, tableNumber);
        pstmt.setString(2, String.valueOf(javaDate));
        // execute the delete statement
        pstmt.executeUpdate();
    } catch (SQLException e) {
        System.out.println(e.getMessage());
        return false;
    }
        return true;
    }

    public boolean isDateCovidLocked(LocalDate javaDate, int tableNumber) throws SQLException {
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from Covid where dateLock = ? and tableNumber= ?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(javaDate));
            preparedStatement.setInt(2, tableNumber);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                return true;
            }
            else{
                return false;
            }
        }
        catch (Exception e)
        {
            return false;
        }finally {
            preparedStatement.close();
            resultSet.close();
        }

    }

    public boolean isBookingConfirmed(int tableNumber, String javaDate) throws SQLException {
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from Bookings where bookingDate = ? and tableNumber= ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, javaDate);
            preparedStatement.setInt(2, tableNumber);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if(resultSet.getInt("confirmed")== 1)
                    return true;
            }
            else{
                return false;
            }
        }
        catch (Exception e)
        {
            return false;
        }finally {
            preparedStatement.close();
            resultSet.close();
        }
        return false;

    }

    public String getBookingUsername(int table, String date) throws SQLException {
        String username = null;
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from Bookings where bookingDate = ? and tableNumber= ?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, date);
            preparedStatement.setInt(2, table);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
               username=  resultSet.getString("username");



            }

        }
        catch (Exception e)
        {System.out.print(e.getMessage());
        }finally {
            preparedStatement.close();
            resultSet.close();
        }


        return username;
    }

    public boolean confirmBooking(int tableEdit, String dateEdit) {
        String sql = "UPDATE Bookings SET confirmed = ? "
                + "WHERE tableNumber = ? and bookingDate = ?";
        connection = SQLConnection.connect();
        try {
            connection = SQLConnection.connect();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            // set the corresponding param
            pstmt.setInt(1, 1);
            pstmt.setInt(2, tableEdit);
            pstmt.setString(3, dateEdit);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public HashMap<String, Integer> getWhiteList(LocalDate javaDate) throws SQLException {
        HashMap<String, Integer> whitelist = new HashMap<String, Integer>();
        String username;
        int table;
        javaDate = javaDate.minusDays(1);
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from Bookings where bookingDate = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(javaDate));
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                username=  resultSet.getString("username");
                table=  resultSet.getInt("tableNumber");
                whitelist.put(username,table);
            }
        }
        catch (Exception e)
        {System.out.print(e.getMessage());
        }finally {
            preparedStatement.close();
            resultSet.close();
        }
    return whitelist;
    }

    public boolean checkIfSatYesterday(String username, int tableNumber, LocalDate javaDate) throws SQLException {
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        javaDate = javaDate.minusDays(1);
        String query = "select * from Bookings where bookingDate = ? and tableNumber= ? and username=?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(javaDate));
            preparedStatement.setInt(2, tableNumber);
            preparedStatement.setString(3, username);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }

        }
        catch (Exception e)
        {System.out.print(e.getMessage());
        }finally {
            preparedStatement.close();
            resultSet.close();
        }
        return false;
    }

    public boolean insertWhitelistException(LocalDate javaDate, int tableNumber) {

        connection = SQLConnection.connect();
        try { // the mysql insert statement
            String query = " insert into Whitelist (whitelistDate, tableNumber)"
                    + " values (?, ?)";
            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, String.valueOf(javaDate));
            preparedStmt.setInt(2, tableNumber);
            // execute the preparedstatement
            preparedStmt.execute();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean checkIfExceptionExists(int tableNumber, LocalDate javaDate) throws SQLException {
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        javaDate = javaDate.minusDays(1);
        String query = "select * from Whitelist where whitelistDate = ? and tableNumber= ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(javaDate));
            preparedStatement.setInt(2, tableNumber);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }

        }
        catch (Exception e)
        {System.out.print(e.getMessage());
        }finally {
            preparedStatement.close();
            resultSet.close();
        }
        return false;

    }

    public void deleteAllBookings(String user) {

        String sql = "DELETE FROM Bookings WHERE username = ? ";
        connection = SQLConnection.connect();
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, String.valueOf(user));
            // execute the delete statement
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}


