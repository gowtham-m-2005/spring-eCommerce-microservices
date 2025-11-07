
# E-Commerce Microservices with Docker & Spring Boot

A distributed **E-Commerce application** built using **Spring Boot 3**, **Spring Cloud Netflix Eureka**, and **Docker Compose**.
This project demonstrates real microservices architecture with separate services for **User**, **Product**, and **Cart** — each running in isolated containers and communicating via **OpenFeign** through a **Service Registry**.
🚧 In Active Development...

---

## 🚀 Technologies Used

* **Java 17**
* **Spring Boot 3**
* **Spring Cloud Netflix Eureka** for service discovery
* **Spring Data JPA** for database persistence
* **PostgreSQL** as the relational database (containerized)
* **OpenFeign** for inter-service communication
* **Docker & Docker Compose** for containerization and orchestration
* **Maven** as the build automation tool

---

## 🧩 Architecture Overview

This system currently consists of four microservices:

| Service              | Role                                                           |
| -------------------- | -------------------------------------------------------------- |
| **user-service**     | Manages user data and CRUD operations                          |
| **product-service**  | Handles product management, search, and updates                |
| **cart-service**     | Manages user carts and interactions between users and products |
| **service-registry** | Eureka server for service registration and discovery           |

---

### ⚙️ Service Communication Flow

```
Client
   ↓
User / Product / Cart Service
   ↓ (via Eureka)
Service Registry (Eureka)
   ↓
PostgreSQL Databases (in Docker)
```

Each service is independently containerized and connected through a shared Docker network, allowing seamless communication and isolated scaling.

---

## Features Implemented

### User Service

* CRUD operations for user profiles
* Integration with PostgreSQL

### Product Service

* Create, update, delete, and search products
* REST API with JPA persistence
* Exposed via `/api/products`

### Cart Service

* Add items to user cart
* Validates user and product existence via Feign clients
* Exposed via `/api/cart`

### Service Registry

* Centralized Eureka server for all service discovery
* Enables dynamic lookup and communication

---

## 🐳 Docker Compose Setup

The project uses a single `docker-compose.yml` file to orchestrate all services:

```bash
# Build and start all containers in detached mode
docker compose up -d

# Stop and remove containers
docker compose down
```
---

## 📡 API Endpoints Overview

### User Service (`/api/users`)

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

## 🧭 Status

This project is **still in active development**, with core microservices containerized and running via Docker Compose.
More features and services coming soon 🚧
