# Phase 1: Spring Fundamentals Master Guide 🧱

Welcome to the **Phase 1 Fundamentals Guide**! This directory covers the foundational building blocks of the Spring Framework, Spring Boot, Inversion of Control (IoC), Dependency Injection (DI), Maven project anatomy, and debugging practices.

---

## 🎯 Phase Goals & Outcomes
* Understand what Spring is and why Spring Boot revolutionized Java development.
* Grasp the core mechanics of Inversion of Control (IoC) and Dependency Injection (DI).
* Understand how Maven organizes enterprise Java projects and handles dependencies.
* Learn how to debug Spring Boot applications effectively by reading stack traces.

---

## 📚 Phase 1 Notes & Curriculum

| # | Note | Topic | Key Takeaway |
|---|---|---|---|
| **01** | [01_What_Is_Spring.md](01_What_Is_Spring.md) | What is Spring? | The Spring ecosystem, enterprise challenges, and why we use it. |
| **02** | [02_Spring_vs_Spring_Boot.md](02_Spring_vs_Spring_Boot.md) | Spring vs Spring Boot | How Spring Boot eliminated XML, added auto-configuration, and embedded Tomcat. |
| **03** | [03_Core_Concepts_IoC_DI.md](03_Core_Concepts_IoC_DI.md) | IoC & Dependency Injection | The ApplicationContext, Beans, and why we use Constructor Injection over `new`. |
| **04** | [03_Maven_Project_Structure.md](03_Maven_Project_Structure.md) | Maven Project Structure | Standard directory layout (`src/main/java`, `pom.xml`, plugins, lifecycles). |
| **05** | [04_Reading_Stack_Traces.md](04_Reading_Stack_Traces.md) | Reading Stack Traces | The "bottom-to-top" rule and hunting down `Caused by:` lines. |
| **06** | [05_Layered_Architecture.md](05_Layered_Architecture.md) | Layered Architecture Intro | Presentation, Business Logic, and Data Access separation. |

---

## 💡 Key Mental Model: The IoC Container (The General Contractor)
Instead of classes instantiating their own dependencies using `new Kitchen()` or `new Stove()`, the **Spring IoC Container** acts as the general contractor. It constructs all beans at startup, wires them together based on constructors, and delivers fully assembled objects wherever requested.

---

⬅️ **Back to [Notes Master Index](../README.md)**
