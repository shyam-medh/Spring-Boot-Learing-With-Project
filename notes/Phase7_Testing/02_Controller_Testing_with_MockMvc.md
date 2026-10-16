# Phase 7.2: Controller Testing with `MockMvc` & Mental Models

---

## 1. Why Test the Controller Layer?

In Phase 7.1, we tested `ProjectService` (The Chef) with pure JUnit 5 & Mockito.  
Now we move up to `ProjectController` (The Waiter).

```text
Customer (Browser/Postman)
           │
           ▼
1. Controller (The Waiter)       <--- WE ARE TESTING THIS!
           │
           ▼
2. Service    (The Chef)         <--- Stunt double (@MockitoBean)
           │
           ▼
3. Database   (The Pantry)       <--- Completely turned OFF!
```

A Controller does **NOT** cook or perform business logic. A Controller has a completely different job:
* It listens to **HTTP URLs and Verbs** (`GET /api/projects`, `POST /api/projects`).
* It deserializes **JSON text into Java objects** (`@RequestBody`).
* It triggers **Bean Validation** (`@Valid`).
* It sets the **HTTP Status Code** (`200 OK`, `400 Bad Request`, `404 Not Found`).
* It serializes **Java objects into JSON**.

To test this, we need to send actual HTTP requests, but **without** starting the slow Tomcat server or PostgreSQL database.

---

## 2. The 3 Characters in a Controller Test

Spring provides a specialized slice test for this:

```text
┌────────────────────────────────────────────────────────────────────────┐
│                      @WebMvcTest(ProjectController.class)               │
│                                                                        │
│  [MockMvc] ─────────────────► [ProjectController] ────────────────► [@MockitoBean Service]
│ (The Fake Postman)            (The Real Waiter)                     (The Stunt Chef)
└────────────────────────────────────────────────────────────────────────┘
```

1. **`@WebMvcTest(ProjectController.class)`**:
   Tells Spring: *"Do NOT load the whole application. Do NOT connect to PostgreSQL. Load ONLY `ProjectController`, JSON converters, and web infrastructure into memory."*
2. **`MockMvc` (The Fake Postman)**:
   An in-memory HTTP client. Instead of opening Postman and manually clicking Send, `MockMvc` fires fake HTTP requests directly into your controller and asserts the response in milliseconds.
3. **`@MockitoBean private ProjectService projectService;`**:
   The Stunt Double Chef. The Controller calls this mock service, and we script its responses using `when(...).thenReturn(...)`!

---

## 3. The Secret of `import static` (Why It Looks Like Magic)

In regular Java, every static method requires typing the class name first:
```java
// Without static import: verbose, cluttered, hard to read
MockMvcRequestBuilders.get("/api/projects");
MockMvcResultMatchers.status().isOk();
```

Because tests have dozens of these calls, Java uses **`import static`** so you can **omit the class name**:

| Without Static Import (Cluttered) | With `import static` (Reads like plain English!) |
|---|---|
| `MockMvcRequestBuilders.get("/api/projects")` | **`get("/api/projects")`** |
| `MockMvcResultMatchers.status().isOk()` | **`status().isOk()`** |
| `MockMvcResultMatchers.jsonPath("$.name")` | **`jsonPath("$.name")`** |
| `Mockito.when(...)` | **`when(...)`** |

> [!NOTE]
> **Nobody memorizes these imports!** You type `get("/api/projects")`, press `Alt + Enter` (or `Ctrl + .`), and let your IDE automatically add the static import.

---

## 4. The 4 Import Buckets Mental Model

Every single import in a Controller test fits into one of **4 simple buckets**:

```text
┌────────────────────────────────────────────────────────┐
│                   THE 4 IMPORT BUCKETS                 │
│                                                        │
│  1. The Postman Actions:    get(), post(), delete()    │
│  2. The Checkers:           status(), jsonPath()       │
│  3. The Chef Stunt Tools:   when(), doNothing()        │
│  4. The Translation Tool:   ObjectMapper               │
└────────────────────────────────────────────────────────┘
```

### Bucket 1: The Postman Buttons (`MockMvcRequestBuilders`)
```java
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
```
* **Role:** These are simply the buttons you click in Postman:
  * `get("/api/projects")` ➔ Fires HTTP GET.
  * `post("/api/projects")` ➔ Fires HTTP POST.
  * `delete("/api/projects/5")` ➔ Fires HTTP DELETE.

### Bucket 2: The Inspectors (`MockMvcResultMatchers` & Hamcrest)
```java
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.hasSize;
```
* **Role:** Inspects the response returned by the Waiter:
  * `status().isOk()` ➔ Verifies HTTP `200`.
  * `status().isNotFound()` ➔ Verifies HTTP `404`.
  * `status().isBadRequest()` ➔ Verifies HTTP `400`.
  * `jsonPath("$.name").value("DevOps")` ➔ Inspects fields inside the JSON body.
  * `hasSize(2)` ➔ Hamcrest helper checking array length.

### Bucket 3: The Chef's Stunt Double (`Mockito`)
```java
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.any;
```
* **Role:** Tells the fake chef what to say:
  * `when(service.getAllProjectS()).thenReturn(list)` ➔ Script the fake chef's return value.
  * `doNothing().when(service).deleteProject(5L)` ➔ Script a `void` method to succeed silently.
  * `any(ProjectDTO.class)` ➔ Match any DTO argument.

### Bucket 4: The Translator (`ObjectMapper`)
```java
private final ObjectMapper objectMapper = new ObjectMapper();
```
* **Role:** In Postman, you type JSON text `{"name": "DevOps"}`. In Java, you have an object `new ProjectDTO(...)`. `ObjectMapper` converts Java objects to JSON strings via `objectMapper.writeValueAsString(dto)`.

---

## 5. Why Does Controller Testing Feel "Heavy"? (The Security Baggage)

When reading `ProjectControllerTest`, you might ask:
*"Why on earth do we have these 3 lines?"*

```java
@MockitoBean private JwtAuthenticationFilter jwtAuthenticationFilter;
@MockitoBean private JwtUtil jwtUtil;
@MockitoBean private CustomUserDetailsService customUserDetailsService;
```

### The Explanation:
* In Phase 6, we configured **Spring Security** (`SecurityConfig`).
* Even though `@WebMvcTest` only loads the web layer, Spring Security is an over-protective bouncer that still wakes up in the test context!
* `SecurityConfig` demands its constructor dependencies (`JwtAuthenticationFilter`, `JwtUtil`, `CustomUserDetailsService`).
* If we don't provide mocks for them, Spring panics and crashes the test!
* **The Solution:** We provide dummy `@MockitoBean` stunt doubles for them, and add `@AutoConfigureMockMvc(addFilters = false)` so security filters don't block our controller tests.

> [!TIP]
> This "security baggage" is why controller slice testing feels heavier than service unit testing. Many teams test 80% of business logic at the Service level, where testing is pure, fast, clean Java.

---

## 6. How to Read `MockMvc` & `jsonPath`

Every `MockMvc` call follows a fluent, readable chain:

```java
mockMvc.perform(get("/api/projects"))                      // 1. ACT: Send HTTP GET
       .andExpect(status().isOk())                         // 2. ASSERT: HTTP 200 OK
       .andExpect(jsonPath("$", hasSize(2)))               // 3. ASSERT: Array size is 2
       .andExpect(jsonPath("$[0].name").value("DevOps")); // 4. ASSERT: First project's name
```

### What is `jsonPath(...)`?
`jsonPath` is a query language for JSON:
* `$` ➔ The root of the JSON document.
* `$[0]` ➔ The first element of a JSON array.
* `$[0].name` ➔ The `name` property of the first element.
* `$.status` ➔ The `status` property of a single JSON object.

---

## 7. The 3 Crucial Tests for a Controller

### Test 1: `GET /api/projects` (Reading & Serializing JSON)
* **Given:** Service mock returns `List.of(dto1, dto2)`.
* **When:** `mockMvc.perform(get("/api/projects"))`.
* **Then:** Status is `200 OK` and JSON response body matches the list.

### Test 2: `POST /api/projects` (Happy Path Creation)
* **Given:** Valid JSON payload `{"name": "Docker Swarm", "status": "ACTIVE"}`.
* **When:** `mockMvc.perform(post("/api/projects").contentType(APPLICATION_JSON).content(...))`.
* **Then:** Status is `200 OK` and response string is `"Project created successfully!"`.

### Test 3: `POST /api/projects` with Invalid Data (Validation Check)
* **Given:** Invalid JSON with blank name `{"name": "", "status": ""}`.
* **When:** `mockMvc.perform(post("/api/projects").contentType(APPLICATION_JSON).content(...))`.
* **Then:** Status is **`400 Bad Request`**! Proves our `@Valid` bouncer and `GlobalExceptionHandler` catch bad input over HTTP!

---

## 8. Summary: Unit Test vs Controller Slice Test vs Full E2E

| Feature | Level 1: Service Unit Test | Level 2: Controller Slice Test | Level 3: Integration (E2E) |
|---|---|---|---|
| **Annotation** | `@ExtendWith(MockitoExtension.class)` | `@WebMvcTest(ProjectController.class)` | `@SpringBootTest` |
| **Spring Context?** | ❌ No (Pure Java) | ⚠️ Partial (Only Web Layer) | ✅ Full (All Beans & DB) |
| **Speed** | Blazing fast (0.05s) | Fast (~1-2s) | Slower (~3-5s) |
| **Mocking Tool** | `@Mock` (pure Mockito) | `@MockitoBean` (Spring-managed mock) | No mocks (or Testcontainers) |
| **What it tests** | Business logic & calculations | HTTP routing, JSON, Validation | Real full user flow |
