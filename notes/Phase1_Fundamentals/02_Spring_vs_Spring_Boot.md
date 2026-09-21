# Spring vs Spring Boot

* **Spring Framework:** The core framework. It provides the building blocks (Dependency Injection, Data access, Security, etc.), but it requires you to manually wire everything together and configure the server.
* **Spring Boot:** An extension of the Spring Framework. It takes an opinionated view of the Spring platform and third-party libraries so you can get started with minimum fuss. It provides:
  * **Auto-configuration:** It automatically configures your application based on the dependencies you add.
  * **Embedded Servers:** It includes Tomcat (or Jetty/Undertow) directly inside your application so you don't need to deploy to an external server. You just run a `.jar` file.
  * **Starter Dependencies:** It groups common dependencies together. For example, `spring-boot-starter-web` gives you everything you need to build a web application (Spring MVC, Tomcat, JSON parsing, etc.).
