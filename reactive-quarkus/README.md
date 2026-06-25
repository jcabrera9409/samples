# 🚀 Reactive Quarkus Exchange Rate API

A reactive microservice built with Quarkus that provides currency exchange rate functionality using reactive programming patterns. The application demonstrates the power of Quarkus for building high-performance, cloud-native reactive applications with MySQL database integration.

## 📋 Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Data Model](#data-model)
- [API Endpoints](#api-endpoints)
- [Database Configuration](#database-configuration)
- [Getting Started](#getting-started)
- [Running with Docker](#running-with-docker)
- [Environment Variables](#environment-variables)
- [Native Compilation](#native-compilation)
- [Configuration](#configuration)
- [Development Notes](#development-notes)

## 🔍 Overview

This Quarkus application is a reactive microservice that provides currency exchange rate information. It leverages Quarkus's reactive capabilities with Hibernate Reactive Panache for database operations and Mutiny for reactive programming patterns. The service uses MySQL as the database backend and demonstrates modern reactive programming techniques.

## 🏗️ Architecture

The application follows a reactive architecture pattern with these layers:

```
REST Layer (JAX-RS Reactive)
    ↓
Service Layer (Business Logic with Mutiny)
    ↓
Repository Layer (Hibernate Reactive Panache)
    ↓
Database Layer (MySQL with Reactive Vertx Driver)
```

### Key Components:

1. **ExchangeController** - Reactive REST controller using JAX-RS
2. **ExchangeService** - Business logic with Mutiny reactive streams
3. **ExchangeRepo** - Reactive repository using Hibernate Reactive Panache
4. **Exchange Entity** - JPA entity with MySQL mapping

## ✨ Features

- **Reactive Programming**: Built with Mutiny for non-blocking I/O operations
- **Reactive Database Access**: Hibernate Reactive Panache with MySQL
- **Fast Startup**: Quarkus super-fast startup time
- **Native Compilation**: GraalVM native image support
- **Container Ready**: Docker configuration included
- **Health Checks**: MySQL database health monitoring
- **Environment Configuration**: Flexible configuration via environment variables
- **Logging**: Comprehensive logging with file rotation

## 🛠️ Technology Stack

- **Java**: 21
- **Quarkus**: 3.27.0
- **Mutiny**: Reactive programming library
- **Hibernate Reactive Panache**: Reactive ORM
- **MySQL**: Database with reactive Vertx driver
- **JAX-RS**: REST API endpoints
- **Jackson**: JSON serialization
- **CDI**: Dependency injection
- **Maven**: Build tool
- **Docker**: Containerization
- **GraalVM**: Native compilation support

## 📁 Project Structure

```
src/
├── main/
│   ├── java/org/quarkus/
│   │   ├── GreetingResource.java           # Sample greeting endpoint
│   │   ├── controller/
│   │   │   └── ExchangeController.java     # REST controller
│   │   ├── service/
│   │   │   └── ExchangeService.java        # Business logic
│   │   ├── repository/
│   │   │   └── ExchangeRepo.java           # Reactive repository
│   │   └── model/
│   │       └── Exchange.java               # JPA entity
│   ├── resources/
│   │   └── application.properties          # Application configuration
│   └── docker/
│       ├── Dockerfile.jvm                  # JVM container image
│       ├── Dockerfile.native               # Native container image
│       ├── Dockerfile.native-micro         # Micro native image
│       └── Dockerfile.legacy-jar           # Legacy JAR container
├── test/
│   └── java/org/quarkus/                   # Test classes
├── docker-compose.yml                      # MySQL database setup
├── init/
│   └── init.sql                           # Database initialization
└── pom.xml                                # Maven configuration
```

## 📊 Data Model

### Exchange Entity

The application manages currency exchange rate data:

```java
@Entity
@Table(name = "exchange_data")
public class Exchange extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "from_currency")
    private String fromCurrency;
    
    @Column(name = "to_currency")
    private String toCurrency;
    
    @Column(name = "exchange_rate")
    private float exchangeRate;
    
    @Column(name = "updated_at")
    @CreationTimestamp
    private LocalDateTime updatedAt;
}
```

### Database Schema

```sql
CREATE TABLE exchange_data (
    id SERIAL PRIMARY KEY,
    from_currency VARCHAR(15) NOT NULL,
    to_currency VARCHAR(15) NOT NULL,
    exchange_rate FLOAT NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## 🌐 API Endpoints

### Exchange Rate Endpoints

#### Get Exchange Rate

```http
GET /api/v1/exchange/{from}/{to}
```

**Parameters:**
- `from` (path): Source currency code (e.g., "USD")
- `to` (path): Target currency code (e.g., "EUR")

**Response:**
```json
3.75
```

**Example:**
```bash
curl http://localhost:8080/api/v1/exchange/PEN/USD
```

### Health and Monitoring

- **Health Check**: `/q/health`
- **Metrics**: `/q/metrics`
- **OpenAPI**: `/q/openapi`

### Sample Greeting Endpoint

```http
GET /hello
```

Returns a simple greeting message for testing.

## 🗄️ Database Configuration

### MySQL Database

The application uses MySQL with reactive Vertx driver:

**Connection Configuration:**
```properties
quarkus.datasource.db-kind=mysql
quarkus.datasource.username=${MYSQL_USER:admin}
quarkus.datasource.password=${MYSQL_PASSWORD:admin}
quarkus.datasource.reactive.url=${DATASOURCE_URL:mysql://localhost:3306/testdb}
quarkus.datasource.reactive.max-size=20
quarkus.datasource.reactive.idle-timeout=PT10M
```

**Sample Data:**
The database is initialized with sample exchange rate data:
```sql
INSERT INTO exchange_data (from_currency, to_currency, exchange_rate)
VALUES ('PEN', 'USD', 3.75);
```

## 🚀 Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.8+
- Docker and Docker Compose
- MySQL 8.0 (via Docker)

### Local Development Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd reactive-quarkus
   ```

2. **Set up environment variables**
   Create a `.env` file in the project root:
   ```env
   MYSQL_USER=admin
   MYSQL_PASSWORD=admin
   MYSQL_DATABASE=testdb
   ```

3. **Start MySQL with Docker**
   ```bash
   docker-compose up mysql_db -d
   ```

4. **Run the application in development mode**
   ```bash
   ./mvnw quarkus:dev
   ```

The application will start on `http://localhost:8080` with live reload enabled.

### Development Mode Features

Quarkus development mode provides:
- **Live Reload**: Automatic restart on code changes
- **Dev UI**: Available at `/q/dev/`
- **Continuous Testing**: Run tests automatically
- **Database Dev Services**: Automatic database setup (optional)

## 🐳 Running with Docker

### Using Docker Compose

1. **Start all services**
   ```bash
   docker-compose up -d
   ```

### Manual Docker Build

1. **Build the application**
   ```bash
   ./mvnw package
   ```

2. **Build Docker image**
   ```bash
   docker build -f src/main/docker/Dockerfile.jvm -t quarkus/reactive-quarkus-jvm .
   ```

3. **Run the container**
   ```bash
   docker run -i --rm -p 8080:8080 quarkus/reactive-quarkus-jvm
   ```

## 🔧 Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `MYSQL_USER` | `admin` | Database username |
| `MYSQL_PASSWORD` | `admin` | Database password |
| `MYSQL_DATABASE` | `testdb` | Database name |
| `DATASOURCE_URL` | `mysql://localhost:3306/testdb` | Reactive MySQL connection URL |
| `APP_PATH` | `.` | Application base path for logs |

## ⚡ Native Compilation

Quarkus supports native compilation with GraalVM for ultra-fast startup times.

### Building Native Image

1. **Build native executable**
   ```bash
   ./mvnw package -Dnative
   ```

2. **Build native container image**
   ```bash
   ./mvnw package -Dnative -Dquarkus.native.container-build=true
   ```

3. **Build native Docker image**
   ```bash
   docker build -f src/main/docker/Dockerfile.native -t quarkus/reactive-quarkus .
   ```

4. **Run native container**
   ```bash
   docker run -i --rm -p 8080:8080 quarkus/reactive-quarkus
   ```

### Native Benefits

- **Ultra-fast startup**: ~0.1 seconds
- **Low memory footprint**: ~20MB RSS
- **No warm-up time**: Peak performance immediately
- **Perfect for serverless**: Functions and containers

## ⚙️ Configuration

### Application Properties

The application uses comprehensive configuration in `application.properties`:

```properties
# HTTP Server
quarkus.http.port=8080

# Database Configuration
quarkus.datasource.db-kind=mysql
quarkus.datasource.username=${MYSQL_USER:admin}
quarkus.datasource.password=${MYSQL_PASSWORD:admin}
quarkus.datasource.reactive.url=${DATASOURCE_URL:mysql://localhost:3306/testdb}

# Hibernate Configuration
quarkus.hibernate-orm.database.generation=update

# Logging Configuration
quarkus.log.console.format=%d{HH:mm:ss} %-5p [%c{2.}] (%t) %s%e%n
quarkus.log.file.enable=true
quarkus.log.file.path=${APP_PATH:.}/logs/app.log
```

### Profile-Specific Configuration

- **Development**: Debug logging, CORS enabled
- **Production**: Info logging, specific CORS origins
- **Test**: File logging disabled

### CORS Configuration

The application includes CORS configuration for frontend integration:

```properties
# Development CORS
%dev.quarkus.http.cors=true
%dev.quarkus.http.cors.origins=http://localhost:4200,http://127.0.0.1:4200

# Production CORS
%prod.quarkus.http.cors=true
%prod.quarkus.http.cors.origins=https://reactive-quarkus.com
```

## 🧪 Testing

### Running Tests

```bash
# Run all tests
./mvnw test

# Run tests in continuous mode
./mvnw quarkus:test

# Run tests with coverage
./mvnw verify
```

### Test Structure

- **Unit Tests**: Service and repository testing
- **Integration Tests**: REST endpoint testing with TestContainers
- **Native Tests**: Native image compatibility testing

## 📝 Key Implementation Details

### Reactive Repository Pattern

```java
@ApplicationScoped
public class ExchangeRepo implements PanacheRepository<Exchange> {
    
    @WithSession
    public Uni<Exchange> findByFromAndToCurrency(String from, String to) {
        return find("fromCurrency = ?1 and toCurrency = ?2", from, to)
                .firstResult();
    }
    
    @WithTransaction
    public Uni<Exchange> save(Exchange exchange) {
        return persist(exchange);
    }
}
```

### Reactive Service Layer

```java
@ApplicationScoped
public class ExchangeService {
    
    public Uni<Float> getExchangeRate(String fromCurrency, String toCurrency) {
        return exchangeRepo.findByFromAndToCurrency(fromCurrency, toCurrency)
                .onItem()
                .ifNotNull()
                .transform(Exchange::getExchangeRate);
    }
}
```

### Reactive REST Controller

```java
@Path("/api/v1/exchange")
@Produces("application/json")
public class ExchangeController {
    
    @GET
    @Path("/{from}/{to}")
    public Uni<Float> getExchangeRate(@PathParam("from") String fromCurrency, 
                                      @PathParam("to") String toCurrency) {
        return exchangeService.getExchangeRate(fromCurrency, toCurrency);
    }
}
```

## 🔄 Reactive Programming Benefits

1. **Non-blocking I/O**: No thread blocking on database operations
2. **Resource Efficiency**: Better resource utilization
3. **Scalability**: Handle more concurrent requests
4. **Backpressure Handling**: Built-in flow control
5. **Composability**: Easy to chain and transform operations

## 🚧 Production Considerations

1. **Security**: Add authentication and authorization
2. **Monitoring**: Integrate with Micrometer and Prometheus
3. **Rate Limiting**: Implement request throttling
4. **Circuit Breaker**: Add fault tolerance patterns
5. **Caching**: Implement Redis caching for better performance
6. **API Documentation**: Add OpenAPI/Swagger documentation
7. **Database Migration**: Use Flyway for schema management

## 📈 Performance Benefits

### Quarkus Advantages

- **Fast Startup**: ~1 second in JVM mode, ~0.1 second native
- **Low Memory**: ~100MB JVM mode, ~20MB native
- **Live Reload**: Instant development feedback
- **Optimized**: Compile-time optimizations

### Reactive Advantages

- **High Throughput**: Non-blocking operations
- **Resource Efficient**: Fewer threads needed
- **Resilient**: Built-in error handling
- **Scalable**: Better concurrent request handling

## 🔗 Useful Resources

- [Quarkus Documentation](https://quarkus.io/guides/)
- [Mutiny Documentation](https://smallrye.io/smallrye-mutiny/)
- [Hibernate Reactive Guide](https://quarkus.io/guides/hibernate-reactive)
- [Quarkus Native Guide](https://quarkus.io/guides/building-native-image)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Implement your changes
4. Add tests for new functionality
5. Run the test suite
6. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](../LICENSE) file for details.

---

**Built with ❤️ using Quarkus - The Supersonic Subatomic Java Framework**