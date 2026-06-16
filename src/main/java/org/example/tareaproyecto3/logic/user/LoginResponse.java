package org.example.tareaproyecto3.logic.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private String token;
    private Long expiresIn;
    private User authUser;
}