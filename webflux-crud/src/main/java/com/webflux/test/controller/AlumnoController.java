package com.webflux.test.controller;

import java.time.Duration;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webflux.test.dto.AlumnoRequestDTO;
import com.webflux.test.dto.AlumnoResponseDTO;
import com.webflux.test.service.IAlumnoService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    private final IAlumnoService alumnoService;

    public AlumnoController(IAlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @PostMapping
    public Mono<ResponseEntity<AlumnoResponseDTO>> crearAlumno(@Valid @RequestBody AlumnoRequestDTO alumno) {
        log.info("Creando alumno: {}", alumno);
        Mono<AlumnoResponseDTO> alumnoCreado = alumnoService.create(alumno);
        return alumnoCreado.map(alumnoResp -> ResponseEntity.status(HttpStatus.CREATED).body(alumnoResp));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<AlumnoResponseDTO>> actualizarAlumno(@PathVariable Long id, @Valid @RequestBody AlumnoRequestDTO alumno) {
        log.info("Actualizando alumno con ID {}: {}", id, alumno);
        Mono<AlumnoResponseDTO> alumnoActualizado = alumnoService.update(id, alumno);
        return alumnoActualizado.map(alumnoResp -> ResponseEntity.ok(alumnoResp));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminarAlumno(@PathVariable Long id) {
        log.info("Eliminando alumno con ID {}", id);
        return alumnoService.delete(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<AlumnoResponseDTO>> obtenerAlumnoPorId(@PathVariable Long id) {
        log.info("Obteniendo alumno con ID {}", id);
        Mono<AlumnoResponseDTO> alumno = alumnoService.findById(id);
        return alumno.map(alumnoResp -> ResponseEntity.ok(alumnoResp))
                     .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<AlumnoResponseDTO> obtenerTodosLosAlumnos() {
        log.info("Obteniendo todos los alumnos");
        return alumnoService.findAll();
    }

    @GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AlumnoResponseDTO> streamAlumnos() {
        log.info("Iniciando stream de alumnos");
        return alumnoService.findAll()
            .delayElements(Duration.ofMillis(500));
    }
}