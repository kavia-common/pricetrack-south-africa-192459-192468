# Backend Profiles and Database

Profiles:
- dev (default): Uses H2 in-memory DB with create-drop DDL. Flyway disabled.
  Run: ./gradlew bootRun (SPRING_PROFILES_ACTIVE defaults to dev)
- prod: Uses PostgreSQL via env variables. Flyway enabled by default.
  Run: SPRING_PROFILES_ACTIVE=prod SPRING_DATASOURCE_URL=jdbc:postgresql://host:5432/db \ 
       SPRING_DATASOURCE_USERNAME=xxx SPRING_DATASOURCE_PASSWORD=yyy \
       ./gradlew bootRun

Migrations:
- Flyway migrations live in src/main/resources/db/migration
- Initial migration: V1__init_schema.sql

Environment:
- See .env.example for required variables in prod.
