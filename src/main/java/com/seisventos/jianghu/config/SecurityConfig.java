package com.seisventos.jianghu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

// configuracao de seguranca basica / basic security configuration
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // permite acesso a essas rotas sem login / allow access to these routes without login
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/home", "/h2-console/**").permitAll()
                .anyRequest().authenticated()
            )
            // desabilita csrf para desenvolvimento / disable csrf for development
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
                .disable()
            )
            // permite frames para h2 console / allow frames for h2 console
            .headers(headers -> headers.frameOptions().disable());
            
        return http.build();
    }
}