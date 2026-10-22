# Error: Cannot Resolve Symbol 'WebMvcTest' in Spring Boot 4

## The Error Log
```text
[ERROR] ProjectControllerTest.java: package org.springframework.boot.test.autoconfigure.web.servlet does not exist
[ERROR] cannot find symbol class WebMvcTest
```

---

## What It Means

In Spring Boot 4, testing starters were refactored for improved modularity:
1. The old aggregate starter `spring-boot-starter-test` was split into specialized starters (e.g. `spring-boot-starter-webmvc-test`).
2. The package location for web testing annotations was reorganized from:
   `org.springframework.boot.test.autoconfigure.web.servlet.*`  
   to:  
   `org.springframework.boot.webmvc.test.autoconfigure.*`.

---

## The Fix

### 1. Update `pom.xml` Dependency
Ensure you have the modular starter:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webmvc-test</artifactId>
    <scope>test</scope>
</dependency>
```

### 2. Update the Java Imports
```java
// ❌ OLD (Spring Boot 2 / 3):
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

// ✅ NEW (Spring Boot 4):
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
```

---

⬅️ **Back to [Errors Catalog](README.md)** | **[Notes Master Index](../README.md)**
