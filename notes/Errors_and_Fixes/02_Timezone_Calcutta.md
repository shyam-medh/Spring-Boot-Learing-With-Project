# Error: FATAL: invalid value for parameter "TimeZone": "Asia/Calcutta"

## The Error Log
```text
Caused by: org.postgresql.util.PSQLException: FATAL: invalid value for parameter "TimeZone": "Asia/Calcutta"
```

## What it means
This is a classic bug that happens specifically to developers in India using Windows! 
When Java connects to the PostgreSQL database, it automatically sends your computer's local timezone. Windows still uses the old name **"Asia/Calcutta"**. However, the modern Linux-based Postgres database inside your Docker container only recognizes the modern name **"Asia/Kolkata"**. 
Because the names don't match, Postgres crashes and rejects the connection!

## The Fix
We need to force Java to use a timezone that Postgres recognizes, like "UTC" (Global Standard Time) or "Asia/Kolkata". 

We can do this by setting a default timezone in our main Application class before Spring Boot even starts.

**Fix inside `BackendApplication.java`:**
```java
package com.devopshub.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.TimeZone; // Add this import

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        // Force Java to use UTC time so Postgres doesn't crash!
        TimeZone.setDefault(TimeZone.getTimeZone("UTC")); 
        
        SpringApplication.run(BackendApplication.class, args);
    }
}
```
