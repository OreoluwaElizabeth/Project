package com.semicolon.web;

import com.semicolon.DTO.request.*;
import com.semicolon.DTO.response.*;
import com.semicolon.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public UserResponse signUp(@RequestBody UserRequest userRequest) {
        return userService.signUp(userRequest);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @PostMapping("/logout")
    public LogoutResponse logout(@RequestBody LogoutRequest logoutRequest) {
        return userService.logout(logoutRequest);
    }

    @PostMapping("/update-profile")
    public UpdateResponse updateProfile(@RequestBody UpdateRequest updateRequest) {
        return userService.updateProfile(updateRequest);
    }

    @PostMapping("/view-profile")
    public ViewProfileResponse viewProfile(@RequestBody ViewProfileRequest viewProfileRequest) {
        return userService.viewProfile(viewProfileRequest);
    }

    @PostMapping("/delete-account")
    public DeleteResponse deleteAccount(@RequestBody AccountDeleteRequest accountDeleteRequest) {
        return userService.deleteAccount(accountDeleteRequest);
    }

}
