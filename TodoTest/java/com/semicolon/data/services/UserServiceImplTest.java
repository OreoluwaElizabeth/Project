package com.semicolon.data.services;

import com.semicolon.DTO.request.*;
import com.semicolon.DTO.response.*;
import com.semicolon.data.models.User;
import com.semicolon.data.repositories.UserRepository;
import com.semicolon.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void signUp() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserName("Lizzy");
        userRequest.setEmail("womder@gmail.com");
        userRequest.setPassWord("Password");
        UserResponse userResponse = userService.signUp(userRequest);
        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getMessage()).isEqualTo("User created successfully");

    }

    @Test
    public void signUpWithInvalidPassword() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserName("Lizzy");
        userRequest.setEmail("Timothy@gmail.com");
        userRequest.setPassWord("short");
        UserResponse userResponse = userService.signUp(userRequest);
        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getMessage()).isEqualTo("Invalid Password, password must be at least 8 characters");
    }

    @Test
    public void signUpWithNullEmail() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserName("Lizzy");
        userRequest.setEmail(null);
        userRequest.setPassWord("Password");
        UserResponse userResponse = userService.signUp(userRequest);
        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getMessage()).isEqualTo("Invalid email address");
    }

    @Test
    public void testLogin() {
        UserRequest signUpRequest = new UserRequest();
        signUpRequest.setUserName("timothy");
        signUpRequest.setPassWord("12345678");
        signUpRequest.setEmail("@timothy@gmail.com");
        userService.signUp(signUpRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("timothy");
        loginRequest.setPassword("12345678");

        LoginResponse loginResponse = userService.login(loginRequest);
        assertThat(loginResponse).isNotNull();
        assertThat(loginResponse.getMessage()).isEqualTo("Logged in successfully");
    }

    @Test
    public void testLogout() {
        LogoutRequest logoutRequest = new LogoutRequest();
        LogoutResponse logoutResponse = userService.logout(logoutRequest);
        assertNotNull(logoutResponse);
        assertEquals("Logged out successfully", logoutResponse.getMessage());
    }

    @Test
    public void testUpdateProfile() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserName("Lizzy");
        userRequest.setEmail("lizzy@gmail.com");
        userRequest.setPassWord("semicolon");
        userService.signUp(userRequest);

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setUsername("Lizzy");
        updateRequest.setNewUsername("Alero");
        updateRequest.setPassword("newpassword");
        UpdateResponse updateResponse = userService.updateProfile(updateRequest);

        assertTrue(updateResponse.isSuccess());
        assertEquals("Profile updated successfully", updateResponse.getMessage());
    }

    @Test
    public void testUserNotFoundProfile() {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setUsername("David");
        updateRequest.setNewUsername("Samuel");
        updateRequest.setPassword("qwerty01");
        UpdateResponse updateResponse = userService.updateProfile(updateRequest);

        assertFalse(updateResponse.isSuccess());
        assertEquals("User not found", updateResponse.getMessage());
    }

    @Test
    public void testViewProfile() {
        User user = new User();
        user.setUsername("Lizabeth");
        user.setEmail("rosh@gmail.com");
        userRepository.save(user);

        ViewProfileRequest request = new ViewProfileRequest();
        request.setUsername("Lizabeth");

        ViewProfileResponse response = userService.viewProfile(request);
        assertEquals("Lizabeth", response.getUserName());
        assertEquals("rosh@gmail.com", response.getEmail());
        assertEquals("Profile viewed successfully", response.getMessage());
    }

    @Test
    public void testDeleteAccount() {
        User user = new User();
        user.setUsername("Elizabeth");
        user.setPassword("12345678");
        userRepository.save(user);

        AccountDeleteRequest request = new AccountDeleteRequest();
        request.setUserName("Elizabeth");
        request.setPassword("12345678");

        DeleteResponse response = userService.deleteAccount(request);
        assertEquals("Account deleted successfully", response.getMessage());
        assertTrue(response.getSuccess());

        List<User> deletedUsers = userRepository.findByUsername("Elizabeth");
        assertTrue(deletedUsers.isEmpty());
    }
}
