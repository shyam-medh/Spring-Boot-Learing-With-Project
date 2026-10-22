# Phase 3: Database & JPA Master Guide 🗄️

Welcome to the **Phase 3 Database & JPA Guide**! This directory details how to configure PostgreSQL in Docker, connect Spring Boot using Spring Data JPA & Hibernate, and map object-oriented domain models to relational tables.

---

## 🎯 Phase Goals & Outcomes
* Spin up and run a containerized PostgreSQL 16 database using Docker.
* Configure Spring Boot datasources and Hibernate properties in `application.yaml`.
* Map Java classes to relational SQL tables using Jakarta Persistence (`@Entity`, `@Table`, `@Id`, `@GeneratedValue`).
* Understand Hibernate's `ddl-auto` lifecycle (`update`, `validate`, `create-drop`).
* Leverage Spring Data `JpaRepository` for zero-boilerplate CRUD SQL queries.

---

## 📚 Phase 3 Notes & Curriculum

| # | Note | Topic | Key Takeaway |
|---|---|---|---|
| **01** | [01_Database_Connection.md](01_Database_Connection.md) | PostgreSQL & Docker Setup | Running PostgreSQL container, configuring `127.0.0.1:5432`, and JDBC connection strings. |
| **02** | [02_JPA_Entities.md](02_JPA_Entities.md) | JPA Entity Mapping | How `@Entity`, `@Id`, `@GeneratedValue(strategy = IDENTITY)`, and columns map Java fields to SQL columns. |

---

## 💡 Key Mental Model: The Pantry (`JpaRepository`) & Blueprints (`@Entity`)
* **`@Entity` (The Blueprint):** Tells Hibernate the exact schema and columns of the database table.
* **`JpaRepository` (The Stock Clerk / Pantry):** You don't write raw SQL strings like `SELECT * FROM projects WHERE id = ?`. Spring Data automatically generates the SQL queries under the hood when you call `findAll()`, `findById()`, or `save()`.

---

## ⚠️ Common Pitfalls Encountered in this Phase
* **Hibernate Dialect Error:** Forgetting to run Docker before starting Spring Boot (`Unable to determine Dialect without JDBC metadata`). See [01_Hibernate_Dialect.md](../Errors_and_Fixes/01_Hibernate_Dialect.md).
* **Calcutta Timezone Issue:** Legacy Windows timezone string unrecognized by Postgres. See [02_Timezone_Calcutta.md](../Errors_and_Fixes/02_Timezone_Calcutta.md).

---

⬅️ **Back to [Notes Master Index](../README.md)**
