# Error: Cannot Resolve Symbol 'MockBean' in Spring Boot 4

## The Error Log
```text
[ERROR] ProjectControllerTest.java: cannot find symbol
  symbol:   class MockBean
  location: package org.springframework.boot.test.mock.mockito
```

---

## What It Means

In Spring Boot 2 and 3, developers used `@MockBean` to inject a Mockito stunt double into Spring's ApplicationContext.  
However, in **Spring Boot 4 (Spring Framework 7)**, `@MockBean` was **completely removed** from Spring Boot because bean overriding was promoted into core Spring Framework itself.

---

## Common Causes

1. Migrating code or following older Spring Boot tutorials (Spring Boot 2.x / 3.x) that use `import org.springframework.boot.test.mock.mockito.MockBean;`.
2. Assuming `@MockBean` is still provided by `spring-boot-starter-test`.

---

## The Fix

Replace `@MockBean` with the official Spring Boot 4 replacement: **`@MockitoBean`**.

```java
// ❌ OLD (Spring Boot 2 / 3 - REMOVED in Spring Boot 4):
import org.springframework.boot.test.mock.mockito.MockBean;
@MockBean
private ProjectService projectService;

// ✅ NEW (Spring Boot 4):
import org.springframework.test.context.bean.override.mockito.MockitoBean;
@MockitoBean
private ProjectService projectService;
```
