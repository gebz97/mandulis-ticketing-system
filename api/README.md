# Mandulis Ticketing System

## Short term goals:
- Get a functional Backend API (With core ticketing features) working
- Build a minimal UI

### 1.1 Basic ticketing
- Basic CRUD on a ticket
- Tag System (No projects)
- Basic assignment to one or more users and/or one group

### 1.2 Basic user features
- Registration
- Login
- Other CRUD operations
- Basic RBAC (User/Agent/Manager/Admin)

### 1.3 Reporting
- Filtering
- Aggregations

---

### 2.1 Frontend Stack

Currently there is no frontend for this project (Only some minimal backend features). I'm still undecided on a frontend stack, but most probably it will be react due to its popularity (although I'm not a huge fan of it).


### How to run:
1. Install PostgreSQL server
2. Change the connection string, username and password in application.properties
3. Run the following command in a terminal:
```
mvn clean spring-boot:run
```
