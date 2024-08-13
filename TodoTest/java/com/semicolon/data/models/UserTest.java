package com.semicolon.data.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {

    @Test
    public void testId() {
        User user = new User();
        user.setId("1");
        assertEquals("1", user.getId());
    }

    @Test
    public void testUserName() {
        User user = new User();
        user.setUsername("Elizabeth");
        assertEquals("Elizabeth", user.getUsername());
    }

    @Test
    public void testPassword() {
        User user = new User();
        user.setPassword("password");
        assertEquals("password", user.getPassword());
    }

    @Test
    public void testInvalidPassword() {
        User user = new User();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()-> {
            user.setPassword("07025");
        });
        assertEquals("Password must be at least 8 characters", exception.getMessage());

    }

    @Test
    public void testEmail() {
        User user = new User();
        user.setEmail("timothywonder01@gmail.com");
        assertEquals("timothywonder01@gmail.com", user.getEmail());
    }

    @Test
    public void testInvalidEmail() {
        User user = new User();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()-> {
            user.setEmail("07025286938");
        });
        assertEquals("Invalid email", exception.getMessage());
    }
}
