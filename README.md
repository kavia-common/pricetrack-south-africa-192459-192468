# pricetrack-south-africa-192459-192468

This workspace contains:
- backend_api (Spring Boot) on port 8080
- web_mobile_frontend (React) on port 3000

## Quickstart (Local Dev)

1) Backend
- cd backend_api
- cp .env.example .env
- Ensure FRONTEND_ORIGIN=http://localhost:3000
- ./gradlew bootRun

2) Frontend
- cd ../web_mobile_frontend
- cp .env.example .env.local
- Ensure REACT_APP_API_BASE_URL=http://localhost:8080
- npm install && npm start

3) Test E2E
- Open http://localhost:3000/dev-e2e
- Signup -> Login -> Get Me -> Fetch Wishlists

For more details see backend_api/README.md and web_mobile_frontend/README.md.