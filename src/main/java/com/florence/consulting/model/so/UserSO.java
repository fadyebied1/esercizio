package com.florence.consulting.model.so;

import jakarta.validation.constraints.Email;
import lombok.Data;

public @Data class UserSO {
    private String idUser;
    private String firstName;
    private String lastName;
    private @Email String email;
    private String address;
}
