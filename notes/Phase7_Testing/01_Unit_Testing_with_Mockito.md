# Phase 7.1: Unit Testing with JUnit 5 & Mockito

---

## 1. What is a Unit Test?
A **Unit Test** tests **one single class in complete isolation** from everything else.
* We are testing our Chef: `ProjectService.java`.
* We do **NOT** want to talk to PostgreSQL.
* We do **NOT** want to start the web server (Tomcat).
* We want the test to run in **0.05 seconds**.

---

## 2. The Core Concept: The Movie Stunt Double (Mockito)

`ProjectService` has a dependency on `ProjectRepository`:
```text
ProjectService (The Chef) ──needs──► ProjectRepository (The Pantry) ──talks to──► PostgreSQL Database
```

If we don't have a database running, `ProjectRepository` will crash.  
So we use **Mockito** to hire a **Stunt Double (A Mock)**:

```text
ProjectService (The Real Chef) ──talks to──► [MOCK] ProjectRepository (The Stunt Double)
                                                      │ (Fake in-memory answers, NO DATABASE!)
```

### Mockito Workflow:
1. **The Stunt Double:** A fake mock instance is created in-memory to replace the real database dependency.
2. **Wiring the Test:** The mock stunt double is automatically passed into the target service constructor.
3. **Training the Mock (`when(...).thenReturn(...)`):** Scripting the stunt double:
   *"Whenever someone calls `findAll()`, return this fake list."*

*(For full syntax and options for `@Test`, `@ExtendWith`, `@Mock`, and `@InjectMocks`, see [notes/ANNOTATIONS.md](../ANNOTATIONS.md#7-testing-junit-5--mockito)).*

---

## 3. The 3 A's Pattern (Arrange - Act - Assert)

Every test method is divided into 3 clean phases:

```java
@Test
void shouldReturnAllProjects_AsDTOs() {
    // 1. ARRANGE (Given): Set up fake data and train the mock
    Project p1 = new Project("DevOps Pipeline", "ACTIVE");
    p1.setId(1L);
    when(projectRepository.findAll()).thenReturn(List.of(p1));

    // 2. ACT (When): Call the real service method
    List<ProjectDTO> result = projectService.getAllProjectS();

    // 3. ASSERT (Then): Check that the output matches expectations
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getName()).isEqualTo("DevOps Pipeline");
    verify(projectRepository, times(1)).findAll();
}
```

---

## 4. Explaining the 4 Tests We Built in `ProjectServiceTest.java`

### Test 1: Happy Path (`getAllProjectS`)
* **What it tests:** Does `ProjectService` take the list of `Project` entities returned by the database and successfully transform every single one into a `ProjectDTO`?
* **Key tool:** `verify(projectRepository, times(1)).findAll()` checks that the repository was actually called.

### Test 2: Finding by ID (`getProjectById`)
* **What it tests:** When project #10 exists in the database, does `ProjectService` return the matching DTO?
* **Key tool:** `Optional.of(p)` simulates a database hit (Spring Data JPA returns `Optional<T>`).

### Test 3: The Sad Path / Error Handling (`assertThrows`)
* **What it tests:** When project #999 does NOT exist in the database, does our service properly throw `ResourceNotFoundException`?
* **Key tool:** `assertThrows(ResourceNotFoundException.class, () -> { ... })` passes ONLY if the specified exception is thrown!

### Test 4: Creating a Project (`save`)
* **What it tests:** When `addProject(dto)` is called, does `ProjectService` convert the DTO to an Entity and pass it to `repository.save()`?
* **Key tool:** `verify(projectRepository, times(1)).save(any(Project.class))` confirms `save()` was executed.

---

## 5. How to Run Unit Tests

```bash
# Run only ProjectServiceTest:
mvn test -Dtest=ProjectServiceTest

# Run all tests in the entire project:
mvn test
```
