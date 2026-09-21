# Spring Boot Validation (The Bouncer)

In a professional application, you can **never** trust the internet. If you expect a `name`, a malicious user might send `null`, an empty string `""`, or a string that is 10,000 characters long to try and crash your database.

To prevent this, we use the **Spring Boot Validation** library. It acts as a "Bouncer" at the door of your Controller.

### How it works
1. **The Rules:** You place annotations on your DTO fields (like `@NotBlank` or `@Size(min=2, max=50)`).
2. **The Enforcer:** You put the `@Valid` annotation in your Controller method next to the incoming data. 
3. **The Result:** If the internet sends bad data, the Controller instantly rejects the request with a `400 Bad Request` before the Service (Chef) ever sees it!

### Common Validation Annotations
* `@NotNull`: The field cannot be `null` (but it can be an empty string `""`).
* `@NotBlank`: The field cannot be `null` AND it cannot be empty spaces `"   "`. (This is usually what you want for Strings!)
* `@Size(min = 1, max = 255)`: Restricts the length of a String.
* `@Email`: Ensures the string is a valid email format.
