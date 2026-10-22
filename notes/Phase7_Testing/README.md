# Phase 7: Automated Testing Master Guide 🧪

Welcome to the **Phase 7 Testing Master Guide**! This directory contains the complete curriculum, mental models, code patterns, and troubleshooting steps for building a rock-solid automated test suite in Spring Boot.

---

## 🏛️ The Testing Pyramid

```text
               ▲
              / \         Level 3: @SpringBootTest (End-to-End)
             /   \        [04_Integration_Testing_SpringBootTest.md]
            /─────\       Zero mocks. Full app boot connected to PostgreSQL in Docker.
           /       \
          /─────────\     Level 2.5: Security Testing (Roles & Access)
         /           \    [03_Security_Testing_with_MockUser.md]
        /─────────────\   Verifies 403 Forbidden & 200 OK using MockMvc.
       /               \
      /─────────────────\ Level 2: Controller Slice Testing (@WebMvcTest)
     /                   \[02_Controller_Testing_with_MockMvc.md]
    /─────────────────────\Tests URLs, JSON serialization, and @Valid 400 errors.
   /                       \
  /─────────────────────────\ Level 1: Unit Testing (Mockito)
 /                           \[01_Unit_Testing_with_Mockito.md]
/─────────────────────────────\Tests the Chef: pure business logic & calculations.
```

---

## 📚 Phase 7 Modules & Guides

| Module | Guide Document | Test Class in Code | Primary Focus |
|---|---|---|---|
| **7.1** | [01_Unit_Testing_with_Mockito.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase7_Testing/01_Unit_Testing_with_Mockito.md) | `ProjectServiceTest.java` | Fast, isolated business logic testing with `@Mock` & `@InjectMocks`. |
| **7.2** | [02_Controller_Testing_with_MockMvc.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase7_Testing/02_Controller_Testing_with_MockMvc.md) | `ProjectControllerTest.java` | Web slice testing with `MockMvc`, JSON mapping, and Bean Validation. |
| **7.3** | [03_Security_Testing_with_MockUser.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase7_Testing/03_Security_Testing_with_MockUser.md) | `ProjectSecurityTest.java` | Role-based authorization (`USER` vs `ADMIN`) and CSRF handling. |
| **7.4** | [04_Integration_Testing_SpringBootTest.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase7_Testing/04_Integration_Testing_SpringBootTest.md) | `ProjectIntegrationTest.java` | Real round-trip testing from HTTP request down to real PostgreSQL in Docker. |

---

## ⚡ Master Testing Comparison

| Feature | Level 1: Unit Test | Level 2: Controller Slice | Level 2.5: Security Test | Level 3: Integration (E2E) |
|---|---|---|---|---|
| **Class** | `ProjectServiceTest` | `ProjectControllerTest` | `ProjectSecurityTest` | `ProjectIntegrationTest` |
| **Annotation** | `@ExtendWith(MockitoExtension.class)` | `@WebMvcTest(ProjectController.class)` | `@WebMvcTest` + `@Import(SecurityConfig.class)` | `@SpringBootTest` + `@AutoConfigureMockMvc` |
| **Spring Context?** | ❌ None (Pure Java) | ⚠️ Partial (Web layer only) | ⚠️ Partial (Web + Security) | ✅ Full ApplicationContext |
| **Mocks Used?** | `@Mock` (Repository) | `@MockitoBean` (Service) | `@MockitoBean` (Service, Jwt) | ❌ Zero Mocks |
| **Database?** | None | None | None | Real PostgreSQL in Docker |
| **Speed** | ⚡ Blazing (0.05s) | 🚀 Fast (~1-2s) | 🚀 Fast (~1-2s) | 🐢 Slower (~4-5s boot) |

---

## 💻 How to Run the Tests

Run individual test suites from the `backend` folder:

```powershell
# Run Level 1 Service Unit Tests
mvn test -Dtest=ProjectServiceTest

# Run Level 2 Controller Slice Tests
mvn test -Dtest=ProjectControllerTest

# Run Level 2.5 Security Tests
mvn test -Dtest=ProjectSecurityTest

# Run Level 3 End-to-End Integration Tests (Docker PostgreSQL must be running)
mvn test -Dtest=ProjectIntegrationTest

# Run ALL tests in the entire project
mvn test
```
