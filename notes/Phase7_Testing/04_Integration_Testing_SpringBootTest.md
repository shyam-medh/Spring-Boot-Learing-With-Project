# Phase 7.4: End-to-End Integration Testing with `@SpringBootTest`

---

## 1. What is an Integration Test?

In previous phases, we tested individual components using **stunt doubles**:
* `ProjectServiceTest`: Tested the Chef with a fake Pantry (`@Mock ProjectRepository`).
* `ProjectControllerTest`: Tested the Waiter with a fake Chef (`@MockitoBean ProjectService`).
* `ProjectSecurityTest`: Tested the Bouncer with fake badges.

**`@SpringBootTest` is the Grand Opening of the Restaurant:**
* **Zero Mocks:** No `@Mock` and no `@MockitoBean`.
* Spring boots the **entire real application**: Tomcat context, Security filters, Controllers, Services, Repositories, and the real PostgreSQL database connection!

```text
┌────────────────────────────────────────────────────────────────────────┐
│                          @SpringBootTest                               │
│                   (FULL ENGINE IGNITION - ZERO MOCKS)                  │
│                                                                        │
│   MockMvc ──────► Real SecurityFilter ──────► Real ProjectController   │
│ (Fake Postman)   (JwtAuthenticationFilter)        (The Waiter)         │
│                                                        │               │
│                                                        ▼               │
│                                               Real ProjectService      │
│                                                   (The Chef)           │
│                                                        │               │
│                                                        ▼               │
│                                               Real ProjectRepository   │
│                                                   (The Pantry)         │
│                                                        │               │
│                                                        ▼               │
│                                              PostgreSQL (Docker)       │
└────────────────────────────────────────────────────────────────────────┘
```

---

## 2. The 3-Step Integration Testing Cycle

Every end-to-end test performs a complete round-trip through the whole architecture:

```text
1. ACT: Send HTTP Request (MockMvc)
         │
         ▼
2. ASSERT 1: Verify HTTP Response (status().isOk(), JSON body)
         │
         ▼
3. ASSERT 2: Verify Database (projectRepository.findAll())
```

### Example: Proving a Project Was Actually Saved in PostgreSQL
```java
// 1. Admin sends POST request over HTTP
mockMvc.perform(post("/api/projects")
                .with(user("admin").roles("ADMIN"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProject)))
        .andExpect(status().isOk());

// 2. Query the real PostgreSQL database table directly!
List<Project> rows = projectRepository.findAll();
assertThat(rows).hasSize(1);
assertThat(rows.get(0).getName()).isEqualTo("Kubernetes Migration");
```

---

## 3. Database Hygiene: `@BeforeEach` Cleanup

Because integration tests write real rows to your Docker PostgreSQL database, running tests repeatedly could cause duplicate key errors or stale data.

To keep each test completely independent and fresh, we clean the table before every test:

```java
@BeforeEach
void cleanDatabase() {
    projectRepository.deleteAll(); // Wipes test rows before each test runs
}
```

---

## 4. Master Comparison: The 3 Levels of Testing

| Feature | Level 1: Unit Test | Level 2: Slice Test | Level 3: Integration (E2E) Test |
|---|---|---|---|
| **Class** | `ProjectServiceTest` | `ProjectControllerTest` | `ProjectIntegrationTest` |
| **Annotation** | `@ExtendWith(MockitoExtension.class)` | `@WebMvcTest(ProjectController.class)` | `@SpringBootTest` + `@AutoConfigureMockMvc` |
| **Spring Boot Booted?** | ❌ No (Pure Java) | ⚠️ Partial (Only Web Layer) | ✅ Yes (Full ApplicationContext) |
| **Database Connection?**| ❌ No (Fake `@Mock`) | ❌ No (Fake `@MockitoBean`) | ✅ Yes (Real PostgreSQL in Docker) |
| **Speed** | ⚡ Blazing fast (~0.05s) | 🚀 Fast (~1-2s) | 🐢 Slower (~3-5s full context boot) |
| **What it proves** | Business logic & edge cases | HTTP routing, JSON, & `@Valid` | The entire system works together end-to-end! |

---

## 5. Important Prerequisites & Troubleshooting
1. **Docker Must Be Running:** Because `@SpringBootTest` boots the real `DataSource`, your PostgreSQL container (`devopshub-db`) must be active (`docker start devopshub-db`).
2. **UTC Timezone Static Initializer:** On Windows in regions with legacy timezone identifiers (like India's `Asia/Calcutta`), JUnit forks will default to Windows local time unless UTC is enforced via a `static { TimeZone.setDefault(TimeZone.getTimeZone("UTC")); }` block. (See [02_Timezone_Calcutta.md](../Errors_and_Fixes/02_Timezone_Calcutta.md)).
3. **Explicit Dialect:** Configure `spring.jpa.properties.hibernate.dialect: org.hibernate.dialect.PostgreSQLDialect` in `application.yaml` so Hibernate doesn't fail on initial metadata inspection. (See [01_Hibernate_Dialect.md](../Errors_and_Fixes/01_Hibernate_Dialect.md)).

---

⬅️ **Back to [Phase 7 Testing Guide](README.md)** | **[Notes Master Index](../README.md)**

