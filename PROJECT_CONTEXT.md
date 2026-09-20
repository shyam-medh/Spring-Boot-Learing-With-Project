# DevOpsHub - Spring Boot Learning Context

## LEARNING GOAL
"Become capable of independently designing, developing, debugging, testing, containerizing and deploying Spring Boot applications."

## CURRENT STATUS
* **CURRENT PHASE:** 2
* **CURRENT MODULE:** REST API Development
* **CURRENT LESSON:** Full CRUD & The Service Layer
* **CURRENT CONCEPT:** HTTP DELETE & @PathVariable
* **STATUS:** Paused for the night
* **LAST COMPLETED CONCEPT:** HTTP POST, @RequestBody, and In-Memory Service Logic
* **NEXT CONCEPT:** Testing the @DeleteMapping
* **CURRENT PROJECT MILESTONE:** v0.2 - REST API basics
* **CURRENT IMPLEMENTATION TASK:** Implement the `deleteProject` method in the Service and Controller.

## SESSION HANDOFF
* **What I learned today:** Initialized the project, learned IoC, Dependency Injection, REST Controllers, GET/POST mapping, JSON, DTOs, layered architecture, and how Spring converts bodies and handles Path Variables.
* **What I implemented:** Created the `backend` Spring Boot app, built `HelloController`, built `ProjectDTO`, built `ProjectService` with an in-memory list, and built `ProjectController` with `GET` and `POST` endpoints.
* **What changed in the project:** Created all the Phase 1 and Phase 2 notes, updated `ProjectController.java`, `ProjectService.java`, and `ProjectDTO.java`.
* **Problems encountered:** Running `javac` directly vs Maven. Understanding how the Service relates to the Controller.
* **Problems solved:** Properly running `mvn spring-boot:run` and understanding how Dependency Injection wires the Controller and Service automatically.
* **Concepts I struggled with:** Dependency Injection and Layered Architecture (Resolved by seeing the real code flow!).
* **Current Git branch:** main
* **Current project state:** API can fetch projects (`GET`) and add projects (`POST`) using an in-memory list.
* **Exact next task:** We paused right before writing the code to handle the `DELETE` request (which introduces `@PathVariable`). 
* **Exact next concept to learn:** Finish the `DELETE` implementation task, then move on to Phase 3: PostgreSQL Database!

## LEARNING PROGRESS
* [x] Phase 0: Prerequisites
* [x] Phase 1: Spring Fundamentals
* [~] Phase 2: REST API Development
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
