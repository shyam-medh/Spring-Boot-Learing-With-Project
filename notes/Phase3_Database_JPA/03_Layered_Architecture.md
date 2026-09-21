# The Spring Boot Layered Architecture

When building a backend, we divide the code into 5 distinct folders (layers). This might feel repetitive at first, but it is the secret to building massive applications that don't become a tangled mess.

Let's use a **Restaurant Analogy** to understand exactly what each folder does.

---

### 1. `entity/` (The Ingredients)
An **Entity** (`Project.java`) is a pure representation of a Database Table. It does zero logic. It doesn't know how to save itself. It is simply a container with variables (columns) like `id`, `name`, and `status`. 
* **Analogy:** These are the raw ingredients sitting on the counter.

### 2. `repository/` (The Fridge / Pantry)
A **Repository** (`ProjectRepository.java`) is the tool used to talk to the Database. It takes Entities and saves them, or fetches Entities from the database. 
* **Analogy:** This is the Fridge. You tell the Fridge, *"Give me the ingredient with ID #3"*, or *"Put this new ingredient into storage."*

### 3. `service/` (The Chef)
The **Service** (`ProjectService.java`) contains your **Business Logic**. It talks to the Repository to get the data, performs calculations, checks if the user is allowed to do something, and prepares the data.
* **Analogy:** This is the Chef. The Chef asks the Fridge (Repository) for ingredients (Entities), cooks them up, and makes sure the meal is perfectly prepared according to the restaurant's rules.

### 4. `controller/` (The Waiter)
The **Controller** (`ProjectController.java`) is the only layer that talks to the Internet. It listens for HTTP requests (GET, POST), takes the JSON from the user, hands it to the Service (Chef), and returns the final HTTP Response back to the user.
* **Analogy:** This is the Waiter. The Waiter takes the customer's order from the internet, walks to the kitchen, hands it to the Chef (Service), and brings the cooked food back to the customer. The Waiter *never* cooks the food, and *never* goes into the Fridge.

### 5. `dto/` (The Menu / Plate)
A **DTO** (Data Transfer Object) is the exact shape of the JSON you want to send to the customer. Sometimes your Entity has a password column in the database, but you don't want to send the password to the internet! So you copy the data from the Entity into a DTO (leaving the password out), and the Waiter serves the DTO to the customer.
* **Analogy:** This is the Plate the food is served on. It is meant strictly for presentation to the customer.

---

### The Flow of Data
Internet -> Waiter (Controller) -> Chef (Service) -> Fridge (Repository) -> Database (Entities)
