# Backend API (Spring Boot)

Handles business logic, user authentication (JWT), product/wishlist management, analytics placeholders, and scheduled price tracking.

## Quickstart (Development)

1) Clone repository and open the backend_api directory:
- Dir: pricetrack-south-africa-192459-192468/backend_api

2) Create .env from example:
- cp .env.example .env
- Ensure FRONTEND_ORIGIN=http://localhost:3000 for React dev server
- SPRING_PROFILES_ACTIVE defaults to dev (H2 in-memory DB)

3) Run the API:
- ./gradlew bootRun
- The app starts on http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/api-docs

4) Verify health:
- curl http://localhost:8080/health -> OK

## Environment Variables

See .env.example:
- SPRING_PROFILES_ACTIVE=dev
- FRONTEND_ORIGIN=http://localhost:3000
- JWT_SECRET=... (raw or base64)
- JWT_EXP_MIN=60
- PRICES_CRON=0 0 * * * *
- SCRAPER_RETAILER_A_URL, SCRAPER_RETAILER_B_URL
- MAIL_HOST, MAIL_API_KEY, MAIL_FROM

Prod DB (when SPRING_PROFILES_ACTIVE=prod):
- SPRING_DATASOURCE_URL=jdbc:postgresql://host:5432/db
- SPRING_DATASOURCE_USERNAME=...
- SPRING_DATASOURCE_PASSWORD=...

## CORS

CORS is configured to allow credentials and standard methods/headers. Set FRONTEND_ORIGIN to your frontend URL (e.g., http://localhost:3000).

## Auth Model (demo)

- Signup: POST /auth/signup { email, displayName? } -> 201 { accessToken }
- Login: POST /auth/login { email } -> 200 { accessToken }
- Refresh: POST /auth/refresh with Authorization: Bearer <token> -> 200 { accessToken }

Use the accessToken with Authorization: Bearer <token> for protected endpoints.

## End-to-End (E2E) Test Flow with Frontend

Prereqs:
- Backend: ./gradlew bootRun (on port 8080)
- Frontend: npm start / yarn dev (on port 3000) with REACT_APP_API_BASE_URL=http://localhost:8080

Steps:
1) Signup:
   curl -X POST http://localhost:8080/auth/signup -H "Content-Type: application/json" -d '{"email":"user@example.com","displayName":"Lebo"}'
   -> Save accessToken.

2) Login (optional after signup):
   curl -X POST http://localhost:8080/auth/login -H "Content-Type: application/json" -d '{"email":"user@example.com"}'
   -> Save accessToken.

3) Protected route (current user):
   curl http://localhost:8080/users/me -H "Authorization: Bearer <token>"

4) Wishlist APIs:
   - List: GET /wishlists (requires Authorization header)
   - Create: POST /wishlists {"name":"My Wishlist"}
   - Add Item: POST /wishlists/{id}/items {"productId":1}
   Note: In dev DB, seed products if needed via Swagger UI or direct DB insert not included by default.

## Notes

- Dev profile uses H2 and creates tables automatically. Data resets on restart.
- Swagger UI available at /swagger-ui.html for interactive exploration.
- Schedulers run hourly by default; can be disabled by removing @EnableScheduling (not necessary for dev).
