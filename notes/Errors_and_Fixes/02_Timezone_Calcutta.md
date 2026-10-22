# Error: FATAL: invalid value for parameter "TimeZone": "Asia/Calcutta"

## The Error Log
```text
Caused by: org.postgresql.util.PSQLException: FATAL: invalid value for parameter "TimeZone": "Asia/Calcutta"
```
*(In automated tests, this often cascades into: `Unable to determine Dialect without JDBC metadata` because the connection is closed before Hibernate can read metadata).*

## What it means
This is a classic bug that happens specifically to developers in India using Windows! 
When Java connects to the PostgreSQL database, it automatically sends your computer's local timezone. Windows still uses the legacy name **"Asia/Calcutta"**. However, the modern Linux-based Postgres database inside your Docker container only recognizes the modern name **"Asia/Kolkata"** or standard **"UTC"**. 
Because the names don't match, Postgres immediately terminates the connection.

## The Testing Trap: Why `main()` is Not Enough!
Initially, developers fix this by adding:
```java
TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
```
inside `public static void main(String[] args)`.

While this works when running `mvn spring-boot:run` or launching the app directly, **automated tests (`@SpringBootTest`) will still fail!**
* **Why?** JUnit never calls `main(String[] args)`. It starts the Spring application context directly using reflection.
* As a result, the test runner stays on `Asia/Calcutta`, causing integration tests to crash when connecting to PostgreSQL.

## The Fix: Enforce UTC via a `static` Initializer Block

In Java, code inside a `static { ... }` block runs the moment the `.class` is loaded into the JVM — whether you are running `main()` or launching automated tests via JUnit!

**1. Update `BackendApplication.java`:**
```java
package com.devopshub.backend;

import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

    static {
        // Enforce UTC globally so PostgreSQL never rejects Windows "Asia/Calcutta"
        // Runs for both app execution AND automated test suites!
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}
```

**2. In Integration Test classes (`ProjectIntegrationTest.java`):**
You can also add the static block directly to your integration test to guarantee UTC execution in test JVM forks:
```java
@SpringBootTest
@AutoConfigureMockMvc
class ProjectIntegrationTest {

    static {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
    // ...
}
```
