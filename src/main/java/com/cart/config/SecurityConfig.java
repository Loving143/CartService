package com.cart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cart.helper.CustomAccessDeniedHandler;
import com.cart.helper.CustomAuthenticationEntryPoint;
import com.cart.helper.JwtAuthFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntrypoint;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, CustomAccessDeniedHandler accessDeniedHandler,
    		CustomAuthenticationEntryPoint authenticationEntrypoint) {
        this.jwtAuthFilter = jwtAuthFilter;
		this.accessDeniedHandler = accessDeniedHandler;
		this.authenticationEntrypoint = authenticationEntrypoint;
	}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception  {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/public/**").permitAll()
                    .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex
                    .accessDeniedHandler(accessDeniedHandler)  // For 403 - Authorization
                    .authenticationEntryPoint(authenticationEntrypoint))  // For 401 - Authentication
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        

        return http.build();
    }
}
