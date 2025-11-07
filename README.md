# E-Commerce Microservices with Docker & Spring Boot

A distributed **E-Commerce application** built using **Spring Boot 3**, **Spring Cloud Netflix Eureka**, and **Docker Compose**.
This project demonstrates real microservices architecture with separate services for **User**, **Product**, and **Cart** — each running in isolated containers and communicating via **OpenFeign** through a **Service Registry**.
🚧 In Active Development...

---

## Technologies Used

* **Java 17**
* **Spring Boot 3**
* **Spring Cloud Netflix Eureka** for service discovery
* **Spring Cloud Config Server** for centralized configuration management
* **Spring Cloud Gateway** for API gateway and routing
* **Spring Security** with JWT for stateless authentication
* **Spring Data JPA** for database persistence
* **PostgreSQL** as the relational database
* **OpenFeign** for inter-service communication
* **BCrypt** for password hashing
* **JJWT (0.12.3)** for JWT token generation and validation
* **Docker & Docker Compose** for containerization and orchestration
* **Maven** as the build automation tool

---

## 🧩 Architecture Overview

This system currently consists of seven microservices:

| Service              | Role                                                                    |
| -------------------- | ----------------------------------------------------------------------- |
| **gateway**          | API Gateway for routing and centralized entry point                     |
| **config-server**    | Centralized configuration management for all microservices              |
| **service-registry** | Eureka server for service registration and discovery                    |
| **user-service**     | Manages user authentication, authorization, and user data               |
| **product-service**  | Handles product management, search, and updates                         |
| **cart-service**     | Manages user carts and interactions between users and products          |

---

### ⚙️ Service Communication Flow

```
                    ┌──────────────────┐
                    │  Config Server   │
                    │   (Port 8888)    │
                    └────────┬─────────┘
                             │
            Startup: Fetch Configuration
                             │
         ┌───────────────────┼───────────────────┐
         ↓                   ↓                   ↓
    ┌─────────┐      ┌──────────────┐     ┌──────────┐
    │ Gateway │      │ User Service │     │ Product  │
    │  8080   │      │              │     │ Service  │
    └────┬────┘      └──────┬───────┘     └────┬─────┘
         │                  │                   │
         └──────────────────┼───────────────────┘
                            ↓
                ┌───────────────────────┐
                │  Service Registry     │
                │  (Eureka - Port 8761) │
                └───────────────────────┘
                            ↑
                            │
                   Services register here
                            │
┌──────────┐                │
│  Client  │────Request────>│
└──────────┘                ↓
                      ┌─────────┐
                      │ Gateway │
                      └────┬────┘
                           │
          ┌────────────────┼────────────────┐
          ↓                ↓                ↓
    ┌──────────┐    ┌──────────┐    ┌──────────┐
    │   User   │    │ Product  │    │   Cart   │
    │ Service  │    │ Service  │    │ Service  │
    └────┬─────┘    └────┬─────┘    └────┬─────┘
         ↓               ↓               ↓
    ┌─────────┐    ┌─────────┐    ┌─────────┐
    │ userDb  │    │productDb│    │ cartDb  │
    └─────────┘    └─────────┘    └─────────┘
```
---

## Features Implemented

### User Service

* **JWT-based Authentication & Authorization**
  - User registration with BCrypt password hashing
  - Login with email and password
  - Stateless authentication using JWT tokens (24-hour expiration)
  - Role-based access control (CUSTOMER, ADMIN)
  - Custom UserDetails implementation
* CRUD operations for user profiles
* Integration with PostgreSQL
* Secure password storage with BCrypt (cost factor: 10)

### Product Service

* Create, update, delete, and search products
* REST API with JPA persistence
* Exposed via `/api/products`

### Cart Service

* Add items to user cart
* Validates user and product existence via Feign clients
* Exposed via `/api/cart`

### Config Server

* Centralized configuration management for all microservices
* External property sources for database, JWT, and service configurations
* Environment-specific configurations (dev, docker, production)

### API Gateway

* Single entry point for all client requests
* Intelligent routing to appropriate microservices
* Load balancing across service instances
* Security configuration for public and protected endpoints
* CORS handling

### Service Registry

* Centralized Eureka server for all service discovery
* Enables dynamic lookup and communication
* Health monitoring and service status tracking

---

## 🐳 Docker Compose Setup

The project uses a single [docker-compose.yml](cci:7://file:///D:/Java%20Projects/Micro%20Services/spring-eCommerce-microservices/docker-compose.yml:0:0-0:0) file to orchestrate all services:

```bash
# Build and start all containers in detached mode
docker compose up -d

# Stop and remove containers
docker compose down
```

---

## 📡 API Endpoints Overview

### Authentication Endpoints (`/api/auth`) - Public

| Method | Endpoint    | Description           | Request Body                                    |
| ------ | ----------- | --------------------- | ----------------------------------------------- |
| POST   | `/register` | Register new user     | `{email, password, firstName, lastName, ...}`   |
| POST   | `/login`    | Login existing user   | `{email, password}`                             |

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "userRole": "CUSTOMER"
}
```

### User Service (`/api/users`) - Protected

> **Note:** Requires JWT token in `Authorization: Bearer <token>` header

| Method | Endpoint | Description    |
| ------ | -------- | -------------- |
| GET    | `/`      | Get all users  |
| GET    | `/{id}`  | Get user by ID |
| POST   | `/`      | Add new user   |
| PUT    | `/{id}`  | Update user    |

### Product Service (`/api/products`)

| Method | Endpoint  | Description             |
| ------ | --------- | ----------------------- |
| POST   | `/`       | Create new product      |
| PUT    | `/{id}`   | Update existing product |
| GET    | `/`       | Get all products        |
| GET    | `/{id}`   | Get product by ID       |
| DELETE | `/{id}`   | Delete a product        |
| GET    | `/search` | Search by keyword       |

### Cart Service (`/api/cart`)

| Method | Endpoint | Description      |
| ------ | -------- | ---------------- |
| POST   | `/`      | Add item to cart |

---

### Environment Variables

```bash
# JWT Configuration
JWT_SECRET=your_base64_encoded_secret_key_here
JWT_EXPIRATION=86400000  # 24 hours in milliseconds

# Database Configuration
DB_URI=jdbc:postgresql://localhost:5432/userDb
DB_USER=postgres
DB_PASSWORD=your_password
```

---

## 🧭 Status

This project is **still in active development**, with core microservices containerized and running via Docker Compose.
---



