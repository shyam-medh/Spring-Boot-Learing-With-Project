# Path Variables

In REST APIs, we often need to perform actions on a *specific* resource. For example, we don't want to just "delete projects," we want to "delete project with ID 2."

To do this, we include the ID directly in the URL: `http://localhost:8080/api/projects/2`

## 1. Dynamic URL Parameters
In RESTful routing, variables like `{id}` in a path are automatically bound to Java method arguments.

Example:
```java
@DeleteMapping("/{id}")
public String deleteProject(@PathVariable Long id) {
    // Spring extracts the {id} from the URL and puts it in the Long variable
    projectService.removeProject(id);
    return "Deleted!";
}
```

*(For full syntax, rules, and examples of `@PathVariable`, see [notes/ANNOTATIONS.md](../ANNOTATIONS.md#2-web--rest-controllers-handling-http-requests)).*

## Why use it?
This makes APIs incredibly predictable and clean. It follows strict REST principles:
* `GET /api/projects` -> Get all
* `GET /api/projects/2` -> Get project 2
* `DELETE /api/projects/2` -> Delete project 2
* `PUT /api/projects/2` -> Update project 2
