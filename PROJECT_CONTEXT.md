# DevOpsHub - Spring Boot Project Context & Engineering Blueprint 📖

> [!NOTE]
> **Project Mission:**  
> "Become capable of independently designing, developing, debugging, testing, containerizing, and deploying production-grade Spring Boot applications from scratch."

---

## 1. Executive Status & Milestone Tracker

* **CURRENT PHASE:** Transitioning from Foundation to Core Application Domain
* **COMPLETED PHASES:** Phase 0 through Phase 8 (100% Completed)
* **CURRENT STATUS:** 
  * ✅ **16 / 16 Automated Tests Passing** across all 4 levels of the testing pyramid.
  * ✅ **Interactive Swagger UI Live** at `http://localhost:8080/swagger-ui/index.html`.
  * ✅ **OpenAPI 3 Specification Live** at `http://localhost:8080/v3/api-docs`.
  * ✅ **Live PostgreSQL Database** running in Docker container `devopshub-db` on port `5432`.
* **CURRENT MILESTONE:** `v0.8` - Fully Documented & Tested REST API Foundation
* **NEXT MILESTONE:** `v1.0` - DevOpsHub Core Domain Engine (Upgraded Project Entity, Enums, Git Repositories, Timestamps, Auditing, and Pagination)
* **CURRENT GIT BRANCH:** `main` (Clean working tree)

---

## 2. Comprehensive Curriculum & Phase Progress

| Phase | Title | Core Concepts Mastered | Status |
|---|---|---|---|
| **Phase 0** | **Prerequisites** | Java 21, OOP principles, Maven wrapper (`mvnw`), terminal navigation | ✅ Completed |
| **Phase 1** | **Spring Fundamentals** | IoC, Dependency Injection, Beans, `@Component`, `@Configuration`, ApplicationContext RAM HashMap | ✅ Completed |
| **Phase 2** | **REST API Development** | DispatcherServlet, `@RestController`, HTTP Verbs (GET, POST, DELETE), `@PathVariable`, `@RequestBody`, DTOs | ✅ Completed |
| **Phase 3** | **Database & JPA** | Docker PostgreSQL, Hibernate ORM, `@Entity`, `@Id`, `@GeneratedValue`, Spring Data JPA Repositories | ✅ Completed |
| **Phase 4** | **Application Architecture**| The 5-Layer Monolith (Controller ➔ Service ➔ Repository ➔ Entity ➔ DTO), single-responsibility rule | ✅ Completed |
| **Phase 5** | **Validation & Errors** | Jakarta Bean Validation (`@Valid`, `@NotBlank`), `@RestControllerAdvice`, standardized JSON errors, custom 404 | ✅ Completed |
| **Phase 6** | **Spring Security & JWT** | BCrypt password hashing, `UserDetailsService`, JWT token lifecycle, `OncePerRequestFilter`, `ThreadLocal`, RBAC | ✅ Completed |
| **Phase 7** | **Automated Testing** | 4-level testing pyramid: Unit (Mockito), Slice (`@WebMvcTest`), Security (`@WithMockUser`), E2E (`@SpringBootTest`) (16/16 Passing) | ✅ Completed |
| **Phase 8** | **API Documentation** | OpenAPI 3, Swagger UI, `OpenApiConfig`, JWT Authorize button, `@Tag`, `@Operation`, `@Schema`, CORS architecture | ✅ Completed |
| **Phase 9** | **Production Features** | Spring Boot Actuator, Health checks, Observability, SLF4J / Logback logging, Multi-Environment Profiles | ⏳ Next |
| **Phase 10** | **Advanced Spring Boot** | Redis Caching, Asynchronous execution (`@Async`), Scheduled tasks (`@Scheduled`) | ⏳ Planned |
| **Phase 11** | **Microservices** | Service boundaries, Spring Cloud Gateway, OpenFeign, Service Registry (Eureka / Consul) | ⏳ Planned |
| **Phase 12** | **Event-Driven Architecture**| Apache Kafka, Producers, Consumers, Event Topics, Asynchronous messaging | ⏳ Planned |
| **Phase 13** | **Docker Containerization** | Multi-stage Dockerfile, Docker Compose full-stack orchestration | ⏳ Planned |
| **Phase 14** | **CI/CD Pipelines** | GitHub Actions (Automated build, test verification, container registry publish) | ⏳ Planned |
| **Phase 15** | **Cloud Deployment** | AWS (EC2 / ECS, RDS PostgreSQL, IAM security, Load Balancer) | ⏳ Planned |

---

## 3. Technology Stack & Dependencies

* **Language & Runtime:** Java 21 (OpenJDK 64-Bit Server VM)
* **Framework:** Spring Boot 4.1.1 (Spring Framework 7.0.9)
* **Web Server:** Embedded Apache Tomcat 11
* **Database & Driver:** PostgreSQL 15 (Docker) + `org.postgresql:postgresql:42.7.13`
* **ORM & Connection Pooling:** Spring Data JPA + Hibernate Core 7.4.5 + HikariCP 7.0.2
* **Data Validation:** Jakarta Bean Validation (`spring-boot-starter-validation` / Hibernate Validator)
* **Security & Auth:** Spring Security 7.0.9 + BCrypt + JJWT (Java JWT) 0.11.5 (`jjwt-api`, `jjwt-impl`, `jjwt-jackson`)
* **API Documentation:** SpringDoc OpenAPI Starter WebMVC UI 3.1.1 (`org.springdoc`)
* **Testing Suite:** JUnit 5 (Jupiter), Mockito (Core & ByteBuddy), Spring Test (`MockMvc`, `@WebMvcTest`, `@SpringBootTest`, `@MockitoBean`), AssertJ, Hamcrest Matchers

---

## 4. Current Application File Registry

```text
backend/src/
├── main/java/com/devopshub/backend/
│   ├── BackendApplication.java            # App entry point & JVM UTC static initializer
│   ├── config/
│   │   ├── SecurityConfig.java            # SecurityFilterChain, BCrypt, RBAC, whitelist rules
│   │   ├── OpenApiConfig.java             # OpenAPI 3 info & BearerAuth JWT security scheme
│   │   └── DataInitializer.java           # Startup DB seeder for default admin account
│   ├── controller/
│   │   ├── AuthController.java            # /api/auth/register & /login endpoints (@Tag, @Operation)
│   │   └── ProjectController.java         # /api/projects CRUD REST endpoints (@Tag, @Operation)
│   ├── service/
│   │   ├── ProjectService.java            # Project business rules, entity mapping, DB delegation
│   │   └── CustomUserDetailsService.java  # Bridge between PostgreSQL User and Spring UserDetails
│   ├── repository/
│   │   ├── ProjectRepository.java         # Spring Data JPA interface for projects table
│   │   └── UserRepository.java            # Spring Data JPA interface for users table
│   ├── entity/
│   │   ├── Project.java                   # JPA entity mapped to "projects" SQL table
│   │   └── User.java                      # JPA entity mapped to "users" SQL table
│   ├── dto/
│   │   ├── ProjectDTO.java                # Project transfer payload with @Schema & @NotBlank
│   │   ├── AuthRequest.java               # Login credentials payload with @Schema
│   │   ├── AuthResponse.java              # JWT bearer token response with @Schema
│   │   └── RegisterRequest.java           # Registration payload with @Schema
│   ├── security/
│   │   ├── JwtUtil.java                   # Cryptographic JWT printer & scanner (HMAC-SHA256)
│   │   └── JwtAuthenticationFilter.java   # OncePerRequestFilter checking tokens & ThreadLocal context
│   └── exception/
│       ├── GlobalExceptionHandler.java    # @RestControllerAdvice centralized error translator
│       └── ResourceNotFoundException.java # Custom runtime exception triggering 404 Not Found
│
└── test/java/com/devopshub/backend/
    ├── BackendApplicationTests.java       # Context boot smoke test with UTC static block (1 test)
    ├── service/
    │   └── ProjectServiceTest.java        # Level 1: Pure Mockito unit tests (@Mock/@InjectMocks) (4 tests)
    ├── controller/
    │   ├── ProjectControllerTest.java     # Level 2: Web slice tests with MockMvc (@MockitoBean) (6 tests)
    │   └── ProjectSecurityTest.java       # Level 2.5: Role security tests with MockMvc (3 tests)
    └── integration/
        └── ProjectIntegrationTest.java    # Level 3: Full E2E tests with real PostgreSQL in Docker (2 tests)
```

---

## 5. Automated Test Suite Registry (16 / 16 Passing)

| Test Class | Level | Test Method | What It Verifies |
|---|---|---|---|
| `ProjectServiceTest` | Level 1 (Unit) | `getAllProjects_shouldReturnProjectList()` | Verifies service calls `repo.findAll()` and maps entities to DTOs |
| `ProjectServiceTest` | Level 1 (Unit) | `getProjectById_whenProjectExists_shouldReturnProject()` | Verifies retrieval by ID when record exists |
| `ProjectServiceTest` | Level 1 (Unit) | `getProjectById_whenProjectDoesNotExist_shouldThrowException()` | Verifies `ResourceNotFoundException` is thrown when ID not found |
| `ProjectServiceTest` | Level 1 (Unit) | `addProject_shouldSaveAndReturnProject()` | Verifies service maps DTO to entity and calls `repo.save()` |
| `ProjectControllerTest` | Level 2 (Slice) | `shouldReturnAllProjects()` | Verifies `GET /api/projects` returns `200 OK` and JSON array |
| `ProjectControllerTest` | Level 2 (Slice) | `shouldReturnProjectById_WhenExists()` | Verifies `GET /api/projects/{id}` returns `200 OK` and matching JSON |
| `ProjectControllerTest` | Level 2 (Slice) | `shouldReturn404_WhenProjectNotFound()` | Verifies `GET /api/projects/999` triggers `GlobalExceptionHandler` 404 |
| `ProjectControllerTest` | Level 2 (Slice) | `shouldCreateProject_WhenValid()` | Verifies `POST /api/projects` returns `200 OK` when payload valid |
| `ProjectControllerTest` | Level 2 (Slice) | `shouldReturn400_WhenProjectNameIsBlank()` | Verifies `@Valid` intercepts blank names before method executes |
| `ProjectControllerTest` | Level 2 (Slice) | `shouldDeleteProject()` | Verifies `DELETE /api/projects/{id}` returns `200 OK` |
| `ProjectSecurityTest` | Level 2.5 (Security) | `shouldRejectAnonymousUser_WhenDeletingProject()` | Verifies stranger with no token gets `403 Forbidden` |
| `ProjectSecurityTest` | Level 2.5 (Security) | `shouldRejectRegularUser_WhenDeletingProject()` | Verifies `@WithMockUser(roles="USER")` gets `403 Forbidden` on admin route |
| `ProjectSecurityTest` | Level 2.5 (Security) | `shouldAllowAdminUser_WhenDeletingProject()` | Verifies `@WithMockUser(roles="ADMIN")` with `.with(csrf())` gets `200 OK` |
| `ProjectIntegrationTest`| Level 3 (E2E) | `shouldCreateAndRetrieveProject_EndToEnd()` | Full round-trip: HTTP POST ➔ real PostgreSQL in Docker ➔ HTTP GET verification |
| `ProjectIntegrationTest`| Level 3 (E2E) | `shouldDeleteProject_EndToEnd()` | Full round-trip: inserts row, sends HTTP DELETE, verifies row deleted in DB |
| `BackendApplicationTests`| Level 3 (Context) | `contextLoads()` | Verifies Spring ApplicationContext boots cleanly with all beans wired |

---

## 6. Real-World Troubleshooting & Solutions Archive

1. **`MockBean` Missing in Spring Boot 4:**
   * *Cause:* Spring Boot 4 modularized test starters and replaced Spring Boot's legacy `@MockBean` with core Spring Framework's `@MockitoBean`.
   * *Solution:* Updated imports to `org.springframework.test.context.bean.override.mockito.MockitoBean`.
2. **`Unable to determine Dialect without JDBC metadata`:**
   * *Cause:* Windows host ran in `Asia/Calcutta` timezone. When JUnit executed `@SpringBootTest` in a forked JVM, the PostgreSQL driver sent `TimeZone: "Asia/Calcutta"` to Linux PostgreSQL in Docker, which rejected the connection string during dialect discovery.
   * *Solution:* 
     1. Added `static { TimeZone.setDefault(TimeZone.getTimeZone("UTC")); }` in `BackendApplication.java`, `ProjectIntegrationTest.java`, and `BackendApplicationTests.java`.
     2. Configured `<argLine>-Duser.timezone=UTC</argLine>` in `maven-surefire-plugin` in `pom.xml`.
     3. Explicitly defined `hibernate.dialect: org.hibernate.dialect.PostgreSQLDialect` in `application.yaml`.
3. **Swagger UI 403 Forbidden Errors:**
   * *Cause:* Spring Security's filter chain blocks all unauthenticated paths by default.
   * *Solution:* Whitelisted `/v3/api-docs/**`, `/swagger-ui/**`, and `/swagger-ui.html` via `.permitAll()` in `SecurityConfig.java`.

---

## 7. The Next Chapter: DevOpsHub Core Domain Development Blueprint

We are moving away from toy stubs into building the real developer platform using the **7-Step Enterprise Lifecycle**:

```text
Step 1: Domain Modeling & Entities (Project, ProjectStatus enum, Auditing timestamps)
Step 2: DTOs & Validation (ProjectCreateRequest, ProjectResponseDTO, strict constraints)
Step 3: Repository Layer (Custom JPQL queries, pagination, sorting)
Step 4: Business Service Layer (Unique project names, ownership verification)
Step 5: REST Controller Layer (Proper 201 Created responses, location headers)
Step 6: Automated Testing Suite (Unit, Slice, Security, and E2E regression tests)
Step 7: OpenAPI & Swagger Documentation (Rich schema examples, tag groups)
```
