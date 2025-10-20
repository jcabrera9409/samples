# Seguridad en WebFlux - OAuth2 y JWT

## OAuth2 (Open Authorization 2.0)

### ¿Qué es OAuth2?
OAuth2 es un framework de autorización que permite a las aplicaciones obtener acceso limitado a cuentas de usuario en un servicio HTTP. Es el estándar de facto para autorización en APIs REST.

### Flujos de Autorización (Grant Types)

#### 1. Authorization Code Flow
- **Uso**: Aplicaciones web del lado del servidor
- **Flujo**: 
  1. Cliente redirige al usuario al servidor de autorización
  2. Usuario se autentica y autoriza
  3. Servidor devuelve código de autorización
  4. Cliente intercambia código por access token

```
GET /authorize?response_type=code&client_id=CLIENT_ID&redirect_uri=CALLBACK_URL&scope=read

POST /token
{
  "grant_type": "authorization_code",
  "code": "AUTH_CODE",
  "client_id": "CLIENT_ID",
  "client_secret": "CLIENT_SECRET"
}
```

#### 2. Client Credentials Flow
- **Uso**: Comunicación servidor a servidor (M2M)
- **Ideal para**: APIs internas del BCP

```
POST /token
{
  "grant_type": "client_credentials",
  "client_id": "CLIENT_ID",
  "client_secret": "CLIENT_SECRET",
  "scope": "api:read api:write"
}
```

#### 3. Resource Owner Password Credentials
- **Uso**: Aplicaciones de confianza (móviles del banco)
- **Cuidado**: Solo cuando el cliente es altamente confiable

### Componentes de OAuth2

1. **Resource Owner**: Usuario final
2. **Client**: Aplicación que solicita acceso
3. **Authorization Server**: Servidor que autentica y autoriza
4. **Resource Server**: Servidor que aloja los recursos protegidos

### Tokens en OAuth2

- **Access Token**: Token de corta duración (15-60 min)
- **Refresh Token**: Token de larga duración para renovar access tokens
- **Scope**: Define permisos específicos (read, write, admin)

## JWT (JSON Web Tokens)

### ¿Qué es JWT?
JWT es un estándar (RFC 7519) para transmitir información de forma segura entre partes como un objeto JSON compacto y auto-contenido.

### Estructura JWT
```
HEADER.PAYLOAD.SIGNATURE
```

#### 1. Header
```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```

#### 2. Payload (Claims)
```json
{
  "sub": "1234567890",
  "name": "Juan Pérez",
  "iat": 1516239022,
  "exp": 1516242622,
  "aud": "bcp-api",
  "iss": "auth.bcp.com.pe",
  "scope": "accounts:read transactions:write"
}
```

#### 3. Signature
```
HMACSHA256(
  base64UrlEncode(header) + "." +
  base64UrlEncode(payload),
  secret
)
```

### Claims Estándar (RFC 7519)

- **iss** (Issuer): Quien emitió el token
- **sub** (Subject): Identificador del usuario
- **aud** (Audience): Para quién está destinado
- **exp** (Expiration): Tiempo de expiración
- **iat** (Issued At): Cuándo se emitió
- **nbf** (Not Before): No válido antes de esta fecha
- **jti** (JWT ID): Identificador único del token

### Ventajas de JWT

1. **Stateless**: No requiere almacenamiento en servidor
2. **Self-contained**: Toda la información está en el token
3. **Escalable**: Ideal para microservicios
4. **Cross-domain**: Funciona entre diferentes dominios

### Desventajas de JWT

1. **Tamaño**: Más grande que session IDs
2. **Revocación**: Difícil de invalidar antes de expiración
3. **Información sensible**: Payload es visible (solo firmado)

## Implementación en Spring WebFlux

### Configuración OAuth2 Resource Server

```java
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
            .authorizeExchange(exchanges -> exchanges
                .pathMatchers("/public/**").permitAll()
                .pathMatchers("/api/admin/**").hasRole("ADMIN")
                .anyExchange().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtDecoder(jwtDecoder()))
            )
            .csrf().disable()
            .build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return ReactiveJwtDecoders.fromIssuerLocation("https://auth.bcp.com.pe");
    }
}
```

### Validación Custom de JWT

```java
@Component
public class JwtAuthenticationConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = extractAuthorities(jwt);
        return Mono.just(new JwtAuthenticationToken(jwt, authorities));
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        List<String> scopes = jwt.getClaimAsStringList("scope");
        return scopes.stream()
            .map(scope -> new SimpleGrantedAuthority("SCOPE_" + scope))
            .collect(Collectors.toList());
    }
}
```

### Cliente WebFlux OAuth2

```java
@Service
public class ExternalApiClient {

    private final WebClient webClient;

    public ExternalApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
            .filter(oauth2Credentials())
            .build();
    }

    private ExchangeFilterFunction oauth2Credentials() {
        return ServerOAuth2AuthorizedClientExchangeFilterFunction
            .clientCredentials();
    }

    public Mono<String> callExternalApi() {
        return webClient
            .get()
            .uri("https://api.external.com/data")
            .retrieve()
            .bodyToMono(String.class);
    }
}
```

## Consideraciones de Seguridad para BCP

### 1. Algoritmos de Firma Recomendados
- **RS256** (RSA + SHA256): Para entornos distribuidos
- **HS256** (HMAC + SHA256): Para entornos controlados

### 2. Tiempo de Expiración
- **Access Token**: 15-30 minutos máximo
- **Refresh Token**: 7-30 días
- **ID Token**: 1 hora

### 3. Validaciones Obligatorias
```java
@Component
public class JwtValidator {

    public Mono<Boolean> validateToken(Jwt jwt) {
        return Mono.fromCallable(() -> {
            // Validar issuer
            if (!"auth.bcp.com.pe".equals(jwt.getIssuer().toString())) {
                throw new JwtValidationException("Invalid issuer");
            }
            
            // Validar audience
            List<String> audiences = jwt.getAudience();
            if (!audiences.contains("bcp-api")) {
                throw new JwtValidationException("Invalid audience");
            }
            
            // Validar expiración
            if (jwt.getExpiresAt().isBefore(Instant.now())) {
                throw new JwtValidationException("Token expired");
            }
            
            return true;
        });
    }
}
```

### 4. Rate Limiting
```java
@Component
public class RateLimitFilter implements WebFilter {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String clientId = extractClientId(exchange);
        return checkRateLimit(clientId)
            .flatMap(allowed -> {
                if (allowed) {
                    return chain.filter(exchange);
                } else {
                    exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                    return exchange.getResponse().setComplete();
                }
            });
    }
}
```

## Patrones de Seguridad en Microservicios

### 1. API Gateway Pattern
- Centralizar autenticación/autorización
- Validar tokens una sola vez
- Propagar contexto de seguridad

### 2. Token Relay Pattern
```java
@GetMapping("/customer/{id}/accounts")
public Mono<List<Account>> getCustomerAccounts(
    @PathVariable String id,
    ServerHttpRequest request) {
    
    String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    
    return accountService.getAccountsByCustomer(id, authHeader);
}
```

### 3. Token Exchange Pattern
- Intercambiar tokens entre servicios
- Implementar diferentes scopes por servicio

## Preguntas Frecuentes en Entrevistas

### 1. ¿Cuándo usar OAuth2 vs JWT directamente?
- **OAuth2**: Framework completo de autorización
- **JWT**: Formato de token (puede usarse dentro de OAuth2)

### 2. ¿Cómo manejar la revocación de tokens JWT?
- Blacklist en Redis con TTL
- Tokens de corta duración
- Token introspection endpoint

### 3. ¿Diferencia entre autenticación y autorización?
- **Autenticación**: ¿Quién eres? (login)
- **Autorización**: ¿Qué puedes hacer? (permisos)

### 4. ¿Ventajas de WebFlux para aplicaciones con OAuth2?
- **Non-blocking**: Mejor para validaciones de tokens remotas
- **Reactive streams**: Manejo eficiente de tokens masivos
- **Backpressure**: Control de flujo en validaciones

### 5. ¿Cómo implementar CORS con OAuth2?
```java
@Bean
public CorsWebFilter corsWebFilter() {
    CorsConfiguration corsConfig = new CorsConfiguration();
    corsConfig.setAllowCredentials(true);
    corsConfig.addAllowedOriginPattern("https://*.bcp.com.pe");
    corsConfig.addAllowedHeader("*");
    corsConfig.addAllowedMethod("*");
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfig);
    
    return new CorsWebFilter(source);
}
```

## Mejores Prácticas

1. **Nunca almacenar secretos en código**
2. **Usar HTTPS siempre**
3. **Implementar logging de seguridad**
4. **Validar todos los claims**
5. **Implementar circuit breaker para auth**
6. **Usar refresh tokens rotation**
7. **Implementar token binding**
8. **Validar redirect URIs**

## Métricas de Seguridad

```java
@Component
public class SecurityMetrics {

    private final MeterRegistry meterRegistry;
    
    public void recordTokenValidation(String result) {
        Counter.builder("auth.token.validation")
            .tag("result", result)
            .register(meterRegistry)
            .increment();
    }
    
    public void recordLoginAttempt(String status) {
        Counter.builder("auth.login.attempts")
            .tag("status", status)
            .register(meterRegistry)
            .increment();
    }
}
```

---

**Nota**: Este resumen cubre los conceptos fundamentales para una entrevista senior en BCP. Enfócate en explicar los patrones reactivos y cómo se integran con la arquitectura de microservicios del banco.