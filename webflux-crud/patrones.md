# Análisis de Patrones de Diseño y Arquitectura - Proyecto WebFlux CRUD

## Resumen del Proyecto
El proyecto **webflux-crud** es una aplicación Spring Boot que implementa operaciones CRUD para gestionar alumnos, utilizando Spring WebFlux para programación reactiva y Spring Data R2DBC para acceso a datos no bloqueante con PostgreSQL.

## Patrones Arquitectónicos

### 1. **Arquitectura en Capas (Layered Architecture)**
La aplicación sigue una arquitectura en capas bien definida:

- **Capa de Presentación**: `AlumnoController`
- **Capa de Servicio**: `IAlumnoService` e `AlumnoServiceImpl`
- **Capa de Acceso a Datos**: `IAlumnoRepository`
- **Capa de Modelo**: `Alumno`, DTOs
- **Capa de Excepción**: Manejo global de errores

```
Controller → Service → Repository → Database
```

### 2. **Arquitectura Reactiva (Reactive Architecture)**
Implementa programación reactiva usando:
- **Spring WebFlux**: Framework reactivo
- **Reactor**: Librería reactiva (Mono, Flux)
- **Spring Data R2DBC**: Acceso a datos reactivo
- **Non-blocking I/O**: Todas las operaciones son asíncronas

### 3. **Microservicios (Microservice Pattern)**
Aunque es una aplicación individual, está diseñada siguiendo principios de microservicios:
- **API REST**: Endpoints bien definidos
- **Configuración externa**: Variables de entorno
- **Health Checks**: Spring Boot Actuator
- **Documentación API**: OpenAPI/Swagger

## Patrones de Diseño

### 1. **Repository Pattern**
```java
public interface IAlumnoRepository extends R2dbcRepository<Alumno, Long>
```
- Abstrae el acceso a datos
- Proporciona operaciones CRUD estándar
- Facilita testing con mocks
- Separa la lógica de negocio del acceso a datos

### 2. **Service Layer Pattern**
```java
public interface IAlumnoService {
    Mono<AlumnoResponseDTO> create(AlumnoRequestDTO alumno);
    Mono<AlumnoResponseDTO> update(Long id, AlumnoRequestDTO alumno);
    // ...
}
```
- Encapsula la lógica de negocio
- Proporciona una interfaz entre controlador y repositorio
- Facilita la reutilización de código
- Permite transacciones y validaciones

### 3. **Data Transfer Object (DTO) Pattern**
```java
// DTOs separados para request y response
public class AlumnoRequestDTO { ... }
public class AlumnoResponseDTO { ... }
```
- **AlumnoRequestDTO**: Para datos de entrada
- **AlumnoResponseDTO**: Para datos de salida
- Encapsula datos de transferencia
- Reduce acoplamiento entre capas
- Permite validaciones específicas

### 4. **Mapper Pattern**
```java
public class AlumnoMapper {
    public static Alumno toEntity(AlumnoRequestDTO dto) { ... }
    public static AlumnoResponseDTO toResponseDTO(Alumno entity) { ... }
}
```
- Convierte entre entidades y DTOs
- Centraliza la lógica de transformación
- Métodos estáticos para simplificidad
- Facilita mantenimiento

### 5. **Builder Pattern**
```java
@Builder
public class Alumno { ... }

@Builder
public class AlumnoRequestDTO { ... }
```
- Usando anotación `@Builder` de Lombok
- Facilita creación de objetos complejos
- Mejora legibilidad del código
- Permite creación fluida de objetos

### 6. **Dependency Injection Pattern**
```java
public AlumnoServiceImpl(IAlumnoRepository alumnoRepository) {
    this.alumnoRepository = alumnoRepository;
}
```
- Inyección por constructor
- Inversión de control
- Facilita testing
- Reduce acoplamiento

### 7. **Exception Handling Pattern (Global Exception Handler)**
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AlumnoNotFoundException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleAlumnoNotFoundException(...) { ... }
}
```
- Manejo centralizado de excepciones
- Respuestas consistentes de error
- Separación de concerns
- Logging estructurado

### 8. **Factory Pattern (Implícito)**
- Spring Container actúa como factory
- Creación automática de beans
- Gestión del ciclo de vida de objetos

## Patrones de Programación Reactiva

### 1. **Observer Pattern (Reactive Streams)**
```java
public Flux<AlumnoResponseDTO> findAll() {
    return alumnoRepository.findAll()
            .map(AlumnoMapper::toResponseDTO);
}
```
- **Mono**: 0 o 1 elemento
- **Flux**: 0 a N elementos
- Streams reactivos
- Backpressure handling

### 2. **Chain of Responsibility (Reactive Chain)**
```java
return alumnoRepository.findById(id)
        .switchIfEmpty(Mono.error(new AlumnoNotFoundException(id)))
        .then(Mono.just(AlumnoMapper.toEntity(id, alumno)))
        .flatMap(alumnoRepository::save)
        .map(AlumnoMapper::toResponseDTO);
```
- Encadenamiento de operaciones reactivas
- Transformaciones funcionales
- Manejo de errores en la cadena

### 3. **Strategy Pattern (Functional Programming)**
```java
.map(AlumnoMapper::toResponseDTO)  // Estrategia de transformación
.doOnSuccess(saved -> log.info(...))  // Estrategia de logging
.doOnError(error -> log.error(...))   // Estrategia de error
```

## Patrones de Testing

### 1. **Test Double Patterns**
```java
@Mock
private IAlumnoRepository alumnoRepository;

@MockitoBean
private IAlumnoService alumnoService;
```
- **Mock Objects**: Para aislar unidades bajo test
- **Test Slices**: `@WebFluxTest` para testing específico

### 2. **AAA Pattern (Arrange-Act-Assert)**
```java
@Test
void testCrearAlumnoExitoso() {
    // Given (Arrange)
    when(alumnoRepository.save(any(Alumno.class))).thenReturn(Mono.just(alumno));
    
    // When (Act)
    Mono<AlumnoResponseDTO> result = alumnoService.create(alumnoRequestDTO);
    
    // Then (Assert)
    StepVerifier.create(result)
            .expectNextMatches(...)
            .verifyComplete();
}
```

### 3. **Reactive Testing Pattern**
```java
StepVerifier.create(result)
    .expectNextMatches(response -> ...)
    .verifyComplete();
```
- Testing específico para streams reactivos
- Verificación de secuencias asíncronas

## Patrones de Configuración

### 1. **Externalized Configuration Pattern**
```yaml
spring:
  r2dbc:
    url: ${DATABASE_URL:r2dbc:postgresql://localhost:5432/testdb}
    username: ${POSTGRES_USER:admin}
    password: ${POSTGRES_PASSWORD:admin}
```
- Variables de entorno
- Configuración por defecto
- Separación de configuración del código

### 2. **Convention over Configuration**
- Spring Boot auto-configuration
- Convenciones de nombres
- Configuración mínima requerida

## Patrones de Validación

### 1. **Bean Validation Pattern**
```java
@NotBlank
@Size(min = 2, max = 100)
private String nombre;

@Email
private String email;
```
- Validaciones declarativas
- Anotaciones JSR-303/JSR-380
- Separación de lógica de validación

### 2. **Fail-Fast Pattern**
```java
@Valid @RequestBody AlumnoRequestDTO alumno
```
- Validación temprana en el controlador
- Respuesta inmediata en caso de error

## Patrones de Logging y Observabilidad

### 1. **Structured Logging Pattern**
```java
@Slf4j
public class AlumnoServiceImpl {
    log.info("Alumno creado con ID: {}", saved.getId());
    log.error("Error al crear alumno: {}", error.getMessage());
}
```
- Logging estructurado con SLF4J
- Diferentes niveles de log
- Información contextual

### 2. **Correlation Pattern**
- Trazabilidad de operaciones
- Logging de entrada y salida de métodos

## Patrones de API Design

### 1. **RESTful API Pattern**
```java
@RestController
@RequestMapping("/api/v1/alumnos")
public class AlumnoController {
    @GetMapping("/{id}")     // GET para lectura
    @PostMapping             // POST para creación
    @PutMapping("/{id}")     // PUT para actualización
    @DeleteMapping("/{id}")  // DELETE para eliminación
}
```
- Verbos HTTP semánticamente correctos
- URLs descriptivas y consistentes
- Códigos de estado HTTP apropiados

### 2. **API Versioning Pattern**
```java
@RequestMapping("/api/v1/alumnos")
```
- Versionado en URL
- Compatibilidad hacia atrás

### 3. **Content Type Negotiation**
```java
@GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
```
- Server-Sent Events (SSE)
- Streaming de datos reactivo

## Patrones de Documentación

### 1. **API Documentation Pattern**
```java
@Tag(name = "Alumnos", description = "Operaciones relacionadas con alumnos")
@Operation(summary = "Crear un nuevo alumno", description = "...")
```
- OpenAPI/Swagger
- Documentación automática
- Descripción de operaciones

## Patrones de Persistencia

### 1. **Active Record Pattern (Simplificado)**
```java
@Table(name = "alumnos")
public class Alumno {
    @Id
    private Long id;
    // ...
}
```
- Mapeo objeto-relacional
- Anotaciones JPA/Spring Data

### 2. **Connection Pooling Pattern**
```yaml
r2dbc:
  pool:
    initial-size: 5
    max-size: 20
    max-idle-time: 30m
```
- Pool de conexiones R2DBC
- Gestión eficiente de recursos

## Conclusión

El proyecto **webflux-crud** implementa una arquitectura moderna y bien estructurada que combina múltiples patrones de diseño y arquitectura:

- **Arquitectura reactiva** para alta concurrencia y escalabilidad
- **Separación de responsabilidades** a través de capas bien definidas
- **Patrones clásicos** adaptados al paradigma reactivo
- **Buenas prácticas** de testing, documentación y configuración
- **API moderna** siguiendo principios REST y estándares OpenAPI

Esta combinación de patrones resulta en una aplicación mantenible, testeable y escalable, apropiada para entornos de alta concurrencia y microservicios.