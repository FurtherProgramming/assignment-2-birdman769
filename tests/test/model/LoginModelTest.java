package test.model;

import org.junit.jupiter.api.Test;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class LoginModelTest {

    private LoginModel loginModel = new LoginModel();

    @Test
    void isLogin() throws SQLException {
        boolean isLog = loginModel.isLogin("test", "test");
        assertTrue(isLog);
    }
    @Test
    void isLoginFalse() throws SQLException {
        boolean isLog = loginModel.isLogin("NotARealUser", "notauser");
        assertFalse(isLog);
    }
    //test user is not an admin, should return false
    @Test
    void isAdmin() {
        boolean isAdmin = loginModel.isAdmin("test");
        assertFalse(isAdmin);
    }
}