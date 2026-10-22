# Error: MockMvc Security Tests Failing with 403 Forbidden (CSRF & Default Status)

## The Error Log
```text
[ERROR] ProjectSecurityTest.shouldAllowAdmin_WhenDeletingProject:67 Status expected:<200> but was:<403>
[ERROR] ProjectSecurityTest.shouldRejectAnonymousUser_WhenDeletingProject:49 Status expected:<401> but was:<403>

MockHttpServletRequest:
      HTTP Method = DELETE
      Request URI = /api/projects/1
Handler:
             Type = null
MockHttpServletResponse:
           Status = 403
    Error message = Forbidden
```

---

## What It Means

When testing secured endpoints with `MockMvc` and Spring Security:

1. **The Admin 403 Failure:**  
   Even though the test method was annotated with `@WithMockUser(username = "admin", roles = {"ADMIN"})`, Spring returned `403 Forbidden` and `Handler: Type = null`. The request was blocked at the doorstep by Spring Security's **CSRF Filter** before the Controller was even evaluated!
   
2. **The Anonymous 401 vs 403 Failure:**  
   When a visitor with no credentials calls a protected endpoint, developers often expect `401 Unauthorized`. However, in standard Spring Security without HTTP Basic or form login entry points configured, the default rejection status is **`403 Forbidden`**.

---

## Common Causes

1. **Missing CSRF Token in MockMvc:**  
   For all state-mutating HTTP methods (`POST`, `PUT`, `DELETE`), Spring Security Test expects a CSRF token. If omitted, MockMvc drops the request with `403 Forbidden`.
2. **Expecting 401 instead of 403:**  
   Unless a custom `AuthenticationEntryPoint` is registered, Spring Security's authorization filter throws an `AccessDeniedException` which translates to HTTP `403`.
3. **Missing Leading Slash in `@RequestMapping`:**  
   Writing `@RequestMapping("api/projects")` instead of `@RequestMapping("/api/projects")` can cause route matching anomalies with Spring Security's `.requestMatchers("/api/projects/**")`.
4. **Stateless Security Wiping Thread-Local `@WithMockUser`:**  
   In stateless applications (`SessionCreationPolicy.STATELESS`), there is no HTTP session. Spring's `SecurityContextHolderFilter` resets the thread-local context on each request. Using MockMvc's `.with(user("admin").roles("ADMIN"))` binds the user directly to the HTTP request attributes so it survives the filter chain!

---

## The Fix

### 1. Use `.with(user(...))` and `.with(csrf())`
Import `SecurityMockMvcRequestPostProcessors`:
```java
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

// Anonymous stranger -> Rejected with 403 Forbidden
mockMvc.perform(delete("/api/projects/1").with(csrf()))
        .andExpect(status().isForbidden());

// Regular USER -> Blocked from admin route with 403 Forbidden
mockMvc.perform(delete("/api/projects/1")
                .with(user("shyam").roles("USER"))
                .with(csrf()))
        .andExpect(status().isForbidden());

// ADMIN user -> Allowed through with 200 OK
mockMvc.perform(delete("/api/projects/1")
                .with(user("admin").roles("ADMIN"))
                .with(csrf()))
        .andExpect(status().isOk());
```

### 2. Standardize `@RequestMapping` with a Leading Slash
In your Controller:
```java
// Correct
@RequestMapping("/api/projects")
@RestController
public class ProjectController { ... }
```

### 3. Ensure `spring-security-test` Dependency Exists in `pom.xml`
```xml
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
</dependency>
```
