# Global Exception Handler (`@ControllerAdvice`)

When the Validation Bouncer (`@Valid`) rejects a request, Spring Boot throws a `MethodArgumentNotValidException` and returns a massive, ugly JSON response to the user.

To fix this, we use a **Global Exception Handler**. Think of this as a special "Customer Service Desk" for your entire application. Any time *any* Waiter (Controller) runs into a problem and throws an Exception, the Customer Service Desk intercepts it, formats an apology nicely, and hands it back to the internet.

### Do I need to memorize this?
**NO.** Absolutely no one memorizes this code. Senior developers copy and paste this exact file from their old projects into their new projects. Keep this note saved forever, and just copy-paste the code block below whenever you start a new Spring Boot project!

### The Code (Copy & Paste this into `exception/GlobalExceptionHandler.java`)

```java
package com.devopshub.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // 1. Tells Spring this is the Global Customer Service Desk
public class GlobalExceptionHandler {

    // 2. Tells Spring: "Listen for ANY validation errors in the app and send them here!"
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        
        Map<String, String> errors = new HashMap<>();
        
        // 3. Loop through the ugly error text, extract the field name and our custom message, and put it in a clean Map
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        return errors;
    }
}
```

### How it works
1. **`@RestControllerAdvice`**: You put this annotation on a class to declare it as the global Customer Service Desk. It sits above your entire app listening for crashes.
2. **`@ExceptionHandler`**: You put this on a method inside the class. It acts as a filter. You can tell it: *"Hey, if anyone ever throws a `MethodArgumentNotValidException` anywhere in the app, send it to this method."*
3. **The `forEach` loop**: The raw error from Spring Boot is a giant, nested Java object containing a list of all the fields that failed validation. We loop through that list, extract just the field name (`name` or `status`) and the clean message (`Project name can't be empty`), and shove it into a simple `HashMap` (which automatically turns into clean JSON!).
