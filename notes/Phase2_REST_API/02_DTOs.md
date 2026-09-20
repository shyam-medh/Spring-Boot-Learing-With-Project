# DTOs (Data Transfer Objects)

When building an API, we rarely send raw Strings or raw Database Entities over the network. Instead, we use **Data Transfer Objects (DTOs)**.

## What is a DTO?
A DTO is a simple Java class (often just fields, getters, and setters) designed specifically to carry data between processes—in our case, between the Spring Boot backend and the frontend over HTTP.

## Why do we use them?
1. **Security/Privacy:** A database entity might have a password or internal ID we don't want the user to see. A DTO allows us to pick exactly which fields to expose to the outside world.
2. **Stability:** If we change our database table, our API response would change if we exposed the entity directly. By using a DTO, the API contract remains stable even if the database changes.
3. **Shape of Data:** The frontend might need data combined from two different tables. A DTO can act as a container for that combined data.

When Spring Boot (via Jackson) sees a DTO returned from a Controller, it automatically turns it into a JSON Object:
```json
{
  "id": 1,
  "name": "DevOps Migration",
  "status": "ACTIVE"
}
```
