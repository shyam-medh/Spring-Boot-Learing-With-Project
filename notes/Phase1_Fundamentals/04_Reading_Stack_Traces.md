# How to Read a Java Stack Trace

When your application crashes, Java spits out a massive wall of text called a **Stack Trace**. It can be hundreds of lines long and look incredibly intimidating. 

Here is the secret to reading them like a senior engineer:

### 1. Scroll to the very bottom!
Java stack traces are printed "inside-out". 
The top of the log usually just says a generic *"Application Failed to Start"*. 
To find the **actual root cause**, you should immediately scroll to the very bottom of the log and scan upwards until you see the last **`Caused by:`** statement.

In our previous error, the very bottom of the log looked like this:
```text
Caused by: org.postgresql.util.PSQLException: FATAL: invalid value for parameter "TimeZone": "Asia/Calcutta"
```
Boom. Instantly readable.

### 2. Look for your own code
Right underneath the `Caused by:` line, you will see a list of files (`at org.springframework...`, `at org.hibernate...`). 

Most of these are internal library files that you didn't write. You should scan down that list until you see **your own package name** (e.g., `com.devopshub.backend...`). 

When you find your package name, look at the end of the line. It will tell you the exact file and the **exact line number** (e.g., `ProjectService.java:24`) where the crash started in your code! 

*(Note: In our timezone error, our code wasn't listed because the crash happened deep inside Spring Boot's automatic startup process before our code even ran!)*
