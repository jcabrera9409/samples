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

import com.webflux.test.model.Alumno;
import com.webflux.test.service.IAlumnoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    private IAlumnoService alumnoService;


    @PostMapping
    public  Mono<ResponseEntity<Alumno>> crearAlumno(@RequestBody Alumno alumno) {
        Mono<Alumno> alumnoCreado = alumnoService.create(alumno);
        return alumnoCreado.map(alumnoResp -> ResponseEntity.status(HttpStatus.CREATED).body(alumnoResp));
    }

    @PutMapping
    public Mono<ResponseEntity<Alumno>> actualizarAlumno(@RequestBody Alumno alumno) {
        Mono<Alumno> alumnoActualizado = alumnoService.update(alumno);
        return alumnoActualizado.map(alumnoResp -> ResponseEntity.ok(alumnoResp));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminarAlumno(@PathVariable Long id) {
        alumnoService.delete(id);
        return Mono.just(ResponseEntity.noContent().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Alumno>> obtenerAlumnoPorId(@PathVariable Long id) {
        Mono<Alumno> alumno = alumnoService.findById(id);
        return alumno.map(alumnoResp -> ResponseEntity.ok(alumnoResp))
                     .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<Alumno> obtenerTodosLosAlumnos() {
        return alumnoService.findAll();
    }

    @GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Alumno> streamAlumnos() {
        return alumnoService.findAll()
            .delayElements(Duration.ofMillis(500));
    }
}