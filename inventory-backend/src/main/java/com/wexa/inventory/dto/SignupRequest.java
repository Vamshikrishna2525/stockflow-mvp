package com.wexa.inventory.dto;

import lombok.Data;

@Data
public class SignupRequest {

    private String email;

    private String password;

    private String organizationName;

}