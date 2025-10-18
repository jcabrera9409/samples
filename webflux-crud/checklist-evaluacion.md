# ✅ Checklist Evaluación WebFlux - 45 Minutos
**Tiempo Total: 45 minutos**  
**Posición: Backend Senior - Spring WebFlux**  
**BCP Technical Assessment**

## 🚨 ELEMENTOS OBLIGATORIOS (0-15 min)

### ⚡ CRÍTICO - Corrección de Bugs (5 min)
- [ ] **FIX DELETE Method** - El método `eliminarAlumno` debe esperar el `Mono`
  ```java
  // CAMBIAR ESTO:
  alumnoService.delete(id);
  return Mono.just(ResponseEntity.noContent().build());
  
  // POR ESTO:
  return alumnoService.delete(id)
      .then(Mono.just(ResponseEntity.noContent().build()));
  ```

### 🛡️ VALIDACIONES BÁSICAS (10 min)
- [ ] **Agregar Bean Validation al modelo Alumno**
  - [ ] `@NotBlank` en nombre y apellido
  - [ ] `@Email` en email
  - [ ] `@Min(18)` y `@Max(100)` en edad
  - [ ] `@Size` para longitud de strings
  
- [ ] **Habilitar validación en Controller**
  - [ ] Agregar `@Valid` en métodos POST y PUT
  - [ ] Agregar dependencia `spring-boot-starter-validation` al POM

## 🎯 ELEMENTOS ESENCIALES (15-30 min)

### 🔧 MANEJO DE EXCEPCIONES (15 min)
- [ ] **Crear GlobalExceptionHandler**
  - [ ] `@RestControllerAdvice`
  - [ ] Manejar `WebExchangeBindException` (validaciones)
  - [ ] Manejar excepciones generales
  - [ ] Response de error estandarizado

- [ ] **Excepciones Personalizadas**
  - [ ] `AlumnoNotFoundException`
  - [ ] Uso en Service cuando no se encuentra por ID

### 🧪 TESTING MÍNIMO (15 min)
- [ ] **Service Tests (3 tests mínimos)**
  - [ ] Test crear alumno exitoso
  - [ ] Test buscar alumno por ID existente
  - [ ] Test buscar alumno por ID no existente

- [ ] **Controller Test (1 test mínimo)**
  - [ ] Test endpoint POST con `WebTestClient`

## 🏆 ELEMENTOS DESEABLES (30-45 min)

### 📝 DTOs Y MAPPERS (10 min)
- [ ] **AlumnoRequestDTO**
  - [ ] Para requests POST/PUT
  - [ ] Sin campo ID
  
- [ ] **AlumnoResponseDTO**
  - [ ] Para responses
  - [ ] Incluir ID

- [ ] **Mapper simple**
  - [ ] Métodos toEntity() y toDTO()

### 📊 LOGGING (5 min)
- [ ] **Agregar logs básicos**
  - [ ] Log en Service para operaciones principales
  - [ ] Log de errores en ExceptionHandler

### 📖 DOCUMENTACIÓN (5 min)
- [ ] **OpenAPI/Swagger (opcional)**
  - [ ] Dependencia `springdoc-openapi-starter-webflux-ui`
  - [ ] Anotaciones básicas en Controller

## ⏰ DISTRIBUCIÓN DE TIEMPO RECOMENDADA

### Primeros 15 minutos (OBLIGATORIO)
```
0-5 min:   ✅ Corregir bug DELETE
5-15 min:  ✅ Implementar validaciones
```

### Siguientes 15 minutos (ESENCIAL)
```
15-25 min: ✅ GlobalExceptionHandler
25-30 min: ✅ Excepciones personalizadas
```

### Últimos 15 minutos (CRÍTICO)
```
30-40 min: ✅ Tests unitarios mínimos
40-45 min: ✅ Revisión y ajustes finales
```

## 🎯 ORDEN DE PRIORIDAD

### **PRIORIDAD 1 (IMPRESCINDIBLE)**
1. ✅ Corregir bug DELETE
2. ✅ Validaciones en modelo
3. ✅ @Valid en controller
4. ✅ GlobalExceptionHandler básico

### **PRIORIDAD 2 (MUY IMPORTANTE)**
5. ✅ AlumnoNotFoundException
6. ✅ 3 tests unitarios de Service
7. ✅ 1 test de Controller

### **PRIORIDAD 3 (DESEABLE)**
8. ✅ DTOs básicos
9. ✅ Logging
10. ✅ Documentación

## 🚀 COMANDOS RÁPIDOS

### Para Tests:
```bash
# Ejecutar tests
mvn test

# Test específico
mvn test -Dtest=AlumnoServiceTest
```

### Para Validaciones (POM):
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

### Para Swagger (POM):
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webflux-ui</artifactId>
    <version>2.2.0</version>
</dependency>
```

## 🎯 CRITERIOS DE APROBACIÓN

### ✅ MÍNIMO PARA APROBAR (6/10):
- [ ] Bug DELETE corregido
- [ ] Validaciones implementadas
- [ ] GlobalExceptionHandler básico
- [ ] Al menos 2 tests unitarios

### 🏆 PARA DESTACAR (8/10):
- [ ] Todo lo anterior +
- [ ] DTOs implementados
- [ ] Suite de tests completa
- [ ] Logging apropiado

### 🌟 EXCELENCIA (10/10):
- [ ] Todo lo anterior +
- [ ] Documentación API
- [ ] Manejo avanzado de errores
- [ ] Tests de integración

## ⚠️ ERRORES COMUNES A EVITAR

- [ ] **NO** exponer entidades directamente en endpoints
- [ ] **NO** olvidar manejar casos de "no encontrado"
- [ ] **NO** usar `@Autowired` en campos (usar constructor)
- [ ] **NO** olvidar validar datos de entrada
- [ ] **NO** ignorar el manejo reactivo de errores

---

## 📋 CHECKLIST FINAL PRE-ENTREGA

- [ ] ✅ Aplicación arranca sin errores
- [ ] ✅ Todos los endpoints funcionan
- [ ] ✅ Tests pasan correctamente
- [ ] ✅ No hay warnings importantes
- [ ] ✅ Código bien formateado
- [ ] ✅ Commits descriptivos en Git

**¡TIEMPO RESTANTE: ___ minutos**