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

### 4. ⚡ Reactive Quarkus Exchange Rate API
**Location**: [`/reactive-quarkus`](./reactive-quarkus/)

A reactive microservice built with Quarkus that provides currency exchange rate functionality. Demonstrates the power of Quarkus for building high-performance, cloud-native reactive applications.

**Key Features:**
- ⚡ Reactive programming with Mutiny
- 🚀 Ultra-fast startup with Quarkus
- 💾 Hibernate Reactive Panache with MySQL
- 🐳 Container-ready with Docker support
- 🔥 Native compilation with GraalVM
- 🌐 JAX-RS reactive endpoints

**Technologies**: Quarkus 3.27, Mutiny, Hibernate Reactive, MySQL, GraalVM

📖 **[View complete documentation →](./reactive-quarkus/README.md)**

---

## 🛠️ General Technology Stack

- **Java**: 21
- **Frameworks**: Spring Boot 3.x, Spring WebFlux, Spring MVC, Quarkus 3.x
- **Reactive Libraries**: Spring WebFlux, Mutiny, R2DBC
- **Databases**: PostgreSQL, MySQL, H2
- **ORM/Data Access**: Spring Data JPA, Hibernate Reactive Panache, R2DBC
- **Tools**: Maven, Docker, Lombok
- **Testing**: JUnit 5, Mockito, WebFlux Test, REST Assured
- **Native Compilation**: GraalVM

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
   - **Intermediate**: Explore the WebFlux CRUD or Quarkus reactive API
   - **Advanced**: Analyze the external API integration
   - **Performance-focused**: Try the Quarkus native compilation

## 📝 Repository Structure

```
samples/
├── springboot/          # Traditional review system
├── test/               # Reactive exchange rate API
├── webflux-crud/       # Reactive student CRUD
├── reactive-quarkus/   # Quarkus reactive exchange API
└── README.md          # This file
```

## 🤝 Contributing

Contributions are welcome! Feel free to open issues or submit pull requests to improve these examples.

## 📄 License

This project is licensed under the terms specified in the [LICENSE](./LICENSE) file.