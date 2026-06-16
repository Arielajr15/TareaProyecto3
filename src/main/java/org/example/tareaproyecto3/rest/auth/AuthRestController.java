package org.example.tareaproyecto3.rest.auth;

import org.example.tareaproyecto3.logic.auth.AuthenticationService;
import org.example.tareaproyecto3.logic.auth.JwtService;
import org.example.tareaproyecto3.logic.user.LoginResponse;
import org.example.tareaproyecto3.logic.user.User;
import org.example.tareaproyecto3.logic.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/auth")
@RestController
public class AuthRestController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    public AuthRestController(
            JwtService jwtService,
            AuthenticationService authenticationService,
            UserRepository userRepository
    ) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody User user) {
        User authenticatedUser = authenticationService.authenticate(user);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        Optional<User> foundedUser = userRepository.findByEmail(user.getEmail());
        foundedUser.ifPresent(loginResponse::setAuthUser);

        return ResponseEntity.ok(loginResponse);
    }
}