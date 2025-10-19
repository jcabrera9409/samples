package com.webflux.test.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.webflux.test.dto.AlumnoRequestDTO;
import com.webflux.test.dto.AlumnoResponseDTO;
import com.webflux.test.exception.AlumnoNotFoundException;
import com.webflux.test.model.Alumno;
import com.webflux.test.repository.IAlumnoRepository;
import com.webflux.test.service.impl.AlumnoServiceImpl;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class AlumnoServiceTest {

    @Mock
    private IAlumnoRepository alumnoRepository;

    @InjectMocks
    private AlumnoServiceImpl alumnoService;

    private AlumnoRequestDTO alumnoRequestDTO;
    private Alumno alumno;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        alumnoRequestDTO = AlumnoRequestDTO.builder()
                .nombre("Juan")
                .apellido("Pérez")
                .edad(25)
                .email("juan.perez@email.com")
                .build();

        alumno = Alumno.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Pérez")
                .edad(25)
                .email("juan.perez@email.com")
                .build();
    }

    @Test
    void testCrearAlumnoExitoso() {
        // Given
        when(alumnoRepository.save(any(Alumno.class))).thenReturn(Mono.just(alumno));

        // When
        Mono<AlumnoResponseDTO> result = alumnoService.create(alumnoRequestDTO);

        // Then
        StepVerifier.create(result)
                .expectNextMatches(response -> 
                    response.getId().equals(1L) &&
                    response.getNombre().equals("Juan") &&
                    response.getApellido().equals("Pérez") &&
                    response.getEdad() == 25 &&
                    response.getEmail().equals("juan.perez@email.com")
                )
                .verifyComplete();
    }

    @Test
    void testBuscarAlumnoPorIdExistente() {
        // Given
        when(alumnoRepository.findById(1L)).thenReturn(Mono.just(alumno));

        // When
        Mono<AlumnoResponseDTO> result = alumnoService.findById(1L);

        // Then
        StepVerifier.create(result)
                .expectNextMatches(response -> 
                    response.getId().equals(1L) &&
                    response.getNombre().equals("Juan") &&
                    response.getApellido().equals("Pérez") &&
                    response.getEdad() == 25 &&
                    response.getEmail().equals("juan.perez@email.com")
                )
                .verifyComplete();
    }

    @Test
    void testBuscarAlumnoPorIdNoExistente() {
        // Given
        when(alumnoRepository.findById(anyLong())).thenReturn(Mono.empty());

        // When
        Mono<AlumnoResponseDTO> result = alumnoService.findById(999L);

        // Then
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> 
                    throwable instanceof AlumnoNotFoundException &&
                    throwable.getMessage().equals("Alumno con ID 999 no encontrado")
                )
                .verify();
    }
}