package org.example.tareaproyecto3.logic.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            AuthenticationProvider authenticationProvider
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(authorize -> authorize

                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/producto/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/categoria/**").authenticated()

                        .requestMatchers(HttpMethod.POST, "/api/producto/**").hasRole("SUPER_ADMIN_ROLE")
                        .requestMatchers(HttpMethod.PUT, "/api/producto/**").hasRole("SUPER_ADMIN_ROLE")
                        .requestMatchers(HttpMethod.PATCH, "/api/producto/**").hasRole("SUPER_ADMIN_ROLE")
                        .requestMatchers(HttpMethod.DELETE, "/api/producto/**").hasRole("SUPER_ADMIN_ROLE")

                        .requestMatchers(HttpMethod.POST, "/api/categoria/**").hasRole("SUPER_ADMIN_ROLE")
                        .requestMatchers(HttpMethod.PUT, "/api/categoria/**").hasRole("SUPER_ADMIN_ROLE")
                        .requestMatchers(HttpMethod.PATCH, "/api/categoria/**").hasRole("SUPER_ADMIN_ROLE")
                        .requestMatchers(HttpMethod.DELETE, "/api/categoria/**").hasRole("SUPER_ADMIN_ROLE")

                        .anyRequest().authenticated()
                )

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authenticationProvider(authenticationProvider)

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}