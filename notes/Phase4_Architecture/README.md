# Phase 4: Application Architecture Master Guide 🏗️

Welcome to the **Phase 4 Architecture Guide**! This directory details the **5-Layer Architecture Pattern** used to build clean, maintainable, decoupled, and testable enterprise Spring Boot applications.

---

## 🎯 Phase Goals & Outcomes
* Master the classic 5-Layer pattern: **Presentation (Controller) ➔ Service (Business Logic) ➔ Data Access (Repository) ➔ Entity (Database Model) ➔ DTO (API Contract)**.
* Guarantee that business rules remain completely isolated from web protocols and database drivers.
* Prevent entity leakage into controllers and JSON responses.
* Enforce constructor-based dependency injection across all service and repository components.

---

## 📚 Phase 4 Notes & Curriculum

| # | Note | Topic | Key Takeaway |
|---|---|---|---|
| **01** | [03_Layered_Architecture.md](03_Layered_Architecture.md) | The 5-Layer Pattern | Why enterprise apps split code across Controller, Service, Repository, Entity, and DTO layers. |
| **02** | [04_Wiring_Service_to_Repository.md](04_Wiring_Service_to_Repository.md) | Wiring Service to Repository | How the Service consumes the Repository, maps entities to DTOs, and handles business validation. |

---

## 💡 Key Mental Model: The 5-Layer Restaurant Architecture
```text
Customer (Browser / Postman)
          │ HTTP Request
          ▼
   [Controller]  <─── The Waiter: Welcomes guests, validates request, returns HTTP status
          │ DTO
          ▼
    [Service]    <─── The Chef: Pure business rules, calculations, security checks
          │ Entity
          ▼
   [Repository]  <─── The Pantry Clerk: Executes SQL queries, fetches/saves data
          │ SQL
          ▼
   [PostgreSQL]  <─── The Warehouse: Durable database storage
```

---

⬅️ **Back to [Notes Master Index](../README.md)**
