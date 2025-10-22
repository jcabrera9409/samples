# Spring WebFlux CRUD API

A reactive REST API built with Spring Boot 3 and Spring WebFlux that provides CRUD operations for student management. The application uses R2DBC for reactive database connectivity with PostgreSQL.

## 🚀 Features

- **Reactive Programming**: Built with Spring WebFlux for non-blocking, asynchronous operations
- **CRUD Operations**: Complete Create, Read, Update, Delete functionality for student entities
- **Database Integration**: PostgreSQL database with R2DBC reactive driver
- **Validation**: Input validation using Bean Validation annotations
- **Error Handling**: Global exception handling with custom error responses
- **API Documentation**: Swagger/OpenAPI 3 documentation with SpringDoc
- **Streaming Support**: Server-Sent Events (SSE) endpoint for real-time data streaming
- **Docker Support**: Containerized PostgreSQL database setup
- **Testing**: Unit tests for controllers with WebFlux test support
- **Logging**: Structured logging with file rotation

## 🛠️ Technology Stack

- **Java**: 21
- **Spring Boot**: 3.5.6
- **Spring WebFlux**: Reactive web framework
- **Spring Data R2DBC**: Reactive database connectivity
- **PostgreSQL**: Database with R2DBC PostgreSQL driver
- **Lombok**: Code generation for reducing boilerplate
- **Bean Validation**: Input validation
- **SpringDoc OpenAPI**: API documentation
- **Docker**: Database containerization
- **JUnit 5**: Testing framework
- **Mockito**: Mocking framework for tests

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/webflux/test/
│   │   ├── TestApplication.java              # Main application class
│   │   ├── controller/
│   │   │   └── AlumnoController.java         # REST controller for student operations
│   │   ├── dto/
│   │   │   ├── AlumnoRequestDTO.java         # Request DTO with validation
│   │   │   └── AlumnoResponseDTO.java        # Response DTO
│   │   ├── exception/
│   │   │   ├── AlumnoNotFoundException.java  # Custom exception for not found students
│   │   │   ├── ErrorResponse.java            # Standardized error response
│   │   │   └── GlobalExceptionHandler.java   # Global exception handler
│   │   ├── mapper/
│   │   │   └── AlumnoMapper.java             # DTO-Entity mapping utility
│   │   ├── model/
│   │   │   └── Alumno.java                   # Student entity
│   │   ├── repository/
│   │   │   └── IAlumnoRepository.java        # R2DBC repository interface
│   │   └── service/
│   │       ├── IAlumnoService.java           # Service interface
│   │       └── impl/
│   │           └── AlumnoServiceImpl.java    # Service implementation
│   └── resources/
│       └── application.yaml                  # Application configuration
└── test/
    └── java/com/webflux/test/
        ├── controller/
        │   └── AlumnoControllerTest.java     # Controller unit tests
        └── service/
```

## 🏗️ Architecture

The application follows a layered architecture pattern:

1. **Controller Layer**: Handles HTTP requests and responses
2. **Service Layer**: Contains business logic and orchestrates operations
3. **Repository Layer**: Manages data persistence using R2DBC
4. **DTO Layer**: Data Transfer Objects for API communication
5. **Exception Layer**: Centralized error handling

## 🎯 API Endpoints

### Students Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/v1/alumnos` | Get all students |
| `GET` | `/api/v1/alumnos/{id}` | Get student by ID |
| `POST` | `/api/v1/alumnos` | Create new student |
| `PUT` | `/api/v1/alumnos/{id}` | Update existing student |
| `DELETE` | `/api/v1/alumnos/{id}` | Delete student |
| `GET` | `/api/v1/alumnos/stream` | Stream all students (SSE) |

### API Documentation

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI Docs**: `http://localhost:8080/v3/api-docs`

## 📊 Data Model

### Student Entity (Alumno)

```java
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "edad": 21,
  "email": "juan.perez@example.com"
}
```

### Request DTO Validation Rules

- `nombre`: Required, 2-100 characters
- `apellido`: Required, 2-100 characters
- `edad`: Required, minimum value 1
- `email`: Required, valid email format

## 🚦 Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6+
- Docker and Docker Compose (for database)

### 1. Clone the Repository

```bash
git clone <repository-url>
cd webflux-crud
```

### 2. Start the Database

The project includes a Docker Compose setup for PostgreSQL:

```bash
docker-compose up -d
```

This will start:
- PostgreSQL database on port 5432
- Database name: `testdb`
- Username: `admin`
- Password: `admin`

The database will be automatically initialized with the `alumnos` table and sample data.

### 3. Configure Environment Variables

The application reads database configuration from environment variables or `.env` file:

```properties
POSTGRES_USER=admin
POSTGRES_PASSWORD=admin
POSTGRES_DB=testdb
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 5. Test the API

#### Create a new student:
```bash
curl -X POST http://localhost:8080/api/v1/alumnos \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Ana",
    "apellido": "García",
    "edad": 22,
    "email": "ana.garcia@example.com"
  }'
```

#### Get all students:
```bash
curl http://localhost:8080/api/v1/alumnos
```

#### Stream students (SSE):
```bash
curl -N -H "Accept: text/event-stream" http://localhost:8080/api/v1/alumnos/stream
```

## 🧪 Testing

Run the unit tests:

```bash
mvn test
```

The project includes:
- Controller layer tests using `@WebFluxTest`
- Reactive testing with `WebTestClient`
- Mock service layer dependencies

## 📝 Configuration

### Database Configuration

The application uses R2DBC for reactive database access:

```yaml
spring:
  r2dbc:
    url: r2dbc:postgresql://localhost:5432/testdb
    username: admin
    password: admin
    pool:
      initial-size: 5
      max-size: 20
      max-idle-time: 30m
```

### Logging Configuration

Structured logging with file rotation:

```yaml
logging:
  level:
    org.springframework.web: DEBUG
  file:
    name: ./logs/app.log
  logback:
    rollingpolicy:
      max-file-size: 10MB
      max-history: 30
```

## 🔧 Key Features Implementation

### Reactive Programming

The application leverages Spring WebFlux's reactive stack:

- **Mono**: For single value operations
- **Flux**: For multi-value operations and streaming
- **Non-blocking I/O**: All database operations are reactive

### Error Handling

Comprehensive error handling with:

- Custom exceptions (`AlumnoNotFoundException`)
- Global exception handler (`@RestControllerAdvice`)
- Standardized error responses
- Validation error mapping

### Streaming Support

Server-Sent Events endpoint for real-time data streaming:

```java
@GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public Flux<AlumnoResponseDTO> streamAlumnos() {
    return alumnoService.findAll()
        .delayElements(Duration.ofMillis(500));
}
```

## 🚀 Production Considerations

1. **Security**: Add Spring Security for authentication/authorization
2. **Monitoring**: Integrate Spring Boot Actuator endpoints
3. **Caching**: Add Redis for reactive caching
4. **Rate Limiting**: Implement request rate limiting
5. **Database Migration**: Use Flyway or Liquibase for schema management
6. **Environment Configuration**: Use Spring Profiles for different environments

## 📚 Additional Resources

- [Spring WebFlux Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html)
- [Spring Data R2DBC Reference](https://docs.spring.io/spring-data/r2dbc/docs/current/reference/html/)
- [Reactive Programming Guide](https://projectreactor.io/docs/core/release/reference/)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.