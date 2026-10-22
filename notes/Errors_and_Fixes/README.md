# DevOpsHub: Master Error & Troubleshooting Catalog 🛠️

This directory serves as the **repository of record** for all real-world errors, exceptions, and pitfalls encountered and solved throughout our Spring Boot journey.

---

## 📋 Master Error Index

| # | Error / Symptom | Phase | Root Cause | Detailed Solution Guide |
|---|---|---|---|---|
| **01** | `Unable to determine Dialect without JDBC metadata` | Phase 3 / 7.4 (Database & Tests) | Docker offline or handshake dropped; missing explicit dialect in `application.yaml`. | [01_Hibernate_Dialect.md](01_Hibernate_Dialect.md) |
| **02** | `unrecognized configuration parameter "TimeZone"` (`Asia/Calcutta`) | Phase 3 / 7.4 (Database & Tests) | Windows Calcutta timezone mismatch with PostgreSQL; `main()` bypassed in JUnit tests. | [02_Timezone_Calcutta.md](02_Timezone_Calcutta.md) |
| **03** | `Status expected:<200> but was:<403>` (MockMvc Security) | Phase 7.3 (Testing) | State-mutating requests (`POST`/`DELETE`) in MockMvc blocked by CSRF filter; unauthenticated requests defaulting to 403. | [03_MockMvc_Security_CSRF_Forbidden.md](03_MockMvc_Security_CSRF_Forbidden.md) |
| **04** | `NoSuchBeanDefinitionException: ObjectMapper` | Phase 7.2 (Testing) | `@WebMvcTest` in Spring Boot 4 does not automatically expose an autowirable `ObjectMapper` bean in slice tests. | [04_MockMvc_ObjectMapper_Injection.md](04_MockMvc_ObjectMapper_Injection.md) |
| **05** | `Cannot resolve symbol 'MockBean'` | Phase 7.1 (Testing) | Legacy `@MockBean` was removed in Spring Boot 4 and replaced by core Spring `@MockitoBean`. | [05_SpringBoot4_MockBean_Removed.md](05_SpringBoot4_MockBean_Removed.md) |
| **06** | `Cannot resolve symbol 'WebMvcTest'` | Phase 7.2 (Testing) | Package location moved to `org.springframework.boot.webmvc.test.autoconfigure.*` under modular starter `spring-boot-starter-webmvc-test`. | [06_SpringBoot4_WebMvcTest_Package_Moved.md](06_SpringBoot4_WebMvcTest_Package_Moved.md) |
| **07** | Route matching anomalies with `SecurityConfig` | Phase 2 / 7 (Routing) | Missing leading forward slash in `@RequestMapping("api/projects")`. | [07_Missing_Leading_Slash_In_RequestMapping.md](07_Missing_Leading_Slash_In_RequestMapping.md) |

---

> [!TIP]
> Whenever you hit an unexpected exception in Spring Boot, search this index first. Most real-world backend bugs trace back to one of these core patterns!

---

⬅️ **Back to [Notes Master Index](../README.md)**
