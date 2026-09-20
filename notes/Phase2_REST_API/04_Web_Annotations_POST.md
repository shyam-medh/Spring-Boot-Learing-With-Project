# POST Annotations in Spring Boot

When we want to **create** or **save** data, we use the HTTP POST method.

## 1. `@PostMapping`
* **What it does:** Similar to `@GetMapping`, this tells Spring to route incoming HTTP POST requests to this specific method.
* **Why use it:** GET is for reading. POST is for writing. If a user tries to send data to the server using a GET request, Spring will reject it because the method is explicitly marked to only accept POST traffic.

## 2. `@RequestBody`
* **What it does:** It tells Spring: "Look inside the body of the incoming HTTP request. You will find JSON data there. Take that JSON, and magically convert it back into a Java Object (like `ProjectDTO`)."
* **Why use it:** Over the internet, everything is just text (JSON). Java doesn't understand raw text. `@RequestBody` triggers Spring's internal library (Jackson) to parse the JSON and map the fields to your Java class automatically.

## How it stores data (In-Memory)
Right now, our `ProjectService` has a `private List<ProjectDTO> projects = new ArrayList<>();`.
When the Controller calls `projectService.addProject(project)`, the newly created Java Object is simply added to this `ArrayList`. 
* **Important Note:** Because this list is just living in the RAM of the computer, if you restart the Spring Boot application, any projects you added will disappear! In Phase 3, we will replace this `ArrayList` with a real PostgreSQL database.
