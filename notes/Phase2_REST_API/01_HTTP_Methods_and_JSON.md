# HTTP Methods & JSON

When a frontend application (like a website or mobile app) wants to talk to our Spring Boot backend, it sends an **HTTP Request**. 

## 1. HTTP Methods (Verbs)
The HTTP Method tells the server *what action* the client wants to perform.
* **GET:** Read data. (e.g., "Give me a list of all projects"). GET requests should never modify data.
* **POST:** Create new data. (e.g., "Create a new project"). The data is sent in the "Body" of the request.
* **PUT:** Update existing data entirely. (e.g., "Update project ID 5").
* **DELETE:** Delete data. (e.g., "Delete project ID 5").

## 2. JSON (JavaScript Object Notation)
When we use `POST` to send data to the server, or when the server responds to a `GET` request, the data must be formatted in a way both computers understand. Today, **JSON** is the absolute standard.

It looks like this:
```json
{
  "id": 1,
  "name": "DevOps Migration",
  "status": "IN_PROGRESS"
}
```
Spring Boot automatically converts Java Objects into JSON when sending a response, and converts JSON into Java Objects when receiving a request! This is done using a library called Jackson, which is included automatically in `spring-boot-starter-web`.
