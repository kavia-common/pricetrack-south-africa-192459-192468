package com.example.backendapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * PUBLIC_INTERFACE
 * Application entrypoint for the backend API.
 * Uses Spring profiles to select environment-specific configuration:
 * - dev: H2 in-memory database with create-drop DDL
 * - prod: PostgreSQL via environment variables
 */
@SpringBootApplication
public class backendapiApplication {

    // PUBLIC_INTERFACE
    public static void main(String[] args) {
        SpringApplication.run(backendapiApplication.class, args);
    }
}
