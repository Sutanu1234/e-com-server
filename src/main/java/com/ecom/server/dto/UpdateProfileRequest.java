package com.ecom.server.dto;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String name;
    private String email;
    private String password; // optional, only update if provided
}