# Path Variables

In REST APIs, we often need to perform actions on a *specific* resource. For example, we don't want to just "delete projects," we want to "delete project with ID 2."

To do this, we include the ID directly in the URL: `http://localhost:8080/api/projects/2`

## 1. `@PathVariable`
Spring allows us to extract that `2` out of the URL and pass it into our Java method using the `@PathVariable` annotation.

Example:
```java
@DeleteMapping("/{id}")
public String deleteProject(@PathVariable Long id) {
    // Spring extracts the {id} from the URL and puts it in the Long variable
    projectService.removeProject(id);
    return "Deleted!";
}
```

## Why use it?
This makes APIs incredibly predictable and clean. It follows strict REST principles:
* `GET /api/projects` -> Get all
* `GET /api/projects/2` -> Get project 2
* `DELETE /api/projects/2` -> Delete project 2
* `PUT /api/projects/2` -> Update project 2
