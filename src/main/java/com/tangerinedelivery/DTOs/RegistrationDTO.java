package com.tangerinedelivery.DTOs;

import lombok.Data;

@Data
public class RegistrationDTO {
    private String email;
    private String firstName;
    private String MiddleName;
    private String LastName;
    private String password;
}