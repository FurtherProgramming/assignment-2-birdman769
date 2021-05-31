package main.model;

import main.SQLConnection;

import java.sql.*;

public class RegisterModel {

    Connection connection;

    public RegisterModel() {

        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);

    }

    public int idCount() throws SQLException {
        Statement s = connection.createStatement();
        ResultSet r = s.executeQuery("SELECT COUNT(*) AS rowcount FROM employee");
        r.next();
        int count = r.getInt("rowcount");
        r.close();
        return count;
    }

    public void RegisterUser(String firstName, String last, int age, String username, String password) throws SQLException {

        String sql = "INSERT INTO employee(id,name,lastName,age,username,password) VALUES(?,?,?,?,?,?)";
        try (Connection conn = connection;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCount()+1);
            pstmt.setString(2, firstName);
            pstmt.setString(3, last);
            pstmt.setInt(4, age);
            pstmt.setString(5, username);
            pstmt.setString(6, password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}


