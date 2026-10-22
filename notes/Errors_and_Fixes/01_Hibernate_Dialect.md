# Error: Unable to determine Dialect without JDBC metadata

## The Error Log
```text
Caused by: org.hibernate.service.spi.ServiceException: Unable to create requested service [org.hibernate.engine.jdbc.env.spi.JdbcEnvironment] due to: Unable to determine Dialect without JDBC metadata (please set 'jakarta.persistence.jdbc.url' for common cases or 'hibernate.dialect' when a custom Dialect implementation must be provided)
Caused by: org.hibernate.HibernateException: Unable to determine Dialect without JDBC metadata
```

## What it means
This error occurs when Spring Boot (specifically Hibernate) tries to connect to the database on startup to check its version and configure itself, but the connection **fails or drops**. Because it cannot complete the connection, it cannot query JDBC metadata to determine the "Dialect" (the specific version of SQL to generate for Postgres).

## Common Causes
1. **The Database is not running:** You forgot to start the Docker container (`docker start devopshub-db`).
2. **Wrong Port / Network:** The database is running on a different port than `5432` or mapped improperly in Docker.
3. **Connection Dropped on Handshake:** The database started, but rejected the incoming JDBC connection during initial handshake (e.g. Windows `Asia/Calcutta` timezone mismatch — see [02_Timezone_Calcutta.md](02_Timezone_Calcutta.md)).
4. **Integration Tests (`@SpringBootTest`):** Unlike `@WebMvcTest` (which mocks the DB), `@SpringBootTest` attempts to connect to the real database. If Docker is offline or timezone handling is only in `main()`, integration tests crash with this error.

## The Fix
1. Ensure the Docker container is actually running:
   ```powershell
   docker ps
   docker start devopshub-db
   ```
2. Force the application to use IPv4 by using `127.0.0.1` instead of `localhost`.
3. **Explicitly configure the Hibernate Dialect** in `application.yaml` so Hibernate does not have to rely on inspecting metadata:
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://127.0.0.1:5432/devopshub
       username: myuser
       password: secret
       driver-class-name: org.postgresql.Driver

     jpa:
       hibernate:
         ddl-auto: update
       show-sql: true
       properties:
         hibernate:
           dialect: org.hibernate.dialect.PostgreSQLDialect
   ```
4. If running automated tests, ensure UTC timezone is enforced globally via a `static` initializer block (see [02_Timezone_Calcutta.md](02_Timezone_Calcutta.md)).

---

⬅️ **Back to [Errors Catalog](README.md)** | **[Notes Master Index](../README.md)**
