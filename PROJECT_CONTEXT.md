# DevOpsHub - Spring Boot Learning Context

## LEARNING GOAL
"Become capable of independently designing, developing, debugging, testing, containerizing and deploying Spring Boot applications."

## CURRENT STATUS
* **CURRENT PHASE:** 7
* **CURRENT MODULE:** Automated Testing
* **CURRENT LESSON:** Phase 7.2: Controller Testing with `@WebMvcTest` and `MockMvc` (COMPLETED)
* **CURRENT CONCEPT:** In-Memory HTTP Simulation, `@WebMvcTest`, `MockMvc`, and `jsonPath` assertions
* **STATUS:** Completed (6/6 tests passing)
* **LAST COMPLETED CONCEPT:** Phase 7.2: Controller Testing with `@WebMvcTest` & `MockMvc`
* **NEXT CONCEPT:** Phase 7.3: Testing Security with `@WithMockUser` or Phase 7.4: `@SpringBootTest` End-to-End
* **CURRENT PROJECT MILESTONE:** v0.7 - Automated Test Suite
* **CURRENT IMPLEMENTATION TASK:** Verified `ProjectControllerTest.java` (6/6 tests passing) and `ProjectServiceTest.java` (4/4 tests passing). Total 10 tests passing!

## SESSION HANDOFF
* **What I learned recently:**
  - **Spring Security & JWT:** Why HTTP is stateless. The 3-Bucket Mental Model (User ➔ Token ➔ Rules). How `JwtAuthenticationFilter` intercepts requests and sets authentication on `SecurityContextHolder`. Role-Based Access Control (RBAC) with `hasRole()` and `hasAnyRole()`.
  - **Automated Testing:** The Testing Pyramid (Unit ➔ Web/Integration ➔ End-to-End). Why we mock dependencies using Mockito (The Movie Stunt Double). The 3 A's pattern (Arrange - Act - Assert). How to test happy paths and sad paths (`assertThrows`).
  - **Controller Testing Mental Models:** The 3 Characters (Waiter under test, Fake Postman `MockMvc`, Stunt Chef `@MockitoBean`). The 4 Import Buckets (Postman Buttons `get/post/delete`, Inspectors `status/jsonPath/content`, Stunt Chef `when/doNothing`, Translator `ObjectMapper`). Why static imports make tests read like English. Why Spring Security requires dummy mocks (`JwtAuthenticationFilter`, `JwtUtil`, `CustomUserDetailsService`) in `@WebMvcTest`.
* **What I implemented:**
  - `ProjectServiceTest.java`: 4 unit tests covering `getAllProjectS()`, `getProjectById()`, exception handling, and `addProject()`.
  - `ProjectControllerTest.java`: 6 controller slice tests with `MockMvc` covering GET, POST, DELETE, 404 handling, and Bean Validation 400.
  - `notes/Phase7_Testing/02_Controller_Testing_with_MockMvc.md`: Comprehensive guide to controller slice testing.
  - Fully annotated `backend/pom.xml` with clear numbers and explanations.
  - Updated `notes/ANNOTATIONS.md` with collapsible dropdowns and Spring Boot 4 modern testing annotations (`@WebMvcTest`, `@AutoConfigureMockMvc`, `@MockitoBean`).
* **Problems encountered:**
  - `MockBean` cannot be resolved in Spring Boot 4.
  - `@WebMvcTest` package location changed in Spring Boot 4 modularized starters.
* **Problems solved:**
  - In Spring Boot 4, legacy `@MockBean` was removed and replaced by `@MockitoBean` (`org.springframework.test.context.bean.override.mockito.MockitoBean`).
  - `@WebMvcTest` and `@AutoConfigureMockMvc` are located in `org.springframework.boot.webmvc.test.autoconfigure.*` under `spring-boot-starter-webmvc-test`.
* **Current Git branch:** main
* **Current project state:** Fully secured Spring Boot backend with passing unit and controller slice tests.

## LEARNING PROGRESS
* [x] Phase 0: Prerequisites (Java check, OOP, Maven)
* [x] Phase 1: Spring Fundamentals (IoC, DI, Beans, `@Component`, `@Configuration`)
* [x] Phase 2: REST API Development (Controllers, DTOs, CRUD, `@PathVariable`, `@RequestBody`)
* [x] Phase 3: Database & JPA (PostgreSQL, Docker, `@Entity`, `@Id`, Spring Data Repositories)
* [x] Phase 4: Proper Application Architecture (5-Layer Pattern: Controller, Service, Repo, Entity, DTO)
* [x] Phase 5: Validation & Error Handling (`@Valid`, `@NotBlank`, `@RestControllerAdvice`, Custom 404)
* [x] Phase 6: Spring Security (BCrypt, Custom UserDetailsService, JWT Stateless Filter, Registration, RBAC)
* [/] Phase 7: Testing (In Progress: 7.1 Unit Testing & 7.2 Controller Testing completed)
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
