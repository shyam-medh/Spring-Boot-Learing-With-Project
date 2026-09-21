# Wiring the Service to the Repository

In Phase 3, we successfully connected our Database and created our Entity and Repository. The final step was to tell our **Business Logic (Service)** to actually use the **Database (Repository)** instead of fake RAM data.

### The Problem with RAM
Before we had a database, our `ProjectService` used an `ArrayList` to store data:
```java
private List<ProjectDTO> projects = new ArrayList<>();
```
This is bad because every time you restart the server, the RAM is cleared and all your data is permanently deleted!

### The Solution: Constructor Injection
To give the Chef (Service) access to the Fridge (Repository), we use Spring's **Dependency Injection** (specifically, Constructor Injection).

```java
@Service
public class ProjectService {

    // 1. Declare the Repository
    private ProjectRepository repository;

    // 2. Inject it via the Constructor
    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
    }
}
```
When Spring Boot starts up, it automatically builds the `ProjectRepository` tool, and hands it directly to the `ProjectService`.

### Using the Repository for CRUD Operations
Once the Service has the Repository, we can delete the `ArrayList` and use the built-in database methods:

* **READ:** `repository.findAll()` generates `SELECT * FROM projects;`
* **CREATE:** `repository.save(entity)` generates `INSERT INTO projects...`
* **DELETE:** `repository.deleteById(id)` generates `DELETE FROM projects WHERE id = ?;`

### The Entity vs DTO Translation
The most important job of the Service when saving or reading data is translating between the **Entity** (Database format) and the **DTO** (Internet format).

When the user wants a list of projects:
1. Service gets `Entity` from Repository.
2. Service copies data onto a safe `DTO`.
3. Service returns `DTO` to the Controller.
