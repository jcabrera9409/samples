package com.webflux.test.controller;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.webflux.test.dto.AlumnoRequestDTO;
import com.webflux.test.dto.AlumnoResponseDTO;
import com.webflux.test.service.IAlumnoService;

import reactor.core.publisher.Mono;

@WebFluxTest(AlumnoController.class)
class AlumnoControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private IAlumnoService alumnoService;

    @Test
    void testEndpointPostCrearAlumno() {
        // Given
        AlumnoRequestDTO request = AlumnoRequestDTO.builder()
                .nombre("Ana")
                .apellido("García")
                .edad(22)
                .email("ana.garcia@email.com")
                .build();

        AlumnoResponseDTO response = AlumnoResponseDTO.builder()
                .id(1L)
                .nombre("Ana")
                .apellido("García")
                .edad(22)
                .email("ana.garcia@email.com")
                .build();

        when(alumnoService.create(any(AlumnoRequestDTO.class))).thenReturn(Mono.just(response));

        // When & Then
        webTestClient.post()
                .uri("/alumnos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.nombre").isEqualTo("Ana")
                .jsonPath("$.apellido").isEqualTo("García")
                .jsonPath("$.edad").isEqualTo(22)
                .jsonPath("$.email").isEqualTo("ana.garcia@email.com");
    }
}