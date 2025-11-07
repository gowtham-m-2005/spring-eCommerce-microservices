# User Service Authentication

This document describes the JWT-based authentication implementation for the User Service.

## Overview

The authentication system uses:
- **Spring Security** for security framework
- **JWT (JSON Web Tokens)** for stateless authentication
- **BCrypt** for password hashing
- **Role-based access control** (CUSTOMER, ADMIN)

## Architecture

### Components

1. **JwtUtil** - Handles JWT token generation and validation
2. **CustomUserDetailsService** - Loads user details from database
3. **JwtAuthenticationFilter** - Intercepts requests and validates JWT tokens
4. **SecurityConfig** - Configures Spring Security settings
5. **AuthService** - Handles registration and login business logic
6. **AuthController** - Exposes authentication endpoints

## API Endpoints

### Public Endpoints (No Authentication Required)

#### Register User
```
POST /api/auth/register
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "password": "securePassword123",
  "phoneNumber": "1234567890",
  "addressDto": {
    "street": "123 Main St",
    "city": "New York",
    "state": "NY",
    "zip": "10001",
    "country": "USA"
  }
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "userRole": "CUSTOMER"
}
```

#### Login
```
POST /api/auth/login
Content-Type: application/json

{
  "email": "john.doe@example.com",
  "password": "securePassword123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "userRole": "CUSTOMER"
}
```

### Protected Endpoints (Authentication Required)

All endpoints under `/api/users/**` require authentication.

#### Get All Users
```
GET /api/users
Authorization: Bearer <your-jwt-token>
```

#### Get User by ID
```
GET /api/users/{id}
Authorization: Bearer <your-jwt-token>
```

#### Create User
```
POST /api/users
Authorization: Bearer <your-jwt-token>
Content-Type: application/json

{
  "firstName": "Jane",
  "lastName": "Smith",
  "email": "jane.smith@example.com",
  "password": "password123",
  "phoneNumber": "9876543210"
}
```

#### Update User
```
PUT /api/users/{id}
Authorization: Bearer <your-jwt-token>
Content-Type: application/json

{
  "firstName": "Jane",
  "lastName": "Smith Updated",
  "email": "jane.smith@example.com",
  "phoneNumber": "9876543210"
}
```

## Configuration

### JWT Settings

Configure in `application.properties`:

```properties
# JWT Secret Key (Base64 encoded)
jwt.secret=${JWT_SECRET:404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970}

# JWT Token Expiration (in milliseconds, default: 24 hours)
jwt.expiration=${JWT_EXPIRATION:86400000}
```

### Environment Variables

For production, set these environment variables:
- `JWT_SECRET` - Your secret key for signing JWT tokens (should be a strong, random string)
- `JWT_EXPIRATION` - Token expiration time in milliseconds

## Security Features

1. **Password Encryption**: All passwords are hashed using BCrypt before storage
2. **Stateless Authentication**: JWT tokens enable stateless authentication
3. **Role-Based Access Control**: Users have roles (CUSTOMER, ADMIN) for authorization
4. **Token Validation**: Every request is validated for token authenticity and expiration
5. **CSRF Protection**: Disabled for stateless API (using JWT)
6. **Session Management**: Stateless session policy

## User Roles

- **CUSTOMER** - Default role for registered users
- **ADMIN** - Administrative privileges

Both roles can access `/api/users/**` endpoints. Additional role-specific restrictions can be added using `@PreAuthorize` annotations.

## Testing with Postman/cURL

### 1. Register a new user
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "password": "password123",
    "phoneNumber": "1234567890"
  }'
```

### 2. Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john.doe@example.com",
    "password": "password123"
  }'
```

### 3. Access protected endpoint
```bash
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer YOUR_JWT_TOKEN_HERE"
```

## Database Schema Changes

The `User` entity now includes:
- `password` field (required, not null)
- `email` field (unique, not null)
- Implementation of `UserDetails` interface for Spring Security

## Security Best Practices

1. **Never commit JWT secret keys** to version control
2. **Use strong, random secret keys** in production
3. **Set appropriate token expiration times** based on your security requirements
4. **Use HTTPS** in production to protect tokens in transit
5. **Implement refresh token mechanism** for better security (future enhancement)
6. **Add rate limiting** to prevent brute force attacks (future enhancement)

## Future Enhancements

- Refresh token mechanism
- Password reset functionality
- Email verification
- Rate limiting for login attempts
- Account lockout after failed attempts
- OAuth2 integration
- Two-factor authentication (2FA)
