package org.example.tareaproyecto3.logic.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse {

    private String token;

    private User authUser;

    private long expiresIn;

}