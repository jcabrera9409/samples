# 🚀 Spring Boot Samples Repository

This repository contains a collection of sample projects built with Spring Boot, demonstrating different technologies and development patterns. Each project includes detailed documentation and is designed to be a practical implementation example.

## 📋 Project Catalog

### 1. 🔄 Spring Boot Item Review System
**Location**: [`/springboot`](./springboot/)

A traditional review system that demonstrates many-to-many relationships between entities using Spring Data JPA. Perfect for understanding fundamental Spring Boot concepts.

**Key Features:**
- ✅ REST API with CRUD operations
- ✅ Review system with users, items, and ratings
- ✅ Complex JPA relationships
- ✅ In-memory H2 database
- ✅ Data validation with Bean Validation

**Technologies**: Spring Boot, Spring Data JPA, H2 Database, Maven

📖 **[View complete documentation →](./springboot/README.md)**

---

### 2. 💱 Spring WebFlux Exchange Rate API
**Location**: [`/test`](./test/)

A reactive application that provides currency exchange rate information. Implements a caching system with PostgreSQL and external API integration.

**Key Features:**
- ⚡ Reactive programming with Spring WebFlux
- 💾 Data caching in PostgreSQL
- 🌐 External exchange rate API integration
- 🐳 Docker Compose configuration
- 📊 Reactive database with R2DBC

**Technologies**: Spring Boot 3, Spring WebFlux, R2DBC, PostgreSQL, Docker

📖 **[View complete documentation →](./test/README.md)**

---

### 3. 👨‍🎓 Spring WebFlux CRUD API
**Location**: [`/webflux-crud`](./webflux-crud/)

A complete reactive REST API for student management. Includes Swagger documentation, global error handling, and real-time streaming support.

**Key Features:**
- 🔄 Complete reactive CRUD
- 📚 Automatic documentation with OpenAPI/Swagger
- 🚨 Global exception handling
- 📡 Server-Sent Events (SSE) for streaming
- 🧪 Complete testing with WebFlux Test
- 📝 Structured logging

**Technologies**: Spring Boot 3, Spring WebFlux, R2DBC, PostgreSQL, SpringDoc OpenAPI

📖 **[View complete documentation →](./webflux-crud/README.md)**

---

## 🛠️ General Technology Stack

- **Java**: 21
- **Spring Boot**: 3.x
- **Frameworks**: Spring MVC, Spring WebFlux
- **Databases**: PostgreSQL, H2
- **Tools**: Maven, Docker, Lombok
- **Testing**: JUnit 5, Mockito, WebFlux Test

## 🚀 Getting Started

1. **Clone the repository**:
   ```bash
   git clone https://github.com/jcabrera9409/samples.git
   cd samples
   ```

2. **Explore the projects**:
   - Each project has its own README with specific instructions
   - Projects can be run independently
   - Some require Docker for the database

3. **Choose your starting point**:
   - **Beginner**: Start with the traditional Spring Boot project
   - **Intermediate**: Explore the WebFlux CRUD
   - **Advanced**: Analyze the external API integration

## 📝 Repository Structure

```
samples/
├── springboot/          # Traditional review system
├── test/               # Reactive exchange rate API
├── webflux-crud/       # Reactive student CRUD
└── README.md          # This file
```

## 🤝 Contributing

Contributions are welcome! Feel free to open issues or submit pull requests to improve these examples.

## 📄 License

This project is licensed under the terms specified in the [LICENSE](./LICENSE) file.