# Spring WebFlux Exchange Rate API

A reactive Spring Boot application built with WebFlux that provides currency exchange rate functionality. The application fetches exchange rates from an external API and caches them in a PostgreSQL database for improved performance.

## 📋 Table of Contents

- [Overview](#overview)
- [Technologies](#technologies)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Features](#features)
- [API Endpoints](#api-endpoints)
- [Database Schema](#database-schema)
- [Configuration](#configuration)
- [Getting Started](#getting-started)
- [Running with Docker](#running-with-docker)
- [Environment Variables](#environment-variables)
- [External Dependencies](#external-dependencies)

## 🔍 Overview

This project is a reactive microservice that provides currency exchange rate information. It implements a caching strategy where exchange rates are first checked in the local PostgreSQL database, and if not found, they are fetched from an external API (exchangerate-api.com) and then cached for future requests.

## 🚀 Technologies

- **Java 21** - Programming language
- **Spring Boot 3.5.6** - Framework
- **Spring WebFlux** - Reactive web framework
- **Spring Data R2DBC** - Reactive database connectivity
- **PostgreSQL 15** - Database
- **R2DBC PostgreSQL Driver** - Reactive database driver
- **Lombok** - Code generation library
- **Maven** - Build tool
- **Docker & Docker Compose** - Containerization

## 🏗️ Architecture

The application follows a reactive architecture pattern with these layers:

```
Controller Layer (REST endpoints)
    ↓
Service Layer (Business logic)
    ↓
Repository Layer (Database operations)
    ↓
External API Integration
```

### Key Components:

1. **ExchangeController** - REST controller exposing exchange rate endpoints
2. **ExchangeService** - Business logic handling caching strategy
3. **ExternalAPI** - Service for fetching data from external exchange rate API
4. **ExchangeRepo** - R2DBC repository for database operations
5. **ExchangeMapper** - Maps between DTOs and entities
6. **GlobalExceptionHandler** - Centralized error handling

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/webflux/test/
│   │   ├── TestApplication.java          # Main application class
│   │   ├── controller/
│   │   │   └── ExchangeController.java   # REST endpoints
│   │   ├── service/
│   │   │   ├── IExchangeService.java     # Service interface
│   │   │   ├── ExchangeService.java      # Service implementation
│   │   │   └── ExternalAPI.java          # External API client
│   │   ├── repository/
│   │   │   └── ExchangeRepo.java         # R2DBC repository
│   │   ├── model/
│   │   │   └── Exchange.java             # Entity model
│   │   ├── dto/
│   │   │   └── ExternalAPIDTO.java       # External API response DTO
│   │   ├── mapper/
│   │   │   └── ExchangeMapper.java       # DTO-Entity mapper
│   │   └── exception/
│   │       ├── ErrorResponse.java        # Error response model
│   │       └── GlobalExceptionHandler.java # Exception handler
│   └── resources/
│       └── application.yaml              # Application configuration
├── docker-compose.yml                    # Docker composition
├── init/
│   └── init.sql                          # Database initialization
└── pom.xml                               # Maven configuration
```

## ✨ Features

- **Reactive Programming**: Built with Spring WebFlux for non-blocking I/O operations
- **Caching Strategy**: Database-first approach with external API fallback
- **Automatic Data Persistence**: Exchange rates are automatically saved when fetched from external API
- **Validation**: Input validation for currency codes (3-character ISO codes)
- **Error Handling**: Comprehensive error handling with detailed error responses
- **Health Checks**: PostgreSQL health monitoring in Docker setup
- **Environment Configuration**: Flexible configuration via environment variables

## 🌐 API Endpoints

### Get Exchange Rate

```http
GET /api/v1/exchange/from/{fromCurrency}/to/{toCurrency}
```

**Parameters:**
- `fromCurrency` (path): Source currency code (3 characters, e.g., "USD")
- `toCurrency` (path): Target currency code (3 characters, e.g., "EUR")

**Response:**
```json
200.75
```

**Example:**
```bash
curl http://localhost:8080/api/v1/exchange/from/USD/to/EUR
```

## 🗄️ Database Schema

### exchange_data table

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | SERIAL | PRIMARY KEY | Auto-generated ID |
| from_currency | VARCHAR(15) | NOT NULL | Source currency code |
| to_currency | VARCHAR(15) | NOT NULL | Target currency code |
| exchange_rate | FLOAT | NOT NULL | Exchange rate value |

**Sample Data:**
```sql
INSERT INTO exchange_data (from_currency, to_currency, exchange_rate)
VALUES ('PEN', 'USD', 3.75);
```

## ⚙️ Configuration

### Application Configuration (application.yaml)

```yaml
spring:
  application:
    name: webflux-crud
  r2dbc:
    url: ${DATABASE_URL:r2dbc:postgresql://localhost:5432/testdb}
    username: ${POSTGRES_USER:admin}
    password: ${POSTGRES_PASSWORD:admin}
    pool:
      initial-size: 5
      max-size: 20
      max-idle-time: 30m
      validation-query: SELECT 1
```

## 🚀 Getting Started

### Prerequisites

- Java 21
- Maven 3.6+
- Docker & Docker Compose
- PostgreSQL 15 (if running locally)

### Local Development Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd test
   ```

2. **Set up environment variables**
   Create a `.env` file in the project root:
   ```env
   POSTGRES_USER=admin
   POSTGRES_PASSWORD=admin
   POSTGRES_DB=testdb
   ```

3. **Start PostgreSQL with Docker**
   ```bash
   docker-compose up postgres -d
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

The application will be available at `http://localhost:8080`

## 🐳 Running with Docker

### Full Stack with Docker Compose

```bash
# Start PostgreSQL
docker-compose up postgres -d

# Build and run the application
mvn clean package
java -jar target/webflux-crud-0.0.1-SNAPSHOT.jar
```

### Database Management

The PostgreSQL database includes:
- **Automatic initialization** via `init/init.sql`
- **Health checks** to ensure database availability
- **Persistent storage** in `postgres_data/` directory
- **Sample data** for testing

## 🔧 Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `DATABASE_URL` | `r2dbc:postgresql://localhost:5432/testdb` | R2DBC connection URL |
| `POSTGRES_USER` | `admin` | Database username |
| `POSTGRES_PASSWORD` | `admin` | Database password |
| `POSTGRES_DB` | `testdb` | Database name |

## 🔗 External Dependencies

### ExchangeRate API

- **Provider**: exchangerate-api.com
- **Endpoint**: `https://v6.exchangerate-api.com/v6/{API_KEY}/pair/{FROM}/{TO}`
- **API Key**: `1227cf5d13731ad6c251bc00` (configured in ExternalAPI.java)
- **Rate Limits**: Check provider documentation
- **Response Format**: JSON with conversion_rate field

### Maven Dependencies

Key dependencies include:
- `spring-boot-starter-webflux` - Reactive web framework
- `spring-boot-starter-data-r2dbc` - Reactive database access
- `r2dbc-postgresql` - PostgreSQL R2DBC driver
- `spring-boot-starter-validation` - Input validation
- `lombok` - Code generation
- `spring-boot-starter-test` - Testing framework
- `reactor-test` - Reactive testing utilities

## 🧪 Testing

The project includes comprehensive testing setup:

```bash
# Run tests
mvn test

# Run tests with coverage
mvn test jacoco:report
```

Test categories:
- **Unit Tests**: Service and mapper testing
- **Integration Tests**: Repository and controller testing
- **Reactive Tests**: Using StepVerifier for reactive streams

## 📊 Monitoring & Health

- **Database Health**: Configured health checks in Docker Compose
- **Application Health**: Spring Boot Actuator endpoints (if added)
- **Logging**: Configured with SLF4J and Logback

## 🔒 Security Considerations

- **API Key Security**: External API key should be externalized to environment variables
- **Database Security**: Use strong passwords and restrict network access
- **Input Validation**: Currency codes are validated for format and length
- **Error Handling**: Sensitive information is not exposed in error responses

## 🚧 Future Enhancements

Potential improvements for the project:
- Add comprehensive unit and integration tests
- Implement API rate limiting
- Add caching with TTL (Time To Live) for exchange rates
- Implement API key security for external service
- Add metrics and monitoring with Micrometer
- Implement circuit breaker pattern for external API calls
- Add API documentation with OpenAPI/Swagger
- Implement authentication and authorization
- Add support for batch currency conversion requests