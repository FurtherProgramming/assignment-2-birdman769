package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserModel {

    Connection connection;



    public Map<String,String> getUserDetails(String user) throws SQLException {
        Map<String, String> userDetails = new HashMap<>();
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from Employee where username= ?";
        try {
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, user);

            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
               userDetails.put("firstName",resultSet.getString("name"));
                userDetails.put("lastName",resultSet.getString("lastName"));
                userDetails.put("age", String.valueOf(resultSet.getInt("age")));

            }
        } catch (Exception e) {
            System.out.println(e);

        } finally {
            preparedStatement.close();
            resultSet.close();
            connection.close();
        }

        System.out.println(userDetails.get("firstName"));
        return userDetails;
    }

    public boolean updateUserDetails(String user, String name, String lastName, int age) {
        String sql = "UPDATE Employee SET name = ? , "
                + "lastName = ? ,"
                + "age = ? "
                + "WHERE username = ?";
        connection = SQLConnection.connect();

        try {
            connection = SQLConnection.connect();
            PreparedStatement pstmt = connection.prepareStatement(sql);

            // set the corresponding param
            pstmt.setString(1, name);
            pstmt.setString(2, lastName);
            pstmt.setInt(3, age);
            pstmt.setString(4, user);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }
}