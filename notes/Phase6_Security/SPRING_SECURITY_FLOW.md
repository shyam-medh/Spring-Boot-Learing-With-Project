# The Complete Spring Security & Database Flow

This document outlines the exact pipeline of what happens when we connect Spring Security to a real PostgreSQL database to authenticate users.

## 1. The Instant Lockdown (`spring-boot-starter-security`)
The moment you paste `<artifactId>spring-boot-starter-security</artifactId>` into your `pom.xml`, Spring Boot panics. It instantly locks every single endpoint in your app and generates a random UUID password in the terminal.
* **Why?** Spring's default behavior is: *"If they want security, lock the doors immediately until they tell me otherwise."*

## 2. Taking Control (`SecurityConfig.java`)
To stop the random passwords and take over, we create the Manager: `SecurityConfig.java`.
* **`@EnableWebSecurity`**: This tells Spring Boot: *"Stand down, I am the Manager now. I am taking over the security rules."*
* **The Decoder Ring (`PasswordEncoder`)**: Inside this file, we create a `@Bean` for the `BCryptPasswordEncoder`. This is mandatory because Spring flat-out refuses to handle or store passwords in plain-text. 

## 3. Building the Database Pipeline
Security is useless without real users in the database.
1. **The Entity (`User.java`)**: The blueprint that tells Spring how to create the `users` table in PostgreSQL.
2. **The Pantry Manager (`UserRepository.java`)**: The repository that contains `findByUsername(String username)` so we can actually query the database.
3. **The Seed Data (`DataInitializer.java`)**: A `CommandLineRunner` script that runs once at startup. It encrypts the word `"supersecret"` using BCrypt and saves an `admin` user into the database so we have someone to test with.

## 4. The Custom Bouncer (`CustomUserDetailsService.java`)
This is the core translation layer. Spring Security has no idea what PostgreSQL or our `User.java` Entity is. It only understands its own internal language: `UserDetails`. 
* We implement the `UserDetailsService` interface.
* **The Flow:** When a login happens, Spring Security is forced to trigger our `loadUserByUsername` method. We use our `UserRepository` to fetch the database User, translate it into Spring's `UserDetails` format using the `.builder()`, and hand it back to Spring Security.

## 5. Getting Access to the Endpoints (Basic Auth)
Now that the whole pipeline is connected, here is what happens when a request is made to a locked endpoint (e.g., `GET /api/projects`):
1. The user goes to Postman, selects **Basic Auth**, and types `admin` and `supersecret`.
2. The request hits the server. Spring Security intercepts it at the front door.
3. Spring Security sees the username `admin` and yells for our Custom Bouncer (`CustomUserDetailsService`).
4. The Bouncer fetches the encrypted password from PostgreSQL and hands it to Spring Security.
5. Spring Security takes the plain-text word `supersecret` that was typed in Postman, runs it through the BCrypt shredder, and compares it to the database's encrypted password.
6. **They match!** Spring Security opens the door, lets the request through to the Waiter (`ProjectController`), and returns the JSON response!
