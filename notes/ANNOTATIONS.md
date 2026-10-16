# The Master Spring Boot Annotations Cheatsheet

> **One Single File to Rule Them All.**  
> Whenever you forget what an `@Annotation` does, where to put it, or why we use it, look here.

---

## Quick Navigation
1. [Component Scanning & Beans (Core Spring)](#1-component-scanning--beans-core-spring)
2. [Web & REST Controllers (Handling HTTP Requests)](#2-web--rest-controllers-handling-http-requests)
3. [Database & JPA (Hibernate Entities)](#3-database--jpa-hibernate-entities)
4. [Validation (Checking User Input)](#4-validation-checking-user-input)
5. [Error Handling (Customer Service Desk)](#5-error-handling-customer-service-desk)
6. [Spring Security](#6-spring-security)
7. [Testing (JUnit 5 & Mockito)](#7-testing-junit-5--mockito)

---

## 1. Component Scanning & Beans (Core Spring)

These annotations tell Spring: *"Manage this object for me in the Application Context (The Container)."*

### `@SpringBootApplication`
* **Where:** Above the `BackendApplication` class.
* **What it does:** The main ignition key of the entire project. Combines 3 things: enables auto-configuration, activates component scanning, and marks the root config class.

### `@Component`
* **Where:** Above any general helper class (e.g., `JwtUtil`, `JwtAuthenticationFilter`).
* **What it does:** Tells Spring: *"Create a Bean (instance) of this class and keep it ready so other classes can inject it."*

### `@Service`
* **Where:** Above a Service class (e.g., `ProjectService`, `CustomUserDetailsService`).
* **What it does:** Exactly like `@Component`, but specifically designates that this class contains **Business Logic** (The Chef).

### `@Repository`
* **Where:** Above a Repository interface/class (e.g., `ProjectRepository`).
* **What it does:** Exactly like `@Component`, but specifically designates that this class talks to the **Database** (The Pantry Manager). Also translates low-level database errors into Spring exceptions.

### `@Configuration`
* **Where:** Above a setup/settings class (e.g., `SecurityConfig`, `DataInitializer`).
* **What it does:** Tells Spring: *"This class is not business logic; it is a factory that defines custom Beans for the application."*

### `@Bean`
* **Where:** Above a **method** inside a `@Configuration` class.
* **What it does:** Tells Spring: *"Run this method on startup, take whatever object it returns, and register it as an official Spring Bean."*
* **Example:**
  ```java
  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }
  ```

---

## 2. Web & REST Controllers (Handling HTTP Requests)

These annotations live in the **Controller Layer** (The Waiters) to accept and respond to HTTP traffic.

### `@RestController`
* **Where:** Above a Controller class (e.g., `ProjectController`, `AuthController`).
* **What it does:** Combines `@Controller` + `@ResponseBody`. Tells Spring: *"This class listens to HTTP requests, and whatever Java object its methods return should be automatically converted to JSON and sent back to the client."*

### `@RequestMapping("/api/projects")`
* **Where:** Above a Controller class (or method).
* **What it does:** Sets the **base URL path** for all methods inside this class.
* **Example:** If the class has `@RequestMapping("/api/projects")`, all endpoints inside will start with `/api/projects`.

### `@GetMapping` / `@PostMapping` / `@PutMapping` / `@DeleteMapping`
* **Where:** Above individual methods in a Controller.
* **What they do:** Map specific HTTP verbs to Java methods:
  * `@GetMapping` ➔ Reading data (`GET`)
  * `@PostMapping` ➔ Creating data (`POST`)
  * `@PutMapping("/{id}")` ➔ Updating existing data (`PUT`)
  * `@DeleteMapping("/{id}")` ➔ Deleting data (`DELETE`)

### `@RequestBody`
* **Where:** On a method parameter in a Controller.
* **What it does:** Tells Spring: *"Look inside the incoming HTTP Request Body. Take the raw JSON text sent by Postman/frontend, and convert it into this Java object."*
* **Example:**
  ```java
  @PostMapping
  public String create(@RequestBody ProjectDTO project) { ... }
  ```

### `@PathVariable("id")`
* **Where:** On a method parameter in a Controller.
* **What it does:** Grabs a dynamic variable embedded inside the URL path.
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
* **What it does:** Grabs query parameters from the end of the URL (`?status=ACTIVE`).
* **Example:**
  ```java
  // URL: GET /api/projects?status=ACTIVE
  @GetMapping
  public List<ProjectDTO> search(@RequestParam("status") String status) { ... }
  ```

---

## 3. Database & JPA (Hibernate Entities)

These annotations live in the **Entity Layer** to tell Hibernate how to map Java classes to PostgreSQL tables.

### `@Entity`
* **Where:** Above an Entity class (e.g., `Project`, `User`).
* **What it does:** Tells Spring & Hibernate: *"This is not a normal Java class. Create a table in PostgreSQL for this class."*

### `@Table(name = "users")`
* **Where:** Above an Entity class (below `@Entity`).
* **What it does:** By default, Spring names the table the same as the class. `@Table(name = "...")` lets you customize the exact SQL table name (e.g., making it plural `"users"` instead of `"user"`).

### `@Id`
* **Where:** Above the primary key field (e.g., `Long id`).
* **What it does:** Tells PostgreSQL: *"This field is the Primary Key (`PRIMARY KEY`) of the table."*

### `@GeneratedValue(strategy = GenerationType.IDENTITY)`
* **Where:** Right below `@Id`.
* **What it does:** Tells PostgreSQL: *"Automatically generate unique sequential numbers for this ID (1, 2, 3...) whenever a new row is inserted."*

### `@Column(name = "...", nullable = false, unique = true)`
* **Where:** Above an Entity field.
* **What it does:** Customizes column properties in the database (e.g. `unique = true` for usernames, `nullable = false` to prevent NULLs).

---

## 4. Validation (Checking User Input)

These annotations live in **DTOs** and **Controllers** to stop bad data before it reaches your database.

### `@Valid`
* **Where:** On a `@RequestBody` parameter in a Controller.
* **What it does:** **The Enforcer.** Tells Spring: *"Before executing this method, run all the validation rules declared inside this DTO. If any rule fails, stop immediately and throw an error!"*
* **Example:**
  ```java
  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) { ... }
  ```

### `@NotBlank(message = "...")`
* **Where:** Above a String field in a DTO.
* **What it does:** Ensures the string is **not null**, **not empty `""`**, and **not just spaces `"   "`**. Best for text inputs like usernames, titles, passwords.

### `@NotNull(message = "...")`
* **Where:** Above any field in a DTO (numbers, objects, dates).
* **What it does:** Ensures the value is not `null` (can be empty string, but cannot be null).

### `@Size(min = 2, max = 50, message = "...")`
* **Where:** Above a String or Collection in a DTO.
* **What it does:** Restricts the minimum and maximum character length.

### `@Min(...)` / `@Max(...)`
* **Where:** Above a numerical field (Integer, Long, Double).
* **What it does:** Enforces numerical boundaries (e.g., `@Min(18)` for age).

### `@Email(message = "...")`
* **Where:** Above a String field in a DTO.
* **What it does:** Ensures the string has a valid email structure (e.g., contains `@` and a valid domain).

---

## 5. Error Handling (Customer Service Desk)

These annotations live in your **Exception Handler** to format error responses into clean JSON.

### `@RestControllerAdvice`
* **Where:** Above the `GlobalExceptionHandler` class.
* **What it does:** Marks this class as the **Global Customer Service Desk**. It hovers above all controllers in the entire app, waiting to catch any uncaught exceptions.

### `@ExceptionHandler(SomeException.class)`
* **Where:** Above a method inside `@RestControllerAdvice`.
* **What it does:** Tells Spring: *"Whenever `SomeException` happens anywhere in the application, funnel it into this method."*
* **Example:**
  ```java
  @ExceptionHandler(ResourceNotFoundException.class)
  public Map<String, String> handleNotFound(ResourceNotFoundException ex) { ... }
  ```

### `@ResponseStatus(HttpStatus.BAD_REQUEST)`
* **Where:** Above an exception handler method (or custom exception class).
* **What it does:** Sets the HTTP status code (400, 404, 500) that Spring sends back to the client.

---

## 6. Spring Security

These annotations configure access control and authentication.

### `@EnableWebSecurity`
* **Where:** Above `SecurityConfig`.
* **What it does:** Tells Spring: *"Disable default security settings. I am taking over the club rules."*

### `@EnableMethodSecurity`
* **Where:** Above `SecurityConfig`.
* **What it does:** Turns on method-level security so you can use `@PreAuthorize` on individual methods.

### `@PreAuthorize("hasRole('ADMIN')")`
* **Where:** Above a Controller or Service method.
* **What it does:** Checks if the logged-in user has the required role before letting the method run. If not, instantly returns `403 Forbidden`.
* **Example:**
  ```java
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public String deleteProject(@PathVariable Long id) { ... }
  ```

---

## 7. Testing (JUnit 5 & Mockito)

These annotations live in the `src/test/java/` directory to run automated verification of your code.

### `@Test`
* **Where:** Above a test method (from `org.junit.jupiter.api.Test`).
* **What it does:** Tells JUnit: *"This is an automated test method. Execute it when `mvn test` is run!"*

### `@ExtendWith(MockitoExtension.class)`
* **Where:** Above a test class.
* **What it does:** Tells JUnit 5: *"Enable Mockito capabilities so I can use `@Mock` and `@InjectMocks` in this test class."*

### `@Mock`
* **Where:** Above a field in a test class.
* **What it does:** **The Stunt Double.** Creates a completely fake instance of a dependency (like `ProjectRepository`). No real database, network, or external connections are used.
* **Example:**
  ```java
  @Mock
  private ProjectRepository projectRepository;
  ```

### `@InjectMocks`
* **Where:** Above the class you are actually testing.
* **What it does:** Creates a real instance of the target class (e.g., `ProjectService`) and automatically injects all the `@Mock` stunt doubles into its constructor.
* **Example:**
  ```java
  @InjectMocks
  private ProjectService projectService;
  ```

### `@SpringBootTest`
* **Where:** Above an integration test class.
* **What it does:** Boots up the **entire Spring Boot ApplicationContext** (loads all beans, configs, database connections). Great for full End-to-End integration tests.

### `@WebMvcTest(SomeController.class)`
* **Where:** Above a Controller test class.
* **What it does:** Starts only the web layer (Controllers, Filters, Converters) without loading the full database or services. Used with `MockMvc` to test HTTP requests quickly.

### `@BeforeEach` / `@AfterEach`
* **Where:** Above helper methods in a test class.
* **What it does:**
  * `@BeforeEach`: Runs before **every single** `@Test` method (great for resetting test data).
  * `@AfterEach`: Runs after **every single** `@Test` method (great for cleanup).

---

## Summary: The Memory Trick

| If you want to... | Look in this section | Key Annotations |
|---|---|---|
| Register a Spring Bean | **1. Core** | `@Component`, `@Service`, `@Repository`, `@Bean` |
| Build an API endpoint | **2. Web** | `@RestController`, `@GetMapping`, `@PostMapping`, `@RequestBody` |
| Save data in Postgres | **3. JPA** | `@Entity`, `@Table`, `@Id`, `@GeneratedValue` |
| Validate user inputs | **4. Validation** | `@Valid`, `@NotBlank`, `@Size`, `@Email` |
| Catch errors globally | **5. Error** | `@RestControllerAdvice`, `@ExceptionHandler` |
| Lock down endpoints | **6. Security** | `@EnableWebSecurity`, `@PreAuthorize` |
| Write automated tests | **7. Testing** | `@Test`, `@ExtendWith`, `@Mock`, `@InjectMocks` |
