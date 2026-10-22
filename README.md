# DevOpsHub: Spring Boot Learning & Development Platform 🛠️

[![Java 21](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot 4.1.1](https://img.shields.io/badge/Spring%20Boot-4.1.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL%2015-blue.svg)](https://www.postgresql.org/)
[![Swagger OpenAPI 3](https://img.shields.io/badge/API%20Docs-OpenAPI%203%20%2F%20Swagger%20UI-green.svg)](http://localhost:8080/swagger-ui/index.html)
[![Tests Passing](https://img.shields.io/badge/Automated%20Tests-16%2F16%20Passing-success.svg)](backend/src/test/java/com/devopshub/backend/)

Welcome to the **DevOpsHub** repository! This project is being engineered progressively as part of a hands-on, production-grade Spring Boot learning journey. 

The goal is to independently design, develop, debug, test, secure, document, containerize, and deploy a complete enterprise application from scratch using **Java 21** and **Spring Boot 4.1.1**.

---

## 📑 Core Documentation Links

* 📐 **[DEVOPSHUB_SPECIFICATION.md](DEVOPSHUB_SPECIFICATION.md)** — **Master Engineering Specification & Domain Architecture**. Details the full 5-entity ER diagram (`users`, `projects`, `environments`, `deployments`, `teams`), business rules, and future stages.
* 📖 **[PROJECT_CONTEXT.md](PROJECT_CONTEXT.md)** — Project technical state, testing registry, dependency log, and troubleshooting history.
* 🗺️ **[roadmap.md](roadmap.md)** — 15-phase curriculum and milestone tracker.
* 📚 **[notes/README.md](notes/README.md)** — Centralized knowledge base index with deep-dives for every completed phase.
* 🏷️ **[notes/ANNOTATIONS.md](notes/ANNOTATIONS.md)** — Master annotations cheatsheet with collapsible dropdowns.
* 🧠 **[notes/MENTAL_MODELS.md](notes/MENTAL_MODELS.md)** — Visual mental models (Bouncer, Stunt Double, 4 Import Buckets).
* 🛠️ **[notes/Errors_and_Fixes/README.md](notes/Errors_and_Fixes/README.md)** — Real-world bug catalog and diagnostics.

---

## 📚 Learning Journey Progress

| Phase | Title | Status | What Was Built & Mastered |
|---|---|---|---|
| **Phase 0** | Prerequisites | ✅ Completed | Java 21, OOP review, Maven wrapper |
| **Phase 1** | Spring Fundamentals | ✅ Completed | IoC, Dependency Injection, Beans, ApplicationContext |
| **Phase 2** | REST API Development | ✅ Completed | `@RestController`, `@GetMapping`, `@PostMapping`, DTOs |
| **Phase 3** | Database & JPA | ✅ Completed | PostgreSQL via Docker, Hibernate ORM, `@Entity`, Repositories |
| **Phase 4** | Layered Architecture | ✅ Completed | Controller ➔ Service ➔ Repository ➔ Entity ➔ DTO |
| **Phase 5** | Validation & Errors | ✅ Completed | Jakarta Bean Validation (`@Valid`, `@NotBlank`), `@RestControllerAdvice`, Custom 404 |
| **Phase 6** | Spring Security & JWT | ✅ Completed | BCrypt, `UserDetailsService`, JWT token filters, RBAC (`ADMIN` vs `USER`) |
| **Phase 7** | Automated Testing | ✅ Completed | 4-Level Testing Pyramid (Unit, WebMvc Slice, Security, E2E) — **16/16 Tests Passing** |
| **Phase 8** | API Documentation | ✅ Completed | OpenAPI 3, Interactive Swagger UI, JWT Authorize Padlock, CORS Architecture |
| **Domain** | DevOpsHub Platform | 🔄 In Progress | Building out real domain: Projects, Environments, Deployments, Teams |
| **Phase 9** | Production Features | ⏳ Planned | Actuator, Observability, Logging (SLF4J), Multi-environment Profiles |
| **Phase 10+**| Advanced & DevOps | ⏳ Planned | Redis Caching, Docker Compose, GitHub Actions CI/CD, AWS Deployment |

---

## 🌐 Interactive API Documentation (Swagger UI)

DevOpsHub features interactive API documentation with integrated JWT authentication:

* 🖥️ **Interactive Swagger UI Dashboard:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
* 📄 **Raw OpenAPI 3 JSON Specification:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

### How to Authenticate in Swagger UI:
1. Open [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).
2. Expand `POST /api/auth/login` under **Authentication**.
3. Click **Try it out**, enter the default credentials, and click **Execute**:
   ```json
   {
     "username": "admin",
     "password": "admin123"
   }
   ```
4. Copy the returned `token` from the response JSON.
5. Click the green 🔒 **Authorize** button at the top of the Swagger page.
6. Paste the token into the value field and click **Authorize**. All protected endpoints are now authorized!

---

## 🔌 Active REST API Endpoints

| Category | HTTP Method | Route | Access | Description |
|---|---|---|---|---|
| **Auth** | `POST` | `/api/auth/register` | Public | Register a new user |
| **Auth** | `POST` | `/api/auth/login` | Public | Authenticate user & receive JWT Bearer token |
| **Projects** | `GET` | `/api/projects` | `USER`, `ADMIN` | List all projects |
| **Projects** | `GET` | `/api/projects/{id}` | `USER`, `ADMIN` | Get project by ID |
| **Projects** | `POST` | `/api/projects` | `ADMIN` | Create a new project |
| **Projects** | `DELETE` | `/api/projects/{id}` | `ADMIN` | Delete a project by ID |
| **Docs** | `GET` | `/swagger-ui/index.html` | Public | Interactive Swagger UI dashboard |
| **Docs** | `GET` | `/v3/api-docs` | Public | Raw OpenAPI 3 JSON schema |

*(For the complete future REST matrix including Environments, Deployments, and Teams, refer to [DEVOPSHUB_SPECIFICATION.md](DEVOPSHUB_SPECIFICATION.md).)*

---

## ⚡ Quickstart & Local Setup

### 1. Prerequisites
* **Java 21** installed (`java -version`)
* **Docker Desktop** installed and running
* **Apache Maven** installed (`mvn -version`)

### 2. Start PostgreSQL via Docker
If creating the container for the first time:
```bash
docker run --name devopshub-db -e POSTGRES_DB=devopshub -e POSTGRES_USER=myuser -e POSTGRES_PASSWORD=secret -p 5432:5432 -d postgres:15
```
If the container already exists:
```bash
docker start devopshub-db
```

### 3. Run the Spring Boot Backend
From the root directory:
```bash
cd backend
mvn spring-boot:run
```
The server will start at `http://localhost:8080`. On initial boot, `DataInitializer` automatically seeds an administrator account (`admin` / `admin123`).

### 4. Run the Automated Test Suite
DevOpsHub has 16 automated tests covering Unit, WebMvc Slice, Security, and E2E layers:
```bash
cd backend
mvn test
```
**Expected Output:**
```text
[INFO] Results:
[INFO] Tests run: 16, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

---

## 📁 Repository Directory Structure

<details>
<summary><b>Click to expand full repository directory tree</b></summary>

```text
spring boot/
├── backend/                                   # Spring Boot Core Application
│   ├── pom.xml                                # Maven build config (Java 21, Spring Boot 4.1.1)
│   └── src/
│       ├── main/java/com/devopshub/backend/
│       │   ├── BackendApplication.java        # Main entry point & UTC static block
│       │   ├── config/
│       │   │   ├── SecurityConfig.java        # SecurityFilterChain, RBAC & path whitelisting
│       │   │   ├── OpenApiConfig.java         # OpenAPI 3 metadata & JWT Bearer padlock scheme
│       │   │   └── DataInitializer.java       # Automatic DB seeder for default admin user
│       │   ├── controller/
│       │   │   ├── AuthController.java        # /api/auth/register & /login endpoints
│       │   │   └── ProjectController.java     # /api/projects CRUD REST controller
│       │   ├── dto/
│       │   │   ├── AuthRequest.java           # Login credentials payload
│       │   │   ├── AuthResponse.java          # JWT bearer token response
│       │   │   ├── RegisterRequest.java       # User registration payload
│       │   │   └── ProjectDTO.java            # Project data transfer object
│       │   ├── entity/
│       │   │   ├── Project.java               # JPA entity for "projects" table
│       │   │   └── User.java                  # JPA entity for "users" table
│       │   ├── repository/
│       │   │   ├── ProjectRepository.java     # Spring Data JPA interface for projects
│       │   │   └── UserRepository.java        # Spring Data JPA interface for users
│       │   ├── service/
│       │   │   ├── ProjectService.java        # Business logic & DTO-to-entity mapping
│       │   │   └── CustomUserDetailsService.java # UserDetails bridge for Spring Security
│       │   ├── security/
│       │   │   ├── JwtUtil.java               # HMAC-SHA256 token generation & validation
│       │   │   └── JwtAuthenticationFilter.java # OncePerRequestFilter for ThreadLocal security
│       │   └── exception/
│       │       ├── GlobalExceptionHandler.java # @RestControllerAdvice centralized error handler
│       │       └── ResourceNotFoundException.java # Custom 404 runtime exception
│       │
│       └── test/java/com/devopshub/backend/
│           ├── BackendApplicationTests.java   # Spring ApplicationContext boot verification
│           ├── service/
│           │   └── ProjectServiceTest.java    # Level 1: Unit tests with Mockito (4 tests)
│           ├── controller/
│           │   ├── ProjectControllerTest.java # Level 2: Slice tests with MockMvc (6 tests)
│           │   └── ProjectSecurityTest.java   # Level 2.5: Role security tests with MockMvc (3 tests)
│           └── integration/
│               └── ProjectIntegrationTest.java# Level 3: Full E2E tests with live PostgreSQL (2 tests)
│
├── notes/                                     # Centralized Knowledge Base & Curriculum
│   ├── README.md                              # Master Curriculum Portal
│   ├── ANNOTATIONS.md                         # Master Annotations Dropdown Cheatsheet
│   ├── MENTAL_MODELS.md                       # 13 Visual Mental Models & Analogies
│   ├── Errors_and_Fixes/                      # Real-world diagnostic guides
│   ├── Phase1_Fundamentals/                   # IoC, DI, Beans, Maven structure
│   ├── Phase2_REST_API/                       # Controllers, HTTP methods, DTOs, PathVariables
│   ├── Phase3_Database_JPA/                   # Docker, PostgreSQL, Hibernate, JPA Repositories
│   ├── Phase4_Architecture/                   # The 5-Layer Monolithic Pattern
│   ├── Phase5_Validation/                     # Jakarta Bean Validation, @RestControllerAdvice
│   ├── Phase6_Security/                       # BCrypt, JWT Filter Chain, RBAC
│   ├── Phase7_Testing/                        # JUnit 5, Mockito, MockMvc, @SpringBootTest
│   └── Phase8_Documentation/                  # OpenAPI 3, Swagger UI, CORS Architecture
│
├── DEVOPSHUB_SPECIFICATION.md                 # Immutable Domain Architecture & Ground Truth Contract
├── PROJECT_CONTEXT.md                         # Project Context, Test Registry, Decisions
├── roadmap.md                                 # Full 15-Phase Roadmap
├── PAUSED.md                                  # Session State Checkpoint (paused sessions only)
└── README.md                                  # Repository Entry Point (This File)
```

</details>

---

## 🛠️ Technology Stack Summary

* **Language:** Java 21 LTS (OpenJDK 64-Bit Server VM)
* **Framework:** Spring Boot 4.1.1 (Spring Framework 7.0.9)
* **Web Server:** Embedded Apache Tomcat 11
* **Database:** PostgreSQL 15 (Docker)
* **Persistence:** Spring Data JPA + Hibernate Core 7.4.5 + HikariCP 7.0.2
* **Validation:** Jakarta Bean Validation (Hibernate Validator)
* **Security:** Spring Security 7.0.9 + JJWT 0.11.5 + BCrypt
* **Documentation:** SpringDoc OpenAPI 3 UI 3.1.1
* **Testing:** JUnit 5, Mockito, MockMvc, AssertJ, Hamcrest
