# Phase 1: Spring Fundamentals

## 1. What is Spring?
Spring is a popular, open-source Java framework that provides comprehensive infrastructure support for developing Java applications. Its primary goal is to make Java development easier, more secure, and faster. 

### Why do we need it?
Before Spring, enterprise Java development (Java EE) was very heavy and required complex setup and massive XML configurations. Spring introduced a simpler approach, allowing developers to focus on the business logic rather than boilerplate infrastructure code.

## 2. Spring vs Spring Boot
* **Spring Framework:** The core framework. It provides the building blocks (Dependency Injection, Data access, Security, etc.), but it requires you to manually wire everything together and configure the server.
* **Spring Boot:** An extension of the Spring Framework. It takes an opinionated view of the Spring platform and third-party libraries so you can get started with minimum fuss. It provides:
  * **Auto-configuration:** It automatically configures your application based on the dependencies you add.
  * **Embedded Servers:** It includes Tomcat (or Jetty/Undertow) directly inside your application so you don't need to deploy to an external server. You just run a `.jar` file.
  * **Starter Dependencies:** It groups common dependencies together. For example, `spring-boot-starter-web` gives you everything you need to build a web application (Spring MVC, Tomcat, JSON parsing, etc.).

## 3. Core Concepts (The "Magic" of Spring)
* **Inversion of Control (IoC):** Instead of your application code controlling the flow and creating objects (`new MyService()`), you hand over control to the framework. Spring creates the objects, wires them together, and manages their lifecycle.
* **Dependency Injection (DI):** A specific pattern of IoC. When an object needs another object to do its job (a dependency), Spring "injects" that dependency into it, typically through the constructor.
* **Spring Bean:** Any Java object that is instantiated, assembled, and managed by the Spring IoC container.
* **ApplicationContext (The IoC Container):** The environment inside Spring where all your Beans live and are managed.

## 4. How it connects to DevOpsHub
To build DevOpsHub, we will need many components: a `ProjectController` to handle HTTP requests, a `ProjectService` to hold business logic, and a `ProjectRepository` to talk to the database. Instead of manually creating these and passing them around, Spring will create them as Beans and inject the repository into the service, and the service into the controller.
