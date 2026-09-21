# Database Connection & Configuration

To connect a Spring Boot application to a real database (like PostgreSQL), we need two main components: Dependencies and Configuration.

## 1. Dependencies (`pom.xml`)

We must add two specific tools to our project:

1. **Spring Data JPA**: `spring-boot-starter-data-jpa`
   * **What it does:** This is the tool that automatically writes SQL queries for us. It translates our Java objects into Database Tables, and our Java methods into SQL statements (like `SELECT`, `INSERT`, `DELETE`).
   
2. **PostgreSQL Driver**: `postgresql`
   * **What it does:** This is the specific language translator that allows Java's general database system to speak to PostgreSQL specifically. (If we were using MySQL, we would use the MySQL driver instead).

## 2. Configuration (`application.yaml`)

We configure the database connection in `src/main/resources/application.yaml`.

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/devopshub
    username: myuser
    password: secret
    
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### Key Properties Explained:
* **`datasource.url`**: Tells Java exactly where the database is located and what the database is named (`devopshub`).
* **`datasource.username / password`**: The credentials to log in.
* **`jpa.hibernate.ddl-auto: update`**: Instructs Spring Boot to automatically look at our Java code and generate/update the SQL Database Tables to match. We don't have to manually write `CREATE TABLE` scripts.
* **`jpa.show-sql: true`**: Forces Spring Boot to print all the raw SQL it writes directly into our terminal so we can see what's happening.
