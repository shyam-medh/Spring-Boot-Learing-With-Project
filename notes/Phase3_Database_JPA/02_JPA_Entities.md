# JPA Entities Explained

An **Entity** is simply a Java class that perfectly mirrors a Database Table. Every field in the class becomes a column in the database table.

To turn a normal Java class into an Entity, we use annotations provided by **JPA** (Java Persistence API).

## Key Annotations

### `@Entity`
This is the most important annotation. It tells Spring Boot: *"This class is not just a normal Java class. I want you to create a database table for it."*

### `@Table(name = "projects")`
By default, if your class is named `Project`, Spring will name the database table `project`. However, it is a standard Database convention to make table names plural (e.g., `projects`, `users`). This annotation lets you manually specify the exact name of the table in the database.

### `@Id`
Every single database table must have a **Primary Key** (a unique identifier for every row, so we don't confuse two projects with the same name). Placing `@Id` above a field (usually `id`) tells Spring Boot to make this field the Primary Key column.

### `@GeneratedValue(strategy = GenerationType.IDENTITY)`
If you don't use this, you would have to manually figure out what ID to give a new project every time you create one. `GenerationType.IDENTITY` tells the PostgreSQL database: *"Please handle the IDs for me. Just auto-increment them (1, 2, 3...) every time I insert a new row."*

## The Empty Constructor
You will notice the `Project` class has an empty constructor: `public Project() {}`. 
This is **strictly required** by JPA. When Spring Boot pulls data out of the database, it creates a blank `Project` object first, and then fills in the fields one by one. Without the empty constructor, Spring Boot will crash when trying to read from the database!
