package com.semicolon.data.services;

import com.semicolon.data.DTO.request.*;
import com.semicolon.data.DTO.response.*;
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
        try {
            User user = userRepository.findByUsername(loginRequest.getUsername());
            if (user == null) {
                throw new Exception("Username not found");
            }
            if (!user.getPassword().equals(loginRequest.getPassword())) {
                return new LoginResponse("Incorrect password");
            }
            return new LoginResponse("Logged in successfully");
        } catch (Exception e) {
            return new LoginResponse("An error occured: " + e.getMessage());
        }

    }

    @Override
    public LogoutResponse logout(LogoutRequest logoutRequest) {
        return new LogoutResponse("Logged out successfully");
    }

    @Override
    public UpdateResponse updateProfile(UpdateRequest updateRequest) {
        User user = userRepository.findByUsername(updateRequest.getUsername());
        if (user == null) {
            return new UpdateResponse("", "User not found");
        }
        user.setUsername(updateRequest.getUsername());
        user.setPassword(updateRequest.getPassword());
        userRepository.save(user);
        return new UpdateResponse("", "Profile updated successfully");
    }

    @Override
    public ViewProfileResponse viewProfile(ViewProfileRequest viewProfileRequest) {
        User user = userRepository.findByUsername(viewProfileRequest.getUsername());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        ViewProfileResponse response = new ViewProfileResponse();
        response.setUserName(user.getUsername());
        response.setEmail(user.getEmail());
        response.setMessage("Profile viewed successfully");
        return response;
    }

    @Override
    public DeleteResponse deleteAccount(AccountDeleteRequest accountDeleteRequest) {
        User user = userRepository.findByUsername(accountDeleteRequest.getUserName());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if (!user.getPassword().equals(user.getPassword())) {
            return new DeleteResponse("Incorrect password", false);
        }
        userRepository.delete(user);
        DeleteResponse response = new DeleteResponse();
        response.setMessage("Account deleted successfully");
        response.setSuccess(true);
        return response;
    }
}
