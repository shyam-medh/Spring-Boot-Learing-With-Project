# Phase 2: REST API Development Master Guide 🌐

Welcome to the **Phase 2 REST API Guide**! This directory details how to build production-grade RESTful APIs using Spring MVC, understand HTTP verbs, design Data Transfer Objects (DTOs), and route web requests cleanly.

---

## 🎯 Phase Goals & Outcomes
* Master standard HTTP methods (`GET`, `POST`, `PUT`, `DELETE`) and their idempotency semantics.
* Map JSON payloads to Java objects automatically using Jackson and Spring MVC.
* Understand why **DTOs** (Data Transfer Objects) are essential to prevent exposing internal data models.
* Use `@PathVariable` and `@RequestParam` to parse dynamic URI inputs.
* Establish standard HTTP status codes (`200 OK`, `201 Created`, `204 No Content`, `404 Not Found`).

---

## 📚 Phase 2 Notes & Curriculum

| # | Note | Topic | Key Takeaway |
|---|---|---|---|
| **01** | [01_HTTP_Methods_and_JSON.md](01_HTTP_Methods_and_JSON.md) | HTTP Methods & JSON | How client-server requests and responses communicate via JSON contracts. |
| **02** | [02_DTOs.md](02_DTOs.md) | Data Transfer Objects (DTOs) | The "Passport Control" pattern — why entities should never be exposed directly to the web. |
| **03** | [03_Dependency_Injection_Flow.md](03_Dependency_Injection_Flow.md) | Request to Service DI Flow | Step-by-step walkthrough of how an HTTP request travels from Controller to Service. |
| **04** | [05_Path_Variables.md](05_Path_Variables.md) | Path Variables & Params | When to use `/api/projects/{id}` vs `/api/projects?status=ACTIVE`. |
| **05** | [06_CRUD_Summary.md](06_CRUD_Summary.md) | REST CRUD Cheat Sheet | Complete cheat sheet of annotations, status codes, and HTTP methods for CRUD. |
| **06** | [07_The_Big_Picture_and_Enterprise_REST.md](07_The_Big_Picture_and_Enterprise_REST.md) | The Big Picture & Enterprise REST | What REST really means from first principles, the 4 pillars, request journey, and the enterprise scale pyramid (Pagination, ETags, Idempotency). |

---

## 💡 Key Mental Model: The Restaurant Waiter (`@RestController`)
The Controller is the **Waiter**. It doesn't cook the food (business logic) or fetch raw ingredients from the farm (database). It greets the customer, validates the order slip (DTO), hands the ticket to the Chef (Service), and returns the plated dish with the correct receipt status code (`200 OK` or `201 Created`).

---

⬅️ **Back to [Notes Master Index](../README.md)**
