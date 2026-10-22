# DevOpsHub - Spring Boot Learning Context

## LEARNING GOAL
"Become capable of independently designing, developing, debugging, testing, containerizing and deploying Spring Boot applications."

## CURRENT STATUS
* **CURRENT PHASE:** 7
* **CURRENT MODULE:** Automated Testing
* **CURRENT LESSON:** Phase 7.4: `@SpringBootTest` End-to-End Integration Testing (COMPLETED)
* **CURRENT CONCEPT:** End-to-End Testing with real PostgreSQL in Docker, zero mocks, `@SpringBootTest`, `@AutoConfigureMockMvc`, `@BeforeEach` cleanup, UTC static timezone handling
* **STATUS:** Completed (15/15 tests passing across Service, Controller, Security, and E2E Integration)
* **LAST COMPLETED CONCEPT:** Phase 7.4: Full End-to-End Integration Testing with `@SpringBootTest` (2/2 passing)
* **NEXT CONCEPT:** Phase 8: API Documentation (OpenAPI / Swagger UI)
* **CURRENT PROJECT MILESTONE:** v0.7 - Complete Automated Test Suite (15/15 Tests Passing)
* **CURRENT IMPLEMENTATION TASK:** Verified `ProjectIntegrationTest.java` (2/2 passing), `ProjectSecurityTest.java` (3/3 passing), `ProjectControllerTest.java` (6/6 passing), `ProjectServiceTest.java` (4/4 passing) -> 15/15 passing tests!

## SESSION HANDOFF
* **What I learned recently:**
  - **Spring Security & JWT:** Why HTTP is stateless. The 3-Bucket Mental Model (User ➔ Token ➔ Rules). How `JwtAuthenticationFilter` intercepts requests and sets authentication on `SecurityContextHolder`. Role-Based Access Control (RBAC) with `hasRole()` and `hasAnyRole()`.
  - **Automated Testing:** The Testing Pyramid (Unit ➔ Web/Integration ➔ End-to-End). Why we mock dependencies using Mockito (The Movie Stunt Double). The 3 A's pattern (Arrange - Act - Assert). How to test happy paths and sad paths (`assertThrows`).
  - **Controller Testing Mental Models:** The 3 Characters (Waiter under test, Fake Postman `MockMvc`, Stunt Chef `@MockitoBean`). The 4 Import Buckets (Postman Buttons `get/post/delete`, Inspectors `status/jsonPath/content`, Stunt Chef `when/doNothing`, Translator `ObjectMapper`). Why static imports make tests read like English. Why Spring Security requires dummy mocks (`JwtAuthenticationFilter`, `JwtUtil`, `CustomUserDetailsService`) in `@WebMvcTest`.
  - **Security Testing with MockMvc:** Why MockMvc enforces CSRF on mutating requests (`.with(csrf())`). Why stateless apps reset thread-locals and need `.with(user("admin").roles("ADMIN"))` bound to the request. Why unauthenticated requests default to `403 Forbidden`.
  - **End-to-End Integration Testing (`@SpringBootTest`):** Zero mocks. Full ApplicationContext boot connecting to real PostgreSQL in Docker. Why JUnit never calls `main(String[] args)` and why UTC timezone must be set via `static { ... }` initializer block to prevent Windows `Asia/Calcutta` connection drops.
* **What I implemented:**
  - `ProjectServiceTest.java`: 4 unit tests covering `getAllProjectS()`, `getProjectById()`, exception handling, and `addProject()`.
  - `ProjectControllerTest.java`: 6 controller slice tests with `MockMvc` covering GET, POST, DELETE, 404 handling, and Bean Validation 400.
  - `ProjectSecurityTest.java`: 3 security integration tests verifying anonymous 403, USER role 403, and ADMIN role 200.
  - `ProjectIntegrationTest.java`: 2 end-to-end integration tests verifying complete round-trip create & read and delete against live PostgreSQL in Docker.
  - `notes/Phase7_Testing/`: Comprehensive testing guides for all 4 testing levels.
  - Dedicated `README.md` portals for all phases (Phases 1 through 7) and `notes/Errors_and_Fixes/README.md`.
* **Problems encountered:**
  - `MockBean` cannot be resolved in Spring Boot 4.
  - `@WebMvcTest` package location changed in Spring Boot 4 modularized starters.
  - `Unable to determine Dialect without JDBC metadata` during `@SpringBootTest` caused by Docker offline and Windows `Asia/Calcutta` timezone mismatch in test runner forks.
* **Problems solved:**
  - In Spring Boot 4, legacy `@MockBean` was removed and replaced by `@MockitoBean`.
  - `@WebMvcTest` and `@AutoConfigureMockMvc` are located in `org.springframework.boot.webmvc.test.autoconfigure.*`.
  - Enforced UTC timezone globally via `static { TimeZone.setDefault(TimeZone.getTimeZone("UTC")); }` in `BackendApplication` and test classes, and configured explicit dialect in `application.yaml`.
* **Current Git branch:** main
* **Current project state:** Fully tested Spring Boot backend with 15/15 passing tests across all layers!

## LEARNING PROGRESS
* [x] Phase 0: Prerequisites (Java check, OOP, Maven)
* [x] Phase 1: Spring Fundamentals (IoC, DI, Beans, `@Component`, `@Configuration`)
* [x] Phase 2: REST API Development (Controllers, DTOs, CRUD, `@PathVariable`, `@RequestBody`)
* [x] Phase 3: Database & JPA (PostgreSQL, Docker, `@Entity`, `@Id`, Spring Data Repositories)
* [x] Phase 4: Proper Application Architecture (5-Layer Pattern: Controller, Service, Repo, Entity, DTO)
* [x] Phase 5: Validation & Error Handling (`@Valid`, `@NotBlank`, `@RestControllerAdvice`, Custom 404)
* [x] Phase 6: Spring Security (BCrypt, Custom UserDetailsService, JWT Stateless Filter, Registration, RBAC)
* [x] Phase 7: Testing (COMPLETED - 15/15 tests passing across all 4 testing levels!)
* [ ] Phase 8: API Documentation (OpenAPI / Swagger)
* [ ] Phase 9: Production Features (Actuator, Logging, Profiles)
* [ ] Phase 10: Advanced Spring Boot (Caching, Async, Scheduling)
* [ ] Phase 11: Microservices (Optional)
* [ ] Phase 12: Messaging & Event-Driven Architecture (Kafka / RabbitMQ)
* [ ] Phase 13: Docker (Multi-stage Dockerfile, Docker Compose)
* [ ] Phase 14: CI/CD (GitHub Actions Pipeline)
* [ ] Phase 15: Cloud Deployment (AWS)

## WHAT I CURRENTLY KNOW
* **Spring Boot Core:** Beans, Dependency Injection, Inversion of Control, Maven configuration.
* **REST APIs:** `@RestController`, `@GetMapping`, `@PostMapping`, `@DeleteMapping`, DTOs, HTTP verbs and status codes.
* **Databases & JPA:** Docker PostgreSQL containerization, Spring Data JPA, Hibernate Entities (`@Entity`, `@Table`, `@Id`, `@GeneratedValue`), Custom repository queries (`findByUsername`).
* **Clean Architecture:** 5-layer monolithic pattern separating Presentation, Business, and Persistence layers.
* **Validation & Errors:** Jakarta Bean Validation (`@Valid`, `@NotBlank`), `@RestControllerAdvice`, centralized customer service desk pattern.
* **Security & Auth:** Password hashing with BCrypt, `UserDetailsService`, JWT token lifecycle (builder/parser), stateless filter chains (`OncePerRequestFilter`), `SecurityContextHolder`, RBAC.
* **Testing:** JUnit 5 (`@Test`, `@ExtendWith`), Mockito (`@Mock`, `@InjectMocks`, `when().thenReturn()`, `verify()`), AssertJ (`assertThat()`), Arrange-Act-Assert.

## MY WEAK AREAS & REMINDERS
* **Don't memorize class names or boilerplate:** Rely on the 3-Bucket Mental Model and IDE autocomplete.
* **HTTP is stateless:** Remember that servers have complete amnesia between requests without tokens or sessions.
* **Single Source of Truth:** Use `notes/ANNOTATIONS.md` for all annotation lookups.

## PROJECT ARCHITECTURE
```text
Client (Postman / Browser)
         │
         ▼
[JwtAuthenticationFilter] ──► Checks "Authorization: Bearer <token>"
         │
         ▼
[SecurityConfig] ──────────► Enforces RBAC & Public vs Private routes
         │
         ▼
[Controllers] ─────────────► ProjectController / AuthController (The Waiters)
         │
         ▼
[Services] ────────────────► ProjectService / CustomUserDetailsService (The Chefs)
         │
         ▼
[Repositories] ────────────► ProjectRepository / UserRepository (The Pantry)
         │
         ▼
[PostgreSQL Database] ─────► Docker container `devopshub-db`
```
