# Phase 6: Spring Security & JWT Master Guide 🔐

Welcome to the **Phase 6 Spring Security & JWT Guide**! This directory details how to secure a production REST API using stateless JSON Web Tokens (JWT), BCrypt password hashing, Role-Based Access Control (RBAC), and custom security filter chains.

---

## 🎯 Phase Goals & Outcomes
* Understand why HTTP is stateless and why token-based authentication (JWT) is the industry standard for REST APIs.
* Store passwords safely using **BCrypt** with salted one-way hashing.
* Implement a custom `SecurityFilterChain` that disables CSRF for stateless APIs and configures route authorization.
* Build the **3-Bucket Security Model**:
  1. **User Details:** `CustomUserDetailsService` & `UserRepository`
  2. **Token Engine:** `JwtUtil` (creation, signature verification, claims extraction)
  3. **Rules & Filter:** `JwtAuthenticationFilter` & `SecurityConfig`
* Implement Role-Based Access Control (RBAC) separating regular `USER` endpoints from `ADMIN` endpoints.

---

## 📚 Phase 6 Notes & Curriculum

| # | Note | Topic | Key Takeaway |
|---|---|---|---|
| **01** | [01_Basic_Security.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase6_Security/01_Basic_Security.md) | Spring Security Defaults | How Spring Security locks down an application out-of-the-box with default credentials. |
| **02** | [02_Security_Config_and_BCrypt.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase6_Security/02_Security_Config_and_BCrypt.md) | SecurityConfig & BCrypt | Creating `SecurityFilterChain`, configuring public vs private endpoints, and hashing passwords. |
| **03** | [03_Complete_JWT_Security_Story.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase6_Security/03_Complete_JWT_Security_Story.md) | The Complete JWT Story | The definitive guide to JWT tokens, stateless filters, claims, and `SecurityContextHolder`. |
| **04** | [04_Registration_and_RBAC.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase6_Security/04_Registration_and_RBAC.md) | Registration & Roles (RBAC) | User registration, database seeding with `DataInitializer`, and role enforcement (`ROLE_USER`, `ROLE_ADMIN`). |
| **05** | [SPRING_SECURITY_FLOW.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase6_Security/SPRING_SECURITY_FLOW.md) | Visual Security Lifecycle | End-to-end flowchart from HTTP `Authorization: Bearer <token>` to `SecurityContextHolder`. |

---

## 💡 Key Mental Model: The Nightclub Bouncer & VIP Badges
* **The Login Desk (`AuthController`):** You show your ID (username & password). If verified, the club issues you a digitally signed, tamper-proof wristband (**JWT**).
* **The Bouncer (`JwtAuthenticationFilter`):** On every subsequent request, the bouncer checks your wristband. If valid, they stamp your hand (`SecurityContextHolder.setAuthentication(...)`).
* **The VIP Lounge (`SecurityConfig`):** The club manager checks your role. Anyone can view public areas, but only users with the `ADMIN` role badge can enter the VIP room to modify or delete projects (`POST`, `DELETE`).

---

⬅️ **Back to [Notes Master Index](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/README.md)**
