package com.semicolon.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDeleteRequest {
    private String userName;
    private String password;
    private Boolean confirmDelete;
}
