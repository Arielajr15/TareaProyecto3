package org.example.tareaproyecto3.logic.auth;

import org.example.tareaproyecto3.logic.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfiguration {

    private final UserRepository userRepository;

    public ApplicationConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {

            @Override
            public String encode(CharSequence rawPassword) {
                return getMD5(rawPassword.toString());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return getMD5(rawPassword.toString()).equals(encodedPassword);
            }

            private String getMD5(String input) {
                try {
                    java.security.MessageDigest md =
                            java.security.MessageDigest.getInstance("MD5");

                    byte[] messageDigest = md.digest(input.getBytes());

                    StringBuilder hexString = new StringBuilder();

                    for (byte b : messageDigest) {
                        String hex = Integer.toHexString(0xff & b);

                        if (hex.length() == 1) {
                            hexString.append('0');
                        }

                        hexString.append(hex);
                    }

                    return hexString.toString();

                } catch (Exception e) {
                    throw new RuntimeException("Error al encriptar con MD5", e);
                }
            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean

    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
                                                         PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }
}