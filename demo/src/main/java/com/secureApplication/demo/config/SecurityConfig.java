package com.secureApplication.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //FIXME: broken method
        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF is disabled, insecure
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // all requests are open
                );

        return http.build();
    }
}
