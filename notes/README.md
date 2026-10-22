# DevOpsHub: Master Knowledge Base & Learning Notes 📖

Welcome to the **DevOpsHub Learning Knowledge Base**! This folder is the centralized reference library for every concept, architectural pattern, mental model, annotation, and bug fix learned while building our production-ready Spring Boot platform.

---

## 🌟 The Core Master References

Before diving into individual phases, these three documents are your **daily developer toolkit**:

1. **[ANNOTATIONS.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/ANNOTATIONS.md)**  
   *The Single Source of Truth for all Spring annotations.* Features collapsible dropdowns (`<details>`) categorized across all 8 phases, complete with syntax, use cases, and the Master Quick Reference Table.
2. **[MENTAL_MODELS.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/MENTAL_MODELS.md)**  
   *Intuitive, zero-jargon analogies.* Contains all 12 mental models (The Restaurant Analogy, The Movie Stunt Double, The Nightclub Bouncer & VIP Badges, The 3-Bucket Security Model, The 4 Import Buckets, and The Front Door vs Circuit Breaker).
3. **[Errors_and_Fixes/README.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Errors_and_Fixes/README.md)**  
   *The Master Troubleshooting Catalog.* Comprehensive diagnostic guides for all 7 real-world errors encountered and fixed throughout this project.

---

## 🗺️ Phase-by-Phase Curriculum

```text
notes/
├── ANNOTATIONS.md                     (Master Annotation Cheatsheet)
├── MENTAL_MODELS.md                   (11 Intuitive Real-World Analogies)
├── Errors_and_Fixes/                  (Master Troubleshooting Catalog - 7 Guides)
│   └── README.md
├── Phase1_Fundamentals/               (Spring IoC, DI, Beans, Maven Structure)
│   └── README.md
├── Phase2_REST_API/                   (Controllers, DTOs, HTTP Verbs, PathVariables)
│   └── README.md
├── Phase3_Database_JPA/               (Docker, PostgreSQL, JPA Entities, Repositories)
│   └── README.md
├── Phase4_Architecture/               (The 5-Layer Monolithic Pattern)
│   └── README.md
├── Phase5_Validation/                 (Jakarta Validation, @Valid, RestControllerAdvice)
│   └── README.md
├── Phase6_Security/                   (Spring Security, BCrypt, JWT Filters, RBAC)
│   └── README.md
└── Phase7_Testing/                    (The Testing Pyramid: Unit, Slice, Security, E2E)
    └── README.md
```

### 1. [Phase 1: Spring Fundamentals](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase1_Fundamentals/README.md)
* **[01_What_Is_Spring.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase1_Fundamentals/01_What_Is_Spring.md)**: Core purpose of the Spring framework.
* **[02_Spring_vs_Spring_Boot.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase1_Fundamentals/02_Spring_vs_Spring_Boot.md)**: Why Spring Boot eliminated XML and boilerplate configuration.
* **[03_Core_Concepts_IoC_DI.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase1_Fundamentals/03_Core_Concepts_IoC_DI.md)**: Inversion of Control and Dependency Injection explained.
* **[03_Maven_Project_Structure.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase1_Fundamentals/03_Maven_Project_Structure.md)**: Standard directory layout and `pom.xml`.
* **[04_Reading_Stack_Traces.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase1_Fundamentals/04_Reading_Stack_Traces.md)**: How to debug Java exceptions systematically.

### 2. [Phase 2: REST API Development](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase2_REST_API/README.md)
* **[01_HTTP_Methods_and_JSON.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase2_REST_API/01_HTTP_Methods_and_JSON.md)**: GET, POST, PUT, DELETE, and JSON contracts.
* **[02_DTOs.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase2_REST_API/02_DTOs.md)**: Why Data Transfer Objects protect internal database models.
* **[03_Dependency_Injection_Flow.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase2_REST_API/03_Dependency_Injection_Flow.md)**: How Spring wires Controllers to Services.
* **[05_Path_Variables.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase2_REST_API/05_Path_Variables.md)**: `@PathVariable` vs `@RequestParam`.
* **[06_CRUD_Summary.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase2_REST_API/06_CRUD_Summary.md)**: Complete REST CRUD reference.

### 3. [Phase 3: Database & JPA](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase3_Database_JPA/README.md)
* **[01_Database_Connection.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase3_Database_JPA/01_Database_Connection.md)**: Docker container setup for PostgreSQL and `application.yaml`.
* **[02_JPA_Entities.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase3_Database_JPA/02_JPA_Entities.md)**: Mapping Java classes to SQL tables with `@Entity` and `@Id`.

### 4. [Phase 4: Application Architecture](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase4_Architecture/README.md)
* **[03_Layered_Architecture.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase4_Architecture/03_Layered_Architecture.md)**: The 5-Layer pattern (Controller, Service, Repository, Entity, DTO).
* **[04_Wiring_Service_to_Repository.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase4_Architecture/04_Wiring_Service_to_Repository.md)**: Clean dependency injection and DTO mapping.

### 5. [Phase 5: Validation & Error Handling](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase5_Validation/README.md)
* **[01_Bean_Validation.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase5_Validation/01_Bean_Validation.md)**: Jakarta Bean Validation (`@Valid`, `@NotBlank`, `@Size`).
* **[02_Global_Exception_Handler.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase5_Validation/02_Global_Exception_Handler.md)**: Centralized error interception with `@RestControllerAdvice`.

### 6. [Phase 6: Spring Security & JWT](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase6_Security/README.md)
* **[01_Basic_Security.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase6_Security/01_Basic_Security.md)**: Spring Security default behavior.
* **[02_Security_Config_and_BCrypt.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase6_Security/02_Security_Config_and_BCrypt.md)**: Password hashing with BCrypt and `SecurityFilterChain`.
* **[03_Complete_JWT_Security_Story.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase6_Security/03_Complete_JWT_Security_Story.md)**: The complete JWT story (creation, parsing, and once-per-request filtering).
* **[04_Registration_and_RBAC.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase6_Security/04_Registration_and_RBAC.md)**: User registration, database seeding, and role-based access (`ADMIN` vs `USER`).
* **[SPRING_SECURITY_FLOW.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase6_Security/SPRING_SECURITY_FLOW.md)**: Visual flowchart of the HTTP request security lifecycle.

### 7. [Phase 7: Automated Testing](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase7_Testing/README.md)
* **[Phase 7 Master Guide](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase7_Testing/README.md)**: Complete overview of the 4 testing levels.
* **[01_Unit_Testing_with_Mockito.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase7_Testing/01_Unit_Testing_with_Mockito.md)**: Level 1: Service testing with `@Mock` and `@InjectMocks`.
* **[02_Controller_Testing_with_MockMvc.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase7_Testing/02_Controller_Testing_with_MockMvc.md)**: Level 2: Web slice testing with `@WebMvcTest` and the 4 Import Buckets.
* **[03_Security_Testing_with_MockUser.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase7_Testing/03_Security_Testing_with_MockUser.md)**: Level 2.5: Role-based testing with MockMvc and `.with(csrf())`.
* **[04_Integration_Testing_SpringBootTest.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase7_Testing/04_Integration_Testing_SpringBootTest.md)**: Level 3: Full end-to-end testing with `@SpringBootTest` and real PostgreSQL.

---

> [!TIP]
> **How to study with these notes:**
> 1. Need an annotation? Open `ANNOTATIONS.md` and use `Ctrl + F`.
> 2. Hit a strange bug? Check `Errors_and_Fixes/README.md`.
> 3. Need to recall the big picture? Review `MENTAL_MODELS.md`.
