package com.example.backendapi.config;

import com.example.backendapi.security.JwtAuthFilter;
import com.example.backendapi.security.JwtService;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

/**
 * PUBLIC_INTERFACE
 * Security configuration:
 * - Configures stateless JWT auth via JwtAuthFilter
 * - Permits auth endpoints and OpenAPI/actuator basics
 * - Configures CORS using FRONTEND_ORIGIN
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${app.security.frontend-origin:*}")
    private String frontendOrigin;

    // PUBLIC_INTERFACE
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration cfg = new CorsConfiguration();
                // Support explicit env origin plus common local dev if wildcard used
                if ("*".equals(frontendOrigin)) {
                    cfg.setAllowedOrigins(List.of("http://localhost:3000", "http://127.0.0.1:3000"));
                } else {
                    cfg.setAllowedOrigins(List.of(frontendOrigin));
                }
                cfg.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
                cfg.setAllowedHeaders(List.of("*"));
                cfg.setAllowCredentials(true);
                return cfg;
            }))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/",
                        "/docs",
                        "/health",
                        "/api/info",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/api-docs/**",
                        "/actuator/health",
                        "/actuator/info",
                        "/auth/signup",
                        "/auth/login",
                        "/auth/refresh"
                ).permitAll()
                .requestMatchers(HttpMethod.GET, "/products/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // PUBLIC_INTERFACE
    @Bean
    public PasswordEncoder passwordEncoder() {
        // For demo purposes; replace with BCryptPasswordEncoder in production once password storage is added.
        return NoOpPasswordEncoder.getInstance();
    }



    // PUBLIC_INTERFACE
    @Bean
    public io.swagger.v3.oas.models.OpenAPI openAPI() {
        return new io.swagger.v3.oas.models.OpenAPI()
                .info(new Info().title("PriceTrack Backend API")
                        .version("0.1.0")
                        .description("REST API for PriceTrack: authentication, products, wishlist, analytics"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("Authorization")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        ));
    }

    // PUBLIC_INTERFACE
    @Bean
    public GroupedOpenApi apiGroup() {
        return GroupedOpenApi.builder()
                .group("pricetrack")
                .pathsToMatch("/**")
                .build();
    }
}
