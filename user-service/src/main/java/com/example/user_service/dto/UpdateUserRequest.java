package com.example.user_service.dto;

import com.example.user_service.entity.Role;
import lombok.Data;

@Data
public class UpdateUserRequest {
    private String username;
    private String email;
    private String password;
    private Role role;
}
