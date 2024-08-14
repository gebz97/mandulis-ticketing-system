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

Currently the frontend stack is react


### How to run the API:
1. Install PostgreSQL server
2. Create a file called ```.env``` in the root of the API with the following:
```
DB_PASSWORD=<USERNAME>
DB_USERNAME=<PASSWORD>
DB_URL=jdbc:postgresql://<HOST>:<PORT>/<DATABASE>
```
4. Run the following command in a terminal:
```
mvn clean spring-boot:run
```
