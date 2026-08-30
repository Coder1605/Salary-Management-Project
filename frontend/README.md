# ACME Salary Management System

A full-stack employee salary management application built using **Spring Boot, Angular, and MySQL**.

## Features

* Employee management
* Add and view employees
* Employee search and filtering
* Pagination
* Salary management
* Salary analytics
* Dashboard
* Form validation
* REST APIs
* Unit testing with JUnit and Mockito

## Tech Stack

**Backend**

* Java 17
* Spring Boot
* Spring Data JPA / Hibernate
* MySQL
* JUnit 5 / Mockito
* Maven

**Frontend**

* Angular
* TypeScript
* HTML
* CSS

## API Endpoints

```text
GET    /api/employees
GET    /api/employees/{id}
POST   /api/employees
PUT    /api/employees/{id}/salary
GET    /api/analytics/summary
```

## Run Backend

```powershell
.\mvnw.cmd spring-boot:run
```

Backend:

```text
http://localhost:8080
```

## Run Frontend

```bash
npm install
ng serve
```

Frontend:

```text
http://localhost:4200
```

## Testing

```powershell
.\mvnw.cmd test
```

## Git

The project was developed incrementally using Git with commits for backend APIs, analytics, validation, tests, Angular pages, forms, and UI improvements.



## Author

**Kiran Giri**
