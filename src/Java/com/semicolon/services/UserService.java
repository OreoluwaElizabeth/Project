package com.semicolon.services;

import com.semicolon.DTO.request.*;
import com.semicolon.DTO.response.*;

public interface UserService {
    UserResponse signUp(UserRequest userRequest);
    LoginResponse login(LoginRequest loginRequest);
    LogoutResponse logout(LogoutRequest logoutRequest);
    UpdateResponse updateProfile(UpdateRequest updateRequest);
    ViewProfileResponse viewProfile(ViewProfileRequest viewProfileRequest);
    DeleteResponse deleteAccount(AccountDeleteRequest AccountDeleteRequest);
}
