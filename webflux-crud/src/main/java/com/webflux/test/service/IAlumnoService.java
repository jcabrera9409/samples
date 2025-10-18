package com.webflux.test.service;

import com.webflux.test.model.Alumno;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAlumnoService {
    Mono<Alumno> create(Alumno alumno);
    Mono<Alumno> update(Alumno alumno);
    Mono<Void> delete(Long id);
    Mono<Alumno> findById(Long id);
    Flux<Alumno> findAll();
}