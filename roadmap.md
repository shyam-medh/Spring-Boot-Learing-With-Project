# Spring Boot + DevOpsHub Learning Roadmap

## 1. Learning Phases & Concepts

| Phase | Title | Core Concepts | Expected Outcome |
|---|---|---|---|
| 0 | Prerequisites | Java check, OOP review, basic setup | Ready to start Spring Boot |
| 1 | Spring Fundamentals | DI, IoC, Beans, ApplicationContext, `@Component`, `@Configuration`, Maven, embedded server | A basic running Spring Boot application |
| 2 | REST API Development | HTTP basics, `@RestController`, Request/Response mapping, DTOs, Exception handling | Basic CRUD REST API for DevOpsHub |
| 3 | Database & JPA | Hibernate, ORM, `@Entity`, Repositories, JPQL, Relationships | Data persisted to PostgreSQL |
| 4 | Proper Architecture | Controller-Service-Repository pattern, Mappers, Clean Code | Refactored, clean, layered monolithic architecture |
| 5 | Validation & Error Handling | Bean Validation, `@ControllerAdvice`, standard error responses | Robust APIs that handle bad data gracefully |
| 6 | Spring Security | Auth filters, JWT, SecurityFilterChain, Role-based auth | Secured endpoints, user registration & login |
| 7 | Testing | JUnit, Mockito, MockMvc, Testcontainers | High confidence in code behavior through automated tests |
| 8 | API Documentation | OpenAPI, Swagger | Interactive API docs available at a specific endpoint |
| 9 | Production Features | SLF4J, Profiles, Env variables, Actuator, Health checks | App is configurable for different environments and observable |
| 10 | Advanced Spring Boot | Async, Scheduling, Application Events, Caching (Redis) | App handles background tasks and performs efficiently |
| 11 | Microservices (TBD) | Service boundaries, OpenFeign, API Gateway, Resilience | Split monolith into smaller services (if justified) |
| 12 | Messaging (TBD) | Kafka, Producers, Consumers, Event-driven | Asynchronous event processing between components |
| 13 | Docker | Dockerfile, Multi-stage builds, Docker Compose | Fully containerized Spring Boot + PostgreSQL + Redis environment |
| 14 | CI/CD | GitHub Actions, Build, Test, Push to Registry | Automated pipeline running on every Git push |
| 15 | Cloud Deployment | AWS EC2/RDS, IAM, Load Balancing | DevOpsHub live on the internet |

## 2. Expected Difficulty

* **Phases 1-3 (Fundamentals & Data):** Moderate. Lots of new terminology and "magic" to understand.
* **Phases 4-5 (Architecture & Validation):** Low. Mostly organizing code and applying rules.
* **Phase 6 (Security):** High. Spring Security has a steep learning curve and complex internal filters.
* **Phases 7-10 (Testing & Prod):** Moderate. Testing requires a mindset shift; production features are straightforward.
* **Phases 11-12 (Microservices & Kafka):** High. Introduces distributed system complexities.
* **Phases 13-15 (DevOps):** Moderate to High, depending on prior cloud exposure.

## 3. Project Milestones & Evolution (DevOpsHub)

* **v0.1:** Simple "Hello World" App
* **v0.2:** REST API (Projects, Teams - in-memory)
* **v0.3:** PostgreSQL Integration (Data persisted)
* **v0.4:** Layered Architecture Refactoring
* **v0.5:** Validation & Standardized Errors applied
* **v0.6:** Authentication (User Registration, Login via JWT)
* **v0.7:** Test Suite established
* **v0.8:** Swagger UI enabled
* **v0.9:** Production ready (Actuator, Logging, Profiles)
* **v1.0:** Docker Compose environment
* **v1.1:** CI/CD Pipeline active
* **v1.2:** Deployed to AWS
* **v2.0+:** Advanced architecture (Async, Kafka, etc.)

## 4. Practical Exercises

* **Phase 1:** Create Beans manually vs component scanning.
* **Phase 2:** Build endpoints with Path Variables vs Query Params.
* **Phase 3:** Solve the N+1 query problem in a simulated scenario.
* **Phase 4:** Manually map an Entity to a DTO and vice versa.
* **Phase 6:** Decode a JWT token manually to understand its structure.
* **Phase 7:** Write a failing test, then write the code to make it pass (TDD).

## 5. Recommended Git Branching Strategy

* `main` - Stable, completed versions (e.g., v0.1, v0.2).
* `phase-X-feature` - Working branch for each phase (e.g., `phase-2-rest-api`).
* We will merge into `main` at the end of each successful phase/version.

## 6. Recommended Maven Dependencies Progression

* **Phase 1:** `spring-boot-starter`
* **Phase 2:** `spring-boot-starter-web`
* **Phase 3:** `spring-boot-starter-data-jpa`, `postgresql`
* **Phase 5:** `spring-boot-starter-validation`
* **Phase 6:** `spring-boot-starter-security`, `jjwt`
* **Phase 7:** `spring-boot-starter-test`, `testcontainers`
* **Phase 8:** `springdoc-openapi-starter-webmvc-ui`
* **Phase 9:** `spring-boot-starter-actuator`
* **Phase 10:** `spring-boot-starter-data-redis`

## 7. Database Evolution

* **Phase 1-2:** No database (or simple in-memory list).
* **Phase 3:** PostgreSQL setup. `users`, `projects`, `teams`, `tasks` tables.
* **Future:** Flyway or Liquibase for database migrations (we will add this when appropriate).

## 8. Testing Strategy

* **Controllers:** `@WebMvcTest` (Mocking the Service layer).
* **Services:** Plain JUnit + Mockito (Unit tests, no Spring Context).
* **Repositories:** `@DataJpaTest` with Testcontainers (Integration tests against real Postgres).
* **Full E2E:** `@SpringBootTest` (Integration testing the whole flow).

## 9. Docker/CI/CD & Cloud Progression

1.  **Local:** Run everything on host OS.
2.  **Hybrid:** Spring Boot on host, Postgres in Docker.
3.  **Docker Compose:** Spring Boot and Postgres in Docker Compose.
4.  **CI:** GitHub actions to build JAR and run tests.
5.  **CD:** Build Docker image and push to GHCR/ECR.
6.  **Cloud:** Deploy container to AWS (EC2 or ECS) and use managed RDS.

## 10. Progress Checklist

* [x] **Phase 0: Prerequisites**

**Phase 1: Spring Fundamentals (Completed)**
* [x] 1.1: Project Setup & Maven Structure
* [x] 1.2: Dependency Injection & Application Context

**Phase 2: REST API Development (Completed)**
* [x] 2.1: Controllers & Routing (`@RestController`, `@GetMapping`, `@PostMapping`)
* [x] 2.2: Data Transfer Objects (DTOs) & JSON Mapping
* [x] 2.3: Path Variables & Request Bodies

**Phase 3: Database & JPA (Completed)**
* [x] 3.1: Docker & PostgreSQL Setup
* [x] 3.2: Entities & Primary Keys (`@Entity`, `@Id`)
* [x] 3.3: Spring Data JPA Repositories
* [x] 3.4: Wiring Service to Repository

**Phase 4: Proper Application Architecture (Completed)**
* [x] 4.1: The 5-Layer Model (Controller, Service, Repository, Entity, DTO)
* [x] 4.2: DTO to Entity Mapping in the Service Layer

**Phase 5: Validation & Error Handling (Completed)**
* [x] 5.1: Bean Validation (`@Valid`, `@NotBlank`)
* [x] 5.2: Global Exception Handler (`@RestControllerAdvice`)
* [x] 5.3: Custom Exceptions (`ResourceNotFoundException`)

**Phase 6: Spring Security**
* [x] 6.1: Basic Security Setup & Default Login
* [ ] 6.2: Password Encoding (BCrypt)
* [ ] 6.3: Custom UserDetailsService & Database Users
* [ ] 6.4: Stateless Authentication & JWT Generation
* [ ] 6.5: JWT Validation Filters
* [ ] 6.6: Role-Based Authorization

**Phase 7: Testing**
* [ ] 7.1: Unit Testing with JUnit 5
* [ ] 7.2: Mocking with Mockito (Testing Services)
* [ ] 7.3: Integration Testing with `@DataJpaTest` (Testing Repositories)
* [ ] 7.4: E2E Testing with `@SpringBootTest` & Testcontainers

* [ ] Phase 8: API Documentation
* [ ] Phase 9: Production Features
* [ ] Phase 10: Advanced Spring Boot
* [ ] Phase 11: Microservices
* [ ] Phase 12: Messaging & Event Driven Architecture
* [ ] Phase 13: Docker
* [ ] Phase 14: CI/CD
* [ ] Phase 15: Cloud Deployment
