# Basic Spring Security

Spring Security is incredibly powerful, but it is famous for being extremely aggressive. The moment you add the library to your project, it completely locks down **every single endpoint** in your application.

### The Default Behavior
If you add Spring Security to your `pom.xml` and do absolutely zero configuration:
1. Every API endpoint (GET, POST, etc.) will return a `401 Unauthorized` error.
2. If you visit your app in a web browser, Spring Boot will redirect you to a built-in login screen.
3. It automatically creates a default user named `user`.
4. It randomly generates a secure password every time you start the server and prints it in the terminal logs.

This is a great starting point, but obviously, for a real API, we don't want a random password changing every time we restart the server, and we don't want a web-browser login screen (we want JSON tokens!). 

We will disable this default behavior and build our own custom configuration in the next steps!
