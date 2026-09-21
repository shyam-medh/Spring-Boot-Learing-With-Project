# CRUD Operations Summary

CRUD stands for **Create, Read, Update, Delete**. These are the 4 basic operations every application needs to perform on data. 

In a REST API built with Spring Boot, these operations map directly to specific HTTP Methods and Annotations:

| Operation | HTTP Method | Spring Annotation | Example URL | Purpose |
| :--- | :--- | :--- | :--- | :--- |
| **Create** | `POST` | `@PostMapping` | `/api/projects` | Creates a new record. Receives data via `@RequestBody`. |
| **Read (All)** | `GET` | `@GetMapping` | `/api/projects` | Fetches a list of all records. |
| **Read (One)** | `GET` | `@GetMapping("/{id}")` | `/api/projects/1` | Fetches a single specific record using `@PathVariable`. |
| **Update** | `PUT` | `@PutMapping("/{id}")` | `/api/projects/1` | Updates an existing record. Receives ID via `@PathVariable` and new data via `@RequestBody`. |
| **Delete** | `DELETE` | `@DeleteMapping("/{id}")` | `/api/projects/1` | Deletes a specific record using `@PathVariable`. |

## Key Takeaway
By combining these 4 HTTP methods with a single base URL (`/api/projects`), you can completely manage any resource in your application cleanly and predictably.
