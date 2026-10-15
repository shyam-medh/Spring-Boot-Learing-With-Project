# Spring Boot Mental Models

This document stores the high-level analogies and "mental models" to help you understand *why* Spring Boot works the way it does, without getting bogged down in the exact code syntax.

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
