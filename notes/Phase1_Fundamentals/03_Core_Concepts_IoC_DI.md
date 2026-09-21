# Core Concepts (The "Magic" of Spring)

* **Inversion of Control (IoC):** Instead of your application code controlling the flow and creating objects (`new MyService()`), you hand over control to the framework. Spring creates the objects, wires them together, and manages their lifecycle.
* **Dependency Injection (DI):** A specific pattern of IoC. When an object needs another object to do its job (a dependency), Spring "injects" that dependency into it, typically through the constructor.
* **Spring Bean:** Any Java object that is instantiated, assembled, and managed by the Spring IoC container.
* **ApplicationContext (The IoC Container):** The environment inside Spring where all your Beans live and are managed.

## How it connects to DevOpsHub
To build DevOpsHub, we will need many components: a `ProjectController` to handle HTTP requests, a `ProjectService` to hold business logic, and a `ProjectRepository` to talk to the database. Instead of manually creating these and passing them around, Spring will create them as Beans and inject the repository into the service, and the service into the controller.
