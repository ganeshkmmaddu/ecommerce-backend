# E-Commerce Backend

A backend application for an e-commerce platform built using **Spring Boot** and **PostgreSQL**. This project is being developed step-by-step while learning real-world backend development concepts and best practices.

---

## 1. Project Overview

This repository contains the backend service for an e-commerce platform. It follows a layered architecture with clear separation between controllers, services, repositories, and entities.

The project is designed as a **learning-focused, production-style codebase** — each milestone adds new backend concepts such as database integration, REST APIs, validation, and future modules like cart, orders, and authentication.

**Base package:** `com.example.ecommerce`

---

## 2. Tech Stack

| Technology | Purpose |
|------------|---------|
| Java 17 | Programming language |
| Spring Boot | Application framework |
| Spring Data JPA | Database access layer |
| Hibernate | ORM (Object-Relational Mapping) |
| PostgreSQL (Supabase) | Cloud-hosted relational database |
| Maven | Build and dependency management |
| Git | Version control |
| GitHub | Code hosting and collaboration |

---

## 3. Features Implemented

- Spring Boot project setup
- PostgreSQL database integration using Supabase
- Product Entity with JPA and validation annotations
- Product Repository (`JpaRepository`)
- Product Service with business logic
- Product Controller with REST APIs
- Basic Product CRUD APIs
- Hello API endpoint for health/testing
- GitHub integration

---

## 4. Project Structure

```
src/main/java/com/example/ecommerce/
├── EcommerceApplication.java      # Main Spring Boot application
├── controller/
│   ├── HelloController.java       # Hello endpoint
│   └── ProductController.java     # Product REST APIs
├── service/
│   └── ProductService.java        # Product business logic
├── repository/
│   └── ProductRepository.java     # Database access
└── entity/
    └── Product.java               # Product database model
```

**Layer responsibilities:**

| Layer | Responsibility |
|-------|----------------|
| `controller` | Handles HTTP requests and responses |
| `service` | Contains business logic |
| `repository` | Communicates with the database |
| `entity` | Defines database table structure |

---

## 5. How to Run Locally

### Prerequisites

- Java 17 or higher
- Maven (or use the included Maven Wrapper)
- A PostgreSQL database (Supabase recommended)
- Git

### Step 1: Clone the repository

```bash
git clone <your-repository-url>
cd ecommerce
```

### Step 2: Configure database connection

Update `src/main/resources/application-dev.properties` with your PostgreSQL (Supabase) credentials:

```properties
spring.datasource.url=jdbc:postgresql://<host>:5432/<database>
spring.datasource.username=<your-username>
spring.datasource.password=<your-password>

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

> **Note:** Do not commit real database passwords to GitHub. Use environment-specific configuration for production.

### Step 3: Run the application

**Windows:**

```bash
.\mvnw.cmd spring-boot:run
```

**macOS / Linux:**

```bash
./mvnw spring-boot:run
```

The application starts on **http://localhost:8080** by default.

### Step 4: Verify the setup

Open in your browser or use curl:

```bash
curl http://localhost:8080/hello
```

Expected response:

```
Hello Ecommerce Backend
```

---

## 6. API Endpoints

### Hello API

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/hello` | Returns a simple welcome message |

### Product APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/products` | Create a new product |
| `GET` | `/api/products` | Get all products |
| `GET` | `/api/products/{id}` | Get a product by ID |
| `PUT` | `/api/products/{id}` | Update a product by ID |
| `DELETE` | `/api/products/{id}` | Delete a product by ID |

### Example: Create a product

```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Wireless Mouse",
    "description": "Ergonomic wireless mouse",
    "price": 29.99,
    "stockQuantity": 50
  }'
```

### Example: Get all products

```bash
curl http://localhost:8080/api/products
```

### Product fields

| Field | Type | Required | Notes |
|-------|------|----------|-------|
| `name` | String | Yes | Cannot be blank |
| `description` | String | No | Optional |
| `price` | BigDecimal | Yes | Must be positive |
| `stockQuantity` | Integer | Yes | Must be zero or positive |

---

## 7. Future Roadmap

Planned enhancements for upcoming milestones:

- [ ] DTO Layer
- [ ] Validation improvements
- [ ] Global Exception Handling
- [ ] Category Module
- [ ] Product-Category Relationship
- [ ] Cart Module
- [ ] Order Module
- [ ] Spring Security
- [ ] JWT Authentication
- [ ] React Frontend
- [ ] Backend Deployment on Render
- [ ] Frontend Deployment on Vercel

---

## Learning Goals

This project is intentionally built in stages to practice:

- RESTful API design
- Layered architecture in Spring Boot
- JPA entity mapping and repository patterns
- Database integration with PostgreSQL
- Version control and GitHub workflow
- Preparing for production-ready features like security, DTOs, and deployment

---

## Author

Built as part of a hands-on backend development learning journey.
