package com.semicolon.services;

import com.semicolon.DTO.request.*;
import com.semicolon.DTO.response.*;
import com.semicolon.data.models.User;
import com.semicolon.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse signUp(UserRequest userRequest) {
        if (userRequest.getPassWord() == null || userRequest.getPassWord().length() < 8) {
            return new UserResponse("Invalid Password, password must be at least 8 characters");
        }
        if (userRequest.getEmail() == null) {
            return new UserResponse("Invalid email address");
        }
        List<User> existingUsers = userRepository.findByEmail(userRequest.getEmail());
        if (!existingUsers.isEmpty()) {
            return new UserResponse("Email address already in use");
        }
        User user = new User();
        user.setUsername(userRequest.getUserName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassWord());
        userRepository.save(user);
        return new UserResponse("User created successfully");
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        List<User> users = userRepository.findByUsername(loginRequest.getUsername());
        if (users.isEmpty() || !users.get(0).getPassword().equals(loginRequest.getPassword())) {
            return new LoginResponse("Invalid username or password");
        }
        return new LoginResponse("Logged in successfully");
    }

    @Override
    public LogoutResponse logout(LogoutRequest logoutRequest) {
        return new LogoutResponse("Logged out successfully");
    }

    @Override
    public UpdateResponse updateProfile(UpdateRequest updateRequest) {
        List<User> users = userRepository.findByUsername(updateRequest.getUsername());
        if (users.isEmpty()) {
            return new UpdateResponse(false, "User not found");
        }
        User user = users.get(0);
        user.setUsername(updateRequest.getNewUsername());
        if (updateRequest.getPassword() != null) {
            user.setPassword(updateRequest.getPassword());
        }
        userRepository.save(user);
        return new UpdateResponse(true, "Profile updated successfully");
    }

    @Override
    public ViewProfileResponse viewProfile(ViewProfileRequest viewProfileRequest) {
        List<User> users = userRepository.findByUsername(viewProfileRequest.getUsername());
        if (users.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = users.get(0);
        ViewProfileResponse response = new ViewProfileResponse();
        response.setUserName(user.getUsername());
        response.setEmail(user.getEmail());
        response.setMessage("Profile viewed successfully");
        return response;
    }

    @Override
    public DeleteResponse deleteAccount(AccountDeleteRequest accountDeleteRequest) {
        List<User> users = userRepository.findByUsername(accountDeleteRequest.getUserName());
        if (users.isEmpty() || !users.get(0).getPassword().equals(accountDeleteRequest.getPassword())) {
            return new DeleteResponse("Invalid username or password", false);
        }
        User user = users.get(0);
        userRepository.delete(user);
        return new DeleteResponse("Account deleted successfully", true);
    }
}
