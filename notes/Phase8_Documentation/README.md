# Phase 8: API Documentation (OpenAPI 3 & Swagger UI) 📑

> [!NOTE]
> [⬅️ Back to Notes Master Index](../README.md)

Welcome to **Phase 8: API Documentation**! This phase introduces automated, self-updating API documentation, interactive browser testing via Swagger UI, and the browser security rules (CORS) that govern frontend-to-backend communication.

---

## 🗺️ Phase 8 Guidebook

| Document | Key Concepts |
|---|---|
| **[01_OpenAPI_and_Swagger_Deep_Dive.md](01_OpenAPI_and_Swagger_Deep_Dive.md)** | Documentation rot, OpenAPI 3 vs Swagger UI, SpringDoc starter, `OpenApiConfig`, JWT Authorize padlock button, `@Tag`, `@Operation`, `@ApiResponse`, and `@Schema`. |
| **[02_CORS_and_Frontend_Integration.md](02_CORS_and_Frontend_Integration.md)** | Why frontends need backend permission, Same-Origin Policy (SOP), the "Evil Hacker Tab" scenario, Preflight `OPTIONS` requests, and Spring Boot CORS configuration. |

---

## 🧠 Mental Model: The Interactive Restaurant Menu & Tasting Station

```text
┌────────────────────────────────────────────────────────┐
│  The Interactive Restaurant (Swagger UI)               │
│                                                        │
│  1. The Printed Recipe Standards: OpenAPI 3 JSON       │
│     - Found at: /v3/api-docs                           │
│     - Machine-readable blueprint of every endpoint.    │
│                                                        │
│  2. The Tasting Station / Buffet: Swagger UI           │
│     - Found at: /swagger-ui/index.html                 │
│     - Interactive dashboard in your browser.           │
│     - Click "Try it out" -> "Execute" without Postman. │
│                                                        │
│  3. The VIP Access Key: 🔒 Authorize Button            │
│     - Paste JWT token once -> All requests authenticated│
│                                                        │
│  4. The Auto-Scribe: SpringDoc OpenAPI                 │
│     - Reads Java annotations via reflection.           │
│     - Zero documentation rot!                          │
└────────────────────────────────────────────────────────┘
```

---

## 🛠️ Verification Endpoints

When your Spring Boot application is running (`./mvnw spring-boot:run`):

* **Interactive Swagger UI Dashboard:**  
  👉 `http://localhost:8080/swagger-ui/index.html`
* **Raw OpenAPI 3 JSON Specification:**  
  👉 `http://localhost:8080/v3/api-docs`
