# Phase 5: Validation & Error Handling Master Guide 🛡️

Welcome to the **Phase 5 Validation & Error Handling Guide**! This directory details how to validate incoming HTTP payloads declaratively using Jakarta Bean Validation, and how to catch and format exceptions centrally using `@RestControllerAdvice`.

---

## 🎯 Phase Goals & Outcomes
* Protect endpoints against malformed data using Jakarta Bean Validation annotations (`@Valid`, `@NotBlank`, `@Size`, `@NotNull`).
* Understand why validation must occur at the Controller level *before* hitting the business service layer.
* Replace generic stack trace HTML errors with standardized, user-friendly JSON error responses.
* Implement a centralized **Customer Service Desk** using `@RestControllerAdvice` and `@ExceptionHandler`.
* Create custom runtime exceptions (like `ResourceNotFoundException`) that automatically map to `404 Not Found`.

---

## 📚 Phase 5 Notes & Curriculum

| # | Note | Topic | Key Takeaway |
|---|---|---|---|
| **01** | [01_Bean_Validation.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase5_Validation/01_Bean_Validation.md) | Jakarta Bean Validation | Adding `@NotBlank`, `@Size`, and `@Valid` to DTOs to enforce input rules and trigger `400 Bad Request`. |
| **02** | [02_Global_Exception_Handler.md](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/Phase5_Validation/02_Global_Exception_Handler.md) | Centralized Exception Handling | Building a `@RestControllerAdvice` class that catches `MethodArgumentNotValidException` and custom 404s. |

---

## 💡 Key Mental Model: The Bouncer at the Door & The Customer Service Desk
* **`@Valid` (The Bouncer at the Door):** Inspects incoming JSON packets. If required fields like `name` or `status` are missing or blank, the request is immediately rejected with `400 Bad Request` before the Service ever wakes up.
* **`@RestControllerAdvice` (The Customer Service Desk):** When anything goes wrong anywhere in the application (like a missing project ID throwing `ResourceNotFoundException`), the exception flies to the Customer Service Desk. The desk catches it, formats a clean JSON payload with timestamps and error messages, and returns the appropriate HTTP status code (`404 Not Found`).

---

⬅️ **Back to [Notes Master Index](file:///s:/Shyam/Grill/Working%20on/learning/playground/java/spring%20boot/notes/README.md)**
