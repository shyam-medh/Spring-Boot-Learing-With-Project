# User Registration & Role-Based Access Control (RBAC)

---

## 1. Authentication vs Authorization (The Golden Difference)

These two words sound similar, but they solve two completely different problems:

| Concept | The Question It Asks | The Real-World Metaphor | How Spring Handles It |
|---|---|---|---|
| **Authentication (AuthN)** | *"Who are you?"* | Showing your ID card at the airport gate. | Username + Password + JWT Token. |
| **Authorization (AuthZ)** | *"What are you allowed to do?"* | Showing your First-Class ticket to enter the VIP Lounge. | Roles (`ROLE_USER`, `ROLE_ADMIN`) + RBAC rules. |

---

## 2. Feature 1: User Registration (`POST /api/auth/register`)

Previously, the only user in our database was `admin` seeded on startup. A real application needs a public registration endpoint.

### The 4-Step Registration Flow:
```text
[Client] ──► POST /api/auth/register { "username": "john", "password": "password123", "role": "USER" }
                 │
                 ▼
          [AuthController.java]
                 │
                 ├─► 1. Check DB: Is "john" already taken?
                 │      If YES ➔ throw IllegalArgumentException ("Username is already taken!")
                 │      (Caught by GlobalExceptionHandler ➔ returns clean 400 Bad Request)
                 │
                 ├─► 2. Hash password: passwordEncoder.encode("password123")
                 │      Produces safe irreversible hash: "$2a$10$abc..."
                 │
                 ├─► 3. Save to PostgreSQL: new User("john", hashedPw, "USER")
                 │
                 └─► 4. Issue JWT immediately: jwtUtil.generateToken("john")
                        Client gets HTTP 200 OK + JWT Token (automatically logged in!)
```

### Why we used `RegisterRequest` (Inbound DTO):
Never accept raw `@Entity User` in a controller. If we accepted `User` directly, a malicious user could pass `"id": 1` or `"role": "SUPER_ADMIN"` to overwrite records or escalate privileges (*Mass Assignment Vulnerability*). An Inbound DTO strictly limits what the client is permitted to send.

---

## 3. Feature 2: Role-Based Access Control (RBAC)

Once users have roles, we configure the access rules in `SecurityConfig.java`.

### How Roles Work Internally in Spring:
1. In `User.java`, our role string is stored as `"ADMIN"` or `"USER"`.
2. In `CustomUserDetailsService.java`, we call:
   ```java
   User.builder()
       .username(user.getUsername())
       .password(user.getPassword())
       .roles(user.getRole()) // Automatically prefixes "ROLE_" ➔ "ROLE_ADMIN"
       .build();
   ```
3. In `SecurityConfig.java`, `.hasRole("ADMIN")` automatically checks for `"ROLE_ADMIN"`.

### The Door Rules in `SecurityConfig.java`:
```java
.authorizeHttpRequests(auth -> auth
    // 1. Completely Public (No token needed)
    .requestMatchers("/api/auth/**").permitAll()

    // 2. Both USER and ADMIN can view projects
    .requestMatchers(HttpMethod.GET, "/api/projects/**").hasAnyRole("USER", "ADMIN")

    // 3. ONLY ADMIN can create or delete projects
    .requestMatchers(HttpMethod.POST, "/api/projects/**").hasRole("ADMIN")
    .requestMatchers(HttpMethod.DELETE, "/api/projects/**").hasRole("ADMIN")

    // 4. Any other route requires being logged in
    .anyRequest().authenticated()
)
```

---

## 4. The Real-World Bug & Fix: Safe Token Parsing

### The Gotcha:
When the server restarts, `JwtUtil` generates a new secret signing key in memory. If a client (or Postman) sends a request with an **old or expired token** in the `Authorization` header to a **public endpoint** (like `/api/auth/register`):
* `jwtUtil.extractUsername(jwt)` threw an unhandled `SignatureException`.
* Spring Security's filter caught the unhandled crash and returned **`403 Forbidden`**, even on a public route!

### The Production Fix in `JwtAuthenticationFilter.java`:
```java
String jwt = authHeader.substring(7);
String username = null;

try {
    username = jwtUtil.extractUsername(jwt);
} catch (Exception e) {
    // If the token is invalid, expired, or signature mismatched:
    // Do NOT crash the request! Just let it pass unauthenticated.
    filterChain.doFilter(request, response);
    return;
}
```
**The Rule:** If a token is corrupted or expired, the filter doesn't blow up — it simply leaves the user unauthenticated and lets the request proceed. If the route is public, it succeeds; if the route is private, Spring Security safely denies access later.

---

## 5. Summary of What We Verified in Postman:

1. **Registration:** `POST /api/auth/register` with `john` ➔ **`200 OK`** + token issued.
2. **Duplicate Check:** Calling it again with `john` ➔ **`400 Bad Request`** with error message.
3. **RBAC Read:** John calling `GET /api/projects` ➔ **`200 OK`** (Allowed).
4. **RBAC Delete (User):** John calling `DELETE /api/projects/1` ➔ **`403 Forbidden`** (Blocked!).
5. **RBAC Delete (Admin):** Admin calling `DELETE /api/projects/1` ➔ **`200 OK`** (Allowed!).
