# JPA Entities Explained

An **Entity** is simply a Java class that perfectly mirrors a Database Table. Every field in the class becomes a column in the database table.

To turn a normal Java class into an Entity, we use annotations provided by **JPA** (Java Persistence API).

*(All JPA annotations like `@Entity`, `@Table`, `@Id`, and `@GeneratedValue` are documented in [notes/ANNOTATIONS.md](../ANNOTATIONS.md#3-database--jpa-hibernate-entities)).*

## The Empty Constructor
You will notice the `Project` class has an empty constructor: `public Project() {}`. 
This is **strictly required** by JPA. When Spring Boot pulls data out of the database, it creates a blank `Project` object first, and then fills in the fields one by one. Without the empty constructor, Spring Boot will crash when trying to read from the database!
