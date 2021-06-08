package main.model;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RegisterModelTest {
    private String user= "test";
    private RegisterModel registerModel = new RegisterModel();
    @Test
    void idCount() throws SQLException {
        int count = registerModel.idCount();
        assertNotEquals(0, count);
    }
    @Test
    void getQuestion() {
        Map<String, String> question = new HashMap<>();
        question.put("question", "name of my cat?");
        question.put("answer", "cat");
        Map<String, String> question2= registerModel.getQuestion(user);
        assertEquals(question, question2);
    }
}