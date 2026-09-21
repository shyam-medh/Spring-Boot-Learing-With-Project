# DevOpsHub - Spring Boot Learning Context

## LEARNING GOAL
"Become capable of independently designing, developing, debugging, testing, containerizing and deploying Spring Boot applications."

## CURRENT STATUS
* **CURRENT PHASE:** 3
* **CURRENT MODULE:** Database & JPA (PostgreSQL)
* **CURRENT LESSON:** Introduction to Databases & Dependencies
* **CURRENT CONCEPT:** Replacing In-Memory Lists with Real Storage
* **STATUS:** Learning
* **LAST COMPLETED CONCEPT:** Completed Phase 2 (REST API, CRUD, DTOs, Service Layer, Path Variables)
* **NEXT CONCEPT:** Adding Database Dependencies and connecting to PostgreSQL
* **CURRENT PROJECT MILESTONE:** v0.3 - Database Integration
* **CURRENT IMPLEMENTATION TASK:** Add PostgreSQL and Spring Data JPA dependencies to pom.xml.

## SESSION HANDOFF
* **What I learned today:** Why we use DevTools (`spring-boot-devtools`) for hot-reloading. How `removeIf` (lambdas) work. The philosophy of REST (using HTTP Methods as verbs instead of URLs). Why data disappears on server restart without a database.
* **What I implemented:** Finished the full CRUD basics by adding the `deleteProject` functionality and fixing the `@PathVariable` requirement in Spring Boot 3.2.
* **What changed in the project:** Updated `pom.xml` to include DevTools. Completed `ProjectController` and `ProjectService`.
* **Problems encountered:** 500 Internal Server error on DELETE request due to a missing compiler parameter name. Forgot to save the Service file before running the server. Typo in XML tag (`dependencies` instead of `dependency`).
* **Problems solved:** Learned to explicitly name `@PathVariable("id")`, learned to check for unsaved files (`Ctrl + S`), and learned XML structure for Maven.
* **Concepts I struggled with:** Remembering the save/compile flow vs hot-reloading (resolved by adding DevTools).
* **Current Git branch:** main
* **Current project state:** Fully functional In-Memory REST API for Projects.
* **Exact next task:** We are starting Phase 3. Need to connect the app to a real PostgreSQL database.
* **Exact next concept to learn:** Spring Data JPA and Entities.

## LEARNING PROGRESS
* [x] Phase 0: Prerequisites
* [x] Phase 1: Spring Fundamentals
* [x] Phase 2: REST API Development
* [ ] Phase 3: Database & JPA
* [ ] Phase 4: Proper Application Architecture
* [ ] Phase 5: Validation & Error Handling
* [ ] Phase 6: Spring Security
* [ ] Phase 7: Testing
* [ ] Phase 8: API Documentation
* [ ] Phase 9: Production Features
* [ ] Phase 10: Advanced Spring Boot
* [ ] Phase 11: Microservices (Optional)
* [ ] Phase 12: Messaging & Event Driven Architecture (Optional)
* [ ] Phase 13: Docker
* [ ] Phase 14: CI/CD
* [ ] Phase 15: Cloud Deployment

## WHAT I CURRENTLY KNOW
* Java (Basic)
* OOP
* SQL
* HTML/CSS/JavaScript (Basics)
* Git/GitHub
* Linux (Basics)
* Docker (Basics)
* REST API (Basic concepts)
* AWS, DevOps, K8s, Terraform, CI/CD (Some knowledge)

## WHAT I DON'T KNOW YET
(To be populated with Spring Boot concepts as we progress)

## MY WEAK AREAS
(To be populated as we identify them)

## PROJECT DECISIONS
(None yet)

## PROJECT ARCHITECTURE
(Not started)
