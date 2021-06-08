package test.model;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UserModelTest {
    private UserModel userModel = new UserModel();
    private  String user = "test";

    @Test
    void getUserDetails() throws SQLException {
        Map<String, String> userDetails = new HashMap<>();
        Map<String, String> testUserDetails;
        userDetails.put("firstName","Homy");
        userDetails.put("lastName","Ash");
        userDetails.put("age", String.valueOf(23));
        testUserDetails = userModel.getUserDetails(user);
        assertEquals(testUserDetails,userDetails);
    }

    @Test
    void isUserAdmin() throws SQLException {
       boolean admin = userModel.isUserAdmin(user);
       assertFalse(admin);

    }

    @Test
    void userExists() throws SQLException {
        boolean exists = userModel.userExists(user);
        assertTrue(exists);
    }
}