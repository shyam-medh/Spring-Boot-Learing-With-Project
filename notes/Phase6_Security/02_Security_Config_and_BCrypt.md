# Security Configuration & Password Encoding

By default, Spring Security uses a randomly generated password. In the real world, we need to take control of this configuration.

### 1. The Security Configuration File
To override Spring Security's default behavior, we create a dedicated security configuration class. This tells Spring: *"Don't use your default lock; use my custom rules instead!"*

*(For full details on `@Configuration` and `@EnableWebSecurity`, see [notes/ANNOTATIONS.md](../ANNOTATIONS.md#6-spring-security)).*

### 2. Password Encoding (BCrypt)
If you save a password like `mySecret123` into a database as plain text, you will get fired. If a hacker breaches your database, they will instantly see everyone's passwords!

To fix this, we use a **Password Encoder**. Spring uses **BCrypt**, a powerful algorithm that hashes passwords. 
`mySecret123` turns into something like `$2a$10$X8/X5Yq9.T5G6T1/...`

Even if a hacker steals the database, they cannot reverse the hash back into the original password!

In Spring Security, you *must* define a `PasswordEncoder` Bean, or else it will refuse to authenticate anyone for safety reasons.
