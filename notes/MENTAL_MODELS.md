# Spring Boot Mental Models

This document stores the high-level analogies and "mental models" to help you understand *why* Spring Boot works the way it does, without getting bogged down in the exact code syntax.

> **Looking for Annotation syntax?** All technical details, placement rules, and code snippets for annotations are maintained in one single place: [notes/ANNOTATIONS.md](ANNOTATIONS.md).

---

## 1. The 5-Layer Architecture (The Restaurant)
When building a Spring Boot application, think of it like a Restaurant:
* **Controller (`@RestController`)**: The Waiter. Takes the HTTP Request from the user, hands it to the kitchen, and brings back the JSON response.
* **Service (`@Service`)**: The Chef. Contains the actual business logic. Decides what happens with the data.
* **Repository (`@Repository`)**: The Pantry Manager. Only talks to the database to fetch or save ingredients (data).
* **Entity (`@Entity`)**: The Ingredients. The exact representation of a table in the database.
* **DTO (Data Transfer Object)**: The Menu/Plating. How we format the data to look nice for the customer, hiding the raw database structure.

---

## 2. Global Exception Handling (The Customer Service Desk)
Instead of every Waiter (Controller) handling customer complaints (Exceptions) individually, we funnel all complaints to a central Customer Service Desk (`@RestControllerAdvice`). This ensures every error is formatted exactly the same way across the entire application.

---

## 3. Spring Security (The Nightclub)
Spring Security is like a very strict nightclub. You don't need to memorize the exact Java classes, just remember the 3 things you must configure to take over the club:
1. **The Club Rules (`@EnableWebSecurity`)**: You must create a `@Configuration` class to tell Spring: "I am taking over the security rules for this app."
2. **The Decoder Ring (`PasswordEncoder`)**: Spring refuses to store passwords in plain text. You *must* give it a tool (like BCrypt) to scramble and unscramble passwords. If you forget this, Spring crashes.
3. **The Guest List (`UserDetailsService`)**: The Bouncer needs a list of people who are allowed inside. Initially, we use an `InMemoryUserDetailsManager` (a paper list). Later, we connect it to a Database.

---

## 4. Boilerplate Code & Memorization (The Interface Guide)
**Rule: Nobody memorizes Spring Security code line-by-line.** 
When developers need to write complex boilerplate like `CustomUserDetailsService`, they do not do it from memory. They rely on **Interfaces** and **IDE Autocomplete**. 

If you remember the single concept (*"I need to implement UserDetailsService"*), your IDE will force you to create the `loadUserByUsername` method. You then look at the return type (`UserDetails`), and let the IDE's autocomplete (`User.builder()...`) guide you through translating your custom PostgreSQL User into the strict format that Spring Security demands. 
*Memorize concepts, not syntax!*

---

## 5. JWT (The VIP Wristband & The Machine)
**Rule: Do NOT memorize JWT syntax!**
No one memorizes the `io.jsonwebtoken` syntax. We copy and paste the `JwtUtil` class between projects. What you must memorize is the **Anatomy of the Machine** (The 3 Components):

1. **The Ink (The Secret Key):** You must have a cryptographic key that the server uses to sign and read tokens.
2. **The Printer (`generateToken`):** Memorize the word **Builder**. You use `Jwts.builder()` to glue together the Username, Expiration Date, and the Signature (Ink).
3. **The Scanner (`extractUsername`):** Memorize the word **Parser**. You use `Jwts.parserBuilder()` to rip the token apart, check the Signature (Ink), and pull out the Subject (Username).

**Summary:**
* Making a token? -> **Builder**
* Reading a token? -> **Parser**
* Both require? -> **The Secret Key**

---

## 6. How to Explain JWT in an Interview (The 5-Step Story)
When asked *"How does JWT authentication work in your Spring Boot app?"*, use this simple mental flow:
1. **Login:** User sends credentials to `/api/auth/login`. `AuthenticationManager` checks DB using BCrypt.
2. **Wristband Created:** `JwtUtil` builds a signed token with username & expiry date and sends it back.
3. **Turnstile Guard:** On every future request, client attaches `Authorization: Bearer <token>`. Our `JwtAuthenticationFilter` intercepts it.
4. **Token Scanned:** Filter extracts the token, verifies signature and expiry with `JwtUtil`, and loads user from `CustomUserDetailsService`.
5. **VIP Clipboard:** Filter pins the user into `SecurityContextHolder`. Spring sees the user is logged in and unlocks the controller!

---

## 7. The 3-Bucket Mental Model (Never Memorize 8 Files!)
Instead of trying to memorize 8 separate security files, organize them into **3 simple buckets**. Every Spring Boot JWT project in the world follows this exact blueprint:

```text
 ┌────────────────────────────────────────────────────────┐
 │ BUCKET 1: THE USER (Where is the user stored?)         │
 │   - User.java                 (The database table)     │
 │   - UserRepository.java       (The DB queries)         │
 │   - CustomUserDetailsService  (The DB-to-Spring bridge)│
 └────────────────────────────────────────────────────────┘
                            ▼
 ┌────────────────────────────────────────────────────────┐
 │ BUCKET 2: THE TOKEN (How is the wristband handled?)    │
 │   - JwtUtil.java              (The printer & scanner)  │
 │   - JwtAuthenticationFilter   (The bouncer checking it)│
 └────────────────────────────────────────────────────────┘
                            ▼
 ┌────────────────────────────────────────────────────────┐
 │ BUCKET 3: THE RULES (Which doors are open?)            │
 │   - AuthController.java       (The /login endpoint)    │
 │   - AuthRequest / AuthResponse(The input/output boxes) │
 │   - SecurityConfig.java       (The rulebook & filters) │
 └────────────────────────────────────────────────────────┘
```

### The Developer's 3-Step Build Checklist:
1. **Bucket 1:** *"Can Spring load users from my database?"* → Write `User`, `UserRepository`, `CustomUserDetailsService`.
2. **Bucket 2:** *"Can I create and check tokens?"* → Bring in `JwtUtil` and `JwtAuthenticationFilter`.
3. **Bucket 3:** *"Can users log in and are doors protected?"* → Write `AuthController` and configure `SecurityConfig`.

---

## 8. Automated Testing & Mockito (The Movie Stunt Double)
**Rule: Never test everything at once.**
When testing a Service (The Chef), you do not start the database or the web server.

1. **The Stunt Double (`@Mock`):**  
   If an action scene is too dangerous, movie directors hire a stunt double. In Java, when calling a real database is too slow or dangerous, we hire a **Mock** repository.
2. **Scripting the Actor (`when(...).thenReturn(...)`):**  
   You give the stunt double its script: *"When asked for projects, return this dummy list."*
3. **Plugging the Actor in (`@InjectMocks`):**  
   Takes your real Chef (`ProjectService`) and hands it the fake stunt double instead of the real database.
4. **The 3 A's Rhythm (Arrange - Act - Assert):**
   * **Arrange:** Set up dummy data & train the mock.
   * **Act:** Call the method you want to test.
   * **Assert:** Verify the result matches expectations.
5. **Checking Results (JUnit 5 vs AssertJ):**
   * Traditional JUnit 5 uses `assertEquals(expected, actual)`.
   * Modern Spring Boot uses AssertJ `assertThat(actual).isEqualTo(expected)` because it reads like natural English and you can never accidentally flip expected and actual values!

---

## 9. Controller Testing with `MockMvc` (The Doorway & The 4 Buckets)

> **The Golden Rule:**
> *"Use Mockito when you want to test the pure business logic inside a Service or a Helper class, and use MockMvc when you want to test if your endpoints are wired up correctly."*

### 1. The 3 Characters:
* **The Waiter:** `ProjectController` (the class under test).
* **The Fake Postman (`MockMvc`):** Fires in-memory HTTP requests directly into Spring's web pipeline without starting Tomcat.
* **The Stunt Chef (`@MockitoBean`):** Injects a mock `ProjectService` into the Spring Web context to return scripted responses.

### 2. The 4 Import Buckets:
```text
┌────────────────────────────────────────────────────────┐
│  1. The Postman Buttons:   get(), post(), delete()     │
│  2. The Inspectors:        status(), jsonPath()        │
│  3. The Chef Stunt Tools:  when(), doNothing()         │
│  4. The Translator:        ObjectMapper (Java <-> JSON)│
└────────────────────────────────────────────────────────┘
```

### 3. Why Static Imports (`import static`)?
In Java tests, static imports allow code to read like natural English:  
Instead of `MockMvcRequestBuilders.get(...)` and `MockMvcResultMatchers.status()`, you just write `get(...)` and `status().isOk()`.

> **Developer Secret:** Nobody types these imports from memory! Type `get("/api/...")`, press **`Alt + Enter`**, and let the IDE auto-import it.

### 4. The 3-Line Rhythm:
```java
mockMvc.perform(get("/api/projects"))                         // 1. ACT (Send request)
       .andExpect(status().isOk())                            // 2. ASSERT (Status code)
       .andExpect(jsonPath("$[0].name").value("DevOps"));    // 3. ASSERT (Inspect JSON)
```

### 5. Why Security Dummy Mocks Exist in Slice Tests:
Even with `@WebMvcTest`, Spring Security's config awakens and looks for its constructor dependencies (`JwtAuthenticationFilter`, `JwtUtil`, `CustomUserDetailsService`). We provide dummy `@MockitoBean` stunt doubles for them and add `@AutoConfigureMockMvc(addFilters = false)` so security filters don't block our isolated controller tests.

---

## 10. Security Testing & `@WithMockUser` (The Fake VIP Badge)

When testing Spring Security, we never want to manually call `/api/auth/login` or copy/paste 100-character JWT tokens in Java tests.

* **The Problem:** In [`SecurityConfig.java`](../backend/src/main/java/com/devopshub/backend/config/SecurityConfig.java), endpoints are protected by roles (`ADMIN` vs `USER`).
* **The Hero (`@WithMockUser`):** Directly hands our test runner a **fake VIP badge** with a username and role.
* **The 3 Security Test Cases:**
  1. **Anonymous Stranger (No Badge):** Rebuffed with `401 Unauthorized` or `403 Forbidden`.
  2. **Regular Member (`@WithMockUser(roles = "USER")`):** Accessing `DELETE /api/projects` ➔ Blocked with `403 Forbidden`.
  3. **Admin Member (`@WithMockUser(roles = "ADMIN")`):** Accessing `DELETE /api/projects` ➔ Allowed with `200 OK`.

---

## 11. End-to-End Testing with `@SpringBootTest` (The Whole Restaurant)

**Rule: In `@SpringBootTest`, ALL stunt doubles are fired.**

```text
┌────────────────────────────────────────────────────────┐
│                   THE TESTING PYRAMID                  │
│                                                        │
│  Level 3: @SpringBootTest (Whole Restaurant - E2E)     │
│  - Zero Mocks. Connects to real PostgreSQL in Docker.  │
│  - Tests complete user journey: HTTP -> DB -> HTTP.    │
│                                                        │
│  Level 2.5: Security Testing (The Bouncer)             │
│  - MockMvc + Roles (.with(user(...)).with(csrf()))     │
│  - Verifies 403 Forbidden & 200 OK role access.        │
│                                                        │
│  Level 2: Slice Testing (The Waiter)                   │
│  - @WebMvcTest(ProjectController.class) + MockMvc      │
│  - Verifies URLs, JSON mapping, & @Valid 400 errors.   │
│                                                        │
│  Level 1: Unit Testing (The Chef)                      │
│  - Pure JUnit 5 + Mockito (@Mock, @InjectMocks)        │
│  - Tests business logic, calculations, & exceptions.   │
└────────────────────────────────────────────────────────┘
```

### The 3-Step Verification Cycle in `@SpringBootTest`:
1. **ACT:** Fake Postman (`MockMvc`) fires an HTTP request.
2. **ASSERT 1:** Check the HTTP status and JSON response (`status().isOk()`).
3. **ASSERT 2 (The Golden Check):** Directly query the real PostgreSQL repository (`projectRepository.findAll()`) to verify the data was actually written to the disk!

---

## 12. The `main()` vs `static` Block Testing Trap (The Front Door vs The Circuit Breaker)

* **The Front Door (`main()` method):**
  When a human runs an application (`mvn spring-boot:run` or clicking Run in an IDE), Java enters through the **Front Door (`main()`)**. Any setup code placed inside `main()` executes before Spring Boot starts.
* **The Roof Hatch (JUnit Automated Testing):**
  When running tests (`mvn test`), JUnit **completely skips `main()`**! JUnit enters through the roof via reflection and directly starts the Spring ApplicationContext. Any code trapped inside `main()` **runs zero times**!
* **The Circuit Breaker (`static { ... }` block):**
  A `static` block in Java executes the exact microsecond the `.class` is loaded into memory, regardless of whether you walked through the front door (`main()`) or dropped in through the roof hatch (JUnit).
* **The Golden Rule:**
  Any global environment or JVM-level setting (like `TimeZone.setDefault("UTC")`) that the application **and** its tests need MUST live inside a `static { ... }` block, never just inside `main()`.



