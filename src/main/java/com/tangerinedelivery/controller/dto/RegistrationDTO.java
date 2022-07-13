<<<<<<<< HEAD:src/main/java/com/tangerinedelivery/controller/dto/RegistrationDTO.java
package com.tangerinedelivery.controller.dto;
========
package com.tangerinedelivery.controllers.dto;
>>>>>>>> origin/develop:src/main/java/com/tangerinedelivery/controllers/dto/RegistrationDTO.java

import lombok.Data;

@Data
public class RegistrationDTO {
    private String email;
    private String firstName;
    private String MiddleName;
    private String LastName;
    private String password;
}