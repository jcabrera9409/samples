# Spring Boot Item Review System

A Spring Boot application that demonstrates a basic item review system with RESTful APIs. This project showcases the implementation of a many-to-many relationship between items, users, and reviews using Spring Data JPA.

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Data Model](#data-model)
- [API Endpoints](#api-endpoints)
- [Database Configuration](#database-configuration)
- [Getting Started](#getting-started)
- [Testing](#testing)
- [Build and Run](#build-and-run)
- [Development Notes](#development-notes)

## Overview

This Spring Boot application implements a review system where:
- **Items** can have multiple reviews
- **Users** can write reviews for items
- **Reviews** contain ratings and comments
- The system provides functionality to query items based on their average ratings

## Architecture

The application follows a layered architecture pattern:

```
Controller Layer (REST API)
    ↓
Service Layer (Business Logic)
    ↓
Repository Layer (Data Access)
    ↓
Entity Layer (Data Model)
```

## Features

- **Item Management**: CRUD operations for items with titles and descriptions
- **User Management**: Basic user entity with username validation
- **Review System**: Users can review items with ratings and comments
- **Rating Queries**: Retrieve items with average ratings below a specified threshold
- **Data Validation**: Input validation using Bean Validation annotations
- **H2 Database**: In-memory database for development and testing

## Technology Stack

- **Java 17**
- **Spring Boot 2.7.6**
- **Spring Data JPA** - Database abstraction layer
- **Spring Data REST** - RESTful web services
- **Spring Boot Validation** - Bean validation
- **H2 Database** - In-memory database
- **Maven** - Dependency management and build tool
- **JUnit 5** - Testing framework
- **Mockito** - Mocking framework for tests
- **AssertJ** - Fluent assertions for tests

## Project Structure

```
src/
├── main/
│   └── java/
│       └── com/
│           └── springboot/
│               ├── Application.java              # Main application class
│               ├── controller/
│               │   └── ItemController.java       # REST controller
│               ├── dao/
│               │   ├── ItemRepository.java       # Item data access
│               │   └── UserRepository.java       # User data access
│               ├── model/
│               │   ├── Item.java                 # Item entity
│               │   ├── Review.java               # Review entity
│               │   └── User.java                 # User entity
│               └── service/
│                   └── ItemService.java          # Business logic
└── test/
    ├── java/
    │   └── com/
    │       └── devskiller/
    │           ├── controller/
    │           │   └── ItemControllerTest.java   # Controller tests
    │           ├── dao/
    │           │   └── ItemRepositoryTest.java   # Repository tests
    │           └── service/
    │               └── ItemServiceTest.java      # Service tests
    └── resources/
        └── application.properties               # Test configuration
```

## Data Model

### Entities

#### Item Entity
- **id**: Primary key (auto-generated)
- **title**: Item title (max 100 chars, required)
- **description**: Item description (max 200 chars, optional)
- **reviews**: One-to-many relationship with Review entity

#### User Entity
- **id**: Primary key (auto-generated)
- **username**: Unique username (max 16 chars, required)

#### Review Entity
- **id**: Primary key (auto-generated)
- **rating**: Numeric rating (Double)
- **comment**: Review comment (max 200 chars)
- **item**: Many-to-one relationship with Item
- **author**: Many-to-one relationship with User

### Entity Relationships

```
User ----< Review >---- Item
```

- One User can write multiple Reviews
- One Item can have multiple Reviews
- Each Review belongs to one User and one Item

## API Endpoints

### Items

#### GET /titles
Retrieves item titles with average ratings below the specified threshold.

**Parameters:**
- `rating` (Double): Rating threshold

**Response:**
- Returns a list of item titles as JSON array

**Example:**
```bash
GET /titles?rating=3.0
```

**Response:**
```json
["Item Title 1", "Item Title 2"]
```

## Database Configuration

### H2 In-Memory Database

The application uses H2 database for development and testing:

- **Test Configuration**: `src/test/resources/application.properties`
  ```properties
  spring.datasource.url=jdbc:h2:mem:testdb;NON_KEYWORDS=USER
  ```

- **Features**:
  - In-memory database (data is lost on application shutdown)
  - Automatic schema generation from JPA entities
  - H2 Console available for development (if enabled)

### Custom Query

The application includes a custom JPQL query in `ItemRepository`:

```java
@Query("SELECT i FROM Item i LEFT JOIN i.reviews r GROUP BY i.id " +
       "HAVING COALESCE(AVG(r.rating), 0) < :rating")
List<Item> findItemsWithAverageRatingLowerThan(Double rating);
```

This query:
- Joins items with their reviews
- Groups by item ID
- Calculates average rating per item
- Returns items where average rating is below the threshold
- Handles items with no reviews (COALESCE with 0)

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd springboot
   ```

2. **Build the project**
   ```bash
   mvn clean compile
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - Base URL: `http://localhost:8080`
   - API endpoint: `http://localhost:8080/titles?rating=5.0`

## Testing

The project includes comprehensive tests for all layers:

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=ItemServiceTest

# Run tests with coverage
mvn test jacoco:report
```

### Test Structure

- **Unit Tests**: Mock-based tests for service layer
- **Integration Tests**: Repository tests with actual database
- **Controller Tests**: REST API endpoint testing

### Example Test

```java
@Test
public void returnsTitlesBasedOnItemsFromPersistenceLayer() {
    Double rating = 10.0;
    given(itemRepository.findItemsWithAverageRatingLowerThan(rating))
            .willReturn(newArrayList(new Item("title1", "desc1"), new Item("title2", "desc2")));

    List<String> titles = itemService.getTitlesWithAverageRatingLowerThan(rating);

    assertThat(titles).containsExactly("title1", "title2");
}
```

## Build and Run

### Maven Commands

```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Package application
mvn package

# Run the packaged JAR
java -jar target/spring-boot-repositories-1.0.jar

# Run in development mode
mvn spring-boot:run
```

### Spring Boot DevTools

The project includes Spring Boot DevTools for enhanced development experience:
- Automatic restart on code changes
- Live reload capability
- Enhanced error reporting

## Development Notes

### Key Design Patterns

1. **Repository Pattern**: Data access abstraction through Spring Data repositories
2. **Service Layer**: Business logic separation from controllers
3. **DTO Pattern**: Implicit through method return types (List<String>)
4. **Dependency Injection**: Constructor-based injection for better testability

### Validation Features

- **Bean Validation**: JSR-303 annotations for entity validation
- **Database Constraints**: Unique constraints and foreign key relationships
- **Custom Validation**: Length restrictions and null checks

### Spring Boot Features Used

- **Auto-configuration**: Automatic bean configuration
- **Starter Dependencies**: Simplified dependency management
- **Embedded Server**: Built-in Tomcat server
- **Actuator Ready**: Health checks and monitoring endpoints
- **Profile Support**: Environment-specific configurations

### Code Quality

- **Clean Code**: Meaningful names and single responsibility
- **SOLID Principles**: Dependency inversion and interface segregation
- **Test Coverage**: Unit and integration tests
- **Documentation**: JavaDoc comments and clear method names

## Future Enhancements

Potential improvements for the application:

1. **Security**: Add Spring Security for authentication/authorization
2. **Pagination**: Implement paginated results for large datasets
3. **Caching**: Add Redis or in-memory caching for performance
4. **API Documentation**: Integrate Swagger/OpenAPI documentation
5. **Database Migration**: Add Flyway or Liquibase for database versioning
6. **Monitoring**: Add Spring Boot Actuator endpoints
7. **Validation**: Enhanced input validation and error handling
8. **REST Features**: Full CRUD operations for all entities

## License

This project is for educational and demonstration purposes.