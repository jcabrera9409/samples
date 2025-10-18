package com.webflux.test.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
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

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    private IAlumnoService alumnoService;


    @PostMapping
    public  Mono<ResponseEntity<AlumnoResponseDTO>> crearAlumno(@RequestBody AlumnoRequestDTO alumno) {
        Mono<AlumnoResponseDTO> alumnoCreado = alumnoService.create(alumno);
        return alumnoCreado.map(alumnoResp -> ResponseEntity.status(HttpStatus.CREATED).body(alumnoResp));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<AlumnoResponseDTO>> actualizarAlumno(@PathVariable Long id, @RequestBody AlumnoRequestDTO alumno) {
        Mono<AlumnoResponseDTO> alumnoActualizado = alumnoService.update(id, alumno);
        return alumnoActualizado.map(alumnoResp -> ResponseEntity.ok(alumnoResp));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminarAlumno(@PathVariable Long id) {
        return alumnoService.delete(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<AlumnoResponseDTO>> obtenerAlumnoPorId(@PathVariable Long id) {
        Mono<AlumnoResponseDTO> alumno = alumnoService.findById(id);
        return alumno.map(alumnoResp -> ResponseEntity.ok(alumnoResp))
                     .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<AlumnoResponseDTO> obtenerTodosLosAlumnos() {
        return alumnoService.findAll();
    }

    @GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AlumnoResponseDTO> streamAlumnos() {
        return alumnoService.findAll()
            .delayElements(Duration.ofMillis(500));
    }
}