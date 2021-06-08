package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserModel {

    private Connection connection;

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

    public boolean isUserAdmin(String user) throws SQLException {
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int isAdmin;
        String query = "select * from Employee where username= ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                isAdmin= resultSet.getInt("isAdmin");
                if(isAdmin == 1)
                    return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            preparedStatement.close();
            resultSet.close();
            connection.close();
        }
        return false;
    }

    public boolean toggleAdmin(String user) {
        String sql = "UPDATE Employee SET isAdmin = ? "
                + "WHERE username = ?";
        connection = SQLConnection.connect();

        try {
            connection = SQLConnection.connect();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            // set the corresponding param
            if(isUserAdmin(user))
            pstmt.setInt(1, 0);
            if(!isUserAdmin(user))
                pstmt.setInt(1, 1);
            pstmt.setString(2, user);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean userExists(String user) throws SQLException {
        connection = SQLConnection.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from Employee where username=?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(user));
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

    public boolean deleteUser(String user) throws SQLException {
        boolean userExists = userExists(user);
        if(!userExists)
            return false;
        String sql = "DELETE FROM Employee WHERE username = ? ";
        connection = SQLConnection.connect();
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, String.valueOf(user));
            // execute the delete statement
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }


    }
}