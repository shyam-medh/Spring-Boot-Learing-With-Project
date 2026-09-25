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
* **Phase 6:** Spring Security (Authentication & Authorization) ← *In Progress (Phase 6.4: JWT)*
* **Phase 7:** Automated Testing
* **Phase 8:** API Documentation (Swagger/OpenAPI)
* **Phase 9:** Production Features (Actuator, Logging)
* **Phase 10+:** Advanced concepts, Docker, CI/CD, and Cloud Deployment

For detailed learning context, check out `PROJECT_CONTEXT.md` and the notes in the `notes/` directory.

## 🛠️ Technology Stack
* **Java 21**
* **Spring Boot 3.x**
* **PostgreSQL** (via Docker)
* **Spring Boot Validation**
* **Spring Boot Security**
* **JJWT 0.11.5** (JSON Web Tokens)

## 📁 Current Project Structure (Phase 6)
```text
spring boot/
├── backend/ (Spring Boot Application)
│   └── src/main/java/com/devopshub/backend/
│       ├── BackendApplication.java       (Entry Point)
│       ├── config/
│       │   ├── SecurityConfig.java       (The Club Manager - Security Rules)
│       │   └── DataInitializer.java      (Seeds admin user into DB on startup)
│       ├── security/
│       │   └── JwtUtil.java              (The Wristband Machine - generates & validates JWTs)
│       ├── controller/
│       │   ├── HelloController.java      (Test GET Endpoint)
│       │   └── ProjectController.java    (REST Controller for Projects)
│       ├── service/
│       │   ├── ProjectService.java       (Business Logic)
│       │   └── CustomUserDetailsService.java (The Bouncer - loads users from DB)
│       ├── repository/
│       │   ├── ProjectRepository.java    (Database Access)
│       │   └── UserRepository.java       (User Database Access)
│       ├── entity/
│       │   ├── Project.java              (Database Model)
│       │   └── User.java                 (User Database Model)
│       ├── dto/
│       │   └── ProjectDTO.java           (Data Transfer Object)
│       └── exception/
│           ├── GlobalExceptionHandler.java    (Customer Service Desk)
│           └── ResourceNotFoundException.java (Custom 404 Error)
├── notes/
│   ├── Phase1_Fundamentals/
│   ├── Phase2_REST_API/
│   ├── Phase3_Database_JPA/
│   ├── Phase4_Architecture/
│   ├── Phase5_Validation/
│   ├── Phase6_Security/
│   │   └── SPRING_SECURITY_FLOW.md      (Full security flow explained)
│   ├── MENTAL_MODELS.md                 (Key analogies & concepts)
│   └── Errors_and_Fixes/
├── PROJECT_CONTEXT.md                    (Learning history and state)
└── PAUSED.md                             (Exactly where the current session stopped)
```

---
*Note: This README will be continuously updated as new features are added to DevOpsHub.*
