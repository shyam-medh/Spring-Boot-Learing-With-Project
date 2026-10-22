# Error: Route Mismatches Caused by Missing Leading Slash in @RequestMapping

## The Symptom
```text
SecurityConfig rules for "/api/projects/**" fail to match Controller endpoints,
or MockMvc requests encounter Handler: Type = null.
```

---

## What It Means

In Spring MVC controllers, developers sometimes write:
```java
@RequestMapping("api/projects") // Missing leading slash!
```
While Spring MVC's internal DispatcherServlet often forgives this by normalizing the path, **Spring Security's AntPathMatcher / PathPatternParser** is strictly configured around standard URI conventions starting with `/` (`/api/projects/**`).

A missing leading slash can cause subtle bugs where:
* Spring Security fails to match the URL pattern.
* Security rules (like `hasRole("ADMIN")`) may not apply as expected.
* MockMvc requests fail to resolve the proper handler method.

---

## The Fix

Always prepend a leading forward slash to `@RequestMapping`, `@GetMapping`, `@PostMapping`, etc.:

```java
// ❌ Avoid:
@RequestMapping("api/projects")

// ✅ Correct:
@RequestMapping("/api/projects")
@RestController
public class ProjectController { ... }
```

---

⬅️ **Back to [Errors Catalog](README.md)** | **[Notes Master Index](../README.md)**
