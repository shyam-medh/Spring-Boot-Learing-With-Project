# Error: NoSuchBeanDefinitionException for ObjectMapper in @WebMvcTest

## The Error Log
```text
org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'com.devopshub.backend.controller.ProjectControllerTest': 
Unsatisfied dependency expressed through field 'objectMapper': 
No qualifying bean of type 'com.fasterxml.jackson.databind.ObjectMapper' available: 
expected at least 1 bean which qualifies as autowire candidate.
```

---

## What It Means

In a unit test or slice test annotated with `@WebMvcTest`, the developer tried to inject Jackson's `ObjectMapper` using:
```java
@Autowired
private ObjectMapper objectMapper;
```
However, Spring Boot slice tests only load a minimal subset of beans. In Spring Boot 4's modularized test starters, an `ObjectMapper` bean is not always registered automatically as an autowirable candidate in the application context.

---

## The Fix

Do **not** `@Autowired` the `ObjectMapper`. Simply instantiate it directly as a standard Java field:

```java
// Direct instantiation: fast, reliable, zero Spring context dependency
private final ObjectMapper objectMapper = new ObjectMapper();
```

This guarantees `objectMapper.writeValueAsString(dto)` is always ready to convert Java DTOs to JSON strings without Spring DI errors.
