package main.model;

import main.SQLConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class RegisterModel {

    private Connection connection;

    public RegisterModel() {

        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }
    public boolean setNewPassword(String username, String password) {
        String sql = "UPDATE Employee SET password = ?"
                + "WHERE username = ?";
        try (Connection conn = connection;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // set the corresponding param
            pstmt.setString(1, password);
            pstmt.setString(2, username);
            // update
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }


    }

    public int idCount() throws SQLException {
        int count =0;
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "select * from Employee";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while( resultSet.next()) {
                count = resultSet.getInt("id");
            }
        } catch (Exception e) {

            System.out.println(e);

        } finally {
            preparedStatement.close();
            resultSet.close();
            connection.close();
        }
        System.out.println(count);
        return count;
    }

    public void RegisterUser(String firstName, String last, int age, String username, String password,
                             String question, String answer) throws SQLException {

        String sql = "INSERT INTO employee(id,name,lastName,age,username,password,question,answer) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection conn = connection;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCount()+1);
            pstmt.setString(2, firstName);
            pstmt.setString(3, last);
            pstmt.setInt(4, age);
            pstmt.setString(5, username);
            pstmt.setString(6, password);
            pstmt.setString(7, question);
            pstmt.setString(8, answer);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Map<String, String> getQuestion(String user) {
        String q = null;
        String a = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from employee where username = ?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                q= resultSet.getString(7);
                a= resultSet.getString(8);
            }
        } catch (Exception e) { }
        Map<String, String> question = new HashMap<>();
        question.put("question", q);
        question.put("answer", a);

        return question;
    }



}


