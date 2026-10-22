# DevOpsHub - Developer & Infrastructure Management Platform

Welcome to the **DevOpsHub** repository! This project is being built progressively as part of a comprehensive Spring Boot learning journey. 

The goal of this project is not just to build an application, but to learn how to independently design, develop, debug, test, containerize, and deploy Spring Boot applications from scratch.

## 🚀 Project Overview

**DevOpsHub** will eventually be a platform allowing users to:
* Register and authenticate
* Create and manage projects, teams, and tasks
* Manage environments (Dev, Stage, Prod)
* Track deployment status and history
* (Eventually) Integrate with CI/CD, Docker, and Cloud platforms.

## 📚 Learning Journey

This project evolves alongside my learning process. We are following a structured roadmap:
* **Phase 1:** Spring Fundamentals & Setup (Completed)
* **Phase 2:** REST API Development (Completed)
* **Phase 3:** Database & JPA (PostgreSQL) (Completed)
* **Phase 4:** Proper Application Architecture (Completed)
* **Phase 5:** Validation & Error Handling (Completed)
* **Phase 6:** Spring Security (Authentication & Authorization) (Completed)
* **Phase 7:** Automated Testing (In Progress: 7.1 Unit, 7.2 Controller, and 7.3 Security Tests Complete - 13/13 Passing!)
* **Phase 8:** API Documentation (Swagger/OpenAPI)
* **Phase 9:** Production Features (Actuator, Logging)
* **Phase 10+:** Advanced concepts, Docker, CI/CD, and Cloud Deployment

For detailed learning context, check out `PROJECT_CONTEXT.md` and the notes in the `notes/` directory.

## 🛠️ Technology Stack
* **Java 21**
* **Spring Boot 4.x (4.1.1)**
* **PostgreSQL** (via Docker)
* **Spring Boot Validation**
* **Spring Boot Security**
* **JJWT 0.11.5** (JSON Web Tokens)
* **Automated Testing:** JUnit 5, Mockito, MockMvc, AssertJ, Hamcrest

## 📁 Current Project Structure (Phase 7)
```text
spring boot/
├── backend/ (Spring Boot Application)
│   └── src/main/java/com/devopshub/backend/
│       ├── BackendApplication.java       (Entry Point)
│       ├── config/
│       │   ├── SecurityConfig.java       (The Club Manager - Security Rules & Filter Chain)
│       │   └── DataInitializer.java      (Seeds admin user into DB on startup)
│       ├── security/
│       │   ├── JwtUtil.java              (The Wristband Machine - generates & validates JWTs)
│       │   └── JwtAuthenticationFilter.java (The Bouncer - intercepts requests & checks JWT)
│       ├── controller/
│       │   ├── AuthController.java       (The Auth Desk - /login & /register)
│       │   └── ProjectController.java    (REST Controller for Projects)
│       ├── service/
│       │   ├── ProjectService.java       (Business Logic)
│       │   └── CustomUserDetailsService.java (The Translator - loads users from DB)
│       ├── repository/
│       │   ├── ProjectRepository.java    (Database Access)
│       │   └── UserRepository.java       (User Database Access)
│       ├── entity/
│       │   ├── Project.java              (Database Model)
│       │   └── User.java                 (User Database Model)
│       ├── dto/
│       │   ├── AuthRequest.java          (Login credentials DTO)
│       │   ├── AuthResponse.java         (JWT Token DTO)
│       │   ├── RegisterRequest.java      (User registration DTO)
│       │   └── ProjectDTO.java           (Data Transfer Object)
│       └── exception/
│           ├── GlobalExceptionHandler.java    (Customer Service Desk)
│           └── ResourceNotFoundException.java (Custom 404 Error)
│   └── src/test/java/com/devopshub/backend/
│       ├── service/
│       │   └── ProjectServiceTest.java        (Level 1: Unit Testing with Mockito - 4/4 passing)
│       ├── controller/
│       │   ├── ProjectControllerTest.java     (Level 2: Slice Testing with MockMvc - 6/6 passing)
│       │   └── ProjectSecurityTest.java       (Level 2.5: Security Testing with MockMvc - 3/3 passing)
│       └── integration/
│           └── ProjectIntegrationTest.java    (Level 3: End-to-End Integration Testing with @SpringBootTest)
├── notes/                                     (Learning Notes & Knowledge Base - see link below)
├── PROJECT_CONTEXT.md                         (Learning history and state)
└── PAUSED.md                                  (Current checkpoint)
```

## 📖 Learning Notes & Knowledge Base

All detailed concept explanations, architectural deep-dives, cheat sheets, and bug fixes are maintained in the **[notes/](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/README.md)** directory:

* 📚 **[DevOpsHub Master Notes Portal](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/README.md)** — Centralized curriculum index linking all phase guides.
* 🏷️ **[Master Annotations Cheatsheet](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/ANNOTATIONS.md)** — Single source of truth for all Spring annotations with collapsible dropdowns.
* 🧠 **[Mental Models & Analogies](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/MENTAL_MODELS.md)** — Intuitive analogies (Restaurant, Bouncer, Stunt Double, 4 Import Buckets, Front Door vs Circuit Breaker).
* 🛠️ **[Errors & Troubleshooting Catalog](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Errors_and_Fixes/README.md)** — Diagnostics and solutions for real-world bugs encountered.

---
*Note: This README will be continuously updated as new features are added to DevOpsHub.*
