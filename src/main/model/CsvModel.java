package main.model;

import javafx.util.Pair;
import main.SQLConnection;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class CsvModel {


    Connection connection;

    public CsvModel() {

        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);

    }

    public void generateCsvEmployees() {
        String csvFilePath = "Employee-export.csv";

        try  {
            connection = SQLConnection.connect();
            String sql = "SELECT * FROM Employee";

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));

            // write header line containing column names
            fileWriter.write("id, name, lastName, age, username, password, question, answer, isAdmin");

            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String lastName = result.getString("lastName");
                int age = result.getInt("age");
                String username = result.getString("username");
                String password = result.getString("password");
                String question = result.getString("question");
                String answer = result.getString("answer");
                int isAdmin = result.getInt("isAdmin");

                String line = String.format("%d,%s,%s, %d,%s,%s,%s,%s,%d",
                        id, name, lastName, age, username, password, question, answer, isAdmin);

                fileWriter.newLine();
                fileWriter.write(line);
                System.out.println(line);
            }

            statement.close();
            fileWriter.close();

        } catch (SQLException e) {
            System.out.println("Datababse error:");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File IO error:");
            e.printStackTrace();
        }

    }

    public void generateCsvBooking(LocalDate javaDate) {
        String csvFilePath = "Booking-export.csv";
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet result=null;

        try  {
            String query = "select * from Bookings where bookingDate=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(javaDate));
            result = preparedStatement.executeQuery();

            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));

            // write header line containing column names
            fileWriter.write("username, booking ID, tableNumber, Date, confirmed status");

            while (result.next()) {
                String username = result.getString("username");
                int id = result.getInt("bookingId");
                int table = result.getInt("tableNumber");
                String date = result.getString("bookingDate");
                int confirmed = result.getInt("confirmed");


                String line = String.format("%s,%d,%d, %s,%d",
                        username, id, table,date, confirmed);

                fileWriter.newLine();
                fileWriter.write(line);
                System.out.println(line);
            }

            preparedStatement.close();
            result.close();
            fileWriter.close();

        } catch (SQLException e) {
            System.out.println("Datababse error:");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File IO error:");
            e.printStackTrace();
        }

    }

}


