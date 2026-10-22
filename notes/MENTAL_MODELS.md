# Spring Boot Deep Mental Models 🧠⚡

> [!NOTE]
> **The Deep Revision Architecture:**  
> This guide is engineered for **instant visual recall** AND **deep mechanical understanding**. Each model contains:
> 1. **The Loud Visual Hook & ASCII Architecture** (What it looks like).
> 2. **Under The Hood (Memory, Threads, & Bytecode)** (What Java & Spring actually do in RAM).
> 3. **The Gotchas & Traps** (What breaks and why).
> 4. **⚡ 30-Second Revision Takeaway** (What to say in an interview).
> 
> *Looking for syntax? Check the [ANNOTATIONS.md](ANNOTATIONS.md) master dictionary.* \| [⬅️ Back to Notes Index](README.md)

---

## 1. The 5-Layer Monolith (The Restaurant & The RAM HashMap) 🍽️

```text
┌────────────────────────────────────────────────────────────────────────┐
│  CUSTOMER (Browser / Postman / Mobile)                                 │
│     │  ▲  HTTP Request (JSON) / HTTP Response (JSON)                   │
│     ▼  │                                                               │
│  1. CONTROLLER (@RestController)  ===> THE WAITER                      │
│     • Handles HTTP verbs, URL routes, extracts @PathVariable / Body.   │
│     • Never contains business calculations or SQL queries!             │
│     │  ▲                                                               │
│     ▼  │                                                               │
│  2. SERVICE (@Service)            ===> THE CHEF                        │
│     • Pure business logic, validations, orchestration, transactions.   │
│     • Completely decoupled from HTTP! Doesn't know what JSON is.       │
│     │  ▲                                                               │
│     ▼  │                                                               │
│  3. REPOSITORY (@Repository)      ===> THE PANTRY MANAGER              │
│     • Talks only to PostgreSQL via Spring Data JPA / Hibernate.        │
│     • Translates Java method calls into optimized SQL queries.         │
│     │  ▲                                                               │
│     ▼  │  JDBC / SQL Queries                                           │
│  4. DATABASE (PostgreSQL)         ===> THE PANTRY SHELVES              │
│                                                                        │
│  --------------------------------------------------------------------  │
│  DATA CARRIERS:                                                        │
│  • ENTITY (@Entity)   = Raw Grocery Ingredients (Raw database columns) │
│  • DTO (ProjectDTO)   = Plated Dish on the Table (Clean, safe JSON)    │
└────────────────────────────────────────────────────────────────────────┘
```

### 🔬 Under The Hood (What Spring Boot Does in Memory):
1. **The ApplicationContext is a `ConcurrentHashMap`:**  
   When Spring starts, it does **not** create a new Service or Controller for every user request. It creates **one single instance (Singleton)** of `ProjectController`, `ProjectService`, and `ProjectRepository`, and stores them in a memory map:
   ```java
   Map<String, Object> singletonObjects = new ConcurrentHashMap<>();
   // singletonObjects.put("projectService", new ProjectService(projectRepository));
   ```
2. **Constructor Injection:**  
   When `ProjectController` is instantiated, Spring looks at its constructor, sees it needs `ProjectService`, looks inside the HashMap, pulls out the `ProjectService` singleton, and passes it into the constructor.
3. **DTO Separation (Mass Assignment Prevention):**  
   If you expose your `@Entity` directly on a Controller, an attacker can send JSON containing fields they shouldn't edit (e.g. `{"id": 1, "role": "ADMIN", "isSuperUser": true}`). By using a **DTO**, you only expose the exact fields the client is permitted to send (`name`, `status`).

### ⚡ 30-Second Revision Takeaway:
* **The Flow:** Request ➔ Controller ➔ Service ➔ Repository ➔ PostgreSQL.
* **Separation of Concerns:** Controller knows HTTP; Service knows Business Logic; Repository knows SQL; Entity models the Database; DTO models the Network JSON.

---

## 2. Global Exception Handling (The Customer Service Desk & AOP) 🛎️

```text
┌────────────────────────────────────────────────────────────────────────┐
│  EXCEPTION OCCURS IN SERVICE OR CONTROLLER                             │
│  (e.g., ResourceNotFoundException, MethodArgumentNotValidException)   │
│                                                                        │
│                    ❌ NO MANUAL TRY/CATCH IN CONTROLLERS               │
│                                   │                                    │
│                                   ▼                                    │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │  @RestControllerAdvice  ===> THE CUSTOMER SERVICE DESK           │  │
│  │                                                                  │  │
│  │  1. Aspect-Oriented Programming (AOP) wraps all Controller calls.│  │
│  │  2. Catches unhandled exceptions bubbling up the call stack.     │  │
│  │  3. Invokes matching @ExceptionHandler method.                   │  │
│  │  4. Returns standardized JSON error response with HTTP status.   │  │
│  │     { "timestamp": "...", "status": 404, "error": "Not Found" }  │  │
│  └──────────────────────────────────────────────────────────────────┘  │
└────────────────────────────────────────────────────────────────────────┘
```

### 🔬 Under The Hood (How Spring Catches Errors):
1. **The DispatcherServlet Try-Catch:**  
   Spring's front controller (`DispatcherServlet`) wraps the execution of every `@RequestMapping` in an internal try-catch block.
2. **HandlerExceptionResolver:**  
   When an exception escapes your controller method, `DispatcherServlet` delegates it to a chain of exception resolvers.
3. **`@RestControllerAdvice`:**  
   Combines `@ControllerAdvice` (interception logic) with `@ResponseBody` (auto-serializing the returned Java error object into JSON via Jackson).

### ⚡ 30-Second Revision Takeaway:
* Never write `try-catch` blocks in Controllers just to format error messages.
* Let exceptions throw freely; the `@RestControllerAdvice` safety net catches them globally and guarantees a consistent error structure for frontend clients.

---

## 3. Spring Security Architecture (The Strict Nightclub & The FilterChain) 🚪🕺

```text
┌────────────────────────────────────────────────────────────────────────┐
│  INCOMING HTTP REQUEST                                                 │
│     │                                                                  │
│     ▼                                                                  │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │  SPRING SECURITY FILTER CHAIN (The Nightclub Entrance)           │  │
│  │                                                                  │  │
│  │  [Filter 1: CorsFilter]            Checks browser origin         │  │
│  │  [Filter 2: CsrfFilter]            Disabled for stateless APIs   │  │
│  │  [Filter 3: JwtAuthFilter] <====== OUR CUSTOM BOUNCER            │  │
│  │     • Checks "Authorization: Bearer <token>"                     │  │
│  │     • Validates cryptographic signature                          │  │
│  │     • Injects user & roles into SecurityContextHolder            │  │
│  │  [Filter 4: UsernamePasswordAuth]  Default Spring login filter   │  │
│  │  [Filter 5: AuthorizationFilter]   Enforces door rules (hasRole) │  │
│  └──────────────────────────────────────────────────────────────────┘  │
│     │                                                                  │
│     ├── IF DENIED ➔ 401 Unauthorized or 403 Forbidden (Immediate halt) │
│     └── IF ALLOWED ➔ Dispatched to DispatcherServlet -> Controller     │
└────────────────────────────────────────────────────────────────────────┘
```

### 🔬 Under The Hood (DelegatingFilterProxy):
1. **Before Spring Even Wakes Up:**  
   Tomcat uses standard Java Servlet Filters. Spring inserts a single bridge called `DelegatingFilterProxy` into Tomcat's filter list.
2. **The SecurityFilterChain Bean:**  
   `DelegatingFilterProxy` delegates all HTTP traffic into Spring's managed `SecurityFilterChain`, where our `JwtAuthenticationFilter` lives.
3. **Order Matters:**  
   We call `.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)` so our JWT scanner runs **before** Spring attempts its default session-cookie check.

---

## 4. Password Security & BCrypt (The One-Way Hash Safe) 🔒🧂

```text
┌────────────────────────────────────────────────────────────────────────┐
│  RAW PASSWORD: "adminPass123"                                          │
│                                                                        │
│                      BCryptPasswordEncoder                             │
│                      [MATHEMATICAL MEAT GRINDER]                       │
│                                   │                                    │
│  1. Generates 16-byte cryptographically random SALT.                   │
│  2. Feeds [Password + Salt] through blowfish cipher 2^10 times.        │
│                                   ▼                                    │
│  DATABASE STORAGE:                                                     │
│  "$2a$10$N9qo8uLOickgx2ZMRZoMye.I... (60 Characters)"                  │
│   ──┬─ ─┬ ──────────────────── ─────────────────────────────           │
│     │   │         │                           │                        │
│  Alg  Cost      Salt                    Cipher Hash                    │
└────────────────────────────────────────────────────────────────────────┘
```

### 🔬 Under The Hood (Why BCrypt Cannot Be Decrypted):
1. **One-Way Trapdoor Function:**  
   BCrypt is mathematically irreversible. Even if a hacker steals the PostgreSQL database, they **cannot decrypt** the hashes.
2. **Verification Logic (`matches`):**  
   When the user logs in later with `"adminPass123"`:
   * BCrypt extracts the **Salt** directly from the stored hash string.
   * Hashes the incoming password using that exact same salt and cost factor.
   * Compares the two hashes. If they match bit-for-bit ➔ Credentials valid!
3. **Slow On Purpose (Work Factor):**  
   BCrypt is deliberately CPU-intensive (cost `10` = 1,024 rounds of hashing) to prevent hardware GPUs from brute-forcing millions of passwords per second.

---

## 5. JWT Anatomy (The Tamper-Proof Cryptographic Wristband) 🎫

```text
┌────────────────────────────────────────────────────────────────────────┐
│  JWT TOKEN STRING:  header.payload.signature                           │
│                                                                        │
│  PART 1: HEADER (Base64Url Encoded JSON)                               │
│  { "alg": "HS256", "typ": "JWT" }                                      │
│                                                                        │
│  PART 2: PAYLOAD / CLAIMS (Base64Url Encoded JSON)                     │
│  { "sub": "shyam_admin", "iat": 1698000000, "exp": 1698036000 }       │
│                                                                        │
│  PART 3: CRYPTOGRAPHIC SIGNATURE (Binary Hash)                         │
│  HMACSHA256(                                                           │
│      base64UrlEncode(header) + "." + base64UrlEncode(payload),         │
│      SECRET_KEY                                                        │
│  )                                                                     │
└────────────────────────────────────────────────────────────────────────┘
```

### 🔬 Under The Hood (Why Tampering Fails):
1. **Base64 is NOT Encryption:**  
   Anyone can decode parts 1 and 2 using `base64 --decode`. Never put sensitive secrets (like passwords or credit cards) in a JWT payload!
2. **The Signature Trap:**  
   If a hacker edits the payload from `"sub": "user"` to `"sub": "admin"`:
   * When the server receives the token, it recalculates the signature using its private `SECRET_KEY`.
   * The calculated signature will **not match** the token's attached signature.
   * `JwtUtil` immediately throws `SignatureException`, and the request is aborted!

---

## 6. Request Lifecycle & `ThreadLocal` (The Tomcat Turnstile) 🧵

How does Spring Security handle 500 simultaneous users without mixing up their roles?

```text
┌────────────────────────────────────────────────────────────────────────┐
│  TOMCAT THREAD POOL                                                    │
│                                                                        │
│  HTTP Request 1 (Admin)  ──> Worker Thread #1 [ThreadLocal Memory Box] │
│                              SecurityContext: User="admin", Role=ADMIN │
│                                                                        │
│  HTTP Request 2 (Guest)  ──> Worker Thread #2 [ThreadLocal Memory Box] │
│                              SecurityContext: null / Anonymous         │
│                                                                        │
│  HTTP Request 3 (User)   ──> Worker Thread #3 [ThreadLocal Memory Box] │
│                              SecurityContext: User="john", Role=USER   │
└────────────────────────────────────────────────────────────────────────┘
```

### 🔬 Under The Hood (`SecurityContextHolder`):
1. **Thread-Isolation:**  
   `SecurityContextHolder.getContext()` uses a Java `ThreadLocal` variable under the hood. A `ThreadLocal` variable provides a separate, isolated memory slot for **each thread**.
2. **Request Clean-Up:**  
   When the request finishes and the thread returns to Tomcat's pool, Spring Security calls `SecurityContextHolder.clearContext()` to ensure the next request that reuses that thread doesn't inherit someone else's identity!

---

## 7. Stateless Architecture & The Amnesiac Server 🧠🚫

```text
┌────────────────────────────────────────────────────────────────────────┐
│  STATEFUL (Old Session-Cookie Way):                                    │
│  • Server stores session in RAM: SessionID #1234 -> User "shyam"       │
│  • Server restarts ➔ ALL USERS LOGGED OUT!                             │
│  • Adding Server #2 ➔ User not found on Server 2 (Needs sticky load    │
│    balancer or shared Redis session store).                            │
├────────────────────────────────────────────────────────────────────────┤
│  STATELESS (Modern JWT Way):                                           │
│  • Server stores ZERO state in RAM (SessionCreationPolicy.STATELESS).  │
│  • Client sends self-contained, cryptographically verifiable token.    │
│  • Server restarts ➔ Zero users affected!                              │
│  • Scale to 50 server instances ➔ Any instance can verify the token!   │
└────────────────────────────────────────────────────────────────────────┘
```

---

## 8. Bean Validation (The Security Check Before the Method) 🛂

```text
┌────────────────────────────────────────────────────────────────────────┐
│  INCOMING JSON: { "name": "", "status": "ACTIVE" }                     │
│     │                                                                  │
│     ▼                                                                  │
│  ProjectController.createProject(@Valid @RequestBody ProjectDTO dto)   │
│     │                                                                  │
│     ├── 1. Jackson deserializes JSON into ProjectDTO instance.         │
│     ├── 2. Hibernate Validator inspects annotations (@NotBlank).       │
│     │                                                                  │
│     ▼ Validation Fails!                                                │
│  Throws MethodArgumentNotValidException BEFORE line 1 of method runs!  │
│     │                                                                  │
│     ▼ Intercepted by @RestControllerAdvice                             │
│  Returns HTTP 400 Bad Request with field error map.                    │
└────────────────────────────────────────────────────────────────────────┘
```

---

## 9. Hibernate ORM, Dirty Checking, & First-Level Cache 🗄️⚡

```text
┌────────────────────────────────────────────────────────────────────────┐
│  SPRING DATA JPA TRANSACTION (@Transactional)                          │
│                                                                        │
│  1. projectRepository.findById(1L)                                     │
│     • Hibernate queries PostgreSQL: SELECT * FROM projects WHERE id=1; │
│     • Loads entity into the FIRST-LEVEL CACHE (Persistence Context).   │
│     • Takes an internal SNAPSHOT of the initial values.                │
│                                                                        │
│  2. project.setStatus("COMPLETED");                                    │
│     • Modifies the Java object in memory.                              │
│                                                                        │
│  3. Transaction Commit (Method Ends):                                  │
│     • DIRTY CHECKING kicks in!                                         │
│     • Hibernate compares current entity state with the snapshot.       │
│     • Detects "status" changed from "ACTIVE" to "COMPLETED".           │
│     • AUTOMATICALLY generates and executes:                            │
│       UPDATE projects SET status='COMPLETED' WHERE id=1;               │
│     • NO NEED TO EXPLICITLY CALL repository.save()!                    │
└────────────────────────────────────────────────────────────────────────┘
```

---

## 10. The Testing Pyramid & Stunt Doubles (Mockito vs MockMvc vs SpringBootTest) 🎬🧪

```text
┌────────────────────────────────────────────────────────────────────────┐
│                          THE TESTING PYRAMID                           │
│                                                                        │
│  Level 3: @SpringBootTest (Whole Restaurant - E2E Integration)         │
│  • Boots entire ApplicationContext, real Hibernate, connects to live   │
│    PostgreSQL in Docker container.                                     │
│  • Tests full round-trip: HTTP -> Filters -> DB -> JSON Response.      │
│  • Speed: Slowest (2-5 seconds). Confidence: Highest (100%).           │
│                                                                        │
│  Level 2: @WebMvcTest (Web Slice - Controller Test)                    │
│  • Boots ONLY Controller + MockMvc. No Service, no Database loaded.    │
│  • Service replaced with @MockitoBean stunt double.                    │
│  • Verifies URLs, HTTP status codes, JSON serialization, and @Valid.   │
│  • Speed: Fast (300-500ms).                                            │
│                                                                        │
│  Level 1: Pure JUnit 5 + Mockito (Unit Test - Service Layer)           │
│  • Zero Spring Boot context loaded. Zero Tomcat.                       │
│  • Uses @Mock (fake repo) and @InjectMocks (real service).             │
│  • Tests business rules, algorithms, and exception logic in memory.    │
│  • Speed: Blazing fast (10-50 milliseconds).                           │
└────────────────────────────────────────────────────────────────────────┘
```

---

## 11. The JVM Lifecycle (`main()` vs `static` vs JUnit Reflection) ⚡🚪

```text
┌────────────────────────────────────────────────────────────────────────┐
│  SCENARIO: Enforcing UTC Timezone Globally                             │
│                                                                        │
│  APPROACH A: Inside main()                                             │
│  public static void main(String[] args) {                              │
│      TimeZone.setDefault(TimeZone.getTimeZone("UTC"));                 │
│      SpringApplication.run(BackendApplication.class, args);            │
│  }                                                                     │
│  ❌ Fails in Automated Tests!                                          │
│  Why? When running 'mvn test', JUnit NEVER calls main()!               │
│  JUnit boots the test context directly via reflection, skipping main() │
│  completely. The JVM keeps Windows' local timezone ("Asia/Calcutta"),  │
│  and Linux PostgreSQL rejects the connection!                          │
├────────────────────────────────────────────────────────────────────────┤
│  APPROACH B: Inside static { ... } block                               │
│  static {                                                              │
│      TimeZone.setDefault(TimeZone.getTimeZone("UTC"));                 │
│  }                                                                     │
│  ✅ 100% Reliable!                                                     │
│  Why? In Java, static blocks execute upon CLASS LOADING (<clinit>      │
│  bytecode phase), guaranteed to execute before ANY method (main OR     │
│  JUnit reflection) can ever run.                                       │
└────────────────────────────────────────────────────────────────────────┘
```

---

## 12. OpenAPI 3 vs Swagger UI (The Machine Blueprint vs The Visual Kiosk) 📢🍔

```text
┌────────────────────────────────────────────────────────────────────────┐
│  THE TOUCHSCREEN KIOSK (Swagger UI: /swagger-ui/index.html)            │
│                                                                        │
│  [🔒 VIP CARD SCANNER] -> OpenApiConfig.java                           │
│  (Scan your JWT badge once -> Unlocks VIP burgers)                     │
│                                                                        │
│  🍔 CATEGORY 1: "PROJECTS"  ---------------------> @Tag                │
│                                                                        │
│     [POST /api/projects] ------------------------> @Operation          │
│     "Create a new DevOps Project"                                      │
│                                                                        │
│     📸 Sample Dish Picture ----------------------> @Schema             │
│     { "name": "Kubernetes Cluster", "status": "ACTIVE" }               │
│                                                                        │
│     ⚠️ Receipt Status Codes ---------------------> @ApiResponse        │
│     • 200: "Burger cooked and served!"                                 │
│     • 400: "Invalid ingredient selected"                               │
│     • 403: "VIP only! You didn't swipe badge"                          │
│                                                                        │
│     [TRY IT OUT -> EXECUTE] = Live kitchen prepares it right now!      │
└────────────────────────────────────────────────────────────────────────┘
```

### 🔬 Under The Hood (SpringDoc Reflection):
1. **At Application Startup:**  
   The `springdoc-openapi` library scans the classpath for classes annotated with `@RestController`.
2. **Annotation Extraction:**  
   It reads methods, parameters, and types via Java reflection, inspecting `@Operation`, `@ApiResponse`, and `@Schema`.
3. **Spec Generation:**  
   It compiles all discovered routes into an OpenAPI 3 compliant JSON document exposed at `/v3/api-docs`.
4. **Visual Rendering:**  
   Swagger UI is a pre-packaged single-page JavaScript application embedded in the JAR that fetches `/v3/api-docs` and dynamically renders the interactive HTML page.

---

## 13. Same-Origin Policy & CORS (The Browser Police & The Evil Hacker Tab) 👮🌐

```text
┌────────────────────────────────────────────────────────────────────────┐
│  THE EVIL HACKER TAB NIGHTMARE:                                        │
│  1. In Tab 1: You log into https://my-bank.com (Browser saves cookies) │
│  2. In Tab 2: You visit https://evil-site.com                          │
│  3. Evil site runs: fetch("https://my-bank.com/api/transfer?amount=1M")│
│  If browsers didn't block cross-origin calls, your money is stolen!    │
├────────────────────────────────────────────────────────────────────────┤
│  THE BROWSER POLICE (Same-Origin Policy - SOP):                        │
│  • Chrome/Edge strictly FORBIDS JavaScript on Origin A (localhost:3000)│
│    from reading data from Origin B (localhost:8080).                   │
├────────────────────────────────────────────────────────────────────────┤
│  CORS (The VIP Guest List Header):                                     │
│  • Backend tells the browser: "I trust localhost:3000!"                │
│  • Header: Access-Control-Allow-Origin: http://localhost:3000          │
│  • Why Postman never gets CORS errors: Postman is NOT a web browser!   │
└────────────────────────────────────────────────────────────────────────┘
```

### 🔬 Under The Hood (The Preflight Handshake):
1. **Origin Definition:**  
   An Origin is `Protocol + Domain + Port`. If any of the three differ (`http://localhost:3000` vs `http://localhost:8080`), they are **Cross-Origin**.
2. **The Preflight `OPTIONS` Request:**  
   Before sending a `POST`, `PUT`, or `DELETE`, the browser automatically sends a lightweight HTTP `OPTIONS` request to the backend:
   ```http
   OPTIONS /api/projects
   Origin: http://localhost:3000
   Access-Control-Request-Method: POST
   ```
3. **The Backend Response:**  
   If Spring Boot has CORS configured, it replies:
   ```http
   HTTP/1.1 200 OK
   Access-Control-Allow-Origin: http://localhost:3000
   Access-Control-Allow-Methods: GET, POST, DELETE, OPTIONS
   ```
4. **The Green Light:**  
   The browser receives the header, sees `http://localhost:3000` is permitted, and immediately fires the real `POST /api/projects` request!
