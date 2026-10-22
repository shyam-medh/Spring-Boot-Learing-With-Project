# The Master Spring Boot Annotations Cheatsheet

> **One Single File to Rule Them All.**  
> 💡 **Tip:** Click any phase dropdown below to toggle its contents. You can keep only what you're currently studying open to read easily without clutter!

---

## 📌 Quick Navigation
* [📦 1. Component Scanning & Beans (Core Spring)](#1-component-scanning--beans-core-spring)
* [🌐 2. Web & REST Controllers (Handling HTTP Requests)](#2-web--rest-controllers-handling-http-requests)
* [🗄️ 3. Database & JPA (Hibernate Entities)](#3-database--jpa-hibernate-entities)
* [🛡️ 4. Validation (Checking User Input)](#4-validation-checking-user-input)
* [🚨 5. Error Handling (Customer Service Desk)](#5-error-handling-customer-service-desk)
* [🔒 6. Spring Security (The Bouncer & Rulebook)](#6-spring-security)
* [🧪 7. Testing (JUnit 5 & Mockito)](#7-testing-junit-5--mockito)
* [📋 8. Master Quick Reference Table](#8-master-quick-reference-table)

---

<details id="1-component-scanning--beans-core-spring">
<summary><h3><a id="1-component-scanning--beans-core-spring"></a>📦 1. Component Scanning & Beans (Core Spring)</h3></summary>

These annotations tell Spring: *"Manage this object for me in the Application Context (The Container)."*

### `@SpringBootApplication`
* **Where:** Above the `BackendApplication` class.
* **What it does:** The main ignition key of the entire project. Combines 3 critical annotations:
  1. `@Configuration`: Marks the class as a bean configuration source.
  2. `@EnableAutoConfiguration`: Automatically configures dependencies (Tomcat, Jackson, DataSource) based on your `pom.xml`.
  3. `@ComponentScan`: Scans the current package and all sub-packages for Spring beans (`@Component`, `@Service`, `@Repository`, `@RestController`).

### `@Component`
* **Where:** Above any general helper utility class (e.g., `JwtUtil`, `JwtAuthenticationFilter`).
* **What it does:** The generic archetype. Tells Spring: *"Create a Bean (singleton instance) of this class and hold it in the container so other classes can inject it."*

### `@Service`
* **Where:** Above a Service class (e.g., `ProjectService`, `CustomUserDetailsService`).
* **What it does:** A specialized `@Component` designating that this class contains **Business Logic** (The Chef).

### `@Repository`
* **Where:** Above a Repository interface or class (e.g., `ProjectRepository`).
* **What it does:** A specialized `@Component` designating that this class interacts with the **Database** (The Pantry Manager). It also automatically translates low-level JDBC/SQL database exceptions into Spring's unified `DataAccessException` hierarchy.

### `@Configuration`
* **Where:** Above a setup or settings class (e.g., `SecurityConfig`, `DataInitializer`).
* **What it does:** Tells Spring: *"This class is not business logic; it is a factory that defines custom Beans for the application."*

### `@Bean`
* **Where:** Above a **method** inside a `@Configuration` class.
* **What it does:** Tells Spring: *"Execute this method on startup, take whatever object it returns, and register it as an official Spring Bean in the Application Context."*
* **Example:**
  ```java
  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }
  ```

### `@Autowired`
* **Where:** Above a constructor, field, or setter.
* **What it does:** Tells Spring: *"Find a matching Bean from the Application Context and automatically inject it here."*
* **Best Practice Tip:** In modern Spring Boot, if a class has **only one constructor**, Spring automatically injects dependencies **without** needing `@Autowired`. Constructor injection without the annotation is preferred because it makes unit testing easier and enforces immutability (`final` fields).

### `@Value("${property.name}")`
* **Where:** Above a class field.
* **What it does:** Injects configuration values from `application.properties` or environment variables into your Java variables.
* **Example:**
  ```java
  @Value("${jwt.secret:defaultSecretKey12345678901234567890}")
  private String secretKey;
  ```

</details>

---

<details id="2-web--rest-controllers-handling-http-requests">
<summary><h3><a id="2-web--rest-controllers-handling-http-requests"></a>🌐 2. Web & REST Controllers (Handling HTTP Requests)</h3></summary>

These annotations live in the **Controller Layer** (The Waiters) to accept, parse, and respond to HTTP traffic.

### `@RestController`
* **Where:** Above a Controller class (e.g., `ProjectController`, `AuthController`).
* **What it does:** A combo of `@Controller` + `@ResponseBody`. Tells Spring: *"This class listens to incoming HTTP requests, and whatever Java object its methods return should be automatically serialized into JSON and written into the HTTP Response Body."*

### `@RequestMapping("/api/projects")`
* **Where:** Above a Controller class (or individual method).
* **What it does:** Sets the **base URL path** for all methods declared inside this class.
* **Example:** If the class has `@RequestMapping("/api/projects")`, an endpoint inside with `@GetMapping` will listen on `/api/projects`.

### `@GetMapping` / `@PostMapping` / `@PutMapping` / `@DeleteMapping`
* **Where:** Above individual methods in a Controller.
* **What they do:** Map specific HTTP request methods to Java methods:
  * `@GetMapping` ➔ Reading data (`GET`)
  * `@PostMapping` ➔ Creating data (`POST`)
  * `@PutMapping("/{id}")` ➔ Updating existing data (`PUT`)
  * `@DeleteMapping("/{id}")` ➔ Deleting data (`DELETE`)

### `@RequestBody`
* **Where:** On a method parameter in a Controller.
* **What it does:** Tells Spring: *"Read the raw JSON body sent by the client, run Jackson to parse it, and convert it into this Java object."*
* **Example:**
  ```java
  @PostMapping
  public String create(@RequestBody ProjectDTO project) { ... }
  ```

### `@PathVariable("id")`
* **Where:** On a method parameter in a Controller.
* **What it does:** Grabs dynamic variables embedded directly inside the URL path.
* **Example:**
  ```java
  // If the user visits GET /api/projects/5
  @GetMapping("/{id}")
  public ProjectDTO getById(@PathVariable("id") Long id) {
      // id will automatically be 5!
  }
  ```

### `@RequestParam("status")`
* **Where:** On a method parameter in a Controller.
* **What it does:** Grabs query parameters appended to the URL (`?status=ACTIVE`).
* **Example:**
  ```java
  // URL: GET /api/projects?status=ACTIVE
  @GetMapping
  public List<ProjectDTO> search(@RequestParam("status") String status) { ... }
  ```

### `@RequestHeader("Authorization")`
* **Where:** On a method parameter in a Controller.
* **What it does:** Extracts an HTTP header directly from the request.
* **Example:**
  ```java
  @GetMapping("/me")
  public String getCurrentUser(@RequestHeader("Authorization") String authHeader) { ... }
  ```

</details>

---

<details id="3-database--jpa-hibernate-entities">
<summary><h3><a id="3-database--jpa-hibernate-entities"></a>🗄️ 3. Database & JPA (Hibernate Entities)</h3></summary>

These annotations live in the **Entity Layer** to tell Hibernate how to map Java classes to PostgreSQL tables.

### `@Entity`
* **Where:** Above an Entity class (e.g., `Project`, `User`).
* **What it does:** Tells Spring & Hibernate: *"This is a JPA Entity. Generate and manage a corresponding database table for this class."*

### `@Table(name = "users")`
* **Where:** Above an Entity class (below `@Entity`).
* **What it does:** Customizes the exact SQL table name (e.g., avoids reserved keywords like `"user"` by naming the table `"users"`).

### `@Id`
* **Where:** Above the primary key field (e.g., `Long id`).
* **What it does:** Designates this field as the **Primary Key (`PRIMARY KEY`)** of the database table.

### `@GeneratedValue(strategy = GenerationType.IDENTITY)`
* **Where:** Right below `@Id`.
* **What it does:** Tells PostgreSQL: *"Use an auto-incrementing identity sequence (1, 2, 3...) whenever a new row is inserted."*

### `@Column(name = "...", nullable = false, unique = true)`
* **Where:** Above an Entity field.
* **What it does:** Customizes column properties in the database (e.g. `unique = true` for usernames, `nullable = false` to enforce SQL `NOT NULL` constraints).

### `@Transient`
* **Where:** Above a field in an Entity class.
* **What it does:** Tells Hibernate: *"Do NOT create a column in the database for this field. It is purely a temporary in-memory calculation in Java."*

</details>

---

<details id="4-validation-checking-user-input">
<summary><h3><a id="4-validation-checking-user-input"></a>🛡️ 4. Validation (Checking User Input)</h3></summary>

These annotations live in **DTOs** and **Controllers** to stop invalid or malicious data before it reaches your business logic.

### `@Valid`
* **Where:** On a `@RequestBody` parameter in a Controller.
* **What it does:** **The Enforcer.** Tells Spring: *"Before executing this method, run all the Bean Validation rules declared inside this DTO. If any rule fails, stop immediately and throw `MethodArgumentNotValidException`!"*
* **Example:**
  ```java
  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) { ... }
  ```

### `@NotBlank(message = "...")`
* **Where:** Above a `String` field in a DTO.
* **What it does:** Ensures the string is **not null**, **not empty `""`**, and **not just spaces `"   "`**. Best choice for usernames, passwords, titles, and text inputs.

### `@NotNull(message = "...")`
* **Where:** Above any field in a DTO (numbers, objects, dates).
* **What it does:** Ensures the value is not `null` (allows empty strings or collections, but rejects `null`).

### `@NotEmpty(message = "...")`
* **Where:** Above a `String`, `Collection`, `Map`, or `Array` in a DTO.
* **What it does:** Ensures the value is not `null` and its size/length is greater than 0 (e.g., `""` is rejected, but `"   "` with spaces passes).

#### The Classic Interview Difference:
| Input Value | `@NotNull` | `@NotEmpty` | `@NotBlank` |
|---|---|---|---|
| `null` | ❌ Invalid | ❌ Invalid | ❌ Invalid |
| `""` (Empty) | ✅ Valid | ❌ Invalid | ❌ Invalid |
| `"   "` (Whitespace) | ✅ Valid | ✅ Valid | ❌ Invalid |
| `"DevOpsHub"` | ✅ Valid | ✅ Valid | ✅ Valid |

### `@Size(min = 2, max = 50, message = "...")`
* **Where:** Above a `String` or Collection in a DTO.
* **What it does:** Enforces length constraints (minimum and maximum character count).

### `@Min(...)` / `@Max(...)`
* **Where:** Above a numerical field (Integer, Long, Double).
* **What it does:** Enforces numerical boundaries (e.g., `@Min(18)` for minimum age).

### `@Email(message = "...")`
* **Where:** Above a String field in a DTO.
* **What it does:** Checks that the string matches standard email formatting (contains `@`, valid domain).

</details>

---

<details id="5-error-handling-customer-service-desk">
<summary><h3><a id="5-error-handling-customer-service-desk"></a>🚨 5. Error Handling (Customer Service Desk)</h3></summary>

These annotations live in your **Exception Handler** to intercept errors and format clean JSON responses.

### `@RestControllerAdvice`
* **Where:** Above the `GlobalExceptionHandler` class.
* **What it does:** Combines `@ControllerAdvice` + `@ResponseBody`. Marks this class as the **Global Customer Service Desk**. It hovers above all controllers in the entire app, waiting to intercept any uncaught exceptions and convert return values into JSON.

### `@ExceptionHandler(SomeException.class)`
* **Where:** Above a method inside `@RestControllerAdvice`.
* **What it does:** Tells Spring: *"Whenever `SomeException` (or a subclass) is thrown anywhere in the controller layer, funnel it into this method."*
* **Example:**
  ```java
  @ExceptionHandler(ResourceNotFoundException.class)
  public Map<String, String> handleNotFound(ResourceNotFoundException ex) { ... }
  ```

### `@ResponseStatus(HttpStatus.BAD_REQUEST)`
* **Where:** Above an exception handler method (or custom exception class).
* **What it does:** Explicitly sets the HTTP response status code (400, 404, 500) returned to the client.

</details>

---

<details id="6-spring-security">
<summary><h3><a id="6-spring-security"></a><a id="6-spring-security-the-bouncer--rulebook"></a>🔒 6. Spring Security (The Bouncer & Rulebook)</h3></summary>

These annotations configure access control, authentication, and role authorization.

### `@EnableWebSecurity`
* **Where:** Above `SecurityConfig`.
* **What it does:** Tells Spring: *"Disable default auto-configured security. I am taking over the security rules."*

### `@EnableMethodSecurity`
* **Where:** Above `SecurityConfig`.
* **What it does:** Activates method-level security so you can use annotations like `@PreAuthorize` on individual controller or service methods.

### `@PreAuthorize("hasRole('ADMIN')")`
* **Where:** Above a Controller or Service method.
* **What it does:** Evaluates a SpEL (Spring Expression Language) expression before executing the method. If the authenticated user does not have the required role/authority, execution halts and Spring returns a `403 Forbidden`.
* **Example:**
  ```java
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public String deleteProject(@PathVariable Long id) { ... }
  ```

</details>

---

<details id="7-testing-junit-5--mockito">
<summary><h3><a id="7-testing-junit-5--mockito"></a>🧪 7. Testing (JUnit 5 & Mockito)</h3></summary>

These annotations live in `src/test/java/` to verify your code automatically.

### `@Test`
* **Where:** Above a test method (imported from `org.junit.jupiter.api.Test`).
* **What it does:** Tells the JUnit runner: *"This is an automated test method. Execute it when `mvn test` is invoked!"*

### `@ExtendWith(MockitoExtension.class)`
* **Where:** Above a test class.
* **What it does:** Tells JUnit 5: *"Initialize Mockito support in this test class so `@Mock` and `@InjectMocks` annotations work."*

### `@Mock`
* **Where:** Above a dependency field in a unit test.
* **What it does:** **The Stunt Double.** Creates a lightweight, fake mock object of a dependency (e.g., `ProjectRepository`). No real database, network, or external connections are used.
* **Example:**
  ```java
  @Mock
  private ProjectRepository projectRepository;
  ```

### `@InjectMocks`
* **Where:** Above the class being tested.
* **What it does:** Creates a real instance of the target class (e.g., `ProjectService`) and automatically injects all the `@Mock` stunt doubles into its constructor or fields.
* **Example:**
  ```java
  @InjectMocks
  private ProjectService projectService;
  ```

### `@Spy`
* **Where:** Above a field in a test class.
* **What it does:** **Partial Mocking.** Creates a wrapper around a real instance. By default, real methods are called unless you explicitly stub them with `when(...)`.

### `@SpringBootTest`
* **Where:** Above an integration test class.
* **What it does:** Boots up the **complete Spring Boot ApplicationContext** (loads all beans, configs, database connections). Used for full integration tests.

### `@WebMvcTest(SomeController.class)`
* **Where:** Above a Controller test class.
* **What it does:** Slices the application to load **only the Web Layer** (Controllers, Filters, Converters) without booting the full database or services. Used together with `MockMvc` for fast HTTP testing.
* **Spring Boot 4 Package:** `import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;`

### `@AutoConfigureMockMvc(addFilters = false)`
* **Where:** Above a `@WebMvcTest` class.
* **What it does:** Automatically configures the `MockMvc` instance. Setting `addFilters = false` bypasses security filters so you can test HTTP routing, JSON mapping, and validation in complete isolation.
* **Spring Boot 4 Package:** `import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;`

### `@MockitoBean` (Spring Boot 4 / 3.4+) / `@MockBean` (Legacy)
* **Where:** Above a field in a `@WebMvcTest` or `@SpringBootTest` class.
* **What it does:** Injects a Mockito mock directly into the **Spring ApplicationContext**, replacing any real bean of that type.
* **Spring Boot 4 Note:** In Spring Boot 4, the legacy `@MockBean` was **completely removed** and replaced by `@MockitoBean` from core Spring Framework.
* **Import:** `import org.springframework.test.context.bean.override.mockito.MockitoBean;`
* **Key Difference from `@Mock`:**
  * `@Mock` = Pure Mockito unit test (fast, no Spring Context loaded).
  * `@MockitoBean` = Spring test (the mock lives inside Spring's ApplicationContext).

### `@BeforeEach` / `@AfterEach`
* **Where:** Above helper setup/teardown methods in a test class.
* **What they do:**
  * `@BeforeEach`: Executes before **every single** `@Test` method (ideal for resetting state or dummy objects).
  * `@AfterEach`: Executes after **every single** `@Test` method (ideal for cleanup).

### `@DisplayName("Descriptive Test Name")`
* **Where:** Above a test method or test class.
* **What it does:** Replaces the default method name in test reports with a clean, human-readable sentence.

### `@WithMockUser(username = "admin", roles = {"ADMIN"})`
* **Where:** Above a test method or test class.
* **What it does:** **The Fake VIP Badge.** Populates Spring Security's `SecurityContextHolder` with an authenticated user having the specified username and roles. Allows testing protected endpoints without generating real JWT tokens or calling login endpoints!
* **Import:** `import org.springframework.security.test.context.support.WithMockUser;`
* **Dependency:** Requires `spring-security-test`.

### `@WithAnonymousUser`
* **Where:** Above a test method.
* **What it does:** Explicitly simulates an unauthenticated guest visitor to verify that secured endpoints reject anonymous requests with `401 Unauthorized` or `403 Forbidden`.
* **Import:** `import org.springframework.security.test.context.support.WithAnonymousUser;`

</details>

---

<details id="8-master-quick-reference-table">
<summary><h3><a id="8-master-quick-reference-table"></a>📋 8. Master Quick Reference Table</h3></summary>

| Goal | Annotation | Layer / File | Primary Purpose |
|---|---|---|---|
| **App Ignition** | `@SpringBootApplication` | `BackendApplication` | Root configuration, auto-config, component scan |
| **General Bean** | `@Component` | Helpers (`JwtUtil`, `Filter`) | Register generic Spring-managed bean |
| **Business Bean** | `@Service` | Services (`ProjectService`) | Register business logic bean |
| **Data Bean** | `@Repository` | Repositories | Register DB access bean & translate exceptions |
| **Custom Factory**| `@Configuration` | Config classes | Factory defining `@Bean` methods |
| **Factory Method** | `@Bean` | Inside `@Configuration` | Return custom object to register as a Bean |
| **Dependency Injection** | `@Autowired` | Constructors / Fields | Inject bean from ApplicationContext |
| **Properties** | `@Value` | Fields | Read property from `application.properties` |
| **REST Controller** | `@RestController` | Controllers | Accept HTTP requests & serialize return to JSON |
| **Route Prefix** | `@RequestMapping` | Controllers | Base URL path mapping |
| **HTTP Verbs** | `@GetMapping`, `@PostMapping`, etc. | Controller methods | Map GET, POST, PUT, DELETE requests |
| **Parse JSON** | `@RequestBody` | Controller params | Convert request JSON body to Java DTO |
| **URL Variable** | `@PathVariable` | Controller params | Extract `{id}` from URL path |
| **Query Param** | `@RequestParam` | Controller params | Extract `?status=...` query param |
| **Header** | `@RequestHeader` | Controller params | Extract HTTP header (e.g., `Authorization`) |
| **SQL Table** | `@Entity` | Entities | Map class to relational database table |
| **Table Name** | `@Table` | Entities | Specify customized SQL table name |
| **Primary Key** | `@Id` | Entity field | Designate primary key column |
| **Auto Increment**| `@GeneratedValue` | Entity field | Auto-increment sequence in DB |
| **Column Setting**| `@Column` | Entity field | Custom constraints (`nullable`, `unique`) |
| **Ignore Field** | `@Transient` | Entity field | Ignore field during DB persistence |
| **Run Validator** | `@Valid` | Controller params | Enforce DTO validation before method runs |
| **Not Blank Text**| `@NotBlank` | DTO String field | Disallows `null`, `""`, and whitespace `"   "` |
| **Not Null** | `@NotNull` | DTO fields | Disallows `null` |
| **Not Empty** | `@NotEmpty` | DTO Strings/Lists | Disallows `null` and length 0 |
| **Length Check** | `@Size` | DTO Strings/Lists | Enforces min/max length |
| **Email Check** | `@Email` | DTO String field | Validates email syntax |
| **Catch Errors** | `@RestControllerAdvice` | Global Exception Handler | Intercept uncaught controller exceptions |
| **Error Method** | `@ExceptionHandler` | Handler methods | Route specific exception to method |
| **Status Code** | `@ResponseStatus` | Handler methods | Set HTTP status (400, 404, 500) |
| **Security Setup**| `@EnableWebSecurity` | `SecurityConfig` | Take over security filter chain rules |
| **Method Security**| `@EnableMethodSecurity`| `SecurityConfig` | Turn on `@PreAuthorize` method checks |
| **Role Check** | `@PreAuthorize` | Controllers/Services | Restrict method by role (`ADMIN`, `USER`) |
| **Test Method** | `@Test` | Test classes | Designate executable JUnit test |
| **Mockito Setup** | `@ExtendWith(MockitoExtension.class)` | Test classes | Enable Mockito in JUnit 5 |
| **Mock Dependency**| `@Mock` | Test classes | Pure Mockito stunt double (no DB) |
| **Inject Mocks** | `@InjectMocks` | Test classes | Real class with injected `@Mock` stunt doubles |
| **Partial Mock** | `@Spy` | Test classes | Real object with selective method stubbing |
| **Context Test** | `@SpringBootTest` | Integration tests | Full ApplicationContext boot |
| **Web Slice Test**| `@WebMvcTest` | Controller tests | Fast web-layer test with MockMvc |
| **Context Mock** | `@MockBean` / `@MockitoBean` | Integration tests | Replace Spring bean with mock in context |
| **Lifecycle** | `@BeforeEach` / `@AfterEach` | Test classes | Run before/after every test method |
| **Test Title** | `@DisplayName` | Test classes/methods | Clean human-readable name in reports |
| **Fake Auth User** | `@WithMockUser` | Test methods/classes | Simulate user with roles (`ADMIN`/`USER`) for security tests |

</details>
