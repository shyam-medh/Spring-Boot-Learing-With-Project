# The 3-Tier Layered Architecture

In Spring Boot, we organize our code into three distinct layers to separate responsibilities. This makes the code easier to read, test, and maintain.

## 1. The Controller Layer (`@RestController`)
* **Role:** The Receptionist / Traffic Cop.
* **What it does:** It listens for incoming HTTP web requests (like GET, POST) from the outside world (like a browser or React frontend). It reads the data coming in, hands it off to the Service layer to do the actual work, and then returns an HTTP response (like a 200 OK or 404 Not Found) back to the user.
* **Rule:** Controllers should **never** contain complex business logic or talk to the database directly.

## 2. The Service Layer (`@Service`)
* **Role:** The Brain / The Chef.
* **What it does:** This is where the core "Business Logic" of your application lives. If you need to calculate a discount, verify a user has permission to delete a project, or orchestrate multiple steps, it happens here.
* **Rule:** Services don't know anything about HTTP (no web annotations) and they don't write SQL queries. They orchestrate the flow.

## 3. The Repository Layer (`@Repository`)
* **Role:** The File Cabinet / The Pantry.
* **What it does:** It is strictly responsible for communicating with the database. It saves data, updates data, deletes data, and fetches data. 
* **Rule:** Repositories know nothing about business rules or web requests. They just blindly execute database operations when told to do so by the Service layer.

## The Flow of a Request
HTTP Request -> **Controller** -> **Service** -> **Repository** -> Database -> **Repository** -> **Service** -> **Controller** -> HTTP Response
