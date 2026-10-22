# Phase 7.3: Testing Spring Security with `@WithMockUser`

---

## 1. Why Test Spring Security?

In Phase 7.2, we tested our Controller with:
```java
@AutoConfigureMockMvc(addFilters = false) // Turned OFF the security bouncer!
```
We did that on purpose to test *only* the controller plumbing (URLs, JSON serialization, and `@Valid`).

**BUT**, in [`SecurityConfig.java`](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/backend/src/main/java/com/devopshub/backend/config/SecurityConfig.java), we configured critical business rules:
* `GET /api/projects/**` ➔ requires `hasAnyRole("USER", "ADMIN")`
* `POST /api/projects/**` ➔ requires `hasRole("ADMIN")`
* `DELETE /api/projects/**` ➔ requires `hasRole("ADMIN")`
* `anyRequest().authenticated()`

If someone accidentally edits `SecurityConfig.java` and opens an admin door to the public, how will we catch it? **With automated security tests!**

---

## 2. The Mental Model: The VIP Badge at the Nightclub Door

```text
┌────────────────────────────────────────────────────────┐
│                   THE SECURITY TEST SCENARIOS          │
│                                                        │
│  Scenario 1: Stranger (No Badge)                       │
│  [MockMvc] ────────► [SecurityFilter] ──► ❌ 401 / 403  │
│                                                        │
│  Scenario 2: Regular Member (@WithMockUser roles="USER")│
│  [MockMvc] ────────► [SecurityFilter] ──► ❌ 403       │
│  (Tries to DELETE project -> Forbidden!)               │
│                                                        │
│  Scenario 3: Admin Member   (@WithMockUser roles="ADMIN")│
│  [MockMvc] ────────► [SecurityFilter] ──► ✅ 200 OK    │
│  (Tries to DELETE project -> Allowed!)                 │
└────────────────────────────────────────────────────────┘
```

* **The Nightclub Bouncer:** Spring Security's `SecurityFilterChain`.
* **The Customer:** `MockMvc` (our Fake Postman).
* **The VIP Badge:** `@WithMockUser`.

---

## 3. The Power of `@WithMockUser`

In real life, to test secured endpoints in Postman, you have to:
1. Call `/api/auth/login` with username & password.
2. Copy the long JWT token string.
3. Paste it into the `Authorization: Bearer <token>` header.

In tests, **`@WithMockUser` skips all that hassle**!  
It directly injects a pre-authenticated user with roles into Spring's `SecurityContextHolder`:

```java
// Simulates an Admin without needing passwords or real JWT tokens:
@WithMockUser(username = "admin", roles = {"ADMIN"})

// Simulates a regular User:
@WithMockUser(username = "shyam", roles = {"USER"})

// Simulates an unauthenticated guest:
@WithAnonymousUser
```

> [!NOTE]
> In Spring Security, `roles = {"ADMIN"}` automatically adds the `ROLE_` prefix under the hood (`ROLE_ADMIN`), perfectly matching `hasRole("ADMIN")` in `SecurityConfig.java`.

---

## 4. The 3 Crucial Security Tests to Write

### Test 1: Unauthenticated Request (Sad Path)
* **Action:** Stranger sends `DELETE /api/projects/1` with no user badge.
* **Expectation:** `status().isUnauthorized()` (`401`) or `status().isForbidden()` (`403`).

### Test 2: Regular USER Tries Admin Action (Role Enforcement)
* **Action:** User with `@WithMockUser(roles = "USER")` sends `DELETE /api/projects/1`.
* **Expectation:** `status().isForbidden()` (`403`). Proves regular users cannot delete data!

### Test 3: ADMIN Performs Admin Action (Happy Path)
* **Action:** User with `@WithMockUser(roles = "ADMIN")` sends `DELETE /api/projects/1`.
* **Expectation:** `status().isOk()` (`200`). Proves admins have full access!

---

## 5. Prerequisite: `spring-security-test` Dependency

To use `@WithMockUser`, we add this dependency to `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
</dependency>
```

---

## 6. The Stateless Security Gotcha: `.with(user(...))` and `.with(csrf())`

When testing a stateless API (`SessionCreationPolicy.STATELESS`), there are two important MockMvc rules to remember:

1. **CSRF Protection on Mutating Requests:**  
   MockMvc automatically enforces CSRF on `POST`, `PUT`, and `DELETE`. Always append `.with(csrf())` so the request isn't blocked with `403` at the door.
2. **Stateless Request Binding with `.with(user(...))`:**  
   In stateless architectures, Spring's `SecurityContextHolderFilter` resets thread-locals on incoming requests. Using `mockMvc.perform(delete(...).with(user("admin").roles("ADMIN")).with(csrf()))` binds the user directly to the HTTP request attributes, guaranteeing it survives the filter chain!

```java
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

// Anonymous stranger -> 403 Forbidden
mockMvc.perform(delete("/api/projects/1").with(csrf()))
        .andExpect(status().isForbidden());

// Regular USER -> 403 Forbidden (Blocked from Admin route)
mockMvc.perform(delete("/api/projects/1")
                .with(user("shyam").roles("USER"))
                .with(csrf()))
        .andExpect(status().isForbidden());

// ADMIN user -> 200 OK (Allowed through!)
mockMvc.perform(delete("/api/projects/1")
                .with(user("admin").roles("ADMIN"))
                .with(csrf()))
        .andExpect(status().isOk());
```

