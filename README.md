# Student Directory CRUD App

A beginner-friendly Java web application for managing a small student directory.

## 1) What the app does
This app lets you:
- View all students in a table
- Add a new student
- Edit a selected student
- Delete a selected student
- Search students by first or last name

Each student record includes:
- Student ID
- First name
- Last name
- Email

The app starts with a few sample students so you can demo it immediately.

## 2) Technologies used
- Java 17
- Spring Boot
- Maven
- Vaadin (web UI)
- Spring Data JPA
- H2 in-memory database

## 3) Prerequisites
- Java 17 installed
- Git installed

No separate database installation is needed because H2 is embedded and in-memory.

## 4) Exact run instructions
From the repository root (`/home/runner/work/student-crud-app/student-crud-app`):

### macOS/Linux
```bash
./mvnw spring-boot:run
```

### Windows (Command Prompt or PowerShell)
```bat
mvnw.cmd spring-boot:run
```

## 5) Local URL
Open this URL in your browser:
- http://localhost:8080

(Optional H2 console):
- http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:studentdb`
  - Username: `sa`
  - Password: *(leave blank)*

## 6) How to demonstrate CRUD
1. **Create**: Fill in Student ID, First name, Last name, and Email, then click **Save**.
2. **Read**: See all records in the grid/table.
3. **Update**: Click a student in the grid, edit fields, then click **Update**.
4. **Delete**: Click a student in the grid, then click **Delete**.
5. **Search/Filter**: Type a first or last name in the search field to filter the table.

Validation/helpful messages:
- Required fields are validated.
- Email format is validated.
- Success/error notifications are shown after add/update/delete attempts.
- Student ID and email must be unique.

## 7) Important project files (beginner guide)
- `pom.xml`  
  Maven project configuration and dependencies (Spring Boot, Vaadin, JPA, H2, testing).

- `mvnw` / `mvnw.cmd` / `.mvn/wrapper/maven-wrapper.properties`  
  Maven Wrapper files so you can run Maven consistently without manually installing the exact Maven version.

- `src/main/java/com/example/studentcrud/StudentCrudApplication.java`  
  Main Spring Boot entry point.

- `src/main/java/com/example/studentcrud/entity/Student.java`  
  JPA entity class that defines the Student table fields and validation rules.

- `src/main/java/com/example/studentcrud/repository/StudentRepository.java`  
  Spring Data repository for CRUD and name search.

- `src/main/java/com/example/studentcrud/service/StudentService.java`  
  Service layer used by the UI for student operations.

- `src/main/java/com/example/studentcrud/ui/MainView.java`  
  Vaadin UI view containing the table, form, buttons, search, and notifications.

- `src/main/java/com/example/studentcrud/config/DataInitializer.java`  
  Loads sample students at startup.

- `src/main/resources/application.properties`  
  Application settings for H2 and JPA.

- `src/test/java/com/example/studentcrud/StudentCrudApplicationTests.java`  
  Basic Spring Boot context-load test.

## 8) Useful Git commands
Check status:
```bash
git status
```

Stage and commit:
```bash
git add .
git commit -m "Build student directory CRUD app"
```

Push:
```bash
git push
```
