
---

# 🚀 📁 README for Backend (`OCMS-Backend`)

```markdown
# 📚 Online Course Management System (Backend)

## 🔹 Description
This is the backend of the Online Course Management System (OCMS) built using Spring Boot. It provides REST APIs for authentication, course management, and enrollment.

---

## 🚀 Features

- User Registration & Login
- Role-based access (Admin & Student)
- Course creation and deletion (Admin)
- Course enrollment (Student)
- Course content management

---

## 🛠 Tech Stack
- Java
- Spring Boot
- Spring Security
- JPA (Hibernate)
- MySQL

---

## 📁 Project Structure
```
controller/
├── AuthController.java      # Handles login & register APIs
├── CourseController.java    # Manages courses

service/
├── UserService.java         # Business logic for users
├── CourseService.java       # Business logic for courses

repository/
├── UserRepository.java      # User database operations
├── CourseRepository.java    # Course database operations

entity/
├── User.java                # User model
├── Course.java              # Course model
```
---

## 🔍 Key Components

- **AuthController**: Handles login and registration APIs
- **CourseController**: Manages course CRUD operations
- **Service Layer**: Contains business logic
- **Repository Layer**: Handles database operations
- **Entities**: Represent database tables

---

## 🔐 Authentication
- Role-based login (Admin & Student)
- Token-based authentication (JWT if implemented)

---

## 🔗 Frontend Repository
👉 https://github.com/Jvivekvardhan/OCMS-Project

---

## ▶️ How to Run

1. Open project in Eclipse
2. Run as Spring Boot Application

Runs on:
