# The Complete Flow: Dependency Injection & HTTP Requests

When learning Spring Boot, it is crucial to understand the exact chronological flow of what happens from the moment you click "Run" to the moment data appears in the browser.

## Step 1: Application Startup (The "Component Scan")
When you run `mvn spring-boot:run`, Spring boots up and scans all your Java files looking for its special annotations (`@RestController`, `@Service`, `@Repository`, etc.).

## Step 2: Creating the Beans (Inversion of Control)
Spring finds `ProjectService` and sees the `@Service` annotation. 
* Spring does this behind the scenes: `ProjectService myServiceBean = new ProjectService();`
* It puts this object inside its internal memory box (the ApplicationContext).

## Step 3: Wiring the Beans (Dependency Injection)
Next, Spring finds `ProjectController` and sees `@RestController`. It wants to create it, but it looks at the constructor:
`public ProjectController(ProjectService projectService)`

Spring says: *"Ah! The controller needs a ProjectService to be created. Do I have one in my memory box? Yes, I just made one!"*
* Spring does this behind the scenes: `ProjectController myControllerBean = new ProjectController(myServiceBean);`
* Both objects are now created, permanently linked together, and waiting for web traffic.

## Step 4: The Web Request
A user opens a browser and types `http://localhost:8080/api/projects`.
1. **Tomcat:** The embedded Tomcat server hears the request on port 8080.
2. **Routing:** Tomcat hands the request to Spring. Spring checks its internal map of URLs and sees that `/api/projects` is mapped to the `getAllProject()` method in `ProjectController`.
3. **Execution:** Spring executes the method. The Controller asks the Service for the data (`projectService.getAllProjectS()`). The Service returns the Java `List`.
4. **JSON Conversion:** Spring takes that Java `List`, automatically converts it into a JSON Array, and sends it back over the internet to the user's browser.
